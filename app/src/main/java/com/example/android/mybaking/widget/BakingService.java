package com.example.android.mybaking.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;

import com.example.android.mybaking.adapter.RecipeDetailAdapter;
import com.example.android.mybaking.data.Step;

import java.util.ArrayList;


public class BakingService extends IntentService {

    private static final String TAG = BakingService.class.getSimpleName();

    private static final String ACTION_UPDATE_BAKING_WIDGETS = "com.example.android.mybaking.action.update_baking_widgets";

    public BakingService() {
        super("BakingService");
    }

    public static void startActionUpdateBakingWidgets(Context context, ArrayList<Step> steps, int stepIndex) {
        Intent intent = new Intent(context, BakingService.class);
        intent.setAction(ACTION_UPDATE_BAKING_WIDGETS);
        intent.putExtra(RecipeDetailAdapter.TRANS_STEPS, steps);
        intent.putExtra(RecipeDetailAdapter.TRANS_STEP_INDEX, stepIndex);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_BAKING_WIDGETS.equals(action)) {
                ArrayList<Step> steps = intent.getParcelableArrayListExtra(RecipeDetailAdapter.TRANS_STEPS);
                int stepIndex = intent.getIntExtra(RecipeDetailAdapter.TRANS_STEP_INDEX, -1);
                if (stepIndex != -1) {
                    handleActionUpdateBakingWidgets(steps, stepIndex);
                }
            }
        }
    }

    private void handleActionUpdateBakingWidgets(ArrayList<Step> steps, int stepIndex) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
        BakingWidgetProvider.updateAppwidgets(this, appWidgetManager, steps, stepIndex, appWidgetIds);
    }
}
