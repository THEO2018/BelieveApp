package com.netset.believeapp.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.JobsModel;
import com.netset.believeapp.R;

import java.util.List;
import java.util.Random;

/**
 * Created by netset on 29/1/18.
 */

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.MyViewHolder> {
    List<JobsModel.Datum> jobList;
    Context mContext;
    private Random mRandom = new Random();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView jobTitleTV;
        private TextView jobTimeTV;
        private TextView churchNameTV;
        private TextView locationTV;

        public MyViewHolder(View view) {
            super(view);
            jobTitleTV = view.findViewById(R.id.jobTitle_TV);
            jobTimeTV = view.findViewById(R.id.jobTime_TV);
            churchNameTV = view.findViewById(R.id.churchName_TV);
            locationTV = view.findViewById(R.id.locationTV);
        }
    }

    public JobsAdapter(Context mContext, List<JobsModel.Datum> imageList) {
        this.mContext = mContext;
        this.jobList = imageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jobs_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.jobTitleTV.setText(jobList.get(position).classifiedTitle);
        holder.churchNameTV.setText(jobList.get(position).classified);
        holder.jobTimeTV.setText(jobList.get(position).timeAgo);
        holder.locationTV.setText(jobList.get(position).venue);
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }


}
