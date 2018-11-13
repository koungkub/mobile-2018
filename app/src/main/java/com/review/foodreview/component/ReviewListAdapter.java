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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.review.foodreview.R;
import com.review.foodreview.dto.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewListAdapter extends ArrayAdapter<Review> {
    private final String TAG = "REVIEWLISTADAPTER";
    private List<Review> reviewList;
    private Context context;

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
        final TextView _description = reviewItem.findViewById(R.id.all_review_item_text_text);
        final TextView _author = reviewItem.findViewById(R.id.all_review_item_text_reviewer);
        final Review review = reviewList.get(position);
        _description.setText(review.getDescription());
        // TODO: Get author name
        // TODO: Show rating
        return reviewItem;
    }
}
