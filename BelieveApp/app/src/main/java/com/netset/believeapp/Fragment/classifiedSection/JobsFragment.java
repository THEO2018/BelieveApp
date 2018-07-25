package com.netset.believeapp.Fragment.classifiedSection;

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
import com.netset.believeapp.Adapter.JobsAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.JobsModel;
import com.netset.believeapp.Model.BlogsModel;
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
 * Created by netset on 29/1/18.
 */

public class JobsFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.jobsList_RV)
    RecyclerView jobsList_RV;
    @BindView(R.id.nodata_TV)
    TextView nodataText;
    Unbinder unbinder;

    JobsAdapter jobsAdapter;

    Call<JsonObject> GetCategoriesList;
    JobsModel result;
    List<BlogsModel> jobsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.jobs_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CallApi();
    }

    public void CallApi() {
        Bundle b = getArguments();
        if(b!=null){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("category",b.getString("id"));
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            GetCategoriesList = baseActivity.apiInterface.Get_Classifieds_Of_Categories(map);
            baseActivity.apiHitAndHandle.makeApiCall(GetCategoriesList, this);
        }

    }

    @Override
    public void onSuccess(Call call, Object object) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), JobsModel.class);


        if(result.data.size() ==0){
            nodataText.setVisibility(View.VISIBLE);
        }else{
            nodataText.setVisibility(View.GONE);
        }

        jobsAdapter = new JobsAdapter(baseActivity, result.data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        jobsList_RV.setLayoutManager(mLayoutManager);
        jobsList_RV.setItemAnimator(new DefaultItemAnimator());
        jobsList_RV.setAdapter(jobsAdapter);

        jobsList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, jobsList_RV,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        //highlightSelectedTab(position);
                        Bundle args = new Bundle();
                        args.putString("jobid",result.data.get(position).id);
                        baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new JobDescriptionFragment(),args);
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
