package com.netset.believeapp.Fragment.bibleFrag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.netset.believeapp.R;
import com.netset.believeapp.bibleModel.BibleModelItem;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BibleListAdapter extends BaseAdapter {
    Context mContext;
    List<BibleModelItem> bibleList;
    BibleListAdapter.ViewHolder viewHolder;
    URL url = null;

    public BibleListAdapter(Context mContext, List<BibleModelItem> bibleList) {
        this.mContext = mContext;
        this.bibleList = bibleList;
    }

    @Override
    public int getCount() {
        return bibleList.size();
    }

    @Override
    public Object getItem(int position) {
        return bibleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.item_store_list, parent, false);
            viewHolder = new BibleListAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BibleListAdapter.ViewHolder) convertView.getTag();
        }
       /* try {
            url = new URL(bibleList.get(position).getBible_url());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert url != null;
        String host = url.getHost();*/
        viewHolder.newsNameTV.setText(bibleList.get(position).getTitle());
//        viewHolder.newsNameTV.setText(host);

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.notesTitle_TV)
        AppCompatTextView newsNameTV;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
