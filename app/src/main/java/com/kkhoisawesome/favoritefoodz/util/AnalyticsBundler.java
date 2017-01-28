package com.kkhoisawesome.favoritefoodz.util;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by kkho on 26.01.2017.
 */

public class AnalyticsBundler {

    public static Bundle BundleAnalyticsEvent(String id, String name, String content, String eventContent) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, content);
        return bundle;
    }
}
