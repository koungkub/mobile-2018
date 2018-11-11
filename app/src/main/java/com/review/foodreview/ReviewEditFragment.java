package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.Toolbar;

public class ReviewEditFragment extends Fragment {

    private static final String TAG = "REVIEWEDIT";
    private Toolbar toolbar;

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
        toolbar = getActivity().findViewById(R.id.review_edit_toolbar);
        toolbar.setTitle("Write a review");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.inflateMenu(R.menu.review_edit);
        getActivity().setActionBar(toolbar);
    }
}
