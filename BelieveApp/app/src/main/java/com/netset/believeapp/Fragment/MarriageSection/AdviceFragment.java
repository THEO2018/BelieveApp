package com.netset.believeapp.Fragment.MarriageSection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.AdviceAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.blogSection.BlogDetailFragment;
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

public class AdviceFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.adviceList_RV)
    RecyclerView adviceListRV;
    Unbinder unbinder;

    Call<JsonObject> GetAdviceList;
    AdviceAdapter adviceAdapter;
    MarriagesModel result;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.advice_fragment, null);
        ((HomeActivity) getActivity()).setToolbarTitle("Advice", true, false, false, null);
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
        GetAdviceList = baseActivity.apiInterface.GetMarriages(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetAdviceList, this);

    }

    @Override
    public void onSuccess(Call call, Object object) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
         result = gson.fromJson(object.toString(), MarriagesModel.class);

        adviceAdapter = new AdviceAdapter(baseActivity, result.getData().getAdvice());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        adviceListRV.setLayoutManager(mLayoutManager);
        adviceListRV.setItemAnimator(new DefaultItemAnimator());
        adviceListRV.setAdapter(adviceAdapter);


        adviceListRV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, adviceListRV, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle args = new Bundle();
                args.putString("blogid",result.getData().getAdvice().get(position).id);
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new BlogDetailFragment(),args);
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
