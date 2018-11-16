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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SearchFragment extends Fragment  {
    private static final String TAG = "SEARCH";
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private GridLayout _categoryGrid;

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

        _categoryGrid = getView().findViewById(R.id.search_grid);

        firestore.collection("category").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (getContext() != null) {
                                for (QueryDocumentSnapshot category : task.getResult()) {
                                    final Button categoryButton = createCategoryButton(category.getString("name"), category.getId());
                                    _categoryGrid.addView(categoryButton);
                                }
                            }
                        }
                    }
                });
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

}
