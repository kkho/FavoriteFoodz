package com.kkhoisawesome.favoritefoodz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.kkhoisawesome.favoritefoodz.framework.model.Recipe;
import com.kkhoisawesome.favoritefoodz.viewholder.RecipeViewHolder;

import java.util.ArrayList;

/**
 * Created by kkho on 16.01.2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    private Context mContext;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    private ArrayList<Recipe> mRecipeList;

    public RecipeAdapter(final Context context, DatabaseReference ref) {
        mContext = context;
        mDatabaseReference = ref;




    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
