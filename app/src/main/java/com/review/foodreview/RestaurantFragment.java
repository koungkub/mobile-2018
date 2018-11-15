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
import com.google.firebase.firestore.EventListener;
import com.review.foodreview.component.ReviewListItem;
import com.review.foodreview.dto.Restaurant;
import com.review.foodreview.dto.Review;

import java.util.*;

public class RestaurantFragment extends Fragment {
    private static final String TAG = "RESTAURANT";

    private String restaurantId, restaurantName;
    private Restaurant restaurant;
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private TextView _restaurantName, _restaurantType, _priceRange, _rating, _reviewCount;
    private TextView _openHours, _delivery;
    private Toolbar _toolbar;
    private Button _writeBtn, _viewAllBtn;
    private LinearLayout _reviewList;
    private ProgressBar _reviewLoading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        MainActivity.onFragmentChanged(TAG);
        return inflater.inflate(R.layout.restaurant, container, false);
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        // get bundle
        Bundle bundle = getArguments();

        if (bundle.getString("restaurantId") == null)
            Log.d(TAG, "onActivityCreated: restaurantId not found in the bundle");
        restaurantId = bundle.getString("restaurantId");

        restaurantName = bundle.getString("restaurantName");

        registerFragmentElements();

        if (restaurantName != null) _toolbar.setTitle(restaurantName);

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
                                    documentSnapshot.getString("priceRange"),
                                    documentSnapshot.getString("openHours"),
                                    documentSnapshot.getString("telephone"),
                                    documentSnapshot.getString("categoryName"),
                                    documentSnapshot.getBoolean("delivery"),
                                    documentSnapshot.getDocumentReference("category"),
                                    documentSnapshot.getGeoPoint("location"),
                                    (HashMap<String, Long>) documentSnapshot.get("rating"),
                                    (List<String>) documentSnapshot.get("imageUri"),
                                    (List<DocumentReference>) documentSnapshot.get("review"),
                                    0
                            );
                        } else {
                            Log.d(TAG, "Restaurant doesn't exist");
                            restaurant = new Restaurant(
                                    "no id",
                                    "no name",
                                    "no price",
                                    "never opens",
                                    "no phone",
                                    "no category",
                                    false,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    0
                            );
                        }
                        // methods that require restaurant data from Firestore
                        createMenu();
                        setTexts(restaurant);
                        initViewAllBtn();
                    }
                });

        Log.d(TAG, "fetching reviews");
        firestore.collection("review")
                .whereEqualTo("restaurant", restaurantRef)
                .limit(5)
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
                                // add to _reviewList LinearLayout
                                if (getContext() != null) {
                                    final ReviewListItem reviewListItem = new ReviewListItem(getContext(), review, _reviewList);
                                    final View restaurantListItemView = reviewListItem.getComponent();
                                    _reviewList.addView(restaurantListItemView);
                                }
                            }
                        } else {
                            // TODO: Handle unsuccessful task
                        }
                        // hide progress bar and make content visible
                        _reviewLoading.setVisibility(View.GONE);
                        _reviewList.setVisibility(View.VISIBLE);
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
        _reviewLoading = getView().findViewById(R.id.restaurant_loading_reviews);
    }

    private void createMenu() {
        _toolbar.setTitle(restaurant.getName());
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        _toolbar.inflateMenu(R.menu.restaurant);
        Objects.requireNonNull(getActivity()).setActionBar(_toolbar);
    }

    private void setTexts(Restaurant restaurant) {
        _restaurantName.setText(restaurant.getName());
        _restaurantType.setText(restaurant.getCategoryName());
        _priceRange.setText(restaurant.getPriceRange());
        // TODO: Calculate restaurant rating
        // _rating.setText(String.format(Locale.ENGLISH, "%.1f", restaurant.getRating().get("food")));
        _reviewCount.setText(
                String.format(
                        Locale.ENGLISH,
                        getString(R.string.page_restaurant_from_d_reviews),
                        restaurant.getReviewCount())
        );
        _openHours.setText(restaurant.getOpenHours());
        if (!restaurant.isDelivery()) _delivery.setText(R.string.page_restaurant_no_delivery);
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
