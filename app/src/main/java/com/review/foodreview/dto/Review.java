package com.review.foodreview.dto;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.List;

/**
 * Restaurant review
 * @return Review
 */
public class Review {
    private final String reviewId;
    private final String description;
    private final DocumentReference authorRef;
    private final DocumentReference restaurantRef;
    private final Timestamp date;
    private final List<String> imageUriList;
    // private RestaurantRating rating;
    private final HashMap<String, Long> rating;

    public Review(String reviewId,
                  DocumentReference authorRef,
                  DocumentReference restaurantRef,
                  String description,
                  Timestamp date,
                  List<String> imageUriList,
                  HashMap<String, Long> rating) {
        this.reviewId = reviewId;
        this.authorRef = authorRef;
        this.restaurantRef = restaurantRef;
        this.description = description;
        this.date = date;
        this.imageUriList = imageUriList;
        this.rating = rating;
    }

    public String getReviewId() {
        return reviewId;
    }

    public DocumentReference getAuthorRef() {
        return authorRef;
    }

    public DocumentReference getRestaurantRef() {
        return restaurantRef;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDate() {
        return date;
    }

    public List<String> getImageUriList() {
        return imageUriList;
    }

    public HashMap<String, Long> getRating() {
        return rating;
    }
}
