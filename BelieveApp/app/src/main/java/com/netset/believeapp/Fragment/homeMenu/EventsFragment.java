package com.netset.believeapp.Fragment.homeMenu;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.EventsAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.EventsModel;
import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_EVENTS;

/**
 * Created by netset on 10/1/18.
 */

public class EventsFragment extends BaseFragment implements ApiResponse {

    List<BlogsModel> blogList = new ArrayList<>();

    EventsAdapter eventsAdapter;
    @BindView(R.id.divView)
    View divView;
    @BindView(R.id.eventList_RV)
    RecyclerView eventList_RV;
    @BindView(R.id.txt_nodata)
    TextView txtNodata;
    Unbinder unbinder;
    EventsModel result;
   /* ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> getEvents;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.events_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_EVENTS, false, false, false, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/
        CallApi();
    }


    public void CallApi() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getEvents = baseActivity.apiInterface.GetEvents(map);
        baseActivity.apiHitAndHandle.makeApiCall(getEvents, this);

    }

    private void setEventsAdapter() {
    }


    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());


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
