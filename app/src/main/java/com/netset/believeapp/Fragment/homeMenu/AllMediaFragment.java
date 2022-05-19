package com.netset.believeapp.Fragment.homeMenu;

import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.MediaAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.MediaListModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_MEDIA;

public class AllMediaFragment extends BaseFragment implements ApiResponse {

    private TextView txtNodata;
    private RecyclerView imageListRV;
    View rootView;
    MediaListModel resultList;
    MediaAdapter mediaAdapter;
    Call<JsonObject> GetMedia;

    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.allmedia_fragment, null);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_MEDIA, true, false, false, null);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtNodata = (TextView) view.findViewById(R.id.txt_nodata);
        imageListRV = (RecyclerView) view.findViewById(R.id.imageList_RV);


        CallApi();
    }



    public void CallApi() {
        Bundle b = getArguments();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("gallary_id", b.getString("id"));
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetMedia = baseActivity.apiInterface.Get_MediaOfGallary(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetMedia, this, baseActivity);

    }



    @Override
    public void onSuccess(Call call, Object object) {

        if(call == GetMedia) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            resultList = gson.fromJson(object.toString(), MediaListModel.class);

            imageListRV.setHasFixedSize(true);

            if(resultList.getData().size() ==0 ){
                txtNodata.setVisibility(View.VISIBLE);
            }else{
                txtNodata.setVisibility(View.GONE);
            }

            mediaAdapter = new MediaAdapter(getActivity(), resultList.getData(),"full","");
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            imageListRV.setLayoutManager(staggeredGridLayoutManager);
            imageListRV.setItemAnimator(new DefaultItemAnimator());
            imageListRV.setAdapter(mediaAdapter);


        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
