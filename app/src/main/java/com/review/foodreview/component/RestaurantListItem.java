package com.review.foodreview.component;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.review.foodreview.R;
import com.review.foodreview.dto.Restaurant;
import com.squareup.picasso.Picasso;

public class RestaurantListItem {
    private static final String TAG = "RESTAURANTLISTITEM";
    private final Restaurant restaurant;
    private final Context context;
    private final LinearLayout _restaurantList;

    /**
     * Create a single restaurant list item.
     * Call {@link #getComponent()} to return the View.
     */
    public RestaurantListItem(Context context, Restaurant restaurant, LinearLayout restaurantList) {
        Log.d(TAG, "RestaurantListItem: New RestaurantListItem");
        this.context = context;
        this.restaurant = restaurant;
        this._restaurantList = restaurantList;
    }

    /**
     * Get a View of {@link #RestaurantListItem}.
     * @return A single restaurant list item (View)
     */
    public View getComponent() {
        Log.d(TAG, "RestaurantListItem: getComponent");
        final View restaurantListItem = LayoutInflater.from(context).inflate(R.layout.restaurant_list_item, _restaurantList, false);
        final TextView _restaurantName = restaurantListItem.findViewById(R.id.restaurant_list_item_text_name);
        final TextView _restaurantType = restaurantListItem.findViewById(R.id.restaurant_list_item_text_type);
        final TextView _priceRange = restaurantListItem.findViewById(R.id.restaurant_list_item_text_price);
        final TextView _rating = restaurantListItem.findViewById(R.id.restaurant_list_item_text_score);
        final ImageView _imageUri = restaurantListItem.findViewById(R.id.restaurant_list_item_image);

        Picasso.get()
                .load(restaurant.getImageUri().get(0))
                .into(_imageUri);
        _restaurantName.setText(restaurant.getName());
        _restaurantType.setText(restaurant.getCategoryName());
        _priceRange.setText(restaurant.getPriceRange());
        // TODO: Display restaurant rating
        // _rating.setText(Float.toString(restaurant.getRating()));
        return restaurantListItem;
    }
}
