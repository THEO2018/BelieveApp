package com.netset.believeapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.netset.believeapp.R;
import com.netset.believeapp.pozo.CountriesPozo;
import com.netset.believeapp.pozo.StatesPozo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by netset on 1/2/18.
 */

public class DialogAdapterPozo extends BaseAdapter {

    Context mContext;
    String[] list;
    ViewHolder viewHolder;
    int totalSize;

    public List<CountriesPozo.Country> countries;
    public List<StatesPozo.State> state;

    public DialogAdapterPozo(Context mContext, List<CountriesPozo.Country> countries, boolean is) {
        this.mContext = mContext;
        this.countries = countries;
        this.totalSize = countries.size();
    }

    public DialogAdapterPozo(Context mContext, List<StatesPozo.State> state) {
        this.mContext = mContext;
        this.state = state;
        this.totalSize = state.size();
    }

    @Override
    public int getCount() {
        return totalSize;
    }

    @Override
    public Object getItem(int position) {
        if (countries != null) {
            return countries.get(position);
        }
        return state.get(position);
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

        if (countries != null) {
            viewHolder.itemText.setText(countries.get(position).name);
        } else {
            viewHolder.itemText.setText(state.get(position).name);
        }
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
