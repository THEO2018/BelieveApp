package com.netset.believeapp.Fragment.homeMenu;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.appointmentSection.MakeAppointmentFragment;
import com.netset.believeapp.Fragment.appointmentSection.SendAppointmentRequestFragment;
import com.netset.believeapp.Fragment.appointmentSection.UpcomingAppointmentFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.activity.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.netset.believeapp.Utils.Constants.SC_MAKE_APPOINTMENT;

/**
 * Created by netset on 29/1/18.
 */

public class AppointmentFragment extends BaseFragment {

    @BindView(R.id.apnt_Container)
    FrameLayout apnt_Container;
    Unbinder unbinder;
    @BindView(R.id.calendar_IV)
    ImageView calendarIV;
    @BindView(R.id.calendar_TV)
    TextView calendarTV;
    @BindView(R.id.calendar_View)
    LinearLayout calendarView;
    @BindView(R.id.upcoming_IV)
    ImageView upcomingIV;
    @BindView(R.id.upcoming_TV)
    TextView upcomingTV;
    @BindView(R.id.upcoming_View)
    LinearLayout upcomingView;
    @BindView(R.id.sendRqst_IV)
    ImageView sendRqstIV;
    @BindView(R.id.sendRqst_TV)
    TextView sendRqstTV;
    @BindView(R.id.sendRqst_View)
    LinearLayout sendRqstView;
    @BindView(R.id.tabContainer)
    LinearLayout tabContainer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.appointment_fragment, null);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_MAKE_APPOINTMENT, false, false, false, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayView(0);
    }

    @OnClick({R.id.calendar_View, R.id.upcoming_View, R.id.sendRqst_View})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calendar_View:
                displayView(0);
                break;
            case R.id.upcoming_View:
                displayView(1);
                break;
            case R.id.sendRqst_View:
                displayView(2);
                break;
        }
    }

    private void displayView(int position) {
        animateView(position);
    }

    private void animateView(int position) {
        switch (position) {
            case 0:
                calendarIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                upcomingIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
                sendRqstIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
                calendarTV.setTextColor(getResources().getColor(R.color.white));
                upcomingTV.setTextColor(getResources().getColor(R.color.light_grey));
                sendRqstTV.setTextColor(getResources().getColor(R.color.light_grey));
                break;

            case 1:
                calendarIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
                upcomingIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                sendRqstIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
                calendarTV.setTextColor(getResources().getColor(R.color.light_grey));
                upcomingTV.setTextColor(getResources().getColor(R.color.white));
                sendRqstTV.setTextColor(getResources().getColor(R.color.light_grey));
                break;

            case 2:
                calendarIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
                upcomingIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
                sendRqstIV.setColorFilter(ContextCompat.getColor(baseActivity, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                calendarTV.setTextColor(getResources().getColor(R.color.light_grey));
                upcomingTV.setTextColor(getResources().getColor(R.color.light_grey));
                sendRqstTV.setTextColor(getResources().getColor(R.color.white));
                break;
        }
        addView(position);
    }

    private void addView(int position) {
        switch (position) {
            case 0:
                baseActivity.navigateFragment_NoBackStack(R.id.apnt_Container, new MakeAppointmentFragment());
                break;

            case 1:
                baseActivity.navigateFragment_NoBackStack(R.id.apnt_Container, new UpcomingAppointmentFragment());
                break;

            case 2:
                baseActivity.navigateFragment_NoBackStack(R.id.apnt_Container, new SendAppointmentRequestFragment());
                break;
        }
    }
}
