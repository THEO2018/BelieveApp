package com.netset.believeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.ArtistModel;
import com.netset.believeapp.GsonModel.RecentPlayListModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;

/**
 * Created by netset on 22/1/18.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> {
    List<RecentPlayListModel.Datum> blogList;
    List<ArtistModel.Datum> artistList;
    Context mContext;
    String From;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView songIcon_IV;
        TextView songsTitle_TV, songDuration_TV;


        public MyViewHolder(View view) {
            super(view);
            songsTitle_TV = view.findViewById(R.id.songsTitle_TV);
            songIcon_IV = view.findViewById(R.id.songIcon_IV);
            songDuration_TV = view.findViewById(R.id.songDuration_TV);
        }
    }

    public SongsAdapter(Context mContext, List<RecentPlayListModel.Datum> blogList,String From) {
        this.mContext = mContext;
        this.blogList = blogList;
        this.From = From;
    }

    public SongsAdapter(Context mContext, List<ArtistModel.Datum> artistlist ,String From,String Extra) {
        this.mContext = mContext;
        this.artistList = artistlist;
        this.From = From;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_songs_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        if(From.equals("songs")){
            holder.songsTitle_TV.setText(blogList.get(position).songTitle);
            CommonDialogs.getDisplayImage2(mContext,blogList.get(position).albumId.albumImage,holder.songIcon_IV,"#dedede");
        }
        else{
            holder.songsTitle_TV.setText(artistList.get(position).artistName);
            CommonDialogs.getDisplayImage2(mContext,artistList.get(position).artistImage,holder.songIcon_IV,"#dedede");
        }

    }

    @Override
    public int getItemCount() {
        if(From.equals("songs")){
            return blogList.size();
        }
        else{
            return artistList.size();
        }


    }
}
