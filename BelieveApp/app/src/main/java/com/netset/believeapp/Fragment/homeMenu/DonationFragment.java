package com.netset.believeapp.Fragment.homeMenu;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.TeachingsAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.blogSection.BlogDetailFragment;
import com.netset.believeapp.GsonModel.DonationModel;
import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.Model.DonationModelNew;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
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

import static com.netset.believeapp.Utils.Constants.SC_DONATION;

/**
 * Created by netset on 16/1/18.
 */

public class DonationFragment extends BaseFragment implements ApiResponse {


    @BindView(R.id.divVIew)
    View divVIew;
    @BindView(R.id.giving_View)
    TextView givingView;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.teachingList_RV)
    RecyclerView teachingList_RV;
    @BindView(R.id.parent)
    LinearLayout parent;
    Unbinder unbinder;
    DonationModelNew result;

    Call<JsonObject> Getdonation,AddNotes;
    TeachingsAdapter teachingsAdapter;
    String GivingLink;
    List<BlogsModel> teachingList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.donation_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_DONATION, false, false, false, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CallApi();
    }


    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        Getdonation = baseActivity.apiInterface.Get_DonationList(map);
        baseActivity.apiHitAndHandle.makeApiCall(Getdonation, this);
    }


    @OnClick({R.id.giving_View})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.giving_View:
                Bundle args = new Bundle();
                args.putString("newslink",GivingLink);
                args.putString("newsname","Giving");
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new WebViewFragment(),args);
                break;
        }
    }


    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), DonationModelNew.class);

        teachingsAdapter = new TeachingsAdapter(baseActivity, result.getData().getTeaching());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
        teachingList_RV.setLayoutManager(mLayoutManager);
        teachingList_RV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
        teachingList_RV.setItemAnimator(new DefaultItemAnimator());
        teachingList_RV.setAdapter(teachingsAdapter);
        if (result.getData().getGiving()!=null){
            GivingLink=result.getData().getGiving().get(0).getGiving_url();

        }
        teachingList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, teachingList_RV, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Bundle args = new Bundle();
                args.putString("blogid", result.getData().getTeaching().get(position).get_id());
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new BlogDetailFragment(), args);
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
