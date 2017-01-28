package com.kkhoisawesome.favoritefoodz.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kkhoisawesome.favoritefoodz.R;

/**
 * Created by kkho on 14.01.2017.
 */

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    public ImageView vRecipeImage;
    public TextView vTitleText;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        vRecipeImage = (ImageView)itemView.findViewById(R.id.recipe_image);
        vTitleText = (TextView) itemView.findViewById(R.id.recipe_title);
    }


}
