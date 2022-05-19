package com.netset.believeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.netset.believeapp.GsonModel.MediaAlbumModel;
import com.netset.believeapp.GsonModel.MediaListModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;
import java.util.Random;

/**
 * Created by netset on 9/1/18.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MyViewHolder> {
    List<MediaListModel.Datum> imageList;
    List<MediaAlbumModel.Medium> mediaShortList;
    Context mContext;
    String from;
    private Random mRandom = new Random();

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image_IV,imageplay_IV;
        RelativeLayout media_lay;

        public MyViewHolder(View view) {
            super(view);
            image_IV = view.findViewById(R.id.mediaImage_IV);
            imageplay_IV = view.findViewById(R.id.img_play);
            media_lay = view.findViewById(R.id.lay_media);
        }
    }

    public MediaAdapter(Context mContext, List<MediaListModel.Datum> imageList,String from,String extra) {
        this.mContext = mContext;
        this.imageList = imageList;
        this.from = from;
    }

    public MediaAdapter(Context mContext, List<MediaAlbumModel.Medium> imageList, String from) {
        this.mContext = mContext;
        this.mediaShortList = imageList;
        this.from = from;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_media_fragment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

    if(from.equals("short")){

        if(mediaShortList.get(position).mediaType.equals("V")){
            holder.imageplay_IV.setVisibility(View.VISIBLE);
            CommonDialogs.getSquareImage2(mContext,mediaShortList.get(position).thumbnail,holder.image_IV);
        }
        else{
            holder.imageplay_IV.setVisibility(View.GONE);
            CommonDialogs.getSquareImage2(mContext,mediaShortList.get(position).mediaFile,holder.image_IV);
        }

        holder.media_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaShortList.get(position).mediaType.equals("V")){
                    String extension = MimeTypeMap.getFileExtensionFromUrl(mediaShortList.get(position).mediaFile);
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                    mediaIntent.setDataAndType(Uri.parse(mediaShortList.get(position).mediaFile), mimeType);
                    mContext.startActivity(mediaIntent);
                }else{

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(mediaShortList.get(position).mediaFile), "image/*");
                    mContext.startActivity(intent);
                }
            }
        });


    }
    else{
        if(imageList.get(position).getMediaType().equals("V")){
            holder.imageplay_IV.setVisibility(View.VISIBLE);
            CommonDialogs.getSquareImage2(mContext,imageList.get(position).getThumbnail(),holder.image_IV);
        }
        else{
            holder.imageplay_IV.setVisibility(View.GONE);
            CommonDialogs.getSquareImage2(mContext,imageList.get(position).getMediaFile(),holder.image_IV);
        }

        holder.media_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageList.get(position).getMediaType().equals("V")){
                    String extension = MimeTypeMap.getFileExtensionFromUrl(imageList.get(position).getMediaFile());
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                    mediaIntent.setDataAndType(Uri.parse(imageList.get(position).getMediaFile()), mimeType);
                    mContext.startActivity(mediaIntent);
                }else{

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(imageList.get(position).getMediaFile()), "image/*");
                    mContext.startActivity(intent);
                }
            }
        });

    }



    }

    @Override
    public int getItemCount() {

        if(from.equals("short")){
            return mediaShortList.size();
        }else{
            return imageList.size();
        }


    }

    // Custom method to get a random number between a range
    protected int getRandomIntInRange(int max, int min){
        return mRandom.nextInt((max-min)+min)+min;
    }
}
