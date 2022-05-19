package com.netset.believeapp.Fragment.birthdaySection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.communityAdapters.BdayProListAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.homeMenu.WebViewFragment;
import com.netset.believeapp.GsonModel.BirthdayModel;
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
 * Created by netset on 3/5/18.
 */

public class BirthdayProductFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.weddingList_RV)
    RecyclerView weddingListRV;
    @BindView(R.id.lable_TV)
    TextView lableTV;
    Unbinder unbinder;
    Call<JsonObject> GetBirthdayList;
    BirthdayModel result;
    BdayProListAdapter bdayProListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wedding_fragment, null);
        ((HomeActivity) getActivity()).setToolbarTitle("Birthday List", true, false, false, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lableTV.setVisibility(View.GONE);
        CallApi();
    }

    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetBirthdayList = baseActivity.apiInterface.Get_Birthdays(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetBirthdayList, this, baseActivity);
    }


    @Override
    public void onSuccess(Call call, Object object) {

        Log.e("Response body", ">>>>>>>>>" + object.toString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result    = gson.fromJson(object.toString(), BirthdayModel.class);
        bdayProListAdapter = new BdayProListAdapter(baseActivity, result.sendData.birthdaylists, false);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(baseActivity, 2);
        weddingListRV.setLayoutManager(mLayoutManager);
        weddingListRV.setItemAnimator(new DefaultItemAnimator());
        weddingListRV.setAdapter(bdayProListAdapter);
        weddingListRV.setNestedScrollingEnabled(false);

        weddingListRV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, weddingListRV, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle args = new Bundle();
                args.putString("newslink",result.sendData.birthdaylists.get(position).bdayListUrlOnlineSite);
                args.putString("newsname",result.sendData.birthdaylists.get(position).bdayListTitle);
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new WebViewFragment(),args);
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
