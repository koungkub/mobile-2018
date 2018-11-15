package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    private ListView listMenu;
    public MeLoginFragment(){
        menu = new ArrayList<>();
        menu.add("Bookmark");
        menu.add("My review");
        menu.add("Sign out");

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
        try {
            if(mUser != null){
                showMenu();
            }
        }catch (Exception e){
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new MeNotLoginFragment())
                    .commit();
        }

    }
    private void showMenu(){
        menuAdapter  = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menu
        );
        listMenu = getView().findViewById(R.id.me_login_menulist);
        listMenu.setAdapter(menuAdapter);
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 2){
                    mAuth.signOut();
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new DiscoverFragment())
                            .commit();
                }
            }
        });
    }
}
