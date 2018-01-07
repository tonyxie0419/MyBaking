package com.example.android.mybaking.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.android.mybaking.R;
import com.example.android.mybaking.adapter.RecipeDetailAdapter;
import com.example.android.mybaking.data.Recipe;

/**
 * Created by xie on 2017/12/28.
 */

public class RecipeDetailFragment extends Fragment {

    private static final String TAG = RecipeDetailFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecipeDetailAdapter mAdapter;

    private Recipe recipe;
    private boolean isTwoPane;

    public RecipeDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        mRecyclerView = rootView.findViewById(R.id.rv_recipe_detail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        if (recipe != null) {
            mAdapter = new RecipeDetailAdapter(recipe);
            mAdapter.setTwoPane(isTwoPane);
            mRecyclerView.setAdapter(mAdapter);
        }
        return rootView;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setTwoPane(boolean twoPane) {
        isTwoPane = twoPane;
    }
}
