package com.netset.believeapp.Fragment.groupSection;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.retrofitManager.ApiResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 22/1/18.
 */

public class GroupAboutFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.lable_Desc)
    TextView lableDesc;
    @BindView(R.id.descText_TV)
    TextView descTextTV;
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    @BindView(R.id.label_grpType)
    TextView labelGrpType;
    @BindView(R.id.grpType_TV)
    TextView grpTypeTV;
    @BindView(R.id.linearLayout4)
    LinearLayout linearLayout4;
    @BindView(R.id.label_members)
    TextView labelMembers;
    @BindView(R.id.memberCount_TV)
    TextView memberCountTV;
    @BindView(R.id.membersGrid_GV)
    RecyclerView membersGrid_GV;
    @BindView(R.id.linearLayout5)
    LinearLayout linearLayout5;
    Unbinder unbinder;

    String groupId,description,members,type,id,user_image,user_id,joinStatus;
    Call<JsonObject> about;
    List<BlogsModel> blogList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grp_about_view, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Bundle b = getArguments();
        if(b != null) {
            groupId = b.getString("groupid");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("group_id",groupId);
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            about = baseActivity.apiInterface.GroupDetail_About(map);
            baseActivity.apiHitAndHandle.makeApiCall(about, this);
        }
    }


    @Override
    public void onSuccess(Call call, Object object) {

        try {
            JSONObject jsonObject = new JSONObject(object.toString());
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            description = jsonObject1.getString("description");
            members = jsonObject1.getString("total_members");
            id = jsonObject1.getString("_id");
            type = jsonObject1.getString("privacy");
            joinStatus = jsonObject1.getString("join_status");


            if(type.equals("Public")) {

                if (joinStatus.equals("true")) {
                    linearLayout4.setVisibility(View.VISIBLE);
                    linearLayout5.setVisibility(View.VISIBLE);
                }else if(joinStatus.equals("false")){
                    linearLayout4.setVisibility(View.GONE);
                    linearLayout5.setVisibility(View.GONE);
                }
                else {
                    linearLayout4.setVisibility(View.GONE);
                    linearLayout5.setVisibility(View.GONE);
                }
            }else{
                linearLayout4.setVisibility(View.GONE);
                linearLayout5.setVisibility(View.GONE);
            }




            blogList.clear();

            JSONArray jsonArray = jsonObject1.getJSONArray("users");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject arrayObj = jsonArray.getJSONObject(i);
                user_id = arrayObj.getString("_id");
                user_image = arrayObj.getString("profile_image");
                BlogsModel model = new BlogsModel(user_id,user_image);
                blogList.add(model);
            }
            MemberAdapter adapter = new MemberAdapter(getActivity(),blogList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity, LinearLayoutManager.HORIZONTAL, false);
            membersGrid_GV.setLayoutManager(mLayoutManager);
            membersGrid_GV.setItemAnimator(new DefaultItemAnimator());
            membersGrid_GV.setAdapter(adapter);

            adapter.notifyDataSetChanged();



            descTextTV.setText(description);
            grpTypeTV.setText(type);
            memberCountTV.setText(members);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }


    public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyViewHolder> {
        MyViewHolder viewHolder;
        Context context;
        List<BlogsModel> blogListAdapter = new ArrayList<>();

        public MemberAdapter(Context context, List<BlogsModel> blogList) {
            this.context = context;
            this.blogListAdapter = blogList;
        }


        @Override
        public MemberAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = null;

                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_member_view, parent, false);

            return new MemberAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MemberAdapter.MyViewHolder holder, int position) {
            CommonDialogs.getDisplayImage(context,blogListAdapter.get(position).getUserImage(),holder.memberImgIV);
        }


        @Override
        public int getItemCount() {

          return blogListAdapter.size();

        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView memberImgIV;


            public MyViewHolder(View view) {
                super(view);
                memberImgIV = view.findViewById(R.id.memberImg_IV);
            }
        }



    }

}