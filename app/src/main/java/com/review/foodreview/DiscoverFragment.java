package com.review.foodreview;

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

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.review.foodreview.component.DiscoverGetListData;
import com.review.foodreview.component.RestaurantListItem;
import com.review.foodreview.dto.GetallFirestore;
import com.review.foodreview.dto.Restaurant;
import com.review.foodreview.dto.ImageModel;
import com.review.foodreview.dto.SlidingImageAdapter;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiscoverFragment extends Fragment{
    private List<Restaurant> restaurants = new ArrayList<>();
    private static final String TAG = "DISCOVERFRAGMENT";
    private static ViewPager mPager;
    private Fragment fragmentrestaurant;
    private Bundle args;
    private FirebaseFirestore mdb;
    private WormDotsIndicator wormDotsIndicator;
    private static final int NUM_PAGES = 3;
    private ArrayList<ImageModel> imageModelArrayList;
    private int[] myImageList = new int[] {
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
        // clear the list to prevent duplicate data
        restaurants.clear();

        // dummy data
        final DocumentReference dummyCategory = mdb.collection("category").document("path");
        final HashMap<String, Long> dummyRating = new HashMap<>();
        dummyRating.put("food", 4L); dummyRating.put("service", 3L); dummyRating.put("atmosphere", 3L);
        final ArrayList<String> dummyImageUri = new ArrayList<>();
        dummyImageUri.add("/path/to/image.jpg");
        final ArrayList<DocumentReference> dummyReviews = new ArrayList<>();
        dummyReviews.add(dummyCategory);

        Restaurant mcdonalds = new Restaurant(
                "testId2",
                "Otoya",
                "$$ (120 - 300)",
                "10.00 - 21.00",
                "0123456789",
                "Japanese",
                true,
                dummyCategory,
                new GeoPoint(10, 10),
                dummyRating,
                dummyImageUri,
                dummyReviews,
                20
        );
        Restaurant otoya = new Restaurant(
                "testId2",
                "Otoya",
                "$$ (120 - 300)",
                "10.00 - 21.00",
                "0123456789",
                "Japanese",
                true,
                dummyCategory,
                new GeoPoint(10, 10),
                dummyRating,
                dummyImageUri,
                dummyReviews,
                20
        );
        restaurants.add(mcdonalds);
        restaurants.add(otoya);
        final LinearLayout _restaurantList = getView().findViewById(R.id.discover_list);

        // add restaurant items to the LinearLayout _restaurantList
        for (Restaurant r : restaurants) {
            final RestaurantListItem restaurantListItem = new RestaurantListItem(getContext(), r, _restaurantList);
            final View restaurantListItemView = restaurantListItem.getComponent();
            _restaurantList.addView(restaurantListItemView);
            restaurantListItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new RestaurantFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
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
        mPager.setPageMargin(-4);
        mPager.setAdapter(new SlidingImageAdapter(
                getContext(),
                imageModelArrayList));
        this.wormDotsIndicator = getView().findViewById(R.id.worm_dots_indicator);
        this.wormDotsIndicator.setViewPager(mPager);

    }
    private void getdiscoverList(){
        DiscoverGetListData discoverGetListData = new DiscoverGetListData(false,false, false, true);
    }

    //set bundle and pass to restaurantFragment
    private void passbundle(String restaurantId){
        this.args.putString("id", restaurantId);
        this.fragmentrestaurant.setArguments(args);
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_view, fragmentrestaurant).commit();
        Log.d(TAG, restaurantId);
    }

}

// Auto start of viewpager open it if you want
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
//                mPager
//                        .setCurrentItem(currentPage++, true);
//            }
//        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 3000, 3000);