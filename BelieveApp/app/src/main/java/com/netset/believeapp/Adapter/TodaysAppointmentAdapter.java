package com.netset.believeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.MyAppointmentsModel;
import com.netset.believeapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by netset on 28/4/18.
 */

public class TodaysAppointmentAdapter extends RecyclerView.Adapter<TodaysAppointmentAdapter.MyViewHolder> {
    List<MyAppointmentsModel.TodayAppointment> apnt_List;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView date_TV, StartTime_TV, EndTime_TV,ApntTitle_TV;

        public MyViewHolder(View view) {
            super(view);
            date_TV = view.findViewById(R.id.apnt_Date_TV);
            StartTime_TV = view.findViewById(R.id.startTime_TV);
            EndTime_TV = view.findViewById(R.id.endTime_TV);
            ApntTitle_TV = view.findViewById(R.id.apntTitle_TV);
        }
    }

    public TodaysAppointmentAdapter(Context mContext, List<MyAppointmentsModel.TodayAppointment> apnt_List) {
        this.mContext = mContext;
        this.apnt_List = apnt_List;
    }

    @Override
    public TodaysAppointmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_up_appointment_view, parent, false);

        return new TodaysAppointmentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TodaysAppointmentAdapter.MyViewHolder holder, final int position) {

        holder.ApntTitle_TV.setText(apnt_List.get(position).getTitle());
        holder.StartTime_TV.setText(apnt_List.get(position).getTimeFrom());
        holder.EndTime_TV.setText(apnt_List.get(position).getTimeTo());
        holder.date_TV.setText(apnt_List.get(position).getDayOfAppointment()+" "+SelectedDate(apnt_List.get(position).getDateOfAppointment()));

    }

    @Override
    public int getItemCount() {
        return apnt_List.size();
    }


    public String SelectedDate(String mydate){
        String string = mydate;
        String defaultTimezone = TimeZone.getDefault().getID();
        Date date = null;
        try {
            date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).parse(string.replaceAll("Z$", "+0000"));
            Log.e("string","string: " + string);
            Log.e("","defaultTimezone: " + defaultTimezone);
            Log.e("date is","date: " + (new SimpleDateFormat("dd MMMM")).format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return ""+(new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }
}
