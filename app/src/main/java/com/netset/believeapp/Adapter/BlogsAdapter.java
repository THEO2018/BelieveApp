package com.netset.believeapp.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.BlogsListModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;

/**
 * Created by netset on 9/1/18.
 */

public class BlogsAdapter extends RecyclerView.Adapter<BlogsAdapter.MyViewHolder> {
    List<BlogsListModel.Datum> blogList;
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

    public BlogsAdapter(Context mContext, List<BlogsListModel.Datum> blogList) {
        this.mContext = mContext;
        this.blogList = blogList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_blog_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        CommonDialogs.getSquareImage(mContext,blogList.get(position).getBlogImage(),holder.blogImg_IV);
        holder.blogTitle_TV.setText(blogList.get(position).getBlogTitle());
        holder.blogHashTags_TV.setText(blogList.get(position).getCategory());
        holder.blogTiming_TV.setText(blogList.get(position).getTimeAgo());

    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }
}
