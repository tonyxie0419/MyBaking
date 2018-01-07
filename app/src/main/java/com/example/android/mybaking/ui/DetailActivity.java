package com.example.android.mybaking.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.android.mybaking.R;
import com.example.android.mybaking.adapter.RecipeDetailAdapter;
import com.example.android.mybaking.data.Ingredient;
import com.example.android.mybaking.data.Recipe;
import com.example.android.mybaking.data.Step;

import java.util.ArrayList;

/**
 * Created by xie on 2017/12/28.
 */

public class DetailActivity extends AppCompatActivity implements RecipeDetailAdapter.OnDetailItemClickListener {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private boolean mTwoPane;
    private Recipe recipe;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private int stepIndex;
    private int currentPosition = 0;
    private FragmentManager fragmentManager;

    private final String TRANS_RECIPE = "recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        recipe = getIntent().getParcelableExtra(TRANS_RECIPE);

        if (findViewById(R.id.recipe_detail_info_container) != null) {
            mTwoPane = true;
            ingredients = recipe.getIngredients();
            steps = recipe.getSteps();

            fragmentManager = getSupportFragmentManager();
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setTwoPane(mTwoPane);
            if (recipe != null) {
                fragment.setRecipe(recipe);
                getSupportActionBar().setTitle(recipe.getRecipeName());
            }
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();

            if (currentPosition == 0) {
                RecipeIngredientsFragment newRecipeIngredientsFragment = new RecipeIngredientsFragment();
                newRecipeIngredientsFragment.setIngredients(ingredients);
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_detail_info_container, newRecipeIngredientsFragment)
                        .commit();
            } else {
                RecipeDetailInfoFragment newRecipeDetailInfoFragment = new RecipeDetailInfoFragment();
                newRecipeDetailInfoFragment.setStepIndex(stepIndex);
                newRecipeDetailInfoFragment.setSteps(steps);
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_detail_info_container, newRecipeDetailInfoFragment)
                        .commit();
            }
        } else {
            mTwoPane = false;
            fragmentManager = getSupportFragmentManager();
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setTwoPane(mTwoPane);
            if (recipe != null) {
                fragment.setRecipe(recipe);
                getSupportActionBar().setTitle(recipe.getRecipeName());
            }
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onItemClick(int position) {
        currentPosition = position;
        stepIndex = position - 1;
        if (currentPosition == 0) {
            RecipeIngredientsFragment newRecipeIngredientsFragment = new RecipeIngredientsFragment();
            newRecipeIngredientsFragment.setIngredients(ingredients);
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_info_container, newRecipeIngredientsFragment)
                    .commit();
        } else {
            RecipeDetailInfoFragment newRecipeDetailInfoFragment = new RecipeDetailInfoFragment();
            newRecipeDetailInfoFragment.setStepIndex(stepIndex);
            newRecipeDetailInfoFragment.setSteps(steps);
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_info_container, newRecipeDetailInfoFragment)
                    .commit();
        }
    }
}
