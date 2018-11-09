package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;

public class RegisterFragment extends Fragment {
    private static final String TAG = "REGISTER";
    private EditText _email, _password, _username;
    private FloatingActionButton _submitBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "RegisterFragment: onActivityCreated");
        registerFragmentElements();
        initSubmitBtn();
    }

    private void registerFragmentElements() {
        Log.d(TAG, "RegisterFragment: registerFragmentElements");
        _email = getView().findViewById(R.id.register_input_email);
        _password = getView().findViewById(R.id.register_input_password);
        _username = getView().findViewById(R.id.register_input_username);
        _submitBtn = getView().findViewById(R.id.register_btn);
    }

    private void initSubmitBtn() {
        _submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
            }
        });
    }
}
