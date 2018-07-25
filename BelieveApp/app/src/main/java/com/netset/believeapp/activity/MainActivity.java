package com.netset.believeapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.netset.believeapp.R;

/**
 * Created by netset on 8/1/18.
 */

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
