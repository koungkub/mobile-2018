package com.review.foodreview.dto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.review.foodreview.R;
import com.review.foodreview.RestaurantFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class SlidingImageAdapter extends PagerAdapter {

    private static final String TAG = "SlidingImageAdapter";
    private ArrayList<ImageModel> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;
    private static FragmentManager fragmentManager;

    public SlidingImageAdapter(Context context, ArrayList<ImageModel> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup view, final int position) {
        FirebaseFirestore mdb = FirebaseFirestore.getInstance();
        final View imageLayout = inflater.inflate(R.layout.discover_slideshow,
                view,
                false);
        Log.d("ADAPTER", String.valueOf(position));
        assert imageLayout != null;
        mdb.collection("restaurant")
                .limit(3)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d(TAG, e.getLocalizedMessage());
                            displayDialog("Error", e.getLocalizedMessage());
                        } else {
                            final List<Restaurant> restaurants = new ArrayList<>();
                            final Bundle args = new Bundle();

                            Restaurant restaurant;

                            final Fragment restaurantFragment = new RestaurantFragment();

                            restaurants.clear();

                            for (QueryDocumentSnapshot doc : querySnapshot) {
                                restaurant = doc.toObject(Restaurant.class);
                                restaurant.setId(doc.getId());
                                restaurants.add(restaurant);
                            }

                            final TextView name = imageLayout
                                    .findViewById(R.id.discover_text_name_restaurant_on_slideshow);
                            final TextView category = imageLayout
                                    .findViewById(R.id.discover_text_category_restaurant_on_slideshow);
                            final ImageView imageView = imageLayout
                                    .findViewById(R.id.image);

                            Log.d(TAG, "restaurants.size = " + restaurants.size());

                            // in case of network failure and restaurants holds no object
                            if (restaurants.size() > 0) {
                                if (position == 0) {
                                    Log.d("SLIDE", "set slide 1");
                                    Log.d("DISCOVER", String.valueOf(fragmentManager));
                                    name.setText(restaurants.get(0).getName());
                                    category.setText(restaurants.get(0).getCategoryName());
                                    Picasso.get()
                                            .load(restaurants.get(0).getImageUri().get(0))
                                            .placeholder(R.drawable.slide1)
                                            .into(imageView);
                                    imageLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            args.putString("restaurantId", restaurants.get(0).getId());
                                            args.putString("restaurantName", restaurants.get(0).getName());
                                            restaurantFragment.setArguments(args);
                                            fragmentManager
                                                    .beginTransaction()
                                                    .replace(R.id.main_view, restaurantFragment)
                                                    .addToBackStack(null)
                                                    .commit();
                                        }
                                    });
                                }

                                if (restaurants.size() > 1) {
                                    if (position == 1) {
                                        Log.d("SLIDE", "set slide 2");
                                        name.setText(restaurants.get(1).getName());
                                        category.setText(restaurants.get(1).getCategoryName());
                                        Picasso.get()
                                                .load(restaurants.get(1).getImageUri().get(0))
                                                .placeholder(R.drawable.slide2)
                                                .into(imageView);
                                        imageLayout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                args.putString("restaurantId", restaurants.get(1).getId());
                                                args.putString("restaurantName", restaurants.get(1).getName());
                                                restaurantFragment.setArguments(args);
                                                fragmentManager
                                                        .beginTransaction()
                                                        .replace(R.id.main_view, restaurantFragment)
                                                        .addToBackStack(null)
                                                        .commit();
                                            }
                                        });
                                    }

                                    if (position == 2) {
                                        Log.d("SLIDE", "set slide 3");
                                        name.setText(restaurants.get(2).getName());
                                        category.setText(restaurants.get(2).getCategoryName());
                                        Picasso.get()
                                                .load(restaurants.get(2).getImageUri().get(0))
                                                .placeholder(R.drawable.slide3)
                                                .into(imageView);
                                        imageLayout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                args.putString("restaurantId", restaurants.get(2).getId());
                                                args.putString("restaurantName", restaurants.get(2).getName());
                                                restaurantFragment.setArguments(args);
                                                fragmentManager
                                                        .beginTransaction()
                                                        .replace(R.id.main_view, restaurantFragment)
                                                        .addToBackStack(null)
                                                        .commit();
                                            }
                                        });
                                    }
                                }
                            } else {
                                displayDialog("Error", "Cannot get results. Possibly because of network failure.");
                            }
                        }
                    }
                });
//        imageView.setImageResource(imageModelArrayList
//                .get(position)
//                .getImage_drawable());
        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public void setFragmentmanager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    private void displayDialog(String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .show();
    }
}