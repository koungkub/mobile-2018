package com.review.foodreview.dto;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

/**
 * Restaurant review
 * @return Review
 */
public class Review {
    private String reviewId, description;
    private DocumentReference authorRef, restaurantRef;
    private Timestamp date;
    private List<String> imageUriList;
    private RestaurantRating rating;

    public Review(String reviewId,
                  DocumentReference authorRef,
                  DocumentReference restaurantRef,
                  String description,
                  Timestamp date,
                  List<String> imageUriList,
                  RestaurantRating rating) {
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

    public RestaurantRating getRating() {
        return rating;
    }
}
