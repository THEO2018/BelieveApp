package com.netset.believeapp.Adapter.communityAdapters;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.Fragment.birthdaySection.SelectedUserProfileFragment;
import com.netset.believeapp.GsonModel.AllUsersModel;
import com.netset.believeapp.GsonModel.OtherUserModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.activity.BaseActivity;
import com.netset.believeapp.listeners.UserCallback;

import java.util.List;

/**
 * Created by netset on 18/1/18.
 */

public class AddCoupleAdapter extends RecyclerView.Adapter<AddCoupleAdapter.MyViewHolder> {
    List<AllUsersModel.Datum> grpList;
    List<OtherUserModel.Datum> otherList;
    Context mContext;
    boolean isfullList,isSearch;
    UserCallback userCallback;
    View.OnClickListener addUser_callback;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView usrImg_IV;
        TextView usrName_TV, usrloc_TV;
        Button addUsr_BTN;

        public MyViewHolder(View view) {
            super(view);
            usrImg_IV = view.findViewById(R.id.usrImg_IV);
            usrName_TV = view.findViewById(R.id.usrName_TV);
            usrloc_TV = view.findViewById(R.id.usrloc_TV);
            addUsr_BTN = view.findViewById(R.id.add_BTN);
        }
    }

    public AddCoupleAdapter(Context mContext, List<OtherUserModel.Datum> grpList,boolean fulllist) {
        this.mContext = mContext;
        this.otherList = grpList;
        this.isfullList = fulllist;

    }



    public AddCoupleAdapter(Context mContext, List<AllUsersModel.Datum> grpList,boolean fulllist,UserCallback callback) {
        this.mContext = mContext;
        this.grpList = grpList;
        this.isfullList = fulllist;
        this.userCallback = callback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_couple_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if(isfullList) {
            CommonDialogs.getDisplayImage(mContext, grpList.get(position).getProfileImage(), holder.usrImg_IV, "#d3d3d3");
            holder.usrName_TV.setText(grpList.get(position).getFirstName() + " " + grpList.get(position).getLastName());
            holder.usrloc_TV.setText(grpList.get(position).getCountry());
        }
        else{
            CommonDialogs.getDisplayImage(mContext, otherList.get(position).getProfileImage(), holder.usrImg_IV, "#d3d3d3");
            holder.usrName_TV.setText(otherList.get(position).getFirstName() + " " + otherList.get(position).getLastName());
            holder.usrloc_TV.setText(otherList.get(position).getCountry());
        }


        holder.addUsr_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCallback.onUserClick(position);
            }
        });

        holder.usrImg_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                if (isfullList) {
                    args.putString("userId", grpList.get(position).getId());
                } else {
                    args.putString("userId", otherList.get(position).getId());
                }
                args.putString("from", "addcouple");
                ((BaseActivity) mContext).navigateFragmentTransaction_ARG(R.id.editViewContainer, new SelectedUserProfileFragment(), args);
            }
        });



    }

    @Override
    public int getItemCount() {
        if(isfullList) {
            return grpList.size();
        }
        else{
            return otherList.size();
        }
    }
}
