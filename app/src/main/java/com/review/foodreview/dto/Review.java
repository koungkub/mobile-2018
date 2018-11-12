package com.review.foodreview.dto;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

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
    private String _date;
    private List<String> imageUriList;
    private RestaurantRating rating;
    // use this you should fix your code argument
    private DocumentReference author;
    private Timestamp date;
    private List<String> imageUri;
    private DocumentReference restaurant;

    public Review(){

    }
    public Review(
            DocumentReference author,
            Timestamp date,
            String id,
            List<String> imageUri,
            DocumentReference restaurant

    ){
        this.author = author;
        this.id = id;
        this.date = date;
        this.imageUri = imageUri;
        this.restaurant = restaurant;
    }

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
        this._date = date;
        this.imageUriList = imageUriList;
        this.rating = rating;
    }

    public DocumentReference getAuthor() { return author; }

    public List<String> getImageUri() { return imageUri; }

    public DocumentReference getRestaurant() { return restaurant; }

    public Timestamp getDate() {
        return date;
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

    public String get_date() { return _date; }


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
