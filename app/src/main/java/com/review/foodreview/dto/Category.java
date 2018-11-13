package com.review.foodreview.dto;

public class Category {
    String id, name;
    public Category(){}
    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
