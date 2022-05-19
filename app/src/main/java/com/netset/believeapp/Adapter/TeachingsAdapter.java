package com.netset.believeapp.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.DonationModel;
import com.netset.believeapp.Model.DonationModelNew;
import com.netset.believeapp.Model.Teaching;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;

/**
 * Created by netset on 29/1/18.
 */

public class TeachingsAdapter extends RecyclerView.Adapter<TeachingsAdapter.MyViewHolder>  {
    List<Teaching> apnt_List;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView blogImg_IV;
        TextView blogTitle_TV, blogTiming_TV, blogHashTags_TV;

        public MyViewHolder(View view) {
            super(view);
            blogTitle_TV = view.findViewById(R.id.message_TV);
            blogImg_IV = view.findViewById(R.id.teachingImg_IV);
            blogTiming_TV = view.findViewById(R.id.time_TV);
            blogHashTags_TV = view.findViewById(R.id.hashTag_TV);
        }
    }

    public TeachingsAdapter(Context mContext, List<Teaching> apnt_List) {
        this.mContext = mContext;
        this.apnt_List = apnt_List;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_teaching_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.blogTitle_TV.setText(apnt_List.get(position).getBlog_title());

        holder.blogTiming_TV.setText(apnt_List.get(position).getTime_ago());
        holder.blogHashTags_TV.setText("#GIVING");
        CommonDialogs.getSquareImage(mContext,apnt_List.get(position).getBlog_image(),holder.blogImg_IV);

    }

    @Override
    public int getItemCount() {
        return apnt_List.size();
    }
}
