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

public class RestaurantFragment extends Fragment {
    private static final String TAG = "RESTAURANT";
    private String restaurantName = "on da table";
    private String restaurantType = "Japanese fusion";
    private String priceRange = "$$ (121-300)";
    private String openHours = "09.00 - 22.00";
    private float rating = 4.3f;
    private int reviewCount = 221;
    private boolean delivery = false;

    private TextView _restaurantName, _restaurantType, _priceRange, _rating, _reviewCount;
    private TextView _openHours, _delivery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.restaurant, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        registerFragmentElements();

        _restaurantName.setText(restaurantName);
        _restaurantType.setText(restaurantType);
        _priceRange.setText(priceRange);
        _rating.setText(Float.toString(rating));
        _reviewCount.setText("from " + reviewCount + " reviews");
        _openHours.setText(openHours);
        if (!delivery) _delivery.setText("Delivery not available");
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
    }
}
