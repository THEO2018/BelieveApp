package com.netset.believeapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.netset.believeapp.Utils.CommonDialogs;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class DownloadFileFromUrl extends AsyncTask<String, Integer, Void> {
    private Context context;
    private String filetype;

    int progress = 0;
    Notification notification;
    NotificationManager notificationManager;
    int id = 10;
    public DownloadFileFromUrl(Context context, String fileType) {
        this.context = context;
        this.filetype = fileType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        CommonDialogs.customToast(context, "Downloading start.");

    }

    @Override
    protected Void doInBackground(String... strings) {
        String fileUrl = strings[0];
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "Download");
        folder.mkdir();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        String fileName = formatter.format(now) + filetype;
        File pdfFile = new File(folder, fileName);
        try {
            pdfFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileDownloader.downloadFile(fileUrl, pdfFile);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        CommonDialogs.customToast(context, "Downloaded successfully.");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onProgressUpdate(Integer... progress) {
        notificationManager = (NotificationManager) context.getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel("id" , "channel_name", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(notificationChannel);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "id")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("title")
                .setContentText(Arrays.toString(progress));
        notificationManager.notify(id, builder.build());

    }

}
