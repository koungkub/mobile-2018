package com.review.foodreview;

import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReviewEditFragment extends Fragment {

    private static final String TAG = "REVIEWEDIT";

    private Toolbar _toolbar;
    private EditText _reviewText;
    private RatingBar _ratingBarFood;
    private RatingBar _ratingBarService;
    private RatingBar _ratingBarAtmosphere;

    private Map<String, Object> data;
    private Map<String, Object> rating;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.review_edit, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "load REVIEWEDIT fragment");
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        createMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Init FirebaseStorage and FirebaseStore
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Init FirebaseAuth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // get parameter from previous fragment using bundle
        Bundle bundle = getArguments();
        String restaurantId = bundle.getString("restaurantId");

        // get value from .xml
        _reviewText = getView().findViewById(R.id.review_edit_description);
        _ratingBarFood = getView().findViewById(R.id.review_edit_star_food);
        _ratingBarService = getView().findViewById(R.id.review_edit_star_service);
        _ratingBarAtmosphere = getView().findViewById(R.id.review_edit_star_atmosphere);

        // convert value
        String stringReview = _reviewText.getText().toString();
        float floatFood = _ratingBarFood.getRating();
        float floatService = _ratingBarService.getRating();
        float floatAtmosphere = _ratingBarAtmosphere.getRating();

        // get user and restaurant reference
        DocumentReference userRef = db.collection("user").document(firebaseAuth.getUid());
        DocumentReference restaurantRef = db.collection("restaurant").document(restaurantId);

        rating = new HashMap<>();
        rating.put("atmosphere", floatAtmosphere);
        rating.put("food", floatFood);
        rating.put("service", floatService);

        data = new HashMap<>();
        data.put("author", userRef);
        data.put("restaurant", restaurantRef);
        data.put("date", new Date());
        data.put("description", stringReview);
        data.put("rating", rating);
        data.put("imageUri", Arrays.asList("cupcake", "muffin"));


        db.collection("review").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "add data to firestore success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "add data to firestore failure");
            }
        });

        return super.onOptionsItemSelected(item);
    }

    private void createMenu() {
        _toolbar = getActivity().findViewById(R.id.review_edit_toolbar);
        _toolbar.setTitle("Write a review");
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        _toolbar.inflateMenu(R.menu.review_edit);
        getActivity().setActionBar(_toolbar);
    }
}
