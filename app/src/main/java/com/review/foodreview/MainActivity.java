package com.review.foodreview;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.review.foodreview.sqlite.DBHelper;
import android.view.View;
import com.review.foodreview.sqlite.DBHelper;
import com.review.foodreview.dto.LogDTO;
import com.review.foodreview.sqlite.DBHelper;

public class MainActivity extends AppCompatActivity{

    private static BottomNavigationView navigationView;
    private DBHelper dbHelper;
    Fragment fragment;
    private static final String LOG = "MAINACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(savedInstanceState);
        setupNavbar();
    }

    public void init(Bundle bundle) {
        if (bundle == null) {
            Fragment fragment = new DiscoverFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, fragment)
                    .commit();
        }
    }

    private void setupNavbar() {
        Log.d(LOG, "Do setupNavbar");
        navigationView = findViewById(R.id.Navbottom);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_discover: {
                        fragment = new DiscoverFragment();
                        break;
                    }
                    case R.id.navigation_search: {
                        fragment = new SearchFragment();
                        break;
                    }

                }
                if (fragment != null) {
                    Log.d(LOG, "Change page");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_view, fragment).commit();
                }
                return true;
            }
        });
    }

    public static void onFragmentChanged(String fragmentName) {
        Log.d(LOG, "onFragmentChanged: " + fragmentName);
        if (fragmentName.equalsIgnoreCase("RESTAURANT")) {
            Log.d(LOG, "Invisible");
            navigationView.setVisibility(View.GONE);
        } else {
            navigationView.setVisibility(View.VISIBLE);
        }
    }
}
