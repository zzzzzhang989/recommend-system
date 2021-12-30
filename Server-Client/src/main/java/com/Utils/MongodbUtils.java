package com.Utils;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MongodbUtils {
    public static void writeToMongodb() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        //创建数据库对象
        MongoDatabase mongoDatabase = mongoClient.getDatabase("recommend");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("userRecommend");

        String filepath = "src/main/resources/data/rank/recommend_movies.csv";
        String line;
        BufferedReader bin = null;
        String[] buf;
        String[] movies;

        try {
            bin = new BufferedReader(new FileReader(filepath));
            bin.readLine();
            while ((line = bin.readLine()) != null) {
                buf = line.split(",");
                String uid = buf[0];
                movies = buf[1].split("\\|");
                Document document = new Document("uid", uid);
                for (int i = 0; i < movies.length; i++) {
                    //只存了movieid
                    document.append(String.valueOf(i + 1), movies[i]);
                }
                mongoCollection.insertOne(document);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<Integer> readFromMongodb(String userId) {
        List<Integer> recommend_movie_ids = new ArrayList<>();
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("recommend");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("userRecommend");
        //查询
        Bson filter = Filters.eq("uid", userId);
        FindIterable<Document> findIterable = mongoCollection.find(filter);
        for (Document o : findIterable) {
            for (int i = 1; i <= 5; i++) {
                recommend_movie_ids.add(Integer.parseInt(o.get(String.valueOf(i)).toString()));
            }

        }
        return recommend_movie_ids;
    }

    public static void main(String[] args) {
        // MongodbUtils.writeToMongodb();
        List<Integer> movies = MongodbUtils.readFromMongodb("1");
        for (Integer i : movies) {
            System.out.println(i);
        }
    }

}
