package com.example.android.mybaking.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.mybaking.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(getString(R.string.main_title));
    }
}
