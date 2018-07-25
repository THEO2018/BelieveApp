package com.netset.believeapp.Fragment.userProfile;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.DialogAdapterPozo;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.EditProfileActivity;
import com.netset.believeapp.pozo.CountriesPozo;
import com.netset.believeapp.pozo.StatesPozo;
import com.netset.believeapp.retrofitManager.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.K_CAMPUS;
import static com.netset.believeapp.Utils.Constants.K_DATE_OF_BIRTH;
import static com.netset.believeapp.Utils.Constants.K_GENDER;
import static com.netset.believeapp.Utils.Constants.K_MARITAL_STATUS;
import static com.netset.believeapp.Utils.Constants.K_PROFESSION;
import static com.netset.believeapp.Utils.Constants.K_PROFILE_IMAGE;
import static com.netset.believeapp.Utils.Constants.K_STATUS;
import static com.netset.believeapp.Utils.Constants.SC_EDIT_PROFILE;

/**
 * Created by netset on 9/1/18.
 */

public class EditProfile_AddressFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.street_ET)
    AppCompatEditText streetET;
    @BindView(R.id.city_ET)
    AppCompatEditText cityET;
    @BindView(R.id.state_ET)
    AppCompatEditText stateET;
    @BindView(R.id.Country_ET)
    AppCompatEditText CountryET;
    @BindView(R.id.postalCode_ET)
    AppCompatEditText postalCodeET;
    @BindView(R.id.submit_BTN)
    AppCompatButton submitBTN;
    Unbinder unbinder;

    Bundle createProfile_Bundle;
    @BindView(R.id.parent)
    ConstraintLayout parent;
    private View rootView;

    String[] countryArray, stateArray;
    String stateOptional = "", country = "",firstname ="",lastname ="",timeZoneCurrent;
    private String maritalStatusOptional = "",image="" ,professionOptional = "", gender = "", status = "", campusOptional = "",
            dateOfBirth = "", street = "", city = "", postalCodeOptional = "", profileImageOptional = "";

    String selectCountryID, stateID, selectedState, selectedCountry;
    /*ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/

    Call<JsonObject> CreateProfile;

    CountriesPozo countriesPozo;
    StatesPozo statesPozo;

    private class EmojiExcludeFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
                String checkMe  = String.valueOf(source.charAt(i));
                Pattern pattern = Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,' ]*");
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
            rootView = inflater.inflate(R.layout.edit_profile_view_address, null);
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

        Calendar calendar = new GregorianCalendar();
        TimeZone timeZone = calendar.getTimeZone();
        timeZoneCurrent= (timeZone.getID());
        ((EditProfileActivity) getActivity()).setToolbarTitle(SC_EDIT_PROFILE, false, false, null);
        /*apiInterface    = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/
        streetET.setFilters(new InputFilter[]{new EditProfile_AddressFragment.EmojiExcludeFilter()});
        cityET.setFilters(new InputFilter[]{new EditProfile_AddressFragment.EmojiExcludeFilter()});
        if (getArguments() != null) {
            createProfile_Bundle = getArguments();

            streetET.setText(createProfile_Bundle.getString("street"));
            cityET.setText(createProfile_Bundle.getString("city"));
            CountryET.setText(createProfile_Bundle.getString("country"));
            stateET.setText(createProfile_Bundle.getString("state"));
            postalCodeET.setText(createProfile_Bundle.getString("postalcode"));

        }
        setUpCountryJson();


    }

    private void setUpCountryJson() {
        String countryJson = baseActivity.commonFunctions.loadJSONFromAsset(baseActivity, "countries.json");
        String statesJson = baseActivity.commonFunctions.loadJSONFromAsset(baseActivity, "states.json");
        countriesPozo = new Gson().fromJson(countryJson, CountriesPozo.class);
        statesPozo = new Gson().fromJson(statesJson, StatesPozo.class);
    }



    @OnClick({R.id.state_ET, R.id.Country_ET, R.id.submit_BTN})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.state_ET:
                showDailog(baseActivity, stateET, 2);
                break;
            case R.id.Country_ET:
                showDailog(baseActivity, CountryET, 1);
                break;
            case R.id.submit_BTN:
                validateCreateProfileData();
                break;
        }
    }

    private void validateCreateProfileData() {
        if (createProfile_Bundle != null) {
            image = createProfile_Bundle.getString(K_PROFILE_IMAGE);
            maritalStatusOptional = createProfile_Bundle.getString(K_MARITAL_STATUS);
            gender = createProfile_Bundle.getString(K_GENDER);
            professionOptional = createProfile_Bundle.getString(K_PROFESSION);
            if(createProfile_Bundle.getString(K_STATUS).equals("Member")){
                status ="N";
            }else{
                status ="A";
            }
            campusOptional = createProfile_Bundle.getString(K_CAMPUS);
            dateOfBirth = createProfile_Bundle.getString(K_DATE_OF_BIRTH);
            firstname = createProfile_Bundle.getString("firstname");
            lastname = createProfile_Bundle.getString("lastname");
        }

        street = streetET.getText().toString().trim();
        city = cityET.getText().toString().trim();
        postalCodeOptional = postalCodeET.getText().toString().trim();
        country = CountryET.getText().toString().trim();
        stateOptional = stateET.getText().toString().trim();


        if (!street.equals("") && !city.equals("") && !country.equals("")) {

        Call<JsonObject> call;

        //   BaseModel model = BaseModel.getInstance();
        File img_file = new File(image);
        if(image.equals("")){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("profession",professionOptional);
            map.put("gender",gender);
            map.put("user_status",status);
            map.put("campus",campusOptional);
            map.put("dob",dateOfBirth);
            map.put("street", street);
            map.put("city", city);
            map.put("state", stateOptional);
            map.put("country", country);
            map.put("first_name", firstname);
            map.put("last_name", lastname);
            map.put("time_zone",timeZoneCurrent);
            map.put("postal_code",postalCodeOptional);
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            map.put("profile_image","");
            map.put("marital_status",maritalStatusOptional);

            CreateProfile =  baseActivity.apiInterface.Edit_MyProfile(map);
            baseActivity.apiHitAndHandle.makeApiCall(CreateProfile, this);
        }
        else {
            HashMap<String, RequestBody> jsonbody = new HashMap<String, RequestBody>();
            HashMap<String, String> jsonbodyy = new HashMap<String, String>();

            jsonbody.put("profession", getRequestBodyParam(professionOptional));
            jsonbody.put("gender", getRequestBodyParam(gender));
            jsonbody.put("user_status", getRequestBodyParam(status));
            jsonbody.put("campus", getRequestBodyParam(campusOptional));
            jsonbody.put("dob", getRequestBodyParam(dateOfBirth));
            jsonbody.put("street", getRequestBodyParam(street));
            jsonbody.put("city", getRequestBodyParam(city));
            jsonbody.put("first_name", getRequestBodyParam(firstname));
            jsonbody.put("last_name", getRequestBodyParam(lastname));
            jsonbody.put("state", getRequestBodyParam(stateOptional));
            jsonbody.put("country", getRequestBodyParam(country));
            jsonbody.put("time_zone",getRequestBodyParam(timeZoneCurrent));
            jsonbody.put("postal_code", getRequestBodyParam(postalCodeOptional));
            jsonbody.put("marital_status", getRequestBodyParam(maritalStatusOptional));
            jsonbody.put("access_token", getRequestBodyParam(GeneralValues.get_Access_Key(getActivity())));

            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), img_file);
            jsonbody.put("profile_image\"; filename=\"" + img_file.getName() + "\" ", body);
            call = baseActivity.apiInterface.Edit_MyProfile2(jsonbody);
            baseActivity.apiHitAndHandle.makeApiCall(call, this);
        }
        }
        else {
            if (street.equals("")) {
                showToast("Enter Street");
            } else if (city.equals("")) {
                showToast("Enter City");
            } else if (!country.equals("")) {
                showToast("Select Country");
            }

    }
    }

    ListView optionList_LV;
    DialogAdapterPozo dialogListAdapter = null;
    public static RequestBody getRequestBodyParam(String value) {
        return RequestBody.create(MediaType.parse("text/form-data"), value);
    }
    public void showDailog(final Context mContext, final EditText editText, final int value) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);

        optionList_LV = dialog.findViewById(R.id.optionList_LV);
        final ImageView cancel_IV = dialog.findViewById(R.id.cancel_IV);


        final List<StatesPozo.State> stateList = new ArrayList<>();
        if (value == 1) {
            dialogListAdapter = new DialogAdapterPozo(mContext, countriesPozo.countries, true);
            optionList_LV.setAdapter(dialogListAdapter);
        } else if (value == 2) {

            for (int i = 0; i < statesPozo.states.size(); i++) {
                if (Integer.parseInt(selectCountryID) == Integer.parseInt(statesPozo.states.get(i).countryId)) {
                    stateList.add(statesPozo.states.get(i));
                }
            }

            dialogListAdapter = new DialogAdapterPozo(mContext, stateList);
            optionList_LV.setAdapter(dialogListAdapter);
        }

        dialog.show();

        optionList_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                if (value == 1) {
                    editText.setText(countriesPozo.countries.get(position).name);
                    selectCountryID = countriesPozo.countries.get(position).id;
                    selectedCountry = countriesPozo.countries.get(position).name;

                  /*  stateList.clear();
                    for (int i = 0; i < statesPozo.states.size(); i++) {
                        if (Integer.parseInt(selectCountryID) == Integer.parseInt(statesPozo.states.get(i).countryId)) {
                            stateList.add(statesPozo.states.get(i));
                        }
                    }
                    dialogListAdapter = new DialogAdapterPozo(mContext, stateList);
                    optionList_LV.setAdapter(dialogListAdapter);*/

                } else if (value == 2) {
                    editText.setText(stateList.get(position).name);
                    stateID = stateList.get(position).id;
                    selectedState = stateList.get(position).name;
                }
            }
        });

        cancel_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onSuccess(Call call, Object object) {

        Log.e("Response body",">>>>>>>>>"+object.toString());
        try {
            JSONObject jsonObject = new JSONObject(object.toString());
            CommonDialogs.customToast(getActivity(), jsonObject.getString("message"));
            getActivity().finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
