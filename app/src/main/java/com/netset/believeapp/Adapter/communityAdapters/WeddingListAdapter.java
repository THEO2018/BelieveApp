package com.netset.believeapp.Adapter.communityAdapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netset.believeapp.Adapter.CommentAdapter;
import com.netset.believeapp.GsonModel.MarriagesModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import java.util.List;

/**
 * Created by netset on 18/1/18.
 */

public class WeddingListAdapter extends RecyclerView.Adapter<WeddingListAdapter.MyViewHolder> {
    List<MarriagesModel.WeddingList> blogList;
    Context mContext;
    boolean showFullList;
    private OnWeddingItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView frstPrsnImg_IV;
        TextView frstPrsnName_TV;

        public MyViewHolder(View view) {
            super(view);
            frstPrsnImg_IV = view.findViewById(R.id.proImage_IV);
            frstPrsnName_TV = view.findViewById(R.id.proName_TV);

        }
    }

    public WeddingListAdapter(Context mContext, List<MarriagesModel.WeddingList> blogList, boolean showFullList,OnWeddingItemClickListener listener) {
        this.mContext = mContext;
        this.blogList = blogList;
        this.showFullList = showFullList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wedding_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if(blogList.size()>0) {
        holder.frstPrsnName_TV.setText(blogList.get(position).getTitle());
        CommonDialogs.getSquareImage2(mContext, blogList.get(position).getCoverPhoto(), holder.frstPrsnImg_IV);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onWeddingItemClick(position);

            }
        });

    }

    @Override
    public int getItemCount() {

        if (showFullList) {
            return blogList.size();
        } else {
            if(blogList.size()<=3){
                return blogList.size();
            }else{
                return 3;
            }

        }
    }

    public interface OnWeddingItemClickListener {
        void onWeddingItemClick(int position);
    }
}
