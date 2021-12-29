package Entity1;

import java.util.ArrayList;
import java.util.List;

public class MovieList {
    private static MovieList instance = null;
    private final List<Movie> movieList;

    private MovieList() {
        movieList = new ArrayList<>();
    }

    public static MovieList getInstance() {
        if (instance == null) {
            instance = new MovieList();
        }
        return instance;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void addMoive(int id, String title, List<String> tagList){
        movieList.add(new Movie(id, title, tagList));
    }
}