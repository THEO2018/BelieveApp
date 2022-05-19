package com.netset.believeapp.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.MarriagesModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;

/**
 * Created by netset on 1/5/18.
 */

public class AdviceAdapter  extends RecyclerView.Adapter<AdviceAdapter.MyViewHolder> {
        List<MarriagesModel.Advice> grpList;
        Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView blogImg_IV;
        TextView blogTitle_TV, blogTiming_TV, blogHashTags_TV;

        public MyViewHolder(View view) {
            super(view);
            blogTitle_TV = view.findViewById(R.id.blogTitle_TV);
            blogImg_IV = view.findViewById(R.id.blogImage_IV);
            blogTiming_TV = view.findViewById(R.id.blogTiming_TV);
            blogHashTags_TV = view.findViewById(R.id.hashTag_TV);
        }
    }

    public AdviceAdapter(Context mContext, List<MarriagesModel.Advice> grpList) {
        this.mContext = mContext;
        this.grpList = grpList;
    }

    @Override
    public AdviceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_blog_view, parent, false);

        return new AdviceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdviceAdapter.MyViewHolder holder, final int position) {
        CommonDialogs.getSquareImage(mContext,grpList.get(position).blogImage,holder.blogImg_IV);
        holder.blogTitle_TV.setText(grpList.get(position).blogTitle);
        holder.blogHashTags_TV.setText(grpList.get(position).category);
        holder.blogTiming_TV.setText(grpList.get(position).timeAgo);

    }

    @Override
    public int getItemCount() {
        return grpList.size();
    }
}