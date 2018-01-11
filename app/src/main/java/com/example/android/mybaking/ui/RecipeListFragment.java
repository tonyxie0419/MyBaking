package com.example.android.mybaking.ui;

import android.os.Bundle;
import android.os.Handler;
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
import com.example.android.mybaking.utilities.Constant;
import com.example.android.mybaking.utilities.OpenRecipeJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by xie on 2017/12/26.
 */

public class RecipeListFragment extends Fragment {

    private static final String TAG = RecipeListFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecipeListAdapter mAdapter;

    private ArrayList<Recipe> recipes;
    private Handler handler = null;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    public RecipeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        handler = new Handler();

        mErrorMessageDisplay = rootView.findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = rootView.findViewById(R.id.pb_loading_indicator);

        mRecyclerView = rootView.findViewById(R.id.rv_recipe_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecipeListAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mLoadingIndicator.setVisibility(View.VISIBLE);

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(Constant.RECIPE_REQUEST_URL)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showErrorMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonRecipeResponse = response.body().string();
                try {
                    recipes = OpenRecipeJsonUtils.getRecipesFromJson(jsonRecipeResponse);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mLoadingIndicator.setVisibility(View.INVISIBLE);
                        showRecipeDataView();
                            mAdapter.setRecipeData(recipes);
                    }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return rootView;
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
