package com.netset.believeapp.Adapter.communityAdapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.BirthdayModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;

/**
 * Created by netset on 18/1/18.
 */

public class BdayProListAdapter extends RecyclerView.Adapter<BdayProListAdapter.MyViewHolder> {
    List<BirthdayModel.Birthdaylist> blogList;
    Context mContext;
    boolean showFullList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView proImage_IV;
        TextView proName_TV;

        public MyViewHolder(View view) {
            super(view);
            proImage_IV = view.findViewById(R.id.proImage_IV);
            proName_TV = view.findViewById(R.id.proName_TV);
        }
    }

    public BdayProListAdapter(Context mContext, List<BirthdayModel.Birthdaylist> blogList, boolean showFullList) {
        this.mContext = mContext;
        this.blogList = blogList;
        this.showFullList = showFullList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wedding_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        CommonDialogs.getSquareImage(mContext,blogList.get(position).bdayListCoverPhoto,holder.proImage_IV);
        holder.proName_TV.setText(blogList.get(position).bdayListTitle);
    }

    @Override
    public int getItemCount() {

        if (showFullList) {
            return blogList.size();
        } else {
            if(blogList.size()<=3){
                return blogList.size();
            }else{
                return 3;
            }
        }
    }
}
