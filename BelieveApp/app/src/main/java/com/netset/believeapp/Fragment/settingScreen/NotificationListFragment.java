package com.netset.believeapp.Fragment.settingScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
import retrofit2.Call;


public class NotificationListFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.notify_lv)
    RecyclerView notifyLv;
    @BindView(R.id.txt_nodata)
    TextView txtNodata;


    Call<JsonObject> getGroups;
    GroupNotificationModel result;
    NotificationInboxAdapter notificationAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.notificationlistfragment, null);

        ((HomeActivity) getActivity()).setToolbarTitle("Messages and notifications", true, false, false, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), GroupNotificationModel.class);



        if(result.data.notifications.size()==0){
            txtNodata.setVisibility(View.VISIBLE);
            notifyLv.setVisibility(View.GONE);
        }
        else{
            txtNodata.setVisibility(View.GONE);
            notifyLv.setVisibility(View.VISIBLE);
        }

        notificationAdapter = new NotificationInboxAdapter(baseActivity, result.data.notifications,true);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        notifyLv.setLayoutManager(mLayoutManager2);
        notifyLv.setItemAnimator(new DefaultItemAnimator());
        notifyLv.setAdapter(notificationAdapter);

        notifyLv.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, notifyLv,
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


    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
