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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.communityAdapters.AlumniMembersAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.birthdaySection.SelectedUserProfileFragment;
import com.netset.believeapp.GsonModel.AlumniModel;
import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.Utils.recyclerCustomisation.SimpleDividerItemDecorationBlue;
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

public class AlumniFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.newMemberList_RV)
    RecyclerView alumniMemberList_RV;
    Unbinder unbinder;

    Call<JsonObject> GetAlumni;
    List<BlogsModel> blogList = new ArrayList<>();
    AlumniModel result;
    AlumniMembersAdapter alumniMembersAdapter;

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
        loadAlumniList();
    }


    private void loadAlumniList() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetAlumni = baseActivity.apiInterface.Get_Alumni(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetAlumni, this, baseActivity);
    }

    @Override
    public void onSuccess(Call call, Object object) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), AlumniModel.class);

        alumniMembersAdapter = new AlumniMembersAdapter(baseActivity, result.getData());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        alumniMemberList_RV.setLayoutManager(mLayoutManager);
        alumniMemberList_RV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
        alumniMemberList_RV.setItemAnimator(new DefaultItemAnimator());
        alumniMemberList_RV.setAdapter(alumniMembersAdapter);
        alumniMembersAdapter.notifyDataSetChanged();


        alumniMemberList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, alumniMemberList_RV, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


                Bundle args = new Bundle();
                args.putString("userId",result.getData().get(position).getId());
                args.putString("from","alumni");
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
