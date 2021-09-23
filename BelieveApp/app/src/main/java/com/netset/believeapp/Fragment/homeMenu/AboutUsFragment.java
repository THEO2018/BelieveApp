package com.netset.believeapp.Fragment.homeMenu;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.AboutUsAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.aboutUsSection.ShowAboutDescriptionFragment;
import com.netset.believeapp.GsonModel.AboutUsListModel;
import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_ABOUT_US;

/**
 * Created by netset on 25/1/18.
 */

public class AboutUsFragment extends BaseFragment implements ApiResponse {


    @BindView(R.id.aboutUs_RV)
    RecyclerView aboutUs_RV;
    Unbinder unbinder;

    AboutUsAdapter aboutUsAdapter;
    List<BlogsModel> abtUsList = new ArrayList<>();

    AboutUsListModel result;
    Call<JsonObject> getAboutUs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about_us_fragment, null);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_ABOUT_US, false, false, false, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        CallApi();
    }

    public void CallApi() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getAboutUs = baseActivity.apiInterface.Get_aboutUs(map);
        baseActivity.apiHitAndHandle.makeApiCall(getAboutUs, this);

    }


    @Override
    public void onSuccess(Call call, Object object) {


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), AboutUsListModel.class);

        aboutUsAdapter = new AboutUsAdapter(baseActivity, result.getData(), true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(baseActivity, 2);//new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        aboutUs_RV.setLayoutManager(mLayoutManager);
        aboutUs_RV.setItemAnimator(new DefaultItemAnimator());
        aboutUs_RV.setAdapter(aboutUsAdapter);


        aboutUs_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, aboutUs_RV, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle args = new Bundle();
                args.putString("aboutid",result.getData().get(position).getId());
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new ShowAboutDescriptionFragment(),args);
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
