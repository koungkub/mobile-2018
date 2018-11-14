package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;
import com.review.foodreview.component.ReviewListAdapter;
import com.review.foodreview.component.ReviewListItem;
import com.review.foodreview.dto.Restaurant;
import com.review.foodreview.dto.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RestaurantFragment extends Fragment {
    private static final String TAG = "RESTAURANT";

    private String restaurantId; // to be assigned with bundle
    private Restaurant restaurant;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private TextView _restaurantName, _restaurantType, _priceRange, _rating, _reviewCount;
    private TextView _openHours, _delivery;
    private Toolbar _toolbar;
    private Button _writeBtn, _viewAllBtn;
    private LinearLayout _reviewList;

    private List<Review> reviewList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        MainActivity.onFragmentChanged(TAG);
        return inflater.inflate(R.layout.restaurant, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        restaurantId = "PxZsYjM909P3IfsvJdPb"; // TODO: Replace with bundle
        reviewList.clear();
        registerFragmentElements();

        Log.d(TAG, "fetching restaurant");
        final DocumentReference restaurantRef = firestore.collection("restaurant").document(restaurantId);
        restaurantRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot,
                                        @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot.exists()) {
                            Log.d(TAG, documentSnapshot.get("name").toString());
                            restaurant = new Restaurant(
                                    documentSnapshot.getId(),
                                    documentSnapshot.getString("name"),
                                    // documentSnapshot.getString("category"),
                                    "Japanese fusion",
                                    documentSnapshot.getString("priceRange"),
                                    documentSnapshot.getString("openHours"),
                                    4.5f,
                                    20,
                                    documentSnapshot.getBoolean("delivery")
                            );
                        } else {
                            Log.d(TAG, "Object doesn't exist");
                            restaurant = new Restaurant(
                                    "no id",
                                    "no name",
                                    "no category",
                                    "no price",
                                    "never opens",
                                    0f,
                                    0,
                                    false
                            );
                        }
                        // methods that require data from Firestore
                        createMenu();
                        setTexts(restaurant);
                        initViewAllBtn();
                    }
                });

        Log.d(TAG, "fetching reviews");
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
                            for (Review review : reviewList) {
                                final ReviewListItem reviewListItem = new ReviewListItem(getContext(), review, _reviewList);
                                final View restaurantListItemView = reviewListItem.getComponent();
                                _reviewList.addView(restaurantListItemView);
                            }
                        } else {
                            // TODO: Handle unsuccessful task
                        }
                        // hide progress bar and make content visible
                        _reviewList.setVisibility(View.VISIBLE);
                        // _loading.setVisibility(View.GONE);
                    }
                });
        initWriteBtn();
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
        _writeBtn = getView().findViewById(R.id.restaurant_review_btn_add);
        _viewAllBtn = getView().findViewById(R.id.restaurant_review_btn_all);
        _reviewList = getView().findViewById(R.id.restaurant_recent_reviews);
    }

    private void createMenu() {
        _toolbar.setTitle(restaurant.getName());
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        _toolbar.inflateMenu(R.menu.restaurant);
        getActivity().setActionBar(_toolbar);
    }

    private void setTexts(Restaurant restaurant) {
        _restaurantName.setText(restaurant.getName());
        _restaurantType.setText(restaurant.getRestaurantType());
        _priceRange.setText(restaurant.getPriceRange());
        _rating.setText(String.format(Locale.ENGLISH, "%.1f", restaurant.getRating()));
        _reviewCount.setText(
                String.format(
                        Locale.ENGLISH,
                        getString(R.string.page_restaurant_from_d_reviews),
                        restaurant.getReviewCount())
        );
        _openHours.setText(restaurant.getOpenHours());
        if (!restaurant.isDeliverable()) _delivery.setText(R.string.page_restaurant_no_delivery);
    }

    private void initWriteBtn() {
        _writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, new ReviewEditFragment())
                        .commit();
            }
        });
    }

    private void initViewAllBtn() {
        final Bundle bundle = new Bundle();
        bundle.putString("restaurantName", restaurant.getName());
        bundle.putString("restaurantId", restaurant.getId());
        _viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ReviewsFragment();
                fragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, fragment)
                        .commit();
            }
        });
    }
}
