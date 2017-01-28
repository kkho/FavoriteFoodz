package com.kkhoisawesome.favoritefoodz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.kkhoisawesome.favoritefoodz.R;
import com.kkhoisawesome.favoritefoodz.framework.model.Recipe;
import com.kkhoisawesome.favoritefoodz.util.ImageLoader;
import com.kkhoisawesome.favoritefoodz.viewholder.RecipeViewHolder;

import java.util.ArrayList;

/**
 * Created by kkho on 16.01.2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    private Context mContext;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private final ImageLoader mImageLoader;

    private ArrayList<Recipe> mRecipeList = new ArrayList<>();
    private ArrayList<String> mRecipeIds = new ArrayList<>();
    private static final String TAG = "RecipeAdapter";

    public RecipeAdapter(final Context context, DatabaseReference ref, @NonNull ImageLoader imageLoader) {
        mContext = context;
        mDatabaseReference = ref;
        mImageLoader = imageLoader;

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                Recipe recipe = dataSnapshot.getValue(Recipe.class);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

/*
                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                Comment newComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // [START_EXCLUDE]
                int commentIndex = mCommentIds.indexOf(commentKey);
                if (commentIndex > -1) {
                    // Replace with the new data
                    mComments.set(commentIndex, newComment);

                    // Update the RecyclerView
                    notifyItemChanged(commentIndex);
                } else {
                    Log.w(TAG, "onChildChanged:unknown_child:" + commentKey);
                }
                // [END_EXCLUDE] */
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();
                /*
                int commentIndex = mCommentIds.
                        /*indexOf(commentKey);
                if (commentIndex > -1) {
                    // Remove data from the list
                    mCommentIds.remove(commentIndex);
                    mComments.remove(commentIndex);

                    // Update the RecyclerView
                    notifyItemRemoved(commentIndex);
                } else {
                    Log.w(TAG, "onChildRemoved:unknown_child:" + commentKey);
                }
                // [END_EXCLUDE] */
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postRecipes:onCancelled", databaseError.toException());
                Toast.makeText(mContext, "Failed to load recipes.",
                        Toast.LENGTH_SHORT).show();
            }
        };

        ref.addChildEventListener(childEventListener);
        mChildEventListener = childEventListener;


    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_recipeitem, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        final Recipe recipe = mRecipeList.get(position);
        mImageLoader.loadImage(recipe.image_url, holder.vRecipeImage);
        holder.vTitleText.setText(recipe.title);

    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public void cleanupListener() {
        if (mChildEventListener != null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
        }
    }
}
