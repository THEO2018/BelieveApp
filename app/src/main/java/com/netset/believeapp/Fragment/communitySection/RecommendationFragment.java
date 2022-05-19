package com.netset.believeapp.Fragment.communitySection;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.communityAdapters.RecommendAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.PrefUtils;
import com.netset.believeapp.pozo.Data;
import com.netset.believeapp.pozo.RecommendedDataNew;
import com.netset.believeapp.retrofitManager.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Fragment.homeMenu.NotesFragment.EMOJI_FILTER;

/**
 * Created by netset on 19/1/18.
 */

public class RecommendationFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.recommendList_RV)
    RecyclerView recommendList_RV;
    @BindView(R.id.ask_BTN)
    Button askBTN;
    @BindView(R.id.noDataFound)
    TextView noDataFound;
    Unbinder unbinder;

    List<Data> recommendedList = new ArrayList<>();
    RecommendedDataNew result;

    RecommendAdapter recommendAdapter;
    private String user_id;
    private Call<JsonObject> recommendation;
    private Call<JsonObject> checkRecommendation;
    private Call<JsonObject> checkListPeople;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recommendation_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user_id= PrefUtils.INSTANCE.getSaveValue(requireContext(),"user_id");
        check_if_recommendation_exist();
        get_list_of_recommended_people();
    }

    private void loadRecommendList() {

        recommendAdapter = new RecommendAdapter(baseActivity, recommendedList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        recommendList_RV.setLayoutManager(mLayoutManager);
        recommendList_RV.setItemAnimator(new DefaultItemAnimator());
        recommendList_RV.setAdapter(recommendAdapter);
    }




    @OnClick({R.id.ask_BTN})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ask_BTN:
                showRecommendationDialog();
//                callAskForRecommendationApi(user_id);
                
                break;

        }
    }
    AlertDialog.Builder dialogBuilder;
    AlertDialog b;

    private void showRecommendationDialog() {
        dialogBuilder = new AlertDialog.Builder(baseActivity);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_note_view, null);
        dialogBuilder.setView(dialogView);
        final EditText title_ET = dialogView.findViewById(R.id.title_ET);
        final TextView dialogTitle_TV = dialogView.findViewById(R.id.dialogTitle_TV);
        final EditText message_ET = dialogView.findViewById(R.id.message_ET);
        Button addNotes_BTN = dialogView.findViewById(R.id.addNote_BTN);
        addNotes_BTN.setText("Send");
        dialogTitle_TV.setText("Recommendation");
        TextView cancel_TV = dialogView.findViewById(R.id.cancel_TV);
        ImageView dismiss_IV = dialogView.findViewById(R.id.dismiss_IV);
        title_ET.setFilters(new InputFilter[]{EMOJI_FILTER});
        b = dialogBuilder.create();
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        b.setCancelable(false);
        addNotes_BTN.setOnClickListener(v -> {
            String title = title_ET.getText().toString().trim();
            String message = message_ET.getText().toString().trim();
            validateNotesData(title, message);
            b.dismiss();
        });
        cancel_TV.setOnClickListener(v -> b.dismiss());
        dismiss_IV.setOnClickListener(v -> b.dismiss());
        b.show();
    }

    private void check_if_recommendation_exist(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        map.put("user_id", user_id);
        checkRecommendation = baseActivity.apiInterface.check_if_recommendation_exist(map);
        baseActivity.apiHitAndHandle.makeApiCall(checkRecommendation, this,true, baseActivity);
    }
    private void get_list_of_recommended_people(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        checkListPeople = baseActivity.apiInterface.get_list_of_recommended_people(map);
        baseActivity.apiHitAndHandle.makeApiCall(checkListPeople, this,true, baseActivity);
    }
    private void validateNotesData(String title, String message) {
        if (isValidText(title) && isValidText(message)) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            map.put("title", title);
            map.put("description", message);
            map.put("user_id", user_id);
            recommendation = baseActivity.apiInterface.ask_for_recommendation(map);
            baseActivity.apiHitAndHandle.makeApiCall(recommendation, this,true, baseActivity);
        }
        else {

            if (!isValidText(title)) {
                CommonDialogs.customToast(getActivity(),"Please Enter Title");
            } else if (!isValidText(message)) {
                CommonDialogs.customToast(getActivity(),"Please Enter Description");
            }else if(!isValidTitle(title)){
                CommonDialogs.customToast(getActivity(),"Please add at least one alphabet in the title");
            }
        }
    }
    public boolean isValidTitle(String title) {
        String TITLE_PATTERN = "^(?=.*\\d)(?=.*[a-zA-Z])$";
        Pattern pattern = Pattern.compile(TITLE_PATTERN);
        Matcher matcher = pattern.matcher(title);
        return matcher.matches();
    }
/*    private void callAskForRecommendationApi(String user_id) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        map.put("user_id", user_id);
        recommendation = baseActivity.apiInterface.ask_for_recommendation(map);
        baseActivity.apiHitAndHandle.makeApiCall(recommendation, this,true, baseActivity);
    }*/

    @Override
    public void onSuccess(Call call, Object object) {

        if(call == recommendation) {
            try {
                JSONObject jsonObject = new JSONObject(object.toString());
                String message = jsonObject.getString("message");
                CommonDialogs.customToast(requireContext(),message);
                check_if_recommendation_exist();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        else if (call==checkRecommendation){
            try {
                JSONObject jsonObject = new JSONObject(object.toString());
                Boolean isExist=jsonObject.getBoolean("isExist");
                if (!isExist){
                    askBTN.setText("Ask for New Recommendation");
                }
                else {
                    askBTN.setText("Recommendation Sent");
                    askBTN.setFocusable(false);
                    askBTN.setEnabled(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if (call==checkListPeople){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), RecommendedDataNew.class);
            recommendedList.addAll(Objects.requireNonNull(result.getData()));
            loadRecommendList();
            if (recommendedList.isEmpty()){
                noDataFound.setVisibility(View.VISIBLE);
            }else {
                noDataFound.setVisibility(View.GONE);

            }
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {
        Log.d("fail::>>",errorMessage);
    }
}
