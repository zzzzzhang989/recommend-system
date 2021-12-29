package Tools;

import Entity1.Movie;
import Entity1.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Alogrithm {

    private static int[] getVector(Map<String, String> map, List<String> target) {
        int[] result = new int[map.size()];
        int i = 0;
        for (String key : map.keySet()) {
            int exist = 0;
            for (String str : target) {
                if (key.equals(str)) {
                    exist = 1;
                    break;
                }
            }
            result[i] = exist;
            i++;
        }
        return result;
    }

    private static double caculateEuclideanDistance(int[] vector1, int[] vector2) {
        double distance = 0.0;
        for (int i = 0; i < vector1.length; i++) {
            distance += Math.pow((vector1[i] - vector2[i]), 2);
        }
        return Math.sqrt(distance);
    }

    public static double caculateSimilarity(Movie movie, User user) {
        Map<String, String> union = new HashMap<>();
        for (String tag : movie.getTagList()) {
            union.put(tag, tag);
        }
        for (String tag : user.getTagList()) {
            union.put(tag, tag);
        }
        int[] movie_vector = getVector(union, movie.getTagList());
        int[] user_vector = getVector(union, user.getTagList());

        return 1 / (1 + caculateEuclideanDistance(movie_vector, user_vector));
    }
}
