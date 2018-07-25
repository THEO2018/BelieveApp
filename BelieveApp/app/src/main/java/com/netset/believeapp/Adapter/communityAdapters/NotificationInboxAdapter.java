package com.netset.believeapp.Adapter.communityAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.GroupNotificationModel;
import com.netset.believeapp.R;

import java.util.List;

/**
 * Created by netset on 20/2/18.
 */

public class NotificationInboxAdapter extends RecyclerView.Adapter<NotificationInboxAdapter.MyViewHolder> {
    List<GroupNotificationModel.Notification> grpList;
    Context mContext;
     boolean isFullList = false;




    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView notificationtimeTV, usernameTV, notificationTypeTV;


        public MyViewHolder(View view) {
            super(view);
            notificationtimeTV = view.findViewById(R.id.notification_time_TV);
            notificationTypeTV = view.findViewById(R.id.notificatioTyp_TV);
            usernameTV = view.findViewById(R.id.username_TV);

        }
    }

    public NotificationInboxAdapter(Context mContext, List<GroupNotificationModel.Notification> grpList,boolean isfull) {
        this.mContext = mContext;
        this.grpList = grpList;
        this.isFullList = isfull;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification_inbox, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.usernameTV.setText(grpList.get(position).authorId.firstName+" "+grpList.get(position).authorId.lastName);
        holder.notificationTypeTV.setText(grpList.get(position).message);
        holder.notificationtimeTV.setText(grpList.get(position).timeAgo);
    }

    @Override
    public int getItemCount() {

        if(isFullList){
            return grpList.size();
        }else{
            if(grpList.size()>4) {
                return 4;
            }else{
                return grpList.size();
            }
        }

    }
}
