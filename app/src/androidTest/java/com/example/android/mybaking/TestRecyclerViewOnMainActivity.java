package com.example.android.mybaking;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.mybaking.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by xie on 2018/1/11.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestRecyclerViewOnMainActivity {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRecyclerView() throws Exception {
        onView(withId(R.id.rv_recipe_list))
                .perform(actionOnItemAtPosition(0, click()));
    }
}
