package com.netset.believeapp.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.AboutUsListModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;

/**
 * Created by netset on 25/1/18.
 */

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.MyViewHolder>  {
    List<AboutUsListModel.Datum> blogList;
    Context mContext;
    boolean showFullList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView catIcon_IV;
        TextView catName_TV;

        public MyViewHolder(View view) {
            super(view);
            catIcon_IV = view.findViewById(R.id.catIcon_IV);
            catName_TV = view.findViewById(R.id.catName_TV);
        }
    }

    public AboutUsAdapter(Context mContext, List<AboutUsListModel.Datum> blogList, boolean showFullList) {
        this.mContext = mContext;
        this.blogList = blogList;
        this.showFullList = showFullList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_about_us_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        CommonDialogs.getDisplayImage(mContext,blogList.get(position).getAboutCoverImage(),holder.catIcon_IV,"#d3d3d3");
        holder.catName_TV.setText(blogList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
            return blogList.size();
    }
}
