package com.example.android.mybaking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.mybaking.R;
import com.example.android.mybaking.adapter.RecipeDetailAdapter;
import com.example.android.mybaking.data.Step;
import com.example.android.mybaking.ui.DetailInfoActivity;

import java.util.ArrayList;

/**
 * Created by xie on 2018/1/4.
 */

public class BakingWidgetProvider extends AppWidgetProvider {

    private static final String TAG = BakingWidgetProvider.class.getSimpleName();

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       ArrayList<Step> steps, int stepIndex, int appWidgetId) {
        Intent intent = new Intent(context, DetailInfoActivity.class);
        if (steps != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(RecipeDetailAdapter.TRANS_STEPS, steps);
            bundle.putInt(RecipeDetailAdapter.TRANS_STEP_INDEX, stepIndex);
            intent.putExtras(bundle);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        if (steps != null) {
            views.setTextViewText(R.id.widget_recipe_step_info, steps.get(stepIndex).getDescription());
            views.setOnClickPendingIntent(R.id.widget_recipe_step_info, pendingIntent);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateAppwidgets(Context context, AppWidgetManager appWidgetManager,
                                        ArrayList<Step> steps, int stepIndex, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, steps, stepIndex, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate: ");
    }
}
