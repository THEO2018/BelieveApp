package com.netset.believeapp.Adapter.communityAdapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.AlumniModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;

/**
 * Created by netset on 19/1/18.
 */

public class AlumniMembersAdapter extends RecyclerView.Adapter<AlumniMembersAdapter.MyViewHolder> {
    List<AlumniModel.Datum> blogList;
    Context mContext;
    boolean showFullList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImg_IV;
        TextView usrName_TV, usrLoc_TV;
        AppCompatImageView imgGlob;

        public MyViewHolder(View view) {
            super(view);
            profileImg_IV = view.findViewById(R.id.profileImg_IV);
            usrName_TV = view.findViewById(R.id.usrName_TV);
            usrLoc_TV = view.findViewById(R.id.usrLoc_TV);
            imgGlob = view.findViewById(R.id.earth_IV);
        }
    }

    public AlumniMembersAdapter(Context mContext, List<AlumniModel.Datum> blogList) {
        this.mContext = mContext;
        this.blogList = blogList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alumni_members_view, parent, false);

        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        CommonDialogs.getDisplayImage(mContext,blogList.get(position).getProfileImage(),holder.profileImg_IV,"#d3d3d3");
        holder.usrName_TV.setText(blogList.get(position).getFirstName()+" "+blogList.get(position).getLastName());
        holder.usrLoc_TV.setText(blogList.get(position).getCountry());
        if(blogList.get(position).getCountry().equals("")){
            holder.imgGlob.setVisibility(View.GONE);
        }else{
            holder.imgGlob.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return blogList.size();
    }
}
