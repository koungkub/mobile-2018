package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.review.foodreview.component.ReviewListAdapter;
import com.review.foodreview.dto.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReviewsFragment extends Fragment {
    private static final String TAG = "REVIEWS";
    private String restaurantName, restaurantId;
    private final List<Review> reviewList = new ArrayList<>();
    private Toolbar _toolbar;
    private ListView _reviewListView;
    private ProgressBar _loading;
    private Bundle bundle;
    private FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.restaurant_reviews, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        MainActivity.onFragmentChanged(TAG);

        reviewList.clear();
        firestore = FirebaseFirestore.getInstance();

        bundle = getArguments();
        restaurantName = bundle.getString("restaurantName");
        restaurantId = bundle.getString("restaurantId");
        registerFragmentElements();
        createMenu();

        _reviewListView.setVisibility(View.INVISIBLE);

        final DocumentReference restaurantRef = firestore.collection("restaurant").document(restaurantId);
        firestore.collection("review")
                .whereEqualTo("restaurant", restaurantRef)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Successfully retrieved reviews");
                            for (QueryDocumentSnapshot reviewSnapshot : task.getResult()) {
                                Review review = new Review(
                                        reviewSnapshot.getId(),
                                        reviewSnapshot.getDocumentReference("author"),
                                        reviewSnapshot.getDocumentReference("restaurant"),
                                        reviewSnapshot.getString("description"),
                                        reviewSnapshot.getTimestamp("date"),
                                        (ArrayList<String>) reviewSnapshot.get("imageUri"),
                                        (HashMap<String, Long>) reviewSnapshot.get("rating")
                                );
                                reviewList.add(review);
                            }
                            if (getContext() != null) {
                                ReviewListAdapter reviewListAdapter = new ReviewListAdapter(getActivity(), R.layout.review_item, reviewList);
                                _reviewListView.setAdapter(reviewListAdapter);
                            }
                        } else {
                            // TODO: Handle unsuccessful task
                        }
                        // hide progress bar and make content visible
                        _reviewListView.setVisibility(View.VISIBLE);
                        _loading.setVisibility(View.GONE);
                    }
                });
    }

    private void createMenu() {
        Log.d(TAG, "createMenu");
        _toolbar = getActivity().findViewById(R.id.reviews_action_bar);
        _toolbar.setTitle(restaurantName);
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getActivity().setActionBar(_toolbar);
    }

    private void registerFragmentElements() {
        Log.d(TAG, "registerFragmentElements");
        _toolbar = getView().findViewById(R.id.reviews_action_bar);
        _reviewListView = getView().findViewById(R.id.reviews_list);
        _loading = getView().findViewById(R.id.reviews_loading);
    }
}
