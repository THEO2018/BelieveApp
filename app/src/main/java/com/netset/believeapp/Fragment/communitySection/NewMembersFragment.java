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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.communityAdapters.NewMembersAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.birthdaySection.SelectedUserProfileFragment;
import com.netset.believeapp.GsonModel.NewMemberModel;
import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 19/1/18.
 */

public class NewMembersFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.newMemberList_RV)
    RecyclerView newMemberList_RV;
    @BindView(R.id.nodata_TV)
    TextView nodataTV;
    Unbinder unbinder;

    Call<JsonObject> GetNewMembers;
    NewMembersAdapter newMembersAdapter;
    List<BlogsModel> blogList = new ArrayList<>();
    NewMemberModel result;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_members_fragment, null);
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
        loadNewMemberList();

    }

    private void loadNewMemberList() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetNewMembers = baseActivity.apiInterface.GetNew_Members(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetNewMembers, this, baseActivity);

    }


    @Override
    public void onSuccess(Call call, Object object) {


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), NewMemberModel.class);

        if(result.getData().size() == 0){
            nodataTV.setVisibility(View.VISIBLE);
            newMemberList_RV.setVisibility(View.GONE);
        }else{
            nodataTV.setVisibility(View.GONE);
            newMemberList_RV.setVisibility(View.VISIBLE);
        }

        newMembersAdapter = new NewMembersAdapter(baseActivity, NewMembersFragment.this, result.getData());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        newMemberList_RV.setLayoutManager(mLayoutManager);
        newMemberList_RV.setItemAnimator(new DefaultItemAnimator());
        newMemberList_RV.setAdapter(newMembersAdapter);
        newMembersAdapter.notifyDataSetChanged();


        newMemberList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, newMemberList_RV, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


                Bundle args = new Bundle();
                args.putString("userId",result.getData().get(position).getId());
                args.putString("from","new_member");
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new SelectedUserProfileFragment(),args);

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
