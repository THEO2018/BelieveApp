package com.netset.believeapp.Adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.netset.believeapp.Model.HomeModel;
import com.netset.believeapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by netset on 9/1/18.
 */

public class HomeAdapter extends BaseAdapter {

    Context mContext;
    List<HomeModel> menuList;
    ViewHolder viewHolder;

    public HomeAdapter(Context mContext, List<HomeModel> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(mContext).
                    inflate(R.layout.item_home_screen, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.catNameTV.setText(menuList.get(position).getTitle());
        //Picasso.with(mContext).load(menuList.get(position).getMenuIcon()).placeholder(R.drawable.ic_stub).into(holder.icon_IM);
        Picasso.with(mContext)
                .load(menuList.get(position).getMenuIcon())
                .into(viewHolder.catIconIM, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.loadingProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        viewHolder.catIconIM.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_no_image));
                    }
                });


        return view;
    }


    class ViewHolder {
        @BindView(R.id.catIcon_IM)
        AppCompatImageView catIconIM;
        @BindView(R.id.catName_TV)
        AppCompatTextView catNameTV;
        @BindView(R.id.loading_ProgressBar)
        ProgressBar loadingProgressBar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
