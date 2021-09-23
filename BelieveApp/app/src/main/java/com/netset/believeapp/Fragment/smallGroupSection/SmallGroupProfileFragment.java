package com.netset.believeapp.Fragment.smallGroupSection;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.Model.SmallGroupDetailModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.CommonFunctions;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 19/1/18.
 */

public class SmallGroupProfileFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.coverImage_IV)
    ImageView coverImageIV;
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
    @BindView(R.id.label_desc)
    TextView labelDesc;
    @BindView(R.id.description_TV)
    TextView descriptionTV;
    @BindView(R.id.label_location)
    TextView labelLocation;
    @BindView(R.id.location_TV)
    TextView locationTV;
    @BindView(R.id.label_leader)
    TextView labelLeader;
    @BindView(R.id.leader_TV)
    TextView leaderTV;
    @BindView(R.id.label_timing)
    TextView labelTiming;
    @BindView(R.id.meetHours_TV)
    TextView meetHoursTV;
    @BindView(R.id.label_members)
    TextView labelMembers;
   /* @BindView(R.id.viewMembersMap_TV)
    TextView viewMembersMap_TV;*/
    @BindView(R.id.membersContainer)
    ConstraintLayout membersContainer;
    @BindView(R.id.lay_join)
            LinearLayout layJoin;
    @BindView(R.id.txt_join)
            TextView txtJoin;
    @BindView(R.id.membersGrid_GV)
    GridView membersGridView;
    Unbinder unbinder;
    SmallGroupDetailModel result;
   /* ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> GetGroupDetail,JoinGroup;
    List<BlogsModel> blogList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.smal_group_profile_fragment, null);

        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/


    }

    @Override
    public void onResume() {
        super.onResume();
        viewAllWEdListSpannable();
    }

    @OnClick({R.id.joinGrp_BTN})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.joinGrp_BTN:
                Bundle b = getArguments();
                if(b!=null) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("small_group_id",b.getString("smallgroupid"));
                    map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                    JoinGroup =  baseActivity.apiInterface.JoinSmallGroups(map);
                    baseActivity.apiHitAndHandle.makeApiCall(JoinGroup, this);
                }
                break;

        }
    }



    private void viewAllWEdListSpannable() {
        Bundle b = getArguments();
        if(b!=null){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("small_group_id", b.getString("smallgroupid"));
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            GetGroupDetail =  baseActivity.apiInterface.GetSmallGroups_Detail(map);
            baseActivity.apiHitAndHandle.makeApiCall(GetGroupDetail, this);
        }



    }

    @Override
    public void onSuccess(Call call, Object object) {

        Log.e("Response body", ">>>>>>>>>" + object.toString());
if(call == GetGroupDetail) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    result = gson.fromJson(object.toString(), SmallGroupDetailModel.class);


    try {
        CommonDialogs.getSquareImage(getActivity(), result.getData().getSmallGroupImage(), coverImageIV);
    } catch (Exception e) {
        e.printStackTrace();
    }
    ((HomeActivity) getActivity()).setToolbarTitle(result.getData().getSmallGroupName(), true, false, false, null);
    if (result.getData().getJoinStatus().equals("true")) {
        txtJoin.setVisibility(View.VISIBLE);
        txtJoin.setText("Joined");
        layJoin.setVisibility(View.VISIBLE);
        joinGrpBTN.setVisibility(View.GONE);
        grpNameTV.setText(result.getData().getSmallGroupName());

        if (result.getData().getTotalMembers().equals("1") || result.getData().getTotalMembers().equals("0")) {
            grpMemberCountTV.setText(result.getData().getTotalMembers() + " Member");
        } else {
            grpMemberCountTV.setText(result.getData().getTotalMembers() + " Members");
        }
       // grpMemberCountTV.setText(""+result.getData().getTotalMembers()+" Members");
        membersContainer.setVisibility(View.VISIBLE);


        MemberAdapter adapter = new MemberAdapter(getActivity(), result.getData().getUsers());
        membersGridView.setAdapter(adapter);
        CommonFunctions.setGridViewHeightBasedOnChildren(membersGridView, result.getData().getUsers().size());
        adapter.notifyDataSetChanged();

    } else if (result.getData().getJoinStatus().equals("false")) {
        txtJoin.setVisibility(View.GONE);
        layJoin.setVisibility(View.GONE);
        joinGrpBTN.setVisibility(View.VISIBLE);
        joinGrpBTN.setEnabled(true);
        joinGrpBTN.setText("Join Group");
        grpNameTV.setText("");
        grpMemberCountTV.setText("");
        membersContainer.setVisibility(View.GONE);
    } else {
        txtJoin.setVisibility(View.GONE);
        layJoin.setVisibility(View.GONE);
        joinGrpBTN.setVisibility(View.VISIBLE);
        joinGrpBTN.setText("Request Sent");
        joinGrpBTN.setEnabled(false);
        grpNameTV.setText("");
        grpMemberCountTV.setText("");
        membersContainer.setVisibility(View.GONE);
    }

    descriptionTV.setText(result.getData().getSmallGroupDescription());
    locationTV.setText(result.getData().getVenue());
    meetHoursTV.setText(result.getData().getStartTime() + " - " + result.getData().getEndTime());
    leaderTV.setText(result.getData().getAdminUsers());
}
else if(call == JoinGroup){
    CommonDialogs.customToast(getActivity(),result.getMessage());
    viewAllWEdListSpannable();
}
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }

    /*ClickableSpan clickableSpan_loadMap = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            baseActivity.navigateFragmentTransaction(R.id.homeContainer, new SmallGroupMembersMap());
        }
    };*/


    public  class MemberAdapter extends BaseAdapter {
        MemberAdapter.ViewHolder viewHolder;
        Context context;
        List<SmallGroupDetailModel.User> blogListAdapter = new ArrayList<>();

        public MemberAdapter(Context context, List<SmallGroupDetailModel.User> blogList) {
            this.context = context;
            this.blogListAdapter = blogList;
        }

        @Override
        public int getCount() {
            return blogListAdapter.size();
        }

        @Override
        public Object getItem(int position) {
            return blogListAdapter.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {

                convertView = LayoutInflater.from(context).
                        inflate(R.layout.item_member_view, parent, false);
                viewHolder = new MemberAdapter.ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MemberAdapter.ViewHolder) convertView.getTag();
            }
            CommonDialogs.getDisplayImage(context,blogListAdapter.get(position).getProfileImage(),viewHolder.memberImgIV);

            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.memberImg_IV)
            ImageView memberImgIV;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }



}
