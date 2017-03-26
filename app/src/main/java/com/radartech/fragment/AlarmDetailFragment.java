package com.radartech.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.radartech.model.DeviceInfoModel;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.LocationAddress;
import com.radartech.util.Utility;

import butterknife.BindView;


/*
 * This Class Is Used For write Journals and stored in local Db
 *
 * @author Eflair, Inc.
 */

public class AlarmDetailFragment extends BaseGoogleMapFragment {

    @BindView(R.id.tv_address)
    TextView mLocationTv;

    private DeviceInfoModel deviceDetails = null;
    int greenVehicle;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_detail, container, false);
        initButterKnife(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        deviceDetails = new DeviceInfoModel();

        Bundle extras = getArguments();


        deviceDetails.setDeviceId(extras.getInt(IntentConstants.DEVICE_ID));
        deviceDetails.setDeviceName(extras.getString(AppConstants.IntentConstants.DEVICE_NAME));
        deviceDetails.setLatitude(Double.parseDouble(extras.getString(AppConstants.IntentConstants.LATITUDE)));
        deviceDetails.setLongitude(Double.parseDouble(extras.getString(AppConstants.IntentConstants.LONGITUDE)));
        deviceDetails.setDeviceId(extras.getInt(AppConstants.IntentConstants.DEVICE_ID));
        deviceDetails.setAlarmType(extras.getInt(AppConstants.IntentConstants.ALARM_TYPE));
        deviceDetails.setAlarmTime(extras.getLong(AppConstants.IntentConstants.ALARM_TIME));
        deviceDetails.setGpsTime(extras.getLong(AppConstants.IntentConstants.GPS_TIME));

        int[] deviceStatusImages = Utility.getTrackingImage(String.valueOf(deviceDetails.getDeviceId()));
        greenVehicle = deviceStatusImages[1];

        //fetching adres from lat,lng
        LocationAddress locationAddress = new LocationAddress();
        locationAddress.getAddressFromLocation(deviceDetails.getLatitude(), deviceDetails.getLongitude(),
                getActivity(), new GeocoderHandler());

        loadMap();
    }

    private void loadPoints(GoogleMap googleMap) {
        LatLng latlong = new LatLng(deviceDetails.getLatitude(), deviceDetails.getLongitude());



        googleMap.addMarker(new MarkerOptions().rotation(80).position(latlong).icon(BitmapDescriptorFactory.fromResource(greenVehicle)));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latlong).zoom(DEFAULT_GOOGLE_MAP_ZOOM_LEVEL).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        mGoogleMap.setInfoWindowAdapter(infoWindowAdapter);
        loadPoints(mGoogleMap);
    }

    /**
     * for fetching address from location
     */
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    deviceDetails.setDeviceLocationAddress(bundle.getString(AppConstants.IntentConstants.LAT_LNG_ADDRESS));
                    mLocationTv.setText(deviceDetails.getDeviceLocationAddress());
                    break;
                default:
            }
        }
    }


    private GoogleMap.InfoWindowAdapter infoWindowAdapter = new GoogleMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_marker_info, null);
            TextView info_title = (TextView) view.findViewById(R.id.info_title);
            TextView info_description = (TextView) view.findViewById(R.id.info_description);

            info_title.setText(getString(R.string.info_target_name, deviceDetails.getDeviceName()));
            String geofenceType = "" ;
            int apiAlarmType = deviceDetails.getAlarmType();
            if (apiAlarmType == AppConstants.AlarmConstants.ALARM_GEO_FENCE_IN) {
                geofenceType = getString(R.string.info_alarm_type, getString(R.string.alarm_geo_fence_in));
            } else if (apiAlarmType == AppConstants.AlarmConstants.ALARM_GEO_FENCE_OUT_ALARM) {
                geofenceType = getString(R.string.info_alarm_type, getString(R.string.alarm_geo_fence_out));
            }
            StringBuilder stringBuilder = new StringBuilder(geofenceType);
            stringBuilder.append(System.getProperty("line.separator"));
            stringBuilder.append(getString(R.string.info_gps_time, Utility.getDateFromLong(deviceDetails.getGpsTime(), AppConstants.YYYY_MM_DD_HH_MM_SS_SLASH)));
            stringBuilder.append(System.getProperty("line.separator"));
            stringBuilder.append(getString(R.string.info_alarm_time, Utility.getDateFromLong(deviceDetails.getAlarmTime(), AppConstants.YYYY_MM_DD_HH_MM_SS_SLASH)));
            info_description.setText(stringBuilder.toString());
            return view;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

    };
}
