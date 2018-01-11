package com.example.android.mybaking.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mybaking.R;
import com.example.android.mybaking.data.Recipe;
import com.example.android.mybaking.ui.DetailActivity;

import java.util.ArrayList;

/**
 * Created by xie on 2017/12/28.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListAdapterViewHolder> {

    private static final String TAG = RecipeListAdapter.class.getSimpleName();

    private ArrayList<Recipe> recipes;

    private Context mContext;

    public RecipeListAdapter(Context context) {
        mContext = context;
    }

    public class RecipeListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;

        public RecipeListAdapterViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_recipe_name);
            mTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Recipe recipe = recipes.get(position);
            DetailActivity.startAction(mContext,recipe);
        }
    }

    @Override
    public RecipeListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeListAdapterViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecipeListAdapterViewHolder holder, int position) {
        holder.mTextView.setText(recipes.get(position).getRecipeName());
    }

    @Override
    public int getItemCount() {
        if (null == recipes) return 0;
        return recipes.size();
    }

    public void setRecipeData(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }
}
