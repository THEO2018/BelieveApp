package com.netset.believeapp.Fragment.birthdaySection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.communityAdapters.NewBornAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.BirthdayModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.Utils.recyclerCustomisation.SimpleDividerItemDecorationBlue;
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

public class NewBornFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.newbornList_RV)
    RecyclerView newbornListRV;
    Unbinder unbinder;

    Call<JsonObject> GetBirthdayList;
    BirthdayModel result;
    NewBornAdapter newBornAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.newborn_fragment, null);
        ((HomeActivity) getActivity()).setToolbarTitle("New Borns", true, false, false, null);
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
        GetBirthdayList = baseActivity.apiInterface.Get_Birthdays(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetBirthdayList, this);
    }



    @Override
    public void onSuccess(Call call, Object object) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), BirthdayModel.class);
        newBornAdapter = new NewBornAdapter(baseActivity, result.sendData.birthNews, true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
        newbornListRV.setLayoutManager(mLayoutManager);
        newbornListRV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
        newbornListRV.setItemAnimator(new DefaultItemAnimator());
        newbornListRV.setAdapter(newBornAdapter);
        newbornListRV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, newbornListRV, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Bundle args = new Bundle();
                args.putString("image",result.sendData.birthNews.get(position).classifiedImage);
                args.putString("title",result.sendData.birthNews.get(position).classifiedTitle);
                args.putString("detail",result.sendData.birthNews.get(position).classified);
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
