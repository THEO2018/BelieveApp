package com.netset.believeapp.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.PollsDetailModel;
import com.netset.believeapp.R;

import java.util.List;

/**
 * Created by netset on 31/1/18.
 */

public class PollsOptionListAdapter extends RecyclerView.Adapter<PollsOptionListAdapter.MyViewHolder> {
    List<PollsDetailModel.Option> itemList;
    Context mContext;
   


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemName_TV;
        ImageView itemSelect_IV;
        LinearLayout optionLay;

        public MyViewHolder(View view) {
            super(view);
            itemSelect_IV = view.findViewById(R.id.itemSelect_IV);
            itemName_TV = view.findViewById(R.id.itemName_TV);
            optionLay = view.findViewById(R.id.option_lay);
        }
    }

    public PollsOptionListAdapter(Context mContext, List<PollsDetailModel.Option> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_polls_option_list_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.itemName_TV.setText(itemList.get(position).option);
        if (itemList.get(position).myVotePoll.equals(true)) {
            holder.itemSelect_IV.setImageResource(R.drawable.ic_star_selected);
        }else{
            holder.itemSelect_IV.setImageResource(R.drawable.ic_star_white);
            holder.itemSelect_IV.setColorFilter(ContextCompat.getColor(mContext, R.color.redish_pink), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
