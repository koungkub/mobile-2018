package com.review.foodreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterFragment extends Fragment {
    private static final String TAG = "REGISTER";
    private EditText _email, _password, _username;
    private Button _submitBtn, _loginBtn;
    private ProgressBar _loading;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
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
        _email = getView().findViewById(R.id.login_input_email);
        _password = getView().findViewById(R.id.login_input_password);
        _username = getView().findViewById(R.id.register_input_username);
        _submitBtn = getView().findViewById(R.id.register_btn);
        _loginBtn = getView().findViewById(R.id.register_btn_login);
        _loading = getView().findViewById(R.id.register_loading);
    }

    private void initSubmitBtn() {
        _submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked");
                final String email = _email.getText().toString();
                final String password = _password.getText().toString();
                final String username = _username.getText().toString();
                if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                    Toast.makeText(getActivity(), "Please complete all the fields", Toast.LENGTH_LONG)
                            .show();
                } else {
                    _loading.setVisibility(View.VISIBLE);
                    _submitBtn.setVisibility(View.INVISIBLE);
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    _loading.setVisibility(View.GONE);
                                    _submitBtn.setVisibility(View.VISIBLE);
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = auth.getCurrentUser();
                                        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(username)
                                                .build();
                                        user.updateProfile(changeRequest)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        getActivity()
                                                                .getSupportFragmentManager()
                                                                .beginTransaction()
                                                                .replace(R.id.main_view, new DiscoverFragment())
                                                                .commit();
                                                    }
                                                });
                                    } else {
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(),
                                                task.getException().getLocalizedMessage(),
                                                Toast.LENGTH_LONG)
                                                .show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void initLoginBtn() {
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .commit();
            }
        });
    }
}
