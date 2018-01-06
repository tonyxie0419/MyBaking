package com.example.android.mybaking.sync;

import com.example.android.mybaking.data.Recipe;

import java.util.ArrayList;

/**
 * Created by xie on 2017/12/26.
 */

public interface AsyncTaskCompleteListener {
    void onTaskComplete(ArrayList<Recipe> result);
}
