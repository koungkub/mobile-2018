package com.review.foodreview.dto;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.model.value.ReferenceValue;

import java.util.List;

public class User {
    String name, uuid;
    List<DocumentReference> bookmark;
    public User(){}
    public User(String name, String uuid, List<DocumentReference> bookmark) {
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
