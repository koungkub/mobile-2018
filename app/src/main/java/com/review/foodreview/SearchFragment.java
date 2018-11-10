package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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
        /*
        setupnavbar
//         */
//        setupNavbar();

    }

//    private void setupNavbar() {
//        Log.d(LOG, "Do setupNavbar");
//        navigationView = getView().findViewById(R.id.Navbottom);
//        navigationView.setActivated(true);
//        navigationView
//                .setOnNavigationItemSelectedListener(new BottomNavigationView
//                        .OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                Fragment fragment = null;
//                switch (menuItem.getItemId()) {
//                    case R.id.navigation_discover: {
//                        fragment = new DiscoverFragment();
//                        break;
//                    }
//
//                }
//                if(fragment != null){
//                    Log.d(LOG, "Go from" + LOG);
//                    getActivity()
//                            .getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.main_view, fragment)
//                            .commit();
//                }
//
//                return true;
//            }
//
//        });
//    }
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        return false;
//    }
}
