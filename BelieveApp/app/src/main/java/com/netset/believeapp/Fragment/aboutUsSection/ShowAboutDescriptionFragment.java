package com.netset.believeapp.Fragment.aboutUsSection;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.AboutUsDetailModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 25/1/18.
 */

public class ShowAboutDescriptionFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.catImage_IV)
    ImageView catImageIV;
    @BindView(R.id.catName_TV)
    TextView catNameTV;
    @BindView(R.id.catText_TV)
    TextView catTextTV;
    @BindView(R.id.catText_View)
    LinearLayout catTextView;
    @BindView(R.id.fbShare_IV)
    ImageView fbShareIV;
    @BindView(R.id.twitterShare_IV)
    ImageView twitterShareIV;
    @BindView(R.id.emailShare_IV)
    ImageView emailShareIV;
    @BindView(R.id.smsShare_IV)
    ImageView smsShareIV;
    @BindView(R.id.whatsShare_IV)
    ImageView whatsShareIV;
    Unbinder unbinder;
    String About_Text,About_Title;

    AboutUsDetailModel result;
    Call<JsonObject> getAboutUsDetail;
    ShareDialog shareDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.show_about_description_fragment, null);
        FacebookSdk.sdkInitialize(this.getActivity());
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle("Presentation", true, false, false, null);
        catTextTV.setTextIsSelectable(true);
        CallApi();
        return rootView;
    }

    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        Bundle b = getArguments();
        if(b!=null) {
            map.put("about_page_id",b.getString("aboutid"));
        }
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getAboutUsDetail = baseActivity.apiInterface.Get_About_Detail(map);
        baseActivity.apiHitAndHandle.makeApiCall(getAboutUsDetail, this);

    }


    @OnClick({R.id.fbShare_IV, R.id.twitterShare_IV, R.id.emailShare_IV, R.id.smsShare_IV, R.id.whatsShare_IV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fbShare_IV:
                if (appInstalledOrNot("com.facebook.katana")) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.setPackage("com.facebook.katana");
                    sharingIntent.putExtra(Intent.EXTRA_TITLE, About_Title);
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, About_Text);
                    startActivity(sharingIntent);

                }else{
                    CommonDialogs.customToast(getActivity(),"Facebook is not installed on your Device");
                }
                break;

            case R.id.twitterShare_IV:

                if (appInstalledOrNot("com.twitter.android")) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.twitter.android");
                    sendIntent.putExtra(Intent.EXTRA_TITLE,About_Title);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, About_Text);
                    startActivity(sendIntent);

                }else{
                    CommonDialogs.customToast(getActivity(),"Twitter is not installed on your device");

                }
                break;

            case R.id.emailShare_IV:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto: abc@xyz.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, About_Title);
                emailIntent.putExtra(Intent.EXTRA_TEXT, About_Text);
                startActivity(Intent.createChooser(emailIntent, About_Text));
                break;

            case R.id.smsShare_IV:
                Intent message = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + "" ) );
                message.putExtra( "sms_body", About_Text);
                startActivity(message);
                break;

            case R.id.whatsShare_IV:

                if (appInstalledOrNot("com.whatsapp")) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.setPackage("com.whatsapp");
                    shareIntent.putExtra(Intent.EXTRA_TITLE,About_Title);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, About_Text);
                    startActivity(shareIntent);

                }else{
                    CommonDialogs.customToast(getActivity(), "Whatsapp is not installed on your Device");
                }
                break;
        }
    }



    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), AboutUsDetailModel.class);
        CommonDialogs.getSquareImage(getActivity(),result.getData().getAboutCoverImage(),catImageIV);
        catNameTV.setText(result.getData().getTitle());
        catTextTV.setText(result.getData().getDescription());
        About_Text = result.getSocialContent().getContent()+"\n     "+result.getData().getDescription();
        About_Title = result.getData().getTitle();
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
