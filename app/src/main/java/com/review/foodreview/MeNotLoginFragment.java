package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MeNotLoginFragment extends Fragment{
    private static final String TAG = "ME";
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_not_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.onFragmentChanged(TAG);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
            loginBtn();
            registerBtn();
            Log.d(TAG, "Authen");
            if(mUser != null)
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new MeLoginFragment())
                    .commit();
    }

    private void loginBtn(){
        Log.d(TAG, "press login button");
        Button loginBtn = getActivity()
                .findViewById(R.id.me_not_login_btn_login);
        loginBtn
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .commit();
            }
        });
    }

    private void registerBtn(){
        Log.d(TAG, "press create account");
        Button register = getActivity()
                .findViewById(R.id.me_not_login_btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .commit();
            }
        });
    }
}
