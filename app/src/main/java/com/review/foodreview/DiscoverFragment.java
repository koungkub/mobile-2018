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

import com.review.foodreview.slideshow.ImageModel;
import com.review.foodreview.slideshow.SlidingImageAdapter;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;

public class DiscoverFragment extends Fragment{
    private static final String LOG = "DISCOVERFRAGMENT";
    private static ViewPager mPager;
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
        Log.d(LOG, "Start discover fragment (ActivityCreated)");
        super.onActivityCreated(savedInstanceState);

        //setup the discover's slideshow
        Log.d(LOG, "Do setupSlideshow");
        setupSlideshow();
    }


    //get image
    private ArrayList<ImageModel> populateList(){

        ArrayList<ImageModel> list = new ArrayList<>();

        for(int i = 0; i < NUM_PAGES; i++){
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }
//setup slideshow here
    private void setupSlideshow() {

        mPager = getView().findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImageAdapter(
                getContext(),
                imageModelArrayList));

        WormDotsIndicator wormDotsIndicator = (WormDotsIndicator) getView().findViewById(R.id.worm_dots_indicator);
        wormDotsIndicator.setViewPager(mPager);

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
}
