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
import com.review.foodreview.component.RestaurantListAdapter;
import com.review.foodreview.dto.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchResultsFragment extends Fragment {
    private static final String TAG = "SEARCHRESULTS";
    private String categoryId, categoryName;
    private final List<Restaurant> restaurantList = new ArrayList<>();

    private Toolbar _toolbar;
    private ProgressBar _loading;
    private ListView _resultsListView;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_results, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: " + categoryId);
        MainActivity.onFragmentChanged(TAG);
        categoryId = getArguments().getString("categoryId");
        categoryName = getArguments().getString("categoryName");

        registerFragmentElements();
        createMenu();

        DocumentReference categoryRef = firestore.collection("category").document(categoryId);
        firestore.collection("restaurant")
                .whereEqualTo("category", categoryRef)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "successfully retrieved search results");
                            if (task.getResult().size() < 1) {
                                // todo: show dialog
                            } else {
                                populateListView(task.getResult(), restaurantList, _resultsListView);
                            }
                        } else {
                            // todo: show dialog
                        }
                        _loading.setVisibility(View.GONE);
                    }
                });
    }

    private void registerFragmentElements() {
        _toolbar = getView().findViewById(R.id.search_results_toolbar);
        _loading = getView().findViewById(R.id.search_results_loading);
        _resultsListView = getView().findViewById(R.id.search_results_list);
    }

    private void createMenu() {
        _toolbar.setTitle(categoryName);
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
    }

    private void populateListView(
            QuerySnapshot results,
            List<Restaurant> listForAdapter,
            ListView listView) {

        for (QueryDocumentSnapshot result : results) {
            Restaurant restaurant = new Restaurant(
                    result.getId(),
                    result.getString("name"),
                    result.getString("priceRange"),
                    result.getString("openHours"),
                    result.getString("telephone"),
                    result.getString("categoryName"),
                    result.getBoolean("delivery"),
                    result.getDocumentReference("category"),
                    result.getGeoPoint("location"),
                    (HashMap<String, Long>) result.get("rating"),
                    (List<String>) result.get("imageUri"),
                    (List<DocumentReference>) result.get("review"),
                    0
            );
            listForAdapter.add(restaurant);
            if (getContext() != null) {
                RestaurantListAdapter restaurantListAdapter = new RestaurantListAdapter(
                        getActivity(),
                        R.layout.restaurant_list_item,
                        listForAdapter
                );
                listView.setAdapter(restaurantListAdapter);
            }
        }
    }
}
