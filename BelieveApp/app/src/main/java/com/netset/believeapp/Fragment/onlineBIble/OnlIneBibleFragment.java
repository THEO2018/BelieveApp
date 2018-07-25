package com.netset.believeapp.Fragment.onlineBIble;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 25/1/18.
 */

public class OnlIneBibleFragment extends BaseFragment implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener, ApiResponse {

    Unbinder unbinder;
    @BindView(R.id.pdfView)
    WebView pdfView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;





    Call<JsonObject> getBible;
    private ProgressDialog pDialog;
    String bibleUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.online_bible_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle("Bible", false, false, false, null);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         CallApi();

    }


    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getBible =  baseActivity.apiInterface.Get_Bible(map);
        baseActivity.apiHitAndHandle.makeApiCall(getBible, this);

    }




    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    public void onPageError(int page, Throwable t) {

    }

    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());

        try {
            JSONObject jsonObject = new JSONObject(object.toString());
            JSONObject jSon = jsonObject.getJSONObject("data");
            bibleUrl = jSon.getString("bible_url");

           /* Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bibleUrl));
            startActivity(browserIntent);*/


            String url = "http://docs.google.com/gview?embedded=true&url=" + bibleUrl;
            String doc="<iframe src='"+url+"' width='100%' height='100%' style='border: none;'></iframe>";

            pdfView.getSettings().setJavaScriptEnabled(true);
            pdfView.getSettings().setJavaScriptEnabled(true);
            pdfView.getSettings().setAllowFileAccess(true);
            pdfView.loadData(doc, "text/html", "UTF-8");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }


    /**
     * Background Async Task to download file
     */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // progressBar.setVisibility(View.VISIBLE);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream
                OutputStream output = new FileOutputStream("/sdcard/downloadedfile.pdf");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {

            String file = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.pdf";
            File internalFile = new File(file);
            Uri internal = Uri.fromFile(internalFile);

          /*  try {
                displayFromURI(internal);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
//            progressBar.setVisibility(View.GONE);
            pDialog.dismiss();
        }
    }

}

