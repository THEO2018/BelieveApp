package com.netset.believeapp.fcmServices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.Constants;
import com.netset.believeapp.activity.CouplesActivity;
import com.netset.believeapp.activity.ShowFullPostActivity;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


/**
 * Created by netset on 12/8/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Intent intent;
    int Notification_Id;
    Context context;
    String channelId="",channelName="",channelDescription="";
    SharedPreferences.Editor mEdit;
    SharedPreferences mPreferencees;
    PendingIntent pendingIntent;
    String lavel, name,id, message, type, otherid, count,sound;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        channelId=getPackageName();
        channelName=getPackageName();
        channelDescription=getApplicationContext().getString(R.string.app_name);
        Map<String, String> data = remoteMessage.getData();
        sendNotification(data);

    }

    private void sendNotification(Map<String, String> messageBody) {
        mPreferencees = getApplicationContext().getSharedPreferences("Believe", MODE_MULTI_PROCESS);
        mEdit = mPreferencees.edit();
            Constants.jsonObj = new JSONObject(messageBody);
            Log.e(">>>>>message body",">>>>>>>>"+messageBody);
        try {

            message = Constants.jsonObj.getString("body");
            lavel   = Constants.jsonObj.getString("title");
            name    = Constants.jsonObj.getString("first_name")+" "+Constants.jsonObj.getString("last_name");
            type    = Constants.jsonObj.getString("notification_type");
            sound   = Constants.jsonObj.getString("notification_sound");
            id      = Constants.jsonObj.getString("id");

            if(type.equals("liked") || type.equals("commented") || type.equals("posted")){
                Notification_Id=1;
                if (mPreferencees.getString("postbool", "").equals("true")) {
                    if (mPreferencees.getString("postId", "").equals(id)) {
                        Constants.Post_Id = id;
                        startActivity(new Intent(this, ShowFullPostActivity.class)
                                .putExtra("from","group")
                                .putExtra("status","true")
                                .putExtra("id",id));

                    }
                } else {
                    Constants.Post_Id = id;
                    intent = new Intent(this, ShowFullPostActivity.class)
                            .putExtra("from","group")
                            .putExtra("status","true")
                            .putExtra("id",id);
                    sendNotification();
                }

            }
            else if(type.equals("betrothed")){
                Notification_Id=2;
                if (mPreferencees.getString("couplebool", "").equals("true")) {
                        startActivity(new Intent(this, CouplesActivity.class));

                }
                else {
                    intent = new Intent(this, CouplesActivity.class);
                    sendNotification();
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void sendNotification() {
      //  Intent intent = new Intent(this, Receiver.class);
      //  intent.setAction("My Call");
        String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(message);
        int uniqueId = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
      //  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Resources res = this.getResources();
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {
           // The id of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            // Create a notification and set the notification channel.
            mChannel.setSound(null,null);
            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder)
                    new NotificationCompat.Builder(this,channelId)
                    .setContentTitle(lavel)
                    .setContentText(fromServerUnicodeDecoded)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.app_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.app_icon))
                    .setContentIntent(pendingIntent);
           if(sound.equalsIgnoreCase("OFF")){
               notificationBuilder.setSound(null,0);
           }else{
               notificationBuilder.setSound(defaultSoundUri);
           }

            notificationManager.createNotificationChannel(mChannel);
            Notification notification = notificationBuilder.build();
            notificationManager.notify(Notification_Id, notification);
        }
        else if (notificationManager != null) {
            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder)
                    new NotificationCompat.Builder(this,channelId)
                    .setContentTitle(lavel)
                    .setContentText(fromServerUnicodeDecoded)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.app_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.app_icon))
                    .setContentIntent(pendingIntent);
            if(sound.equalsIgnoreCase("OFF")){
                notificationBuilder.setSound(null,0);
            }else{
                notificationBuilder.setSound(defaultSoundUri);
            }
            // NotificationCompat.Builder notificationBuilder = getNotificationBuilder();
            notificationManager.notify(Notification_Id /* ID of notification */, notificationBuilder.build());
            //notificationManager.notify(Notification_Id /* ID of notification */, notificationBuilder.build());
        }
    }

}
