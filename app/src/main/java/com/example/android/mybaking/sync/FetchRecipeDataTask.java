package com.example.android.mybaking.sync;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.mybaking.data.Recipe;
import com.example.android.mybaking.utilities.OpenRecipeJsonUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xie on 2017/12/26.
 */

public class FetchRecipeDataTask extends AsyncTask<String, Void, ArrayList<Recipe>> {

    private static final String TAG = FetchRecipeDataTask.class.getSimpleName();

    private AsyncTaskCompleteListener mListener;

    public FetchRecipeDataTask(AsyncTaskCompleteListener listener) {
        mListener = listener;
    }

    @Override
    protected ArrayList<Recipe> doInBackground(String... strings) {

        if (strings.length == 0) {
            return null;
        }

        String url = strings[0];
        try {
            URL requestUrl = new URL(url);
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            String jsonRecipeResponse = response.body().string();
            return OpenRecipeJsonUtils.getRecipesFromJson(jsonRecipeResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Recipe> recipes) {
        mListener.onTaskComplete(recipes);
    }
}
