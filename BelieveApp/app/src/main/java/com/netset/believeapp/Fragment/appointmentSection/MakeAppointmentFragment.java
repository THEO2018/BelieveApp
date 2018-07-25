package com.netset.believeapp.Fragment.appointmentSection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.CalendarCellRecyclerAdapter;
import com.netset.believeapp.Adapter.TodaysAppointmentAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.MyAppointmentsModel;
import com.netset.believeapp.Model.TaskListModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.SimpleDividerItemDecorationBlue;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.listeners.CalendarClickListener;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_MAKE_APPOINTMENT;

/**
 * Created by netset on 16/1/18.
 */

public class MakeAppointmentFragment extends BaseFragment implements CalendarClickListener, ApiResponse {

    @BindView(R.id.calendar_left_arrow)
    ImageView calendarLeftArrow;
    @BindView(R.id.calendar_month_year_tV)
    AppCompatTextView calendarMonthYearTV;
    @BindView(R.id.calendar_right_arrow)
    ImageView calendarRightArrow;
    @BindView(R.id.calendar_title_view)
    RelativeLayout calendarTitleView;
    @BindView(R.id.sun_day_tv)
    AppCompatTextView sunDayTv;
    @BindView(R.id.mon_day_tv)
    AppCompatTextView monDayTv;
    @BindView(R.id.tue_day_tv)
    AppCompatTextView tueDayTv;
    @BindView(R.id.wed_day_tv)
    AppCompatTextView wedDayTv;
    @BindView(R.id.thu_day_tv)
    AppCompatTextView thuDayTv;
    @BindView(R.id.fri_day_tv)
    AppCompatTextView friDayTv;
    @BindView(R.id.sat_day_tv)
    AppCompatTextView satDayTv;
    @BindView(R.id.daysLL)
    LinearLayout daysLL;
    @BindView(R.id.weekday_RV)
    RecyclerView weekdayRV;
    @BindView(R.id.linearLayout4)
    LinearLayout linearLayout4;
    @BindView(R.id.view_no_task)
    LinearLayout viewNoTask;
    @BindView(R.id.currenttask_listView)
    RecyclerView currenttaskListView;
    @BindView(R.id.parent_homeView)
    RelativeLayout parentHomeView;
    Unbinder unbinder;

    Call<JsonObject> GetAppointment;

    CalendarCellRecyclerAdapter calenderAdapter;

    private String mTime;
    private String mCurrentDate;
    private Calendar _calendar;
    private int month;
    private int year;

    int temp_month = 0, temp_year = 0;
    ArrayList<String> dateList = new ArrayList<>();
    ArrayList<TaskListModel> taskList = new ArrayList<>();
    MyAppointmentsModel result;
    TodaysAppointmentAdapter makeAppointmentAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_layout, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_MAKE_APPOINTMENT, false, false, false, null);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calenderAdapter = new CalendarCellRecyclerAdapter(baseActivity, R.layout.date_cell, month, year, this, taskList, dateList);
    }
    public void CallApi(){

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("date", formattedDate);
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetAppointment = baseActivity.apiInterface.GetToday_Appointments(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetAppointment, this,true);
    }
    @Override
    public void onResume() {
        super.onResume();
        CallApi();
        setCalendarDates();
    }

    /**
     * Call for previous month ...
     */
    private void previousMonthCalender() {

        if (month <= 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        setGridCellAdapterToDate(month, year);
    }

    /**
     * Call for Next Month ...
     */
    private void nextMonthCalender() {

        if (month > 11) {
            month = 1;
            year++;
        } else {
            month++;
        }
        setGridCellAdapterToDate(month, year);
    }


    /**
     * Setting Dates to Calendar ...
     */
    public void setCalendarDates() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        mTime = sdf.format(c.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        mCurrentDate = dateFormat.format(date);
        System.out.println(dateFormat.format(date));
        _calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);

        /*Call to Method ...*/
        temp_month = month;
        temp_year = year;
        setGridCellAdapterToDate(month, year);
    }

    /**
     * Set Dates to Grid ...
     *
     * @param month
     * @param year
     */
    public void setGridCellAdapterToDate(int month, int year) {
        calenderAdapter = new CalendarCellRecyclerAdapter(baseActivity, R.layout.date_cell, month, year, this, taskList, dateList);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        calendarMonthYearTV.setText(DateFormat.format("MMMM yyyy", _calendar.getTime()));

        weekdayRV.setLayoutManager(new GridLayoutManager(baseActivity, 7));
        weekdayRV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
        weekdayRV.setItemAnimator(new DefaultItemAnimator());
        weekdayRV.setAdapter(calenderAdapter);
    }


    @Override
    public void onCalDateClickListener(int position, String date) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("date", date);
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetAppointment = baseActivity.apiInterface.GetToday_Appointments(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetAppointment, this);

    }

    @OnClick({R.id.calendar_left_arrow, R.id.calendar_right_arrow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calendar_left_arrow:
                previousMonthCalender();
                break;
            case R.id.calendar_right_arrow:
                nextMonthCalender();
                break;
        }
    }

    @Override
    public void onSuccess(Call call, Object object) {

        Log.e("Response body",">>>>>>>>>"+object.toString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), MyAppointmentsModel.class);

        makeAppointmentAdapter = new TodaysAppointmentAdapter(baseActivity,result.getData().getTodayAppointments());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
        currenttaskListView.setLayoutManager(mLayoutManager);
        currenttaskListView.setItemAnimator(new DefaultItemAnimator());
        currenttaskListView.setAdapter(makeAppointmentAdapter);

         if(result.getData().getTodayAppointments().size() ==0){
             viewNoTask.setVisibility(View.GONE);
         }else{
             viewNoTask.setVisibility(View.VISIBLE);
         }

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        for(int i = 0;i< result.getData().getUpcomingAppointments().size();i++){
             if(result.getData().getTodayAppointments().size() != 0){

            for(int j=0;j<result.getData().getTodayAppointments().size();j++) {
                //  String todaydate = SelectedDate(result.getData().getTodayAppointments().get(i).getDateOfAppointment());
                if (SelectedDate(result.getData().getTodayAppointments().get(j).getDateOfAppointment()).equals(formattedDate)) {

                    dateList.add(formattedDate);
                }
            }
             }
            String string = SelectedDate(result.getData().getUpcomingAppointments().get(i).getDateOfAppointment());
             dateList.add(string);
        }
        calenderAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }


    public String SelectedDate(String mydate){
        String string = mydate;
        String defaultTimezone = TimeZone.getDefault().getID();
        Date date = null;
        try {
            date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).parse(string.replaceAll("Z$", "+0000"));
            Log.e("string","string: " + string);
            Log.e("","defaultTimezone: " + defaultTimezone);
            Log.e("date is","date: " + (new SimpleDateFormat("yyyy-MM-dd")).format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return ""+(new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }
}
