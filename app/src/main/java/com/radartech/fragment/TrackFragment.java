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

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.radartech.activity.DevicesListActivity;
import com.radartech.model.APIBaseResponse;
import com.radartech.model.track.BaseTrackResponse;
import com.radartech.model.track.TrackResponse;
import com.radartech.network.RadarTechService;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.LocationAddress;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;
import com.radartech.util.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.radartech.util.AppConstants.IntentConstants.ACC_OFF;
import static com.radartech.util.AppConstants.IntentConstants.ACC_ON;
import static com.radartech.util.AppConstants.PreferenceConstants.INVALIDCREDENTIALS;
import static com.radartech.util.AppConstants.PreferenceConstants.PREF_PASSWORD;
import static com.radartech.util.AppConstants.PreferenceConstants.PREF_USER_NAME;


/**
 * Created by office on 9/22/2016.
 */
public class TrackFragment extends BaseGoogleMapFragment {

    @BindView(R.id.title)
    TextView mDeviceName;

    private int aDeviceId;
    private int aOverSpeed;
    private String aDeviceName;

    private String locationAddress;

    private List<TrackResponse> carDeviceList = new ArrayList<>();
    private ArrayList<LatLng> latLngPlottedList = new ArrayList<>();

    private Marker marker;

    final Handler handler = new Handler();

    public Runnable onTimerLiveData = new Runnable() {
        @Override
        public void run() {
            UiUtils.logMessage("shiva : onTimerLiveData");
            callTrackAPI();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_track, container, false);
        initButterKnife(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {

        carDeviceList = new ArrayList<>();
        latLngPlottedList = new ArrayList<>();
        Bundle bundle = this.getArguments();
        aDeviceId = bundle.getInt(IntentConstants.DEVICE_ID);
        aDeviceName = bundle.getString(IntentConstants.DEVICE_NAME);
        aOverSpeed = SharedPreferenceUtils.readInteger(aDeviceId + PreferenceConstants.DEVICE_OVER_SPEED);
        mDeviceName.setText(aDeviceName);

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
        carDeviceList.clear();
        callTrackAPI();
    }

    private void refreshDeviceData() {
        handler.postDelayed(onTimerLiveData, AppConstants.DEVICE_LIST_REFRESH_TIME);
    }

    @OnClick(R.id.back_button)
    public void onClickDeviceBack() {
        getActivity().finish();
    }

    GoogleMap.InfoWindowAdapter infoWindowAdapter = new GoogleMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            String stringPos = marker.getTag().toString();
            int position = Integer.parseInt(stringPos);
            TrackResponse devicesListResponse = carDeviceList.get(position);

            View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_marker_info, null);

            TextView info_title = (TextView) view.findViewById(R.id.info_title);
            TextView info_description = (TextView) view.findViewById(R.id.info_description);


            info_title.setText(aDeviceName);


            String status = devicesListResponse.getStatus();
            String accLetter = Character.toString(status.charAt(7));
            accLetter = Utility.hexToBinary(accLetter);
            String acc;
            if (accLetter.equals(StatusConstants.ACC_BINARY_NUMBER)) {
                acc = ACC_OFF;
            } else {
                acc = ACC_ON;
            }
            StringBuilder stringBuilder = new StringBuilder();
            String deviceStatus = Utility.getDeviceStatus(devicesListResponse);
            if (deviceStatus.equalsIgnoreCase(StatusConstants.STATIC)) {
                long diffInMinutes = devicesListResponse.getSystime() - devicesListResponse.getServertime();
                stringBuilder.append(getString(R.string.info_status, deviceStatus) + "(" + Utility.getDurationBreakdown(Math.abs(diffInMinutes)) + ")");
            } else if (deviceStatus.equalsIgnoreCase(StatusConstants.OFFLINE)) {
                long diffInMinutes = devicesListResponse.getHearttime() - devicesListResponse.getServertime();
                stringBuilder.append(getString(R.string.info_status, deviceStatus) + "(" + Utility.getDurationBreakdown(Math.abs(diffInMinutes)) + ")");
                stringBuilder.append(System.getProperty("line.separator"));
                stringBuilder.append(getString(R.string.info_sped, String.valueOf(devicesListResponse.getSpeed())));

            } else {
                stringBuilder.append(getString(R.string.info_status, deviceStatus));
                stringBuilder.append(System.getProperty("line.separator"));
                stringBuilder.append(getString(R.string.info_sped, String.valueOf(devicesListResponse.getSpeed())));

            }
            stringBuilder.append(System.getProperty("line.separator"));
            stringBuilder.append(getString(R.string.info_time, Utility.getDateFromLong(devicesListResponse.getGpstime(), AppConstants.YYYY_MM_DD_HH_MM_SS_SLASH)));

            stringBuilder.append(System.getProperty("line.separator"));
            stringBuilder.append(getString(R.string.info_acc, acc));
            stringBuilder.append(System.getProperty("line.separator"));
            if (locationAddress == null) {
                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation(devicesListResponse.getLat(), devicesListResponse.getLng(), getContext(), new GeocoderHandler());
            }
            if (locationAddress != null)
                stringBuilder.append(getString(R.string.info_address, locationAddress));
            stringBuilder.append(System.getProperty("line.separator"));
            info_description.setText(stringBuilder.toString().trim());
            return view;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    };

