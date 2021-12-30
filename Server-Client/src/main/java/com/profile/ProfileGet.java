package com.profile;

import com.Utils.Serialize;
import com.service.MovieProfile;
import com.service.UserProfile;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProfileGet {

    Jedis jedis;

    public ProfileGet() {
        jedis = new Jedis("localhost");
    }

    public UserProfile getUserProfileById(long user_id) {

        jedis.select(8);
        UserProfile user;
        user = (UserProfile) Serialize.unserialize(jedis.get(String.valueOf(user_id).getBytes()));
        return user;
    }

    public MovieProfile getMovieProfileById(long movie_id) {

        jedis.select(9);
        MovieProfile movie;
        movie = (MovieProfile) Serialize.unserialize(jedis.get(String.valueOf(movie_id).getBytes()));
        return movie;
    }

    public List<UserProfile> getAllUserProfile() {
        List<UserProfile> userlist = new ArrayList<>();
        UserProfile user;
        jedis.select(8);
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            user = (UserProfile) Serialize.unserialize(jedis.get(key.getBytes()));
            userlist.add(user);
        }
        return userlist;
    }

    public List<MovieProfile> getAllMovieProfile() {
        List<MovieProfile> movielist = new ArrayList<>();
        MovieProfile movie;
        jedis.select(9);
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            movie = (MovieProfile) Serialize.unserialize(jedis.get(key.getBytes()));
            movielist.add(movie);
        }
        return movielist;
    }
}
