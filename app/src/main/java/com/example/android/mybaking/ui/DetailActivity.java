package com.example.android.mybaking.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.mybaking.R;
import com.example.android.mybaking.data.Recipe;

/**
 * Created by xie on 2017/12/28.
 */

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private final String TRANS_RECIPE = "recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Recipe recipe = getIntent().getParcelableExtra(TRANS_RECIPE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        if (recipe != null) {
            fragment.setRecipe(recipe);
            getSupportActionBar().setTitle(recipe.getRecipeName());
        }
        fragmentManager.beginTransaction()
                .add(R.id.recipe_detail_container, fragment)
                .commit();
    }
}
