package com.netset.believeapp.Fragment.userAuthentication;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.activity.UserAuthenticationActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_FORGOT_PASSWORD;

/**
 * Created by netset on 8/1/18.
 */

public class ForgotPasswordFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.emailOrPhone_ET)
    AppCompatEditText emailOrPhoneET;
    @BindView(R.id.resetPassword_BT)
    AppCompatButton resetPasswordBT;
    @BindView(R.id.parent)
    ConstraintLayout parent;
    Unbinder unbinder;
    /*ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> ForgotPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.forgot_password_fragment, null);
        ((UserAuthenticationActivity) getActivity()).setToolbarTitle(SC_FORGOT_PASSWORD, true, false, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parent.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        /*apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/

    }

    public void CallApi(String email) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("email", email);
        ForgotPassword =  baseActivity.apiInterface.Forgot_Password(map);
        baseActivity.apiHitAndHandle.makeApiCall(ForgotPassword, this, baseActivity);
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @OnClick(R.id.resetPassword_BT)
    public void onClick() {
        validateResetData();
    }

    private void validateResetData() {
        String emailOrPhone = emailOrPhoneET.getText().toString().trim();
        if (isValidText(emailOrPhone)) {
            if (isEmailValid(emailOrPhone)) {
                CallApi(emailOrPhone);
            } else {

                if (!isEmailValid(emailOrPhone)) {
                    showToast("Enter Valid Email or Phone Number");
                }
            }
        } else {
            showToast("Enter Email or Phone Number");
        }
    }

    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("response body",">>>>>>>"+object.toString());

        try {
            JSONObject jsonObject = new JSONObject(object.toString());
            CommonDialogs.customToast(getActivity(),jsonObject.getString("message"));
            emailOrPhoneET.setText("");
            getFragmentManager().popBackStack();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {
        showToast(""+errorMessage);

    }
}
