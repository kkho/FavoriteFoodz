package com.kkhoisawesome.favoritefoodz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kkhoisawesome.favoritefoodz.util.Settings;

/**
 * Created by kkho on 01.02.2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isLoggedIn = getSharedPreferences(Settings.SHARED_PREF, MODE_PRIVATE).getBoolean(Settings.SHARED_PREF_LOGIN, false);
        if(isLoggedIn) {
            Intent intent = new Intent(getApplicationContext(), RecipeListActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}