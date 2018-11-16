package com.review.foodreview.dto;

import com.google.firebase.firestore.DocumentReference;

public class Bookmark {
    private DocumentReference owner;
    private DocumentReference restaurant;

    public Bookmark(DocumentReference ownerRef, DocumentReference restaurantRef) {
        this.owner = ownerRef;
        this.restaurant = restaurantRef;
    }

    public DocumentReference getOwner() {
        return owner;
    }

    public DocumentReference getRestaurant() {
        return restaurant;
    }
}
