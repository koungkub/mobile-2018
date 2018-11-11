package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.Toolbar;
import com.review.foodreview.dto.Restaurant;

public class RestaurantFragment extends Fragment {
    private static final String TAG = "RESTAURANT";

    private String restaurantName = "on da table";
    private String restaurantType = "Japanese fusion";
    private String priceRange = "$$ (121-300)";
    private String openHours = "09.00 - 22.00";
    private float rating = 4.3f;
    private int reviewCount = 221;
    private boolean delivery = false;

    private Restaurant restaurant;

    private TextView _restaurantName, _restaurantType, _priceRange, _rating, _reviewCount;
    private TextView _openHours, _delivery;
    private Toolbar _toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        restaurant = new Restaurant(restaurantName, restaurantType, priceRange, openHours, rating, reviewCount, delivery);
        return inflater.inflate(R.layout.restaurant, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        registerFragmentElements();
        createMenu();

        _restaurantName.setText(restaurant.getRestaurantName());
        _restaurantType.setText(restaurant.getRestaurantType());
        _priceRange.setText(restaurant.getPriceRange());
        _rating.setText(Float.toString(restaurant.getRating()));
        _reviewCount.setText("from " + restaurant.getReviewCount() + " reviews");
        _openHours.setText(restaurant.getOpenHours());
        if (!restaurant.isDeliverable()) _delivery.setText("Delivery not available");
    }

    private void registerFragmentElements() {
        Log.d(TAG, "registerFragmentElements");
        _restaurantName = getView().findViewById(R.id.restaurant_text_restaurant);
        _restaurantType = getView().findViewById(R.id.restaurant_text_type);
        _priceRange = getView().findViewById(R.id.restaurant_text_price);
        _rating = getView().findViewById(R.id.restaurant_text_review);
        _reviewCount = getView().findViewById(R.id.restaurant_text_reviewer);
        _openHours = getView().findViewById(R.id.restaurant_text_time);
        _delivery = getView().findViewById(R.id.restaurant_text_delivery);
        _toolbar = getView().findViewById(R.id.restaurant_action_bar);
    }

    private void createMenu() {
        _toolbar = getActivity().findViewById(R.id.restaurant_action_bar);
        _toolbar.setTitle(restaurant.getRestaurantName());
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        _toolbar.inflateMenu(R.menu.restaurant);
        getActivity().setActionBar(_toolbar);
    }
}
