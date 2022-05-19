package com.netset.believeapp.Fragment.MarriageSection;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import android.view.inputmethod.InputMethodManager;
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
import java.util.Objects;

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
    @BindView(R.id.noDataFound)
    TextView noDataFound;
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
                    baseActivity.apiHitAndHandle.makeApiCall(GetAllUsers, AddFianceFragment.this, baseActivity);
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
        baseActivity.apiHitAndHandle.makeApiCall(getSearchUser, this,false, baseActivity);
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
        Objects.requireNonNull(searchET.getText()).clear();
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
        baseActivity.apiHitAndHandle.makeApiCall(GetAllUsers, this, baseActivity);

    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void loadBetrothedView() {
        hideKeyboard(requireActivity());
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
        baseActivity.apiHitAndHandle.makeApiCall(GetMarriageList, this, baseActivity);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
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
            if (result.getData().getBetrotheds().size()==0){
                noDataFound.setVisibility(View.VISIBLE);
            }else {
                noDataFound.setVisibility(View.GONE);

            }
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
            if (result.getData().size()==0){
                noDataFound.setVisibility(View.VISIBLE);
            }else {
                noDataFound.setVisibility(View.GONE);

            }
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
            if (result.getData().size()==0){
                noDataFound.setVisibility(View.VISIBLE);
            }else {
                noDataFound.setVisibility(View.GONE);

            }
          //  loadAddCoupleView();
        }

        else if(call == addCouple){
            try {
                JSONObject jsonObject= new JSONObject(""+object);
                CommonDialogs.customToast(getActivity(),jsonObject.getString("message"));
                if (!jsonObject.getString("status").equals("400")){
                    baseActivity.navigateFragmentTransaction(R.id.editViewContainer, new AddFianceFragment());
                }

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
        baseActivity.apiHitAndHandle.makeApiCall(addCouple, AddFianceFragment.this, baseActivity);

    }
}
