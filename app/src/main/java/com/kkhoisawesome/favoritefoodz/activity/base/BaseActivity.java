package com.kkhoisawesome.favoritefoodz.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.kkhoisawesome.favoritefoodz.R;
import com.kkhoisawesome.favoritefoodz.activity.RecipeListActivity;
import com.kkhoisawesome.favoritefoodz.util.AnalyticsBundler;

/**
 * Created by kkho on 14.01.2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Fragment mFragment;
    public static final String ARG_URI = "_uri";

    protected static final int NAVVIEW_ITEM_LIST = R.id.action_list;
    protected static final int NAVVIEW_ITEM_ADD = R.id.action_add;

    protected static final int NAVVIEW_ITEM_INVALID = -1;
    protected static FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseCrash.log("Activity created");

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if(item.getItemId() == getSelfNavDrawerItem()) {
                            return false;
                        }

                        switch (item.getItemId()) {
                            case R.id.action_list:
                                Bundle bundle = AnalyticsBundler.BundleAnalyticsEvent("0","RecipeFragment" ,"Go to recipelist", "bottomnavview");
                                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                                GoToActivity(RecipeListActivity.class);
                                break;
                            case R.id.action_add:

                                break;
                        }
                        return false;
                    }
                });

        if (savedInstanceState == null) {
            mFragment = onCreatePane();
            mFragment.setArguments(intentToFragmentArguments(getIntent()));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.root_container, mFragment, "single_pane")
                    .commit();
        } else {
            mFragment = getSupportFragmentManager().findFragmentByTag("single_pane");
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        //Set whether analytics collection is enabled for this app on this device.
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setMinimumSessionDuration(2000);
        mFirebaseAnalytics.setSessionTimeoutDuration(300000);
    }

    private void GoToActivity(Class c) {
        createBackStack(new Intent(this, c));
    }

    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            finish();
        }
    }

    protected int getSelfNavDrawerItem() {
        return NAVVIEW_ITEM_INVALID;
    }

    protected abstract Fragment onCreatePane();

    public Fragment getFragment() {
        return mFragment;
    }

    public static Bundle intentToFragmentArguments(Intent intent) {
        Bundle arguments = new Bundle();
        if (intent == null) {
            return arguments;
        }

        final Uri data = intent.getData();
        if (data != null) {
            arguments.putParcelable(ARG_URI, data);
        }

        final Bundle extras = intent.getExtras();
        if (extras != null) {
            arguments.putAll(intent.getExtras());
        }
        return arguments;
    }

    public static Intent fragmentArgumentToIntent(Bundle arguments) {
        Intent intent = new Intent();
        if (arguments == null) {
            return intent;
        }

        final Uri data = arguments.getParcelable(ARG_URI);
        if (data != null) {
            intent.setData(data);
        }

        intent.putExtras(arguments);
        intent.removeExtra(ARG_URI);
        return intent;
    }
}