    private void addMarker() {
        if (mGoogleMap == null) {
            return;
        }

        if (carDeviceList.size() == 0) {
            return;
        }
        int plottedListSize = latLngPlottedList.size();
        TrackResponse trackResponse = null;
        try{
            trackResponse = carDeviceList.get(plottedListSize);
            latLngPlottedList.add(new LatLng(trackResponse.getLat(), trackResponse.getLng()));

        }catch (ArrayIndexOutOfBoundsException e){

        }

        Double alat;
        Double aLong;
        int carSpeed;
        if (plottedListSize == 0) {
            MarkerOptions ma = new MarkerOptions()
                    .position(latLngPlottedList.get(plottedListSize)).rotation(trackResponse.getCourse());
            marker = mGoogleMap.addMarker(ma);
            marker.setTag(plottedListSize);
            alat = trackResponse.getLat();
            aLong = trackResponse.getLng();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(alat, aLong, getContext(), new GeocoderHandler());

            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLngPlottedList.get(plottedListSize), DEFAULT_GOOGLE_MAP_ZOOM_LEVEL);
            mGoogleMap.animateCamera(yourLocation);
        } else {
            LatLng srcLatLng = latLngPlottedList.get(plottedListSize - 1);
            LatLng destLatLng = latLngPlottedList.get(plottedListSize);
            alat = trackResponse.getLat();
            aLong = trackResponse.getLng();

            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(alat, aLong, getContext(), new GeocoderHandler());
            carSpeed = trackResponse.getSpeed();

            marker.setPosition(destLatLng);
            marker.setTag(plottedListSize);
            marker.setRotation(trackResponse.getCourse());
//            marker.showInfoWindow();

            int lineColor;
            if (carSpeed > aOverSpeed) {
                lineColor = Color.RED;
            } else {
                lineColor = Color.GREEN;
            }

            mGoogleMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(srcLatLng.latitude, srcLatLng.longitude), new LatLng(destLatLng.latitude, destLatLng.longitude))
                    .width(DEFAULT_GOOGLE_MAP_LINE_WIDTH)
                    .color(lineColor).geodesic(true));

            //zooms map when marker goes out of the screen
            LatLng tempLatLng = new LatLng(trackResponse.getLat(), trackResponse.getLng());
            boolean contains = mGoogleMap.getProjection()
                    .getVisibleRegion()
                    .latLngBounds
                    .contains(tempLatLng);
            if (!contains) {
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(tempLatLng, DEFAULT_GOOGLE_MAP_ZOOM_LEVEL);
                mGoogleMap.moveCamera(yourLocation);
            }
        }
        String deviceStatus = Utility.getDeviceStatus(trackResponse);
        carDeviceList.get(plottedListSize).setCustomStatus(deviceStatus);
        int[] deviceStatusImages = Utility.getTrackingImage(String.valueOf(trackResponse.getDeviceid()));
        int greyVehicle = deviceStatusImages[0];
        int greenVehicle = deviceStatusImages[1];
        int redVehicle = deviceStatusImages[2];
        int offlineVehicle = deviceStatusImages[3];
        int orangeVehicle = deviceStatusImages[4];
        int vehicleImage = offlineVehicle;
        if (deviceStatus.equalsIgnoreCase(StatusConstants.OFFLINE)) {
            vehicleImage = offlineVehicle;
        } else if (deviceStatus.equalsIgnoreCase(StatusConstants.LOGGED_OFF)) {
            vehicleImage = offlineVehicle;
        } else if (deviceStatus.equalsIgnoreCase(StatusConstants.STATIC)) {
            vehicleImage = greyVehicle;
        } else if (deviceStatus.equalsIgnoreCase(StatusConstants.MOVING)) {
            vehicleImage = greenVehicle;
        } else if (deviceStatus.equalsIgnoreCase(StatusConstants.HIGH_SPEED)) {
            vehicleImage = orangeVehicle;
        } else if (deviceStatus.equalsIgnoreCase(StatusConstants.OVER_SPEED)) {
            vehicleImage = redVehicle;
        }
        marker.setIcon(BitmapDescriptorFactory.fromResource(vehicleImage));

        mGoogleMap.setInfoWindowAdapter(infoWindowAdapter);
    }

    private void callTrackAPI() {
        Observable<BaseTrackResponse> loginObservable = RadarTechService.getInstance().getAPIService()
                .trackAPI(SharedPreferenceUtils.readString(AppConstants.PreferenceConstants.PREF_COOKIES), String.valueOf(aDeviceId), AppConstants.MAP_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<BaseTrackResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
//                UiUtils.showErrorMessage(getActivity(), e);
            }

            @Override
            public void onNext(BaseTrackResponse response) {
                if (response.getErrorcode() == 0) {
                    //load map based on response
                    carDeviceList.add(response.getRecord());
                    refreshDeviceData();
                    addMarker();
                }
                else {
                    UiUtils.showToast(getActivity(), "Device items added");
                    String password = Utility.md5(SharedPreferenceUtils.readString(PREF_PASSWORD));
                    callLoginAPI(SharedPreferenceUtils.readString(PREF_USER_NAME),password);
                }
//                UiUtils.showToast(getActivity(), response.getErrorcode().toString());
                UiUtils.dismissProgress();
            }
        });

        addSubscription(subscribe);
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString(IntentConstants.LAT_LNG_ADDRESS);
                    break;
                default:
                    locationAddress = null;
            }
        }
    }
    private void callLoginAPI(final String userName, final String password) {
        Log.i("shiva", userName + "");
        Log.i("shiva", password + "");

        Observable<APIBaseResponse> loginObservable = RadarTechService.getInstance().getAPIService()
                .loginAPI(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<APIBaseResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
            }

            @Override
            public void onNext(APIBaseResponse response) {
                Log.i("shiva", response.getErrorcode() + "");

                UiUtils.dismissProgress();
                if (response.getErrorcode() == 0) {
                    callTrackAPI();
                } else {
                    if (response.getErrorcode().equals(INVALIDCREDENTIALS)){
                    }
                }
            }
        });

        addSubscription(subscribe);
    }


}