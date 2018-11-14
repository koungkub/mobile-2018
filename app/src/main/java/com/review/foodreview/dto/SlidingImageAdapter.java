package com.review.foodreview.dto;
import android.content.Context;
import android.os.Parcelable;
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
import com.review.foodreview.dto.ImageModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.annotation.Nullable;

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
    public Object instantiateItem(ViewGroup view, final int position) {
        FirebaseFirestore mdb = FirebaseFirestore.getInstance();
        final View imageLayout = inflater.inflate(R.layout.discover_slideshow,
                view,
                false);
        Log.d("ADAPTER", String.valueOf(position));
        assert imageLayout != null;
        mdb.collection("restaurant").limit(3).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    Restaurant restaurant = doc.toObject(Restaurant.class);
                    final TextView name = imageLayout
                            .findViewById(R.id.discover_text_name_restaurant_on_slideshow);
                    final ImageView imageView = imageLayout
                            .findViewById(R.id.image);
                    name.setText(restaurant.getName());
                    if(position == 0){
                        Picasso.get()
                                .load(restaurant.getImageUri().get(0))
                                .placeholder(R.drawable.slide1)
                                .into(imageView);
                    }
                    else if(position == 1){
                        Picasso.get()
                                .load(restaurant.getImageUri().get(0))
                                .placeholder(R.drawable.slide2)
                                .into(imageView);
                    }
                    else {
                        Picasso.get()
                                .load(restaurant.getImageUri().get(0))
                                .placeholder(R.drawable.slide3)
                                .into(imageView);
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


}