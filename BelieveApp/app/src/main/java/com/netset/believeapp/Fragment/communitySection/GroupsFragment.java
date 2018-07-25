package com.netset.believeapp.Fragment.communitySection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.communityAdapters.GroupAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.groupSection.GroupProfilePageFragment;
import com.netset.believeapp.GsonModel.BlogData;
import com.netset.believeapp.GsonModel.BlogModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 18/1/18.
 */

public class GroupsFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.groupCount_TV)
    AppCompatTextView groupCountTV;
    @BindView(R.id.groupList_RV)
    RecyclerView groupListRV;
    @BindView(R.id.txt_nodata)
    TextView txtNodata;
    Unbinder unbinder;

    List<BlogModel> groupListGson;
    Call<JsonObject> Group;
    GroupAdapter groupAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.groups_fragment, null);
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
        GetGroupApi();
    }

    public void GetGroupApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        Group = baseActivity.apiInterface.GetGroup(map);
        baseActivity.apiHitAndHandle.makeApiCall(Group, this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onSuccess(Call call, Object object) {


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BlogData result = gson.fromJson(object.toString(), BlogData.class);

            groupListGson = result.getData();

            if(groupListGson.size()> 1){
                groupCountTV.setText(groupListGson.size() + " Groups Here");
            }
            else{
                groupCountTV.setText(groupListGson.size() + " Group Here");
            }

            groupAdapter = new GroupAdapter(baseActivity, groupListGson);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(baseActivity, 2);
            groupListRV.setLayoutManager(mLayoutManager);
            groupListRV.setItemAnimator(new DefaultItemAnimator());
            groupListRV.setAdapter(groupAdapter);


            if(groupListGson.size() ==0){
                txtNodata.setVisibility(View.VISIBLE);
                groupListRV.setVisibility(View.GONE);
            }else{
                txtNodata.setVisibility(View.GONE);
                groupListRV.setVisibility(View.VISIBLE);
            }


            groupListRV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, groupListRV,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            GroupProfilePageFragment groupProfilePageFragment = new GroupProfilePageFragment();
                            Bundle args = new Bundle();
                            args.putString("groupid", groupListGson.get(position).getId());
                            args.putString("groupname", groupListGson.get(position).getGroupName());
                            args.putString("groupimage", groupListGson.get(position).getGroupImage());
                            args.putString("groupmembers", groupListGson.get(position).getTotalMembers().toString());
                            args.putString("status", groupListGson.get(position).getStatus());
                            groupProfilePageFragment.setArguments(args);
                            baseActivity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.homeContainer, groupProfilePageFragment, "")
                                    .addToBackStack("group")
                                    .commit();


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
