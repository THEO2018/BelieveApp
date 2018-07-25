package com.netset.believeapp.Adapter.communityAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.Fragment.communitySection.NewMembersFragment;
import com.netset.believeapp.GsonModel.NewMemberModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;

/**
 * Created by netset on 19/1/18.
 */

public class NewMembersAdapter extends RecyclerView.Adapter<NewMembersAdapter.MyViewHolder> {
    List<NewMemberModel.Datum> blogList;
    Context mContext;
    boolean showFullList;
    private NewMembersFragment mFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImg_IV;
        TextView usrName_TV, usrLoc_TV, sendMsg_TV;

        public MyViewHolder(View view) {
            super(view);
            profileImg_IV = view.findViewById(R.id.profileImg_IV);
            usrName_TV = view.findViewById(R.id.usrName_TV);
            usrLoc_TV = view.findViewById(R.id.usrLoc_TV);
            //sendMsg_TV = view.findViewById(R.id.sendMsg_TV);
        }
    }

    public NewMembersAdapter(Context mContext, NewMembersFragment fragment, List<NewMemberModel.Datum> blogList) {
        this.mContext = mContext;
        this.blogList = blogList;
        mFragment = fragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_members_view, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //viewAllNewsSpannable(holder);
        CommonDialogs.getDisplayImage(mContext,blogList.get(position).getProfileImage(),holder.profileImg_IV,"#d3d3d3");
        holder.usrName_TV.setText(blogList.get(position).getFirstName()+" "+blogList.get(position).getLastName());
        holder.usrLoc_TV.setText(blogList.get(position).getCountry());
    }


    @Override
    public int getItemCount() {
        return blogList.size();
    }
}
