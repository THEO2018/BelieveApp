package com.netset.believeapp.Fragment.groupSection;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.GroupPhotosAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Model.PhotosModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.retrofitManager.ApiResponse;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.utils.PathUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.REQUEST_CODE_CHOOSE;
import static com.netset.believeapp.retrofitManager.ApiHitAndHandle.pDialog;

/**
 * Created by netset on 22/1/18.
 */

public class GroupMediaFragment extends BaseFragment implements ApiResponse {


    @BindView(R.id.imageList_RV)
    RecyclerView imageList_RV;
    @BindView(R.id.txt_nodata)
            TextView txtNodata;
    Unbinder unbinder;

    String groupId;
    GroupPhotosAdapter mediaAdapter;
    List<PhotosModel> blogList = new ArrayList<>();

    File videoFile, profileImage;
    List<Uri> mSelected;
    public String selectedFilePathOptional = "";
    View rootView;

    Call<JsonObject> GetPhotos,AddPhoto;
    String user_id,group_id,photo_id,photo,user_image;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView==null) {

            rootView = inflater.inflate(R.layout.grp_media_fragment, null);
            unbinder = ButterKnife.bind(this, rootView);
            CallApi();
        }
        return rootView;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle b = getArguments();
        if(b.getString("joinstatus").equals("true")){

            imageList_RV.setVisibility(View.VISIBLE);
        }else{

            imageList_RV.setVisibility(View.GONE);
        }

    }



    public void CallApi(){
        Bundle b = getArguments();
        if(b != null) {
            this.groupId = b.getString("groupid");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("group_id",groupId);
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            GetPhotos = baseActivity.apiInterface.GetGroup_Photos(map);
            baseActivity.apiHitAndHandle.makeApiCall(GetPhotos,this);
        }
    }


    private void selectMedia() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .countable(true).capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.netset.believeapp"))
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen._120dp))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new PicassoEngine()).forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            sendBackImagePath(mSelected.get(0));
        }
    }

    void sendBackImagePath(Uri inputUri) {
        selectedFilePathOptional = PathUtils.getPath(baseActivity, inputUri);
        if (!inputUri.toString().contains("video") || !inputUri.toString().contains("Movies") || !inputUri.toString().contains("movies")) {
            videoFile = null;
            profileImage = new File(selectedFilePathOptional);
          //  Picasso.with(baseActivity).load(profileImage).into(selectImageIM);

            Bundle b = getArguments();
            if(b != null) {
                this.groupId = b.getString("groupid");
                HashMap<String, RequestBody> jsonbody = new HashMap<String, RequestBody>();
                jsonbody.put("group_id", getRequestBodyParam(groupId));
                jsonbody.put("access_token", getRequestBodyParam(GeneralValues.get_Access_Key(getActivity())));
                RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), profileImage);
                jsonbody.put("group_photo\"; filename=\"" + profileImage.getName() + "\" ", body);
                AddPhoto = baseActivity.apiInterface.AddGroup_Photo(jsonbody);
                baseActivity.apiHitAndHandle.makeApiCall(AddPhoto, this,false);
            }
        } else {
            showToast("Only Images are Acceptable");

        }
    }

    public static RequestBody getRequestBodyParam(String value) {
        return RequestBody.create(MediaType.parse("text/form-data"), value);
    }




    @Override
    public void onSuccess(Call call, Object object) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(object.toString());

            if(call == GetPhotos) {

                JSONObject jsonObj1 = jsonObject.getJSONObject("data");
                JSONObject jsonObj = jsonObj1.getJSONObject("userInfo");
                user_image = jsonObj.getString("profile_image");
                blogList.clear();
                JSONArray jsonArray = jsonObj1.getJSONArray("photos");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    photo = jsonObject1.getString("group_photo");
                    PhotosModel model = new PhotosModel(user_id, photo_id, photo, group_id);
                    blogList.add(model);
                }

                if(blogList.size() == 0){
                 txtNodata.setVisibility(View.VISIBLE);
                 imageList_RV.setVisibility(View.GONE);
                }else{
                    txtNodata.setVisibility(View.GONE);
                    imageList_RV.setVisibility(View.VISIBLE);
                }


                mediaAdapter = new GroupPhotosAdapter(baseActivity, blogList);
                staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                imageList_RV.setLayoutManager(staggeredGridLayoutManager);
                imageList_RV.setItemAnimator(new DefaultItemAnimator());
                imageList_RV.setAdapter(mediaAdapter);
                imageList_RV.setNestedScrollingEnabled(false);


            }
             if(call == AddPhoto){

                 if(pDialog.isShowing()){
                     pDialog.dismiss();

                 }

                 JSONObject jsonObj1 = jsonObject.getJSONObject("data");

                 photo = jsonObj1.getString("group_photo");
                 PhotosModel model = new PhotosModel(user_id, photo_id, photo, group_id);
                 blogList.add(model);
                 mediaAdapter.notifyDataSetChanged();
                 CommonDialogs.customToast(getActivity(),jsonObject.getString("message"));
              //  CallApi();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
