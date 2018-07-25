package com.netset.believeapp.Fragment.classifiedSection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.PrayerRqstAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.PrayerModel;
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
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by netset on 29/1/18.
 */

public class PrayerRequestFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.prayerList_RV)
    RecyclerView prayerList_RV;
    @BindView(R.id.liveList_RV)
    RecyclerView liveList_RV;

    PrayerRqstAdapter prayerRqstAdapter;
    List<BlogsModel> prayerList = new ArrayList<>();

    @BindView(R.id.addpostContainer)
    FrameLayout addpostContainer;
    @BindView(R.id.trend_IV)
    ImageView trendIV;
    @BindView(R.id.trend_TV)
    TextView trendTV;
    @BindView(R.id.trend_View)
    LinearLayout trendLL;

    @BindView(R.id.live_IV)
    ImageView liveIV;
    @BindView(R.id.live_TV)
    TextView liveTV;
    @BindView(R.id.live_View)
    LinearLayout liveLL;

    @BindView(R.id.post_IV)
    ImageView postIV;
    @BindView(R.id.post_TV)
    TextView postTV;
    @BindView(R.id.post_View)
    LinearLayout postLL;
    @BindView(R.id.list_lay)
    RelativeLayout listRV;

    @BindView(R.id.nodata_TV)
            TextView   nodata_text;


    Call<JsonObject> GetTrendPrayerList,GetLivePrayerList;
    PrayerModel result;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.prayer_rqst_fragment, null);
        ButterKnife.bind(this, rootView);

        TrendingApi();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public void TrendingApi(){
        trendLL.setEnabled(false);
        liveLL.setEnabled(true);
        postLL.setEnabled(true);

        addpostContainer.setVisibility(View.GONE);
        prayerList_RV.setVisibility(View.VISIBLE);
        liveList_RV.setVisibility(View.GONE);
        listRV.setVisibility(View.VISIBLE);
        trendIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
        liveIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        postIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);

        trendTV.setTextColor(getResources().getColor(R.color.white));
        liveTV.setTextColor(getResources().getColor(R.color.light_grey));
        postTV.setTextColor(getResources().getColor(R.color.light_grey));

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "TREND");
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetTrendPrayerList = baseActivity.apiInterface.Get_Prayers(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetTrendPrayerList, this, true);
    }

    public void LiveApi(){
        trendLL.setEnabled(true);
        liveLL.setEnabled(false);
        postLL.setEnabled(true);
        listRV.setVisibility(View.VISIBLE);
        addpostContainer.setVisibility(View.GONE);
        prayerList_RV.setVisibility(View.GONE);
        liveList_RV.setVisibility(View.VISIBLE);
        trendIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        liveIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
        postIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);

        trendTV.setTextColor(getResources().getColor(R.color.light_grey));
        liveTV.setTextColor(getResources().getColor(R.color.white));
        postTV.setTextColor(getResources().getColor(R.color.light_grey));

        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put("type", "LIVE");
        map2.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetLivePrayerList = baseActivity.apiInterface.Get_Prayers(map2);
        baseActivity.apiHitAndHandle.makeApiCall(GetLivePrayerList, this, true);
    }

    public void CreatePrayer(){
        trendLL.setEnabled(true);
        liveLL.setEnabled(true);
        postLL.setEnabled(false);
        listRV.setVisibility(View.GONE);
        liveList_RV.setVisibility(View.GONE);
        addpostContainer.setVisibility(View.VISIBLE);
        prayerList_RV.setVisibility(View.GONE);
        trendIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        liveIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        postIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);

        trendTV.setTextColor(getResources().getColor(R.color.light_grey));
        liveTV.setTextColor(getResources().getColor(R.color.light_grey));
        postTV.setTextColor(getResources().getColor(R.color.white));

        baseActivity.navigateFragment_NoBackStack(R.id.addpostContainer, new CreatePrayerRequestFragment());
    }

    @OnClick({R.id.trend_View, R.id.live_View, R.id.post_View})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trend_View:
                TrendingApi();
                //  displayView(0);
                break;
            case R.id.live_View:
                LiveApi();
              //  displayView(1);
                break;
            case R.id.post_View:
                CreatePrayer();
               // displayView(2);
                break;
        }
    }



    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());

        if(call == GetTrendPrayerList) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), PrayerModel.class);

            if(result.data.size() ==0){
                nodata_text.setVisibility(View.VISIBLE);
            }else{
                nodata_text.setVisibility(View.GONE);
            }

            liveList_RV.setVisibility(View.GONE);
            prayerList_RV.setVisibility(View.VISIBLE);
            prayerRqstAdapter = new PrayerRqstAdapter(baseActivity, result.data, false);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
            prayerList_RV.setLayoutManager(mLayoutManager);
            prayerList_RV.setItemAnimator(new DefaultItemAnimator());
            prayerList_RV.setAdapter(prayerRqstAdapter);

            prayerList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, prayerList_RV,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            //highlightSelectedTab(position);
                            Bundle args = new Bundle();
                            args.putString("id", result.data.get(position).id);
                            args.putString("title", result.data.get(position).title);
                            baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new PrayerDescriptionFragment(), args);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
        }
         if(call == GetLivePrayerList){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), PrayerModel.class);

             if(result.data.size() ==0){
                 nodata_text.setVisibility(View.VISIBLE);
             }else{
                 nodata_text.setVisibility(View.GONE);
             }

            prayerList_RV.setVisibility(View.GONE);
            liveList_RV.setVisibility(View.VISIBLE);
            prayerRqstAdapter = new PrayerRqstAdapter(baseActivity, result.data, false);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
             liveList_RV.setLayoutManager(mLayoutManager);
             liveList_RV.setItemAnimator(new DefaultItemAnimator());
             liveList_RV.setAdapter(prayerRqstAdapter);

             liveList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, liveList_RV,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            //highlightSelectedTab(position);
                            Bundle args = new Bundle();
                            args.putString("id", result.data.get(position).id);
                            args.putString("title", result.data.get(position).title);
                            baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new PrayerDescriptionFragment(), args);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
