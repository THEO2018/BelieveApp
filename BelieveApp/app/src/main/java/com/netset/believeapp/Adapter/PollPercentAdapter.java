package com.netset.believeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.PollsDetailModel;
import com.netset.believeapp.R;

import java.util.List;

/**
 * Created by netset on 31/1/18.
 */

public class PollPercentAdapter extends RecyclerView.Adapter<PollPercentAdapter.MyViewHolder> {
    List<PollsDetailModel.Option> itemList;
    Context mContext;




    public class MyViewHolder extends RecyclerView.ViewHolder {

        ProgressBar Progress;
        TextView itemNameTV, percentTV;

        public MyViewHolder(View view) {
            super(view);
            Progress = view.findViewById(R.id.progress);
            itemNameTV = view.findViewById(R.id.itemName_TV);
            percentTV = view.findViewById(R.id.percent_TV);
        }
    }

    public PollPercentAdapter(Context mContext, List<PollsDetailModel.Option> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @Override
    public PollPercentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_poll_percent_view, parent, false);

        return new PollPercentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PollPercentAdapter.MyViewHolder holder, final int position) {
        Log.e("position",position+"-----Size----"+itemList.size());
        holder.itemNameTV.setText(itemList.get(position).option);
        holder.percentTV.setText(String.valueOf(itemList.get(position).percent)+"%");//, true);
        holder.Progress.setProgress(itemList.get(position).percent);//, true);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
