package com.netset.believeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netset.believeapp.Model.DayEntityModel;
import com.netset.believeapp.Model.TaskListModel;
import com.netset.believeapp.R;
import com.netset.believeapp.listeners.CalendarClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by netset on 24/11/17.
 */

public class CalendarCellRecyclerAdapter extends RecyclerView.Adapter<CalendarCellRecyclerAdapter.MyViewHolder> {

    Context context;
    int resource;
    private ArrayList<DayEntityModel> daysList;
    private int daysInMonth;

    boolean isClicked = false;
    int clickedItem = -1;
    private int month, year;
    String date;
    String mTime;
    String mCurrentDate;


    private Calendar _calendar;
    CalendarClickListener clickInterface;
    ArrayList<TaskListModel> taskList = new ArrayList<>();
    ArrayList<String> MyDateList = new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView gridcell, counterTv;
        RelativeLayout layCell;
        ImageView im_isevent;
        View dividerView;

        public MyViewHolder(View view) {
            super(view);
            gridcell = view.findViewById(R.id.calendar_tv);
            counterTv = view.findViewById(R.id.counter_tv);
            im_isevent = view.findViewById(R.id.isevent);
            dividerView = view.findViewById(R.id.lineView);
            layCell = view.findViewById(R.id.lay_cell);
        }
    }


    public CalendarCellRecyclerAdapter(Context context, int resource, int month,
                                       int year, CalendarClickListener clickInterface, ArrayList<TaskListModel> taskList, ArrayList<String> MyDateList) {
        super();
        this.context = context;
        this.resource = resource;
        daysList = new ArrayList<DayEntityModel>();
        printMonth(month, year);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:MM a");
        mTime = sdf.format(c.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        mCurrentDate = dateFormat.format(date);
        System.out.println(dateFormat.format(date));
        _calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        this.clickInterface = clickInterface;
        this.taskList = taskList;
        this.MyDateList = MyDateList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(resource, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public int getItemCount() {
        return daysList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        // Set the Day GridCell
        holder.gridcell.setText(daysList.get(position).day);
        int counter = 0;
        if (counter > 0) {
            holder.counterTv.setText("" + counter);
        } else {
            holder.counterTv.setText("");
        }

        holder.gridcell.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                clickedItem = position;
                doCellAction(position);
                notifyDataSetChanged();


            }

            private boolean checkDateIs(String mCurrentDate, String date) {
                boolean isDate = false;
                try {
                    // Create 2 dates starts
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = sdf.parse(sdf.format(new Date()));
                    Date date2 = sdf.parse(date);

                    System.out.println("Date1" + sdf.format(date1));
                    System.out.println("Date2" + sdf.format(date2));
                    System.out.println();
                    // Create 2 dates ends
                    // Date object is having 3 methods namely after,before and equals for comparing
                    // after() will return true if and only if date1 is after date 2
                    if (date1.after(date2)) {
                        System.out.println("Date1 is after Date2");
                        isDate = false;
                    } else
                        // before() will return true if and only if date1 is before date2
                        if (date1.before(date2)) {
                            System.out.println("Date1 is before Date2");
                            isDate = true;
                        } else
                            //equals() returns true if both the dates are equal
                            if (date1.equals(date2)) {
                                System.out.println("Date1 is equal Date2");
                                isDate = true;
                            }
                    System.out.println();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                return isDate;
            }
        });


        if (!daysList.get(position).isCurrentMonthDay) {
            holder.gridcell.setTextColor(context.getResources().getColor(R.color.light_pink));
        } else {
            holder.gridcell.setTextColor(context.getResources().getColor(R.color.blue));
        }
        //set Background on Current Date.
        if ((daysList.get(position).isCurrentDay && !isClicked)
                || (isClicked && (clickedItem == position))) {

            holder.gridcell.setTextColor(context.getResources().getColor(R.color.light_pink));
            holder.layCell.setBackgroundResource(R.drawable.ic_circle_back);
        } else if (daysList.get(position).isCurrentDay && isClicked) {
            holder.layCell.setBackgroundResource(0);
            holder.gridcell.setTextColor(context.getResources().getColor(R.color.white));
        } else if (!daysList.get(position).isCurrentMonthDay) {
            holder.layCell.setBackgroundResource(0);
            holder.gridcell.setTextColor(context.getResources().getColor(
                    android.R.color.transparent));
        } else {

            holder.layCell.setBackgroundResource(0);
            holder.gridcell.setTextColor(context.getResources().getColor(R.color.white));
        }


        if (MyDateList.contains(daysList.get(position).date.trim())) {
            if (daysList.get(position).isCurrentMonthDay) {
                holder.im_isevent.setVisibility(View.VISIBLE);
            } else {
                holder.im_isevent.setVisibility(View.GONE);
            }
        } else {
            holder.im_isevent.setVisibility(View.GONE);
        }
    }


    private int getNumberOfDaysOfMonth(int year, int month) {
        return totalMonthDays(year, month);
    }

    private DayEntityModel getDayEntity(int year, int month,
                                        int day,
                                        boolean isChecked,
                                        boolean isCurrentDay,
                                        boolean isCurrentMonthDay) {
        DayEntityModel entity = new DayEntityModel();
        String MM = "";
        String dd = "";
        if (month < 10) {
            MM = "0" + month;
        } else {
            MM = "" + month;
        }
        if (day < 10) {
            dd = "0" + day;
        } else {
            dd = "" + day;
        }
        entity.date = year + "-" + MM + "-" + dd;
        entity.isChecked = isChecked;
        entity.isCurrentMonthDay = isCurrentMonthDay;
        entity.isCurrentDay = isCurrentDay;
        entity.year = String.valueOf(year);
        entity.month = String.valueOf(month);
        entity.day = String.valueOf(day);
        return entity;
    }

    /**
     * Prints Month
     *
     * @param MM
     * @param yy
     */
    private void printMonth(int MM, int yy) {
        date = yy + "-" + MM + "-" + "01";
        int trailingSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;
        int currentMonth2 = MM - 1;
        int currentMonth = MM;
        daysInMonth = getNumberOfDaysOfMonth(yy, currentMonth2);
        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth2, 1);
        if (currentMonth2 == 11) {
            prevMonth = currentMonth2 - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(yy, prevMonth);
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy + 1;
        } else if (currentMonth2 == 0) {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(yy, prevMonth);
            nextMonth = 1;
        } else {
            prevMonth = currentMonth2 - 1;
            nextMonth = currentMonth2 + 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(yy, prevMonth);
        }
        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        trailingSpaces = currentWeekDay;
        // Trailing Month days
        for (int i = 0; i < trailingSpaces; i++) {
            DayEntityModel entity = getDayEntity(prevYear, prevMonth,
                    ((daysInPrevMonth - trailingSpaces + 1) + i), false,
                    false, false);
            daysList.add(entity);
        }
        // Current Month Days
        for (int i = 1; i <= daysInMonth; i++) {
            DayEntityModel entity = getDayEntity(yy, currentMonth, i, false,
                    false, true);
            if (getCurrentDate("yyyy-MM-dd").equalsIgnoreCase(entity.date)) {
                entity.isCurrentDay = true;
            }
            daysList.add(entity);
        }
        // Leading Month days
        for (int i = 0; i < daysList.size() % 7; i++) {
            DayEntityModel entity = getDayEntity(nextYear, nextMonth, i + 1,
                    false, false, false);
            daysList.add(entity);
        }
    }

    private void setEvent(ImageView view) {
        for (int i = 0; i < daysList.size(); i++) {

            Log.e("cal_date", "" + daysList.get(i).date.trim());
            Log.e("my dates", "" + MyDateList.size());
            if (MyDateList.contains(parseDateToddMMyyyy((daysList.get(i).date.trim())))) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }

    private boolean compareDates(Date calDate, Date taskDate) {
        try {
            if (calDate.before(taskDate)) {
                return false;  // If start date is before end date.
            } else if (calDate.equals(taskDate)) {
                return true;  // If two dates are equal.
            } else {
                return false; // If start date is after the end date.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Date getTaskDate(String dateString) {
        Date date = null;
        if (dateString != null && !dateString.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//
            try {
                date = format.parse(dateString);
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
                try {
                    date = format.parse("2016-10-30");
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return date;
    }

    private Date getCalendarDate(String dateString) {
        Date date = null;
        if (dateString != null && !dateString.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//
            try {
                date = format.parse(dateString);
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
                try {
                    date = format.parse("2016-10-30");
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return date;
    }

    @SuppressWarnings("unused")
    protected void doCellAction(int position) {
        try {
            isClicked = true;
            mCurrentDate = daysList.get(position).date;
            SimpleDateFormat mDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date mDate = null;
            try {
                mDate = mDateFormat.parse(mCurrentDate);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.e("current_date : ", "" + mCurrentDate);
            //Hit API and get response
            //Call Interfaces
            //clickInterface.getTimeSlot(PreferencesManager.getInstance().getValue(Constants.PreferencesKey.USER_ID), mCurrentDate);
            // mPresenter.getTimeSlotByUserId(PreferencesManager.getInstance().getValue(Constants.PreferencesKey.USER_ID), mCurrentDate);
            clickInterface.onCalDateClickListener(position, mCurrentDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getCurrentDate(String pattern) {
        String date = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            date = sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public int totalMonthDays(int year, int month) {
        int monthMaxDays = 30;
        try {
            Calendar c = new GregorianCalendar(year, month, 1);
            monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return monthMaxDays;
    }

    public interface OnDateClickInterface {
        void getTimeSlot(String userId, String selectedDate);
    }


    /**
     * Change Given Date To Other Format ...
     *
     * @param time
     * @return
     */
    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
