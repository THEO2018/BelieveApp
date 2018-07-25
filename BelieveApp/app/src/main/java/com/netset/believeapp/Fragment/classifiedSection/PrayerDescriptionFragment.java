package com.netset.believeapp.Fragment.classifiedSection;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.CommentAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.birthdaySection.SelectedUserProfileFragment;
import com.netset.believeapp.Fragment.communitySection.ShowPollCommentsFragment;
import com.netset.believeapp.GsonModel.PrayerDetailModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.Utils.recyclerCustomisation.SimpleDividerItemDecorationBlue;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 29/1/18.
 */

public class PrayerDescriptionFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.time_TV)
    TextView timeTV;
    @BindView(R.id.like_TV)
    TextView likeTV;
    @BindView(R.id.health_TV)
    TextView healthTV;
    @BindView(R.id.detail_TV)
    TextView detailTV;
    @BindView(R.id.prayertitle_TV)
    TextView prayertitleTV;
    /*  @BindView(R.id.location_TV)
      TextView locationTV;*/
    @BindView(R.id.commentheader_RV)
    RelativeLayout commentHeaderRV;
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
    @BindView(R.id.commentList_RV)
    RecyclerView commentList_RV;
    @BindView(R.id.comment_ET)
    EditText commentET;
    @BindView(R.id.send_IV)
    ImageView sendIV;
    @BindView(R.id.viewAllCmnt_TV)
    TextView viewAllCmntTV;
    @BindView(R.id.pray_BT)
    Button prayBT;
    @BindView(R.id.prayerdone_TV)
    TextView prayerdoneTV;
    @BindView(R.id.share_lay)
    LinearLayout shareLay;
    @BindView(R.id.comntView)
    RelativeLayout commentView;
    Unbinder unbinder;
    CommentAdapter commentAdapter;

    Call<JsonObject> GetPrayerDetail, AddComment, AnswerPrayer, PrayForother;
    PrayerDetailModel result;
    View rootView;
    String BlogText = "", BlogTitle = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView==null) {

            rootView = inflater.inflate(R.layout.prayer_description_fragment, null);
            ButterKnife.bind(this, rootView);
            CallApi();
        }
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  setCommentAdapter();
    }


    public void CallApi() {
        Bundle b = getArguments();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("prayer_id", b.getString("id"));
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetPrayerDetail = baseActivity.apiInterface.Get_Prayers_Detail(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetPrayerDetail, this);
    }


    @OnClick({R.id.fbShare_IV, R.id.twitterShare_IV, R.id.emailShare_IV, R.id.smsShare_IV, R.id.whatsShare_IV, R.id.send_IV, R.id.pray_BT})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fbShare_IV:


                if (appInstalledOrNot("com.facebook.katana")) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.setPackage("com.facebook.katana");
                    sharingIntent.putExtra(Intent.EXTRA_TITLE, BlogText);
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, BlogTitle);
                    startActivity(sharingIntent);

                } else {
                    CommonDialogs.customToast(getActivity(), "Facebook is not installed on your Device");
                    //  CommonDialogs.customToast(Feed_Detail.this, "Facebook is not installed on  your Device");
                }
                break;

            case R.id.twitterShare_IV:

                if (appInstalledOrNot("com.twitter.android")) {

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.twitter.android");
                    sendIntent.putExtra(Intent.EXTRA_TITLE, BlogTitle);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, BlogText);
                    startActivity(sendIntent);

                } else {
                    CommonDialogs.customToast(getActivity(), "Twitter is not installed on your device");

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

                Intent message = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + ""));
                message.putExtra("sms_body", BlogText);
                startActivity(message);


                break;
            case R.id.whatsShare_IV:
                if (appInstalledOrNot("com.whatsapp")) {

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.setPackage("com.whatsapp");
                    shareIntent.putExtra(Intent.EXTRA_TITLE, BlogTitle);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, BlogText);
                    startActivity(shareIntent);

                } else {
                    CommonDialogs.customToast(getActivity(), "Whatsapp is not installed on your Device");
                }

                break;

            case R.id.send_IV:

                if (commentET.getText().toString().trim().equals("")) {

                } else {
                    sendIV.setEnabled(false);
                    Bundle b = getArguments();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("prayer_id", b.getString("id"));
                    map.put("comment_msg", commentET.getText().toString().trim());
                    map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                    AddComment = baseActivity.apiInterface.AddComment_toPrayer(map);
                    baseActivity.apiHitAndHandle.makeApiCall(AddComment, this, false);
                }
                break;
            case R.id.pray_BT:

                if(result.data.myPrayer.equals(true)){
                    Bundle b = getArguments();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("prayer_id", b.getString("id"));
                    map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                    AnswerPrayer = baseActivity.apiInterface.Answer_Prayer(map);
                    baseActivity.apiHitAndHandle.makeApiCall(AnswerPrayer, this, false);

                 }else{
                    Bundle b = getArguments();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("prayer_id", b.getString("id"));
                    map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                    PrayForother = baseActivity.apiInterface.AddPrayer_toPrayer(map);
                    baseActivity.apiHitAndHandle.makeApiCall(PrayForother, this, false);
                }

                break;
        }
    }


    private void viewAllComntListSpannable() {
        String viewAll_TEXT = getResources().getString(R.string.label_view_all);
        SpannableString content = new SpannableString(viewAll_TEXT);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(clickableSpan_Comment_ViewAll, 0, content.length(), 0);
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, content.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        viewAllCmntTV.setMovementMethod(LinkMovementMethod.getInstance());
        viewAllCmntTV.setText(content);

    }

    ClickableSpan clickableSpan_Comment_ViewAll = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Bundle args = new Bundle();
            Bundle b = getArguments();
            args.putString("pollId", b.getString("id"));
            args.putString("title", "pray");
            baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new ShowPollCommentsFragment(), args);
        }
    };

    @Override
    public void onSuccess(Call call, Object object) {


        if (call == GetPrayerDetail) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), PrayerDetailModel.class);

            ((HomeActivity) getActivity()).setToolbarTitle(result.data.title, true, false, false, null);
            prayertitleTV.setText(result.data.title);
            timeTV.setText(result.data.createdDate + " " + result.data.createdTime);
            likeTV.setText("" + result.data.prayesCount);
            healthTV.setText(result.data.category.name);
            detailTV.setText(result.data.message);

            BlogText = result.data.message;
            BlogTitle = result.data.title;

            if (result.data.myPrayer.equals(true)) {
                if (result.data.answered.equals(true)) {
                    prayBT.setText("Answered");
                    commentView.setVisibility(View.GONE);
                    shareLay.setVisibility(View.GONE);
                    prayerdoneTV.setVisibility(View.VISIBLE);
                    prayBT.setBackgroundResource(R.drawable.btn_shape_grey);
                    prayBT.setEnabled(false);
                } else {
                    prayBT.setText("Answered");
                    commentView.setVisibility(View.VISIBLE);
                    shareLay.setVisibility(View.VISIBLE);
                    prayerdoneTV.setVisibility(View.GONE);
                    prayBT.setBackgroundResource(R.drawable.btn_shape_dark_yellow);
                    prayBT.setEnabled(true);
                }
            } else {
                if (result.data.answered.equals(true)) {

                    prayBT.setText("Answered");
                    commentView.setVisibility(View.GONE);
                    shareLay.setVisibility(View.GONE);
                    prayerdoneTV.setVisibility(View.VISIBLE);
                    prayBT.setBackgroundResource(R.drawable.btn_shape_grey);
                    prayBT.setEnabled(false);
                } else {
                    prayerdoneTV.setVisibility(View.GONE);
                    shareLay.setVisibility(View.VISIBLE);
                    commentView.setVisibility(View.VISIBLE);
                    if (result.data.isPray.equals(false)) {
                        prayBT.setText("I pray for this");
                        prayBT.setEnabled(true);
                        prayBT.setBackgroundResource(R.drawable.btn_shape_dark_yellow);
                    } else {
                        prayBT.setText("I prayed for this");
                        prayBT.setEnabled(false);
                        prayBT.setBackgroundResource(R.drawable.btn_shape_grey);
                    }


                }

            }

            viewAllComntListSpannable();

            if (result.data.comments.size() == 0) {
                commentHeaderRV.setVisibility(View.GONE);
                commentList_RV.setVisibility(View.GONE);
            } else {
                commentHeaderRV.setVisibility(View.VISIBLE);
                commentList_RV.setVisibility(View.VISIBLE);
            }


            commentAdapter = new CommentAdapter(baseActivity, result.data.comments, false, "pray");
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
            commentList_RV.setLayoutManager(mLayoutManager);
            commentList_RV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
            commentList_RV.setItemAnimator(new DefaultItemAnimator());
            commentList_RV.setAdapter(commentAdapter);
            commentAdapter.notifyDataSetChanged();

            commentList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, commentList_RV,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            Bundle args = new Bundle();
                            args.putString("userId", result.data.comments.get(position).userId.id);
                            args.putString("from", "pray");
                            baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new SelectedUserProfileFragment(), args);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));


        }
        else if(call == AddComment){
            sendIV.setEnabled(true);
            commentET.setText("");
           CallApi();
        }
        else if(call == AnswerPrayer){
            CommonDialogs.customToast(getActivity(),result.message);
            CallApi();
        }
        else if(call == PrayForother){
            CommonDialogs.customToast(getActivity(),result.message);
            CallApi();
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
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
