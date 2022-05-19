package com.netset.believeapp.Adapter.communityAdapters;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netset.believeapp.Fragment.communitySection.PollsFragment;
import com.netset.believeapp.Fragment.communitySection.ShowPollFragment;
import com.netset.believeapp.GsonModel.PollsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;

/**
 * Created by netset on 19/1/18.
 */

public class PollsAdapter extends RecyclerView.Adapter<PollsAdapter.MyViewHolder> {
    List<PollsModel.Datum> blogList;
    Context mContext;
    boolean showFullList;
    PollsFragment fragment;
    String type;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView pollImg_IV;
        TextView pollName_TV, duration_TV, postedBy_TV;
        RelativeLayout polling_parentView;

        public MyViewHolder(View view) {
            super(view);
            pollImg_IV = view.findViewById(R.id.pollImg_IV);
            pollName_TV = view.findViewById(R.id.pollName_TV);
            duration_TV = view.findViewById(R.id.duration_TV);
            postedBy_TV = view.findViewById(R.id.postedBy_TV);
            polling_parentView = view.findViewById(R.id.polling_parentView);
        }
    }

    public PollsAdapter(Context mContext, PollsFragment pollsFragment, List<PollsModel.Datum> blogList,String type) {
        this.mContext = mContext;
        this.blogList = blogList;
        this.fragment = pollsFragment;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_polls_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.pollName_TV.setText(blogList.get(position).pollTitle);
        holder.postedBy_TV.setText("Admin");
        holder.duration_TV.setText(blogList.get(position).timeAgo);
        CommonDialogs.getDisplayImage(mContext,blogList.get(position).pollImage,holder.pollImg_IV,"#d3d3d3");

        holder.polling_parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Fragment showPollFragment = new ShowPollFragment();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isClosed", fragment.isClosed);
                    bundle.putString("id",blogList.get(position).id);
                    showPollFragment.setArguments(bundle);
                    fragment.baseActivity.navigateFragmentTransaction(R.id.homeContainer, showPollFragment);

            }
        });

    }


    @Override
    public int getItemCount() {
        return blogList.size();
    }
}
