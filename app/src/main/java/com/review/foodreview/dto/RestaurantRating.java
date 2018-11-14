package com.review.foodreview.dto;

/**
 * Star rating for restaurant reviews
 * @return RestaurantRating
 */
public class RestaurantRating {
    private final float food;
    private final float service;
    private final float atmosphere;

    public RestaurantRating(float food, float service, float atmosphere) {
        this.food = food;
        this.service = service;
        this.atmosphere = atmosphere;
    }

    /**
     * Get the rating of food
     * @return float
     */
    public float getFood() {
        return food;
    }

    /**
     * Get the rating of service
     * @return float
     */
    public float getService() {
        return service;
    }

    /**
     * Get the rating of atmosphere
     * @return float
     */
    public float getAtmosphere() {
        return atmosphere;
    }
}
