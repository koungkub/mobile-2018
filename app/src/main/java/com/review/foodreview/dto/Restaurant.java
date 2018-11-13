package com.review.foodreview.dto;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;
import java.util.List;

public class Restaurant {
    private DocumentReference category;
    private boolean delivery;
    private String id, name, restaurantType, priceRange, openHours, telephone;
    private List<String> imageUri;
    private GeoPoint location;
    private List<DocumentReference> review;
    private float rating;
    private int reviewCount;

    public Restaurant(String id,
                      String name,
                      String restaurantType,
                      String priceRange,
                      String openHours,
                      float rating,
                      int reviewCount,
                      boolean deliverable) {
        this.id = id;
        this.name = name;
        this.restaurantType = restaurantType;
        this.priceRange = priceRange;
        this.openHours = openHours;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.delivery = deliverable;
    }

    // use this in delivery
    public Restaurant(
            DocumentReference category,
            Boolean delivery,
            String id,
            List<String> imageUri,
            GeoPoint location,
            String name,
            String openHours,
            List<DocumentReference> review,
            String telephone) {
        this.category = category;
        this.delivery = delivery;
        this.id = id;
        this.imageUri = imageUri;
        this.location = location;
        this.name = name;
        this.openHours = openHours;
        this.review = review;
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public String getRestaurantType() {
        return restaurantType;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public String getOpenHours() {
        return openHours;
    }

    public float getRating() {
        return rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public boolean isDeliverable() {
        return delivery;
    }

    public DocumentReference getCategory() { return category; }

    public boolean isDelivery() { return delivery; }

    public String getId() { return id; }

    public String getTelephone() { return telephone; }

    public List<String> getImageUri() { return imageUri; }

    public GeoPoint getLocation() { return location; }

    public List<DocumentReference> getReview() { return review; }

}
