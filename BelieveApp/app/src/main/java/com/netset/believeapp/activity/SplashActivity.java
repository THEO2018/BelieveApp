package com.netset.believeapp.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;

/**
 * Created by netset on 8/1/18.
 */

public class SplashActivity extends BaseActivity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_splash);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            Double dv = Double.parseDouble(version);
            GeneralValues.set_appVersion(SplashActivity.this,""+version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        handler.postDelayed(runnable, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if(GeneralValues.get_loginbool(SplashActivity.this)){
                startActivity(new Intent(SplashActivity.this, HomeActivity.class).putExtra("From","splash"));
                finish();
            }else{
                startActivity(new Intent(SplashActivity.this, UserAuthenticationActivity.class));
                finish();
            }

        }
    };
}
