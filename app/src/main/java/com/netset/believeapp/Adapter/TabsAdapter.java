package com.netset.believeapp.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netset.believeapp.Model.TabBarModel;
import com.netset.believeapp.R;

import java.util.List;

/**
 * Created by netset on 17/1/18.
 */

public class TabsAdapter extends RecyclerView.Adapter<TabsAdapter.MyViewHolder>  {
    List<TabBarModel> catList;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView catTitle_TV;

        public MyViewHolder(View view) {
            super(view);
            catTitle_TV = view.findViewById(R.id.tabItem_TV);
        }
    }

    public TabsAdapter(Context mContext, List<TabBarModel> catList) {
        this.mContext = mContext;
        this.catList = catList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tab_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.catTitle_TV.setText(catList.get(position).getCatName());

        if (catList.get(position).isSelected()){
            holder.catTitle_TV.setTextColor(mContext.getResources().getColor(R.color.light_pink));
        }else{
            holder.catTitle_TV.setTextColor(mContext.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }
}
