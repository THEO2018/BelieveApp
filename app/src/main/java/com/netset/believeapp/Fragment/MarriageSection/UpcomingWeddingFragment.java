package com.netset.believeapp.Fragment.MarriageSection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.netset.believeapp.GsonModel.MarriagesModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 1/5/18.
 */

public class UpcomingWeddingFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.upcomingList_RV)
    RecyclerView upcomingListRV;
    @BindView(R.id.txt_nodata)
    TextView txtNodata;
    Unbinder unbinder;

    Call<JsonObject> GetMarriageList;
    MarriagesModel result;
    EventsAdapter upcomingWeddingAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.upcomingwedding_fragment, null);
        ((HomeActivity) getActivity()).setToolbarTitle("Upcoming Weddings", true, false, false, null);
        unbinder = ButterKnife.bind(this, rootView);
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
        GetMarriageList = baseActivity.apiInterface.GetMarriages(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetMarriageList, this, baseActivity);

    }

    @Override
    public void onSuccess(Call call, Object object) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), MarriagesModel.class);


        upcomingWeddingAdapter = new EventsAdapter(baseActivity, result.getData().getUpcomingMarriages(),"wedding");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
        upcomingListRV.setLayoutManager(mLayoutManager);
        upcomingListRV.setItemAnimator(new DefaultItemAnimator());
        upcomingListRV.setAdapter(upcomingWeddingAdapter);

        if(result.getData().getUpcomingMarriages().size()==0){
            txtNodata.setVisibility(View.VISIBLE);
            upcomingListRV.setVisibility(View.GONE);
        }else{
            txtNodata.setVisibility(View.GONE);
            upcomingListRV.setVisibility(View.VISIBLE);
        }


        upcomingListRV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, upcomingListRV, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                EventDetailFragment eventDetailFragment = new EventDetailFragment();
                Bundle args = new Bundle();
                args.putString("eventid", result.getData().getUpcomingMarriages().get(position).id);
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
