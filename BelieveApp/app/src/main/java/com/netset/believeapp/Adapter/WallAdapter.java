package com.netset.believeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netset.believeapp.CommonConst;
import com.netset.believeapp.Fragment.birthdaySection.SelectedUserProfileFragment;
import com.netset.believeapp.Fragment.settingScreen.MyProfileFragment;
import com.netset.believeapp.Model.PostsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.BaseActivity;
import com.netset.believeapp.callbacks.CommentClickCallback;
import com.netset.believeapp.listeners.LikeClickCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by netset on 9/1/18.
 */

public class WallAdapter extends RecyclerView.Adapter<WallAdapter.MyViewHolder> {
    List<PostsModel> blogList;
    Context mContext;
    CommentClickCallback commentClickCallback;
    LikeClickCallback likeClickCallback;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView blogImg_IV,thumb_image,Like_IV,commentImg;
        CircleImageView myImage,other_UserImage;

        TextView blogTitle_TV, blogCommentcount_TV,userName_TV,duration_TV,comentLabel_TV
                ,likecount_TV,likeLabel_TV,otherUsername_TV,otherUserComment_Tv;
        LinearLayout comentView,likeView,userLay;
        RelativeLayout mediaLay,CommentContainer;

        public MyViewHolder(View view) {
            super(view);
            myImage = view.findViewById(R.id.profile_image_IV);
            userName_TV = view.findViewById(R.id.userName_TV);
            duration_TV = view.findViewById(R.id.duration_TV);
            blogTitle_TV = view.findViewById(R.id.postText_TV);
            blogImg_IV = view.findViewById(R.id.postMedia_IV);
            blogCommentcount_TV = view.findViewById(R.id.comntCount_TV);
            comentLabel_TV = view.findViewById(R.id.comnt_label_TV);
            likecount_TV = view.findViewById(R.id.likeCount_TV);
            likeLabel_TV = view.findViewById(R.id.likes_label_TV);
            other_UserImage = view.findViewById(R.id.comnt_userPic_IV);
            comentView = view.findViewById(R.id.comentView);
            likeView = view.findViewById(R.id.like_view);
            otherUsername_TV = view.findViewById(R.id.comnt_userName_TV);
            otherUserComment_Tv = view.findViewById(R.id.comnt_userComnt_TV);
            thumb_image = view.findViewById(R.id.img_thumb);
            Like_IV = view.findViewById(R.id.like_IV);
            commentImg = view.findViewById(R.id.img_comment);
            mediaLay = view.findViewById(R.id.media_layout);
            CommentContainer = view.findViewById(R.id.comment_Container);
            userLay = view.findViewById(R.id.user_lay);

        }
    }

    public WallAdapter(Context mContext, List<PostsModel> blogList, CommentClickCallback commentClickCallback,LikeClickCallback likeClickCallback) {
        this.mContext = mContext;
        this.blogList = blogList;
        this.commentClickCallback = commentClickCallback;
        this.likeClickCallback = likeClickCallback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wall_post, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final PostsModel model = blogList.get(position);

        holder.comentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentClickCallback.onCommentClick(position);
            }
        });

        holder.likeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeClickCallback.onLikeClick(position);
            }
        });
//        MemoryCacheUtils.removeFromCache(model.getImage(), ImageLoader.getInstance().getMemoryCache());
//        DiskCacheUtils.removeFromCache(model.getImage(), ImageLoader.getInstance().getDiskCache());
//        CommonDialogs.getDisplayImage(mContext,model.getImage(),holder.myImage);
        CommonConst.Companion.loadGlide(mContext,model.getImage(),R.drawable.user_pic).into(holder.myImage);

        holder.userName_TV.setText(model.getName());
        holder.duration_TV.setText(model.getTime());
        holder.blogTitle_TV.setText(model.getPost_message());
        holder.blogCommentcount_TV.setText(model.getComment_count());
        holder.likecount_TV.setText(model.getLike_count());


        if(model.getMedia_status().equals("P")){
            holder.mediaLay.setVisibility(View.VISIBLE);
            holder.thumb_image.setVisibility(View.GONE);
//            CommonDialogs.getSquareImage(mContext,model.getPost_image(),holder.blogImg_IV);
            CommonConst.Companion.loadGlide(mContext,model.getPost_image(),R.drawable.empty).into(holder.blogImg_IV);

        }else if(model.getMedia_status().equals("V")){
            holder.mediaLay.setVisibility(View.VISIBLE);
            holder.thumb_image.setVisibility(View.VISIBLE);
//            CommonDialogs.getSquareImage(mContext,model.getPost_thumb(),holder.blogImg_IV);
            CommonConst.Companion.loadGlide(mContext,model.getPost_thumb(),R.drawable.empty).into(holder.blogImg_IV);

        }else{
            holder.mediaLay.setVisibility(View.GONE);
        }


        holder.mediaLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.getMedia_status().equals("V")){
                    String extension = MimeTypeMap.getFileExtensionFromUrl(model.getPost_image());
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                    mediaIntent.setDataAndType(Uri.parse(model.getPost_image()), mimeType);
                    mContext.startActivity(mediaIntent);
                }
                else if(model.getMedia_status().equals("P")){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(model.getPost_image()), "image/*");
                    mContext.startActivity(intent);
                }
            }
        });





        if(model.getComment_count().equals("1")||model.getComment_count().equals("0")){
            holder.comentLabel_TV.setText("Comment");
        }else{
            holder.comentLabel_TV.setText("Comments");
        }
        if(model.getLike_count().equals("1")||model.getLike_count().equals("0")){
            holder.likeLabel_TV.setText("Like");
        }else{
            holder.likeLabel_TV.setText("Likes");
        }
        if(model.getLike_status().equals("true")){
            holder.Like_IV.setBackgroundResource(R.drawable.ic_like);
        }else{
            holder.Like_IV.setBackgroundResource(R.drawable.ic_unlike);
        }

        if(model.getOther_comment().equals("") && model.getCommentimg().equals("")){
            holder.CommentContainer.setVisibility(View.GONE);
        }else{
            holder.CommentContainer.setVisibility(View.VISIBLE);
        }


        if(model.getIsimagestatus().equals("true")){
            holder.commentImg.setVisibility(View.VISIBLE);
            holder.otherUserComment_Tv.setVisibility(View.GONE);
//            CommonDialogs.getSquareImage(mContext,model.getCommentimg(),holder.commentImg);
            CommonConst.Companion.loadGlide(mContext,model.getCommentimg(),R.drawable.empty).into(holder.commentImg);

        }else{
            holder.commentImg.setVisibility(View.GONE);
            holder.otherUserComment_Tv.setVisibility(View.VISIBLE);
            holder.otherUserComment_Tv.setText(model.getOther_comment());
        }

//        CommonDialogs.getDisplayImage(mContext,model.getOther_image(),holder.other_UserImage);
        CommonConst.Companion.loadGlide(mContext,model.getOther_image(),R.drawable.user_pic).into(holder.other_UserImage);

        holder.otherUsername_TV.setText(model.getOther_name());


        holder.userLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(model.getId().equals(GeneralValues.get_user_id(mContext))){
                    Bundle args = new Bundle();
                    args.putString("From", "wall");
                    ((BaseActivity)mContext).navigateFragmentTransaction_ARG(R.id.homeContainer, new MyProfileFragment(),args);
                }else{
                    Bundle args = new Bundle();
                    args.putString("userId", model.getId());
                    ((BaseActivity)mContext).navigateFragmentTransaction_ARG(R.id.homeContainer, new SelectedUserProfileFragment(),args);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }
}
