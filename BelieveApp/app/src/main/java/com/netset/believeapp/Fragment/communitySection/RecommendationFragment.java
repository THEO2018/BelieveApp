package com.netset.believeapp.Fragment.communitySection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.netset.believeapp.Adapter.communityAdapters.RecommendAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 19/1/18.
 */

public class RecommendationFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.recommendList_RV)
    RecyclerView recommendList_RV;
    @BindView(R.id.ask_BTN)
    Button askBTN;
    Unbinder unbinder;

    List<BlogsModel> blogList = new ArrayList<>();

    RecommendAdapter recommendAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recommendation_fragment, null);
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
        loadRecommendList();
    }


    private void loadRecommendList() {

        recommendAdapter = new RecommendAdapter(baseActivity, blogList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        recommendList_RV.setLayoutManager(mLayoutManager);
        recommendList_RV.setItemAnimator(new DefaultItemAnimator());
        recommendList_RV.setAdapter(recommendAdapter);
    }




    @OnClick({R.id.ask_BTN})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ask_BTN:

                
                break;

        }
    }

    @Override
    public void onSuccess(Call call, Object object) {

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
