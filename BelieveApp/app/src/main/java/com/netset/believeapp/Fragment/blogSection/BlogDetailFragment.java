package com.netset.believeapp.Fragment.blogSection;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.BlogDetailModel;
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

public class BlogDetailFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.blogImage_IV)
    ImageView blogImageIV;
    @BindView(R.id.blogName_TV)
    TextView blogNameTV;
    @BindView(R.id.blogAuthor_TV)
    TextView blogAuthorTV;
    @BindView(R.id.blogText_TV)
    TextView blogTextTV;
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
    Call<JsonObject> getBlogDetail;
    BlogDetailModel result;
    String BlogText = "", BlogTitle = "";
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.blog_detail_fragment, null);
        callbackManager = CallbackManager.Factory.create();
        unbinder = ButterKnife.bind(this, rootView);

        blogTextTV.setTextIsSelectable(true);
        CallApi();
        shareDialog = new ShareDialog(this);
        // this part is optional
        return rootView;
    }

    public void CallApi() {

        HashMap<String, String> map = new HashMap<String, String>();
        Bundle b = getArguments();
        if(b!=null) {
            map.put("blog_id",b.getString("blogid"));
        }
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getBlogDetail = baseActivity.apiInterface.GetBlogDetail(map);
        baseActivity.apiHitAndHandle.makeApiCall(getBlogDetail, this);

    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick({R.id.fbShare_IV, R.id.twitterShare_IV, R.id.emailShare_IV, R.id.smsShare_IV, R.id.whatsShare_IV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fbShare_IV:


                if (appInstalledOrNot("com.facebook.katana")) {

                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse("https://facebook.com/believe"))
                                .setQuote(BlogText)
                                .build();
                        shareDialog.show(content);
                    }

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
                    sendIntent.putExtra(Intent.EXTRA_TITLE,BlogTitle);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, BlogText);
                    startActivity(sendIntent);

                }else{
                    CommonDialogs.customToast(getActivity(),"Twitter is not installed on your device");

                }
                break;

            case R.id.emailShare_IV:

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto: abc@xyz.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, BlogTitle);
                emailIntent.putExtra(Intent.EXTRA_TEXT, BlogText);
                startActivity(Intent.createChooser(emailIntent, BlogText));

                break;

            case R.id.smsShare_IV:

                Intent message = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + "" ) );
                message.putExtra( "sms_body", BlogText);
                startActivity(message);

                break;

            case R.id.whatsShare_IV:

                if (appInstalledOrNot("com.whatsapp")) {

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.setPackage("com.whatsapp");
                    shareIntent.putExtra(Intent.EXTRA_TITLE,BlogTitle);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, BlogText);
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
        result = gson.fromJson(object.toString(), BlogDetailModel.class);

        CommonDialogs.getSquareImage(getActivity(),result.getData().getBlogImage(),blogImageIV);
        blogNameTV.setText(result.getData().getBlogTitle());
        blogTextTV.setText(Html.fromHtml(Html.fromHtml(result.getData().getBlog()).toString()));
        if (result.getSocialContent()!=null){
            BlogText = result.getSocialContent().getContent()+"\n "+(Html.fromHtml(Html.fromHtml(result.getData().getBlog()).toString()));
            BlogTitle = result.getData().getBlogTitle();
        }

        ((HomeActivity) getActivity()).setToolbarTitle(BlogTitle, true, false, false, null);
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
