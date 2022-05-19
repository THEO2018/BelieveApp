package com.netset.believeapp.Fragment.homeMenu;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.ServiceTimeModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_SERVICE_TIME;

/**
 * Created by netset on 9/1/18.
 */

public class ServiceTimeFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.divView)
    View divView;
    @BindView(R.id.believe_TV)
    AppCompatTextView believeTV;
    @BindView(R.id.timing_TV)
    AppCompatTextView timingTV;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.address_TV)
    AppCompatTextView addressTV;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.contact_TV)
    AppCompatTextView contactTV;
    @BindView(R.id.infoContainer)
    ConstraintLayout infoContainer;
    @BindView(R.id.follow_label_TV)
    AppCompatTextView followLabelTV;
    @BindView(R.id.fb_IV)
    AppCompatImageView fbIV;
    @BindView(R.id.twitter_IV)
    AppCompatImageView twitterIV;
    @BindView(R.id.insta_IV)
    AppCompatImageView instaIV;
    @BindView(R.id.parent)
    ConstraintLayout parent;
    Unbinder unbinder;

    ServiceTimeModel result;
    /*ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> getServiceTime;
    String FbLink,InstaLink,TwitterLink;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.service_time_fragment, null);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_SERVICE_TIME, false, false, false, null);
        unbinder = ButterKnife.bind(this, rootView);
        parent.setBackgroundResource(0);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/
        CallApi();
    }

    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getServiceTime = baseActivity.apiInterface.Get_ServiceTime(map);
        baseActivity.apiHitAndHandle.makeApiCall(getServiceTime, this, baseActivity);
    }


    @OnClick({R.id.fb_IV, R.id.twitter_IV, R.id.insta_IV})
    public void onClick(View view) {
        if (result!=null){
            switch (view.getId()) {
                case R.id.fb_IV:
                    if (appInstalledOrNot("com.facebook.katana")) {
                        String url = FbLink;
                        if (!url.startsWith("http://") && !url.startsWith("https://")){
                            url = "http://" + url;
                        }
                        Log.e(">>>>>>>webUrl is",">>>>>>>"+ Uri.parse(url));
                        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                        facebookIntent.setData(Uri.parse(url));


                        startActivity(facebookIntent);
                    }else{
                        String url = FbLink;
                        if (!url.startsWith("http://") && !url.startsWith("https://")&&url!=null){
                            url = "http://" + url;
                        }
                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));
                        // CommonDialogs.customToast(getActivity(),"Facebook is not installed on your device");
                    }
                    break;

                case R.id.twitter_IV:
                    if (appInstalledOrNot("com.twitter.android")) {

                        try {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_VIEW);
                            sendIntent.setPackage("com.twitter.android");

                            String url = TwitterLink;
                            if (!url.startsWith("http://") && !url.startsWith("https://")&&url!=null){
                                url = "http://" + url;
                            }
                            sendIntent.setData(Uri.parse(url));
                            startActivity(sendIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }else{
                        String url = TwitterLink;
                        if (!url.startsWith("http://") && !url.startsWith("https://")&&url!=null){
                            url = "http://" + url;
                        }
                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));
                        // CommonDialogs.customToast(getActivity(),"Twitter is not installed on your device");

                    }

                    break;
                case R.id.insta_IV:
                    if(appInstalledOrNot("com.instagram.android")) {
                        Intent likeIng = new Intent(Intent.ACTION_VIEW);
                        String url = InstaLink;
                        if (!url.startsWith("http://") && !url.startsWith("https://")&&url!=null){
                            url = "http://" + url;
                        }
                        likeIng.setData(Uri.parse(url));
                        likeIng.setPackage("com.instagram.android");
                        startActivity(likeIng);

                    }
                    else{
                        String url = InstaLink;
                        if (!url.startsWith("http://") && !url.startsWith("https://")&&url!=null){
                            url = "http://" + url;
                        }
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    }

                    break;



            }
        }
        else {
            showToast("Link not found");
        }

    }

    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), ServiceTimeModel.class);
        parent.setVisibility(View.VISIBLE);
        if (result!=null){
            timingTV.setText(result.getData().getTime());
            addressTV.setText(result.getData().getVenue());
            contactTV.setText(result.getData().getPhone());
            FbLink = result.getData().getFacebookLink();
            InstaLink = result.getData().getInstagramLink();
            TwitterLink = result.getData().getTwitterLink();
        }
        else {
            showToast("Service details not found");
        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
