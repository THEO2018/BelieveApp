package com.netset.believeapp.Adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netset.believeapp.CommonConst;
import com.netset.believeapp.Fragment.ImageDialogFragment;
import com.netset.believeapp.Fragment.birthdaySection.SelectedUserProfileFragment;
import com.netset.believeapp.Fragment.settingScreen.MyProfileFragment;
import com.netset.believeapp.Model.PostsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by netset on 22/1/18.
 */

public class ShowFullPostAdapter extends RecyclerView.Adapter<ShowFullPostAdapter.MyViewHolder> {
    List<PostsModel> blogList= new ArrayList<>();
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgComment;
        CircleImageView profile_image_IV;
        LinearLayout lay_user;
        TextView cmnt_UserName_TV, cmnt_Duration_TV, cmnt_Text_TV;

        public MyViewHolder(View view) {
            super(view);
            cmnt_UserName_TV = view.findViewById(R.id.cmnt_UserName_TV);
            profile_image_IV = view.findViewById(R.id.profile_image_IV);
            cmnt_Duration_TV = view.findViewById(R.id.cmnt_Duration_TV);
            cmnt_Text_TV = view.findViewById(R.id.cmnt_Text_TV);
            imgComment = view.findViewById(R.id.img_comment);
            lay_user = view.findViewById(R.id.postAction_Container);
        }
    }

    public ShowFullPostAdapter(Context mContext, List<PostsModel> blogList) {
        this.mContext = mContext;
        this.blogList = blogList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final PostsModel model = blogList.get(position);
        holder.cmnt_Duration_TV.setText(model.getTime());
        holder.cmnt_UserName_TV.setText(model.getOther_name());
//        CommonDialogs.getDisplayImage(mContext,model.getOther_image(),holder.profile_image_IV,"#d3d3d3");
        CommonConst.Companion.loadGlide(mContext,model.getOther_image(),R.drawable.empty).into(holder.profile_image_IV);
        if(model.getIsimagestatus().equals("true")){
            holder.imgComment.setVisibility(View.VISIBLE);
            holder.cmnt_Text_TV.setVisibility(View.GONE);
//            MemoryCacheUtils.removeFromCache(model.getCommentimg(), ImageLoader.getInstance().getMemoryCache());
//            DiskCacheUtils.removeFromCache(model.getCommentimg(), ImageLoader.getInstance().getDiskCache());
//            CommonDialogs.getSquareImage(mContext,model.getCommentimg(),holder.imgComment);
            CommonConst.Companion.loadGlide(mContext,model.getCommentimg(),R.drawable.empty).into(holder.imgComment);

            holder.imgComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String extension = MimeTypeMap.getFileExtensionFromUrl(model.getCommentimg());
//                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//                    Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
//                    mediaIntent.setDataAndType(Uri.parse(model.getCommentimg()), mimeType);
//                    if (null != mediaIntent.resolveActivity(mContext.getPackageManager())) {
//                        mContext.startActivity(mediaIntent);
//                    }
                    FragmentActivity activity = (FragmentActivity)(mContext);
                    FragmentManager fm = activity.getSupportFragmentManager();
                    ImageDialogFragment alertDialog = new ImageDialogFragment(model.getCommentimg());
                    alertDialog.show(fm, "P");
                }
            });

        }else{
            holder.imgComment.setVisibility(View.GONE);
            holder.cmnt_Text_TV.setVisibility(View.VISIBLE);
            holder.cmnt_Text_TV.setText(model.getOther_comment());
        }

        holder.lay_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(model.getOther_id().equals(GeneralValues.get_user_id(mContext))){
                    Bundle args = new Bundle();
                    args.putString("From", "fullpost");
                    ((BaseActivity)mContext).navigateFragmentTransaction_ARG(R.id.homeContainer, new MyProfileFragment(),args);
                }else{
                    Bundle args = new Bundle();
                    args.putString("userId", model.getOther_id());
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
