package com.example.android.mybaking.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.mybaking.R;
import com.example.android.mybaking.adapter.RecipeListAdapter;
import com.example.android.mybaking.data.Recipe;
import com.example.android.mybaking.sync.AsyncTaskCompleteListener;
import com.example.android.mybaking.sync.FetchRecipeDataTask;

import java.util.ArrayList;


/**
 * Created by xie on 2017/12/26.
 */

public class RecipeListFragment extends Fragment implements AsyncTaskCompleteListener {

    private static final String TAG = RecipeListFragment.class.getSimpleName();

    private final static String RECIPE_REQUEST_URL =
            "https://s3.cn-north-1.amazonaws.com.cn/static-documents/nd801/ProjectResources/Baking/baking-cn.json";

    private RecyclerView mRecyclerView;
    private RecipeListAdapter mAdapter;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    public RecipeListFragment() {
        new FetchRecipeDataTask(this).execute(RECIPE_REQUEST_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        mErrorMessageDisplay = rootView.findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = rootView.findViewById(R.id.pb_loading_indicator);

        mRecyclerView = rootView.findViewById(R.id.rv_recipe_list);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecipeListAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        return rootView;
    }

    @Override
    public void onTaskComplete(ArrayList<Recipe> result) {
        if (result != null) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            showRecipeDataView();
            mAdapter.setRecipeData(result);
        } else {
            showErrorMessage();
        }
    }

    private void showRecipeDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
