package com.netset.believeapp.Fragment.communitySection;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.communityAdapters.SmallGroupAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.smallGroupSection.SmallGroupProfileFragment;
import com.netset.believeapp.Model.SmallGroupsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.GpsTracker;
import com.netset.believeapp.Utils.PreviewUtil;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.retrofitManager.ApiResponse;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * Created by netset on 18/1/18.
 */

public class SmallGroupsFragment extends BaseFragment implements OnMapReadyCallback, ApiResponse, SmallGroupAdapter.RefreshCallBack {

    @BindView(R.id.listView_Option_TV)
    TextView listViewOptionTV;
    @BindView(R.id.mapView_Option_TV)
    TextView mapViewOptionTV;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.search_ET)
    AppCompatEditText searchET;
    @BindView(R.id.searchView_Container)
    RelativeLayout searchViewContainer;
    @BindView(R.id.grpList_RV)
    RecyclerView grpListRV;
    @BindView(R.id.smallgp_parent)
    LinearLayout smallgpParent;
    @BindView(R.id.mapContainer)
    ConstraintLayout mapContainer;
    @BindView(R.id.txt_nodata)
    TextView txtNodata;
    @BindView(R.id.grpList_viewContainer)
    LinearLayout grpListViewContainer;
    Unbinder unbinder;
    GoogleMap googleMap;
    Handler handler;
    String  stringLatitude,stringLongitude;
    SmallGroupAdapter smallGroupAdapter;
    SmallGroupsModel result;
    SupportMapFragment mapFragment;
    Call<JsonObject>  smallGroupData,JoinGroup,searchGroup;
    String data;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.small_groups_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        smallgpParent.setBackgroundResource(0);

        handler = new Handler();

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                data = searchET.getText().toString().trim();

                if(data.equals("")){
                    CommonDialogs.hideSoftKeyboard(getActivity());
                    loadGrpList();
                }else{
                    CallSearchApi(searchET.getText().toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        loadGrpList();
        new Handler().postDelayed(() -> {

            try {
                GpsTracker gpsTracker = new GpsTracker(requireActivity());

                if (gpsTracker.getIsGPSTrackingEnabled())
                {
                    stringLatitude    = String.valueOf(gpsTracker.getLatitude());
                    stringLongitude   = String.valueOf(gpsTracker.getLongitude());

                    GeneralValues.set_current_lat(getActivity(),stringLatitude);
                    GeneralValues.set_current_long(getActivity(),stringLongitude);
                }
                else
                {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gpsTracker.showSettingsAlert();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, 2000);
        displayView(0);

    }

    @Override
    public void onStart() {
        super.onStart();

    }


    private void loadGrpList() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("latitude", GeneralValues.get_current_lat(getActivity()));
        map.put("longitude", GeneralValues.get_current_long(getActivity()));
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        smallGroupData = baseActivity.apiInterface.GetSmallGroups(map);
        baseActivity.apiHitAndHandle.makeApiCall(smallGroupData, this, baseActivity);
    }

    public void CallSearchApi(String data){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("latitude", stringLatitude);
        map.put("longitude", stringLongitude);
        map.put("small_group_name", data);
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        searchGroup = baseActivity.apiInterface.GetSmallGroupsSearch(map);
        baseActivity.apiHitAndHandle.makeApiCall(searchGroup, this,false, baseActivity);

    }


    @OnClick({R.id.listView_Option_TV, R.id.mapView_Option_TV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.listView_Option_TV:
                displayView(0);
                break;
            case R.id.mapView_Option_TV:
                displayView(1);
                break;
        }
    }

    private void displayView(int value) {
        switch (value) {
            case 0:
                loadGrpListView();
                break;
            case 1:
                loadMapView();
                break;
        }
    }

    private void loadGrpListView() {

        mapContainer.setVisibility(View.GONE);
        grpListViewContainer.setVisibility(View.VISIBLE);

        listViewOptionTV.setBackgroundResource(R.drawable.round_left_edge_shape);
        mapViewOptionTV.setBackgroundResource(0);

        listViewOptionTV.setTextColor(getResources().getColor(R.color.black));
        mapViewOptionTV.setTextColor(getResources().getColor(R.color.white));


    }

    private void loadMapView() {
        grpListViewContainer.setVisibility(View.GONE);
        mapContainer.setVisibility(View.VISIBLE);

        mapViewOptionTV.setBackgroundResource(R.drawable.round_right_edge_shape);
        listViewOptionTV.setBackgroundResource(0);

        mapViewOptionTV.setTextColor(getResources().getColor(R.color.black));
        listViewOptionTV.setTextColor(getResources().getColor(R.color.white));

        loadGrpList();
        loadMapViewData();
    }

    private void loadMapViewData() {
        if (mapFragment == null) {
            mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
        }
        assert mapFragment != null;
        mapFragment.getMapAsync(SmallGroupsFragment.this);

    }


    private Bitmap createStoreMarker(final Marker marker,String img) {
        //Modelmap modelmap=new Modelmap();
        final View markerLayout = getActivity().getLayoutInflater().inflate(R.layout.marker_layout, null);
        RelativeLayout rel_marker = (RelativeLayout) markerLayout.findViewById(R.id.rel_marker);
        CircleImageView userImg = (CircleImageView) markerLayout.findViewById(R.id.user_img);
        ImageView markerImage = (ImageView) markerLayout.findViewById(R.id.marker_img);


        Picasso.with(getActivity())
                .load(img)
                .placeholder(R.drawable.mapmarker)
                .error(R.drawable.mapmarker)
                .into(userImg, new Callback() {
                    @Override
                    public void onSuccess() {
                        markerLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                        markerLayout.layout(0, 0, markerLayout.getMeasuredWidth(), markerLayout.getMeasuredHeight());
                        final Bitmap bitmap = Bitmap.createBitmap(markerLayout.getMeasuredWidth(), markerLayout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        markerLayout.draw(canvas);
                        try {
                            marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });


        markerLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        markerLayout.layout(0, 0, markerLayout.getMeasuredWidth(), markerLayout.getMeasuredHeight());
        final Bitmap bitmap = Bitmap.createBitmap(markerLayout.getMeasuredWidth(), markerLayout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        markerLayout.draw(canvas);
        return bitmap;
    }


    protected Marker createMarker(int position, GoogleMap map, double latitude, double longitude, String img) {
        Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
        marker.setTag(position);
        marker.setAnchor(0.5f, 0.5f);

        marker.setIcon(BitmapDescriptorFactory.fromBitmap(createStoreMarker(marker,img)));

        //   String lat=SharedPrefrences.get_new_latitude(getActivity());
        //  String  lon=SharedPrefrences.get_new_longitude(getActivity());
        double iMeter = 50 * 1609.34;
        Circle circle_map = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(Double.parseDouble(""+latitude), Double.parseDouble(""+longitude)))
                .radius(iMeter) // Converting Miles into Meters...
                .strokeColor(Color.RED)
                .strokeWidth(5));
        circle_map.remove();
        float currentZoomLevel = getZoomLevel(circle_map);
        float animateZomm = currentZoomLevel + 5;

//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(animateZomm), 2000, null);
        return marker;
    }

    public float getZoomLevel(Circle circlew) {
        float zoomLevel=0;
        if (circlew != null){
            double radius = circlew.getRadius();
            double scale = radius / 500;
            zoomLevel =(int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel +.1f;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        this.googleMap = googleMap;
        new Thread(new Runnable() {
            public void run(){
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (int i = 0; i < result.getData().size(); i++) {
                                createMarker(i,googleMap,Double.parseDouble(result.getData().get(i).getVenueLatitude()),Double.parseDouble(result.getData().get(i).getVenueLongitude()), result.getData().get(i).getSmallGroupImage());


                                //  Marker mark = map.addMarker(new MarkerOptions().position(new LatLng(modelmaps.get(i).getLatitude(),modelmaps.get(i).getLongitude()))); //...
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        //          mainAsyncTask.cancelCommonDialog();
                    }
                });
            }
        }).start();
        if (!result.getData().isEmpty()){
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(result.getData().get(0).getVenueLatitude()), Double.parseDouble(result.getData().get(0).getVenueLongitude())), 10));

        }


        googleMap.setOnMarkerClickListener(marker -> {
            Object position=marker.getTag();
            marker.showInfoWindow();
            assert baseActivity != null;
            SmallGroupProfileFragment smallGroupProfileFragment = new SmallGroupProfileFragment();
            Bundle args = new Bundle();
            args.putString("smallgroupid", result.getData().get((Integer) position).getId());
            smallGroupProfileFragment.setArguments(args);
            baseActivity.navigateFragmentTransaction(R.id.homeContainer, smallGroupProfileFragment);
//                PreviewUtil.INSTANCE.addNotes(baseActivity);
            return false;
        });
       googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                Object position=marker.getTag();

                View v = getLayoutInflater().inflate(R.layout.small_group_info, null);

                TextView tLocation = (TextView) v.findViewById(R.id.locationTvMap);

                TextView distance = (TextView) v.findViewById(R.id.groupnameTvMap);
                TextView showMore = (TextView) v.findViewById(R.id.showMoreMap);

                tLocation.setText(result.getData().get((Integer) position).getSmallGroupName());
                distance.setText("Distance: "+result.getData().get((Integer) position).getMilesDistance());
                showMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SmallGroupProfileFragment smallGroupProfileFragment = new SmallGroupProfileFragment();
                        Bundle args = new Bundle();
                        args.putString("smallgroupid", result.getData().get((Integer) position).getId());
                        smallGroupProfileFragment.setArguments(args);
                        baseActivity.navigateFragmentTransaction(R.id.homeContainer, smallGroupProfileFragment);
                    }
                });
                return v;

            }
        });
    }



    @Override
    public void onSuccess(Call call, Object object) {


        try {
            if(baseActivity.apiHitAndHandle.pDialog.isShowing()){
                baseActivity.apiHitAndHandle.pDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        if(call == smallGroupData) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), SmallGroupsModel.class);
            smallGroupAdapter = new SmallGroupAdapter(baseActivity, result.getData(),baseActivity);
            try {
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
                mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                grpListRV.setLayoutManager(mLayoutManager);
                grpListRV.setItemAnimator(new DefaultItemAnimator());
                grpListRV.setAdapter(smallGroupAdapter);
                grpListRV.setNestedScrollingEnabled(false);
                smallGroupAdapter.setAdapterCallback(this);
                smallGroupAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(result.getData().size()==0){
                txtNodata.setVisibility(View.VISIBLE);
                grpListRV.setVisibility(View.GONE);
            }else{
                txtNodata.setVisibility(View.GONE);
                grpListRV.setVisibility(View.VISIBLE);
            }



        }
        else if(call == JoinGroup){
            CommonDialogs.customToast(getActivity(),result.getMessage());
            loadGrpList();
        }
        else if(call == searchGroup){

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), SmallGroupsModel.class);
            try {
                smallGroupAdapter = new SmallGroupAdapter(baseActivity, result.getData(),baseActivity);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
                grpListRV.setLayoutManager(mLayoutManager);
                grpListRV.setItemAnimator(new DefaultItemAnimator());
                grpListRV.setAdapter(smallGroupAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }

            grpListRV.setNestedScrollingEnabled(false);
            smallGroupAdapter.notifyDataSetChanged();

            if(result.getData().size()==0){
                txtNodata.setVisibility(View.VISIBLE);
                grpListRV.setVisibility(View.GONE);
            }else{
                txtNodata.setVisibility(View.GONE);
                grpListRV.setVisibility(View.VISIBLE);
            }


            grpListRV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, grpListRV, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    SmallGroupProfileFragment smallGroupProfileFragment = new SmallGroupProfileFragment();
                    Bundle args = new Bundle();
                    args.putString("smallgroupid", result.getData().get(position).getId());
                    smallGroupProfileFragment.setArguments(args);
                    baseActivity.navigateFragmentTransaction(R.id.homeContainer, smallGroupProfileFragment);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }


    @Override
    public void refreshlist() {
        loadGrpList();
    }
}
class MarkerHolder {
    public String startdate;
    public String enddate;
    public String fromplace;
    public String toplace;

    public MarkerHolder(String sd, String ed, String fp, String tp) {
        startdate = sd;
        enddate  = ed;
        fromplace = fp;
        toplace = tp;
    }
}