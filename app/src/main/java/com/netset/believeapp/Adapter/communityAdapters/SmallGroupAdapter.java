package com.netset.believeapp.Adapter.communityAdapters;

import android.content.Context;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.smallGroupSection.SmallGroupProfileFragment;
import com.netset.believeapp.Model.SmallGroupsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.BaseActivity;
import com.netset.believeapp.callbacks.JoinbtnClickCallback;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * Created by netset on 18/1/18.
 */

public class SmallGroupAdapter extends RecyclerView.Adapter<SmallGroupAdapter.MyViewHolder> implements ApiResponse {
    List<SmallGroupsModel.Datum> grpList;
    Context mContext;
    BaseActivity baseActivity;
    JoinbtnClickCallback clickCallback;
   /* ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> JoinGroup;
    RefreshCallBack refreshlistner;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView grpIcon_IV;
        TextView grpTitle_TV, grpLoc_TV, grp_Distance_TV;
        Button joinGrp_BTN;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            grpIcon_IV = view.findViewById(R.id.grpIcon_IV);
            grpTitle_TV = view.findViewById(R.id.grpName_TV);
            grpLoc_TV = view.findViewById(R.id.loc_TV);
            grp_Distance_TV = view.findViewById(R.id.distance_TV);
            joinGrp_BTN = view.findViewById(R.id.joinGrp_BTN);
            cardView = view.findViewById(R.id.card_view);
        }
    }

    public SmallGroupAdapter(Context mContext, List<SmallGroupsModel.Datum> grpList,BaseActivity baseActivity) {
        this.mContext = mContext;
        this.grpList = grpList;
        this.baseActivity = baseActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_small_group_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        CommonDialogs.getDisplayImage(mContext,grpList.get(position).getSmallGroupImage(),holder.grpIcon_IV,"#d3d3d3");
        holder.grpTitle_TV.setText(grpList.get(position).getSmallGroupName());


        holder.joinGrp_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  clickCallback.onJoinBtnClick(position);

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("small_group_id",grpList.get(position).getId());
                map.put("access_token", GeneralValues.get_Access_Key(mContext));
                JoinGroup =  baseActivity.apiInterface.JoinSmallGroups(map);
                baseActivity.apiHitAndHandle.makeApiCall(JoinGroup, SmallGroupAdapter.this, baseActivity);
            }
        });

        Log.e(">>>>>.join status",">>>>>>>>>>"+grpList.get(position).getJoinStatus());
        if(grpList.get(position).getJoinStatus().equals("true")){
            holder.joinGrp_BTN.setVisibility(View.GONE);
        }
        else if(grpList.get(position).getJoinStatus().equals("false")){
            holder.joinGrp_BTN.setVisibility(View.VISIBLE);
            holder.joinGrp_BTN.setEnabled(true);
            holder.joinGrp_BTN.setText("Join Group");
        }
        else if(grpList.get(position).getJoinStatus().equals("Pending")){
            holder.joinGrp_BTN.setEnabled(false);
            holder.joinGrp_BTN.setVisibility(View.VISIBLE);
            holder.joinGrp_BTN.setText("Request Sent");
        }

        holder.grpLoc_TV.setText(grpList.get(position).getVenue());
        holder.grp_Distance_TV.setText(grpList.get(position).getMilesDistance());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("smallgroupid", grpList.get(position).getId());
                ((BaseActivity)mContext).navigateFragmentTransaction_ARG(R.id.homeContainer, new SmallGroupProfileFragment(),args);
            }
        });



    }

    @Override
    public int getItemCount() {
        return grpList.size();
    }

    @Override
    public void onSuccess(Call call, Object object) {
        try {
            if (refreshlistner != null) {
                refreshlistner.refreshlist();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }


    public void setAdapterCallback(RefreshCallBack adapterCallback) {
        this.refreshlistner = adapterCallback;
    }

    public interface RefreshCallBack {
        void refreshlist();
    }

}
