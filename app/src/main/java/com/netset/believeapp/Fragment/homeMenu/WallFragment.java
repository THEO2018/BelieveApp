package com.netset.believeapp.Fragment.homeMenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.WallAdapter;
import com.netset.believeapp.BelieveApplication;
import com.netset.believeapp.CommonConst;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GalaryFilter;
import com.netset.believeapp.Model.PostsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.VideoUtil;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.activity.ShowFullPostActivity;
import com.netset.believeapp.callbacks.CheckPermissionInterface;
import com.netset.believeapp.callbacks.CommentClickCallback;
import com.netset.believeapp.listeners.LikeClickCallback;
import com.netset.believeapp.retrofitManager.ApiResponse;
import com.simform.videooperations.CallBackOfQuery;
import com.simform.videooperations.FFmpegCallBack;
import com.simform.videooperations.LogMessage;
import com.simform.videooperations.Statistics;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.materialdialog.MaterialDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.REQUEST_CODE_CHOOSE;
import static com.netset.believeapp.Utils.Constants.SC_WALL;

/**
 * Created by netset on 10/1/18.
 */

public class WallFragment extends BaseFragment implements CommentClickCallback, ApiResponse, LikeClickCallback, CheckPermissionInterface {
    @BindView(R.id.profile_image_IV)
    CircleImageView profileImageIV;
    @BindView(R.id.status_ET)
    EditText statusET;
    @BindView(R.id.uploadPhoto_TV)
    AppCompatTextView uploadPhotoTV;
    @BindView(R.id.uploadVideo_TV)
    AppCompatTextView uploadVideoTV;
    @BindView(R.id.button)
    AppCompatButton button;
    @BindView(R.id.status_Container)
    ConstraintLayout statusContainer;
    @BindView(R.id.postList_RV)
    RecyclerView postList_RV;
    @BindView(R.id.upload_img_lay)
    RelativeLayout uploadLay;
    @BindView(R.id.upload_img_post)
    AppCompatImageView uploadImg;

    @BindView(R.id.txt_nodata)
    TextView txtNodata;
    Unbinder unbinder;

    WallAdapter wallAdapter;
    List<PostsModel> blogList = new ArrayList<>();
    String isClicked;
    File videoFile = null, profileImage = null;
    List<Uri> mSelected;
    List<String> mSelected2;
    public String selectedFilePath = "";
   /* ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/

    Call<JsonObject> Detail, LikePost, AddPost, AddPost1;
    String user_Id, user_Image, user_Firstname, user_Lastname, other_name, other_image, other_id, post_id, post_thumb, post_type, post_title, post_time, post_message, post_image, post_commentcount,
            post_likecount, other_userid, other_username, other_user_image, other_usercomment, other_commentid, like_status, comment_img, media_status;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wall_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_WALL, false, false, false, null);
        CallApi(true);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/

        checkPermission=this;
      //  setBlogAdapter();
    }

 /*   private void setBlogAdapter() {
        wallAdapter = new WallAdapter(baseActivity, blogList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
        postList_RV.setLayoutManager(mLayoutManager);
        postList_RV.setItemAnimator(new DefaultItemAnimator());
        postList_RV.setAdapter(wallAdapter);
        postList_RV.setNestedScrollingEnabled(false);
    }*/


    public void CallApi(Boolean loader) {

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            Detail = baseActivity.apiInterface.GetWallPost(map);
//        baseActivity.apiHitAndHandle.makeApiCall(Detail, loader,this, baseActivity);
        baseActivity.apiHitAndHandle.makeApiCall(Detail,this, baseActivity);


    }

    public static ProgressDialog pDialog;

