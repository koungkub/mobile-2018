package com.review.foodreview.dto;

import java.util.List;

/**
 * Restaurant review
 * @return Review
 */
public class Review {
    private String id;
    private String authorId;
    private String restaurantId;
    private String description;
    private String date;
    private List<String> imageUriList;
    private RestaurantRating rating;

    public Review(String id,
                  String authorId,
                  String restaurantId,
                  String description,
                  String date,
                  List<String> imageUriList,
                  RestaurantRating rating) {
        this.id = id;
        this.authorId = authorId;
        this.restaurantId = restaurantId;
        this.description = description;
        this.date = date;
        this.imageUriList = imageUriList;
        this.rating = rating;
    }

    /**
     * Get review ID
     * @return String
     */
    public String getId() {
        return id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public List<String> getImageUriList() {
        return imageUriList;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public RestaurantRating getRating() {
        return rating;
    }
}
