package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.GridLayout;
import android.widget.Button;
import android.widget.SearchView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SearchFragment extends Fragment  {
    private static final String TAG = "SEARCH";
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private GridLayout _categoryGrid;
    private QuerySnapshot categorySnapshot;
    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (categorySnapshot == null) {
            Log.d(TAG, "onCreate: category not loaded, retrieving categories.");
            firestore.collection("category").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onComplete: categories retrieved.");
                                if (getContext() != null) {
                                    categorySnapshot = task.getResult();
                                    populateList(categorySnapshot);
                                }
                            }
                        }
                    });
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.onFragmentChanged(TAG);
        Log.d(TAG, "onActivityCreated");
        _categoryGrid = getView().findViewById(R.id.search_grid);
        if (categorySnapshot != null) populateList(categorySnapshot);
        //from start >>> searchbar
        searchbar();
    }

    private Button createCategoryButton(final String categoryName, final String categoryId) {
        Button button = new Button(getContext());
        button.setText(categoryName);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 final Fragment fragment = new SearchResultsFragment();
                 final Bundle bundle = new Bundle();
                 bundle.putString("categoryId", categoryId);
                 bundle.putString("categoryName", categoryName);
                 fragment.setArguments(bundle);
                 getFragmentManager()
                         .beginTransaction()
                         .replace(R.id.main_view, fragment)
                         .addToBackStack(null)
                         .commit();
            }
        });
        return button;
    }

    private void populateList(QuerySnapshot categorySnapshot) {
        Log.d(TAG, "populateList");
        for (QueryDocumentSnapshot category : categorySnapshot) {
            final Button categoryButton = createCategoryButton(category.getString("name"), category.getId());
            _categoryGrid.addView(categoryButton);
        }
    }
    private void searchbar(){
        searchView = getView().findViewById(R.id.search_btn_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle args = new Bundle();
                Fragment searchResultFragment = new SearchBarFragment();
                args.putString("search", query);
                searchResultFragment.setArguments(args);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, searchResultFragment)
                        .addToBackStack(null)
                        .commit();
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}
