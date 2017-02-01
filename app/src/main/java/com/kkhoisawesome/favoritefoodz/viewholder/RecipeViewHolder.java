package com.kkhoisawesome.favoritefoodz.viewholder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kkhoisawesome.favoritefoodz.R;
import com.kkhoisawesome.favoritefoodz.activity.RecipeContentActivity;
import com.kkhoisawesome.favoritefoodz.framework.model.Recipe;

/**
 * Created by kkho on 14.01.2017.
 */

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    public ImageView vRecipeImage;
    public TextView vTitleText;
    public Recipe vRecipeObject;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        vRecipeImage = (ImageView)itemView.findViewById(R.id.recipe_image);
        vTitleText = (TextView) itemView.findViewById(R.id.recipe_title);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RecipeContentActivity.class);
                intent.putExtra("RecipeContent", new Gson().toJson(vRecipeObject));
                view.getContext().startActivity(intent);
            }
        });
    }



}
