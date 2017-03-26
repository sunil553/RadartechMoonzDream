/*
 *
 *                  Moonzdream Confidential and Proprietary
 *
 *    This work contains valuable confidential and proprietary information.
 *    Disclosure, use or reproduction outside of Moonzdream, Inc. is prohibited
 *    except as authorized in writing. This unpublished work is protected by
 *    the laws of the United States and other countries. If publication occurs,
 *    following notice shall apply:
 *
 *                        Copyright 2016, Moonzdream Inc.
 *                            All rights reserved.
 *                   Freedom of Information Act(5 USC 522) and
 *            Disclosure of Confidential Information Generaly(18 USC 1905)
 *
 *    This material is being furnished in confidence by Moonzdream, Inc. The
 *    information disclosed here falls within Exemption (b)(4) of 5 USC 522
 *    and the prohibitions of 18 USC 1905
 */

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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.radartech.network.NetworkUtils;
import com.radartech.util.LocationAddress;
import com.radartech.activity.DeviceTraceActivity;
import com.radartech.model.devices.list.BaseDevicesList;
import com.radartech.model.devices.list.DevicesListResponse;
import com.radartech.network.RadarTechService;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;
import com.radartech.util.Utility;

import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.radartech.util.AppConstants.IntentConstants.ACC_OFF;
import static com.radartech.util.AppConstants.IntentConstants.ACC_ON;

/**
 * Created by moonzdream on 9/27/2016.
 */


/**
 * MonitorActivity to show monitor devices in right_arrow google map.
 */
public class MonitorFragment extends BaseGoogleMapFragment {

    @BindView(R.id.tv_address)
    TextView mLocationTv;

    private String mLocationAddress;
    private List<DevicesListResponse> devicesListResponses;
    private int markerDevice;

    final Handler handler = new Handler();

