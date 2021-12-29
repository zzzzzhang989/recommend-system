package Entity2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieList {
    private static MovieList instance = null;
    private final Map<Integer, Movie> movieList;

    private MovieList() {
        movieList = new HashMap<>();
    }

    public static MovieList getInstance() {
        if (instance == null) {
            instance = new MovieList();
        }
        return instance;
    }

    public Map<Integer, Movie> getMovieList() {
        return movieList;
    }

    public void addMoive(int id, String title, List<String> tagList) {
        movieList.put(id, new Movie(id, title, tagList));
    }

    public Movie getMovie(int id){
        return movieList.get(id);
    }
}