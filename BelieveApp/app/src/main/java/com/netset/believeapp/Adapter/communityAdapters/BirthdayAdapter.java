package com.netset.believeapp.Adapter.communityAdapters;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netset.believeapp.Fragment.birthdaySection.SelectedUserProfileFragment;
import com.netset.believeapp.GsonModel.BirthdayModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.activity.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by netset on 18/1/18.
 */

public class BirthdayAdapter extends RecyclerView.Adapter<BirthdayAdapter.MyViewHolder> {

    List<BirthdayModel.TodayBirthday> tdList;
    List<BirthdayModel.UpcomingBirthday> ucList;

    String WhichList,Extra;
    Context mContext;
    boolean showFullList;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView userImg_IV;
        TextView username_TV, usertext_TV;
        RelativeLayout mainLay;

        public MyViewHolder(View view) {
            super(view);
            username_TV = view.findViewById(R.id.userName_TV);
            userImg_IV = view.findViewById(R.id.userImg_IV);
            usertext_TV = view.findViewById(R.id.bdayInfo_TV);
            mainLay = view.findViewById(R.id.main_lay);
        }
    }

    public BirthdayAdapter(Context mContext, List<BirthdayModel.TodayBirthday> blogList, boolean showFullList,String whichlist) {
        this.mContext = mContext;
        this.tdList = blogList;
        this.WhichList = whichlist;
        this.showFullList = showFullList;
    }

    public BirthdayAdapter(Context mContext, List<BirthdayModel.UpcomingBirthday> blogList, boolean showFullList,String whichlist,String extra) {
        this.mContext = mContext;
        this.ucList = blogList;
        this.WhichList = whichlist;
        this.showFullList = showFullList;
        this.Extra = extra;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bday_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if(WhichList.equals("today")){


            holder.usertext_TV.setText(GetDate(tdList.get(position).dob));
            holder.username_TV.setText(tdList.get(position).firstName+" "+tdList.get(position).lastName);
            CommonDialogs.getDisplayImage(mContext,tdList.get(position).profileImage,holder.userImg_IV,"#d3d3d3");
        }else{

            holder.usertext_TV.setText(GetDate(ucList.get(position).dob));
            holder.username_TV.setText(ucList.get(position).firstName+" "+ucList.get(position).lastName);
            CommonDialogs.getDisplayImage(mContext,ucList.get(position).profileImage,holder.userImg_IV,"#d3d3d3");

        }


        holder.mainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(WhichList.equals("today")){
                    Bundle args = new Bundle();
                    args.putString("userId", tdList.get(position).id);
                    ((BaseActivity)mContext).navigateFragmentTransaction_ARG(R.id.homeContainer, new SelectedUserProfileFragment(),args);
                }else{
                    Bundle args = new Bundle();
                    args.putString("userId", ucList.get(position).id);
                    ((BaseActivity)mContext).navigateFragmentTransaction_ARG(R.id.homeContainer, new SelectedUserProfileFragment(),args);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        if (!showFullList) {
            if(WhichList.equals("today")) {
                if (tdList.size() <= 2) {
                    return tdList.size();
                } else {
                    return 2;
                }
            }else{
                if (ucList.size() <= 2) {
                    return ucList.size();
                } else {
                    return 2;
                }
            }
        } else {
            if(WhichList.equals("today")) {
                return tdList.size();
            }
            else{
                return ucList.size();
            }
        }
    }



    public String GetDate(String dob){
        String defaultTimezone = TimeZone.getDefault().getID();
        Date date = null;
        try {
            date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).parse(dob.replaceAll("Z$", "+0000"));
            Log.e("string","string: " + dob);
            Log.e("","defaultTimezone: " + defaultTimezone);
            Log.e("date is","date: " + (new SimpleDateFormat("yyyy-MM-dd")).format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ""+new SimpleDateFormat("yyyy-MM-dd").format(date);
    }


}
