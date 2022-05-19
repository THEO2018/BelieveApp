package com.netset.believeapp.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netset.believeapp.CommonConst;
import com.netset.believeapp.GsonModel.PrayerModel;
import com.netset.believeapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by netset on 29/1/18.
 */

public class PrayerRqstAdapter extends RecyclerView.Adapter<PrayerRqstAdapter.MyViewHolder> {
    List<PrayerModel.Datum> blogList;
    Context mContext;
    boolean showFullList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView prayTitleTV;
        private TextView timeTV;
        private TextView likeTV;
        private TextView healthTV;
        private TextView locationTV;
        private TextView descTV;

        public MyViewHolder(View view) {
            super(view);
            prayTitleTV = (TextView) view.findViewById(R.id.prayTitle_TV);
            timeTV = (TextView) view.findViewById(R.id.time_TV);
            likeTV = (TextView) view.findViewById(R.id.like_TV);
            healthTV = (TextView) view.findViewById(R.id.health_TV);
           // locationTV = (TextView) view.findViewById(R.id.location_TV);
            descTV = (TextView) view.findViewById(R.id.desc_TV);
        }
    }

    public PrayerRqstAdapter(Context mContext, List<PrayerModel.Datum> blogList, boolean showFullList) {
        this.mContext = mContext;
        this.blogList = blogList;
        this.showFullList = showFullList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prayer_rqst, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.prayTitleTV.setText(blogList.get(position).title);
        holder.descTV.setText(blogList.get(position).message);
        holder.likeTV.setText(""+blogList.get(position).prayesCount);
        holder.healthTV.setText(blogList.get(position).category.name);

     /*   SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm a", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(blogList.get(position).createdDate+" "+blogList.get(position).createdTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(date);
*/
        String dateConverted= CommonConst.Companion.utcToLocalDate(blogList.get(position).createdDate+" "+blogList.get(position).createdTime);
        holder.timeTV.setText(dateConverted);
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }
}
