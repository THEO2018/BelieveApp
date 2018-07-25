package com.netset.believeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.AlbumModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;

/**
 * Created by netset on 22/1/18.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder>  {
    List<AlbumModel.Datum> blogList;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView albumIcon_IV;
        TextView albumName_TV;

        public MyViewHolder(View view) {
            super(view);
            albumName_TV = view.findViewById(R.id.albumName_TV);
            albumIcon_IV = view.findViewById(R.id.albumIcon_IV);
        }
    }

    public AlbumsAdapter(Context mContext, List<AlbumModel.Datum> blogList) {
        this.mContext = mContext;
        this.blogList = blogList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_music_album, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.albumName_TV.setText(blogList.get(position).albumTitle);
        CommonDialogs.getSquareImage3(mContext,blogList.get(position).albumImage,holder.albumIcon_IV);
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }
}
