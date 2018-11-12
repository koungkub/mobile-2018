package com.review.foodreview.dto;

import android.util.Log;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class GetallFirestore {
    private String message;
    FirebaseFirestore mdb = FirebaseFirestore.getInstance();

    public GetallFirestore(String message){
        this.message = message;
        getRestaurant();
    }
    private void getRestaurant(){
        mdb.collection("restaurant").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot doc : queryDocumentSnapshots){

                }
                getReview();
            }
        });
    }
    private void getReview(){
        mdb.collection("review").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot doc : queryDocumentSnapshots){

                }
                Log.d("ADAPTER", message);
            }
        });
    }
}
