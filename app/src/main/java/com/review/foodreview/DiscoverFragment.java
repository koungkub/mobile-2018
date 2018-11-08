package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DiscoverFragment extends Fragment {
    private ArrayList<Restaurant> restaurants = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.discover, container, false);
    }

    @Override
    public void onActivityCreated(
            @Nullable Bundle savedInstanceState
    ) {
        super.onActivityCreated(savedInstanceState);
        restaurants.clear();

        Restaurant mcdonalds = new Restaurant(
                "McDonald's",
                "Fast food",
                "$ (< 120)",
                "24 hours",
                4.2f,
                318,
                true
        );
        Restaurant otoya = new Restaurant(
                "Otoya",
                "Japanese",
                "$$ (120 - 300)",
                "10.00 - 21.00",
                4.6f,
                282,
                true
        );
        restaurants.add(mcdonalds);
        restaurants.add(otoya);

        final LinearLayout _restaurantList = getView().findViewById(R.id.discover_list);
        for (Restaurant r : restaurants) {
            final View restaurantListItem = LayoutInflater.from(getContext()).inflate(R.layout.restaurant_list_item, _restaurantList, false);
            final TextView _restaurantName = restaurantListItem.findViewById(R.id.restaurant_list_item_text_name);
            final TextView _restaurantType = restaurantListItem.findViewById(R.id.restaurant_list_item_text_type);
            final TextView _priceRange = restaurantListItem.findViewById(R.id.restaurant_list_item_text_price);
            final TextView _rating = restaurantListItem.findViewById(R.id.restaurant_list_item_text_score);
            _restaurantName.setText(r.getRestaurantName());
            _restaurantType.setText(r.getRestaurantType());
            _priceRange.setText(r.getPriceRange());
            _rating.setText(Float.toString(r.getRating()));
            _restaurantList.addView(restaurantListItem);
        }
    }
}
