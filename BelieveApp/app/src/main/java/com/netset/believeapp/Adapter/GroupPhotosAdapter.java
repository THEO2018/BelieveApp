package com.netset.believeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.netset.believeapp.CommonConst;
import com.netset.believeapp.Fragment.ImageDialogFragment;
import com.netset.believeapp.Model.PhotosModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;
import java.util.Random;

/**
 * Created by netset on 26/4/18.
 */

public class GroupPhotosAdapter extends RecyclerView.Adapter<GroupPhotosAdapter.MyViewHolder> {
    List<PhotosModel> imageList;
    Context mContext;
    private Random mRandom = new Random();


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image_IV;

        public MyViewHolder(View view) {
            super(view);
            image_IV = view.findViewById(R.id.mediaImage_IV);
        }
    }

    public GroupPhotosAdapter(Context mContext, List<PhotosModel> imageList) {
        this.mContext = mContext;
        this.imageList = imageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_media_fragment, parent, false);

        return new GroupPhotosAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupPhotosAdapter.MyViewHolder holder, final int position) {

//        CommonDialogs.getSquareImage2(mContext,imageList.get(position).getPhoto(),holder.image_IV);
        CommonConst.Companion.loadGlide(mContext,imageList.get(position).getPhoto(),R.drawable.empty).into(holder.image_IV);


        holder.image_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.parse(imageList.get(position).getPhoto()), "image/*");
//                mContext.startActivity(intent);

                FragmentActivity activity = (FragmentActivity)(mContext);
                FragmentManager fm = activity.getSupportFragmentManager();
                ImageDialogFragment alertDialog = new ImageDialogFragment(imageList.get(position).getPhoto());
                alertDialog.show(fm, "fragment_alert");

//                ImageDialogFragment imageDialogFragment = new ImageDialogFragment(imageList.get(position).getPhoto());
//                imageDialogFragment.show(
//                       this.supportFragmentManager,
//                        imageDialogFragment.tag
//                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    // Custom method to get a random number between a range
    protected int getRandomIntInRange(int max, int min){
        return mRandom.nextInt((max-min)+min)+min;
    }

}
