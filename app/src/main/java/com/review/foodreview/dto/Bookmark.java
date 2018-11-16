package com.review.foodreview.dto;

import com.google.firebase.firestore.DocumentReference;

public class Bookmark {
    private DocumentReference ownerRef;
    private DocumentReference restaurantRef;

    public Bookmark(DocumentReference ownerRef, DocumentReference restaurantRef) {
        this.ownerRef = ownerRef;
        this.restaurantRef = restaurantRef;
    }

    public DocumentReference getOwnerRef() {
        return ownerRef;
    }

    public DocumentReference getRestaurantRef() {
        return restaurantRef;
    }
}
