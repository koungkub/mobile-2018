package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MeLoginFragment extends Fragment{
    ArrayAdapter<String> menuAdapter;
    private static final String TAG = "ME";
    private List<String> menu;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    public MeLoginFragment(){
        menu = new ArrayList<>();

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.onFragmentChanged(TAG);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            Log.d(TAG, "Authen");
            initbookmark();
            initsignout();
        }
        else{
            Log.d(TAG, "Not authen");
        }
    }

    private void initbookmark(){
        TextView bookmark = getView()
                .findViewById(R.id.me_login_bookmark);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Do bookmark menu");
            }
        });
    }

    private void initsignout(){
        TextView signoutBtn = getView()
                .findViewById(R.id.me_login_signout);
        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MeNotLoginFragment()).commit();
            }
        });
    }

}
