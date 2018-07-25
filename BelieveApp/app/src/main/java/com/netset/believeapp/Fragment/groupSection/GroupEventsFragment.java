package com.netset.believeapp.Fragment.groupSection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.EventsAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.homeMenu.EventDetailFragment;
import com.netset.believeapp.GsonModel.EventsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.retrofitManager.ApiResponse;

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

public class GroupEventsFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.eventList_RV)
    RecyclerView eventList_RV;
    @BindView(R.id.txt_nodata)
    TextView txtNodata;
    Unbinder unbinder;

    List<EventsModel.Datum> blogList = new ArrayList<>();

    EventsAdapter eventsAdapter;
    Call<JsonObject> getEvents;
    EventsModel result;
    String groupId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grp_events_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CallApi();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



    public void CallApi() {
        Bundle b = getArguments();
        groupId =  b.getString("groupid");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("group_id", groupId);
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getEvents = baseActivity.apiInterface.GetGroup_Events(map);
        baseActivity.apiHitAndHandle.makeApiCall(getEvents, this);

    }

    @Override
    public void onSuccess(Call call, Object object) {


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), EventsModel.class);


        eventsAdapter = new EventsAdapter(baseActivity, result.getData(),"event");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
        eventList_RV.setLayoutManager(mLayoutManager);
        eventList_RV.setItemAnimator(new DefaultItemAnimator());
        eventList_RV.setAdapter(eventsAdapter);

        if(result.getData().size()==0){
            txtNodata.setVisibility(View.VISIBLE);
            eventList_RV.setVisibility(View.GONE);
        }else{
            txtNodata.setVisibility(View.GONE);
            eventList_RV.setVisibility(View.VISIBLE);
        }



        eventList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, eventList_RV, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                EventDetailFragment eventDetailFragment = new EventDetailFragment();
                Bundle args = new Bundle();
                args.putString("eventid", result.getData().get(position).getId());
                eventDetailFragment.setArguments(args);
                baseActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeContainer, eventDetailFragment, "")
                        .addToBackStack("event")
                        .commit();

                //  baseActivity.navigateFragmentTransaction(R.id.homeContainer, new EventDetailFragment());
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
