package com.review.foodreview.permission;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authenticate {

    private final String TAG = getClass().getSimpleName();

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private boolean registerStatus;
    private boolean sendEmailStatus;

    public void register(String email, String password) {
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                registerStatus = true;
                Log.d(TAG, "register success");

                firebaseUser = authResult.getUser();
                registerSendEmail();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                registerStatus = false;
                Log.d(TAG, "register failed");
            }
        });
    }

    private void registerSendEmail() {
        firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                sendEmailStatus = true;
                Log.d(TAG, "send email success");

                firebaseAuth.signOut();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                sendEmailStatus = false;
                Log.d(TAG, "send email failed");
            }
        });
    }

    public void signIn(String email, String password) {

    }

    public boolean getRegisterStatus() {
        return registerStatus;
    }

    public boolean getSendEmailStatus() {
        return sendEmailStatus;
    }

}
