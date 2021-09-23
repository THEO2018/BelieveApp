package com.netset.believeapp.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.netset.believeapp.GsonModel.GroupNotificationModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.BaseActivity;
import com.netset.believeapp.listeners.SwitchCheckChangeListener;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;


/**
 * Created by netset on 20/2/18.
 */

public class GroupAdapter  extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> implements ApiResponse {
    List<GroupNotificationModel.NotificationSetting> grpList;
    Context mContext;
    BaseActivity baseActivity;
    SwitchCheckChangeListener clickCallback;
    RefreshCallBack refreshlistner;
    Call<JsonObject> GroupNotify;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView groupName_TV;
        ImageView groupIV;
        SwitchCompat switchCompat;
        LinearLayout switchLay;

        public MyViewHolder(View view) {
            super(view);
            groupIV = view.findViewById(R.id.groupIV);
            groupName_TV = view.findViewById(R.id.groupName_TV);
            switchCompat = view.findViewById(R.id.notification_SW);
            switchLay = view.findViewById(R.id.switch_lay);
        }
    }

    public GroupAdapter(Context mContext, List<GroupNotificationModel.NotificationSetting> grpList, BaseActivity baseActivity) {
        this.mContext = mContext;
        this.grpList = grpList;
        this.baseActivity = baseActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_notification, parent, false);
       /* apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(mContext);
*/
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final int i = position;

        if(grpList.get(position).status.equals("ON")){
            holder.switchCompat.setChecked(true);
        }else{
            holder.switchCompat.setChecked(false);
        }
        CommonDialogs.getDisplayImage(mContext,grpList.get(position).groupImage,holder.groupIV);
        holder.groupName_TV.setText(grpList.get(position).groupName);


        holder.switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("access_token", GeneralValues.get_Access_Key(mContext));
                map.put("group_id",grpList.get(position).groupId);
                if(grpList.get(position).status.equals("ON")){
                    map.put("status","OFF");
                }else{
                    map.put("status","ON");
                }
                GroupNotify = baseActivity.apiInterface.Get_GroupNotifyChange(map);
                baseActivity.apiHitAndHandle.makeApiCall(GroupNotify,GroupAdapter.this,false);
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
