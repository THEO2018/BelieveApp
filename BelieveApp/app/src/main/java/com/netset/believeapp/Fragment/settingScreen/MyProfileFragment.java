package com.netset.believeapp.Fragment.settingScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.ProfileModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.EditProfileActivity;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 11/1/18.
 */

public class MyProfileFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.coverImage_IV)
    AppCompatImageView coverImageIV;
    @BindView(R.id.profileIMG_IV)
    AppCompatImageView profileIMGIV;
    @BindView(R.id.email_TV)
    AppCompatTextView emailTV;
    @BindView(R.id.maritalStatus_TV)
    AppCompatTextView maritalStatusTV;
    @BindView(R.id.labelDOB_TV)
    AppCompatTextView labelDOBTV;
    @BindView(R.id.dob_TV)
    AppCompatTextView dobTV;
    @BindView(R.id.labelGENDER_TV)
    AppCompatTextView labelGENDERTV;
    @BindView(R.id.gender_TV)
    AppCompatTextView genderTV;
    @BindView(R.id.labelCAMPUS_TV)
    AppCompatTextView labelCAMPUSTV;
    @BindView(R.id.campus_TV)
    AppCompatTextView campusTV;
    @BindView(R.id.labelSTATUS_TV)
    AppCompatTextView labelSTATUSTV;
    @BindView(R.id.status_TV)
    AppCompatTextView statusTV;
    @BindView(R.id.labelPROFESSION_TV)
    AppCompatTextView labelPROFESSIONTV;
    @BindView(R.id.profession_TV)
    AppCompatTextView professionTV;
    @BindView(R.id.aboutUsContainer)
    LinearLayoutCompat aboutUsContainer;
    @BindView(R.id.labelContact_TV)
    AppCompatTextView labelContactTV;
    @BindView(R.id.contactDivView)
    View contactDivView;
    @BindView(R.id.labelADDRESS_TV)
    AppCompatTextView labelADDRESSTV;
    @BindView(R.id.address_TV)
    AppCompatTextView addressTV;
    @BindView(R.id.addressDivView)
    View addressDivView;
    @BindView(R.id.labelMobile_TV)
    AppCompatTextView labelMobileTV;
    @BindView(R.id.mobPhone_TV)
    AppCompatTextView mobPhoneTV;
    @BindView(R.id.contactInfoContainer)
    LinearLayoutCompat contactInfoContainer;
    @BindView(R.id.parent)
    ConstraintLayout parent;
    Unbinder unbinder;

    /*ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> GetProfile;
    Handler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_profile_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        handler = new Handler();

        Bundle b = getArguments();
        if(b.getString("From").equals("setting")){
            ((HomeActivity) getActivity()).setToolbarTitle("Profile", true, true, false, null);
        }else{
            ((HomeActivity) getActivity()).setToolbarTitle("Profile", true, false, false, null);
        }
        GetProfileApi();
        handler.postDelayed(this::visibleLayout, 600);

        /*apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    private void visibleLayout(){
        parent.setVisibility(View.VISIBLE);
    }

    public void GetProfileApi(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetProfile =  baseActivity.apiInterface.GetMyProfile(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetProfile, this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onSuccess(Call call, Object object) {

        Log.e("Response body",">>>>>>>>>"+object.toString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ProfileModel result = gson.fromJson(object.toString(), ProfileModel.class);

        CommonDialogs.getSquareImage(getActivity(),result.getData().getProfileImage(),coverImageIV);
        CommonDialogs.getSquareImage(getActivity(),result.getData().getProfileImage(),profileIMGIV);
        emailTV.setText(result.getData().getEmail());
        maritalStatusTV.setText(result.getData().getMaritalStatus());



        String string = result.getData().getDob();
        String defaultTimezone = TimeZone.getDefault().getID();
        Date date = null;
        try {
            date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).parse(string.replaceAll("Z$", "+0000"));
            Log.e("string","string: " + string);
            Log.e("","defaultTimezone: " + defaultTimezone);
            Log.e("date is","date: " + (new SimpleDateFormat("yyyy-MM-dd")).format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        dobTV.setText(""+new SimpleDateFormat("yyyy-MM-dd").format(date));
        genderTV.setText(result.getData().getGender());
        campusTV.setText(result.getData().getCampus());
        if(result.getData().getUserStatus().equals("N")){
            statusTV.setText("Member");
        }else{
            statusTV.setText("Alumni");
        }
        professionTV.setText(result.getData().getProfession());
        addressTV.setText(result.getData().getStreet()+","+result.getData().getCity()+","+result.getData().getCountry());

      if(result.getData().getPhoneNumber().equals("")){
          mobPhoneTV.setText("--");
      }else{
          mobPhoneTV.setText(result.getData().getPhoneNumber());
      }



        ((HomeActivity) getActivity()).setToolbarTitle(result.getData().getFirstName().trim()+" "+result.getData().getLastName().trim(), true, true, false, null);
        setHasOptionsMenu(false);

        HomeActivity.actionText_TV.setBackgroundResource(R.drawable.ic_edit);
        HomeActivity.actionText_TV.setText("");
        HomeActivity.actionText_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(baseActivity, EditProfileActivity.class));
            }
        });
        parent.setBackgroundResource(0);

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