    public void showDialog() {
        try {
            pDialog = new ProgressDialog(baseActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage("Please wait...\nYour video is compressing. It will take time.");
            pDialog.setCancelable(false);
            pDialog.setIndeterminate(true);
            pDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void dismissDialog() {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onCommentClick(int position) {

      //  showToast("Clicked Item : " + position);

        startActivity(new Intent(getActivity(), ShowFullPostActivity.class)
                .putExtra("from","wall")
                .putExtra("postid",blogList.get(position).getPost_id()));

     /*   ShowPostFullFragment showPostFullFragment = new ShowPostFullFragment();
        Bundle args = new Bundle();
        args.putString("from","wall");
        args.putString("postid", blogList.get(position).getPost_id());
        showPostFullFragment.setArguments(args);
        baseActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeContainer, showPostFullFragment, "")
                .addToBackStack("wall")
                .commit();*/


    }

    @Override
    public void onLikeClick(int position) {

        blogList.get(position).getPost_id();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type","wall");
        map.put("group_post_id", blogList.get(position).getPost_id());
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        if (blogList.get(position).getLike_status().equals("true")) {
            map.put("like_status", "U");
        } else {
            map.put("like_status", "L");
        }
        LikePost = baseActivity.apiInterface.AddGroupPostLike(map);
        baseActivity.apiHitAndHandle.makeApiCall(LikePost, this, true, baseActivity);


    }

    private void selectMedia() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .countable(true).capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.netset.believeapp"))
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen._120dp))
                .showSingleMediaType(true)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new PicassoEngine()).forResult(REQUEST_CODE_CHOOSE);
    }


