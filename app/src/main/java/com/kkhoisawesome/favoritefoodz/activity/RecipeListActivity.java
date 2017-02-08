package com.kkhoisawesome.favoritefoodz.activity;

import android.support.v4.app.Fragment;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.kkhoisawesome.favoritefoodz.activity.base.BaseActivity;
import com.kkhoisawesome.favoritefoodz.fragment.RecipeListFragment;

/**
 * Created by kkho on 16.01.2017.
 */

public class RecipeListActivity extends BaseActivity {

    public RecipeListActivity() {
    }

    public FirebaseAnalytics getFirebaseAnalytics() {
        if(mFirebaseAnalytics == null) {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            //Set whether analytics collection is enabled for this app on this device.
            mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
            mFirebaseAnalytics.setMinimumSessionDuration(2000);
            mFirebaseAnalytics.setSessionTimeoutDuration(300000);
        }
        return mFirebaseAnalytics;
    }

    @Override
    protected Fragment onCreatePane() {
        return new RecipeListFragment();
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVVIEW_ITEM_LIST;
    }

}
