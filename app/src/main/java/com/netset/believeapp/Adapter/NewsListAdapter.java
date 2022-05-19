package com.netset.believeapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.NewsListModel;
import com.netset.believeapp.R;

import java.util.List;

/**
 * Created by netset on 27/4/18.
 */

public class NewsListAdapter extends BaseAdapter {

    Context mContext;
    List<NewsListModel.Data> newsList;
    NewsListAdapter.ViewHolder viewHolder;

    public NewsListAdapter(Context mContext, List<NewsListModel.Data> menuList) {
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
                    inflate(R.layout.item_news_list, parent, false);
            viewHolder = new NewsListAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (NewsListAdapter.ViewHolder) view.getTag();
        }



        return view;
    }


    class ViewHolder {
        ImageView itemImage_IV;
        TextView headline_TV, time_TV,desc_TV;

        ViewHolder(View view) {


            itemImage_IV = view.findViewById(R.id.itemImage_IV);
            headline_TV = view.findViewById(R.id.headline_TV);
            time_TV = view.findViewById(R.id.time_TV);
            desc_TV = view.findViewById(R.id.desc_TV);

        }
    }
}
