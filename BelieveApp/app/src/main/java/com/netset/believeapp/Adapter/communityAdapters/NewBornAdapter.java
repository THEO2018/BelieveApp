package com.netset.believeapp.Adapter.communityAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.BirthdayModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;

/**
 * Created by netset on 19/1/18.
 */

public class NewBornAdapter extends RecyclerView.Adapter<NewBornAdapter.MyViewHolder>  {
    List<BirthdayModel.BirthNews> blogList;
    Context mContext;
    boolean showFullList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage_IV;
        TextView headline_TV, time_TV;

        public MyViewHolder(View view) {
            super(view);
            itemImage_IV = view.findViewById(R.id.itemImage_IV);
            headline_TV = view.findViewById(R.id.headline_TV);
            time_TV = view.findViewById(R.id.time_TV);
        }
    }

    public NewBornAdapter(Context mContext, List<BirthdayModel.BirthNews> blogList, boolean showFullList) {
        this.mContext = mContext;
        this.blogList = blogList;
        this.showFullList = showFullList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_born_white, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.time_TV.setText(blogList.get(position).timeAgo);
        holder.headline_TV.setText(blogList.get(position).classifiedTitle);
        CommonDialogs.getSquareImage2(mContext,blogList.get(position).classifiedImage,holder.itemImage_IV);
    }

    @Override
    public int getItemCount() {

        if (showFullList) {
            return blogList.size();
        } else {
            if(blogList.size()<=2){
              return blogList.size();
            }else{
                return 2;
            }

        }
    }

}