    private void selectMedia2(){
        Matisse.from(this)
                .choose(MimeType.ofVideo(), false)
                .countable(true)
                .addFilter(new GalaryFilter())
                .captureStrategy(new CaptureStrategy(true, "com.netset.believeapp"))
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen._120dp))
                .showSingleMediaType(true)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new PicassoEngine()).forResult(2);
    }

    private File cachedFile;
    String FILENAME_FORMAT="yyyy-MM-dd-HH-mm-ss-SSS";
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
        //   if(data != null && data.getData() != null){
                try {
                    sendBackImagePath(mSelected.get(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
          //  }
        }
        if(requestCode == 2 && resultCode == getActivity().RESULT_OK){
          showDialog();
            mSelected2 = Matisse.obtainPathResult(data);
            //   if(data != null && data.getData() != null){
            try {
                Log.e("pathVideo",mSelected2.get(0));
                cachedFile= new File(VideoUtil.INSTANCE.getVideoStoragePath(baseActivity), new SimpleDateFormat(FILENAME_FORMAT, Locale.getDefault())
                        .format(System.currentTimeMillis())+".mp4");
//                sendBackVideoPath(mSelected.get(0));
                String[] videoPath=VideoUtil.INSTANCE.compressor(mSelected2.get(0),cachedFile.getAbsolutePath());
                Log.e("pathvodep",""+videoPath[2]);
                new CallBackOfQuery().callQuery(baseActivity, videoPath, new FFmpegCallBack() {
                    @Override
                    public void process(@NotNull LogMessage logMessage) {
                        Log.e("AbcdProcess",logMessage.toString());
                    }

                    @Override
                    public void statisticsProcess(@NotNull Statistics statistics) {

                    }

                    @Override
                    public void success() {
                        dismissDialog();
                        Log.e("Abcdsuccess","success");
                        Log.e("Abcdsuccess","success"+cachedFile.getAbsolutePath());
//                        sendBackVideoPath(Uri.parse(cachedFile.getAbsolutePath()));
                        videoFile= new File(cachedFile.getAbsolutePath());
                        selectedFilePath = ""+videoFile;
                        isClicked="Video";
                        Bitmap bitmap2 = ThumbnailUtils.createVideoThumbnail(videoFile.getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                        uploadLay.setVisibility(View.VISIBLE);
                        uploadImg.setImageBitmap(bitmap2);

                    }

                    @Override
                    public void cancel() {
                        Log.e("AbcdCancel","cancel");
                        dismissDialog();

                    }

                    @Override
                    public void failed() {
                        Log.e("AbcdFailed","failed");
                        dismissDialog();


                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("AbcdExcep","failed"+e.toString());
                dismissDialog();

            }
        }

        if (resultCode == getActivity().RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                selectedFilePath = resultUri.getEncodedPath();
                profileImage = new File(selectedFilePath);
                Log.e("path--------", profileImage + "");

                uploadLay.setVisibility(View.VISIBLE);
//                MemoryCacheUtils.removeFromCache("" + profileImage, ImageLoader.getInstance().getMemoryCache());
//                DiskCacheUtils.removeFromCache("" + profileImage, ImageLoader.getInstance().getDiskCache());
                Picasso.with(getActivity())
                        .load(profileImage)
                        .skipMemoryCache()
                        .into(uploadImg);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }

    }


    boolean clickedMediaPhoto=false;
    @OnClick({R.id.uploadPhoto_TV, R.id.uploadVideo_TV, R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.uploadPhoto_TV:
                clickedMediaPhoto=true;
               checkPermissionsForCamera();
                break;
            case R.id.uploadVideo_TV:
                clickedMediaPhoto=false;
                checkPermissionsForCamera();
                break;
            case R.id.button:

                if (selectedFilePath.equals("")) {
                    if (statusET.getText().toString().trim().equals("")) {

                    } else {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                        map.put("wall_post_status", statusET.getText().toString().trim());
                        AddPost = baseActivity.apiInterface.AddWallPost1(map);
                        baseActivity.apiHitAndHandle.makeApiCall(AddPost, this, true, baseActivity);
                        CommonDialogs.hideSoftKeyboard(requireActivity());

                    }

                } else {
                    HashMap<String, RequestBody> jsonbody = new HashMap<String, RequestBody>();
                    HashMap<String, String> jsonbodyy = new HashMap<String, String>();

                    jsonbody.put("wall_post_status", getRequestBodyParam(statusET.getText().toString().trim()));
                    jsonbody.put("access_token", getRequestBodyParam(GeneralValues.get_Access_Key(getActivity())));

                    // File file = new File(selectedFilePath);

                    if(isClicked.equals("Image")){
                        jsonbody.put("wall_post_media_type", getRequestBodyParam("P"));
                        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), profileImage);
                        jsonbody.put("wall_post_media\"; filename=\"" + profileImage.getName() + "\" ", body);
                    }
                    if(isClicked.equals("Video")){
                        jsonbody.put("wall_post_media_type", getRequestBodyParam("V"));

                        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), videoFile);
                        jsonbody.put("wall_post_media\"; filename=\"" + videoFile.getName() + "\" ", body);
                    }
                    AddPost1 = baseActivity.apiInterface.AddWallPost(jsonbody);
                    baseActivity.apiHitAndHandle.makeApiCall(AddPost1, this, true, baseActivity);
                    CommonDialogs.hideSoftKeyboard(requireActivity());
                }

                break;
        }
    }


    public static RequestBody getRequestBodyParam(String value) {
        return RequestBody.create(MediaType.parse("text/form-data"), value);
    }




    void sendBackVideoPath(Uri inputUri){
        if (inputUri.toString().contains("video") || inputUri.toString().contains("Movies") || inputUri.toString().contains("movies")||inputUri.toString().contains(".mp4")) {
            profileImage = null;
            //  selectedFilePath = PathUtils.getPath(baseActivity, inputUri);
            //  videoFile = new File(selectedFilePath);
            // Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(selectedFilePath, MediaStore.Images.Thumbnails.);
            //statusPicIV.setImageBitmap(getVideoFrame(selectedFilePath));
//            selectedFilePath = CommonDialogs.getPathFromURI(getActivity(), inputUri);
            isClicked = "Video";
            if (inputUri.toString().contains(".png") || inputUri.toString().contains(".jpg") || inputUri.toString().contains(".jpeg")) {
                final MaterialDialog mMaterialDialog = new MaterialDialog(getActivity());
                mMaterialDialog.setTitle("Error");
                mMaterialDialog.setMessage("You can only select video.");
                mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });

                mMaterialDialog.show();
            } else {
               /* Log.e(">.video Path", ">>>>" + selectedFilePath);
                if (selectedFilePath != null) {
                    try {
                        videoFile = new File(selectedFilePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    long time = 0;
                    try {
                        MediaPlayer mp = MediaPlayer.create(getActivity(), Uri.parse(selectedFilePath));
                        int duration1 = mp.getDuration();

                        time = duration1;
                        CommonDialogs.getDurationBreakdown(time);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //    Bitmap bmp = customCreatThumbnail(selectedFilePath);
                    Bitmap resizedbitmap = ThumbnailUtils.createVideoThumbnail(selectedFilePath, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                    File thumbfile = new File(CommonDialogs.saveimagetosdcard(getActivity(), resizedbitmap));
                    uploadLay.setVisibility(View.VISIBLE);
                    MemoryCacheUtils.removeFromCache("" + thumbfile, ImageLoader.getInstance().getMemoryCache());
                    DiskCacheUtils.removeFromCache("" + thumbfile, ImageLoader.getInstance().getDiskCache());
                    Picasso.with(getActivity())
                            .load(thumbfile)
                            .skipMemoryCache()
                            .into(uploadImg);
                    //statusPicIV.setImageBitmap(bmp);
                    Log.e("VIDEOURI", "" + inputUri);
                }*/

                try
                {
                    AssetFileDescriptor videoAsset = getActivity().getContentResolver().openAssetFileDescriptor(inputUri, "r");
                    FileInputStream fis = videoAsset.createInputStream();
                    File root=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Believe");
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    File file;
                    videoFile =new File(root,"video_"+System.currentTimeMillis()+".mp4" );
                    selectedFilePath = ""+videoFile;
                    Environment.getExternalStorageDirectory();
                    FileOutputStream fos = new FileOutputStream(videoFile);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = fis.read(buf)) > 0) {
                        fos.write(buf, 0, len);
                    }
                    fis.close();
                    fos.close();
                    Bitmap bitmap2 = ThumbnailUtils.createVideoThumbnail(videoFile.getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                    uploadLay.setVisibility(View.VISIBLE);
                    uploadImg.setImageBitmap(bitmap2);
                  //  setVideoThumbnail(bitmap2);
                   // Bitmap resizedbitmap = ThumbnailUtils.createVideoThumbnail(selectedFilePath, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
//                    File thumbfile = new File(CommonDialogs.saveimagetosdcard(getActivity(), bitmap2));
//                    uploadLay.setVisibility(View.VISIBLE);
//                    MemoryCacheUtils.removeFromCache("" + thumbfile, ImageLoader.getInstance().getMemoryCache());
//                    DiskCacheUtils.removeFromCache("" + thumbfile, ImageLoader.getInstance().getDiskCache());
//                    Picasso.with(getActivity())
//                            .load(thumbfile)
//                            .skipMemoryCache()
//                            .into(uploadImg);
                    //statusPicIV.setImageBitmap(bmp);
                    Log.e("VIDEOURI", "" + inputUri);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            // videoIConIV.setVisibility(View.VISIBLE);
        }else{
            final MaterialDialog mMaterialDialog = new MaterialDialog(getActivity());
            mMaterialDialog.setTitle("Error");
            mMaterialDialog.setMessage("You can only select video.");
            mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                }
            });

            mMaterialDialog.show();
        }
    }





    void sendBackImagePath(Uri inputUri) {


            isClicked = "Image";
            UCrop.of(inputUri, Uri.fromFile(new File(getActivity().getExternalCacheDir(), "Believe.jpeg")))
                    .withAspectRatio(1, 1)
                    .start(getActivity(), this);

            videoFile = null;
           /* profileImage = new File(selectedFilePath);
            selectedFilePath = PathUtils.getPath(baseActivity, inputUri);*/

            // Picasso.with(baseActivity).load(profileImage).into(statusPicIV);
            // videoIConIV.setVisibility(View.GONE);


    }


    @Override
    public void onSuccess(Call call, Object object) {

        Log.e("Response body", ">>>>>>>>>" + object.toString());

/*
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        WallPostModel result = gson.fromJson(object.toString(), WallPostModel.class);*/
        JSONObject jsonObject = null;


        try {
            if(call == Detail) {
                jsonObject = new JSONObject(object.toString());
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                JSONObject jsonObject2 = jsonObject1.getJSONObject("userInfo");
                user_Id = jsonObject2.getString("user_id");
                user_Firstname = jsonObject2.getString("first_name");
                user_Lastname = jsonObject2.getString("last_name");
                user_Image = jsonObject2.getString("profile_image");

//                MemoryCacheUtils.removeFromCache(user_Image, ImageLoader.getInstance().getMemoryCache());
//                DiskCacheUtils.removeFromCache(user_Image, ImageLoader.getInstance().getDiskCache());
                //CommonDialogs.getDisplayImage(requireContext(), user_Image, profileImageIV);
                CommonConst.Companion.loadGlide(BelieveApplication.mInstance,user_Image,R.drawable.user_pic).into(profileImageIV);


                blogList.clear();
                JSONArray jsonArray = jsonObject1.getJSONArray("discussion");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    JSONObject jObj1 = jObj.getJSONObject("user_id");

                    other_name = (jObj1.getString("first_name") + " " + jObj1.getString("last_name"));
                    other_image = jObj1.getString("profile_image");
                    other_id = jObj1.getString("_id");

                    post_id = jObj.getString("_id");
                    post_title = jObj.getString("group_post_status");
                    post_image = jObj.getString("group_post_media");
                    post_thumb = jObj.getString("thumbnail");
                    post_type = jObj.getString("group_post_media_type");
                    post_commentcount = jObj.getString("totalComments");
                    post_likecount = jObj.getString("totalLikes");
                    post_time = jObj.getString("time_ago");
                    like_status = jObj.getString("likeStatus");


                    JSONObject jObj2 = jObj.getJSONObject("lastComment");
                    JSONObject jObj3 = jObj2.getJSONObject("user_id");
                    other_userid = jObj3.getString("_id");
                    other_username = jObj3.getString("first_name") + " " + jObj3.getString("last_name");
                    other_user_image = jObj3.getString("profile_image");
                    other_usercomment = jObj2.getString("comment_msg");
                    other_commentid = jObj2.getString("_id");
                    comment_img = jObj2.getString("group_post_comment_img");
                    media_status = jObj2.getString("media_status");
                    PostsModel model = new PostsModel(other_id, other_name, "", other_image, post_time, post_id, post_title, post_image, post_commentcount, post_likecount,
                            other_username, other_userid, other_user_image, other_usercomment, post_type, post_thumb, like_status, comment_img, media_status);

                    blogList.add(model);
                }

                if(blogList.size()==0){
                    txtNodata.setVisibility(View.VISIBLE);
                    postList_RV.setVisibility(View.GONE);
                }else{
                    txtNodata.setVisibility(View.GONE);
                    postList_RV.setVisibility(View.VISIBLE);
                }



                wallAdapter = new WallAdapter(baseActivity, blogList, this, this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
                postList_RV.setLayoutManager(mLayoutManager);
                postList_RV.setItemAnimator(new DefaultItemAnimator());
                postList_RV.setAdapter(wallAdapter);
                postList_RV.setNestedScrollingEnabled(false);
                wallAdapter.notifyDataSetChanged();
            }
            else if (call == LikePost) {
                 jsonObject = new JSONObject(object.toString());
                CommonDialogs.customToast(getActivity(), jsonObject.getString("message"));
                CallApi(true);
            }

            else if (call == AddPost) {
                profileImage = null;
                selectedFilePath = "";
                uploadLay.setVisibility(View.GONE);
                jsonObject = new JSONObject(object.toString());
//                CommonDialogs.customToast(getActivity(), jsonObject.getString("message"));
                CommonDialogs.customToast(getActivity(), "Post added successfully");
                statusET.setText("");
                CallApi(true);
            } else if (call == AddPost1) {
                profileImage = null;
                selectedFilePath = "";
                uploadLay.setVisibility(View.GONE);
                jsonObject = new JSONObject(object.toString());
                CommonDialogs.customToast(getActivity(), "Post added successfully");
                statusET.setText("");
                CallApi(true);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }


    @Override
    public void OnPermissionAccepted() {

        if (clickedMediaPhoto){
            selectMedia();
        }
        else {
            selectMedia2();
        }
    }
}
