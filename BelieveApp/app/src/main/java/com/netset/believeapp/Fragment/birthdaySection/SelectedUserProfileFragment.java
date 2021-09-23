package com.netset.believeapp.Fragment.birthdaySection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.OtherProfileModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 19/1/18.
 */

public class SelectedUserProfileFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.parent)
    ScrollView parent;
    @BindView(R.id.imageConatiner)
    ConstraintLayout imageConatiner;
    @BindView(R.id.coverImage_IV)
    ImageView coverImageIV;
    @BindView(R.id.profileIMG_IV)
    ImageView profileIMGIV;
    @BindView(R.id.msg_TV)
    TextView msgTV;
    @BindView(R.id.aboutUsContainer)
    LinearLayoutCompat aboutUsContainer;
    @BindView(R.id.maritalStatus_TV)
    AppCompatTextView maritalStatusTV;
    @BindView(R.id.labelAge_TV)
    AppCompatTextView labelAgeTV;
    @BindView(R.id.age_TV)
    AppCompatTextView ageTV;
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
    @BindView(R.id.labelCITY_TV)
    AppCompatTextView labelCITYTV;
    @BindView(R.id.usrCity_TV)
    AppCompatTextView usrCityTV;
    @BindView(R.id.reportMember_TV)
    TextView reportMemberTV;

    String userId;
    Unbinder unbinder;
    Call<JsonObject> getOtherProfile;
    OtherProfileModel result;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.selected_user_profile_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CallApi();
    }

    public void CallApi() {
        Bundle b = getArguments();
        if (b != null) {
            this.userId = b.getString("userId");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("user_id", userId);
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            getOtherProfile = baseActivity.apiInterface.GetOtherProfile(map);
            baseActivity.apiHitAndHandle.makeApiCall(getOtherProfile, this);
        }
    }

    @Override
    public void onSuccess(Call call, Object object) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), OtherProfileModel.class);


        CommonDialogs.getSquareImage(getActivity(),result.getData().getProfileImage(),coverImageIV);
        CommonDialogs.getSquareImage(getActivity(),result.getData().getProfileImage(),profileIMGIV);

        maritalStatusTV.setText(result.getData().getMaritalStatus());


        ageTV.setText(""+getAge(result.getData().getDob()));
        genderTV.setText(result.getData().getGender());
        campusTV.setText(result.getData().getCampus());

        if(result.getData().getUserStatus().equals("N")){
            statusTV.setText("New Member");
        }else if(result.getData().getUserStatus().equals("A")){
            statusTV.setText("Alumni");
        }
        usrCityTV.setText(result.getData().getCity());
        ((HomeActivity) getActivity()).setToolbarTitle(result.getData().getFirstName()+" "+result.getData().getLastName(), true, false, false, null);


    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }

    public String SelectedDate(String mydate){
        String string = mydate;
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


        return ""+(new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }



    public static int getAge(String date) {

        int age = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            Date date1 = dateFormat.parse(date.replaceAll("Z$", "+0000"));
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            dob.setTime(date1);
            if (dob.after(now)) {
                throw new IllegalArgumentException("Can't be born in the future");
            }
            int year1 = now.get(Calendar.YEAR);
            int year2 = dob.get(Calendar.YEAR);
            age = year1 - year2;
            int month1 = now.get(Calendar.MONTH);
            int month2 = dob.get(Calendar.MONTH);
            if (month2 > month1) {
                age--;
            } else if (month1 == month2) {
                int day1 = now.get(Calendar.DAY_OF_MONTH);
                int day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    age--;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return age ;

    }

}
