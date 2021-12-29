package Entity2;

import java.util.Map;

public class User {
    private int id;
    private Map<String, String> tagList;

    private double totalRating;
    private int ratingCount;
    private double average;

    public User(int id, Map<String, String> tagList){
        this.id = id;
        this.tagList = tagList;
        this.totalRating = 0;
        this.ratingCount = 0;
        this.average = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Map<String, String> getTagList() {
        return tagList;
    }

    public void setTagList(Map<String, String> tagList) {
        this.tagList = tagList;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getAverage() {
        return average;
    }
}
