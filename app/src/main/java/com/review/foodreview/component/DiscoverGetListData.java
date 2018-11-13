package com.review.foodreview.component;

import android.util.Log;

import com.review.foodreview.dto.Category;
import com.review.foodreview.dto.GetallFirestore;
import com.review.foodreview.dto.Restaurant;
import com.review.foodreview.dto.Review;
import com.review.foodreview.dto.User;

import java.util.ArrayList;
import java.util.List;


public class DiscoverGetListData extends GetallFirestore {
    private List<Restaurant> restaurant;
    private List<Review> review;
    private List<Category> categories;
    private List<User> users;

    public DiscoverGetListData(boolean wantRestaurant, boolean wantReview, boolean wantCategory, boolean wantUser){
        super(wantRestaurant, wantReview, wantCategory, wantUser);
        restaurant = new ArrayList<>();
        review = new ArrayList<>();
        categories = new ArrayList<>();
        users = new ArrayList<>();
        restaurantList();
    }

    @Override
    public void doActivity(List<Restaurant> restaurant, List<Review> review, List<Category> categories, List<User> users) {
        Log.d("LISTDATA", "Restaurant = " + restaurant);
        Log.d("LISTDATA", "Review = " + review);
        Log.d("LISTDATA", "Category = " + categories);
        Log.d("LISTDATA", "User = " + users);
        Log.d("LISTDATA", "This is a Example");
    }

}
