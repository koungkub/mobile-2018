package com.review.foodreview.dto;


public class User {
    String name, uuid;
    public User(){}
    public User(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }
    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }
}
