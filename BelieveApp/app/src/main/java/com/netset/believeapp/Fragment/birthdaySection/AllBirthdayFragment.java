package com.netset.believeapp.Fragment.birthdaySection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.communityAdapters.BirthdayAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.BirthdayModel;
import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.SimpleDividerItemDecorationBlue;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_ALL_BIRTHDAYS;

/**
 * Created by netset on 19/1/18.
 */

public class AllBirthdayFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.bdayLogo)
    ImageView bdayLogo;
    @BindView(R.id.today_TV)
    TextView today_TV;
    @BindView(R.id.upcoming_TV)
    TextView upcoming_TV;
    @BindView(R.id.optioNCont)
    RelativeLayout optioNCont;
    @BindView(R.id.bdayList_RV)
    RecyclerView bdayList_RV;
    @BindView(R.id.bdayList_Container)
    LinearLayout bdayListContainer;
    @BindView(R.id.nodata_TV)
    TextView nodataTV;

    Unbinder unbinder;

    List<BlogsModel> blogList = new ArrayList<>();

    BirthdayAdapter birthdayAdapter;
    @BindView(R.id.todaysView)
    View todaysView;
    @BindView(R.id.upcomingView)
    View upcomingView;

    BirthdayModel result;
    Call<JsonObject> GetBirthdayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_birthday_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_ALL_BIRTHDAYS, true, false, false, null);
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
        GetBirthdayList = baseActivity.apiInterface.Get_Birthdays(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetBirthdayList, this);


    }


    @OnClick({R.id.today_TV, R.id.upcoming_TV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.today_TV:
                displayView(0);
                break;
            case R.id.upcoming_TV:
                displayView(1);
                break;
        }
    }

    public void displayView(int value) {
        switch (value) {
            case 0:
                showTodaysData();
                break;
            case 1:
                showUpcomingData();
                break;
        }
    }

    private void showTodaysData() {

        String sourceString = "<b>Today's</b> ";
        today_TV.setText(Html.fromHtml(sourceString));
        upcoming_TV.setText("Upcoming");

        todaysView.setVisibility(View.VISIBLE);
        upcomingView.setVisibility(View.INVISIBLE);
        loadtodayList();

    }

    private void showUpcomingData() {
        String sourceString = "<b>Upcoming</b> ";
        upcoming_TV.setText(Html.fromHtml(sourceString));
        today_TV.setText("Today's");

        upcomingView.setVisibility(View.VISIBLE);
        todaysView.setVisibility(View.INVISIBLE);

        loadupcomingList();
    }




    private void loadtodayList(){
        birthdayAdapter = new BirthdayAdapter(baseActivity, result.sendData.todayBirthdays, true,"today");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        bdayList_RV.setLayoutManager(mLayoutManager);
        bdayList_RV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
        bdayList_RV.setItemAnimator(new DefaultItemAnimator());
        bdayList_RV.setAdapter(birthdayAdapter);

         if(result.sendData.todayBirthdays.size() == 0){
            nodataTV.setVisibility(View.VISIBLE);
            nodataTV.setText("Nobody's Birthday Today");
        }
        else{
            nodataTV.setVisibility(View.GONE);
        }

    }


    private void loadupcomingList() {
        birthdayAdapter = new BirthdayAdapter(baseActivity, result.sendData.upcomingBirthdays, true,"upcoming","");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        bdayList_RV.setLayoutManager(mLayoutManager);
        bdayList_RV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
        bdayList_RV.setItemAnimator(new DefaultItemAnimator());
        bdayList_RV.setAdapter(birthdayAdapter);

        if(result.sendData.upcomingBirthdays.size() == 0){
            nodataTV.setVisibility(View.VISIBLE);
            nodataTV.setText("Nobody's Birthday Found");
        }

        else{
            nodataTV.setVisibility(View.GONE);
        }


    }





    @Override
    public void onSuccess(Call call, Object object) {

        Log.e("Response body", ">>>>>>>>>" + object.toString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), BirthdayModel.class);


        Bundle b = getArguments();
        if(b!= null){
            String type = b.getString("type");

            if(type.equals("today")){
                displayView(0);
            }else{
                displayView(1);
            }
        }else{
            displayView(0);
        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
