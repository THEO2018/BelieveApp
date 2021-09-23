package com.netset.believeapp.Fragment.homeMenu;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netset.believeapp.Adapter.TabsAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.communitySection.AlumniFragment;
import com.netset.believeapp.Fragment.communitySection.BirthdayFragment;
import com.netset.believeapp.Fragment.communitySection.GroupsFragment;
import com.netset.believeapp.Fragment.communitySection.MarriageFragment;
import com.netset.believeapp.Fragment.communitySection.NewMembersFragment;
import com.netset.believeapp.Fragment.communitySection.PollsFragment;
import com.netset.believeapp.Fragment.communitySection.RecommendationFragment;
import com.netset.believeapp.Fragment.communitySection.SmallGroupsFragment;
import com.netset.believeapp.Model.TabBarModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.GpsTracker;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.activity.HomeActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.netset.believeapp.Utils.Constants.SC_COMMUNITY;

/**
 * Created by netset on 17/1/18.
 */

public class CommunityFragment extends BaseFragment {

    @BindView(R.id.tabList_RV)
    RecyclerView tabListRV;
    Unbinder unbinder;
    TabsAdapter tabsAdapter;
    String  stringLatitude,stringLongitude;
    ArrayList<TabBarModel> categoryList = new ArrayList();
    View rootView;
    int selected_TabPosition = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.community_fragment, null);
            unbinder = ButterKnife.bind(this, rootView);
            ((HomeActivity) getActivity()).setToolbarTitle(SC_COMMUNITY, false, false, false, null);
            showTabBar();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_COMMUNITY, false, false, false, null);


        new Handler().postDelayed(new Runnable() {
            public void run() {

                try {
                    GpsTracker gpsTracker = new GpsTracker(getActivity());

                    if (gpsTracker.getIsGPSTrackingEnabled())
                    {
                        stringLatitude    = String.valueOf(gpsTracker.getLatitude());
                        stringLongitude   = String.valueOf(gpsTracker.getLongitude());
                        Log.e(">>>>>>latitude",">>>>."+stringLatitude);
                        Log.e(">>>>>>logitude",">>>>."+stringLongitude);
                        GeneralValues.set_current_lat(getActivity(),stringLatitude);
                        GeneralValues.set_current_long(getActivity(),stringLongitude);
                    }
                    else
                    {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gpsTracker.showSettingsAlert();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 2000);

    }


    private void showTabBar() {

        if (categoryList.size() > 0)
            categoryList.clear();

        TabBarModel model = new TabBarModel();
        model.setCatName("Groups");
        model.setSelected(true);
        categoryList.add(model);

        TabBarModel model1 = new TabBarModel();
        model1.setCatName("Small Groups");
        model1.setSelected(false);
        categoryList.add(model1);

        TabBarModel model2 = new TabBarModel();
        model2.setCatName("Marriage");
        model2.setSelected(false);
        categoryList.add(model2);

        TabBarModel model3 = new TabBarModel();
        model3.setCatName("Birthdays");
        model3.setSelected(false);
        categoryList.add(model3);

        TabBarModel model4 = new TabBarModel();
        model4.setCatName("New Members");
        model4.setSelected(false);
        categoryList.add(model4);

        TabBarModel model5 = new TabBarModel();
        model5.setCatName("Alumni's");
        model5.setSelected(false);
        categoryList.add(model5);

        TabBarModel model6 = new TabBarModel();
        model6.setCatName("Polls");
        model6.setSelected(false);
        categoryList.add(model6);

        TabBarModel model7 = new TabBarModel();
        model7.setCatName("Recommendations");
        model7.setSelected(false);
        categoryList.add(model7);

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
        selected_TabPosition = 0;

        baseActivity.navigateFragment_NoBackStack(R.id.tabSelection_Container, new GroupsFragment());
        tabListRV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, tabListRV,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        highlightSelectedTab(position);
                        if(position ==0){
                            selected_TabPosition = 0;
                            baseActivity.navigateFragment_NoBackStack(R.id.tabSelection_Container, new GroupsFragment());
                        }
                        else if(position ==1){
                            selected_TabPosition = 1;
                            baseActivity.navigateFragment_NoBackStack(R.id.tabSelection_Container, new SmallGroupsFragment());
                        }
                        else if(position ==2){
                            selected_TabPosition = 2;
                            baseActivity.navigateFragment_NoBackStack(R.id.tabSelection_Container, new MarriageFragment());
                        }
                        else if(position ==3){
                            selected_TabPosition = 3;
                            baseActivity.navigateFragment_NoBackStack(R.id.tabSelection_Container, new BirthdayFragment());
                        }
                        else if(position == 4){
                            selected_TabPosition = 4;
                            baseActivity.navigateFragment_NoBackStack(R.id.tabSelection_Container, new NewMembersFragment());
                        }
                        else if(position == 5){
                            selected_TabPosition = 5;
                            baseActivity.navigateFragment_NoBackStack(R.id.tabSelection_Container, new AlumniFragment());
                        }
                        else if(position ==6){
                            selected_TabPosition = 6;
                            baseActivity.navigateFragment_NoBackStack(R.id.tabSelection_Container, new PollsFragment());
                        }
                        else if(position == 7){
                            selected_TabPosition = 7;
                            baseActivity.navigateFragment_NoBackStack(R.id.tabSelection_Container, new RecommendationFragment());
                        }
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

      //  displaySelectedTabView(position);
    }

    private void displaySelectedTabView(int position) {
        switch (position) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

            case 6:

                break;

            case 7:

                break;

        }
    }


}
