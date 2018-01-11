package com.example.android.mybaking.utilities;


import com.example.android.mybaking.data.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by xie on 2017/12/26.
 */

public class OpenRecipeJsonUtils {

    private static final String TAG = OpenRecipeJsonUtils.class.getSimpleName();

    public static ArrayList<Recipe> getRecipesFromJson(String recipeJsonStr) throws JSONException {
        Gson gson = new Gson();
        return gson.fromJson(recipeJsonStr, new TypeToken<ArrayList<Recipe>>() {
        }.getType());
    }
}
