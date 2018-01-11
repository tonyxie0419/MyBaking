package com.example.android.mybaking.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mybaking.R;
import com.example.android.mybaking.data.Ingredient;
import com.example.android.mybaking.data.Recipe;
import com.example.android.mybaking.data.Step;
import com.example.android.mybaking.ui.DetailInfoActivity;

import java.util.ArrayList;

/**
 * Created by xie on 2017/12/28.
 */

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailAdapterViewHolder> {

    private static final String TAG = RecipeDetailAdapter.class.getSimpleName();

    private Recipe mRecipe;

    private Context context;
    private boolean mTwoPane = false;

    OnDetailItemClickListener mCallback;

    public static final String TRANS_INGREDIENTS = "ingredients";

    public static final String TRANS_STEP_INDEX = "step_index";
    public static final String TRANS_STEPS = "steps";

    public static final String TRANS_RECIPE_NAME = "recipe_name";

    public RecipeDetailAdapter(Recipe recipe) {
        mRecipe = recipe;
    }

    public interface OnDetailItemClickListener {
        void onItemClick(int position);
    }

    public class RecipeDetailAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;

        public RecipeDetailAdapterViewHolder(View itemView) {
            super(itemView);
            if (itemView.getContext() instanceof OnDetailItemClickListener) {
                mCallback = (OnDetailItemClickListener) itemView.getContext();
            }
            mTextView = itemView.findViewById(R.id.tv_recipe_detail_info);
            mTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (mTwoPane) {
                if (mCallback != null) {
                    mCallback.onItemClick(position);
                }
            } else {
                Intent intent = new Intent(context, DetailInfoActivity.class);
                Bundle bundle = new Bundle();
                if (position == 0) {
                    ArrayList<Ingredient> ingredients = mRecipe.getIngredients();
                    bundle.putParcelableArrayList(TRANS_INGREDIENTS, ingredients);
                } else {
                    //因为第一个位置给了ingredients，所以第二个位置才是步骤的第一步
                    int stepIndex = position - 1;
                    ArrayList<Step> steps = mRecipe.getSteps();
                    bundle.putInt(TRANS_STEP_INDEX, stepIndex);
                    bundle.putParcelableArrayList(TRANS_STEPS, steps);
                }
                String recipeName = mRecipe.getRecipeName();
                bundle.putString(TRANS_RECIPE_NAME, recipeName);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        }
    }

    @Override
    public RecipeDetailAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recipe_detail_item, parent, false);
        return new RecipeDetailAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeDetailAdapterViewHolder holder, int position) {
        if (position == 0) {
            holder.mTextView.setText(context.getString(R.string.recipe_ingredients));
        } else {
            holder.mTextView.setText(mRecipe.getSteps().get(position - 1).getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        return mRecipe.getSteps().size() + 1;
    }

    public void setTwoPane(boolean isTwoPane) {
        mTwoPane = isTwoPane;
    }

}
