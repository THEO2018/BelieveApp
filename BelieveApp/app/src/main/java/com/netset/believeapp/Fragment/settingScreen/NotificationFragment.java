package com.netset.believeapp.Fragment.settingScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.GroupAdapter;
import com.netset.believeapp.Adapter.communityAdapters.NotificationInboxAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.GroupNotificationModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.activity.CouplesActivity;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.activity.ShowFullPostActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_NOTIFICATIONS;

/**
 * Created by netset on 11/1/18.
 */

public class NotificationFragment extends BaseFragment implements ApiResponse, GroupAdapter.RefreshCallBack {

    @BindView(R.id.divView)
    View divView;
    @BindView(R.id.notificationLabel_TV)
    AppCompatTextView notificationLabelTV;

    @BindView(R.id.notifONOFF_container)
    ConstraintLayout notifONOFFContainer;
    @BindView(R.id.textView8)
    AppCompatTextView textView8;
   /* @BindView(R.id.viewAll_TV)
    AppCompatTextView viewAllTV;*/
    @BindView(R.id.group_RV)
    RecyclerView groupRV;
    @BindView(R.id.constraintLayout2)
    ConstraintLayout constraintLayout2;
    @BindView(R.id.label_notification)
    AppCompatTextView labelNotification;
    @BindView(R.id.viewAllNotif_TV)
    AppCompatTextView viewAllNotifTV;
    @BindView(R.id.notificationList_LV)
    RecyclerView notificationListRV;
    @BindView(R.id.notification_container)
    ConstraintLayout notificationContainer;
    @BindView(R.id.view_container)
    ConstraintLayout viewContainer;
    @BindView(R.id.containerView)
    ScrollView containerView;
    @BindView(R.id.parent)
    RelativeLayout parent;
    @BindView(R.id.txt_nodata)
    TextView txtNodata;
    @BindView(R.id.txt_nogroup)
            TextView txtNogroup;
    @BindView(R.id.notif_IV)
    SwitchCompat  notifSwitch;

    Unbinder unbinder;

    NotificationInboxAdapter notificationAdapter;
    GroupAdapter groupAdapter;

    Call<JsonObject> getGroups,SetNotifySound;
    GroupNotificationModel result;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notification_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_NOTIFICATIONS, true, false, false, null);
        parent.setBackgroundResource(0);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        CallApi();

    }

    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getGroups =  baseActivity.apiInterface.Get_NotificationGroup(map);
        baseActivity.apiHitAndHandle.makeApiCall(getGroups, this);
    }



    @Override
    public void onSuccess(Call call, Object object) {

        Log.e("Response body", ">>>>>>>>>" + object.toString());

        if(call == getGroups) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), GroupNotificationModel.class);

            if (result.data.notificationSettings.size() == 0) {
                txtNogroup.setVisibility(View.VISIBLE);
                groupRV.setVisibility(View.GONE);

            } else {
                txtNogroup.setVisibility(View.GONE);
                groupRV.setVisibility(View.VISIBLE);

            }

            if(result.data.notifications.size()==0){
                txtNodata.setVisibility(View.VISIBLE);
                notificationListRV.setVisibility(View.GONE);
                viewAllNotifTV.setVisibility(View.GONE);
            }
            else{
                txtNodata.setVisibility(View.GONE);
                notificationListRV.setVisibility(View.VISIBLE);
                viewAllNotifTV.setVisibility(View.VISIBLE);
            }




            groupAdapter = new GroupAdapter(baseActivity, result.data.notificationSettings,baseActivity);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
            groupRV.setLayoutManager(mLayoutManager);
            groupRV.setItemAnimator(new DefaultItemAnimator());
            groupRV.setAdapter(groupAdapter);
            groupAdapter.notifyDataSetChanged();
            groupAdapter.setAdapterCallback(this);


            notificationAdapter = new NotificationInboxAdapter(baseActivity, result.data.notifications,false);
            RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
            notificationListRV.setLayoutManager(mLayoutManager2);
            notificationListRV.setItemAnimator(new DefaultItemAnimator());
            notificationListRV.setAdapter(notificationAdapter);

            notificationListRV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, notificationListRV,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            if(result.data.notifications.get(position).notificationType.equals("liked")){


                                startActivity(new Intent(getActivity(), ShowFullPostActivity.class)
                                        .putExtra("from","notify")
                                        .putExtra("status","true")
                                        .putExtra("postid",result.data.notifications.get(position).postId.id));


                            }
                            else if(result.data.notifications.get(position).notificationType.equals("commented")){
                                startActivity(new Intent(getActivity(), ShowFullPostActivity.class)
                                        .putExtra("from","notify")
                                        .putExtra("status","true")
                                        .putExtra("postid",result.data.notifications.get(position).postId.id));
                            }
                            else if(result.data.notifications.get(position).notificationType.equals("posted")){
                                startActivity(new Intent(getActivity(), ShowFullPostActivity.class)
                                        .putExtra("from","notify")
                                        .putExtra("status","true")
                                        .putExtra("postid",result.data.notifications.get(position).postId.id));
                            }

                            else if(result.data.notifications.get(position).notificationType.equals("betrothed")){
                                startActivity(new Intent(getActivity(), CouplesActivity.class));
                            }


                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));





            viewAllNotifTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    baseActivity.navigateFragmentTransaction(R.id.homeContainer, new NotificationListFragment());
                }
            });




            if(result.data.soundStatus.equals("ON")){
              notifSwitch.setChecked(true);
            }else{
                notifSwitch.setChecked(false);
            }

            notifSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                    if(result.data.soundStatus.equals("ON")){
                        map.put("status","OFF");
                    }else{
                        map.put("status","ON");
                    }
                    SetNotifySound =  baseActivity.apiInterface.Get_NotifySound(map);
                    baseActivity.apiHitAndHandle.makeApiCall(SetNotifySound, NotificationFragment.this,false);
                }
            });


        }
     /*   else if(call == GroupNotify){
            CallApi();
        }*/
        else if(call ==SetNotifySound){
            CallApi();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }


    @Override
    public void refreshlist() {
        CallApi();
    }


}
