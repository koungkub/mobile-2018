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
import com.review.foodreview.component.RestaurantListItem;
import com.review.foodreview.slideshow.SlideShowManage;

import java.util.ArrayList;

public class DiscoverFragment extends Fragment{
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
            final RestaurantListItem restaurantListItem = new RestaurantListItem(getContext(), r, _restaurantList);
            final View viewItem = restaurantListItem.getComponent();
            _restaurantList.addView(viewItem);
        }

        SlideShowManage slideShow = new SlideShowManage();
    }
}
