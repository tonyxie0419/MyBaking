package com.example.android.mybaking.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mybaking.R;
import com.example.android.mybaking.adapter.RecipeIngredientsAdapter;
import com.example.android.mybaking.data.Ingredient;

import java.util.ArrayList;

/**
 * Created by xie on 2017/12/29.
 */

public class RecipeIngredientsFragment extends Fragment {

    private ArrayList<Ingredient> ingredients;
    private RecyclerView mRecyclerView;
    private RecipeIngredientsAdapter mAdapter;

    public RecipeIngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);

        mRecyclerView = rootView.findViewById(R.id.rv_recipe_ingredients);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mAdapter = new RecipeIngredientsAdapter();
        if (ingredients != null) {
            mAdapter.setIngredientsData(ingredients);
        }
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
