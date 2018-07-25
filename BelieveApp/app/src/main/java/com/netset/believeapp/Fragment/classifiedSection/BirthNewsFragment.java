package com.netset.believeapp.Fragment.classifiedSection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.netset.believeapp.Adapter.ClassifiedBirthAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.birthdaySection.NewBornDetailFragment;
import com.netset.believeapp.GsonModel.ClassifiedBirthModel;
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

public class BirthNewsFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.birthList_RV)
    RecyclerView birthList_RV;
    @BindView(R.id.nodata_TV)
    TextView noDatatext;
    Unbinder unbinder;

    Call<JsonObject> GetCategoriesList;
    ClassifiedBirthModel result;
    List<BlogsModel> blogList = new ArrayList<>();

    ClassifiedBirthAdapter classifiedBirthAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.birth_news_fragment, null);
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
        Log.e("Response body", ">>>>>>>>>" + object.toString());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), ClassifiedBirthModel.class);


        if(result.data.size() ==0){
            noDatatext.setVisibility(View.VISIBLE);
        }else{
            noDatatext.setVisibility(View.GONE);
        }

        classifiedBirthAdapter = new ClassifiedBirthAdapter(baseActivity, result.data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        birthList_RV.setLayoutManager(mLayoutManager);
        birthList_RV.setItemAnimator(new DefaultItemAnimator());
        birthList_RV.setAdapter(classifiedBirthAdapter);
        birthList_RV.setNestedScrollingEnabled(false);

        birthList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, birthList_RV,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        //highlightSelectedTab(position);


                        Bundle args = new Bundle();
                        args.putString("image",result.data.get(position).classifiedImage);
                        args.putString("title",result.data.get(position).classifiedTitle);
                        args.putString("detail",result.data.get(position).classified);
                        baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new NewBornDetailFragment(),args);
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
