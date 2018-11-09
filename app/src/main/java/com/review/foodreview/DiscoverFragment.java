package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.review.foodreview.component.RestaurantListItem;
import com.review.foodreview.dto.Restaurant;
import com.review.foodreview.dto.ImageModel;
import com.review.foodreview.dto.SlidingImageAdapter;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private List<Restaurant> restaurants = new ArrayList<>();
    private static final String LOG = "DISCOVERFRAGMENT";
    private static ViewPager mPager;
    WormDotsIndicator wormDotsIndicator;
    private BottomNavigationView navigationView;
    private static int NUM_PAGES = 3;
    private ArrayList<ImageModel> imageModelArrayList;
    private int[] myImageList = new int[]{R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG, "Start discover fragment (Create)");
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        Log.d(LOG, "Start discover fragment (CreateView)");
        return inflater.inflate(R.layout.discover, container, false);
    }

    @Override
    public void onActivityCreated(
            @Nullable Bundle savedInstanceState
    ) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG, "Start discover fragment (ActivityCreated)");
        restaurants.clear();
        Restaurant mcdonalds = new Restaurant(
                "McDonald's",
                "Fast food",
                "$ (< 120)",
                "24 hours",
                4.2f,
                318,
                true
        );
        Restaurant otoya = new Restaurant(
                "Otoya",
                "Japanese",
                "$$ (120 - 300)",
                "10.00 - 21.00",
                4.6f,
                282,
                true
        );
        restaurants.add(mcdonalds);
        restaurants.add(otoya);

        final LinearLayout _restaurantList = getView().findViewById(R.id.discover_list);
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
        //setup the discover's slideshow
        Log.d(LOG, "Do setupSlideshow");
        setupSlideshow();
        //setup the discover's navbar to clickable
        setupNavbar();
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

    }

    private void setupNavbar() {
        Log.d(LOG, "Do setupNavbar");
        navigationView = getView().findViewById(R.id.Navbottom);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_search: {
                        fragment = new SearchFragment();
                        break;
                    }

                }
                if(fragment != null){
                    Log.d(LOG, "Go to " + fragment);
                    getActivity()
                            .getSupportFragmentManager().beginTransaction().replace(R.id.main_view, fragment).commit();
                }

                return true;
            }

        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
