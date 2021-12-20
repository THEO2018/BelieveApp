package com.netset.believeapp.Fragment.groupSection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.WallAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Model.PostsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.ShowFullPostActivity;
import com.netset.believeapp.callbacks.CheckPermissionInterface;
import com.netset.believeapp.callbacks.CommentClickCallback;
import com.netset.believeapp.listeners.LikeClickCallback;
import com.netset.believeapp.retrofitManager.ApiResponse;
import com.netset.believeapp.videocompression.MediaController;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.drakeet.materialdialog.MaterialDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.REQUEST_CODE_CHOOSE;

/**
 * Created by netset on 22/1/18.
 */

public class GroupDiscussionFragment extends BaseFragment implements CommentClickCallback, ApiResponse, LikeClickCallback, CheckPermissionInterface {


    @BindView(R.id.profile_image_IV)
    ImageView profileImageIV;
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
    @BindView(R.id.status_ET)
    EditText statusET;
    @BindView(R.id.upload_img_lay)
    RelativeLayout uploadLay;
    @BindView(R.id.upload_img_post)
    AppCompatImageView uploadImg;

    Unbinder unbinder;

    ProgressDialog progressBar;
    File Videos;
    String qq;
    String isClicked;
    WallAdapter wallAdapter;
    List<PostsModel> blogList = new ArrayList<>();

    String groupId;
    View rootView;
    File videoFile = null, profileImage = null;
    List<Uri> mSelected;
    public String selectedFilePath = "";

    Call<JsonObject> Detail, LikePost, AddPost, AddPost1;
    String user_Id, user_Image, user_Firstname, user_Lastname, other_name, other_image, other_id, post_id, post_thumb, post_type, post_title, post_time, post_message, post_image, post_commentcount,
            post_likecount, other_userid, other_username, other_user_image, other_usercomment, other_commentid, like_status, comment_img, media_status;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       if(rootView == null){
           rootView = inflater.inflate(R.layout.wall_fragment, null);
           unbinder = ButterKnife.bind(this, rootView);

           CallApi(true);
       }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        CallApi(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkPermission=this;

        Bundle b = getArguments();
        if(b!= null){
        if(b.getString("joinstatus").equals("true"))
        {
          statusContainer.setVisibility(View.VISIBLE);
        }
        else{
            statusContainer.setVisibility(View.GONE);
        }
        }

    }


