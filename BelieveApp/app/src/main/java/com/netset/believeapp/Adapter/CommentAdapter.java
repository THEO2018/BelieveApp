package com.netset.believeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.PollsDetailModel;
import com.netset.believeapp.GsonModel.PrayerDetailModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.activity.BaseActivity;

import java.util.List;

/**
 * Created by netset on 29/1/18.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    List<PollsDetailModel.Comment> comnt_List;
    List<PrayerDetailModel.Comment> commentList;
    Context mContext;
    String type;
    boolean showFullList = false;

    public CommentAdapter(BaseActivity baseActivity, List<PrayerDetailModel.Comment> comments, boolean b, String pray) {
        this.mContext = baseActivity;
        this.commentList = comments;
        this.type = pray;
        this.showFullList = b;

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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
            CommonDialogs.getDisplayImage(mContext,commentList.get(position).userId.profileImage,holder.blogImg_IV,"#d3d3d3");
        }


    }

    @Override
    public int getItemCount() {

        if(type.equals("poll")) {
            if (showFullList) {
                return comnt_List.size();
            } else {
                if (comnt_List.size() <= 2) {
                    return comnt_List.size();
                } else {
                    return 2;
                }
            }
        }else{
            if (showFullList) {
                return commentList.size();
            } else {
                if (commentList.size() <= 2) {
                    return commentList.size();
                } else {
                    return 2;
                }
            }
        }
    }












}
