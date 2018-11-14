package com.review.foodreview.dto;


import android.support.annotation.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.List;

public class Restaurant {
    private String id, name, priceRange, openHours, telephone, categoryName;
    private boolean delivery;
    private GeoPoint location;
    private List<String> imageUri;
    private List<DocumentReference> reviews;
    private HashMap<String, Long> rating;
    private DocumentReference category;

    private int reviewCount;

    public Restaurant(String id,
                      String name,
                      String priceRange,
                      String openHours,
                      String telephone,
                      String categoryName,
                      boolean deliverable,
                      @Nullable DocumentReference category,
                      @Nullable GeoPoint location,
                      @Nullable HashMap<String, Long> rating,
                      @Nullable List<String> imageUri,
                      @Nullable List<DocumentReference> reviews,
                      int reviewCount) {
        this.id = id;
        this.name = name;
        this.priceRange = priceRange;
        this.openHours = openHours;
        this.categoryName = categoryName;
        this.rating = rating;
        this.delivery = deliverable;
        this.telephone = telephone;
        this.imageUri = imageUri;
        this.category = category;
        this.location = location;
        this.reviews = reviews;
        this.reviewCount = reviewCount;
    }

    // use this in delivery
    /* public Restaurant(
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
    } */

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public String getOpenHours() {
        return openHours;
    }

    public String getTelephone() {
        return telephone;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public GeoPoint getLocation() {
        // TODO: Handle null imageUri
        return location;
    }

    public List<String> getImageUri() {
        // TODO: Handle null imageUri
        return imageUri;
    }

    public List<DocumentReference> getReviews() {
        // TODO: Handle null reviews
        return reviews;
    }

    public HashMap<String, Long> getRating() {
        // TODO: Handle null rating
        return rating;
    }

    public DocumentReference getCategory() {
        // TODO: Handle null category
        return category;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
