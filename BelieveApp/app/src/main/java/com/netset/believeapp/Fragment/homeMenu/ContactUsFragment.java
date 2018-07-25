package com.netset.believeapp.Fragment.homeMenu;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.ContactModel;
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

import static com.netset.believeapp.Utils.Constants.SC_CONTACT_US;

/**
 * Created by netset on 10/1/18.
 */

public class ContactUsFragment extends BaseFragment implements ApiResponse {


    @BindView(R.id.divView)
    View divView;
    @BindView(R.id.believe_TV)
    AppCompatTextView believeTV;
    @BindView(R.id.countryName_TV)
    AppCompatTextView countryNameTV;
    @BindView(R.id.address_TV)
    AppCompatTextView addressTV;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.contact_TV)
    AppCompatTextView contactTV;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.email_TV)
    AppCompatTextView emailTV;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.website_TV)
    AppCompatTextView websiteTV;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.direction_TV)
    AppCompatTextView directionTV;
    @BindView(R.id.infoContainer)
    LinearLayout infoContainer;
    @BindView(R.id.follow_label_TV)
    AppCompatTextView followLabelTV;
    @BindView(R.id.fb_IV)
    AppCompatImageView fbIV;
    @BindView(R.id.twitter_IV)
    AppCompatImageView twitterIV;
    @BindView(R.id.insta_IV)
    AppCompatImageView instaIV;
    Unbinder unbinder;
    @BindView(R.id.parent)
    ConstraintLayout parent;

    ContactModel result;

    Call<JsonObject> getContactUs;
    String FbLink,InstaLink,TwitterLink;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_us_fragment, null);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_CONTACT_US, false, false, false, null);
        unbinder = ButterKnife.bind(this, rootView);
        parent.setBackgroundResource(0);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        CallApi();

    }

    public void CallApi() {

        HashMap<String, String> map = new HashMap<String, String>();

        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getContactUs = baseActivity.apiInterface.Get_Contacts_Detail(map);
        baseActivity.apiHitAndHandle.makeApiCall(getContactUs, this);

    }

    public Intent getFacebookIntent(String url) {

        PackageManager pm = getActivity().getPackageManager();
        Uri uri = Uri.parse(url);

        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://page/" + url);
            }
        }

        catch (PackageManager.NameNotFoundException ignored) {
        }

        return new Intent(Intent.ACTION_VIEW, uri);
    }



    @OnClick({R.id.fb_IV, R.id.twitter_IV, R.id.insta_IV,R.id.email_TV,R.id.website_TV,R.id.direction_TV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_IV:
                if (appInstalledOrNot("com.facebook.katana")) {
                    String url = FbLink;
                    if (!url.startsWith("http://") && !url.startsWith("https://")){
                        url = "http://" + url;
                    }
                    Log.e(">>>>>>>webUrl is",">>>>>>>"+Uri.parse(url));
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                    facebookIntent.setData(Uri.parse(url));


                    startActivity(facebookIntent);
                }else{
                    String url = FbLink;
                    if (!url.startsWith("http://") && !url.startsWith("https://")){
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
                        if (!url.startsWith("http://") && !url.startsWith("https://")){
                            url = "http://" + url;
                    }
                        sendIntent.setData(Uri.parse(url));
                        startActivity(sendIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else{
                    String url = TwitterLink;
                    if (!url.startsWith("http://") && !url.startsWith("https://")){
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
                    if (!url.startsWith("http://") && !url.startsWith("https://")){
                        url = "http://" + url;
                    }
                    likeIng.setData(Uri.parse(url));
                    likeIng.setPackage("com.instagram.android");
                    startActivity(likeIng);

                }
                else{
                    String url = InstaLink;
                    if (!url.startsWith("http://") && !url.startsWith("https://")){
                        url = "http://" + url;
                    }
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }

                break;

            case R.id.email_TV:

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto: "+result.getData().getMail()));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contact Us");
                startActivity(Intent.createChooser(intent, ""));

                /*Intent emailIntent = new Intent(Intent.ACTION_VIEW);
                emailIntent.setData(Uri.parse(result.getData().getMail()));
                getActivity().startActivity(Intent.createChooser(emailIntent, ""));*/
                break;

            case R.id.website_TV:
                Intent browserIntent = null;
                if(!result.getData().getWebsiteLink().contains("https://")){
                     browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+result.getData().getWebsiteLink()));
                }
               else{
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getData().getWebsiteLink()));
                }
                getActivity().startActivity(browserIntent);
                break;

            case R.id.direction_TV:

                Bundle args = new Bundle();
                args.putString("lat",result.getData().getLatitude());
                args.putString("lng",result.getData().getLongitude());
                args.putString("location",result.getData().getLocation());
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer,new MapFragment(),args);

                break;


        }
    }

    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), ContactModel.class);
        addressTV.setText(result.getData().getLocation());
        contactTV.setText(result.getData().getPhone());
        emailTV.setText(result.getData().getMail());
        websiteTV.setText(result.getData().getWebsiteLink());
        countryNameTV.setText(result.getData().getCountry());
        FbLink = result.getData().getFacebookLink();
        InstaLink = result.getData().getInstagramLink();
        TwitterLink = result.getData().getTwitterLink();
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
