package com.netset.believeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.netset.believeapp.Model.HomeModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by netset on 9/1/18.
 */

public class SettingAdapter extends BaseAdapter {

    Context mContext;
    List<HomeModel> menuList;
    ViewHolder viewHolder;

    public SettingAdapter(Context mContext, List<HomeModel> menuList) {
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
                    inflate(R.layout.item_setting_view, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }



        if(position ==2){
            if(GeneralValues.get_logintype(mContext).equals("F") || GeneralValues.get_logintype(mContext).equals("G")){
                viewHolder.itemNameTV.setText(menuList.get(position).getTitle());
                viewHolder.itemNameTV.setCompoundDrawablesWithIntrinsicBounds(menuList.get(position).getMenuIcon(), 0,
                        0, 0);
            }else{
                viewHolder.itemNameTV.setText(menuList.get(position).getTitle());
                viewHolder.itemNameTV.setCompoundDrawablesWithIntrinsicBounds(menuList.get(position).getMenuIcon(), 0,
                        R.drawable.right_arrow, 0);
            }
        }
else{
            viewHolder.itemNameTV.setText(menuList.get(position).getTitle());
            viewHolder.itemNameTV.setCompoundDrawablesWithIntrinsicBounds(menuList.get(position).getMenuIcon(), 0,
                    R.drawable.right_arrow, 0);
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.itemName_TV)
        AppCompatTextView itemNameTV;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
