package Entity2;

import java.util.List;

public class Movie {
    private int id;
    private String title;
    private List<String> tagList;

    public Movie(int id, String name, List<String>tagList){
        this.id = id;
        this.title = name;
        this.tagList = tagList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
}
