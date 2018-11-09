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
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {
    private static final String TAG = "REGISTER";
    private EditText _email, _password, _username;
    private FloatingActionButton _submitBtn;
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
                String email = _email.getText().toString();
                String password = _password.getText().toString();
                String username = _username.getText().toString();
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "createUserWithEmail:success");
                                    getActivity()
                                            .getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.main_view, new DiscoverFragment())
                                            .commit();
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
        });
    }
}
