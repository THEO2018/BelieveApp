package com.netset.believeapp.Fragment.classifiedSection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.CategoryModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 31/1/18.
 */

public class CreatePrayerRequestFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.title_ET)
    EditText titleET;
    @BindView(R.id.slctCat_TV)
    TextView slctCatTV;
    @BindView(R.id.down_IV)
    ImageView downIV;
    @BindView(R.id.postAnonym_IV)
    SwitchCompat postAnonymIV;
    @BindView(R.id.slctCat_View)
    RelativeLayout slctCatView;
    @BindView(R.id.msg_ET)
    EditText msgET;
    @BindView(R.id.selecter_SPN)
    Spinner selecterSPN;
    @BindView(R.id.post_BTN)
    Button postBTN;
    Unbinder unbinder;
    String Checked ="true";
    Call<JsonObject> GetCategoriesList,AddPost;
    CategoryModel result;
    String CatId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_prayer_rqst_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        CallApi();
        postAnonymIV.setChecked(true);
        postAnonymIV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    Checked = "true";
                }else{
                    Checked = "false";
                }
            }
        });
    }


    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetCategoriesList = baseActivity.apiInterface.Get_PrayerCategory(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetCategoriesList, this, baseActivity);
    }

    @OnClick({R.id.post_BTN})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post_BTN:
                if(titleET.getText().toString().trim().equals("")){
                    CommonDialogs.customToast(getActivity(),"Please add title");
                }else if(msgET.getText().toString().trim().equals("")){
                    CommonDialogs.customToast(getActivity(),"Please add message");
                }
                else if(CatId==null||CatId.equals("")||CatId.isEmpty()){
                    CommonDialogs.customToast(getActivity(),"Please select any category");
                }else{
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("title", titleET.getText().toString());
                    map.put("category", CatId);
                    map.put("message", msgET.getText().toString());
                    map.put("post_as_anonymous", Checked);
                    map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                    AddPost = baseActivity.apiInterface.Add_Prayer(map);
                    baseActivity.apiHitAndHandle.makeApiCall(AddPost, this,true, baseActivity);
                }
               //baseActivity.onBackPressed();
                break;
        }
    }


    @Override
    public void onSuccess(Call call, Object object) {

        if (call == GetCategoriesList) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), CategoryModel.class);


            if (result.data.size()==0){
                showToast("category list not found.");
                return;
            }
            final String[] str = new String[result.data.size()];
            //  ArrayList<String> str = new ArrayList<>();
            final String[] inId = new String[result.data.size()];
            for (int i = 0; i < result.data.size(); i++) {

                str[i] = String.valueOf(result.data.get(i).name);
                inId[i] = String.valueOf(result.data.get(i).id);
            }
            CatId = inId[0];
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (getActivity(), R.layout.item_white_text, str); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selecterSPN.setAdapter(spinnerArrayAdapter);

            selecterSPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    ((TextView) view).setText(null);
                    CatId = inId[i];
                    slctCatTV.setText(result.data.get(i).name);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
        else if(call == AddPost){
            titleET.setText("");
            msgET.setText("");
            CatId   ="";
            Checked ="true";
            postAnonymIV.setChecked(true);
            baseActivity.navigateFragment_NoBackStack(R.id.tabSelection_Container, new PrayerRequestFragment());

        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
