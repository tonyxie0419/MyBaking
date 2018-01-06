package com.example.android.mybaking.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mybaking.R;
import com.example.android.mybaking.data.Ingredient;

import java.util.ArrayList;

/**
 * Created by xie on 2018/1/2.
 */

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.RecipeIngredientsAdapterViewHolder> {

    private ArrayList<Ingredient> ingredients;

    public class RecipeIngredientsAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView quantityDisplay;
        private TextView measureDisplay;
        private TextView ingredientsDisplay;

        public RecipeIngredientsAdapterViewHolder(View itemView) {
            super(itemView);
            quantityDisplay = itemView.findViewById(R.id.tv_quantity_display);
            measureDisplay = itemView.findViewById(R.id.tv_measure_display);
            ingredientsDisplay = itemView.findViewById(R.id.tv_ingredient_display);
        }
    }

    @Override
    public RecipeIngredientsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_ingredients_item, parent, false);
        return new RecipeIngredientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeIngredientsAdapterViewHolder holder, int position) {
        holder.quantityDisplay.setText(ingredients.get(position).getQuantity());
        holder.measureDisplay.setText(ingredients.get(position).getMeasure());
        holder.ingredientsDisplay.setText(ingredients.get(position).getIngredient());
    }

    @Override
    public int getItemCount() {
        if (null == ingredients) return 0;
        return ingredients.size();
    }

    public void setIngredientsData(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }
}
