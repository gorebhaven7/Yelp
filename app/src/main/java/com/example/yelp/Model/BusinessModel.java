package com.example.yelp.Model;

public class BusinessModel {

    private String BusinessId;
    private String name;
    private String image_url;
    private String rating;
    private String distance;
    private String id;
    private String url;



    public BusinessModel(){

    }

    public BusinessModel(String id, String name, String image_url, String rating, String distance, String BusinessId) {
        this.id = id;
        this.name = name;
        this.image_url = image_url;
        this.rating = rating;
        this.distance = distance;
        this.BusinessId = BusinessId;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
