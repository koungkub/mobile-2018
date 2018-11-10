package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import com.review.foodreview.dto.LogDTO;
import com.review.foodreview.sqlite.DBHelper;
import java.util.List;
import android.view.ViewGroup;

public class SearchFragment extends Fragment  {
    private static final String LOG = "SEARCHFRAGMENT";
    private BottomNavigationView navigationView;

    public SearchFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(LOG, "Start SearchFragment");
        return inflater.inflate(R.layout.search,
                container,
                false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
