package com.netset.believeapp.Fragment.MarriageSection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.netset.believeapp.Adapter.communityAdapters.AddCoupleAdapter;
import com.netset.believeapp.Adapter.communityAdapters.CoupleListAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.AllUsersModel;
import com.netset.believeapp.GsonModel.MarriagesModel;
import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.CouplesActivity;
import com.netset.believeapp.listeners.UserCallback;
import com.netset.believeapp.retrofitManager.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_ADD_FIANCEE;

/**
 * Created by netset on 19/1/18.
 */

public class AddFianceFragment extends BaseFragment implements ApiResponse, UserCallback {
    @BindView(R.id.divView)
    View divView;
    @BindView(R.id.betrothed_TV)
    TextView betrothed_TV;
    @BindView(R.id.addCpl_TV)
    TextView addCpl_TV;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.cplList_RV)
    RecyclerView cplList_RV;
    @BindView(R.id.search_ET)
    AppCompatEditText searchET;
    @BindView(R.id.searchView_Container)
    RelativeLayout searchViewContainer;
    @BindView(R.id.memberList_RV)
    RecyclerView memberList_RV;
    @BindView(R.id.memberList_Container)
    ConstraintLayout memberList_Container;
    @BindView(R.id.betrothedContainer)
    RelativeLayout betrothedContainer;
    Unbinder unbinder;

    CoupleListAdapter coupleListAdapter;
    AddCoupleAdapter addCoupleAdapter;
    Call<JsonObject> GetMarriageList,GetAllUsers,getSearchUser,addCouple;
    List<BlogsModel> blogList = new ArrayList<>();
    String data;
    AllUsersModel result;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_fiance_fragment, null);
        ((CouplesActivity) getActivity()).setToolbarTitle(SC_ADD_FIANCEE, true, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadBetrothedView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                data = searchET.getText().toString().trim();

                if(data.equals("")){
                    try {
                        CommonDialogs.hideSoftKeyboard(getActivity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                    GetAllUsers = baseActivity.apiInterface.GetUsers(map);
                    baseActivity.apiHitAndHandle.makeApiCall(GetAllUsers, AddFianceFragment.this);
                }else{
                    CallSearchApi(searchET.getText().toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }



    public void CallSearchApi(String data){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_name",data);
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getSearchUser = baseActivity.apiInterface.SearchUser(map);
        baseActivity.apiHitAndHandle.makeApiCall(getSearchUser, this,false);
    }


    @OnClick({R.id.betrothed_TV, R.id.addCpl_TV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.betrothed_TV:
                onStart();
                break;
            case R.id.addCpl_TV:
              loadAddCoupleView();
                break;
        }
    }


    private void loadAddCoupleView() {
        memberList_Container.setVisibility(View.VISIBLE);
        betrothedContainer.setVisibility(View.GONE);
        betrothed_TV.setEnabled(true);
        addCpl_TV.setEnabled(false);
        addCpl_TV.setBackgroundResource(R.drawable.round_right_edge_shape);
        betrothed_TV.setBackgroundResource(0);
        addCpl_TV.setTextColor(getResources().getColor(R.color.black));
        betrothed_TV.setTextColor(getResources().getColor(R.color.white));

       // loadAddMemberList();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetAllUsers = baseActivity.apiInterface.GetUsers(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetAllUsers, this);

    }


    private void loadBetrothedView() {
        betrothedContainer.setVisibility(View.VISIBLE);
        memberList_Container.setVisibility(View.GONE);

        betrothed_TV.setEnabled(false);
        addCpl_TV.setEnabled(true);
        betrothed_TV.setBackgroundResource(R.drawable.round_left_edge_shape);
        addCpl_TV.setBackgroundResource(0);

        betrothed_TV.setTextColor(getResources().getColor(R.color.black));
        addCpl_TV.setTextColor(getResources().getColor(R.color.white));

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetMarriageList = baseActivity.apiInterface.GetMarriages(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetMarriageList, this);

    }


    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());


        if(call == GetMarriageList) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            MarriagesModel result = gson.fromJson(object.toString(), MarriagesModel.class);
            coupleListAdapter = new CoupleListAdapter(baseActivity, result.getData().getBetrotheds(), true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
            cplList_RV.setLayoutManager(mLayoutManager);
            cplList_RV.setItemAnimator(new DefaultItemAnimator());
            cplList_RV.setAdapter(coupleListAdapter);
            coupleListAdapter.notifyDataSetChanged();
          //  loadBetrothedView();

        }

        else if(call == GetAllUsers){

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), AllUsersModel.class);
            addCoupleAdapter = new AddCoupleAdapter(getActivity(), result.getData(),true,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
            memberList_RV.setLayoutManager(mLayoutManager);
            memberList_RV.setItemAnimator(new DefaultItemAnimator());
            memberList_RV.setAdapter(addCoupleAdapter);
            addCoupleAdapter.notifyDataSetChanged();

         //   loadAddCoupleView();
        }
        else if(call == getSearchUser){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), AllUsersModel.class);
            addCoupleAdapter = new AddCoupleAdapter(baseActivity, result.getData(),true,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
            memberList_RV.setLayoutManager(mLayoutManager);
            memberList_RV.setItemAnimator(new DefaultItemAnimator());
            memberList_RV.setAdapter(addCoupleAdapter);
            addCoupleAdapter.notifyDataSetChanged();
          //  loadAddCoupleView();
        }

        else if(call == addCouple){
            try {
                JSONObject jsonObject= new JSONObject(""+object);
                CommonDialogs.customToast(getActivity(),jsonObject.getString("message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }

    @Override
    public void onUserClick(int position) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("first_user_id", GeneralValues.get_user_id(getActivity()));
        map.put("second_user_id", result.getData().get(position).getId());
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        addCouple = baseActivity.apiInterface.Add_Betrothed(map);
        baseActivity.apiHitAndHandle.makeApiCall(addCouple, AddFianceFragment.this);
    }
}
