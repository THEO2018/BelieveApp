package com.netset.believeapp.Fragment.classifiedSection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.JobsDetailModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 29/1/18.
 */

public class JobDescriptionFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.churchLOGO_IV)
    ImageView churchLOGOIV;
    @BindView(R.id.jobTitle_TV)
    TextView jobTitleTV;
    @BindView(R.id.description_TV)
    TextView descriptionTV;

    Unbinder unbinder;

    Call<JsonObject> GetCategoriesdetail;
    JobsDetailModel result;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.job_description_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle("Jobs", true, false, false, null);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CallApi();
    }


    public void CallApi() {
        Bundle b = getArguments();
        if(b!=null){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("classified_id",b.getString("jobid"));
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            GetCategoriesdetail = baseActivity.apiInterface.Get_ClassifiedDetail(map);
            baseActivity.apiHitAndHandle.makeApiCall(GetCategoriesdetail, this);
        }

    }


    @Override
    public void onSuccess(Call call, Object object) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), JobsDetailModel.class);

        jobTitleTV.setText(result.data.classifiedTitle);
        descriptionTV.setText(result.data.classified);
        CommonDialogs.getSquareImage(getActivity(),result.data.classifiedImage,churchLOGOIV);
        ((HomeActivity) getActivity()).setToolbarTitle(result.data.classifiedTitle, true, false, false, null);
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
