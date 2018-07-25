package com.netset.believeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.netset.believeapp.GsonModel.StoreListModel;
import com.netset.believeapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by netset on 27/4/18.
 */

public class StoreListAdapter extends BaseAdapter {

    Context mContext;
    List<StoreListModel.Datum> newsList;
    StoreListAdapter.ViewHolder viewHolder;

    public StoreListAdapter(Context mContext, List<StoreListModel.Datum> menuList) {
        this.mContext = mContext;
        this.newsList = menuList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.item_store_list, parent, false);
            viewHolder = new StoreListAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (StoreListAdapter.ViewHolder) view.getTag();
        }

        viewHolder.newsNameTV.setText(newsList.get(position).getName());

        return view;
    }


    class ViewHolder {

        @BindView(R.id.notesTitle_TV)
        AppCompatTextView newsNameTV;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
