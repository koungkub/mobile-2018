package com.review.foodreview;

public class Restaurant {
    private String restaurantName, restaurantType, priceRange, openHours;
    private float rating;
    private int reviewCount;
    private boolean delivery;

    public Restaurant(String restaurantName,
                      String restaurantType,
                      String priceRange,
                      String openHours,
                      float rating,
                      int reviewCount,
                      boolean delivery) {
        this.restaurantName = restaurantName;
        this.restaurantType = restaurantType;
        this.priceRange = priceRange;
        this.openHours = openHours;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.delivery = delivery;
    }

    public String getRestaurantName() {
        return restaurantName;
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

    public boolean isDelivery() {
        return delivery;
    }
}
