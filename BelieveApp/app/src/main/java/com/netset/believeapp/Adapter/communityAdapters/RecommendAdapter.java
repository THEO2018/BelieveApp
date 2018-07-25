package com.netset.believeapp.Adapter.communityAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.R;

import java.util.List;

/**
 * Created by netset on 19/1/18.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.MyViewHolder> {
    List<BlogsModel> blogList;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImg_IV;
        TextView usrName_TV, fromChurch_TV, sendMsg_TV;

        public MyViewHolder(View view) {
            super(view);
            profileImg_IV = view.findViewById(R.id.profileImg_IV);
            usrName_TV = view.findViewById(R.id.usrName_TV);
            fromChurch_TV = view.findViewById(R.id.fromChurch_TV);
            sendMsg_TV = view.findViewById(R.id.sendMsg_TV);
        }
    }

    public RecommendAdapter(Context mContext, List<BlogsModel> blogList) {
        this.mContext = mContext;
        this.blogList = blogList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recommend_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        String text = "From: <font color=#000000>Faith Pentecostal Church</font>";
        holder.fromChurch_TV.setText(Html.fromHtml(text));
        viewAllNewsSpannable(holder);
    }



    private void viewAllNewsSpannable(MyViewHolder holder) {
        String viewAll_TEXT = mContext.getResources().getString(R.string.label_click_recommend);
        SpannableString content = new SpannableString(viewAll_TEXT);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(clickableSpan_sendMessage, 0, content.length(), 0);
        content.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.light_pink)), 0, content.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        holder.sendMsg_TV.setMovementMethod(LinkMovementMethod.getInstance());
        holder.sendMsg_TV.setTextColor(mContext.getResources().getColor(R.color.light_pink));
        holder.sendMsg_TV.setText(content);
    }

    ClickableSpan clickableSpan_sendMessage = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Toast.makeText(mContext, "Recommendation Sent", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public int getItemCount() {
        return 2;
    }
}
