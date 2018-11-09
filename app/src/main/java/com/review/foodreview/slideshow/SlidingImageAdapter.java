package com.review.foodreview.slideshow;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.review.foodreview.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class SlidingImageAdapter extends PagerAdapter {


    private ArrayList<ImageModel> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;


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
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.discover_slideshow,
                view,
                false);
        Log.d("ADAPTER", String.valueOf(position));
        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);
        if(position == 0){
            Picasso.get()
                    .load("https://www.knorr.com/content/dam/unilever/knorr_world/global/other_foods/all/regional_dishes-thai_north-eastern_dishes-hero_image-858794.jpg")
                    .placeholder(R.drawable.slide1)
                    .into(imageView);
        }
        else if(position == 1){
            Picasso.get()
                    .load("https://www.knorr.com/content/dam/unilever/knorr_world/global/other_foods/all/type_of_dishes-international_dishes-hero_image-861954.jpg")
                    .placeholder(R.drawable.slide2)
                    .into(imageView);
        }
        else {
            Picasso.get()
                    .load("https://www.knorr.com/content/dam/unilever/knorr_world/global/other_foods/all/type_of_dishes-stir-fried-hero_image-862950.jpg")
                    .placeholder(R.drawable.slide3)
                    .into(imageView);
        }
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


}