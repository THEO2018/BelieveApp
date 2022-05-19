package com.netset.believeapp.Fragment.homeMenu;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.StoreListAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.StoreListModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_STORE;

/**
 * Created by netset on 10/1/18.
 */

public class StoreFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.newsList)
    ListView NewsLV;
    @BindView(R.id.nodata_TV)
    TextView noDataText;
    Unbinder unbinder;
    StoreListModel result;
    /*ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> getStore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_storelist, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_STORE, false, false, false, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/
        CallApi();
    }

    public void CallApi() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getStore = baseActivity.apiInterface.Get_Store(map);
        baseActivity.apiHitAndHandle.makeApiCall(getStore, this, baseActivity);

    }


    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), StoreListModel.class);
        if(result.getData().size()==0){
            noDataText.setVisibility(View.VISIBLE);
        }else{
            noDataText.setVisibility(View.GONE);
        }
        StoreListAdapter adapter = new StoreListAdapter(baseActivity,result.getData());
        NewsLV.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        NewsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle args = new Bundle();
                args.putString("newslink",result.getData().get(i).getOnlineStoreUrl());
                args.putString("newsname",result.getData().get(i).getName());
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new WebViewFragment(),args);

            }
        });

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
