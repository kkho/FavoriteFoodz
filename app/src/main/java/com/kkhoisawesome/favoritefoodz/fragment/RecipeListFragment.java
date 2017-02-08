package com.kkhoisawesome.favoritefoodz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.kkhoisawesome.favoritefoodz.R;
import com.kkhoisawesome.favoritefoodz.activity.RecipeListActivity;
import com.kkhoisawesome.favoritefoodz.adapter.RecipeAdapter;
import com.kkhoisawesome.favoritefoodz.framework.model.Recipe;
import com.kkhoisawesome.favoritefoodz.util.ImageLoader;
import com.kkhoisawesome.favoritefoodz.viewholder.RecipeViewHolder;

import java.util.ArrayList;

/**
 * Created by kkho on 16.01.2017.
 */

public class RecipeListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    private LinearLayoutManager mManager;
    private RecipeAdapter mRecipeAdapter;
    private ValueEventListener mRecipeListener;
    private TextView mErrorTextView;

    private ImageLoader mImageLoader;


    public RecipeListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        View rootView = inflater.inflate(R.layout.fragment_recipelist, container, false);
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.general_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mErrorTextView = (TextView)rootView.findViewById(R.id.item_notfound_error_text);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        ValueEventListener recipeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mErrorTextView.setText("");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Tag", "loadRecipe:onCancelled", databaseError.toException());
                Toast.makeText(getActivity(), "Failed to load recipes.",
                        Toast.LENGTH_SHORT).show();
                mErrorTextView.setText("No Recipes found");

            }
        };

        mRecipeListener = recipeListener;
        mDatabase.addValueEventListener(recipeListener);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mManager);
        mImageLoader = new ImageLoader(getActivity(), R.drawable.io_logo);
        RecipeListActivity activity = (RecipeListActivity)getActivity();
        mRecipeAdapter = new RecipeAdapter(activity,activity.getFirebaseAnalytics(), mDatabase, mImageLoader);
        mRecyclerView.setAdapter(mRecipeAdapter);

    }

    @Override
    public void onStop() {
        super.onStop();

        if (mRecipeListener != null) {
            mDatabase.removeEventListener(mRecipeListener);
        }

        mRecipeAdapter.cleanupListener();
    }
}
