package com.service;

import com.Utils.MongodbUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.profile.ProfileGet;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class ServerRestful {

    /**
     * 日志
     */
    private static final Logger logger = Logger.getLogger(ServerRestful.class.getName());
    /**
     * 服务端开启对象
     */
    private Server server;

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new ServiceImpl())
                .build()
                .start();
        System.out.println("*** server start up");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    ServerRestful.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final ServerRestful server = new ServerRestful();
        server.start();
        server.blockUntilShutdown();
    }


    static class ServiceImpl extends ServiceGrpc.ServiceImplBase {

        /**
         * 发送post请求
         *
         * @param url      路径
         * @param inputs   输入
         * @param encoding 编码格式
         * @return
         */
        public static List<Float> postToGetResult(String url, String inputs, String encoding) throws ParseException, IOException {
            List<Float> result = new ArrayList<>();
            //创建httpclient对象
            CloseableHttpClient client = HttpClients.createDefault();
            //创建post方式请求对象
            HttpPost httpPost = new HttpPost(url);
            //装填参数
            StringEntity s = new StringEntity(JSONObject.parse("{\"signature_name\": \"serving_default\",\"instances\":" + inputs + "}").toString());
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            //设置参数到请求对象中
            httpPost.setEntity(s);
            // System.out.println("请求地址：" + url);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //执行请求操作，并拿到结果（同步阻塞）
            CloseableHttpResponse response = client.execute(httpPost);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                JSONArray items = JSONObject.parseObject(EntityUtils.toString(entity, encoding)).
                        getJSONArray("predictions");
                for (Object item : items) {
                    result.add(Float.parseFloat(JSONArray.parseArray(item.toString()).get(0).toString()));
                }
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        }


        private List<Integer> createRandomList(List<Integer> list, int n) {
            Map<Integer, String> map = new HashMap<>();
            List<Integer> listNew = new ArrayList<>();
            if (list.size() <= n) {
                return list;
            } else {
                while (map.size() < n) {
                    int random = (int) (Math.random() * list.size());
                    if (!map.containsKey(random)) {
                        map.put(random, "");
                        listNew.add(list.get(random));
                    }
                }
                return listNew;
            }
        }

        @Override
        public void getRecommendMovies(RecommendMovieRequest request, StreamObserver<RecommendMovieResponse> responseObserver) {
            // super.getRecommendMovies(request, responseObserver);
            RecommendMovieResponse.Builder builder = RecommendMovieResponse.newBuilder();
            ProfileGet profileGet = new ProfileGet();

            if (request.getRecommendWay() == 2) {
                List<Integer> movieIds = MongodbUtils.readFromMongodb(String.valueOf(request.getUserId()));

                for (int movieId : movieIds) {
                    Movie.Builder movieBuilder = Movie.newBuilder();
                    movieBuilder.setMovieId(movieId);
                    movieBuilder.setTitle(profileGet.getMovieProfileById(movieId).getTitle());
                    builder.addMovie(movieBuilder.build());
                }
                RecommendMovieResponse response = builder.build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }


            UserProfile userProfile = profileGet.getUserProfileById(request.getUserId());
            List<MovieProfile> movieProfiles = profileGet.getAllMovieProfile();

            System.out.println("The user profile is :\n" + userProfile);
            List<String> inputs = new ArrayList<>();
            StringBuilder input;
            for (MovieProfile movieProfile : movieProfiles) {
                input = new StringBuilder();
                input.append("[").append(userProfile.getAvgScore()).append(",").append(movieProfile.getAvgScore()).append(",").
                        append(userProfile.getUserTag(0) / 73051.0).append(",").append(userProfile.getUserTag(1) / 73051.0).append(",").
                        append(userProfile.getUserTag(2) / 73051.0).append(",").append(userProfile.getUserTag(3) / 73051.0).append(",").
                        append(userProfile.getUserTag(4) / 73051.0).append(",").append(movieProfile.getMovieTag(0) / 73051.0).append(",").
                        append(movieProfile.getMovieTag(1) / 73051.0).append(",").append(movieProfile.getMovieTag(2) / 73051.0).append(",").
                        append(movieProfile.getMovieTag(3) / 73051.0).append(",").append(movieProfile.getMovieTag(4) / 73051.0).append("]");
                inputs.add(input.toString());
            }
            List<Float> predictions = new ArrayList<>();
            try {
                String url = "http://localhost:8501/v1/models/finalmodel:predict";
                predictions = postToGetResult(url, inputs.toString(), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (request.getRecommendWay() == 0) {
                Float max;
                int index;
                for (int i = 0; i < 5; i++) {
                    max = Collections.max(predictions);
                    index = predictions.indexOf(max);
                    System.out.println(movieProfiles.get(index) + "predictions:" + predictions.get(index) + "\n");
                    predictions.set(index, -1.0f);
                    Movie.Builder movieBuilder = Movie.newBuilder();
                    movieBuilder.setMovieId(movieProfiles.get(index).getMovieId());
                    movieBuilder.setTitle(movieProfiles.get(index).getTitle());
                    builder.addMovie(movieBuilder.build());
                }
            } else {
                List<Integer> indexes = new ArrayList<>();
                for (int i = 0; i < predictions.size(); i++) {
                    if (predictions.get(i) > 0.9f) {
                        indexes.add(i);
                    }
                }

                if (indexes.size() > 5) {
                    List<Integer> newIndexes = createRandomList(indexes, 5);
                    for (Integer i : newIndexes) {
                        Movie.Builder movieBuilder = Movie.newBuilder();
                        movieBuilder.setMovieId(movieProfiles.get(i).getMovieId());
                        movieBuilder.setTitle(movieProfiles.get(i).getTitle());
                        builder.addMovie(movieBuilder.build());
                        System.out.println(movieProfiles.get(i) + "predictions:" + predictions.get(i) + "\n");
                    }
                } else {
                    Float max;
                    int index;
                    for (int i = 0; i < 5; i++) {
                        max = Collections.max(predictions);
                        index = predictions.indexOf(max);
                        System.out.println(movieProfiles.get(index) + "predictions:" + predictions.get(index) + "\n");
                        predictions.set(index, -1.0f);
                        Movie.Builder movieBuilder = Movie.newBuilder();
                        movieBuilder.setMovieId(movieProfiles.get(index).getMovieId());
                        movieBuilder.setTitle(movieProfiles.get(index).getTitle());
                        builder.addMovie(movieBuilder.build());
                    }
                }
            }

            RecommendMovieResponse response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
