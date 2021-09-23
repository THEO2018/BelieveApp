package com.netset.believeapp.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.EventsModel;
import com.netset.believeapp.GsonModel.MarriagesModel;
import com.netset.believeapp.R;
import com.netset.believeapp.activity.BaseActivity;

import java.util.List;

/**
 * Created by netset on 9/1/18.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    List<EventsModel.Datum> blogList;
    Context mContext;
    String from;
    List<MarriagesModel.UpcomingMarriage> dataList;
    View.OnClickListener mOnClickListener;


    public EventsAdapter(BaseActivity baseActivity, List<MarriagesModel.UpcomingMarriage> upcomingMarriages, String from) {
        this.mContext = baseActivity;
        this.dataList = upcomingMarriages;
        this.from = from;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView blogTitle_TV, blogTiming_TV, blogLocation_TV;

        public MyViewHolder(View view) {
            super(view);
            blogTitle_TV = view.findViewById(R.id.eventTitle_TV);
            blogTiming_TV = view.findViewById(R.id.eventDate_TV);
            blogLocation_TV = view.findViewById(R.id.eventLoc_TV);
        }
    }

    public EventsAdapter(Context mContext, List<EventsModel.Datum> blogList,String from) {
        this.mContext = mContext;
        this.blogList = blogList;
        this.from = from;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_events_fragment, parent, false);

        if (mOnClickListener != null) {
            itemView.setOnClickListener(mOnClickListener);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if(from.equals("event")){
            holder.blogTitle_TV.setText(blogList.get(position).getTitle());
            holder.blogTiming_TV.setText(blogList.get(position).getDate());
            holder.blogLocation_TV.setText(blogList.get(position).getVenue());
        }
        else{
            holder.blogTitle_TV.setText(dataList.get(position).title);
            holder.blogTiming_TV.setText(dataList.get(position).date);
            holder.blogLocation_TV.setText(dataList.get(position).venue);
        }

    }

    @Override
    public int getItemCount() {

        if(from.equals("event")){
            return blogList.size();
        }else{
            return dataList.size();
        }

    }
}
