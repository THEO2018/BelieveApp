package com.netset.believeapp.Adapter.communityAdapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.MarriagesModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;

/**
 * Created by netset on 18/1/18.
 */

public class CoupleListAdapter extends RecyclerView.Adapter<CoupleListAdapter.MyViewHolder> {
    List<MarriagesModel.Betrothed> blogList;
    Context mContext;
    boolean showFullList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView frstPrsnImg_IV, scndPrsnImg_IV;
        TextView frstPrsnName_TV, scndPrsnName_TV;

        public MyViewHolder(View view) {
            super(view);
            frstPrsnImg_IV = view.findViewById(R.id.frstPrsn_IV);
            scndPrsnImg_IV = view.findViewById(R.id.scndPrsn_IV);
            frstPrsnName_TV = view.findViewById(R.id.fstPrsn_Name_TV);
            scndPrsnName_TV = view.findViewById(R.id.scndPrsn_Name_TV);
        }
    }

    public CoupleListAdapter(Context mContext, List<MarriagesModel.Betrothed> blogList, boolean showFullList) {
        this.mContext = mContext;
        this.blogList = blogList;
        this.showFullList = showFullList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (!showFullList) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_mrg_cpl, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_marg_cpl_full, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.frstPrsnName_TV.setText(blogList.get(position).getFirstUserId().getFirstName()+" "+blogList.get(position).getFirstUserId().getLastName());
        CommonDialogs.getDisplayImage(mContext,blogList.get(position).getFirstUserId().getProfileImage(),holder.frstPrsnImg_IV,"#d3d3d3");
        holder.scndPrsnName_TV.setText(blogList.get(position).getSecondUserId().getFirstName()+" "+blogList.get(position).getSecondUserId().getLastName());
        CommonDialogs.getDisplayImage(mContext,blogList.get(position).getSecondUserId().getProfileImage(),holder.scndPrsnImg_IV,"#d3d3d3");

    }

    @Override
    public int getItemCount() {
        if (showFullList) {
            return blogList.size();
        } else {
            return blogList.size();
        }
    }
}
