package com.netset.believeapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloader {
    private static final int  MEGABYTE = 1024 * 1024;

    public static Integer downloadFile(String fileUrl, File directory){
         int progress = 0;
        int sentData = 0;
        try {

            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            try (FileOutputStream fileOutputStream = new FileOutputStream(directory)) {
                int length = inputStream.available();
                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, bufferLength);
                    sentData += bufferLength;
                    progress = (int) ((sentData / (float) length) * 100);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return progress;
    }
}


