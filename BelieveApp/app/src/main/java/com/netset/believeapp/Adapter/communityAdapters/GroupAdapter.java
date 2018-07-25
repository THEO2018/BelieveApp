package com.netset.believeapp.Adapter.communityAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.BlogModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by netset on 18/1/18.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {
    List<BlogModel> blogList;
    Context mContext;
    ArrayList<String> arrayList = new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView gpImg_IV, memImage_1_IV, memImage_2_IV, memImage_3_IV;
        TextView grpTitle_TV, grpCount_TV;

        public MyViewHolder(View view) {
            super(view);
            grpTitle_TV = view.findViewById(R.id.grpName_TV);
            gpImg_IV = view.findViewById(R.id.grpImage_IV);
            memImage_1_IV = view.findViewById(R.id.memberImage_IV_1);
            memImage_2_IV = view.findViewById(R.id.memberImage_IV_2);
            memImage_3_IV = view.findViewById(R.id.memberImage_IV_3);
            grpCount_TV = view.findViewById(R.id.gpMemberCount_TV);
        }
    }

    public  GroupAdapter(Context mContext, List<BlogModel> blogList) {
        this.mContext = mContext;
        this.blogList = blogList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

         BlogModel model = blogList.get(position);

         holder.grpTitle_TV.setText(model.getGroupName());
         holder.grpCount_TV.setText(model.getTotalMembers()+" Members");
         CommonDialogs.getDisplayImage(mContext,model.getGroupImage(),holder.gpImg_IV,"#ADADAD");
       //  Picasso.with(mContext).load(model.getBlogImage()).into(holder.gpImg_IV)
        arrayList.clear();
        for(int i=0;i<model.getUsers().size();i++){
            arrayList.add(model.getUsers().get(i).getProfileImage());
        }

        if(arrayList.size()>0){

            for(int i =0;i<arrayList.size();i++){

                if(i == 0){
                    CommonDialogs.getDisplayImage(mContext,arrayList.get(i),holder.memImage_1_IV,"#FFFFFF");
                }else if(i ==1){
                    CommonDialogs.getDisplayImage(mContext,arrayList.get(i),holder.memImage_2_IV,"#FFFFFF");
                }
                else if(i==2){
                    CommonDialogs.getDisplayImage(mContext,arrayList.get(i),holder.memImage_3_IV,"#FFFFFF");
                }
            }
        }

    }



    @Override
    public int getItemCount() {
        return blogList.size();
    }
}
