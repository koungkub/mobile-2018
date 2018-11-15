package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toolbar;

public class SearchResultsFragment extends Fragment {
    private static final String TAG = "SEARCHRESULTS";
    private String categoryId, categoryName;
    private Toolbar _toolbar;
    private ProgressBar _loading;

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

        // TODO: Pull search results
    }

    private void registerFragmentElements() {
        _toolbar = getView().findViewById(R.id.search_results_toolbar);
        _loading = getView().findViewById(R.id.search_results_loading);
    }

    private void createMenu() {
        _toolbar.setTitle(categoryName);
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
    }
}
