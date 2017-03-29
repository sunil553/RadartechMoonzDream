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

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cardiomood.android.controls.gauge.SpeedometerGauge;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.radartech.model.ParkTimeData;
import com.radartech.model.PlayBackResponse;
import com.radartech.model.devices.list.DevicesListResponse;
import com.radartech.network.RadarTechService;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;
import com.radartech.util.Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by office on 9/22/2016.
 */
public class PlayBackFragment extends BaseGoogleMapFragment {

    private static final float DEFAULT_LABEL_TEXT_SIZE_DP = 6;
    private final int POPUP_TIME = 21;
    private final int POPUP_SPEED = 22;

    @BindView(R.id.ib_device_trace_time)
    ImageView mTimeButton;
    @BindView(R.id.ib_device_trace_speed)
    ImageView mSpeedButton;
    @BindView(R.id.sbar_history)
    SeekBar sbar_history;
    @BindView(R.id.ibtn_control)
    CheckBox mPlayStopSeekBar;
    @BindView(R.id.playback_timer)
    TextView playback_timer;
    @BindView(R.id.playback_kms)
    TextView playback_kms;
    @BindView(R.id.playback_speed)
    TextView playback_speed;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.tv_time)
    TextView playbackTime;
    @BindView(R.id.layout1)
    RelativeLayout playbackDetailsLayout;

    private Marker marker;
    private int aDeviceId;
    private int aDeviceOverSpeed;
    private String aDeviceName;

    private ArrayList<ParkTimeData> parkTimeList = new ArrayList<>();
    private ArrayList<LatLng> latLngPlottedList = new ArrayList<>();
    private ArrayList<DevicesListResponse> carLatLngList = new ArrayList<>();

    private PopupWindow popupWindow;
    private String mToMilliSeconds;
    private String mFromMilliSeconds;

    float distanceINKM = 0;
    private int mInterval = 1000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    private int count = 0;
    private DatePickerDialog fromDatePickerDialog;
    int popupSlectedRadioTimePos = 0;
    int popupSlectedRadioSpeedPos = INDEX_NORMAL_SPEED;

    private SpeedometerGauge speedometer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_play_back, container, false);
        initButterKnife(view);

        // Customize SpeedometerGauge
        speedometer = (SpeedometerGauge) view.findViewById(R.id.speedometer);
        float density = getResources().getDisplayMetrics().density;
        //Text size to meter
        speedometer.setLabelTextSize(Math.round(DEFAULT_LABEL_TEXT_SIZE_DP * density));

        speedometer.setVisibility(View.VISIBLE);
        // Add label converter
        speedometer.setLabelConverter(new SpeedometerGauge.LabelConverter() {
            @Override
            public String getLabelFor(double progress, double maxProgress) {
                return String.valueOf((int) Math.round(progress));
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {

        playbackDetailsLayout.setVisibility(View.GONE);
        playbackTime.setVisibility(View.GONE);
        Bundle bundle = this.getArguments();
        aDeviceId = bundle.getInt(IntentConstants.DEVICE_ID);
        aDeviceName = bundle.getString(IntentConstants.DEVICE_NAME);
        aDeviceOverSpeed = SharedPreferenceUtils.readInteger(aDeviceId + PreferenceConstants.DEVICE_OVER_SPEED);
        mTitle.setText(aDeviceName);

        /*sbar_history.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });*/
        sbar_history.setOnSeekBarChangeListener(onSeekBarChangeListener);
        mHandler = new Handler();
        loadMap();
    }

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            UiUtils.logMessage("progress :" + progress + fromUser);
            if (fromUser) {
                int updatedPoint = (progress * carLatLngList.size()) / 100;
                stopRepeatingTask();
                if (latLngPlottedList.size() > updatedPoint) {
                    resetTimerData();
                }
                while (latLngPlottedList.size() < updatedPoint) {
                    addMapMarker(false);
                }
                startRepeatingTask();
            }
        }
    };

    @Override
    public void onDestroyView() {
        dismissPopupWindow();
        stopRepeatingTask();
        super.onDestroyView();
    }

    private void dismissPopupWindow() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }


    private Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            addMapMarker(true);
        }
    };

    private void showDialogAfterNavigation() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_play_back, null);
        final TextView tvFrom = (TextView) alertLayout.findViewById(R.id.tv_from);
        final TextView tvTo = (TextView) alertLayout.findViewById(R.id.tv_to);
        final TextView tvMilage = (TextView) alertLayout.findViewById(R.id.tv_milage_et);

        tvFrom.setText(getString(R.string.playback_dialog_from, Utility.getDateFromLong(Long.parseLong(mFromMilliSeconds), YYYY_MM_DD_HH_MM_SS_DASH)));
        tvTo.setText(getString(R.string.playback_dialog_to, Utility.getDateFromLong(Long.parseLong(mToMilliSeconds), YYYY_MM_DD_HH_MM_SS_DASH)));
        tvMilage.setText(getString(R.string.playback_dialog_mileage, String.valueOf((float) Math.round(distanceINKM * 100) / 100)));
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Info");
        alert.setView(alertLayout);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.setCancelable(true);

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void showUserDefinedDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.time_selector_layout, null);
        final TextView tvDateSelector = (TextView) alertLayout.findViewById(R.id.time_selector_date);
        final TextView tvTimeFromSelector = (TextView) alertLayout.findViewById(R.id.time_selector_time);
        final TextView tvTimeToSelector = (TextView) alertLayout.findViewById(R.id.time_selector_time_to);

        tvDateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                fromDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat(EEE_MMM_dd_yyyy);
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        dateFormat.format(newDate.getTime());
                        tvDateSelector.setText(dateFormat.format(newDate.getTime()));
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                fromDatePickerDialog.show();
            }
        });
        tvTimeToSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear;
                int mMonth;
                int mDay;
                int mHour;
                int mMinute;
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog tpd = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {


                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // Display Selected time in textbox
                                String sHour = "00";
                                if (hourOfDay < 10) {
                                    sHour = "0" + hourOfDay;
                                } else {
                                    sHour = String.valueOf(hourOfDay);
                                }

                                String sMinute = "00";
                                if (minute < 10) {
                                    sMinute = "0" + minute;
                                } else {
                                    sMinute = String.valueOf(minute);
                                }
                                String toTime = sHour + ":" + sMinute + ":" + "00";
                                tvTimeToSelector.setText(toTime);

                            }
                        }, mHour, mMinute, false);
                //        tpd.set
                tpd.show();

            }
        });

        tvTimeFromSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear;
                int mMonth;
                int mDay;
                int mHour;
                int mMinute;
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog tpd = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {


                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // Display Selected time in textbox
                                String sHour = "00";
                                if (hourOfDay < 10) {
                                    sHour = "0" + hourOfDay;
                                } else {
                                    sHour = String.valueOf(hourOfDay);
                                }

                                String sMinute = "00";
                                if (minute < 10) {
                                    sMinute = "0" + minute;
                                } else {
                                    sMinute = String.valueOf(minute);
                                }
                                String toTime = sHour + ":" + sMinute + ":" + "00";
                                tvTimeFromSelector.setText(toTime);
                            }
                        }, mHour, mMinute, false);
                //        tpd.set
                tpd.show();
            }
        });
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Choose time range");
        alert.setView(alertLayout);
        alert.setCancelable(true);
        alert.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String selectedDate = tvDateSelector.getText().toString();
                String selectedFromTime = tvTimeFromSelector.getText().toString();
                String selectedToTime = tvTimeToSelector.getText().toString();

                Utility.showLogMessage("today time ", "selectedDate : " + selectedDate + selectedFromTime + ", selectedFromTime" + selectedToTime);

                //TODO have to check whethe mfromseonds is valid or not
                mFromMilliSeconds = getTimeInMilliSeconds(selectedDate + " " + selectedFromTime, EEE_MMM_dd_yyyy + " kk:mm:ss ");
                mToMilliSeconds = getTimeInMilliSeconds(selectedDate + " " + selectedToTime, EEE_MMM_dd_yyyy + " kk:mm:ss ");
                Utility.showLogMessage("today time ", mFromMilliSeconds + ", mFromMilliSeconds" + mToMilliSeconds);
                callAsyncTask();

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    @OnCheckedChanged(R.id.ibtn_control)
    public void onCheckboxPlayStop(boolean isChecked) {
        UiUtils.logMessage("onCheckboxPlayStop : " + isChecked);
        if (isChecked) {

            if (carLatLngList == null || carLatLngList.size() == 0) {//if the carlatlng list is size  playing today
                stopRepeatingTask();
                RadioButton radioButton = new RadioButton(getActivity());
                radioButton.setTag(String.valueOf(popupSlectedRadioTimePos));
                onClickTimePopupListener.onClick(radioButton);
            } else {
                if (carLatLngList == null) {
                    return;
                }
                if (latLngPlottedList.size() != carLatLngList.size()) {//updating playback based on api cal
                    startRepeatingTask();
                } else if (latLngPlottedList.size() == carLatLngList.size()) {//after completion of playback restart same cyscle
                    stopRepeatingTask();
                    latLngPlottedList.clear();
                    parkTimeList.clear();
                    mGoogleMap.clear();
                    addMapMarker(false);
                    startRepeatingTask();
                }
            }
        } else {
            stopRepeatingTask();
        }
    }

    @OnClick(R.id.ib_device_trace_speed)
    public void onClickDeviceTrackSpeed() {
        showPopUp(POPUP_SPEED);
    }

    @OnClick(R.id.ib_device_trace_time)
    public void onClickDeviceTrackTime() {
        showPopUp(POPUP_TIME);
    }

    @OnClick(R.id.back_button)
    public void onClickDeviceBack() {
        getActivity().finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        if (mGoogleMap != null) {
            mGoogleMap.setOnMarkerClickListener(onMarkerClickListener);
            mGoogleMap.setInfoWindowAdapter(infoWindowAdapter);
            mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    dismissPopupWindow();
                }
            });

            showCurrentLocation();
        }
    }

    private void showCurrentLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        String provider = locationManager.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            if (mGoogleMap != null) {
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_MINIMUM_GOOGLE_MAP_ZOOM_LEVEL));

            }
        }
    }


    private void showPopUp(int popUpType) {

        int tempPopupDimension = (int) getResources().getDimension(R.dimen.custom_popup_window_height);
        dismissPopupWindow();
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = null;
        if (popUpType == POPUP_TIME) {
            popupView = layoutInflater.inflate(R.layout.time_window_layout, null);
        } else if (popUpType == POPUP_SPEED) {
            popupView = layoutInflater.inflate(R.layout.speed_window_layout, null);
        }
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        if (popUpType == POPUP_TIME) {
            LinearLayout popupTimeLayout = (LinearLayout) popupView.findViewById(R.id.popup_time_layout);
            showPopUpTime(popupTimeLayout);
            popupWindow.showAtLocation(mTimeButton, Gravity.TOP | Gravity.RIGHT, mTimeButton.getWidth() / 2, tempPopupDimension);
//            popupWindow.showAsDropDown(mTimeButton);

        } else if (popUpType == POPUP_SPEED) {
            LinearLayout popupSpeedLayout = (LinearLayout) popupView.findViewById(R.id.popup_speed_layout);
            showPopUpSpeed(popupSpeedLayout);
            popupWindow.showAtLocation(mSpeedButton, Gravity.TOP | Gravity.RIGHT, mTimeButton.getWidth() + mSpeedButton.getWidth() / 2, tempPopupDimension);
//            popupWindow.showAsDropDown(mSpeedButton);
        }

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
    }

    private void showPopUpTime(final LinearLayout radioGroupTimeLayout) {
        for (int i = 0; i < radioGroupTimeLayout.getChildCount(); i++) {
            RadioButton speedRadio = (RadioButton) radioGroupTimeLayout.getChildAt(i);
            if (i == popupSlectedRadioTimePos) {
                speedRadio.setChecked(true);
            } else {
                speedRadio.setChecked(false);
            }
            speedRadio.setOnClickListener(onClickTimePopupListener);
        }
    }

    View.OnClickListener onClickTimePopupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton radioButton = (RadioButton) v;
            int index = Integer.parseInt(radioButton.getTag().toString());
            dismissPopupWindow();
            popupSlectedRadioTimePos = index;
            switch (index) {
                case 0:
                    mFromMilliSeconds = getTimeInMilliSeconds(getCurrentStartOfDateString());
                    mToMilliSeconds = getTimeInMilliSeconds(getCurrentDateString());
                    callAsyncTask();
                    break;
                case 1:
                    mFromMilliSeconds = getTimeInMilliSeconds(getYesterdayStartDateString());
                    mToMilliSeconds = getTimeInMilliSeconds(getYesterdayEndDateString());
                    callAsyncTask();
                    break;
                case 2:
                    mFromMilliSeconds = getTimeInMilliSeconds(getBeforeYesterdayStartDateString());
                    mToMilliSeconds = getTimeInMilliSeconds(getBeforeYesterdayEndDateString());
                    callAsyncTask();
                    break;
                case 3:
                    mToMilliSeconds = getTimeInMilliSeconds(getAccurateDateString());
                    mFromMilliSeconds = getTimeInMilliSeconds(getOneHourDateString());
                    Utility.showLogMessage("hour time ", getOneHourDateString() + "milliseconds" + mToMilliSeconds);
                    callAsyncTask();
                    break;
                case 4:
                    showUserDefinedDialog();
                    break;

            }
        }
    };


    private void showPopUpSpeed(final LinearLayout radioButtonLayout) {
        for (int i = 0; i < radioButtonLayout.getChildCount(); i++) {
            RadioButton speedRadio = (RadioButton) radioButtonLayout.getChildAt(i);
            if (i == popupSlectedRadioSpeedPos) {
                speedRadio.setChecked(true);
            } else {
                speedRadio.setChecked(false);
            }
            speedRadio.setOnClickListener(onClickSpeedPopupListener);
        }
    }

    View.OnClickListener onClickSpeedPopupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton radioButton = (RadioButton) v;
            int index = Integer.parseInt(radioButton.getTag().toString());
            dismissPopupWindow();
            popupSlectedRadioSpeedPos = index;
            switch (index) {
                case 0:
                    mInterval = SpeedConstants.SPEED_VERY_SLOW;
                    break;
                case 1:
                    mInterval = SpeedConstants.SPEED_SLOW;
                    break;
                case 2:
                    mInterval = SpeedConstants.SPEED_NORMAL;
                    break;
                case 3:
                    mInterval = SpeedConstants.SPEED_FAST;
                    break;
                case 4:
                    mInterval = SpeedConstants.SPEED_VERY_FAST;
                    break;

            }
        }
    };

    private void callAsyncTask() {
        dismissPopupWindow();
        callPlayBackAPI();
    }

    private void addMapMarker(boolean calTimer) {

        if (carLatLngList == null) {
            return;
        }
        if (carLatLngList.size() == 0) {
            UiUtils.showToast(getActivity(), getString(R.string.play_back_no_record));
            return;
        } else if (latLngPlottedList.size() == carLatLngList.size()) {
            stopRepeatingTask();
            mPlayStopSeekBar.setChecked(false);
            showDialogAfterNavigation();
            distanceINKM = 0;
            return;
        }

        if (!mPlayStopSeekBar.isChecked()) {
            mPlayStopSeekBar.setChecked(true);
        }
        int plottedListSize = latLngPlottedList.size();

        DevicesListResponse devicesListResponse = carLatLngList.get(plottedListSize);
        latLngPlottedList.add(new LatLng(devicesListResponse.getLat(), devicesListResponse.getLng()));
        Date date = new Date(devicesListResponse.getHearttime());
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy kk:mm:ss");
        String dateText = df2.format(date);
        playback_timer.setText(dateText);
        playback_kms.setText(getString(R.string.playback_speed, String.valueOf(devicesListResponse.getSpeed())));


        df2 = new SimpleDateFormat("kk:mm:ss");
        playbackTime.setText(df2.format(date));

        if (plottedListSize == 0) {
            MarkerOptions ma = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.gray_car))
                    .position(latLngPlottedList.get(plottedListSize)).rotation(Float.valueOf(devicesListResponse.getCourse()));
            marker = mGoogleMap.addMarker(ma);
            marker.setTag(AppConstants.DEFAULT_VALUE);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLngPlottedList.get(plottedListSize), DEFAULT_GOOGLE_MAP_ZOOM_LEVEL);
            mGoogleMap.animateCamera(yourLocation);
        } else {
           final  LatLng srcLatLng = latLngPlottedList.get(plottedListSize - 1);
           final LatLng destLatLng = latLngPlottedList.get(plottedListSize);
            distanceINKM += Utility.distBetweenLatLngInKM(srcLatLng.latitude, srcLatLng.longitude, destLatLng.latitude, destLatLng.longitude);
            float currentSpeed = (float) Math.round(distanceINKM * 100) / 100;
            playback_speed.setText(getString(R.string.playback_distance, String.valueOf(currentSpeed)));


            long parkTime = devicesListResponse.getHearttime() - carLatLngList.get(plottedListSize - 1).getHearttime();
            int prefParkTimeMillis = SharedPreferenceUtils.readInteger(devicesListResponse.getDeviceid() + PreferenceConstants.PARK_TIME) * 60 * 1000;
            if ((Math.abs(parkTime)) > prefParkTimeMillis) {
                parkTimeList.add(new ParkTimeData(devicesListResponse.getHearttime(), carLatLngList.get(plottedListSize - 1).getHearttime()));
                MarkerOptions ma = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                        .position(latLngPlottedList.get(plottedListSize));
                Marker parkMarker = mGoogleMap.addMarker(ma);
                parkMarker.setTag(parkTimeList.size() - 1);
            }

            speedometer.setMaxSpeed(IntentConstants.SPEEDOMETER_MAX_SPEED);
            speedometer.setMajorTickStep(IntentConstants.SPEEDOMETER_MAJOR_TICK_STEP);
            speedometer.setMinorTicks(IntentConstants.SPEEDOMETER_MINOR_TICK_STEP);
            //needle rotation
            speedometer.setSpeed(devicesListResponse.getSpeed());
            // Configure value range colors
            speedometer.addColoredRange(IntentConstants.SPEEDOMETER_MIN_SPEED, IntentConstants.SPEEDOMETER_AVG_SPEED, Color.WHITE);
            speedometer.addColoredRange(IntentConstants.SPEEDOMETER_AVG_SPEED, IntentConstants.SPEEDOMETER_ABOVE_AVG_SPEED, Color.WHITE);
            speedometer.addColoredRange(IntentConstants.SPEEDOMETER_ABOVE_AVG_SPEED, IntentConstants.SPEEDOMETER_MAX_SPEED, Color.WHITE);


            //Animate marker from one point to another


            final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(mInterval); // duration 1 second
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override public void onAnimationUpdate(ValueAnimator animation) {
                    try {
                        float v = animation.getAnimatedFraction();
                        LatLng newPosition = latLngInterpolator.interpolate(v, srcLatLng, destLatLng);
                        marker.setPosition(newPosition);
                    } catch (Exception ex) {
                        // I don't care atm..
                    }
                }
            });

            valueAnimator.start();

