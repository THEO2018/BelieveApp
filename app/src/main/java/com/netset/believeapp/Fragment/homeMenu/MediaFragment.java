package com.netset.believeapp.Fragment.homeMenu;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.MediaAlbum_Adapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.MediaAlbumModel;
import com.netset.believeapp.GsonModel.MediaCategoryModel;
import com.netset.believeapp.GsonModel.MediaListModel;
import com.netset.believeapp.Model.PhotosModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_MEDIA;

/**
 * Created by netset on 10/1/18.
 */

public class MediaFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.divView)
    View divView;
    @BindView(R.id.imageList_RV)
    RecyclerView imageList_RV;
    @BindView(R.id.txt_nodata)
            TextView txtNodata;
    @BindView(R.id.cat_name)
            TextView category_name;
    Unbinder unbinder;

    MediaAlbum_Adapter mediaAdapter;
    List<PhotosModel> blogList = new ArrayList<>();
   HomeActivity homeActivity = new HomeActivity();
    MediaCategoryModel result;
    MediaListModel resultList;
    MediaAlbumModel mediaAlbumModel;
    /*ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> GetMedia,GetCategory,GetMediaOnClick;
    List<MediaCategoryModel.Datum> mediaCategoryList = new ArrayList<>();
  String[] str;
    String[] inId;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.media_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_MEDIA, false, false, false, null);
        ((HomeActivity) getActivity()).setToolbarSpinner();
        /*apiInterface    = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CallApi();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetCategory = baseActivity.apiInterface.Get_Categories(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetCategory, this,true, baseActivity);

    }


    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());

        if(call == GetCategory){

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), MediaCategoryModel.class);
            mediaCategoryList = result.data;

            str = new String[mediaCategoryList.size()];
            //  ArrayList<String> str = new ArrayList<>();
            inId = new String[mediaCategoryList.size()];
            for (int i = 0; i < mediaCategoryList.size(); i++) {

                str[i] = String.valueOf(mediaCategoryList.get(i).name);
                inId[i] = String.valueOf(mediaCategoryList.get(i).id);
            }


            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (getActivity(), R.layout.item_white_text, str); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            HomeActivity.selecter_SPN.setAdapter(spinnerArrayAdapter);

            HomeActivity.selecter_SPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                   // ((TextView)view).setText(null);
                    category_name.setText(str[i]);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("category", inId[i]);
                    map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                    GetMediaOnClick = baseActivity.apiInterface.Get_AllGallaries(map);
                    baseActivity.apiHitAndHandle.makeApiCall(GetMediaOnClick, MediaFragment.this, baseActivity);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        }

        else if(call == GetMedia) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            mediaAlbumModel = gson.fromJson(object.toString(), MediaAlbumModel.class);

          //  imageList_RV.setHasFixedSize(true);

            if(mediaAlbumModel.data.size() ==0 ){
                imageList_RV.setVisibility(View.GONE);
                txtNodata.setVisibility(View.VISIBLE);
            }else{
                imageList_RV.setVisibility(View.VISIBLE);
                txtNodata.setVisibility(View.GONE);
            }

            mediaAdapter = new MediaAlbum_Adapter(baseActivity, mediaAlbumModel.data,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
            imageList_RV.setLayoutManager(mLayoutManager);
            imageList_RV.setItemAnimator(new DefaultItemAnimator());
            imageList_RV.setAdapter(mediaAdapter);
            mediaAdapter.notifyDataSetChanged();


        }

        else if(call == GetMediaOnClick) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            mediaAlbumModel = gson.fromJson(object.toString(), MediaAlbumModel.class);

            imageList_RV.setHasFixedSize(true);

            if(mediaAlbumModel.data.size() ==0 ){
                txtNodata.setVisibility(View.VISIBLE);
            }else{
                txtNodata.setVisibility(View.GONE);
            }

            mediaAdapter = new MediaAlbum_Adapter(baseActivity, mediaAlbumModel.data,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
            imageList_RV.setLayoutManager(mLayoutManager);
            imageList_RV.setItemAnimator(new DefaultItemAnimator());
            imageList_RV.setAdapter(mediaAdapter);




        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
