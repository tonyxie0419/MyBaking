package com.example.android.mybaking.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.example.android.mybaking.R;
import com.example.android.mybaking.adapter.RecipeDetailAdapter;
import com.example.android.mybaking.data.Ingredient;
import com.example.android.mybaking.data.Step;

import java.util.ArrayList;

/**
 * Created by xie on 2017/12/28.
 */

public class DetailInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = DetailInfoActivity.class.getSimpleName();

    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private int stepIndex;
    private String recipeName;
    private FragmentManager fragmentManager;

    private Button btnPre;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        fragmentManager = getSupportFragmentManager();
        ActionBar actionBar = getSupportActionBar();

        btnPre = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ingredients = bundle.getParcelableArrayList(RecipeDetailAdapter.TRANS_INGREDIENTS);
            steps = bundle.getParcelableArrayList(RecipeDetailAdapter.TRANS_STEPS);
            stepIndex = bundle.getInt(RecipeDetailAdapter.TRANS_STEP_INDEX);
            recipeName = bundle.getString(RecipeDetailAdapter.TRANS_RECIPE_NAME);
            if (actionBar != null) {
                actionBar.setTitle(recipeName);
            }
            if (ingredients != null) {
                RecipeIngredientsFragment newRecipeIngredientsFragment = new RecipeIngredientsFragment();
                btnPre.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
                newRecipeIngredientsFragment.setIngredients(ingredients);
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_detail_info_container, newRecipeIngredientsFragment)
                        .commit();
            } else if (steps != null) {
                RecipeDetailInfoFragment newRecipeDetailInfoFragment = new RecipeDetailInfoFragment();
                btnPre.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
                newRecipeDetailInfoFragment.setStepIndex(stepIndex);
                newRecipeDetailInfoFragment.setSteps(steps);
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_detail_info_container, newRecipeDetailInfoFragment)
                        .commit();
            }
        }

        btnPre.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_previous:
                if (stepIndex > 0) {
                    stepIndex--;
                    RecipeDetailInfoFragment newFragment = new RecipeDetailInfoFragment();
                    newFragment.setStepIndex(stepIndex);
                    newFragment.setSteps(steps);
                    fragmentManager.beginTransaction()
                            .replace(R.id.recipe_detail_info_container, newFragment)
                            .commit();
                }
                break;
            case R.id.btn_next:
                if (stepIndex < steps.size() - 1) {
                    stepIndex++;
                    RecipeDetailInfoFragment newFragment = new RecipeDetailInfoFragment();
                    newFragment.setStepIndex(stepIndex);
                    newFragment.setSteps(steps);
                    fragmentManager.beginTransaction()
                            .replace(R.id.recipe_detail_info_container, newFragment)
                            .commit();
                }
                break;
        }
    }
}
