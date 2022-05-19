package com.netset.believeapp.Fragment.classifiedSection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.netset.believeapp.GsonModel.JobsModel;
import com.netset.believeapp.R;

import java.util.List;
/**
 * Created by Mithilesh
 * on 14/03/22 at Netset
 */
public class OtherClassifiedAdapter extends RecyclerView.Adapter<OtherClassifiedAdapter.MyViewHolder> {
    List<JobsModel.Datum> jobList;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView jobTitleTV;
        private TextView jobTimeTV;
        private TextView churchNameTV;


        public MyViewHolder(View view) {
            super(view);
            jobTitleTV = view.findViewById(R.id.jobTitle_TV);
            jobTimeTV = view.findViewById(R.id.jobTime_TV);
            churchNameTV = view.findViewById(R.id.churchName_TV);

        }
    }

    public OtherClassifiedAdapter(Context mContext, List<JobsModel.Datum> imageList) {
        this.mContext = mContext;
        this.jobList = imageList;
    }

    @Override
    public OtherClassifiedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_other_view, parent, false);

        return new OtherClassifiedAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OtherClassifiedAdapter.MyViewHolder holder, final int position) {

        holder.jobTitleTV.setText(jobList.get(position).classifiedTitle);
        holder.churchNameTV.setText(jobList.get(position).classified);
        holder.jobTimeTV.setText(jobList.get(position).timeAgo);
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }


}
