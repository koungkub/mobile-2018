package com.review.foodreview.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.review.foodreview.R;
import com.review.foodreview.dto.Review;

import java.util.List;

public class ReviewListAdapter extends ArrayAdapter<Review> {
    private final String TAG = "REVIEWLISTADAPTER";
    private final List<Review> reviewList;
    private final Context context;

    public ReviewListAdapter(@NonNull Context context, int resource, @NonNull List<Review> reviews) {
        super(context, resource, reviews);
        Log.d(TAG, "ReviewListAdapter: New ReviewListAdapter");
        this.reviewList = reviews;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View reviewItem = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        final TextView _description = reviewItem.findViewById(R.id.review_item_text_description);
        final TextView _author = reviewItem.findViewById(R.id.review_item_text_author);
        final TextView _ratingFood = reviewItem.findViewById(R.id.review_item_rating_food);
        final TextView _ratingService = reviewItem.findViewById(R.id.review_item_rating_service);
        final TextView _ratingAtmosphere = reviewItem.findViewById(R.id.review_item_rating_atmosphere);
        final Review review = reviewList.get(position);
        _description.setText(review.getDescription());
        _ratingFood.setText(review.getRating().get("food").toString());
        _ratingService.setText(review.getRating().get("service").toString());
        _ratingAtmosphere.setText(review.getRating().get("atmosphere").toString());
        // TODO: Get author name
        return reviewItem;
    }
}