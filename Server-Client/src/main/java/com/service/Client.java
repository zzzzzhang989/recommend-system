package com.service;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final ServiceGrpc.ServiceBlockingStub blockingStub;

    public Client(Channel channel) {
        blockingStub = ServiceGrpc.newBlockingStub(channel);
    }

    public void getRecommendMoviesById(int id, int type) {
        RecommendMovieRequest recommendMovieRequest = RecommendMovieRequest.newBuilder().
                setUserId(id).setRecommendWay(type).build();
        RecommendMovieResponse recommendMovieResponse;
        try {
            recommendMovieResponse = blockingStub.getRecommendMovies(recommendMovieRequest);
        } catch (StatusRuntimeException e) {
            //TODO
            return;
        }
        System.out.println(recommendMovieResponse);
    }

    public static void main(String[] args) throws Exception {
        // Access a service running on the local machine on port 50051
        String target = "localhost:50051";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext(true)
                .build();
        try {
            Client client = new Client(channel);
            System.out.println("*** client successfully connect to server!\n" +
                    "*** please input -1 to quit\n" +
                    "*** input like <uid> <type>\n" +
                    "*** please input type in range [0,1,2]\n" +
                    "*** 0 means non-random, 1 means random, 2 means get from mongodb");
            int id, type;
            Scanner scanner = new Scanner(System.in);
            id = scanner.nextInt();
            type = scanner.nextInt();
            while (type != 0 && type != 1 && type != 2) {
                System.out.println("*** please input type in range [0,1,2]");
                id = scanner.nextInt();
                type = scanner.nextInt();
            }
            while (id != -1) {
                System.out.println("*** is recommending, please wait");
                client.getRecommendMoviesById(id, type);
                System.out.println("*** 0 means non-random, 1 means random, 2 means get from mongodb");
                scanner = new Scanner(System.in);
                id = scanner.nextInt();
                type = scanner.nextInt();
                while (type != 0 && type != 1 && type != 2) {
                    System.out.println("*** please input type in range [0,1,2]");
                    id = scanner.nextInt();
                    type = scanner.nextInt();
                }
            }
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
