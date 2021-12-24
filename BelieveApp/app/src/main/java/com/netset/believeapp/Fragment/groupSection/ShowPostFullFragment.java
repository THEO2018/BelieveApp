package com.netset.believeapp.Fragment.groupSection;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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
import android.widget.Toast;

import com.facebook.common.Common;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.ShowFullPostAdapter;
import com.netset.believeapp.BelieveApplication;
import com.netset.believeapp.CommonConst;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.ImageDialogFragment;
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
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.REQUEST_CODE_CHOOSE;

/**
 * Created by netset on 22/1/18.
 */

public class ShowPostFullFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.profile_image_IV)
    CircleImageView profileImageIV;
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
    Handler mHandler;

    String PostId, type;
    ShowFullPostAdapter showFullPostAdapter;
    File videoFile, profileImage;
    List<Uri> mSelected;
    public String selectedFilePathOptional = "";

    Call<JsonObject> GetPostDetail, SendComment, LikePost;
    List<PostsModel> blogList = new ArrayList<>();
    String user_Id, user_Image, user_Firstname, user_Lastname, post_id, post_thumb, post_type, post_title, post_time, post_image, post_commentcount,
            post_likecount, other_userid, other_username, other_user_image, other_usercomment, other_commentid, like_status, comment_time, comment_img, media_status;

    private Boolean isApiCalled = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.show_post_full_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((ShowFullPostActivity) getActivity()).setToolbarTitle("Comments", true, false);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HomeActivity.isPostDetailVisible = true;
        mHandler = new Handler();
        Bundle b = getArguments();
        if (b != null) {
            if (b.getString("from").equals("group")) {
                if (b.getString("status").equals("true")) {
                    linearLayout6.setVisibility(View.VISIBLE);
                } else {
                    linearLayout6.setVisibility(View.GONE);
                }
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(mMessageReceiver);
        HomeActivity.isPostDetailVisible = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        HomeActivity.isPostDetailVisible = false;
    }

    public void CallApi(Boolean loader) {
        Bundle b = getArguments();
        if (b != null) {
            this.PostId = b.getString("postid");
            type = b.getString("from");
            HashMap<String, String> map = new HashMap<String, String>();
            if (type.equals("wall")) {
                map.put("type", "wall");
            } else {
                map.put("type", "group");
            }
            map.put("post_id", PostId);
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            GetPostDetail = baseActivity.apiInterface.GetGroupPost_Detail(map);
            baseActivity.apiHitAndHandle.makeApiCall(GetPostDetail, loader, this);
        } else {
            Intent in = new Intent();
            if (in != null) {
                if (in.getStringExtra("from").equals("group")) {
                    this.PostId = in.getStringExtra("postid");
                    type = in.getStringExtra("from");
                    HashMap<String, String> map = new HashMap<String, String>();
                    if (type.equals("wall")) {
                        map.put("type", "wall");
                    } else {
                        map.put("type", "group");
                    }
                    map.put("post_id", PostId);
                    map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                    GetPostDetail = baseActivity.apiInterface.GetGroupPost_Detail(map);
                    baseActivity.apiHitAndHandle.makeApiCall(GetPostDetail, loader, this);

                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));
        requireActivity().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        CallApi(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            //   socketObj.destroySocket()
            requireActivity().getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            CommonDialogs.hideSoftKeyboard(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.camera_IV, R.id.actionSend_TV, R.id.lay_like})
    public void onClick(View view) {
        HashMap<String, String> map = new HashMap<String, String>();
        switch (view.getId()) {
            case R.id.camera_IV:
//                selectMedia();
                pick();
                break;
            case R.id.actionSend_TV:

                Bundle b = getArguments();
                if (b != null) {
                    this.PostId = b.getString("postid");
                    type = b.getString("from");
                }
                if (type.equals("wall")) {
                    map.put("type", "wall");
                } else {
                    map.put("type", "group");
                }
                map.put("group_post_id", this.PostId);
                map.put("comment_msg", addCmntET.getText().toString().trim());
                map.put("access_token", GeneralValues.get_Access_Key(getActivity()));

                SendComment = baseActivity.apiInterface.AddGroupPostComment(map);
                baseActivity.apiHitAndHandle.makeApiCall(SendComment, this);
                CommonDialogs.hideSoftKeyboard(requireActivity());
                break;

            case R.id.lay_like:

                Bundle b1 = getArguments();
                if (b1 != null) {
                    this.PostId = b1.getString("postid");
                    type = b1.getString("from");
                }
                if (type.equals("wall")) {
                    map.put("type", "wall");
                } else {
                    map.put("type", "group");
                }

                map.put("group_post_id", this.PostId);
                map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                if (like_status.equals("true")) {
                    map.put("like_status", "U");
                } else {
                    map.put("like_status", "L");
                }
                LikePost = baseActivity.apiInterface.AddGroupPostLike(map);
                baseActivity.apiHitAndHandle.makeApiCall(LikePost, this, false);
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

    void pick() {
        ImagePicker.Companion.with((ShowFullPostActivity) getActivity())
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start((integer, intent) -> {
                    if (integer == Activity.RESULT_OK) {
                        profileImage = ImagePicker.Companion.getFile(intent);
                        Bundle b1 = getArguments();
                        if (b1 != null) {
                            this.PostId = b1.getString("postid");
                            type = b1.getString("from");
                        }

                        HashMap<String, RequestBody> jsonbody = new HashMap<String, RequestBody>();
                        jsonbody.put("group_post_id", getRequestBodyParam(this.PostId));
                        if (type.equals("wall")) {
                            jsonbody.put("type", getRequestBodyParam("wall"));
                        } else {
                            jsonbody.put("type", getRequestBodyParam("group"));
                        }

                        jsonbody.put("access_token", getRequestBodyParam(GeneralValues.get_Access_Key(getActivity())));
                        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), profileImage);
                        jsonbody.put("group_post_comment_img\"; filename=\"" + profileImage.getName() + "\" ", body);
                        isApiCalled = true;
                        SendComment = baseActivity.apiInterface.AddGroupPostComment2(jsonbody);
                        baseActivity.apiHitAndHandle.makeApiCall(SendComment, this, false);
                   /* mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           CallApi(true);
                        }
                    }, 3000);*/
                    } else if (integer == ImagePicker.RESULT_ERROR) {
                        Toast.makeText(requireContext(), ImagePicker.Companion.getError(intent), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                    }
                    return null;
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
 /*       if (requestCode == REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            try {

                sendBackImagePath(mSelected.get(0));

            }
            catch (Exception e){
                e.printStackTrace();
                Log.d("camera>>",""+e);
            }
        }*/

    }

    void sendBackImagePath(Uri inputUri) {
        selectedFilePathOptional = PathUtils.getPath(baseActivity, inputUri);
        if (!inputUri.toString().contains("video") || !inputUri.toString().contains("Movies") || !inputUri.toString().contains("movies")) {
            videoFile = null;
            profileImage = new File(selectedFilePathOptional);
            //   Picasso.with(baseActivity).load(profileImage).into(selectImageIM);
            Bundle b1 = getArguments();
            if (b1 != null) {
                this.PostId = b1.getString("postid");
                type = b1.getString("from");
            }


            HashMap<String, RequestBody> jsonbody = new HashMap<String, RequestBody>();
            jsonbody.put("group_post_id", getRequestBodyParam(this.PostId));
            if (type.equals("wall")) {
                jsonbody.put("type", getRequestBodyParam("wall"));
            } else {
                jsonbody.put("type", getRequestBodyParam("group"));
            }

            jsonbody.put("access_token", getRequestBodyParam(GeneralValues.get_Access_Key(getActivity())));
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), profileImage);
            jsonbody.put("group_post_comment_img\"; filename=\"" + profileImage.getName() + "\" ", body);
            SendComment = baseActivity.apiInterface.AddGroupPostComment2(jsonbody);
            baseActivity.apiHitAndHandle.makeApiCall(SendComment, this, true);

        } else {
            showToast("Only Images are Acceptable");

        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("startLoading").equalsIgnoreCase("0")) {
                Log.e("startLoading","true");
               // baseActivity.apiHitAndHandle.showDialog();
            } else {
                CallApi(false);
            }
        }
    };

    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(object.toString());

            if (call == GetPostDetail) {
                blogList.clear();
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                JSONObject jsonObject2 = jsonObject1.getJSONObject("user_id");
                user_Id = jsonObject2.getString("_id");
                user_Firstname = jsonObject2.getString("first_name");
                user_Lastname = jsonObject2.getString("last_name");
                user_Image = jsonObject2.getString("profile_image");
//                MemoryCacheUtils.removeFromCache(user_Image, ImageLoader.getInstance().getMemoryCache());
//                DiskCacheUtils.removeFromCache(user_Image, ImageLoader.getInstance().getDiskCache());
//                CommonDialogs.getDisplayImage(getActivity(), user_Image, profileImageIV,"#d3d3d3");
                CommonConst.Companion.loadGlideCircular(BelieveApplication.mInstance, user_Image, R.drawable.user_pic).into(profileImageIV);

                userNameTV.setText(user_Firstname + " " + user_Lastname);


                post_id = jsonObject1.getString("_id");

                post_title = StringEscapeUtils.unescapeJava(jsonObject1.getString("group_post_status"));
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
//                    MemoryCacheUtils.removeFromCache(post_image, ImageLoader.getInstance().getMemoryCache());
//                    DiskCacheUtils.removeFromCache(post_image, ImageLoader.getInstance().getDiskCache());
                    CommonDialogs.getSquareImage(getActivity(), post_image, postMediaIV);
//                    CommonConst.Companion.loadGlide(getContext(),post_image,R.drawable.empty).into(postMediaIV);

                } else if (post_type.equals("V")) {
                    mediaLay.setVisibility(View.VISIBLE);
                    imgThumb.setVisibility(View.VISIBLE);
                    CommonDialogs.getSquareImage(getActivity(), post_thumb, postMediaIV);
                } else {
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

                if (like_status.equals("false")) {
                    likeIV.setBackgroundResource(R.drawable.ic_unlike);
                } else {
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
                    other_usercomment = Obj.getString("comment_msg");
                    other_usercomment = Obj.getString("comment_msg");
                    if (type.equals("wall")) {
                        comment_img = Obj.getString("wall_post_comment_img");
                    } else {
                        comment_img = Obj.getString("group_post_comment_img");
                    }

                    media_status = Obj.getString("media_status");
                    other_commentid = Obj.getString("_id");
                    comment_time = Obj.getString("time_ago");

                    PostsModel model = new PostsModel(comment_time, other_username, other_userid, other_user_image, other_usercomment, other_commentid, comment_img, media_status);

                    blogList.add(model);
                }

                ShowFullPostAdapter showFullPostAdapter = new ShowFullPostAdapter(getActivity(), blogList);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                cmntList_RV.setLayoutManager(mLayoutManager);
                cmntList_RV.setAdapter(showFullPostAdapter);
                cmntList_RV.setNestedScrollingEnabled(false);

                mediaLay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (post_type.equals("P")) {
//                            String extension = MimeTypeMap.getFileExtensionFromUrl(post_image);
//                            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//                            Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
//                            mediaIntent.setDataAndType(Uri.parse(post_image), mimeType);
//                            startActivity(mediaIntent);
                            FragmentActivity activity = (FragmentActivity) (getActivity());
                            FragmentManager fm = activity.getSupportFragmentManager();
                            ImageDialogFragment alertDialog = new ImageDialogFragment(post_image);
                            alertDialog.show(fm, "P");
                        } else if (post_type.equals("V")) {
//                            Intent intent = new Intent();
//                            intent.setAction(Intent.ACTION_VIEW);
//                            intent.setDataAndType(Uri.parse(post_image), "image/*");
//                            startActivity(intent);
                            FragmentActivity activity = (FragmentActivity) (getActivity());
                            FragmentManager fm = activity.getSupportFragmentManager();
                            ImageDialogFragment alertDialog = new ImageDialogFragment(post_image);
                            alertDialog.show(fm, "V");
                        }
                    }
                });

            }

            if (call == SendComment || isApiCalled) {
                baseActivity.apiHitAndHandle.dismissDialog();
                isApiCalled = false;
                addCmntET.setText("");
                Intent intent = new Intent("custom-event-name");
                intent.putExtra("startLoading", "1");
                LocalBroadcastManager.getInstance(requireActivity()).sendBroadcast(intent);
            }

            if (call == LikePost) {
                jsonObject = new JSONObject(object.toString());
                CommonDialogs.customToast(getActivity(), jsonObject.getString("message"));
                CallApi(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
