package com.netset.believeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.Model.VolumeModel;
import com.netset.believeapp.R;

import java.util.List;

/**
 * Created by netset on 24/1/18.
 */

public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.MyViewHolder>  {
    List<VolumeModel> volumeList;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        View volView;

        public MyViewHolder(View view) {
            super(view);

            volView = view.findViewById(R.id.volView);
        }
    }

    public VolumeAdapter(Context mContext, List<VolumeModel> volumeList) {
        this.mContext = mContext;
        this.volumeList = volumeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_volume_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if(volumeList.get(position).isSelected()){
           holder.volView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else{
            holder.volView.setBackgroundColor(mContext.getResources().getColor(R.color.dark_grey));
        }
    }

    @Override
    public int getItemCount() {
        return volumeList.size();
    }
}
