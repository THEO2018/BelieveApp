package com.netset.believeapp.Fragment.userProfile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.ProfileModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.BaseActivity;
import com.netset.believeapp.activity.EditProfileActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.utils.PathUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.K_CAMPUS;
import static com.netset.believeapp.Utils.Constants.K_DATE_OF_BIRTH;
import static com.netset.believeapp.Utils.Constants.K_GENDER;
import static com.netset.believeapp.Utils.Constants.K_MARITAL_STATUS;
import static com.netset.believeapp.Utils.Constants.K_PROFESSION;
import static com.netset.believeapp.Utils.Constants.K_PROFILE_IMAGE;
import static com.netset.believeapp.Utils.Constants.K_STATUS;
import static com.netset.believeapp.Utils.Constants.REQUEST_CODE_CHOOSE;
import static com.netset.believeapp.Utils.Constants.SC_EDIT_PROFILE;

/**
 * Created by netset on 2/2/18.
 */

public class EditProfile_PersonalFragment extends BaseFragment implements BaseActivity.OnCreateProfileListener, ApiResponse {


    Unbinder unbinder;
    @BindView(R.id.selectImage_IM)
    CircleImageView selectImageIM;
    @BindView(R.id.textView6)
    AppCompatTextView textView6;
    @BindView(R.id.firstName_ET)
    EditText firstNameET;
    @BindView(R.id.input_layout_fName)
    TextInputLayout inputLayoutFName;
    @BindView(R.id.lastName_ET)
    EditText lastNameET;
    @BindView(R.id.input_layout_lName)
    TextInputLayout inputLayoutLName;
    @BindView(R.id.nameView)
    LinearLayout nameView;
    @BindView(R.id.maritalStatus_ET)
    EditText maritalStatusET;
    @BindView(R.id.msView)
    TextInputLayout msView;
    @BindView(R.id.gender_ET)
    EditText genderET;
    @BindView(R.id.genderView)
    TextInputLayout genderView;
    @BindView(R.id.profession_ET)
    EditText professionET;
    @BindView(R.id.professionView)
    TextInputLayout professionView;
    @BindView(R.id.status_ET)
    EditText statusET;
    @BindView(R.id.statusView)
    TextInputLayout statusView;
    @BindView(R.id.campus_ET)
    EditText campusET;
    @BindView(R.id.campusView)
    TextInputLayout campusView;
    @BindView(R.id.dob_ET)
    EditText dobET;
    @BindView(R.id.bdayView)
    TextInputLayout bdayView;
    @BindView(R.id.parent)
    ConstraintLayout parent;
    private View rootView;

