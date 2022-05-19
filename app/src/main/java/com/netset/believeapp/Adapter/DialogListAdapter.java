package com.netset.believeapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.netset.believeapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by netset on 1/2/18.
 */

public class DialogListAdapter extends BaseAdapter {

    Context mContext;
    String[] list;
    ViewHolder viewHolder;

    public DialogListAdapter(Context mContext, String[] list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.spinner_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.itemText.setText(list[position]);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.itemText)
        TextView itemText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
