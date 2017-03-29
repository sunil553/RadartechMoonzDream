package com.radartech.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.radartech.sw.R;

import butterknife.OnClick;

public class BaseGoogleMapFragment extends BaseFragment implements OnMapReadyCallback {

    public GoogleMap mGoogleMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void loadMap() {
        if (this instanceof TrackFragment){

        }
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.map_monitor, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mGoogleMap == null) {
            mGoogleMap = googleMap;
            Object localObject = googleMap.getUiSettings();
            ((UiSettings) localObject).setZoomControlsEnabled(true);
            ((UiSettings) localObject).setRotateGesturesEnabled(false);
            ((UiSettings) localObject).setMapToolbarEnabled(true);
        }
    }


    @OnClick(R.id.iv_monitor_map_normal)
    public void onClickMapNormal(View v) {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @OnClick(R.id.iv_monitor_map_satellite)
    public void onClickMapSatellite(View v) {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }
}