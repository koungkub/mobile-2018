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
    private String message;
    private List<Restaurant> restaurant;
    private List<Review> review;
    FirebaseFirestore mdb = FirebaseFirestore.getInstance();

    public GetallFirestore(String message){
        restaurant = new ArrayList<>();
        review = new ArrayList<>();
        this.message = message;
        getRestaurant();
    }
    private void getRestaurant(){
        mdb.collection("restaurant").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    restaurant.add(doc.toObject(Restaurant.class));
                }
                getReview(restaurant);
            }
        });
    }
    private void getReview(final List<Restaurant> restaurant){
        mdb.collection("review").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot doc : queryDocumentSnapshots){

                }
                Log.d("ADAPTER", restaurant.get(0).getName());
                Log.d("ADAPTER", message);
            }
        });
    }
}
