package com.netset.believeapp.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.CommonConst;
import com.netset.believeapp.Fragment.homeMenu.WebViewFragment;
import com.netset.believeapp.GsonModel.PollsDetailModel;
import com.netset.believeapp.GsonModel.PrayerDetailModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.activity.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import me.saket.bettermovementmethod.BetterLinkMovementMethod;

/**
 * Created by netset on 29/1/18.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    List<PollsDetailModel.Comment> comnt_List;
    List<PrayerDetailModel.Comment> commentList;
    Context mContext;
    String type;
    boolean showFullList = false;
    private OnItemClickListener listener;
    public CommentAdapter(BaseActivity baseActivity, List<PrayerDetailModel.Comment> comments, boolean b, String pray, OnItemClickListener listener) {
        this.mContext = baseActivity;
        this.commentList = comments;
        this.type = pray;
        this.showFullList = b;
        this.listener = listener;

    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView blogImg_IV;
        TextView blogTitle_TV, blogTiming_TV, blogComment_TV;

        public MyViewHolder(View view) {
            super(view);
            blogTitle_TV = view.findViewById(R.id.cmnt_UserName_TV);
            blogImg_IV = view.findViewById(R.id.profile_image_IV);
            blogComment_TV = view.findViewById(R.id.cmnt_Text_TV);
            blogTiming_TV = view.findViewById(R.id.cmnt_Duration_TV);
        }
    }


    public CommentAdapter(Context mContext, List<PollsDetailModel.Comment> comnt_List, boolean showFullList,String typeof) {
        this.mContext = mContext;
        this.comnt_List = comnt_List;
        this.showFullList = showFullList;
        this.type = typeof;
    }


    @Override
    public @NotNull MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if(type.equals("poll")){
            holder.blogTitle_TV.setText(comnt_List.get(position).userId.firstName+" "+comnt_List.get(position).userId.lastName);
            holder.blogComment_TV.setText(comnt_List.get(position).commentMsg);
            CommonDialogs.getDisplayImage(mContext,comnt_List.get(position).userId.profileImage,holder.blogImg_IV,"#d3d3d3");
        }else{
            holder.blogTitle_TV.setText(commentList.get(position).userId.firstName+" "+commentList.get(position).userId.lastName);
            holder.blogComment_TV.setText(commentList.get(position).commentMsg);
            holder.blogTiming_TV.setText(commentList.get(position).timeAgo);
//          CommonDialogs.getDisplayImage(mContext,commentList.get(position).userId.profileImage,holder.blogImg_IV,"#d3d3d3");
            CommonConst.Companion.loadGlide(mContext.getApplicationContext(),commentList.get(position).userId.profileImage,R.drawable.empty ).into(holder.blogImg_IV);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
        BetterLinkMovementMethod
                .linkify(Linkify.ALL, holder.blogComment_TV)
                .setOnLinkClickListener((textView, url) -> {
                    if (url.contains("youtube") || url.contains("youtu.be")) {
                        // then redirect to youtube
                    } else if (url.contains("pdf")) {
                        // then redirect to pdf app
                    } else {
                        Bundle args = new Bundle();
                        args.putString("newslink",url);
                        args.putString("newsname","Post");
                        ((BaseActivity)mContext).navigateFragmentTransaction_ARG(R.id.homeContainer, new WebViewFragment(),args);
                    }

                    return true;
                })
                .setOnLinkLongClickListener((textView, url) -> {
                    // Handle long-clicks.
                    return true;
                });


    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    @Override
    public int getItemCount() {

        if(type.equals("poll")) {
            if (showFullList) {
                return comnt_List.size();
            } else {
                return Math.min(comnt_List.size(), 2);
            }
        }else{
            if (showFullList) {
                return commentList.size();
            } else {
                return Math.min(commentList.size(), 2);
            }
        }
    }
}
