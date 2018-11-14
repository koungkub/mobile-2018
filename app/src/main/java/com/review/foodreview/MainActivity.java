package com.review.foodreview;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.review.foodreview.sqlite.DBHelper;
import android.view.View;

public class MainActivity extends AppCompatActivity{
    private static BottomNavigationView navigationView;
    private DBHelper dbHelper;
    private Fragment fragment;
    private Fragment fragmentDiscover;
    private Fragment fragmentSearch;
    private Fragment fragmentMe;

    private static final String TAG = "MAINACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(savedInstanceState);
        setupNavbar();
        fragmentDiscover = new DiscoverFragment();
        fragmentSearch = new SearchFragment();
        fragmentMe = new LoginFragment();

//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageReference = storage.getReference();
//
//        StorageReference restaurant = storageReference.child("restaurant");
//
//        StorageReference koung = restaurant.child("cat.jpg");
//
//        Log.d("PHOTO", koung.getName());
//        Log.d("PHOTO", koung.getPath());
//        Log.d("PHOTO", koung.getBucket());
//        final long ONE_MEGABYTE = 1024 * 1024;
//
//        koung.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Log.d("PHOTO", "success");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("PHOTO", "failure");
//                e.printStackTrace();
//            }
//        });

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
        Log.d(TAG, "Do setupNavbar");
        navigationView = findViewById(R.id.Navbottom);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Log.d(TAG, String.valueOf(menuItem.getItemId()));
                switch (menuItem.getItemId()) {
                    case R.id.navigation_discover: {
                        fragment = fragmentDiscover;
                        break;
                    }
                    case R.id.navigation_search: {
                        fragment = fragmentSearch;
                        break;
                    }
                    case R.id.navigation_me: {
                        fragment = fragmentMe;
                        break;
                    }
                }
                if (fragment != null) {
                    Log.d(TAG, "Change page");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_view, fragment).commit();
                }
                return true;
            }
        });
    }

    public static void onFragmentChanged(String fragmentName) {
        Log.d(TAG, "onFragmentChanged: " + fragmentName);
        if (fragmentName.equalsIgnoreCase("RESTAURANT")) {
            Log.d(TAG, "Invisible nav");
            navigationView.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "Visible nav");
            navigationView.setVisibility(View.VISIBLE);
        }
    }
}