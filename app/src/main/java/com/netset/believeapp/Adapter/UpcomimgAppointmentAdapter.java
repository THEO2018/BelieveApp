package com.netset.believeapp.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netset.believeapp.GsonModel.UpcomingApoimtmentModel;
import com.netset.believeapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by netset on 29/1/18.
 */

public class UpcomimgAppointmentAdapter extends RecyclerView.Adapter<UpcomimgAppointmentAdapter.MyViewHolder> {
    List<UpcomingApoimtmentModel.Datum> apnt_List;
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

    public UpcomimgAppointmentAdapter(Context mContext, List<UpcomingApoimtmentModel.Datum> apnt_List) {
        this.mContext = mContext;
        this.apnt_List = apnt_List;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_up_appointment_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

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
