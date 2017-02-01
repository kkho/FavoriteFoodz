package com.kkhoisawesome.favoritefoodz.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.kkhoisawesome.favoritefoodz.R;
import com.kkhoisawesome.favoritefoodz.activity.base.BaseActivity;
import com.kkhoisawesome.favoritefoodz.framework.model.Recipe;
import com.kkhoisawesome.favoritefoodz.util.ImageLoader;

/**
 * Created by kkho on 14.01.2017.
 */

public class RecipeContentActivity extends AppCompatActivity {
    private Fragment mFragment;
    private Toolbar mToolbar;
    private ImageView mImageView;
    private ImageLoader mImageLoader;
    private Recipe mRecipe;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_content);
        mRecipe = new Gson().fromJson(this.getIntent().getStringExtra("RecipeContent"), Recipe.class);

        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
        mImageView = (ImageView) findViewById(R.id.main_backdrop);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        mImageLoader = new ImageLoader(this, R.drawable.ic_menu_gallery);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        mWebView = (WebView) findViewById(R.id.webview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        reloadRecipeInformation();
    }

    private void reloadRecipeInformation() {
        mImageLoader.loadImage(mRecipe.image_url, mImageView);
        mToolbar.setTitle(mRecipe.title);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mRecipe.source_url);
    }
}
