package com.netset.believeapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netset.believeapp.Fragment.homeMenu.AllMediaFragment;
import com.netset.believeapp.Fragment.homeMenu.MediaFragment;
import com.netset.believeapp.GsonModel.MediaAlbumModel;
import com.netset.believeapp.R;

import java.util.List;

public class MediaAlbum_Adapter extends RecyclerView.Adapter<MediaAlbum_Adapter.MyViewHolder> {

    private List<MediaAlbumModel.Datum> objects;

    private Context context;
    private LayoutInflater layoutInflater;
    MediaAdapter mediaAdapter;
    MediaFragment fragment;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mediaLayout;
        private LinearLayout albumLay;
        private TextView albumTV;
        private TextView dateTV;
        private TextView viewTV;
        private TextView nodataTV;
        private RecyclerView mediaRV;

        public MyViewHolder(View view) {
            super(view);
            mediaLayout =  view.findViewById(R.id.media_layout);
            albumLay = view.findViewById(R.id.album_lay);
            albumTV =  view.findViewById(R.id.album_TV);
            dateTV =  view.findViewById(R.id.date_TV);
            viewTV =  view.findViewById(R.id.view_TV);
            nodataTV = view.findViewById(R.id.nodata_TV);
            mediaRV =  view.findViewById(R.id.media_RV);
        }
    }


    public MediaAlbum_Adapter(Context context, List<MediaAlbumModel.Datum> arrayList,MediaFragment fragment) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = arrayList;
        this.fragment = fragment;
    }



    @Override
    public MediaAlbum_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_mediaalbum, parent, false);

        return new MediaAlbum_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MediaAlbum_Adapter.MyViewHolder holder, final int position) {

        String viewAll_TEXT = context.getResources().getString(R.string.label_view_all);
        final SpannableString content = new SpannableString(viewAll_TEXT);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.white)), 0, content.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        holder.viewTV.setMovementMethod(LinkMovementMethod.getInstance());
        holder.viewTV.setTextColor(context.getResources().getColor(R.color.white));
        holder.viewTV.setText(content);

        holder.viewTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mediaFragment = new AllMediaFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id",objects.get(position).id);
                mediaFragment.setArguments(bundle);
                fragment.baseActivity.navigateFragmentTransaction(R.id.homeContainer, mediaFragment);
            }
        });
        holder.albumTV.setText(objects.get(position).gallaryTitle);
        holder.dateTV.setText(objects.get(position).date);

        if(objects.get(position).media.size() ==0){
            holder.nodataTV.setVisibility(View.VISIBLE);
            holder.viewTV.setVisibility(View.GONE);
        }else{
            holder.nodataTV.setVisibility(View.GONE);
            holder.viewTV.setVisibility(View.VISIBLE);
        }


        mediaAdapter = new MediaAdapter(context,objects.get(position).media,"short");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.mediaRV.setLayoutManager(mLayoutManager);
        holder.mediaRV.setItemAnimator(new DefaultItemAnimator());
        holder.mediaRV.setAdapter(mediaAdapter);


    }


    @Override
    public int getItemCount() {
        return objects.size();
    }







}
