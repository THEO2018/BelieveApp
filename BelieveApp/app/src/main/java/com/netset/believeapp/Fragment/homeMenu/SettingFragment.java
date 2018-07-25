package com.netset.believeapp.Fragment.homeMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.SettingAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.settingScreen.ChangePasswordFragment;
import com.netset.believeapp.Fragment.settingScreen.MyProfileFragment;
import com.netset.believeapp.Fragment.settingScreen.NotificationFragment;
import com.netset.believeapp.Model.HomeModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.Constants;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.BaseActivity;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.activity.UserAuthenticationActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_SETTING;

/**
 * Created by netset on 9/1/18.
 */

public class SettingFragment extends BaseFragment implements BaseActivity.OnConfirmationDialogClickListener, ApiResponse {

    @BindView(R.id.settingItem_LV)
    ListView settingItem_LV;
    Unbinder unbinder;
    GoogleApiClient mGoogleApiClient =null;
    /*ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> Logout,removeAccount,GetContent;
    AlertDialog alert11;
    String Content;

    int[] menuItemIcons = {R.drawable.ic_notification,
            R.drawable.ic_profile,
            R.drawable.ic_password,
            R.drawable.ic_share,
            R.drawable.ic_star,
            R.drawable.ic_delete,
            R.drawable.ic_logout};

    String[] settingItemName;
    ArrayList<HomeModel> settingItemList;

    SettingAdapter settingAdapter;

    String removeAcnt_Message = "Do you really want to remove this account?";
    String logout_Message = "Do you really want to logout?";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.setting_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_SETTING, false, false, false, null);
        baseActivity.RegisterConfirmationDialogListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/
        setAdapter();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetContent = baseActivity.apiInterface.GetSocialContent(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetContent, SettingFragment.this,false);
    }

    private void setAdapter() {
        settingItemList = new ArrayList<>();
        settingItemName = getResources().getStringArray(R.array.setting_item_names);
        for (int i = 0; i < settingItemName.length; i++) {
            HomeModel model = new HomeModel();
            model.setTitle(settingItemName[i]);
            model.setMenuIcon(menuItemIcons[i]);
            settingItemList.add(model);
        }
        settingAdapter = new SettingAdapter(baseActivity, settingItemList);
        settingItem_LV.setAdapter(settingAdapter);

        settingItem_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(position);
            }
        });
    }

    private void displayView(int position) {
        switch (position) {
            case 0:
                baseActivity.navigateFragmentTransaction(R.id.homeContainer, new NotificationFragment());
                break;

            case 1:
                Bundle args = new Bundle();
                args.putString("From", "setting");
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new MyProfileFragment(),args);
                break;

            case 2:
                if(GeneralValues.get_logintype(getActivity()).equals("F") || GeneralValues.get_logintype(getActivity()).equals("G")){

                    final MaterialDialog mMaterialDialog = new MaterialDialog(getActivity());
                    mMaterialDialog.setTitle("Alert");
                    mMaterialDialog.setMessage("This option is not available for Social Account Login.");
                    mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();

                        }
                    });

                    mMaterialDialog.show();
                }else{
                    baseActivity.navigateFragmentTransaction(R.id.homeContainer, new ChangePasswordFragment());
                }

                break;

            case 3:
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_TITLE,Content);
                share.putExtra(Intent.EXTRA_TEXT, "http://www.google.com");
                startActivity(Intent.createChooser(share, "Share App link"));
                break;

            case 4:
                CommonDialogs.customToast(getActivity(),"Coming Soon");
                break;

            case 5:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                View view = View.inflate(getActivity(), R.layout.alerrt_dialog_view, null);
                builder1.setView(view);
                builder1.setCancelable(true);

                alert11 = builder1.create();
                TextView message_TV = view.findViewById(R.id.message_TV);
                Button yes_TV = view.findViewById(R.id.yes_TV);
                Button no_TV = view.findViewById(R.id.no_TV);
                message_TV.setText(removeAcnt_Message);

                yes_TV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert11.cancel();
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                        removeAccount = baseActivity.apiInterface.RemoveAccount(map);
                        baseActivity.apiHitAndHandle.makeApiCall(removeAccount, SettingFragment.this);

                    }
                });

                no_TV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert11.cancel();
                    }
                });

                alert11.show();
                break;

            case 6:

                baseActivity.confirmationAlertDialog(logout_Message, SettingFragment.this, true);
                break;
        }
    }



    @Override
    public void onConfirmationDialogClick(boolean isLogout) {

        if(isLogout){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            Logout = baseActivity.apiInterface.Logout(map);
            baseActivity.apiHitAndHandle.makeApiCall(Logout, this);

        }
    }

    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());

        if(call == Logout) {
            try {
                LoginManager.getInstance().logOut();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (mGoogleApiClient != null) if (!mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Constants.FBImage ="";
            GeneralValues.set_Access_Key(getActivity(), "");
            GeneralValues.set_user_id(getActivity(), "");
            GeneralValues.set_loginbool(getActivity(), false);
            startActivity(new Intent(getActivity(), UserAuthenticationActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            getActivity().finish();
        }
        else if(call == removeAccount){
            try {
                LoginManager.getInstance().logOut();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (mGoogleApiClient != null) if (!mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Constants.FBImage ="";
            GeneralValues.set_Access_Key(getActivity(), "");
            GeneralValues.set_user_id(getActivity(), "");
            GeneralValues.set_loginbool(getActivity(), false);
            startActivity(new Intent(getActivity(), UserAuthenticationActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            getActivity().finish();

        }else if(call == GetContent){

            try {
                JSONObject jsonObject = new JSONObject(object.toString());

                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                String status = jsonObject.getString("status");

                if( status.equals("1")){
                    Content = jsonObject1.getString("content");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
