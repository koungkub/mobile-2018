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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private static final String TAG = "LOGIN";
    private EditText _email, _password;
    private Button _submitBtn, _registerBtn;
    private ProgressBar _loading;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerFragmentElements();
        initLoginBtn();
        initRegisterBtn();
    }

    private void registerFragmentElements() {
        Log.d(TAG, "LoginFragment: registerFragmentElements");
        _email = getView().findViewById(R.id.login_input_email);
        _password = getView().findViewById(R.id.login_input_password);
        _submitBtn = getView().findViewById(R.id.login_btn);
        _registerBtn = getView().findViewById(R.id.login_btn_register);
        _loading = getView().findViewById(R.id.login_loading);
    }

    private void initLoginBtn() {
        _submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = _email.getText().toString();
                final String password = _password.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getActivity(), "Please complete all the fields", Toast.LENGTH_LONG)
                            .show();
                } else {
                    _loading.setVisibility(View.VISIBLE);
                    _submitBtn.setVisibility(View.INVISIBLE);
                    _registerBtn.setVisibility(View.INVISIBLE);
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    _loading.setVisibility(View.GONE);
                                    _submitBtn.setVisibility(View.VISIBLE);
                                    _registerBtn.setVisibility(View.VISIBLE);
                                    if (task.isSuccessful()) {
                                        currentUser = auth.getCurrentUser();
                                        getFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.main_view, new DiscoverFragment())
                                                .commit();
                                    } else {
                                        Toast.makeText(getActivity(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG)
                                                .show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void initRegisterBtn() {
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .commit();
            }
        });
    }
}
