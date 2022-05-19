package com.netset.believeapp.Fragment.appointmentSection;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_MAKE_APPOINTMENT;

/**
 * Created by netset on 29/1/18.
 */

public class SendAppointmentRequestFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.label_title_TV)
    TextView labelTitleTV;
    @BindView(R.id.title_ET)
    EditText titleET;
    @BindView(R.id.label_when_TV)
    TextView labelWhenTV;
    @BindView(R.id.date_ET)
    TextView dateET;
    @BindView(R.id.startTime_ET)
    TextView startTimeET;
    @BindView(R.id.endTime_ET)
    TextView endTimeET;
    @BindView(R.id.sendRqst_BTN)
    Button sendRqstBTN;
    Unbinder unbinder;
    int mHour, mMinute;
    public static String startTime, endTime;
    String selectedDate = "";
    Call<JsonObject> AddApointment;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    @BindView(R.id.main)
    LinearLayout mainLay;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.send_appointment_request_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_MAKE_APPOINTMENT, false, false, false, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleET.setFilters(new InputFilter[]{EMOJI_FILTER});
        mainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    CommonDialogs.hideSoftKeyboard(getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    @OnClick({R.id.date_ET, R.id.startTime_ET, R.id.endTime_ET, R.id.sendRqst_BTN})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_ET:
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity(), R.style.datepicker,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                int temp_month = monthOfYear + 1;
                                int temp_year = year;


                                dateET.setText(year + "-" + temp(String.valueOf(monthOfYear + 1)) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


                datePickerDialog.show();

                // baseActivity.datePicker(dateET, null, false);
                break;
            case R.id.startTime_ET:

                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Date strDate =null;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            strDate = sdf.parse(dateET.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        Date now = new Date(System.currentTimeMillis());
                        Date nowdate = null;
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        //sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm");
                        String formattedDate = df.format(now);
                        try {
                            nowdate = df.parse(formattedDate);
                        }catch (ParseException e){
                            e.printStackTrace();
                        }


                            String AM_PM;
                            if (hourOfDay > 12) {
                                hourOfDay -= 12;
                                AM_PM = "PM";
                            } else if (hourOfDay == 0) {
                                hourOfDay += 12;
                                AM_PM = "AM";
                            } else if (hourOfDay == 12) {
                                AM_PM = "PM";
                            } else {
                                AM_PM = "AM";
                            }


                            startTimeET.setText(convertDate(hourOfDay) + ":" + convertDate(minute) + " " + AM_PM);
                            startTime = convertDate(hourOfDay) + ":" + convertDate(minute) + " " + AM_PM;
                      //  }
                    }
                }, mHour, mMinute, false);

                timePickerDialog.show();

                break;
            case R.id.endTime_ET:
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String AM_PM;
                        if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            AM_PM = "PM";
                        } else if (hourOfDay == 0) {
                            hourOfDay += 12;
                            AM_PM = "AM";
                        } else if (hourOfDay == 12) {
                            AM_PM = "PM";
                        } else {
                            AM_PM = "AM";
                        }


                        endTime = convertDate(hourOfDay) + ":" + convertDate(minute) + " " + AM_PM;
                        endTimeET.setText(convertDate(hourOfDay) + ":" + convertDate(minute) + " " + AM_PM);

                    }
                }, mHour, mMinute, false);

                timePickerDialog.show();

                break;
            case R.id.sendRqst_BTN:


                String formattedDate = null;
                DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat targetFormat = new SimpleDateFormat("EEEE");
                Date date = null;
                try {
                    date = originalFormat.parse(dateET.getText().toString());
                    formattedDate = targetFormat.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (titleET.getText().toString().trim().equals("")) {
                    CommonDialogs.customToast(getActivity(), "Please Enter Title");
                } else if (dateET.getText().toString().trim().equals("")) {
                    CommonDialogs.customToast(getActivity(), "Please select date for appointment");
                } else if (startTime.equals("")) {
                    CommonDialogs.customToast(getActivity(), "Please select time from");
                } else if (endTime.equals("")) {
                    CommonDialogs.customToast(getActivity(), "Please select time to");
                }/* else if (!isValidTitle(titleET.getText().toString().trim())) {
                    CommonDialogs.customToast(getActivity(), "Please add at least one alphabet in the title");
                } */else {


                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("title", titleET.getText().toString().trim());
                    map.put("date_of_appointment", dateET.getText().toString().trim());
                    map.put("day_of_appointment", formattedDate);
                    map.put("time_from", startTime);
                    map.put("time_to", endTime);
                    map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                    AddApointment = baseActivity.apiInterface.MakeAppointment_Request(map);
                    baseActivity.apiHitAndHandle.makeApiCall(AddApointment, this, baseActivity);
                }

                break;
        }
    }


    public boolean isValidTitle(String title) {
        String TITLE_PATTERN = "^(?=.*\\d)(?=.*[a-zA-Z])$";
        Pattern pattern = Pattern.compile(TITLE_PATTERN);
        Matcher matcher = pattern.matcher(title);
        return matcher.matches();
    }

    public String convertDate(int input) {
        if (input >= 10) {
            Log.e(">>>>>>>>>>>>value", ">>>>>>>>>" + input);
            return "" + input;
        } else {
            return "0" + String.valueOf(input);
        }

    }

    private String temp(String month) {
        String t = "";
        if (Integer.parseInt(month) < 9) {
            t = "0" + month;
        } else {
            t = month;
        }
        return t;
    }


    @Override
    public void onSuccess(Call call, Object object) {

        try {
            JSONObject jsonObject = new JSONObject(object.toString());
            String message = jsonObject.getString("message");
            CommonDialogs.customToast(getActivity(), message);
            titleET.setText("");
            dateET.setText("");
            startTimeET.setText("");
            endTimeET.setText("");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }


    public static InputFilter EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
                char c = source.charAt(index);
                if (isCharAllowed(c))
                    sb.append(c);
                else
                    keepOriginal = false;
            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };

    private static boolean isCharAllowed(char c) {
        return Character.isLetterOrDigit(c) || Character.isSpaceChar(c);
    }
}