    public Runnable onTimerLiveData = new Runnable() {
        @Override
        public void run() {
            UiUtils.logMessage("shiva : onTimerLiveData");
            if (NetworkUtils.isNetworkAvailable(getActivity())) {
                callCustomerDeviceAndGpsoneAPI();
            }
        }
    };
    private LatLng Latlng1;
    private int count = 1;
    LatLngBounds.Builder builder = new LatLngBounds.Builder();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor, container, false);
        initButterKnife(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        loadMap();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(onTimerLiveData);

    }

    @Override
    public void onResume() {
        super.onResume();
        UiUtils.displayProgress(getActivity(), "");
        handler.removeCallbacks(onTimerLiveData);
        callCustomerDeviceAndGpsoneAPI();
    }

    private void refreshDeviceData() {
        UiUtils.logMessage("monitor, timer called");
        handler.postDelayed(onTimerLiveData, AppConstants.DEVICE_LIST_REFRESH_TIME);
    }

    private void addMarkers() {
        if (mGoogleMap == null) {
            return;
        }
        for (int i = 0; i < devicesListResponses.size(); i++) {
            markerDevice = R.drawable.gray_car;
            DevicesListResponse devicesListResponse = devicesListResponses.get(i);

            int[] deviceStatusImages = Utility.getTrackingImage(String.valueOf(devicesListResponse.getDeviceid()));
            int greyVehicle = deviceStatusImages[0];
            int greenVehicle = deviceStatusImages[1];
            int redVehicle = deviceStatusImages[2];
            int offlineVehicle = deviceStatusImages[3];
            int orangeVehicle = deviceStatusImages[4];

            String deviceStatus = Utility.getDeviceStatus(devicesListResponse);
            if (deviceStatus.equalsIgnoreCase(StatusConstants.OFFLINE)) {
                markerDevice = offlineVehicle;
            }else if (deviceStatus.equalsIgnoreCase(StatusConstants.LOGGED_OFF)) {
                markerDevice = offlineVehicle;
            } else if (deviceStatus.equalsIgnoreCase(StatusConstants.STATIC)) {
                markerDevice = greyVehicle;
            } else if (deviceStatus.equalsIgnoreCase(StatusConstants.MOVING)) {
                markerDevice = greenVehicle;
            } else if (deviceStatus.equalsIgnoreCase(StatusConstants.HIGH_SPEED)) {
                markerDevice = orangeVehicle;
            } else if (deviceStatus.equalsIgnoreCase(StatusConstants.OVER_SPEED)) {
                markerDevice = redVehicle;
            }

            Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(devicesListResponse.getLat(), devicesListResponse.getLng()))
                    .title(mLocationAddress)
                    .icon(BitmapDescriptorFactory.fromResource(markerDevice))
                    .rotation(Float.valueOf(devicesListResponse.getCourse())));
            marker.setTag(i);

            if (count <=devicesListResponses.size()){
                Latlng1 = new LatLng(devicesListResponse.getLat(), devicesListResponse.getLng());
                builder.include(Latlng1);
                LatLngBounds bounds = builder.build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, DEFAULT_GOOGLE_MAP_ZOOM_LEVEL));
                count++;
            }
        }
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation(marker.getPosition().latitude
                        , marker.getPosition().longitude,
                        getActivity(), new GeocoderHandler());
                mLocationTv.setText("Loading...");
                return false;
            }
        });
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    mLocationAddress = bundle.getString("address");
                    mLocationTv.setText(getString(R.string.info_address, mLocationAddress));
                    break;
                default:
                    mLocationAddress = null;
            }
        }
    }

    private void callCustomerDeviceAndGpsoneAPI() {

        Observable<BaseDevicesList> loginObservable = RadarTechService.getInstance().getAPIService()
                .customerDeviceAndGpsoneAPI(SharedPreferenceUtils.readString(PreferenceConstants.PREF_COOKIES), AppConstants.MAP_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<BaseDevicesList>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(getActivity(), e);
            }

            @Override
            public void onNext(BaseDevicesList response) {
                if (response.getErrorcode() == 0) {
                    if (mGoogleMap != null) {
                        mGoogleMap.clear();
                    }
                    devicesListResponses = response.getRecords();
                    addMarkers();
                    refreshDeviceData();
                } else {
                    UiUtils.showToast(getActivity(), response.getErrorcode().toString());
                }
                UiUtils.dismissProgress();
            }
        });

        addSubscription(subscribe);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        mGoogleMap.setInfoWindowAdapter(infoWindowAdapter);
        mGoogleMap.setOnInfoWindowClickListener(onInfoWindowClickListener);
    }

    GoogleMap.OnInfoWindowClickListener onInfoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            String stringPos = marker.getTag().toString();
            int position = Integer.parseInt(stringPos);
            DevicesListResponse devicesListResponse = devicesListResponses.get(position);

            Bundle bundle = new Bundle();
            bundle.putString(IntentConstants.DEVICE_NAME, devicesListResponse.getDevicename());
            bundle.putInt(IntentConstants.DEVICE_OVER_SPEED, devicesListResponse.getOverspeed());
            bundle.putInt(IntentConstants.DEVICE_ID, devicesListResponse.getDeviceid());

            UiUtils.launchActivity(getActivity(), DeviceTraceActivity.class, bundle, false);
        }
    };

    GoogleMap.InfoWindowAdapter infoWindowAdapter = new GoogleMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            String stringPos = marker.getTag().toString();
            int position = Integer.parseInt(stringPos);
            DevicesListResponse devicesListResponse = devicesListResponses.get(position);

            View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_marker_info, null);
            TextView info_title = (TextView) view.findViewById(R.id.info_title);
            TextView info_description = (TextView) view.findViewById(R.id.info_description);

            info_title.setText(devicesListResponse.getDevicename());

            String status = devicesListResponse.getStatus();
            String accLetter = Character.toString(status.charAt(7));
            accLetter =  Utility.hexToBinary(accLetter);
            String acc;
            if (accLetter.equals(StatusConstants.ACC_BINARY_NUMBER)){
                acc = ACC_OFF;
            }else {
                acc = ACC_ON;
            }
            StringBuilder stringBuilder = new StringBuilder(getString(R.string.info_status, Utility.getDeviceStatus(devicesListResponse)));
            stringBuilder.append(System.getProperty("line.separator"));
            stringBuilder.append(getString(R.string.info_time, Utility.getDateFromLong(devicesListResponse.getHearttime(), AppConstants.YYYY_MM_DD_HH_MM_SS_SLASH)));
            stringBuilder.append(System.getProperty("line.separator"));
            stringBuilder.append(getString(R.string.info_acc, acc));
            info_description.setText(stringBuilder.toString());
            return view;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

    };
}
