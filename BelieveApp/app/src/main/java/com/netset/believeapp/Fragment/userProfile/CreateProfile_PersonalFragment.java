package com.netset.believeapp.Fragment.userProfile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.Constants;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.BaseActivity;
import com.netset.believeapp.activity.UserAuthenticationActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.utils.PathUtils;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.netset.believeapp.Utils.Constants.K_CAMPUS;
import static com.netset.believeapp.Utils.Constants.K_DATE_OF_BIRTH;
import static com.netset.believeapp.Utils.Constants.K_GENDER;
import static com.netset.believeapp.Utils.Constants.K_MARITAL_STATUS;
import static com.netset.believeapp.Utils.Constants.K_PROFESSION;
import static com.netset.believeapp.Utils.Constants.K_PROFILE_IMAGE;
import static com.netset.believeapp.Utils.Constants.K_STATUS;
import static com.netset.believeapp.Utils.Constants.REQUEST_CODE_CHOOSE;
import static com.netset.believeapp.Utils.Constants.SC_CREATE_PROFILE;

/**
 * Created by netset on 9/1/18.
 */

public class CreateProfile_PersonalFragment extends BaseFragment implements BaseActivity.OnCreateProfileListener {

    @BindView(R.id.selectImage_IM)
    CircleImageView selectImageIM;
    @BindView(R.id.maritalStatus_ET)
    TextView maritalStatusET;
    @BindView(R.id.gender_ET)
    TextView genderET;
    @BindView(R.id.profession_ET)
    AppCompatEditText professionET;
    @BindView(R.id.status_ET)
    TextView statusET;
    @BindView(R.id.campus_ET)
    AppCompatEditText campusET;
    @BindView(R.id.dob_ET)
    AppCompatTextView dobET;
    @BindView(R.id.div)
    View div;
    @BindView(R.id.parent)
    ConstraintLayout parent;
    Unbinder unbinder;

    File videoFile, profileImage;
    List<Uri> mSelected;
    DatePickerDialog datePickerDialog;
    public String selectedFilePathOptional = "";

    String[] maritalArray, genderArray, statusArray;
    String maritalStatusOptional = "", gender = "", status = "", professionalOptional = "", campusOptional = "", dateOfBirth = "";
    private View rootView;



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
            rootView = inflater.inflate(R.layout.create_profile_view_personal, null);
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

            ((UserAuthenticationActivity) getActivity()).setToolbarTitle(SC_CREATE_PROFILE,
                    false, true, this);

       ((UserAuthenticationActivity) getActivity()).actionText_TV.setText("Next");

        professionET.setFilters(new InputFilter[]{new CreateProfile_PersonalFragment.EmojiExcludeFilter()});
        campusET.setFilters(new InputFilter[]{new CreateProfile_PersonalFragment.EmojiExcludeFilter()});

        if(GeneralValues.get_logintype(getActivity()).equals("F") || GeneralValues.get_logintype(getActivity()).equals("G")){
          Bundle b = getArguments();
          if(Constants.FBImage != ""){
             // selectedFilePathOptional = "file//:"+Constants.FBImage;
              profileImage = new File(Constants.FBImage);
              selectedFilePathOptional = ""+profileImage;
              Log.e(">>>>>>image in create",">>>>>>>"+Constants.FBImage);
              Log.e(">>>>>>file in create",">>>>>>>"+profileImage);
              MemoryCacheUtils.removeFromCache("" + profileImage, ImageLoader.getInstance().getMemoryCache());
              DiskCacheUtils.removeFromCache("" + profileImage, ImageLoader.getInstance().getDiskCache());
            //  CommonDialogs.getDisplayImage2(getActivity(),"file://"+Constants.FBImage,selectImageIM);
            //  Picasso.with(baseActivity).load(profileImage).into(selectImageIM);
              Picasso.with(getActivity())
                      .load(profileImage)
                      .skipMemoryCache()
                      .into(selectImageIM);
          }
        }
        else{
            selectedFilePathOptional = "";
            profileImage =null;
        }


        baseActivity.RegisterCreateProfileListener(this);

        parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                try {
                    CommonDialogs.hideSoftKeyboard(getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }



    @OnClick({R.id.selectImage_IM, R.id.dob_ET, R.id.maritalStatus_ET, R.id.gender_ET, R.id.status_ET})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectImage_IM:
                selectMedia();
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
                datePickerDialog.show();
               // baseActivity.datePicker(dobET, null, true);
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

            baseActivity.navigateFragmentTransaction_ARG(R.id.authViewContainer, new CreateProfile_AddressFragment(), bundle);
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

    private void selectMedia() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .countable(true).capture(true).video(false)
                .captureStrategy(new CaptureStrategy(true, "com.netset.believeapp"))
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen._120dp))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new PicassoEngine()).forResult(REQUEST_CODE_CHOOSE);
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

            UCrop.of(inputUri, Uri.fromFile(new File(getActivity().getExternalCacheDir(), "Believe.jpeg")))
                    .withAspectRatio(1, 1)
                    .start(getActivity(), this);
        } else {
            showToast("Only Images are Acceptable");

        }
    }

}
