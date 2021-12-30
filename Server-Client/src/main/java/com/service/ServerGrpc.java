package com.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.profile.ProfileGet;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
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
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.TensorProto;
import org.tensorflow.framework.TensorShapeProto;
import tensorflow.serving.Model;
import tensorflow.serving.Predict;
import tensorflow.serving.PredictionServiceGrpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class ServerGrpc {

    /**
     * 日志
     */
    private static final Logger logger = Logger.getLogger(ServerGrpc.class.getName());
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
                    ServerGrpc.this.stop();
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
        final ServerGrpc server = new ServerGrpc();
        server.start();
        server.blockUntilShutdown();
    }


    static class ServiceImpl extends ServiceGrpc.ServiceImplBase {


        public static List<Float> grpcToGetResult(List<List<Float>> inputs) {
            List<Float> floatList = Arrays.asList(1.0f, 2.0f, 0.5f, 0.2f, 0.1f, 0.4f, 0.5f, 0.2f, 0.1f, 0.4f, 0.5f, 0.2f);
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8500).usePlaintext(true).build();

            //这里还是先用block模式
            PredictionServiceGrpc.PredictionServiceBlockingStub stub = PredictionServiceGrpc.newBlockingStub(channel);

            //创建请求
            Predict.PredictRequest.Builder predictRequestBuilder = Predict.PredictRequest.newBuilder();

            //模型名称和模型方法名预设
            Model.ModelSpec.Builder modelSpecBuilder = Model.ModelSpec.newBuilder();
            modelSpecBuilder.setName("finalmodel");
            modelSpecBuilder.setSignatureName("serving_default");
            predictRequestBuilder.setModelSpec(modelSpecBuilder);

            //设置入参,访问默认是最新版本，如果需要特定版本可以使用tensorProtoBuilder.setVersionNumber方法
            TensorProto.Builder tensorProtoBuilder = TensorProto.newBuilder();
            tensorProtoBuilder.setDtype(DataType.DT_FLOAT);

            //设置维度
            TensorShapeProto.Builder tensorShapeBuilder = TensorShapeProto.newBuilder();
            tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(-1).setName(""));
            tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(12).setName(""));

            tensorProtoBuilder.setTensorShape(tensorShapeBuilder.build());
            tensorProtoBuilder.addAllFloatVal(floatList);

            predictRequestBuilder.putInputs("dense_input", tensorProtoBuilder.build());

            System.out.println(predictRequestBuilder.build());
            //访问并获取结果
            Predict.PredictRequest request = predictRequestBuilder.build();
            Predict.PredictResponse predictResponse = stub.predict(request);
            org.tensorflow.framework.TensorProto result = predictResponse.toBuilder().getOutputsOrThrow("outputs");
            System.out.println("预测值是:" + result.getFloatValList());
            return result.getFloatValList();
        }


        @Override
        public void getRecommendMovies(RecommendMovieRequest request, StreamObserver<RecommendMovieResponse> responseObserver) {
            // super.getRecommendMovies(request, responseObserver);
            RecommendMovieResponse.Builder builder = RecommendMovieResponse.newBuilder();
            ProfileGet profileGet = new ProfileGet();
            UserProfile userProfile = profileGet.getUserProfileById(request.getUserId());
            List<MovieProfile> movieProfiles = profileGet.getAllMovieProfile();

            System.out.println("The user profile is :\n" + userProfile);
            // List<String> inputs = new ArrayList<>();
            List<List<Float>> inputs = new ArrayList<>();
            List<Float> input;
            for (MovieProfile movieProfile : movieProfiles) {
                input = new ArrayList<>();
                input.add(userProfile.getAvgScore());
                input.add(movieProfile.getAvgScore());
                for (int i = 0; i < 5; i++) {
                    input.add(userProfile.getUserTag(i) / 73051.0f);
                }
                for (int i = 0; i < 5; i++) {
                    input.add(movieProfile.getMovieTag(i) / 73051.0f);
                }
                inputs.add(input);
            }
            List<Float> predictions = grpcToGetResult(inputs);
            Float max;
            int index;
            for (int i = 0; i < 5; i++) {
                max = Collections.max(predictions);
                index = predictions.indexOf(max);
                System.out.println(movieProfiles.get(index).getMovieId() + "\t" +
                        predictions.get(index) + "\n" + movieProfiles.get(index));
                predictions.set(index, -1.0f);
                Movie.Builder movieBuilder = Movie.newBuilder();
                movieBuilder.setMovieId(movieProfiles.get(index).getMovieId());
                movieBuilder.setTitle("fuck!");
                builder.addMovie(movieBuilder.build());
            }

            RecommendMovieResponse response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
