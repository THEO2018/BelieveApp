package com.netset.believeapp.Fragment.settingScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_CHANGE_PASSWORD;

/**
 * Created by netset on 16/1/18.
 */

public class ChangePasswordFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.oldPassword_ET)
    AppCompatEditText oldPasswordET;
    @BindView(R.id.newPassword_ET)
    AppCompatEditText newPasswordET;
    @BindView(R.id.confirmPassword_ET)
    AppCompatEditText confirmPasswordET;
    @BindView(R.id.save_BTN)
    AppCompatButton saveBTN;
    @BindView(R.id.delete_TV)
    AppCompatTextView deleteTV;
    @BindView(R.id.parent)
    ConstraintLayout parent;
    Unbinder unbinder;

    /*ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> ChangePassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.change_password_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_CHANGE_PASSWORD, true, false, false, null);
        parent.setBackgroundResource(0);
       /* apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/
        oldPasswordET.setFilters(new InputFilter[] { filter });
        newPasswordET.setFilters(new InputFilter[] { filter });
        confirmPasswordET.setFilters(new InputFilter[] { filter });
        return rootView;
    }


    @OnClick({R.id.save_BTN, R.id.delete_TV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_BTN:
                validateChangePassword();
                break;
            case R.id.delete_TV:
                baseActivity.onBackPressed();
                break;
        }
    }
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

    private void validateChangePassword() {
        String newPassword = newPasswordET.getText().toString().trim();
        String oldPassword = oldPasswordET.getText().toString().trim();
        String confirmNewPassword = confirmPasswordET.getText().toString().trim();

        if (isValidText(oldPassword) && isValidText(newPassword) && isValidText(confirmNewPassword) &&
                newPassword.equals(confirmNewPassword)) {

            if (!oldPassword.equals(newPassword) && newPassword.equals(confirmNewPassword)) {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("old_password",oldPassword);
                map.put("new_password",newPassword);
                map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                ChangePassword =  baseActivity.apiInterface.Change_Password(map);
                baseActivity.apiHitAndHandle.makeApiCall(ChangePassword, this);



            } else {
                if (oldPassword.equals(newPassword)) {
                    showToast("Old Password and New Password cannot be same");
                }
                else if (!newPassword.equals(confirmNewPassword)) {
                    showToast("Confirm Password and New Password need to be same");
                }

            }
        } else {
            if (!isValidText(oldPassword)) {
                showToast("Enter Old Password");
            } else if (!isValidText(newPassword)) {
                showToast("Enter New Password");
            } else if (!isValidText(confirmNewPassword)) {
                showToast("Enter Confirm Password");
            }
            else if (oldPassword.equals(newPassword)) {
                showToast("Old Password and New Password cannot be same");
            }
            else if (!newPassword.equals(confirmNewPassword)) {
                showToast("Confirm Password and New Password need to be same");
            }

        }
    }

    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body",">>>>>>>>>"+object.toString());
        try {
            JSONObject jsonObject = new JSONObject(object.toString());
            newPasswordET.setText("");
            oldPasswordET.setText("");
            confirmPasswordET.setText("");
            CommonDialogs.customToast(getActivity(), jsonObject.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
