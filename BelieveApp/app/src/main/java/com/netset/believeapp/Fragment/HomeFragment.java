package com.netset.believeapp.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folioreader.FolioReader;
import com.folioreader.model.HighLight;
import com.folioreader.ui.base.OnSaveHighlight;
import com.folioreader.util.OnHighlightListener;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.HomeAdapter;
import com.netset.believeapp.Fragment.homeMenu.AboutUsFragment;
import com.netset.believeapp.Fragment.homeMenu.AppointmentFragment;
import com.netset.believeapp.Fragment.homeMenu.BlogsFragment;
import com.netset.believeapp.Fragment.homeMenu.ClassifiedFragment;
import com.netset.believeapp.Fragment.homeMenu.CommunityFragment;
import com.netset.believeapp.Fragment.homeMenu.ContactUsFragment;
import com.netset.believeapp.Fragment.homeMenu.DonationFragment;
import com.netset.believeapp.Fragment.homeMenu.EventsFragment;
import com.netset.believeapp.Fragment.homeMenu.MediaFragment;
import com.netset.believeapp.Fragment.homeMenu.MusicFragment;
import com.netset.believeapp.Fragment.homeMenu.NewsFragment;
import com.netset.believeapp.Fragment.homeMenu.NotesFragment;
import com.netset.believeapp.Fragment.homeMenu.ServiceTimeFragment;
import com.netset.believeapp.Fragment.homeMenu.SettingFragment;
import com.netset.believeapp.Fragment.homeMenu.StoreFragment;
import com.netset.believeapp.Fragment.homeMenu.WallFragment;
import com.netset.believeapp.Model.HighlightData;
import com.netset.believeapp.Model.HomeModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 9/1/18.
 */

public class HomeFragment extends BaseFragment implements ApiResponse, OnHighlightListener {

    int[] menuItemIcons = {R.drawable.ic_community,
            R.drawable.ic_event,
            R.drawable.ic_news,
            R.drawable.ic_wall,
            R.drawable.ic_music,
            R.drawable.ic_donation,
            R.drawable.ic_classified,
            R.drawable.ic_store,
            R.drawable.ic_blog,
            R.drawable.ic_bible,
            R.drawable.ic_about,
            R.drawable.ic_media,
            R.drawable.ic_note,
            R.drawable.ic_setting,
            R.drawable.ic_service_time,
            R.drawable.ic_appointment,
            R.drawable.ic_contact};

    String[] homeItemName;
    ArrayList<HomeModel> homeItemList;
    HomeAdapter homeAdapter;

    Call<JsonObject> getBible;
    String bibleUrl;
    private FolioReader folioReader;
    Unbinder unbinder;
    @BindView(R.id.homeMenu_GV)
    GridView homeMenu_GV;
    private ProgressDialog pDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        folioReader = FolioReader.get().setOnHighlightListener(this);
        ((HomeActivity) getActivity()).setToolbarTitle("", false, false, true, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pDialog = new ProgressDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        pDialog.setMessage("Loading..");
        pDialog.setCancelable(false);
        setHomeMenu();
    }


    private void setHomeMenu() {
        homeItemList = new ArrayList<>();
        homeItemName = getResources().getStringArray(R.array.home_item_names);
        for (int i = 0; i < homeItemName.length; i++) {
            HomeModel model = new HomeModel();
            model.setTitle(homeItemName[i]);
            model.setMenuIcon(menuItemIcons[i]);
            homeItemList.add(model);
        }
        homeAdapter = new HomeAdapter(baseActivity, homeItemList);
        homeMenu_GV.setAdapter(homeAdapter);
        homeMenu_GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(position);
            }
        });

    }

    /**
     * Method for handling click of items in Recycler view...
     *
     * @param position
     */
    private void displayView(int position) {
        switch (position) {
            case 0:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new CommunityFragment());
                break;
            case 1:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new EventsFragment());
                break;

            case 2:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new NewsFragment());
                break;
            case 3:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new WallFragment());
                break;

            case 4:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new MusicFragment());
                break;
            case 5:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new DonationFragment());
                break;
            case 6:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new ClassifiedFragment());
                break;

            case 7:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new StoreFragment());
                break;

            case 8:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new BlogsFragment());
                break;

            case 9:
                CallApi();
                //baseActivity.navigateFragmentTransaction(R.id.homeContainer, new OnlIneBibleFragment());
                break;
            case 10:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new AboutUsFragment());
                break;
            case 11:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new MediaFragment());
                break;
            case 12:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new NotesFragment());
                break;
            case 13:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new SettingFragment());
                break;
            case 14:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new ServiceTimeFragment());
                break;
            case 15:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new AppointmentFragment());
                break;
            case 16:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new ContactUsFragment());
                break;
        }
    }


    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getBible =  baseActivity.apiInterface.Get_Bible(map);
        baseActivity.apiHitAndHandle.makeApiCall(getBible, this);

    }

    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());

        try {
            JSONObject jsonObject = new JSONObject(object.toString());
            JSONObject jSon = jsonObject.getJSONObject("data");
            bibleUrl = jSon.getString("bible_url");

            File dir = new File(Environment.getExternalStorageDirectory() + "/myfile.epub");

            if (bibleUrl.contains(".pdf")) {
                CommonDialogs.customToast(getActivity(), "There is a problem while opening Bible, Please try again later.");
            } else {
                if (dir.exists()) {
                    folioReader.openBook(dir.getAbsolutePath());
                } else {
                  //  new DownloadFile().execute(bibleUrl, "/myfile.epub");
                      new DownloadTask(getActivity()).execute(bibleUrl);
                }
            }

            getHighlightsAndSave();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }


    private void getHighlightsAndSave() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<HighLight> highlightList = new ArrayList<>();
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    if (highlightList.size() > 0) {
                        highlightList = objectMapper.readValue(loadAssetTextAsString("highlights/highlights_data.json"),
                                new TypeReference<List<HighlightData>>() {
                                });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (highlightList == null) {
                    folioReader.saveReceivedHighLights(highlightList, new OnSaveHighlight() {
                        @Override
                        public void onFinished() {
                            //You can do anything on successful saving highlight list
                        }
                    });
                }
            }
        }).start();
    }


    private String loadAssetTextAsString(String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = getActivity().getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ((str = in.readLine()) != null) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            Log.e("HomeActivity", "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("HomeActivity", "Error closing asset " + name);
                }
            }
        }
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        FolioReader.clear();
    }

    @Override
    public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {

    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showpDialog();
        }


        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream("/sdcard/myfile.epub");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            hidepDialog();

            File dir = new File(Environment.getExternalStorageDirectory() + "/myfile.epub");

            if (bibleUrl.contains(".pdf")) {
                CommonDialogs.customToast(getActivity(), "There is a problem while opening Bible, Please try again later.");
            } else {
                if (dir.exists()) {
                    folioReader.openBook(dir.getAbsolutePath());
                } else {
                   // new DownloadTask(getActivity()).execute(bibleUrl);
                }
            }

        }
    }
}
