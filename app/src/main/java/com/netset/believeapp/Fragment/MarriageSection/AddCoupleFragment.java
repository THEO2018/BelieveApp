package com.netset.believeapp.Fragment.MarriageSection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.communityAdapters.AddCoupleAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.OtherUserModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.CouplesActivity;
import com.netset.believeapp.listeners.UserCallback;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 2/5/18.
 */

public class AddCoupleFragment extends BaseFragment implements ApiResponse, UserCallback {

    @BindView(R.id.couple_lay)
    LinearLayout coupleLay;
    @BindView(R.id.frstPrsn_IV)
    ImageView frstPrsnIV;
    @BindView(R.id.fstPrsn_Name_TV)
    TextView fstPrsnNameTV;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.scndPrsn_IV)
    ImageView scndPrsnIV;
    @BindView(R.id.scndPrsn_Name_TV)
    TextView scndPrsnNameTV;
    @BindView(R.id.memberList_RV)
    RecyclerView memberListRV;
    @BindView(R.id.addUsr_BTN)
    AppCompatButton addUsrBTN;

    Unbinder unbinder;

    OtherUserModel result;
    AddCoupleAdapter addCoupleAdapter;

    Call<JsonObject> getOtherUsers,addCouple;

    String id,image,name,Other_userid;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_couple_fragment, null);
        ((CouplesActivity) getActivity()).setToolbarTitle("Add a Couple", true, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addUsrBTN.setVisibility(View.GONE);
        Bundle b = getArguments();
        if(b!= null) {

            id = b.getString("id");
            name = b.getString("username");
            image = b.getString("userimage");

            fstPrsnNameTV.setText(name);
            CommonDialogs.getDisplayImage(getActivity(),image,frstPrsnIV,"#d3d3d3");
            CallApi(id);
        }

    }

    public void CallApi(String id) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("other_user_id", id);
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getOtherUsers = baseActivity.apiInterface.GetOtherUsers(map);
        baseActivity.apiHitAndHandle.makeApiCall(getOtherUsers, this, baseActivity);
    }

    @Override
    public void onUserClick(int position) {

        addUsrBTN.setVisibility(View.VISIBLE);
        scndPrsnNameTV.setText(result.getData().get(position).getFirstName()+" "+result.getData().get(position).getLastName());
        CommonDialogs.getDisplayImage(getActivity(), result.getData().get(position).getProfileImage(), scndPrsnIV, "#d3d3d3");
        Other_userid = result.getData().get(position).getId();

    }

    @OnClick({R.id.addUsr_BTN})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addUsr_BTN:

                Bundle b = getArguments();
                id = b.getString("id");
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("first_user_id", id);
                map.put("second_user_id", Other_userid);
                map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                addCouple = baseActivity.apiInterface.Add_Betrothed(map);
                baseActivity.apiHitAndHandle.makeApiCall(addCouple, this, baseActivity);

                break;

        }
    }

    @Override
    public void onSuccess(Call call, Object object) {

        if(call == getOtherUsers){

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), OtherUserModel.class);

            addCoupleAdapter = new AddCoupleAdapter(baseActivity, result.getData(),false);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
            memberListRV.setLayoutManager(mLayoutManager);
            memberListRV.setItemAnimator(new DefaultItemAnimator());
            memberListRV.setAdapter(addCoupleAdapter);

        }
        else if(call == addCouple){
            CommonDialogs.customToast(getActivity(),result.getMessage());
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }


}