    public void CallApi(Boolean loader) {
        Bundle b = getArguments();
        if (b != null) {
            this.groupId = b.getString("groupid");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("group_id", groupId);
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            Detail = baseActivity.apiInterface.GroupDetail_Discussion(map);
            baseActivity.apiHitAndHandle.makeApiCall(Detail, loader,this);

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

                Bundle b = getArguments();
                if (b != null) {
                    this.groupId = b.getString("groupid");
                }


                if (selectedFilePath.equals("")) {
                    if (statusET.getText().toString().trim().equals("")) {

                    } else {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                        map.put("group_id", groupId);
                        map.put("group_post_status", statusET.getText().toString().trim());
                        AddPost = baseActivity.apiInterface.AddGroupPost1(map);
                        baseActivity.apiHitAndHandle.makeApiCall(AddPost, this);
                        CommonDialogs.hideSoftKeyboard(requireActivity());

                    }

                } else {
                    HashMap<String, RequestBody> jsonbody = new HashMap<String, RequestBody>();
                    HashMap<String, String> jsonbodyy = new HashMap<String, String>();

                    jsonbody.put("group_id", getRequestBodyParam(groupId));
                    jsonbody.put("group_post_status", getRequestBodyParam(statusET.getText().toString().trim()));
                    jsonbody.put("access_token", getRequestBodyParam(GeneralValues.get_Access_Key(getActivity())));

                   // File file = new File(selectedFilePath);

                    if(isClicked.equals("Image")){
                        jsonbody.put("group_post_media_type", getRequestBodyParam("P"));
                        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), profileImage);
                        jsonbody.put("group_post_media\"; filename=\"" + profileImage.getName() + "\" ", body);
                    }
                     if(isClicked.equals("Video")){
                         jsonbody.put("group_post_media_type", getRequestBodyParam("V"));
                         RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), videoFile);
                         jsonbody.put("group_post_media\"; filename=\"" + videoFile.getName() + "\" ", body);
                     }
                    AddPost1 = baseActivity.apiInterface.AddGroupPost(jsonbody);
                    baseActivity.apiHitAndHandle.makeApiCall(AddPost1, this);
                }
                break;
        }
    }


    public static RequestBody getRequestBodyParam(String value) {
        return RequestBody.create(MediaType.parse("text/form-data"), value);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onCommentClick(int position) {

        Bundle b = getArguments();
   startActivity(new Intent(getActivity(), ShowFullPostActivity.class)
   .putExtra("from","discussion")
   .putExtra("status",b.getString("joinstatus"))
   .putExtra("postid",blogList.get(position).getPost_id()));


    }

    @Override
    public void onLikeClick(int position) {
        blogList.get(position).getPost_id();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type","group");
        map.put("group_post_id", blogList.get(position).getPost_id());
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        if (blogList.get(position).getLike_status().equals("true")) {
            map.put("like_status", "U");
        } else {
            map.put("like_status", "L");
        }
        LikePost = baseActivity.apiInterface.AddGroupPostLike(map);
        baseActivity.apiHitAndHandle.makeApiCall(LikePost, this);
    }


    @Override
    public void onSuccess(Call call, Object object) {

        try {

            if (call == Detail) {
                try {
                JSONObject jsonObject = new JSONObject(object.toString());
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                JSONObject jsonObject2 = jsonObject1.getJSONObject("userInfo");
                user_Id = jsonObject2.getString("user_id");
                user_Firstname = jsonObject2.getString("first_name");
                user_Lastname = jsonObject2.getString("last_name");
                user_Image = jsonObject2.getString("profile_image");


                CommonDialogs.getDisplayImage(getActivity(), user_Image, profileImageIV);


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

                wallAdapter = new WallAdapter(baseActivity, blogList, this, this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
                postList_RV.setLayoutManager(mLayoutManager);
                //postList_RV.addItemDecoration(new SimpleDividerItemDecorationTransparent(baseActivity));
                postList_RV.setItemAnimator(new DefaultItemAnimator());
                postList_RV.setAdapter(wallAdapter);
                postList_RV.setNestedScrollingEnabled(false);
                wallAdapter.notifyDataSetChanged();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (call == LikePost) {
                JSONObject jsonObject = new JSONObject(object.toString());
                CommonDialogs.customToast(getActivity(), jsonObject.getString("message"));
                CallApi(true);
            } else if (call == AddPost) {
                profileImage = null;
                selectedFilePath = "";
                uploadLay.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(object.toString());
                CommonDialogs.customToast(getActivity(), "Post added succesfully.");
                statusET.setText("");
                CallApi(true);
            } else if (call == AddPost1) {
                profileImage = null;
                selectedFilePath = "";
                uploadLay.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(object.toString());
                CommonDialogs.customToast(getActivity(), "Post added successfully.");
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

    private void selectMedia2(){
        Matisse.from(this)
                .choose(MimeType.ofVideo(), false)
                .countable(true).capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.netset.believeapp"))
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen._120dp))
                .showSingleMediaType(true)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new PicassoEngine()).forResult(2);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            sendBackImagePath(mSelected.get(0));
        }

        if(requestCode == 2 && resultCode == getActivity().RESULT_OK){

            mSelected = Matisse.obtainResult(data);
            //   if(data != null && data.getData() != null){
            try {
                sendBackVideoPath(mSelected.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (resultCode == getActivity().RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                selectedFilePath = resultUri.getEncodedPath();
                profileImage = new File(selectedFilePath);
                Log.e("path--------", profileImage + "");

                uploadLay.setVisibility(View.VISIBLE);
                MemoryCacheUtils.removeFromCache("" + profileImage, ImageLoader.getInstance().getMemoryCache());
                DiskCacheUtils.removeFromCache("" + profileImage, ImageLoader.getInstance().getDiskCache());
                Picasso.with(getActivity())
                        .load(profileImage)
                        .skipMemoryCache()
                        .into(uploadImg);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }

    }



    void sendBackVideoPath(Uri inputUri){
        if (inputUri.toString().contains("video") || inputUri.toString().contains("Movies") || inputUri.toString().contains("movies")) {
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

                try
                {
                    AssetFileDescriptor videoAsset = getActivity().getContentResolver().openAssetFileDescriptor(inputUri, "r");
                    FileInputStream fis = videoAsset.createInputStream();
                    File root=new File(Environment.getExternalStorageDirectory(),"Believe");
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    File file;
                    videoFile =new File(root,"video_"+System.currentTimeMillis()+".mp4" );
                    selectedFilePath = ""+videoFile;
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


            }


        private Bitmap customCreatThumbnail (String filePath){
            Bitmap bitmap = null;
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                retriever.setDataSource(filePath);
                bitmap = retriever.getFrameAtTime(-1);
            } catch (IllegalArgumentException ex) {
                // Assume this is a corrupt video file
            } catch (RuntimeException ex) {
                // Assume this is a corrupt video file
            } finally {
                try {
                    retriever.release();
                } catch (RuntimeException ex) {
                }
            }
            if (bitmap == null)
                return null;

            return bitmap;
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


    private class VideoCompressor extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar = new ProgressDialog(getActivity());
            progressBar.setTitle("Please wait...");
            progressBar.show();
            progressBar.setCancelable(false);

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return MediaController.getInstance().convertVideo(selectedFilePath);
        }

        @Override
        protected void onPostExecute(Boolean compressed) {
            super.onPostExecute(compressed);
            if (compressed) {
                progressBar.dismiss();

                qq = MediaController.cachedFile.getPath();
                Videos = new File(qq);

            }

        }
    }




}
