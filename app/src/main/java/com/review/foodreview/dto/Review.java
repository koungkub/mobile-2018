package com.review.foodreview.dto;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

/**
 * Restaurant review
 * @return Review
 */
public class Review {
    private String reviewId, authorId, restaurantId, description;
    private Timestamp date;
    private List<String> imageUriList;
    private RestaurantRating rating;

    public Review(String reviewId,
                  String authorId,
                  String restaurantId,
                  String description,
                  Timestamp date,
                  List<String> imageUriList,
                  RestaurantRating rating) {
        this.reviewId = reviewId;
        this.authorId = authorId;
        this.restaurantId = restaurantId;
        this.description = description;
        this.date = date;
        this.imageUriList = imageUriList;
        this.rating = rating;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getRestaurantId() {
        return restaurantId;
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
