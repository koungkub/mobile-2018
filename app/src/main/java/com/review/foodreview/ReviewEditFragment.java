package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Toolbar;

public class ReviewEditFragment extends Fragment {

    private static final String TAG = "REVIEWEDIT";
    private Toolbar _toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.review_edit, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createMenu();
    }

    private void createMenu() {
        _toolbar = getActivity().findViewById(R.id.review_edit_toolbar);
        _toolbar.setTitle("Write a review");
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        _toolbar.inflateMenu(R.menu.review_edit);
        getActivity().setActionBar(_toolbar);
    }
}
