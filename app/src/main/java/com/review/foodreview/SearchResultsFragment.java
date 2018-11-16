package com.review.foodreview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.Objects;

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

        restaurantList.clear();
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
                                displayDialog("No result", "There is no restaurant in this category.");
                            } else {
                                populateListView(task.getResult(), restaurantList, _resultsListView);
                            }
                        } else {
                            displayDialog("Error", task.getException().getLocalizedMessage());
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
        Objects.requireNonNull(getActivity()).setActionBar(_toolbar);
        _toolbar.setTitle(categoryName);
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        _toolbar.setNavigationContentDescription("Back");
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    private void populateListView(
            QuerySnapshot results,
            final List<Restaurant> listForAdapter,
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
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d(TAG, "onItemClick: " + position);
                        handleListItemClick(listForAdapter, position);
                    }
                });
            }
        }
    }

    private void displayDialog(String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog_Alert);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .show();
    }

    private void handleListItemClick(List<Restaurant> restaurantList, int position) {
        final Restaurant restaurant = restaurantList.get(position);
        Log.d(TAG, "clicked on: " + position + ", " + restaurant.getName());
        final Fragment fragment = new RestaurantFragment();
        final Bundle bundle = new Bundle();
        bundle.putString("restaurantName", restaurant.getName());
        bundle.putString("restaurantId", restaurant.getId());
        fragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_view, fragment)
                .commit();
    }
}
