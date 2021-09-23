package com.netset.believeapp.Fragment.homeMenu;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.groupSection.GroupProfilePageFragment;
import com.netset.believeapp.GsonModel.EventDetailModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_EVENT_DETAILS;

/**
 * Created by netset on 12/3/18.
 */

public class EventDetailFragment extends BaseFragment implements OnMapReadyCallback, ApiResponse {


    @BindView(R.id.txtTime)
    TextView txtTime;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtPrice)
    TextView txtPrice;
    @BindView(R.id.txtCategory)
    TextView txtCategory;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    @BindView(R.id.txtEventName)
    TextView txtEventName;
    @BindView(R.id.headerImage)
    ImageView headerImage;
    @BindView(R.id.groupGrid_GV)
    RecyclerView GroupGridView;
    @BindView(R.id.txtlocation)
    TextView TxtLoc;

    Unbinder unbinder;
    String EventId;
    EventDetailModel result;
    /*ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> EventDetail;
    SupportMapFragment mapFragment;
    String Lat, Lng;
    GoogleMap googleMap;
    List<EventDetailModel.GroupId> groupList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.event_details, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_EVENT_DETAILS, true, false, false, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/
        CallApi();

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void CallApi() {
        Bundle b = getArguments();
        if (b != null) {
            this.EventId = b.getString("eventid");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("event_id", EventId);
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            EventDetail = baseActivity.apiInterface.GetEvents_Detail(map);
            baseActivity.apiHitAndHandle.makeApiCall(EventDetail, this);
        }
    }


    private void loadMapViewData() {
        if (mapFragment == null) {
            mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
        }
        mapFragment.getMapAsync(EventDetailFragment.this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        new Thread(new Runnable() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            createMarker(googleMap, Double.parseDouble(result.getData().getVenueLatitude()), Double.parseDouble(result.getData().getVenueLongitude()));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }).start();

    }


    protected void createMarker(GoogleMap map, double latitude, double longitude) {
        googleMap = map;
        double iMeter = 50 * 1609.34;
        Circle circle_map = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(latitude, longitude))
                .radius(iMeter) // Converting Miles into Meters...
                .strokeColor(Color.RED)
                .strokeWidth(5));
        circle_map.remove();
        float currentZoomLevel = getZoomLevel(circle_map);
        float animateZomm = currentZoomLevel + 5;
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(currentZoomLevel), 2000, null);
    }



    public float getZoomLevel(Circle circlew) {
        float zoomLevel = 0;
        if (circlew != null) {
            double radius = circlew.getRadius();
            double scale = radius / 500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel + .5f;
    }



    @Override
    public void onSuccess(Call call, Object object) {

        Log.e("Response body", ">>>>>>>>>" + object.toString());


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), EventDetailModel.class);

        CommonDialogs.getSquareImage(getActivity(), result.getData().getEventCover(), headerImage);
        txtEventName.setText(result.getData().getTitle());
        txtDescription.setText(result.getData().getDescription());
        txtCategory.setText(result.getData().getEventCategory().getName());
        if(result.getData().getPrice().equals("")){
            txtPrice.setText("Free");
        }else{
            txtPrice.setText(result.getData().getPrice());
        }



        String string = result.getData().getDate();
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


        txtDate.setText(""+(new SimpleDateFormat("yyyy-MM-dd")).format(date));

        txtTime.setText(result.getData().getStartTime() + " - " + result.getData().getEndTime());
        TxtLoc.setText(result.getData().getVenue());
        Lat = result.getData().getVenueLatitude();
        Lng = result.getData().getVenueLongitude();
        groupList = result.getData().getGroupId();
        MemberAdapter adapter = new MemberAdapter(getActivity(), groupList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity, LinearLayoutManager.HORIZONTAL, false);
        GroupGridView.setLayoutManager(mLayoutManager);
        GroupGridView.setAdapter(adapter);

        GroupGridView.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, GroupGridView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        GroupProfilePageFragment groupProfilePageFragment = new GroupProfilePageFragment();
                        Bundle args = new Bundle();
                        args.putString("groupid", groupList.get(position).getId());
                        groupProfilePageFragment.setArguments(args);
                        baseActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.homeContainer, groupProfilePageFragment, "")
                                .addToBackStack("group")
                                .commit();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

        loadMapViewData();

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }

    private Date getDate(String time) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();//get your local time zone.
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        sdf.setTimeZone(tz);//set time zone.

     String time1 = ""+ DateUtils.getRelativeTimeSpanString(Long.parseLong(time) * 1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);

        String localTime = sdf.format(time1);
        Date date = new Date();
        try {
            date = sdf.parse(localTime);//get local date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }




    public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyViewHolder> {
        List<EventDetailModel.GroupId> blogListAdapter;
        Context mContext;


        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView memberImgIV;

            public MyViewHolder(View view) {
                super(view);
                memberImgIV = view.findViewById(R.id.memberImg_IV);

            }
        }

        public MemberAdapter(Context context, List<EventDetailModel.GroupId> blogList) {
            this.mContext = context;
            this.blogListAdapter = blogList;
        }

        @Override
        public MemberAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = null;

                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_member_view, parent, false);

            return new MemberAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MemberAdapter.MyViewHolder holder, final int position) {

            CommonDialogs.getDisplayImage(mContext, blogListAdapter.get(position).getGroupImage(), holder.memberImgIV);

        }

        @Override
        public int getItemCount() {

                return blogListAdapter.size();

        }
    }


    /*public class MemberAdapter extends BaseAdapter {
        MemberAdapter.ViewHolder viewHolder;
        Context context;
        List<EventDetailModel.GroupId> blogListAdapter = new ArrayList<>();

        public MemberAdapter(Context context, List<EventDetailModel.GroupId> blogList) {
            this.context = context;
            this.blogListAdapter = blogList;
        }

        @Override
        public int getCount() {
            return blogListAdapter.size();
        }

        @Override
        public Object getItem(int position) {
            return blogListAdapter.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {

                convertView = LayoutInflater.from(context).
                        inflate(R.layout.item_member_view, parent, false);
                viewHolder = new MemberAdapter.ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MemberAdapter.ViewHolder) convertView.getTag();
            }
            CommonDialogs.getDisplayImage(context, blogListAdapter.get(position).getGroupImage(), viewHolder.memberImgIV);


            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.memberImg_IV)
            ImageView memberImgIV;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }*/
    }

