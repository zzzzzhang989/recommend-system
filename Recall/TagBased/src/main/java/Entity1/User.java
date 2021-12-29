package Entity1;

import java.util.List;

public class User {
    private int id;
    private List<String> tagList;

    public User(int id, List<String>tagList){
        this.id = id;
        this.tagList = tagList;
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
