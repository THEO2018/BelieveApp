package com.netset.believeapp.Fragment.groupSection;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.common.Common;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.ShowFullPostAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Model.PostsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.activity.ShowFullPostActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.utils.PathUtils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.REQUEST_CODE_CHOOSE;

/**
 * Created by netset on 22/1/18.
 */

public class ShowPostFullFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.profile_image_IV)
    ImageView profileImageIV;
    @BindView(R.id.userName_TV)
    AppCompatTextView userNameTV;
    @BindView(R.id.duration_TV)
    AppCompatTextView durationTV;
    @BindView(R.id.profielInfo_Container)
    ConstraintLayout profielInfoContainer;
    @BindView(R.id.postText_TV)
    AppCompatTextView postTextTV;
    @BindView(R.id.postMedia_IV)
    AppCompatImageView postMediaIV;
    @BindView(R.id.postInfo_Container)
    ConstraintLayout postInfoContainer;
    @BindView(R.id.comntCount_TV)
    AppCompatTextView comntCountTV;
    @BindView(R.id.comnt_label_TV)
    AppCompatTextView comntLabelTV;
    @BindView(R.id.likeCount_TV)
    AppCompatTextView likeCountTV;
    @BindView(R.id.likes_label_TV)
    AppCompatTextView likesLabelTV;
    @BindView(R.id.like_IV)
    AppCompatImageView likeIV;
    @BindView(R.id.like_TV)
    AppCompatTextView likeTV;
    @BindView(R.id.comnt_IV)
    AppCompatImageView comntIV;
    @BindView(R.id.comnt_action_TV)
    AppCompatTextView comntActionTV;
    @BindView(R.id.comentView)
    LinearLayout comentView;
    @BindView(R.id.postAction_Container)
    ConstraintLayout postActionContainer;
    @BindView(R.id.divView)
    View divView;
    @BindView(R.id.cmntList_RV)
    RecyclerView cmntList_RV;
    @BindView(R.id.camera_IV)
    ImageView cameraIV;
    @BindView(R.id.addCmnt_ET)
    EditText addCmntET;
    @BindView(R.id.actionSend_TV)
    ImageView actionSendTV;
    @BindView(R.id.linearLayout6)
    LinearLayout linearLayout6;
    @BindView(R.id.img_thumb)
    ImageView imgThumb;
    @BindView(R.id.lay_like)
            LinearLayout layLike;
    @BindView(R.id.media_lay)
    RelativeLayout mediaLay;
    Unbinder unbinder;
    Handler mHandler=new Handler();

    String PostId,type;
    ShowFullPostAdapter showFullPostAdapter;
    File videoFile, profileImage;
    List<Uri> mSelected;
    public String selectedFilePathOptional = "";

    Call<JsonObject> GetPostDetail,SendComment,LikePost;
    List<PostsModel> blogList = new ArrayList<>();
    String user_Id,user_Image,user_Firstname,user_Lastname,post_id,post_thumb,post_type,post_title,post_time,post_image,post_commentcount,
            post_likecount,other_userid,other_username,other_user_image,other_usercomment,other_commentid,like_status,comment_time,comment_img,media_status;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.show_post_full_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((ShowFullPostActivity) getActivity()).setToolbarTitle("Comments", true, false);
        return rootView;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CallApi();
        HomeActivity.isPostDetailVisible = true;
        Bundle b = getArguments();
        if(b!=null){
        if(b.getString("from").equals("group")){
            if(b.getString("status").equals("true")){
                linearLayout6.setVisibility(View.VISIBLE);
            }else{
                linearLayout6.setVisibility(View.GONE);
            }
        }}

    }

    @Override
    public void onPause() {
        super.onPause();
        HomeActivity.isPostDetailVisible = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        HomeActivity.isPostDetailVisible = false;
    }


    public void CallApi(){
        Bundle b = getArguments();
        if(b != null) {
            this.PostId = b.getString("postid");
            type = b.getString("from");
            HashMap<String, String> map = new HashMap<String, String>();
            if(type.equals("wall")){
                map.put("type","wall");
            }else{
                map.put("type","group");
            }
            map.put("post_id",PostId);
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            GetPostDetail = baseActivity.apiInterface.GetGroupPost_Detail(map);
            baseActivity.apiHitAndHandle.makeApiCall(GetPostDetail,this);
        }else {
            Intent in = new Intent();
            if(in !=null) {
                if (in.getStringExtra("from").equals("group")) {
                        this.PostId = in.getStringExtra("postid");
                        type = in.getStringExtra("from");
                        HashMap<String, String> map = new HashMap<String, String>();
                        if(type.equals("wall")){
                            map.put("type","wall");
                        }else{
                            map.put("type","group");
                        }
                        map.put("post_id",PostId);
                        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                        GetPostDetail = baseActivity.apiInterface.GetGroupPost_Detail(map);
                    baseActivity.apiHitAndHandle.makeApiCall(GetPostDetail,this,true);

                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            //   socketObj.destroySocket()
            requireActivity().getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            CommonDialogs.hideSoftKeyboard(getActivity());
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.camera_IV, R.id.actionSend_TV,R.id.lay_like})
    public void onClick(View view) {
        HashMap<String, String> map = new HashMap<String, String>();
        switch (view.getId()) {
            case R.id.camera_IV:
                selectMedia();
                break;
            case R.id.actionSend_TV:

                Bundle b = getArguments();
                if(b != null) {
                    this.PostId = b.getString("postid");
                    type = b.getString("from");
                }
                if(type.equals("wall")){
                    map.put("type","wall");
                }else{
                    map.put("type","group");
                }
                map.put("group_post_id", this.PostId);
                map.put("comment_msg",addCmntET.getText().toString().trim());
                map.put("access_token",GeneralValues.get_Access_Key(getActivity()));

                SendComment = baseActivity.apiInterface.AddGroupPostComment(map);
                baseActivity.apiHitAndHandle.makeApiCall(SendComment, this);
                CommonDialogs.hideSoftKeyboard(requireActivity());
                break;

            case R.id.lay_like:

                Bundle b1 = getArguments();
                if(b1 != null) {
                    this.PostId = b1.getString("postid");
                    type = b1.getString("from");
                }
                if(type.equals("wall")){
                    map.put("type","wall");
                }else{
                    map.put("type","group");
                }

                map.put("group_post_id",this.PostId);
                map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                if(like_status.equals("true")){
                    map.put("like_status", "U");
                }else{
                    map.put("like_status", "L");
                }
                LikePost = baseActivity.apiInterface.AddGroupPostLike(map);
                baseActivity.apiHitAndHandle.makeApiCall(LikePost,this,false);
                break;
        }
    }

    public static RequestBody getRequestBodyParam(String value) {
        return RequestBody.create(MediaType.parse("text/form-data"), value);
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
            try {

                sendBackImagePath(mSelected.get(0));

            }
            catch (Exception e){
                e.printStackTrace();
                Log.d("camera>>",""+e);
            }
        }
    }

    void sendBackImagePath(Uri inputUri) {
        selectedFilePathOptional = PathUtils.getPath(baseActivity, inputUri);
        if (!inputUri.toString().contains("video") || !inputUri.toString().contains("Movies") || !inputUri.toString().contains("movies")) {
            videoFile = null;
            profileImage = new File(selectedFilePathOptional);
         //   Picasso.with(baseActivity).load(profileImage).into(selectImageIM);
            Bundle b1 = getArguments();
            if(b1 != null) {
                this.PostId = b1.getString("postid");
                type = b1.getString("from");
            }


            HashMap<String, RequestBody> jsonbody = new HashMap<String, RequestBody>();
            jsonbody.put("group_post_id", getRequestBodyParam(this.PostId));
            if(type.equals("wall")){
                jsonbody.put("type", getRequestBodyParam("wall"));
            }else{
                jsonbody.put("type", getRequestBodyParam("group"));
            }

            jsonbody.put("access_token", getRequestBodyParam(GeneralValues.get_Access_Key(getActivity())));
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), profileImage);
            jsonbody.put("group_post_comment_img\"; filename=\"" + profileImage.getName() + "\" ", body);
            SendComment = baseActivity.apiInterface.AddGroupPostComment2(jsonbody);
            baseActivity.apiHitAndHandle.makeApiCall(SendComment, this,false);
           /* mHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        CallApi();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });*/

        } else {
            showToast("Only Images are Acceptable");

        }
    }

    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(object.toString());

            if(call == GetPostDetail) {
                blogList.clear();
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                JSONObject jsonObject2 = jsonObject1.getJSONObject("user_id");
                user_Id = jsonObject2.getString("_id");
                user_Firstname = jsonObject2.getString("first_name");
                user_Lastname = jsonObject2.getString("last_name");
                user_Image = jsonObject2.getString("profile_image");
                MemoryCacheUtils.removeFromCache(user_Image, ImageLoader.getInstance().getMemoryCache());
                DiskCacheUtils.removeFromCache(user_Image, ImageLoader.getInstance().getDiskCache());
                CommonDialogs.getDisplayImage(getActivity(), user_Image, profileImageIV,"#d3d3d3");
                userNameTV.setText(user_Firstname + " " + user_Lastname);


                post_id = jsonObject1.getString("_id");

                post_title = StringEscapeUtils.unescapeJava( jsonObject1.getString("group_post_status"));
              //  post_title = jsonObject1.getString("group_post_status");
                post_image = jsonObject1.getString("group_post_media");
                post_thumb = jsonObject1.getString("thumbnail");
                post_type = jsonObject1.getString("group_post_media_type");
                post_commentcount = jsonObject1.getString("totalComments");
                post_likecount = jsonObject1.getString("totalLikes");
                post_time = jsonObject1.getString("time_ago");
                like_status = jsonObject1.getString("likeStatus");

                durationTV.setText(post_time);
                postTextTV.setText(post_title);
                if (post_type.equals("P")) {
                    mediaLay.setVisibility(View.VISIBLE);
                    imgThumb.setVisibility(View.GONE);
                    MemoryCacheUtils.removeFromCache(post_image, ImageLoader.getInstance().getMemoryCache());
                    DiskCacheUtils.removeFromCache(post_image, ImageLoader.getInstance().getDiskCache());
                    CommonDialogs.getSquareImage(getActivity(), post_image, postMediaIV);
                }
                else if(post_type.equals("V")) {
                    mediaLay.setVisibility(View.VISIBLE);
                    imgThumb.setVisibility(View.VISIBLE);
                    CommonDialogs.getSquareImage(getActivity(), post_thumb, postMediaIV);
                }else{
                    mediaLay.setVisibility(View.GONE);
                }
                if (post_commentcount.equals("1") || post_commentcount.equals("0")) {
                    comntLabelTV.setText("Comment");
                } else {
                    comntLabelTV.setText("Comments");
                }
                if (post_likecount.equals("1") || post_likecount.equals("0")) {
                    likesLabelTV.setText("Like");
                } else {
                    likesLabelTV.setText("Likes");
                }

                if(like_status.equals("false")){
                    likeIV.setBackgroundResource(R.drawable.ic_unlike);
                }
                else{
                    likeIV.setBackgroundResource(R.drawable.ic_like);
                }

                comntCountTV.setText(post_commentcount);
                likeCountTV.setText(post_likecount);


                JSONArray jsonArray = jsonObject1.getJSONArray("allComments");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject Obj = jsonArray.getJSONObject(i);
                    JSONObject Obj1 = Obj.getJSONObject("user_id");
                    other_userid = Obj1.getString("_id");
                    other_username = Obj1.getString("first_name") + " " + Obj1.getString("last_name");
                    other_user_image = Obj1.getString("profile_image");
                    other_usercomment =  Obj.getString("comment_msg");
                    comment_img = Obj.getString("group_post_comment_img");
                    media_status = Obj.getString("media_status");
                    other_commentid = Obj.getString("_id");
                    comment_time = Obj.getString("time_ago");

                    PostsModel model = new PostsModel(comment_time, other_username, other_userid, other_user_image, other_usercomment, other_commentid,comment_img,media_status);

                    blogList.add(model);
                }

                showFullPostAdapter = new ShowFullPostAdapter(getActivity(), blogList);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                cmntList_RV.setLayoutManager(mLayoutManager);
                cmntList_RV.setAdapter(showFullPostAdapter);
                cmntList_RV.setNestedScrollingEnabled(false);
                showFullPostAdapter.notifyDataSetChanged();



                mediaLay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(post_type.equals("P")){
                            String extension = MimeTypeMap.getFileExtensionFromUrl(post_image);
                            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                            Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                            mediaIntent.setDataAndType(Uri.parse(post_image), mimeType);
                            startActivity(mediaIntent);
                        }
                        else if(post_type.equals("V")){
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.parse(post_image), "image/*");
                            startActivity(intent);
                        }
                    }
                });


            }
            if(call == SendComment){
                addCmntET.setText("");
                CallApi();
            }

            if(call == LikePost){
                jsonObject = new JSONObject(object.toString());
                CommonDialogs.customToast(getActivity(),jsonObject.getString("message"));
                CallApi();
            }
        } catch (JSONException e) {
            e.printStackTrace();


        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
