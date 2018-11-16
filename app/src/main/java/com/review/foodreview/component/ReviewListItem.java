package com.review.foodreview.component;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.review.foodreview.R;
import com.review.foodreview.dto.Review;

import java.util.Locale;

public class ReviewListItem {
    private static final String TAG = "REVIEWLISTITEM";
    private final Review review;
    private final Context context;
    private final LinearLayout _reviewList;

    /**
     * Create a single review list item.
     * Call {@link #getComponent()} to return the View.
     */
    public ReviewListItem(Context context, Review review, LinearLayout reviewList) {
        Log.d(TAG, "ReviewListItem: New ReviewListItem");
        this.context = context;
        this.review = review;
        this._reviewList = reviewList;
    }

    /**
     * Get a View of {@link #ReviewListItem}.
     * @return A single review list item (View)
     */
    public View getComponent() {
        Log.d(TAG, "ReviewListItem: getComponent");
        final View reviewListItem = LayoutInflater.from(context).inflate(R.layout.review_item, _reviewList, false);
        final TextView _description = reviewListItem.findViewById(R.id.review_item_text_description);
        final TextView _author = reviewListItem.findViewById(R.id.review_item_text_author);
        final TextView _ratingFood = reviewListItem.findViewById(R.id.review_item_rating_food);
        final TextView _ratingService = reviewListItem.findViewById(R.id.review_item_rating_service);
        final TextView _ratingAtmosphere = reviewListItem.findViewById(R.id.review_item_rating_atmosphere);
        _description.setText(review.getDescription());
        _ratingFood.setText(String.valueOf(review.getRating().get("food")));
        _ratingService.setText(String.valueOf(review.getRating().get("service")));
        _ratingAtmosphere.setText(String.valueOf(review.getRating().get("atmosphere")));
        // TODO: Get author name
        return reviewListItem;
    }
}
