package com.review.foodreview.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.review.foodreview.R;
import com.review.foodreview.dto.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListAdapter extends ArrayAdapter<Restaurant> {

    private final String TAG = "RESTAURANTLISTADAPTER";
    private List<Restaurant> restaurantList = new ArrayList<>();
    private final Context context;

    public RestaurantListAdapter(@NonNull Context context,
                                 int resource,
                                 @NonNull List<Restaurant> restaurants) {
        super(context, resource, restaurants);
        Log.d(TAG, "RestaurantListAdapter: New RestaurantListAdapter");
        this.restaurantList = restaurants;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "RestaurantListAdapter: getView");
        final View restaurantListItem = LayoutInflater.from(context).inflate(R.layout.restaurant_list_item, parent, false);
        final TextView _restaurantName = restaurantListItem.findViewById(R.id.restaurant_list_item_text_name);
        final TextView _restaurantType = restaurantListItem.findViewById(R.id.restaurant_list_item_text_type);
        final TextView _priceRange = restaurantListItem.findViewById(R.id.restaurant_list_item_text_price);
        // final TextView _rating = restaurantListItem.findViewById(R.id.restaurant_list_item_text_score);
        final ImageView _thumbnail = restaurantListItem.findViewById(R.id.restaurant_list_item_image);

        final Restaurant restaurant = restaurantList.get(position);
        _restaurantName.setText(restaurant.getName());
        _restaurantType.setText(restaurant.getCategoryName());
        _priceRange.setText(restaurant.getPriceRange());
        Picasso.get()
                .load(restaurant.getImageUri().get(0))
                .into(_thumbnail);
        return restaurantListItem;
    }
}
