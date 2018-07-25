package com.netset.believeapp.Fragment.appointmentSection;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.netset.believeapp.Adapter.UpcomimgAppointmentAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.UpcomingApoimtmentModel;
import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 29/1/18.
 */

public class UpcomingAppointmentFragment extends BaseFragment implements ApiResponse {


    UpcomimgAppointmentAdapter makeAppointmentAdapter;
    @BindView(R.id.apponintmentList_RV)
    RecyclerView apponintmentList_RV;
    @BindView(R.id.txt_nodata)
    TextView txtNoData;
    Unbinder unbinder;
   /* ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> GetUpcoming;
    UpcomingApoimtmentModel result;
    List<BlogsModel> apnt_List = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.upcoming_appointment_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle("Upcoming Appointments", false, false, false, null);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CallApi();
    }

    public void CallApi(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetUpcoming = baseActivity.apiInterface.Upcoming_Appointments(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetUpcoming, this);
    }


    @Override
    public void onSuccess(Call call, Object object) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), UpcomingApoimtmentModel.class);


        if(result.getData().size()==0){
            txtNoData.setVisibility(View.VISIBLE);
        }else{
            txtNoData.setVisibility(View.GONE);
        }

        makeAppointmentAdapter = new UpcomimgAppointmentAdapter(baseActivity, result.getData());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
        apponintmentList_RV.setLayoutManager(mLayoutManager);
        apponintmentList_RV.setItemAnimator(new DefaultItemAnimator());
        apponintmentList_RV.setAdapter(makeAppointmentAdapter);

    }


    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
