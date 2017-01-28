package com.kkhoisawesome.favoritefoodz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kkhoisawesome.favoritefoodz.R;
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
        mDatabase = FirebaseDatabase.getInstance().getReference("favoritefoodz").child("recipes");
        View rootView = inflater.inflate(R.layout.fragment_recipelist, container, false);
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.general_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mManager);
        mImageLoader = new ImageLoader(getActivity(), R.drawable.io_logo);
        mRecipeAdapter = new RecipeAdapter(getActivity(), mDatabase, mImageLoader);
        mRecyclerView.setAdapter(mRecipeAdapter);

    }

    @Override
    public void onStop() {
        super.onStop();
        // Clean up comments listener
        mRecipeAdapter.cleanupListener();
    }
}
