package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

public class ReviewsFragment extends Fragment {
    private static final String TAG = "REVIEWS";
    private String restaurantName, restaurantId;
    private Toolbar _toolbar;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_review, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        MainActivity.onFragmentChanged(TAG);
        bundle = getArguments();
        restaurantName = bundle.getString("restaurantName");
        restaurantId = bundle.getString("restaurantId");
        registerFragmentElements();
        createMenu();
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
    }
}
