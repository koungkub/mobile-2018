package com.review.foodreview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.review.foodreview.component.RestaurantListItem;
import com.review.foodreview.dto.Restaurant;
import com.review.foodreview.dto.ImageModel;
import com.review.foodreview.dto.SlidingImageAdapter;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment{
    private final List<Restaurant> restaurants = new ArrayList<>();
    private static final String TAG = "DISCOVER";
    private static ViewPager mPager;
    private Fragment fragmentrestaurant;
    private Bundle args;
    private FirebaseFirestore mdb;
    private WormDotsIndicator wormDotsIndicator;
    private static final int NUM_PAGES = 3;
    private ArrayList<ImageModel> imageModelArrayList;
    private final int[] myImageList = new int[] {
            R.drawable.slide1,
            R.drawable.slide2,
            R.drawable.slide3
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Start discover fragment (Create)");
        mdb = FirebaseFirestore.getInstance();
        args = new Bundle();
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        fragmentrestaurant = new RestaurantFragment();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Start discover fragment (CreateView)");
        return inflater.inflate(R.layout.discover, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.onFragmentChanged(TAG);
        Log.d(TAG, "Start discover fragment (ActivityCreated)");
        // set up Featured slideshow
        Log.d(TAG, "Do setupSlideshow");
        setupSlideshow();
        // get Discover List
        getdiscoverList();
    }


    //get image
    private ArrayList<ImageModel> populateList() {

        ArrayList<ImageModel> list = new ArrayList<>();

        for (int i = 0; i < NUM_PAGES; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }

    //setup slideshow here
    private void setupSlideshow() {
        mPager = getView().findViewById(R.id.pager);
        Log.d(TAG, String.valueOf(mPager.getCurrentItem()));
        mPager.setPageMargin(-4);
        SlidingImageAdapter slidingImageAdapter = new SlidingImageAdapter(getContext(),
                imageModelArrayList);
        slidingImageAdapter.setFragmentmanager(getActivity().getSupportFragmentManager());
        mPager.setAdapter(slidingImageAdapter);
        this.wormDotsIndicator = getView().findViewById(R.id.worm_dots_indicator);
        this.wormDotsIndicator.setViewPager(mPager);
        Log.d(TAG, "setupSlideshow: finished setup");
    }
    private void getdiscoverList(){
        // do with firestore
        mdb.collection("restaurant").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                restaurants.clear();
                Restaurant restaurant;
                Log.d(TAG, "Do query in Restaurant");
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    restaurant = doc.toObject(Restaurant.class);
                    restaurant.setId(doc.getId());
                    restaurants.add(restaurant);
                }

                final LinearLayout _restaurantList = getView().findViewById(R.id.discover_list);
                // add restaurant items to the LinearLayout _restaurantList
                for (final Restaurant r : restaurants) {
                    final RestaurantListItem restaurantListItem = new RestaurantListItem(getContext(), r, _restaurantList);
                    final View restaurantListItemView = restaurantListItem.getComponent();
                    _restaurantList.addView(restaurantListItemView);
                    restaurantListItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            passbundle(r.getId(), r.getName());
                        }
                    });
                }
            }
        });
    }

    // set bundle and pass to restaurantFragment
    private void passbundle(String restaurantId, String restaurantName){
        Log.d(TAG, "Send data to RestaurantFragment");
        this.args.putString("restaurantId", restaurantId);
        this.args.putString("restaurantName", restaurantName);
        this.fragmentrestaurant.setArguments(args);
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_view, fragmentrestaurant)
                .commit();
        Log.d(TAG, restaurantId);
    }

    private void displayDialog(String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog_Alert);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .show();
    }
}