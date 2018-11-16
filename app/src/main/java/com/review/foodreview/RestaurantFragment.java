package com.review.foodreview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import com.google.firebase.firestore.EventListener;
import com.review.foodreview.component.ReviewListItem;
import com.review.foodreview.dto.Bookmark;
import com.review.foodreview.dto.Restaurant;
import com.review.foodreview.dto.Review;
import com.squareup.picasso.Picasso;

import java.util.*;

public class RestaurantFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "RESTAURANT";

    private String restaurantId, restaurantName, bookmarkId;
    private static Restaurant restaurant;
    private boolean isLoggedIn;
    private DocumentReference userRef, restaurantRef;

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private GoogleMap mMap;

    private TextView _restaurantName, _restaurantType, _priceRange, _rating, _reviewCount;
    private TextView _openHours, _delivery;
    private Toolbar _toolbar;
    private Button _writeBtn, _viewAllBtn, _bookmarkBtn;
    private LinearLayout _reviewList;
    private ProgressBar _reviewLoading;
    private MapView _mapView;
    private ImageView _headerImage;
    private Menu menu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Trying to setHasOptionsMenu");
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        MainActivity.onFragmentChanged(TAG);
        return inflater.inflate(R.layout.restaurant, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        // get bundle
        Bundle bundle = getArguments();

        if (bundle.getString("restaurantId") == null)
            Log.d(TAG, "onActivityCreated: restaurantId not found in the bundle");
        restaurantId = bundle.getString("restaurantId");
        restaurantName = bundle.getString("restaurantName");
        restaurantRef = firestore.collection("restaurant").document(restaurantId);

        isLoggedIn = auth.getCurrentUser() != null;
        if (isLoggedIn) userRef = firestore.collection("user").document(auth.getCurrentUser().getUid());

        registerFragmentElements();
        createMenu();

        Log.d(TAG, "fetching restaurant");
        restaurantRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot,
                                        @javax.annotation.Nullable FirebaseFirestoreException e) {
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
                        } else {
                            Log.d(TAG, "Restaurant doesn't exist");
                            restaurant = new Restaurant(
                                    "no id",
                                    "no name",
                                    "no price",
                                    "never opens",
                                    "no phone",
                                    "no category",
                                    false,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    0
                            );
                        }
                        // methods that require restaurant data from Firestore
                        _toolbar.setTitle(restaurant.getName());
                        setTexts(restaurant);
                        if (restaurant.getImageUri() != null)
                            Picasso.get().load(restaurant.getImageUri().get(0)).into(_headerImage);
                        initViewAllBtn();
                        createMap(savedInstanceState);
                    }
                });

        Log.d(TAG, "fetching reviews");
        firestore.collection("review")
                .whereEqualTo("restaurant", restaurantRef)
                .limit(5)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Successfully retrieved reviews");
                            for (QueryDocumentSnapshot reviewSnapshot : task.getResult()) {
                                Review review = new Review(
                                        reviewSnapshot.getId(),
                                        reviewSnapshot.getDocumentReference("author"),
                                        reviewSnapshot.getDocumentReference("restaurant"),
                                        reviewSnapshot.getString("description"),
                                        reviewSnapshot.getTimestamp("date"),
                                        (ArrayList<String>) reviewSnapshot.get("imageUri"),
                                        (HashMap<String, Long>) reviewSnapshot.get("rating")
                                );
                                // add to _reviewList LinearLayout
                                if (getContext() != null) {
                                    final ReviewListItem reviewListItem = new ReviewListItem(getContext(), review, _reviewList);
                                    final View restaurantListItemView = reviewListItem.getComponent();
                                    _reviewList.addView(restaurantListItemView);
                                }
                            }
                        } else {
                            displayDialog("Error", task.getException().getLocalizedMessage());
                        }
                        // hide progress bar and make content visible
                        _reviewLoading.setVisibility(View.GONE);
                        _reviewList.setVisibility(View.VISIBLE);
                    }
                });

        if (isLoggedIn) {
            Log.d(TAG, "fetching bookmark status");
            firestore.collection("bookmark")
                    .whereEqualTo("owner", userRef)
                    .whereEqualTo("restaurant", restaurantRef)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().size() > 0) {
                                    for (QueryDocumentSnapshot bm : task.getResult()) bookmarkId = bm.getId();
                                    if (menu != null) {
                                        toggleBookmarkIconSaved(true);
                                    }
                                } else {
                                    bookmarkId = null; // reset to null
                                    if (menu != null) {
                                        toggleBookmarkIconSaved(false);
                                    }
                                }
                            } else {
                                displayDialog("Error", task.getException().getLocalizedMessage());
                            }
                        }
                    });
        }

        initWriteBtn();
    }

    private void registerFragmentElements() {
        Log.d(TAG, "registerFragmentElements");
        _restaurantName = getView().findViewById(R.id.restaurant_text_restaurant);
        _restaurantType = getView().findViewById(R.id.restaurant_text_type);
        _priceRange = getView().findViewById(R.id.restaurant_text_price);
        _rating = getView().findViewById(R.id.restaurant_text_review);
        _reviewCount = getView().findViewById(R.id.restaurant_text_reviewer);
        _openHours = getView().findViewById(R.id.restaurant_text_time);
        _delivery = getView().findViewById(R.id.restaurant_text_delivery);
        _toolbar = getView().findViewById(R.id.restaurant_action_bar);
        _writeBtn = getView().findViewById(R.id.restaurant_review_btn_add);
        _viewAllBtn = getView().findViewById(R.id.restaurant_review_btn_all);
        _reviewList = getView().findViewById(R.id.restaurant_recent_reviews);
        _reviewLoading = getView().findViewById(R.id.restaurant_loading_reviews);
        _mapView = getView().findViewById(R.id.restaurant_map);
        _headerImage = getView().findViewById(R.id.restaurant_image_restaurant);
        _bookmarkBtn = getView().findViewById(R.id.restaurant_menu_bookmark);
    }

    private void createMenu() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(_toolbar);
        if (restaurantName != null) _toolbar.setTitle(restaurantName);
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        _toolbar.setNavigationContentDescription("Back");
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    private void setTexts(Restaurant restaurant) {
        _restaurantName.setText(restaurant.getName());
        _restaurantType.setText(restaurant.getCategoryName());
        _priceRange.setText(restaurant.getPriceRange());
        // TODO: Calculate restaurant rating
        // _rating.setText(String.format(Locale.ENGLISH, "%.1f", restaurant.getRating().get("food")));
        _reviewCount.setText(
                String.format(
                        Locale.ENGLISH,
                        getString(R.string.page_restaurant_from_d_reviews),
                        restaurant.getReviewCount())
        );
        _openHours.setText(restaurant.getOpenHours());
        if (!restaurant.isDelivery()) _delivery.setText(R.string.page_restaurant_no_delivery);
    }

    private void initWriteBtn() {
        _writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLoggedIn) {
                    final Fragment fragment = new ReviewEditFragment();
                    final Bundle bundle = new Bundle();
                    bundle.putString("restaurantName", restaurant.getName());
                    bundle.putString("restaurantId", restaurant.getId());
                    fragment.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.main_view, fragment)
                            .commit();
                } else {
                    displayDialog("Please log in", "You need to log in first to write reviews.");
                }
            }
        });
    }

    private void initViewAllBtn() {
        final Bundle bundle = new Bundle();
        bundle.putString("restaurantName", restaurant.getName());
        bundle.putString("restaurantId", restaurant.getId());
        _viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ReviewsFragment();
                fragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, fragment)
                        .commit();
            }
        });
    }

    private void displayDialog(String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog_Alert);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .show();
    }

    private void createMap(Bundle savedInstanceState) {
        Log.d(TAG, "createMap");
        _mapView.onCreate(savedInstanceState);
        _mapView.onResume();
        _mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady");
        mMap = googleMap;
        LatLng restaurantPin = new LatLng(restaurant.getLocation().getLatitude(), restaurant.getLocation().getLongitude());
        mMap.addMarker(new MarkerOptions().position(restaurantPin).title(restaurantName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(restaurantPin));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16F));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.d(TAG, "onCreateOptionsMenu: ");
        this.menu = menu;
        inflater.inflate(R.menu.restaurant, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final String menuName = item.getTitle().toString();
        Log.d(TAG, "onOptionsItemSelected: " + menuName);
        if (menuName.equalsIgnoreCase("bookmark")) {
            if (isLoggedIn) {
                Log.d(TAG, "onOptionsItemSelected: bookmark is " + bookmarkId);
                if (bookmarkId == null) addToBookmark(restaurantRef, userRef);
                else removeBookmark(bookmarkId);
            } else {
                displayDialog("Not logged in", "You need to log in to save bookmarks.");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToBookmark(DocumentReference restaurantRef, DocumentReference userRef) {
        Log.d(TAG, "addToBookmark");
        if (isLoggedIn) {
            Log.d(TAG, "addToBookmark: saving");
            firestore.collection("bookmark")
                    .add(new Bookmark(userRef, restaurantRef))
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "addToBookmark: done");
                                bookmarkId = task.getResult().getId(); 
                                toggleBookmarkIconSaved(true);
                            } else
                                displayDialog("Error", task.getException().getLocalizedMessage());
                        }
                    });
        }
    }

    private void removeBookmark(final String theBookmarkId) {
        Log.d(TAG, "removeBookmark");
        firestore.collection("bookmark")
                .document(theBookmarkId)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            bookmarkId = null;
                            toggleBookmarkIconSaved(false);
                        } else
                            displayDialog("Error", task.getException().getLocalizedMessage());
                    }
                });
    }

    private void toggleBookmarkIconSaved(boolean isSaved) {
        if (isSaved) {
            menu.findItem(R.id.restaurant_menu_bookmark).setVisible(false);
            menu.findItem(R.id.restaurant_menu_bookmark_saved).setVisible(true);
        } else {
            menu.findItem(R.id.restaurant_menu_bookmark_saved).setVisible(false);
            menu.findItem(R.id.restaurant_menu_bookmark).setVisible(true);
        }
    }
}
