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

public abstract class GetallFirestore {
    private static final String TAG = "GETALLDATA";
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

    public void getRestaurant(){
        if(wantRestaurant == true){
            mdb.collection("restaurant")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                    @Nullable FirebaseFirestoreException e) {
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        restaurant.add(doc.toObject(Restaurant.class));
                        Log.d(TAG,"RESTAURANT = " +  doc.getData());
                    }
                    getReview();
                }
            });
        }
        else {
            getReview();
        }

    }
    public void getReview(){
        if(wantReview == true){
            mdb.collection("review")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                    @Nullable FirebaseFirestoreException e) {
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        review.add(doc.toObject(Review.class));
                        Log.d(TAG, "REVIEW = " + doc.getData());
                        getCategory();
                    }
                }
            });
        }
        else {
            getCategory();
        }
    }
    public void getCategory(){
        if(wantCategory == true){
            mdb.collection("category")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                    @Nullable FirebaseFirestoreException e) {
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        categories.add(doc.toObject(Category.class));
                        Log.d(TAG,"CATEGORY = " + doc.getData());
                        getUser();
                    }
                }
            });
        }
        else {
            getUser();
        }

    }
    public void getUser(){
        if(wantUser == true){
            mdb.collection("user")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                            @Nullable FirebaseFirestoreException e) {
                            for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                                users.add(doc.toObject(User.class));
                                Log.d(TAG, "USER =" + doc.getData());
                                doActivity(restaurant,
                                        review,
                                        categories,
                                        users);
                            }
                        }
                    });
        }
        else {
            doActivity(restaurant,
                    review,
                    categories,
                    users);
        }

    }
    public abstract void doActivity(List<Restaurant> restaurant,
                                    List<Review> review,
                                    List<Category> categories,
                                    List<User> users);
}
