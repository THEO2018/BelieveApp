package com.netset.believeapp.Fragment.groupSection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 22/1/18.
 */

public class GroupProfilePageFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.coverImage_IV)
    AppCompatImageView coverImageIV;
    @BindView(R.id.joinGrp_BTN)
    Button joinGrpBTN;
    @BindView(R.id.grpName_TV)
    TextView grpNameTV;
    @BindView(R.id.grpNameContainer)
    LinearLayout grpNameContainer;
    @BindView(R.id.grpMemberCount_TV)
    TextView grpMemberCountTV;
    @BindView(R.id.secndContainer)
    LinearLayout secndContainer;
    @BindView(R.id.grpInfoContainer)
    LinearLayout grpInfoContainer;
    @BindView(R.id.photoContainer)
    ConstraintLayout photoContainer;
    @BindView(R.id.about_TV)
    TextView aboutTV;
    @BindView(R.id.discussion_TV)
    TextView discussionTV;
    @BindView(R.id.photos_TV)
    TextView photosTV;
    @BindView(R.id.events_TV)
    TextView eventsTV;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.tabViewContainer)
    LinearLayout tabViewContainer;
    @BindView(R.id.text_join)
    TextView textJoin;
    @BindView(R.id.img_check)
    ImageView imgCheck;
    @BindView(R.id.img_down)
            ImageView imgDown;
    @BindView(R.id.lay_join)
    LinearLayout layJoin;
    Unbinder unbinder;

    int selectionTabPosition = 0;


    Call<JsonObject> about,Join;

    String groupId,groupTitle,members,type,joinStatus="false",groupImage,privacy_status;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.group_profile_page_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle("Group", true, false, false, null);

        CallApi();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventsTV.setTextColor(getResources().getColor(R.color.black));
        aboutTV.setTextColor(getResources().getColor(R.color.light_pink));
        discussionTV.setTextColor(getResources().getColor(R.color.black));
        photosTV.setTextColor(getResources().getColor(R.color.black));
        Bundle args = new Bundle();
        args.putString("groupid",groupId);
        args.putString("joinstatus",joinStatus);
        baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabViewContainer,new GroupAboutFragment(),args);
    }


    public void CallApi(){
        Bundle b = getArguments();
        if(b != null) {
            this.groupId = b.getString("groupid");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("group_id",groupId);
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            about = baseActivity.apiInterface.GroupDetail_About(map);
            baseActivity.apiHitAndHandle.makeApiCall(about, this,false);

        }
    }


    @OnClick({R.id.joinGrp_BTN, R.id.about_TV, R.id.discussion_TV, R.id.photos_TV,R.id.events_TV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.joinGrp_BTN:
                joinGrpBTN.setEnabled(false);
                Bundle b = getArguments();
                if(b != null) {
                    this.groupId = b.getString("groupid");
                    HashMap<String, String> map1 = new HashMap<String, String>();
                    map1.put("group_id",groupId);
                    map1.put("access_token", GeneralValues.get_Access_Key(getActivity()));

                    Join = baseActivity.apiInterface.JoinGroup(map1);
                    baseActivity.apiHitAndHandle.makeApiCall(Join, this,true);

                }
                break;

            case R.id.about_TV:
               // showSelection(0);

                aboutTV.setTextColor(getResources().getColor(R.color.light_pink));
                discussionTV.setTextColor(getResources().getColor(R.color.black));
                photosTV.setTextColor(getResources().getColor(R.color.black));
                eventsTV.setTextColor(getResources().getColor(R.color.black));

               // GroupAboutFragment groupAboutFragment = new GroupAboutFragment();
                Bundle args = new Bundle();
                args.putString("groupid",groupId);
                args.putString("joinstatus",joinStatus);

                baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabViewContainer,new GroupAboutFragment(),args);

                break;
            case R.id.discussion_TV:
                discussionTV.setTextColor(getResources().getColor(R.color.light_pink));
                aboutTV.setTextColor(getResources().getColor(R.color.black));
                photosTV.setTextColor(getResources().getColor(R.color.black));
                eventsTV.setTextColor(getResources().getColor(R.color.black));

              //  GroupDiscussionFragment groupDiscussionFragment = new GroupDiscussionFragment();
                Bundle args1 = new Bundle();
                args1.putString("groupid",groupId);
                args1.putString("joinstatus",joinStatus);
                baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabViewContainer,new GroupDiscussionFragment(),args1);
                break;
            case R.id.photos_TV:
                photosTV.setTextColor(getResources().getColor(R.color.light_pink));
                aboutTV.setTextColor(getResources().getColor(R.color.black));
                discussionTV.setTextColor(getResources().getColor(R.color.black));
                eventsTV.setTextColor(getResources().getColor(R.color.black));


               // GroupMediaFragment groupMediaFragment = new GroupMediaFragment();
                Bundle args2 = new Bundle();
                args2.putString("groupid",groupId);
                args2.putString("joinstatus",joinStatus);
                baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabViewContainer,new GroupMediaFragment(),args2);
                break;

            case R.id.events_TV:
                photosTV.setTextColor(getResources().getColor(R.color.black));
                aboutTV.setTextColor(getResources().getColor(R.color.black));
                discussionTV.setTextColor(getResources().getColor(R.color.black));
                eventsTV.setTextColor(getResources().getColor(R.color.light_pink));

                Bundle args3 = new Bundle();
                args3.putString("groupid",groupId);
                args3.putString("joinstatus",joinStatus);
                baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabViewContainer,new GroupEventsFragment(),args3);

                break;

        }
    }
    

    @Override
    public void onSuccess(Call call, Object object) {

        try {
            if(call == about) {
                JSONObject jsonObject = new JSONObject(object.toString());
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                groupTitle = jsonObject1.getString("group_name");
                groupId = jsonObject1.getString("_id");
                groupImage = jsonObject1.getString("group_image");
                members = jsonObject1.getString("total_members");
                joinStatus = jsonObject1.getString("join_status");
                privacy_status = jsonObject1.getString("privacy");


                ((HomeActivity) getActivity()).setToolbarTitle(groupTitle, true, false, false, null);
                grpNameTV.setText(groupTitle);
                if (members.equals("1") || members.equals("0")) {
                    grpMemberCountTV.setText(members + " Member");
                } else {
                    grpMemberCountTV.setText(members + " Members");
                }
                CommonDialogs.getSquareImage(getActivity(), groupImage, coverImageIV);

                if(privacy_status.equals("Public")) {

                    if (joinStatus.equals("true")) {
                        joinGrpBTN.setVisibility(View.GONE);
                        textJoin.setVisibility(View.VISIBLE);
                        layJoin.setVisibility(View.VISIBLE);
                        textJoin.setText("Joined");
                    }else if(joinStatus.equals("false")){
                        layJoin.setVisibility(View.GONE);
                        joinGrpBTN.setVisibility(View.VISIBLE);
                        textJoin.setVisibility(View.GONE);
                        joinGrpBTN.setText(R.string.label_join_group);
                        joinGrpBTN.setEnabled(true);
                    }
                    else {
                        layJoin.setVisibility(View.GONE);
                        textJoin.setVisibility(View.GONE);
                        joinGrpBTN.setVisibility(View.VISIBLE);
                        joinGrpBTN.setText(R.string.label_request_sent);
                        joinGrpBTN.setEnabled(false);
                    }
                }else{
                    layJoin.setVisibility(View.GONE);
                    joinGrpBTN.setVisibility(View.GONE);
                    textJoin.setVisibility(View.GONE);
                }
            }
           else if(call == Join){
                JSONObject jsonObject = new JSONObject(object.toString());
                String message = jsonObject.getString("message");
                CommonDialogs.customToast(getActivity(),message);
                CallApi();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {


    }
}
