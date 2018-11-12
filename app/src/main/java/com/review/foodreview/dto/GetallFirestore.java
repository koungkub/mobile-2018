package com.review.foodreview.dto;

import android.util.Log;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class GetallFirestore {
    private boolean wantRestaurant;
    private boolean wantReview;
    private List<Restaurant> restaurant;
    private List<Review> review;
    FirebaseFirestore mdb = FirebaseFirestore.getInstance();

    public GetallFirestore(Boolean wantRestaurant, Boolean wantReview){
        restaurant = new ArrayList<>();
        review = new ArrayList<>();
        this.wantRestaurant = wantRestaurant;
        this.wantReview = wantReview;
        getRestaurant();
    }
    private void getRestaurant(){
        if(wantRestaurant == true){
            mdb.collection("restaurant").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        restaurant.add(doc.toObject(Restaurant.class));
                    }
                }
            });
        }
        getReview();

    }
    private void getReview(){
        if(wantReview == true){
            mdb.collection("review").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        Log.d("ADAPTERFIREBASE", doc.getData().toString());
                        review.add(doc.toObject(Review.class));
                    }
                    Log.d("ADAPTERFIREBASE", restaurant.get(0).getName());
                    Log.d("ADAPTERFIREBASE", review.get(0).getId());
                    Log.d("ADAPTERFIREBASE", "to do something");
                }
            });
        }

    }
}