//            marker.setPosition(destLatLng);
            marker.setRotation(Float.valueOf(devicesListResponse.getCourse()));


            int lineColor = Color.GREEN;
            if (devicesListResponse.getSpeed() > aDeviceOverSpeed) {
                lineColor = Color.RED;
            } else {
                lineColor = Color.GREEN;
            }


            mGoogleMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(srcLatLng.latitude, srcLatLng.longitude), new LatLng(destLatLng.latitude, destLatLng.longitude))
                    .width(DEFAULT_GOOGLE_MAP_LINE_WIDTH)
                    .color(lineColor).geodesic(true));
        }

        if (plottedListSize < carLatLngList.size() - 1) {

            String deviceStatus = Utility.getDeviceStatus(devicesListResponse);
            int[] deviceStatusImages = Utility.getTrackingImage(String.valueOf(devicesListResponse.getDeviceid()));
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

            //zooms map when marker goes out of the screen
            LatLng tempLatLng = new LatLng(carLatLngList.get(plottedListSize + 1).getLat(), carLatLngList.get(plottedListSize + 1).getLng());
            boolean contains = mGoogleMap.getProjection()
                    .getVisibleRegion()
                    .latLngBounds
                    .contains(tempLatLng);
            if (!contains) {
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(tempLatLng, DEFAULT_GOOGLE_MAP_ZOOM_LEVEL);
                mGoogleMap.moveCamera(yourLocation);
            }
        }


        playbackTime.setVisibility(View.VISIBLE);
        playbackDetailsLayout.setVisibility(View.VISIBLE);

        sbar_history.setProgress((latLngPlottedList.size() * 100) / carLatLngList.size());

        if (calTimer) {
            mHandler.postDelayed(mStatusChecker, mInterval);
        }
    }

    private String getYesterdayStartDateString() {
        DateFormat dateFormat = new SimpleDateFormat(START_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }
    private interface LatLngInterpolator {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

        class LinearFixed implements LatLngInterpolator {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lngDelta = b.longitude - a.longitude;
                // Take the shortest path across the 180th meridian.
                if (Math.abs(lngDelta) > 180) {
                    lngDelta -= Math.signum(lngDelta) * 360;
                }
                double lng = lngDelta * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
    }

    private String getYesterdayEndDateString() {
        DateFormat dateFormat = new SimpleDateFormat(END_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }

    private String getBeforeYesterdayStartDateString() {
        DateFormat dateFormat = new SimpleDateFormat(START_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        return dateFormat.format(cal.getTime());
    }

    private String getBeforeYesterdayEndDateString() {
        DateFormat dateFormat = new SimpleDateFormat(END_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        return dateFormat.format(cal.getTime());
    }

    private String getOneHourDateString() {
        DateFormat dateFormat = new SimpleDateFormat(CURRENT_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        return dateFormat.format(cal.getTime());
    }

    private String getAccurateDateString() {
        DateFormat dateFormat = new SimpleDateFormat(CURRENT_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -0);
        return dateFormat.format(cal.getTime());
    }

    private String getCurrentDateString() {
        DateFormat dateFormat = new SimpleDateFormat(END_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -0);
        return dateFormat.format(cal.getTime());
    }

    private String getCurrentStartOfDateString() {
        DateFormat dateFormat = new SimpleDateFormat(START_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -0);
        return dateFormat.format(cal.getTime());
    }

    private String getTimeInMilliSeconds(String givenDateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(CURRENT_DATE_FORMAT);
        long timeInMilliseconds = 0;
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(timeInMilliseconds);
    }

    private String getTimeInMilliSeconds(String givenDateString, String dateFormatter) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatter.trim());
        long timeInMilliseconds = 0;
        try {
            Date mDate = sdf.parse(givenDateString.trim());
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(timeInMilliseconds);
    }

    private void callPlayBackAPI() {
        UiUtils.logMessage("callPlayBackAPI: " + String.valueOf(aDeviceId) + "," + mFromMilliSeconds + "," + mToMilliSeconds + "," + AppConstants.MAP_TYPE);
        Observable<PlayBackResponse> loginObservable = RadarTechService.getInstance().getAPIService()
                .playbackAPI(SharedPreferenceUtils.readString(PreferenceConstants.PREF_COOKIES), String.valueOf(aDeviceId), mFromMilliSeconds, mToMilliSeconds, AppConstants.MAP_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<PlayBackResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(getActivity(), e);
            }

            @Override
            public void onNext(PlayBackResponse response) {
                if (response.getErrorcode() == 0) {

                    stopRepeatingTask();
                    resetStatus();
                    getFiniteLatlng(parsePlayBackResponse(response));
                    startRepeatingTask();
                } else {
                    UiUtils.showToast(getActivity(), response.getErrorcode().toString());
                }
                UiUtils.dismissProgress();
            }


        });

        addSubscription(subscribe);
    }

    private void getFiniteLatlng(ArrayList<DevicesListResponse> devicesListResponses) {
        int waste_point_count = 0;
        DevicesListResponse prevDeviceListResponse = null;
        for (DevicesListResponse currentDevicesListResponse : devicesListResponses) {
            int speed = currentDevicesListResponse.getSpeed();
            int MAX_DISTANCE_PER_SEC = 55;
            if (speed < 5) {
                waste_point_count++;
                continue;//don't inlude this lat long in playback
            }
            if (speed >= 5 && speed < 10 && waste_point_count > 5) {
                //ignore this point too
                waste_point_count++;
                continue;//don't inlude this lat long in playback
            }
            if (speed >= 10 && speed < 15 && prevDeviceListResponse != null && prevDeviceListResponse.getSpeed() < 5 && waste_point_count > 8) {
                //ignore this point too
                waste_point_count++;
                continue;//don't inlude this lat long in playback
            }
//            if(distance_between_last_point_to_current_point > time_difference_from_last_point_to_current_point * MAX_DISTANCE_PER_SEC)
/*
            if(prevDeviceListResponse != null &&
                    Utility.distBetweenLatLngInMeters(prevDeviceListResponse.getLat(), prevDeviceListResponse.getLng(), currentDevicesListResponse.getLat(),
                    currentDevicesListResponse.getLng()) > (prevDeviceListResponse.getHearttime() - currentDevicesListResponse.getHearttime()) * MAX_DISTANCE_PER_SEC)
            //TODO have to check
            {
                waste_point_count++;
                prevDeviceListResponse = currentDevicesListResponse;
                continue;//don't inlude this lat long in playback
            }
*/
            waste_point_count = 0;
            prevDeviceListResponse = currentDevicesListResponse;
            carLatLngList.add(currentDevicesListResponse);

        }
        /*total lat long loop {

            speed = current point speed
            MAX_DISTANCE_PER_SEC = 55 (merters)

            if(speed<5)
            {
                waste_point_count++;
                prev_point = current_point;
                continue;//don't inlude this lat long in playback
            }
            if(speed>=5 && speed<10 && waste_point_count>5)
            {
                //ignore this point too
                waste_point_count++;
                prev_point = current_point;
                continue;//don't inlude this lat long in playback
            }
            if(speed>=10 && speed<15 && prev_point_speed<5 && waste_point_count>8)
            {
                //ignore this point too
                waste_point_count++;
                prev_point = current_point;
                continue;//don't inlude this lat long in playback
            }
            if(distance_between_last_point_to_current_point > time_difference_from_last_point_to_current_point * MAX_DISTANCE_PER_SEC)
            {
                waste_point_count++;
                prev_point = current_point;
                continue;//don't inlude this lat long in playback
            }

            waste_point_count=0;
            prev_point = current_point;
            current_point needs to be displayed on map.


        }

//Once the above loop got comleted we will get new points in new array which are actually having some speed now the below login will work for new points only
//Logic to show parktime/staytime
        set STAY_TIME = 3 mins // customer can able to change this value from settings.
        if(time_difference_from_last_point_to_current_point > STAY_TIME) {
            mark it as parked and show the the park time as
            "time_difference_from_last_point_to_current_point "
        }*/
    }

    private ArrayList<DevicesListResponse> parsePlayBackResponse(PlayBackResponse response) {
        ArrayList<DevicesListResponse> deviceDetailsFromApi = new ArrayList<>();
        String record = response.getRecord();
        String[] semiList = record.split(";");

        List<String> listLat = new ArrayList<String>();
        for (String name : semiList) {
            System.out.println("semiList: " + name);
            listLat.add(name);
        }
        for (int i = 0; i < listLat.size(); i++) {
            String j = listLat.get(i);
            String[] listLong = j.split(",");
            if (listLong.length == 5) {
                DevicesListResponse userInfo = new DevicesListResponse();

                String stringLongitude = listLong[0];
                String stringLatitude = listLong[1];
                String stringHeartTime = listLong[2];
                String stringSpeed = listLong[3];
                String stringCourse = listLong[4];
                if (stringLatitude.length() > 0 && stringLatitude.length() > 0
                        && stringHeartTime.length() > 0 && stringSpeed.length() > 0 && stringCourse.length() > 0) {
                    userInfo.setLng(Double.parseDouble(stringLongitude));
                    userInfo.setLat(Double.parseDouble(stringLatitude));
                    userInfo.setHearttime(Long.parseLong(stringHeartTime));
                    userInfo.setSpeed(Integer.valueOf(stringSpeed));
                    userInfo.setCourse(Integer.valueOf(stringCourse));
                    userInfo.setOverspeed(aDeviceOverSpeed);
                    userInfo.setDeviceid(aDeviceId);
                    deviceDetailsFromApi.add(userInfo);
                }
            }
        }
        return deviceDetailsFromApi;
    }

    private void resetStatus() {
        if (carLatLngList != null) {
            carLatLngList.clear();
        }
        resetTimerData();
    }

    private void resetTimerData() {
        latLngPlottedList.clear();
        parkTimeList.clear();
        if (mGoogleMap != null)
            mGoogleMap.clear();
        distanceINKM = 0;
    }

    GoogleMap.OnMarkerClickListener onMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            if (marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
            } else {
                marker.showInfoWindow();
            }
            return true;
        }
    };

    GoogleMap.InfoWindowAdapter infoWindowAdapter = new GoogleMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            String stringPos = marker.getTag().toString();
            int position = Integer.parseInt(stringPos);
            if (position == AppConstants.DEFAULT_VALUE) {
                return null;
            }

            ParkTimeData parkTimeData = parkTimeList.get(position);

            View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_marker_info, null);
            TextView info_title = (TextView) view.findViewById(R.id.info_title);
            TextView info_description = (TextView) view.findViewById(R.id.info_description);

            info_title.setVisibility(View.GONE);

            long parkDifference = Math.abs(parkTimeData.getCurrentHeartTime() - parkTimeData.getPreviousHeartTime());
            StringBuilder stringBuilder = new StringBuilder(getString(R.string.info_stay_duration, Utility.getDurationBreakdown(parkDifference)));
            stringBuilder.append(System.getProperty("line.separator"));
            stringBuilder.append(getString(R.string.info_from_date, Utility.getDateFromLong(parkTimeData.getPreviousHeartTime(), AppConstants.YYYY_MM_DD_HH_MM_SS_SLASH)));
            stringBuilder.append(System.getProperty("line.separator"));
            stringBuilder.append(getString(R.string.info_to_date, Utility.getDateFromLong(parkTimeData.getCurrentHeartTime(), AppConstants.YYYY_MM_DD_HH_MM_SS_SLASH)));
            info_description.setText(stringBuilder.toString());
            return view;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

    };
}