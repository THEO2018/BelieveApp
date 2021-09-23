package com.netset.believeapp.Fragment.communitySection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.communityAdapters.PollsAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.PollsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 19/1/18.
 */

public class PollsFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.activePoll_TV)
    TextView activePoll_TV;
    @BindView(R.id.closedPoll_TV)
    TextView closedPoll_TV;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.pollsCount_TV)
    TextView pollsCountTV;
    @BindView(R.id.pollsList_RV)
    RecyclerView pollsList_RV;
    @BindView(R.id.grpList_viewContainer)
    ConstraintLayout grpListViewContainer;
    @BindView(R.id.txt_nodata)
    TextView txtNodata;
    @BindView(R.id.parent)
    ConstraintLayout parent;
    Unbinder unbinder;
    PollsAdapter pollsAdapter;
    public Boolean isClosed = true;
    View rootView;
    PollsModel result;

    Call<JsonObject> GetActiveList,GetCompleteList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.polls_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        parent.setBackgroundResource(0);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        displayView(0);
    }


    public void GetActivePollApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetActiveList = baseActivity.apiInterface.Get_ActivePolls(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetActiveList, this);
    }


    public void GetCompletePollApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetCompleteList = baseActivity.apiInterface.Get_ClosedPolls(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetCompleteList, this);
    }



    @OnClick({R.id.activePoll_TV, R.id.closedPoll_TV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activePoll_TV:
                displayView(0);
                break;
            case R.id.closedPoll_TV:
                displayView(1);
                break;
        }
    }

    private void displayView(int value) {
        switch (value) {
            case 0:
                loadGrpListView(0);
                break;
            case 1:
                loadGrpListView(1);
                break;
        }
    }

    private void loadGrpListView(int position) {
        if (position == 0) {
            activePoll_TV.setBackgroundResource(R.drawable.round_left_edge_shape);
            closedPoll_TV.setBackgroundResource(0);
            activePoll_TV.setTextColor(getResources().getColor(R.color.black));
            closedPoll_TV.setTextColor(getResources().getColor(R.color.white));
            pollsCountTV.setText("2 Active Polls");
            GetActivePollApi();

        } else if (position == 1) {
            closedPoll_TV.setBackgroundResource(R.drawable.round_right_edge_shape);
            activePoll_TV.setBackgroundResource(0);
            closedPoll_TV.setTextColor(getResources().getColor(R.color.black));
            activePoll_TV.setTextColor(getResources().getColor(R.color.white));
            pollsCountTV.setText("2 Closed Polls");

            GetCompletePollApi();
        }
    }

    @Override
    public void onSuccess(Call call, Object object) {


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), PollsModel.class);

        if(call == GetActiveList){
            isClosed = false;
            pollsCountTV.setText(result.data.size()+" Active Polls");
            pollsAdapter = new PollsAdapter(baseActivity, PollsFragment.this, result.data,"active");
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
            pollsList_RV.setLayoutManager(mLayoutManager);
            pollsList_RV.setItemAnimator(new DefaultItemAnimator());
            pollsList_RV.setAdapter(pollsAdapter);
            if(result.data.size() ==0){
                txtNodata.setVisibility(View.VISIBLE);
                pollsList_RV.setVisibility(View.GONE);
            }else{
                txtNodata.setVisibility(View.GONE);
                pollsList_RV.setVisibility(View.VISIBLE);
            }
        }


        else if(call == GetCompleteList){
            isClosed = true;
            pollsCountTV.setText(result.data.size()+" Closed Polls");
            pollsAdapter = new PollsAdapter(baseActivity, PollsFragment.this, result.data,"closed");
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
            pollsList_RV.setLayoutManager(mLayoutManager);
            pollsList_RV.setItemAnimator(new DefaultItemAnimator());
            pollsList_RV.setAdapter(pollsAdapter);

            if(result.data.size() ==0){
                txtNodata.setVisibility(View.VISIBLE);
                pollsList_RV.setVisibility(View.GONE);
            }else{
                txtNodata.setVisibility(View.GONE);
                pollsList_RV.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