    /*ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> GetProfile;
    List<Uri> mSelected;
    DatePickerDialog datePickerDialog;
    public String selectedFilePathOptional = "",street,city,country,state,postalcode;
    File videoFile = null, profileImage = null;
    public String selectedFilePath = "";
    String[] maritalArray, genderArray, statusArray;
    String maritalStatusOptional = "", gender = "", status = "", professionalOptional = "", campusOptional = "", dateOfBirth = "";



    private class EmojiExcludeFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
                String checkMe  = String.valueOf(source.charAt(i));
                Pattern pattern = Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz' ]*");
                Matcher matcher = pattern.matcher(checkMe);
                boolean valid   = matcher.matches();
                if (!valid) {
                    Log.d("", "invalid");
                    return "";
                }
            }
            return null;
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.edit_profile_personal_fragment, null);
        }
        unbinder = ButterKnife.bind(this, rootView);
        parent.setBackgroundResource(0);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/

        firstNameET.setFilters(new InputFilter[]{new EditProfile_PersonalFragment.EmojiExcludeFilter()});
        lastNameET.setFilters(new InputFilter[]{new EditProfile_PersonalFragment.EmojiExcludeFilter()});
        professionET.setFilters(new InputFilter[]{new EditProfile_PersonalFragment.EmojiExcludeFilter()});
        campusET.setFilters(new InputFilter[]{new EditProfile_PersonalFragment.EmojiExcludeFilter()});
        ((EditProfileActivity) getActivity()).setToolbarTitle(SC_EDIT_PROFILE, false, true, this);

       GetProfileApi();
    }



    public void GetProfileApi(){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetProfile =  baseActivity.apiInterface.GetMyProfile(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetProfile, this);
    }

    @OnClick({R.id.selectImage_IM, R.id.maritalStatus_ET, R.id.gender_ET, R.id.status_ET, R.id.dob_ET})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectImage_IM:
                selectMedia();
                break;
            case R.id.maritalStatus_ET:
                maritalArray = getResources().getStringArray(R.array.marital_status_array);
                baseActivity.commonFunctions.showDailog(baseActivity, maritalArray, maritalStatusET, null);
                break;
            case R.id.gender_ET:
                genderArray = getResources().getStringArray(R.array.gender_array);
                baseActivity.commonFunctions.showDailog(baseActivity, genderArray, genderET, null);
                break;
            case R.id.status_ET:
                statusArray = getResources().getStringArray(R.array.status_array);
                baseActivity.commonFunctions.showDailog(baseActivity, statusArray, statusET, null);
                break;
            case R.id.dob_ET:


                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                c.add(Calendar.YEAR, -10);                //Goes 10 Year Back in time ^^
                long upperLimit = c.getTimeInMillis();

                datePickerDialog = new DatePickerDialog(getActivity(), R.style.datepicker,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                int temp_month = monthOfYear + 1;
                                int temp_year = year;

                                dobET.setText(year+"-"+temp(String.valueOf(monthOfYear + 1))+"-"+dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);

                 datePickerDialog.getDatePicker().setMaxDate(upperLimit);
             //    datePickerDialog.getDatePicker().setMinDate(upperLimit);
                datePickerDialog.show();
              //  baseActivity.datePicker(dobET, null, true);
                break;
        }
    }


    private String temp(String month) {
        String t = "";
        if (Integer.parseInt(month) < 9) {
            t = "0" + month;
        } else {
            t = month;
        }
        return t;
    }


    private void selectMedia() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .countable(true).capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.netset.believeapp"))
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen._120dp))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine()).forResult(REQUEST_CODE_CHOOSE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            sendBackImagePath(mSelected.get(0));
        }

        if (resultCode == getActivity().RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                selectedFilePathOptional = resultUri.getEncodedPath();
                profileImage = new File(selectedFilePathOptional);
                Log.e("path--------", profileImage + "");

                MemoryCacheUtils.removeFromCache("" + profileImage, ImageLoader.getInstance().getMemoryCache());
                DiskCacheUtils.removeFromCache("" + profileImage, ImageLoader.getInstance().getDiskCache());
                Picasso.with(getActivity())
                        .load(profileImage)
                        .skipMemoryCache()
                        .into(selectImageIM);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    void sendBackImagePath(Uri inputUri) {
        selectedFilePathOptional = PathUtils.getPath(baseActivity, inputUri);
        if (!inputUri.toString().contains("video") || !inputUri.toString().contains("Movies") || !inputUri.toString().contains("movies")) {
            videoFile = null;
         //   profileImage = new File(selectedFilePathOptional);
        //    Picasso.with(baseActivity).load(profileImage).into(selectImageIM);
            UCrop.of(inputUri, Uri.fromFile(new File(getActivity().getExternalCacheDir(), "Believe.jpeg")))
                    .withAspectRatio(1, 1)
                    .start(getActivity(), this);

        } else {
            showToast("Only Images are Acceptable");

        }
    }

    @Override
    public void onCreateProfileResponse() {
        professionalOptional = professionET.getText().toString().trim();
        campusOptional = campusET.getText().toString().trim();
        dateOfBirth = dobET.getText().toString().trim();
        maritalStatusOptional = maritalStatusET.getText().toString().trim();
        gender = genderET.getText().toString().trim();
        status = statusET.getText().toString().trim();

        if (isValidText(gender) && isValidText(status)
                && isValidText(dateOfBirth)) {

            Bundle bundle = new Bundle();
            bundle.putString(K_PROFILE_IMAGE, selectedFilePathOptional);
            bundle.putString(K_MARITAL_STATUS, maritalStatusOptional);
            bundle.putString(K_GENDER, gender);
            bundle.putString(K_PROFESSION, professionalOptional);
            bundle.putString(K_STATUS, status);
            bundle.putString(K_CAMPUS, campusOptional);
            bundle.putString(K_DATE_OF_BIRTH, dateOfBirth);
            bundle.putString("street",street);
            bundle.putString("city",city);
            bundle.putString("country",country);
            bundle.putString("state",state);
            bundle.putString("postalcode",postalcode);
            bundle.putString("firstname",firstNameET.getText().toString().trim());
            bundle.putString("lastname",lastNameET.getText().toString().trim());

            baseActivity.navigateFragmentTransaction_ARG(R.id.editViewContainer, new EditProfile_AddressFragment(), bundle);
        } else {
            if (!isValidText(gender)) {
                showToast("Select Gender");
            } else if (!isValidText(status)) {
                showToast("Select Status");
            } else if (!isValidText(dateOfBirth)) {
                showToast("Select Date of Birth");
            }
        }
    }

    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body",">>>>>>>>>"+object.toString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ProfileModel result = gson.fromJson(object.toString(), ProfileModel.class);
        Picasso.with(getActivity())
                .load(result.getData().getProfileImage())
                .skipMemoryCache()
                .into(selectImageIM);
      //  CommonDialogs.getDisplayImage(getActivity(),result.getData().getProfileImage(),selectImageIM);
        firstNameET.setText(result.getData().getFirstName());
        lastNameET.setText(result.getData().getLastName());
        maritalStatusET.setText(result.getData().getMaritalStatus());
        genderET.setText(result.getData().getGender());
        professionET.setText(result.getData().getProfession());

        if(result.getData().getUserStatus().equals("N")){
            statusET.setText("Member");
        }else{
            statusET.setText("Alumni");
        }

        campusET.setText(result.getData().getCampus());

        dobET.setText(SelectedDate(result.getData().getDob()));
        street  = result.getData().getStreet();
        city    = result.getData().getCity();
        country = result.getData().getCountry();
        state   = result.getData().getState();
        postalcode = result.getData().getPostalCode();

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

}
