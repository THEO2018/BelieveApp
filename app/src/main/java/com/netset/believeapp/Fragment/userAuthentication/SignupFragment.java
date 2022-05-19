package com.netset.believeapp.Fragment.userAuthentication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Model.BaseModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.activity.UserAuthenticationActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_SIGNUP;

/**
 * Created by netset on 8/1/18.
 */

public class SignupFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.firstName_ET)
    AppCompatEditText firstNameET;
    @BindView(R.id.lastName_ET)
    AppCompatEditText lastNameET;
    @BindView(R.id.emailOrPhone_ET)
    AppCompatEditText emailOrPhoneET;
    @BindView(R.id.password_ET)
    AppCompatEditText passwordET;
    @BindView(R.id.confirmPassword_ET)
    AppCompatEditText confirmPasswordET;
    @BindView(R.id.termsPolicy_TV)
    AppCompatTextView termsPolicyTV;
    @BindView(R.id.signup_BT)
    AppCompatButton signupBT;
    @BindView(R.id.parent)
    ScrollView parent;
    Unbinder unbinder;
   /* ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/

    Call<JsonObject> signUp;
    String first_name,last_name,access_token,user_id,profile_status;

    InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (Character.isWhitespace(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }

    };

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
        View rootView = inflater.inflate(R.layout.signup_fragment, null);
        ((UserAuthenticationActivity) getActivity()).setToolbarTitle(SC_SIGNUP, true, false, null);
        unbinder = ButterKnife.bind(this, rootView);
        parent.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createHyperlink();
        /*apiInterface    = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/
//        firstNameET.setFilters(new InputFilter[]{new SignupFragment.EmojiExcludeFilter()});
//        lastNameET.setFilters(new InputFilter[]{new SignupFragment.EmojiExcludeFilter()});
        passwordET.setFilters(new InputFilter[] { filter });
        confirmPasswordET.setFilters(new InputFilter[] { filter });
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
        //baseActivity.apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }



    /**
     * Method to create Spannable String ...
     */
    private void createHyperlink() {

        String signupText = getResources().getString(R.string.label_terms_policy);

        SpannableString styledString = new SpannableString(signupText);
        styledString.setSpan(new RelativeSizeSpan(1f), 0, 21, 0);
        styledString.setSpan(new UnderlineSpan(), 48, 64, 0);
        styledString.setSpan(new UnderlineSpan(), 69, 83, 0);
        styledString.setSpan(clickableSpanTerms, 48, 64, 0);
        styledString.setSpan(clickableSpanPrivacyPolicy, 69, 83, 0);
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 48, 64, 0);
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 69, 83, 0);
        termsPolicyTV.setMovementMethod(LinkMovementMethod.getInstance());
        termsPolicyTV.setTextColor(getResources().getColor(R.color.white));

        termsPolicyTV.setText(styledString);
    }

    ClickableSpan clickableSpanTerms = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.blackcoupon.co"));
            startActivity(browserIntent);
        }
    };

    ClickableSpan clickableSpanPrivacyPolicy = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.blackcoupon.co"));
            startActivity(browserIntent);
        }
    };

    @OnClick({R.id.signup_BT,R.id.termsPolicy_TV})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signup_BT:
                checkSignupValidation();
                break;
            case R.id.termsPolicy_TV:
                Intent browserIntent = null;
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.blackcoupon.co"));
                getActivity().startActivity(browserIntent);
                break;
        }

    }

    private void checkSignupValidation() {
        String fName = firstNameET.getText().toString().trim();
        String lName = lastNameET.getText().toString().trim();
        String emailOrPhone = emailOrPhoneET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String confirmPassword = confirmPasswordET.getText().toString().trim();



        if (isValidText(fName) && isValidText(lName) && isValidText(emailOrPhone) && isValidText(password)
                && password.equals(confirmPassword)) {

            if (checkValidationsForEmailMobile(emailOrPhone) || checkValidationsForEmailMobile(emailOrPhone)) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("social_media_id", "1");
                map.put("first_name", fName);
                map.put("last_name", lName);
                map.put("password", password);
                map.put("register_type", "O");
                map.put("device_id", FirebaseInstanceId.getInstance().getToken());
                map.put("device_type", "A");
                map.put("app_version", "0.1");
                map.put("access_token","9a218c9b5dfdae8b5abc11a41905ed48");
            if(checkValidationsForEmailMobile(emailOrPhone)){
                map.put("email", emailOrPhone);
                map.put("registered_by", "E");
            }else{
                map.put("phone_number",emailOrPhone);
                map.put("registered_by", "P");
            }
                signUp =  baseActivity.apiInterface.Signup(map);
            baseActivity.apiHitAndHandle.makeApiCall(signUp, this, baseActivity);
            }
            else {
                showToast("Enter Valid Email or Phone Number");
            }
        } else {
          if (!isValidText(fName)) {
                CommonDialogs.customToast(getActivity(),"Enter First Name");

            } else if (!isValidText(lName)) {
                CommonDialogs.customToast(getActivity(),"Enter Last Name");
               // showToast("Enter Last Name");
            } else if (!isValidText(emailOrPhone)) {
                CommonDialogs.customToast(getActivity(),"Enter Email or Phone Number");
                //showToast("Enter Email or Phone Number");
            }  else  if (!checkValidationsForEmailMobile(emailOrPhone)) {
              CommonDialogs.customToast(getActivity(),"Enter Valid Email or Phone Number");
            } else if (!checkValidationsForEmailMobile(emailOrPhone)) {
                showToast("Enter Valid Email or Phone Number");
            } else if (!isValidText(password)) {
                CommonDialogs.customToast(getActivity(),"Enter Password");
               // showToast("Enter Password");
            } else if (!isValidText(password)) {
                CommonDialogs.customToast(getActivity(),"Enter Password");
              //  showToast("Enter Password");
            }
            else if (!checkValidation(emailOrPhone)){
                CommonDialogs.customToast(getActivity(),"Enter Valid Email or Number");
            }else if (!password.equals(confirmPassword)) {
                CommonDialogs.customToast(getActivity(),"Passwords does't match");
               // showToast("Passwords doesnot match");
            }
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean checkValidationsForEmailMobile(String email)
    {
        if(email.contains("@"))
        {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        else
        {
            if(!Pattern.matches("[a-zA-Z]+", email)) {
                return email.length() > 6 && email.length() <= 13;
            }
            return false;
        }
    }
    @Override
    public void onSuccess(Call call, Object object) {

        Log.e("response body",">>>>>>>"+object.toString());

        try {
            JSONObject jsonObject = new JSONObject(object.toString());
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");

            user_id = jsonObject1.getString("_id");
            access_token = jsonObject1.getString("access_token");
            first_name = jsonObject1.getString("first_name");
            last_name = jsonObject1.getString("last_name");
            profile_status = jsonObject1.getString("profile_status");


            BaseModel model = new BaseModel(getActivity());
            model.setAccess_token(access_token);
            model.setLogin_type("O");
            model.setUser_id(user_id);
            model.setUser_fname(first_name);
            model.setUser_lname(last_name);
            model.setProfile_status(profile_status);
            model.setLogin(true);
            CommonDialogs.customToast(getActivity(),""+jsonObject.getString("message"));
            getFragmentManager().popBackStack();

        } catch (JSONException e) {
            e.printStackTrace();
        }





      //  baseActivity.navigateFragmentTransaction(R.id.authViewContainer, new LoginFragment());
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
