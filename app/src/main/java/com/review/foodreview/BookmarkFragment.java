package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.review.foodreview.component.RestaurantListItem;
import com.review.foodreview.dto.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BookmarkFragment extends Fragment{
    private static final String TAG = "BOOKMARK";
    private FirebaseFirestore mdb;
    private FirebaseAuth mAuth;
    private static Restaurant restaurant;
    private Toolbar _toolbar;
    private FirebaseUser mUser;
    private List<Restaurant> restaurants = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bookmark, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.onFragmentChanged(TAG);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mdb = FirebaseFirestore.getInstance();
        createMenu();
        findBookmark();

    }
    private void createMenu() {
        _toolbar = getView()
                .findViewById(R.id.bookmark_action_bar);
        _toolbar.setTitle("Bookmark");
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        Objects.requireNonNull(getActivity()).setActionBar(_toolbar);
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    private void findBookmark(){
        DocumentReference documentReferenceUid = mdb.collection("user").document(mUser.getUid());

        mdb.collection("bookmark")
                .whereEqualTo("owner", documentReferenceUid).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List<DocumentReference> bookmarkList = new ArrayList<>();
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots){
                    bookmarkList.add((DocumentReference) doc.get("restaurant"));
                }
                showBookmark(bookmarkList);

            }
        });
        }

    private void showBookmark(List<DocumentReference> bookmarkList) {
        if (bookmarkList.size() < 1) {
            Log.d(TAG, "Not have bookmark");
        } else {
            TextView haveBookmark = getView().findViewById(R.id.bookmark_text_nothave);
            haveBookmark.setVisibility(View.GONE);
            Log.d(TAG, "get bookmark to List");
            for (DocumentReference bookmark : bookmarkList) {
                bookmark.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        restaurants.clear();
                        if (documentSnapshot.exists()) {
                            Log.d(TAG, documentSnapshot.get("name").toString());
                            restaurant = new Restaurant(
                                    documentSnapshot.getId(),
                                    documentSnapshot.getString("name"),
                                    documentSnapshot.getString("priceRange"),
                                    documentSnapshot.getString("openHours"),
                                    documentSnapshot.getString("telephone"),
                                    documentSnapshot.getString("categoryName"),
                                    documentSnapshot.getBoolean("delivery"),
                                    documentSnapshot.getDocumentReference("category"),
                                    documentSnapshot.getGeoPoint("location"),
                                    (HashMap<String, Long>) documentSnapshot.get("rating"),
                                    (List<String>) documentSnapshot.get("imageUri"),
                                    (List<DocumentReference>) documentSnapshot.get("review"),
                                    0
                            );
                        }
                        restaurants.add(restaurant);
                        final LinearLayout _restaurantList = getView().findViewById(R.id.bookmark_list);
                        // add restaurant items to the LinearLayout _restaurantList
                        for (final Restaurant r : restaurants) {
                            final RestaurantListItem restaurantListItem = new RestaurantListItem(getContext(), r, _restaurantList);
                            final View restaurantListItemView = restaurantListItem.getComponent();
                            _restaurantList.addView(restaurantListItemView);
                            restaurantListItemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("restaurantId", r.getId());
                                    Fragment restaurantFragment = new RestaurantFragment();
                                    restaurantFragment.setArguments(bundle);
                                    getActivity()
                                            .getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.main_view, restaurantFragment)
                                            .addToBackStack(null)
                                            .commit();
                                }
                            });
                        }
                    }
                });
            }
        }
    }
}
