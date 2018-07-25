package com.netset.believeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.netset.believeapp.GsonModel.MarriagesModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;

/**
 * Created by netset on 1/5/18.
 */

public class UpcomingWeddingAdapter extends RecyclerView.Adapter<UpcomingWeddingAdapter.MyViewHolder> {
    List<MarriagesModel.UpcomingMarriage> grpList;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView usrImg_IV;
        public MyViewHolder(View view) {
            super(view);
            usrImg_IV = view.findViewById(R.id.imageView);
        }
    }

    public UpcomingWeddingAdapter(Context mContext, List<MarriagesModel.UpcomingMarriage> grpList) {
        this.mContext = mContext;
        this.grpList = grpList;
    }

    @Override
    public UpcomingWeddingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_wedding_view, parent, false);

        return new UpcomingWeddingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UpcomingWeddingAdapter.MyViewHolder holder, final int position) {
        CommonDialogs.getSquareImage(mContext,grpList.get(position).eventCover,holder.usrImg_IV);

}

    @Override
    public int getItemCount() {
        return grpList.size();
    }
}