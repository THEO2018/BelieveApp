package com.netset.believeapp.Fragment.homeMenu;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.TabsAdapter;
import com.netset.believeapp.CommonConst;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.classifiedSection.BirthNewsFragment;
import com.netset.believeapp.Fragment.classifiedSection.JobsFragment;
import com.netset.believeapp.Fragment.classifiedSection.PrayerRequestFragment;
import com.netset.believeapp.GsonModel.ClassifiedCatModel;
import com.netset.believeapp.Model.TabBarModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiHitAndHandle;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_CLASSIFIED;

/**
 * Created by netset on 29/1/18.
 */

public class ClassifiedFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.tabList_RV)
    RecyclerView tabListRV;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.tabSelection_Container)
    FrameLayout tabSelectionContainer;
    Unbinder unbinder;
    ArrayList<TabBarModel> categoryList = new ArrayList();
    TabsAdapter tabsAdapter;
    int selected_TabPosition = 0;
    Call<JsonObject> GetCategories;
    ClassifiedCatModel result;
    private View rootView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.classified_fragment, null);
            unbinder = ButterKnife.bind(this, rootView);
            ((HomeActivity) getActivity()).setToolbarTitle(SC_CLASSIFIED, false, false, false, null);
            CallApi();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetCategories = baseActivity.apiInterface.Get_Classifieds_Categories(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetCategories, this,false);

    }


    private void showTabBar() {

        if(categoryList.size()>0) {
            categoryList.clear();
        }
            for(int i =0;i<result.data.size();i++){
                TabBarModel model = new TabBarModel(result.data.get(i).id,result.data.get(i).name,false);
                categoryList.add(model);
        }

        for (int i = 0; i < categoryList.size(); i++) {
            if (selected_TabPosition == i) {
                categoryList.get(i).setSelected(true);
            } else {
                categoryList.get(i).setSelected(false);
            }
        }

        tabsAdapter = new TabsAdapter(baseActivity, categoryList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity, LinearLayoutManager.HORIZONTAL, false);
        tabListRV.setLayoutManager(mLayoutManager);
        tabListRV.setItemAnimator(new DefaultItemAnimator());
        tabListRV.setAdapter(tabsAdapter);
        tabsAdapter.notifyDataSetChanged();
        displaySelectedTabView(selected_TabPosition);


        tabListRV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, tabListRV,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        highlightSelectedTab(position);

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
    }

    /**
     * Method for highlighting Selected Tab Option ...
     *
     * @param position
     */
    private void highlightSelectedTab(int position) {
        for (int i = 0; i < categoryList.size(); i++) {
            if (i == position) {
                categoryList.get(i).setSelected(true);
            } else {
                categoryList.get(i).setSelected(false);
            }
        }
        tabsAdapter.notifyDataSetChanged();

        if (position == categoryList.size() - 1) {
            tabListRV.scrollToPosition(position);
        } else if (position == 0) {
            tabListRV.scrollToPosition(position);
        } else {
            tabListRV.scrollToPosition(position + 1);
        }

        displaySelectedTabView(position);
    }

    private void displaySelectedTabView(int position) {
        Bundle args = new Bundle();

        if( categoryList.size() > 0){

            if (position == 0) {
                if (categoryList.get(0).getCatName().equals("Birth News")) {
                    args.putString("id", categoryList.get(0).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new BirthNewsFragment(), args);
                } else if (categoryList.get(0).getCatName().equals("jobs")) {
                    args.putString("id", categoryList.get(0).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new JobsFragment(), args);
                } else if (categoryList.get(position).getCatName().equals("Prayer Requests")) {
                    args.putString("id", categoryList.get(0).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new PrayerRequestFragment(), args);
                } else {
                    args.putString("id", categoryList.get(position).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new JobsFragment(), args);
                }
            } else if (position == 1) {
                if (categoryList.get(1).getCatName().equals("Birth News")) {
                    args.putString("id", categoryList.get(1).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new BirthNewsFragment(), args);
                } else if (categoryList.get(1).getCatName().equals("jobs")) {
                    args.putString("id", categoryList.get(1).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new JobsFragment(), args);
                } else if (categoryList.get(position).getCatName().equals("Prayer Requests")) {
                    args.putString("id", categoryList.get(1).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new PrayerRequestFragment(), args);
                } else {
                    args.putString("id", categoryList.get(position).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new JobsFragment(), args);
                }
            } else if (position == 2) {
                if (categoryList.get(2).getCatName().equals("Birth News")) {
                    args.putString("id", categoryList.get(2).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new BirthNewsFragment(), args);
                } else if (categoryList.get(2).getCatName().equals("jobs")) {
                    args.putString("id", categoryList.get(2).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new JobsFragment(), args);
                } else if (categoryList.get(position).getCatName().equals("Prayer Requests")) {
                    args.putString("id", categoryList.get(2).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new PrayerRequestFragment(), args);
                } else {
                    args.putString("id", categoryList.get(position).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new JobsFragment(), args);
                }
            } else {
                if (categoryList.get(position).getCatName().equals("Birth News")) {
                    args.putString("id", categoryList.get(position).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new BirthNewsFragment(), args);
                } else if (categoryList.get(position).getCatName().equals("jobs")) {
                    args.putString("id", categoryList.get(position).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new JobsFragment(), args);
                } else if (categoryList.get(position).getCatName().equals("Prayer Requests")) {
                    args.putString("id", categoryList.get(position).getId());
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new PrayerRequestFragment(), args);
                } else {
                    args.putString("id", categoryList.get(position).getId());
                    CommonConst.Companion.setPositionMain(position);
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.tabSelection_Container, new JobsFragment(), args);
                }
            }
        }
    }


    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), ClassifiedCatModel.class);
        showTabBar();
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
