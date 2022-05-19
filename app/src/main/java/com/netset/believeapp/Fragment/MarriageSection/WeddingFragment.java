package com.netset.believeapp.Fragment.MarriageSection;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.communityAdapters.WeddingListAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.homeMenu.WebViewFragment;
import com.netset.believeapp.GsonModel.MarriagesModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 1/5/18.
 */

public class WeddingFragment extends BaseFragment implements ApiResponse {


    @BindView(R.id.weddingList_RV)
    RecyclerView weddingListRV;
    @BindView(R.id.lable_TV)
    TextView lableTV;
    Unbinder unbinder;

    Call<JsonObject> GetMarriageList;
    WeddingListAdapter weddingListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wedding_fragment, null);
        ((HomeActivity) getActivity()).setToolbarTitle("Wedding List", true, false, false, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lableTV.setVisibility(View.GONE);
        CallApi();
    }

    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetMarriageList = baseActivity.apiInterface.GetMarriages(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetMarriageList, this, baseActivity);
    }


    @Override
    public void onSuccess(Call call, Object object) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final MarriagesModel result = gson.fromJson(object.toString(), MarriagesModel.class);

        weddingListAdapter = new WeddingListAdapter(baseActivity, result.getData().getWeddingLists(), true, new WeddingListAdapter.OnWeddingItemClickListener() {
            @Override
            public void onWeddingItemClick(int position) {
                Uri uri = Uri.parse(result.getData().getWeddingLists().get(position).getUrlOnlineSite()); // missing 'http://' will cause crashed
                if (uri.toString().contains("http://")||uri.toString().contains("https://")){
                    Bundle args = new Bundle();
                    args.putString("newslink",result.getData().getWeddingLists().get(position).getUrlOnlineSite());
                    args.putString("newsname","Wedding");
                    baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new WebViewFragment(),args);

                }
                else {
                    uri = Uri.parse("http://"+uri);
                    Bundle args = new Bundle();
                    args.putString("newslink",uri.toString());
                    args.putString("newsname","Marriage");
                    baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new WebViewFragment(),args);
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(baseActivity, 2);
        weddingListRV.setLayoutManager(mLayoutManager);
        weddingListRV.setItemAnimator(new DefaultItemAnimator());
        weddingListRV.setAdapter(weddingListAdapter);
       /* weddingListRV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, weddingListRV,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Uri uri = Uri.parse(result.getData().getWeddingLists().get(position).getUrlOnlineSite()); // missing 'http://' will cause crashed
                        if (uri.toString().contains("http://")||uri.toString().contains("https://")){
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                        else {
                            uri = Uri.parse("http://"+uri);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));*/
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }

}
