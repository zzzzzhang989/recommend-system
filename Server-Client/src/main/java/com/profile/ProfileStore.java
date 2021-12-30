package com.profile;

import com.Utils.Serialize;
import com.service.MovieProfile;
import com.service.UserProfile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ProfileStore {
    private Jedis jedis;
    private String USER_PRO_URL = "src/main/resources/data/profile/profile_user.csv";
    private String MOVIE_PRO_URL = "src/main/resources/data/profile/profile_movie.csv";

    public ProfileStore() {
        jedis = new Jedis("localhost");
    }

    private UserProfile getUserPro(String line) {
        UserProfile.Builder user = UserProfile.newBuilder();
        String buf[] = line.split(",");
        user.setUserId(Long.parseLong(buf[0]));
        for (int i = 1; i <= 5; i++) {
            user.addUserTag(Long.parseLong(buf[i]));
        }
        user.setAvgScore(Float.parseFloat(buf[6]));
        return user.build();
    }

    private MovieProfile getMoviePro(String line) {
        int startlength = 0;
        MovieProfile.Builder movie = MovieProfile.newBuilder();
        String[] buf = line.split(",");
        movie.setMovieId(Long.parseLong(buf[0]));
        startlength += buf[0].length();
        for (int i = 1; i <= 5; i++) {
            movie.addMovieTag(Long.parseLong(buf[i]));
            startlength += buf[i].length();
        }
        movie.setAvgScore(Float.parseFloat(buf[6]));
        startlength += buf[6].length();
        movie.setTitle(line.substring(startlength + 7));
        return movie.build();
    }

    /**
     * 存储新的userProfile
     */
    public void storeUserPro() {
        jedis.select(8);
        Pipeline pipeline = jedis.pipelined();
        BufferedReader bin = null;
        String tmpline = "";
        UserProfile userProfile;
        byte[] bytes;
        try {
            bin = new BufferedReader(new FileReader(USER_PRO_URL));
            bin.readLine();
            while ((tmpline = bin.readLine()) != null) {
                userProfile = getUserPro(tmpline);
                bytes = Serialize.serialize(userProfile);
                String key = tmpline.split(",")[0];
                pipeline.set(key.getBytes(), bytes);
            }
            pipeline.sync();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bin != null) {
                try {
                    bin.close();
                } catch (IOException e1) {
                }
            }
        }
    }

//    public static void main(String[] args) {
//        ProfileStore store = new ProfileStore();
//        String MOVIE_PRO_URL = "src/main/resources/data/profile/profile_movie.csv";
//        BufferedReader bin = null;
//        String tmpline = "";
//        MovieProfile movieProfile;
//        byte[] bytes;
//        try {
//            bin = new BufferedReader(new FileReader(MOVIE_PRO_URL));
//            bin.readLine();
//            int count = 0;
//            while ((tmpline = bin.readLine()) != null) {
//                movieProfile = store.getMoviePro(tmpline);
//                System.out.println(movieProfile);
//                if (count > 11) break;
//                count++;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (bin != null) {
//                try {
//                    bin.close();
//                } catch (IOException ignored) {
//                }
//            }
//        }
//    }


    /**
     * 存储新的movieProfile
     */
    public void storeMoviePro() {
        jedis.select(9);
        Pipeline pipeline = jedis.pipelined();
        BufferedReader bin = null;
        String tmpline = "";
        MovieProfile movieProfile;
        byte[] bytes;
        try {
            bin = new BufferedReader(new FileReader(MOVIE_PRO_URL));
            bin.readLine();
            while ((tmpline = bin.readLine()) != null) {
                movieProfile = getMoviePro(tmpline);
                bytes = Serialize.serialize(movieProfile);
                String key = tmpline.split(",")[0];
                pipeline.set(key.getBytes(), bytes);
            }
            pipeline.sync();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bin != null) {
                try {
                    bin.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}
