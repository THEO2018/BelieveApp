package com.netset.believeapp.Fragment.homeMenu;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.activity.HomeActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by netset on 9/5/18.
 */

public class MapFragment extends BaseFragment implements OnMapReadyCallback {

    Unbinder unbinder;
    SupportMapFragment mapFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.group_members_map, null);
        ((HomeActivity) getActivity()).setToolbarTitle("Map", true, false, false, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadMapViewData();
    }


    private void loadMapViewData() {
        if (mapFragment == null) {
            mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
        }
        mapFragment.getMapAsync(MapFragment.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Bundle b = getArguments();

        String lat = b.getString("lat");
        String lng = b.getString("lng");
        String title = b.getString("location");


        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Float.parseFloat(lat), Float.parseFloat(lng)))
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Float.parseFloat(lat), Float.parseFloat(lng)), 10));
    }
}
