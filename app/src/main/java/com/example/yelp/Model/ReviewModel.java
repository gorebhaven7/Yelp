package com.example.yelp.Model;

public class ReviewModel {

    private String name;
    private String rating;
    private String text;
    private String time_created;

    public ReviewModel(){

    }

    public ReviewModel(String name, String rating, String text, String time_created) {
        this.name = name;
        this.rating = rating;
        this.text = text;
        this.time_created = time_created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime_created() {
        return time_created;
    }

    public void setTime_created(String time_created) {
        this.time_created = time_created;
    }
}
