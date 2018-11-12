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
    private boolean wantCategory;
    private boolean wantUser;
    private List<Restaurant> restaurant;
    private List<Review> review;
    private List<Category> categories;
    private List<User> users;

    FirebaseFirestore mdb = FirebaseFirestore.getInstance();

    public GetallFirestore(boolean wantRestaurant, boolean wantReview, boolean wantCategory, boolean wantUser) {
        this.wantRestaurant = wantRestaurant;
        this.wantReview = wantReview;
        this.wantCategory = wantCategory;
        this.wantUser = wantUser;
        restaurant = new ArrayList<>();
        review = new ArrayList<>();
        categories = new ArrayList<>();
        users = new ArrayList<>();
    }
    private void getRestaurant(){
        if(wantRestaurant == true){
            mdb.collection("restaurant")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                    @Nullable FirebaseFirestoreException e) {
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
            mdb.collection("review")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                    @Nullable FirebaseFirestoreException e) {
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        review.add(doc.toObject(Review.class));
                    }
                }
            });
        }
        getCategory();
    }
    private void getCategory(){
        if(wantCategory == true){
            mdb.collection("category")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                    @Nullable FirebaseFirestoreException e) {
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        categories.add(doc.toObject(Category.class));
                    }
                }
            });
        }
    }
    private void getUser(){
        if(wantUser == true){
            mdb.collection("user")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                            @Nullable FirebaseFirestoreException e) {
                            for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                                users.add(doc.toObject(User.class));
                            }
                        }
                    });
        }

        Log.d("FIRESTORE", "TO DO SOMETHING HERE");
    }
}
