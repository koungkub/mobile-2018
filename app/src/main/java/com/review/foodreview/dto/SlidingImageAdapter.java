package com.review.foodreview.dto;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
        mdb.collection("restaurant").limit(3).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                final List<Restaurant> restaurants = new ArrayList<>();
                final Bundle args = new Bundle();
                Restaurant restaurant;
                final Fragment restaurantFragment = new RestaurantFragment();
                for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    restaurant = doc.toObject(Restaurant.class);
                    restaurant.setId(doc.getId());
                    restaurants.add(restaurant);
                }
                final TextView name = imageLayout
                        .findViewById(R.id.discover_text_name_restaurant_on_slideshow);

                final ImageView imageView = imageLayout
                        .findViewById(R.id.image);
                if(position == 0) {
                    Log.d("DISCOVER", String.valueOf(fragmentManager));
                    name.setText(restaurants.get(0).getName());
                    Picasso.get()
                            .load(restaurants.get(0).getImageUri().get(0))
                            .placeholder(R.drawable.slide1)
                            .into(imageView);
                    imageLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            args.putString("restaurantId", restaurants.get(0).getId());
                            restaurantFragment.setArguments(args);
                            fragmentManager.beginTransaction().replace(R.id.main_view, restaurantFragment).addToBackStack(null).commit();
                        }
                    });
                }
                if (restaurants.size() > 1) {
                    if (position == 1) {
                        name.setText(restaurants.get(1).getName());
                        Picasso.get()
                                .load(restaurants.get(1).getImageUri().get(0))
                                .placeholder(R.drawable.slide2)
                                .into(imageView);
                        imageLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                args.putString("restaurantId", restaurants.get(1).getId());
                                restaurantFragment.setArguments(args);
                                fragmentManager.beginTransaction().replace(R.id.main_view, restaurantFragment).addToBackStack(null).commit();
                            }
                        });
                    } else {
                        name.setText(restaurants.get(2).getName());
                        Picasso.get()
                                .load(restaurants.get(2).getImageUri().get(0))
                                .placeholder(R.drawable.slide3)
                                .into(imageView);
                        imageLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                args.putString("restaurantId", restaurants.get(2).getId());
                                restaurantFragment.setArguments(args);
                                fragmentManager.beginTransaction().replace(R.id.main_view, restaurantFragment).addToBackStack(null).commit();
                            }
                        });
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
    public void setFragmentmanager(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

}