package com.netset.believeapp.fcmServices;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.netset.believeapp.Utils.Constants;


/**
 * Created by netset on 12/8/16.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public static String refreshedToken;
    Context context;
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if (refreshedToken != null) {
            sendRegistrationToServer(refreshedToken);
            Constants.DeviceId = refreshedToken;
            Log.e(TAG, "Refreshed token: >>>>>>>>>>>>>>>" + refreshedToken);
          // GeneralValues.set_device_id(context,FirebaseInstanceId.getInstance().getToken());

        }
    }

    private void sendRegistrationToServer(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Believe", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();


        editor.putString("Device_Id", token).commit();
        Log.d(TAG, "Refreshed token: " + token);
    }

}
