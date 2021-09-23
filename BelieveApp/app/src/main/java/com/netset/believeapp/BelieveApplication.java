package com.netset.believeapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import androidx.multidex.MultiDex;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;
import com.netset.believeapp.retrofitManager.ApiHitAndHandle;
import com.netset.believeapp.retrofitManager.ApiInterface;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.twitter.sdk.android.core.Twitter;

/**
 * Created by netset on 1/2/18.
 */

public class BelieveApplication extends Application {
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!

    public static BelieveApplication mInstance;

   Context context;
    ApiHitAndHandle apiHitAndHandle;
    ApiInterface apiInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!

        mInstance = this;
        Twitter.initialize(this);
/*        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:994512197198:android:c526fcf6cc789e03") // Required for Analytics.
                .setApiKey("AIzaSyBbEFuWTgu8VEkOCyEA_1GvFMCR5GSVzVg") // Required for Auth.
                .build();
        FirebaseApp.initializeApp(this,options);*/
        Firebase.setAndroidContext(getApplicationContext());

        initImageLoader(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static BelieveApplication getInstance() {
        return mInstance;
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app_icon
                .build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
