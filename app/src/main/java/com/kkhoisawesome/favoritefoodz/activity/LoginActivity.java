package com.kkhoisawesome.favoritefoodz.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.kkhoisawesome.favoritefoodz.R;
import com.kkhoisawesome.favoritefoodz.activity.base.BaseActivity;
import com.kkhoisawesome.favoritefoodz.util.AnalyticsBundler;
import com.kkhoisawesome.favoritefoodz.util.Settings;

/**
 * Created by kkho on 26.01.2017.
 */

public class LoginActivity extends AppCompatActivity {
    private TextView mUserTextView;
    private TextView mPasswordTextView;
    private Button mLoginButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserTextView = (TextView) findViewById(R.id.email_user);
        mPasswordTextView = (TextView) findViewById(R.id.password_user);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("LoginActivity", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("LoginActivity", "onAuthStateChanged:signed_out");
                }
            }
        };

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(mUserTextView.getText().toString(), mPasswordTextView.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("LoginActivity", "signInWithEmail:onComplete:" + task.isSuccessful());
                                if (!task.isSuccessful()) {
                                    Log.w("LoginActivity", "signInWithEmail:failed", task.getException());
                                    Toast.makeText(LoginActivity.this, "Login failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else if(task.isSuccessful()) {
                                    SharedPreferences.Editor editor = getSharedPreferences(Settings.SHARED_PREF, MODE_PRIVATE).edit();
                                    editor.putBoolean(Settings.SHARED_PREF_LOGIN, true);
                                    editor.apply();

                                    Intent intent = new Intent(LoginActivity.this, RecipeListActivity.class);
                                    startActivity(intent);
                                }

                            }
                        });
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }




}
