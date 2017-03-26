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


package com.radartech.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.radartech.model.track.BaseTrackResponse;
import com.radartech.model.track.TrackResponse;
import com.radartech.network.RadarTechService;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;
import com.radartech.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * GeofenceNewActivity To implement to create right_arrow Geofence.
 */
public class GeofenceNewActivity extends BaseActivity implements AppConstants {



    @BindView(R.id.ib_geofence_back)
    ImageButton mGeoFenceButton;

    @BindView(R.id.tv_fence_radius)
    TextView tv_fence_radius;

    @BindView(R.id.tv_geofence_title)
    TextView tv_geofence_title;

    @BindView(R.id.btn_set_circle_fence)
    Button mSaveBtn;

    @BindView(R.id.btn_set_polygon_fence)
    Button mSavePolygonBtn;

    @BindView(R.id.btn_reset_polygon_fence)
    Button mResetPolygon;

    @BindView(R.id.ib_geofence_polygon)
    ImageButton impolugon;

    @BindView(R.id.ib_geofence_circle)
    ImageButton imcircle;

    @BindView(R.id.ib_reduce_radius)
    ImageButton imreduce;

    @BindView(R.id.ib_increase_radius)
    ImageButton imincrease;

    private LinearLayout polyGeoFence;
    private RelativeLayout circleGeoFence;
    private Circle mcircle, mRadiusCircle;
    private SeekBar seekbar;
    private CircleOptions circleCameraOptions;


    private SupportMapFragment fm;
    private Circle marker = null;
    private MarkerOptions markerOptions;
    private GoogleMap googleMap;
    private CircleOptions circleOptions;
    private Boolean circle = true;
    private Boolean polygon  = false;
    private ArrayList<LatLng> arrayPoints;
    private ArrayList<LatLng> latLongPointsPolygon;

    private Polygon p;
    private int radius = 200;
    private String aDeviceGeofence, aDeviceLat, aDeviceLong, aDeviceRadius, aDeviceEfenceName, aDevicePhoneNumber;
    private int aDeviceShapeType,aDeviceAlarmType,aDeviceIsOpen,aUpdate, aDeviceEfenceId;
    private double finalLat;
    private double finalLong;
    int count =0;

    private double deviceLat;
    private double deviceLng;

    private TrackResponse devicesItemResponses = null;

    private String aDeviceShapeParam;
    RelativeLayout mSeekBarLayOut;
    private StringBuffer sBuffer;
    private Polygon p1;
    private int aDeviceid;
    private MarkerOptions currentLocationMarker;
    private float GOOGLE_MAP_ZOOM_LEVEL = 13f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);

        mUnbinder = ButterKnife.bind(this);
        initViews();
        callTrackAPI();
        mGeoFenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSaveGeoFence();
            }
        });
        mSavePolygonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialogSaveGeoFence();
                if (polygon){
                    if (arrayPoints.size()<3){
                        Toast.makeText(getApplicationContext(),"Polygon Geofence's point number must be more than 2 points",Toast.LENGTH_LONG).show();
                    }else {
                        dialogSaveGeoFence();

                    }
                }else {
                    dialogSaveGeoFence();

                }

            }
        });
        mResetPolygon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialogSaveGeoFence();
                arrayPoints.clear();
                googleMap.clear();

            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = progress * 100;
                radius = this.progress;
                Utility.showLogMessage("radius seekONp", String.valueOf(radius));
                GOOGLE_MAP_ZOOM_LEVEL = googleMap.getCameraPosition().zoom;
                loadMap();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_fence_radius.setText("Covered: " + progress + "/" + seekBar.getMax() * 100);
                radius = progress;

                Utility.showLogMessage("radius stop", String.valueOf(radius));
                GOOGLE_MAP_ZOOM_LEVEL = googleMap.getCameraPosition().zoom;
                loadMap();

            }
        });
        imincrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radius < DEFAULT_MAX_RADIUS) {
                    radius = radius + DEFAULT_MIN_RADIUS;
                    seekbar.setProgress(radius / DEFAULT_MIN_RADIUS);
                    Utility.showLogMessage("radius seekONINC", String.valueOf(radius));
                    tv_fence_radius.setText(radius+"Meters");
                    GOOGLE_MAP_ZOOM_LEVEL = googleMap.getCameraPosition().zoom;
                    loadMap();

                }
            }
        });

        imreduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radius > DEFAULT_MIN_ADD_RADIUS) {
                    radius = radius - DEFAULT_MIN_RADIUS;
                    seekbar.setProgress(radius / DEFAULT_MIN_RADIUS);
                    Utility.showLogMessage("radius seekONDEC", String.valueOf(radius));
                    tv_fence_radius.setText(radius+"Meters");
                    GOOGLE_MAP_ZOOM_LEVEL = googleMap.getCameraPosition().zoom;
                    loadMap();
                } else {
                    radius = DEFAULT_MIN_ADD_RADIUS;
                    tv_fence_radius.setText(radius+"Meters");
                    loadMap();
                }
            }
        });

        impolugon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                circle = false;
                polygon = true;
                try {
                    if (mRadiusCircle != null)
                        mRadiusCircle.setRadius(radius);
                } catch (NullPointerException e) {

                }
                makeVisibleGeofence();

            }
        });

        imcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circle = true;
                polygon = false;
                makeVisibleGeofence();
            }
        });


    }

    private void initViews() {
        circleCameraOptions = new CircleOptions();
        sBuffer = new StringBuffer();
        latLongPointsPolygon = new ArrayList<LatLng>();
        circleGeoFence = (RelativeLayout) findViewById(R.id.choose_circle_fence);
        polyGeoFence = (LinearLayout) findViewById(R.id.choose_polygon_fence);
        seekbar = (SeekBar) findViewById(R.id.sbar_fence_radius);
        fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_monitor);
        mSeekBarLayOut = (RelativeLayout) findViewById(R.id.rl_seekbar);
        markerOptions = new MarkerOptions();
        currentLocationMarker = new MarkerOptions();
        arrayPoints = new ArrayList<LatLng>();



        try {
            Bundle bundle = getIntent().getExtras();
            aUpdate = bundle.getInt(IntentConstants.UPDATE);
            aDeviceid = bundle.getInt(IntentConstants.DEVICE_ID);
            // if alreaady CIRCLE or POLYGON exist this methd is called
            if (aUpdate == IntentConstants.Geofence_TYPE_ONE) {
                aDeviceShapeType = bundle.getInt(IntentConstants.SHAPETYPE);
                aDeviceGeofence = bundle.getString(IntentConstants.GEOFENCE);
                // If shapetype is Geofence_TYPE_THREE this is polygon
                if (aDeviceShapeType == IntentConstants.Geofence_TYPE_THREE) {
                    String str = aDeviceGeofence;
                    ArrayList aList = new ArrayList(Arrays.asList(str.split(";")));
                    for (int i = 0; i < aList.size(); i++) {
                        String splitLat = aList.get(i).toString();
                        ArrayList aList1 = new ArrayList(Arrays.asList(splitLat.split(",")));
                        String longi = aList1.get(0).toString();
                        String lat = aList1.get(1).toString();
                        latLongPointsPolygon.add(new LatLng(Double.valueOf(lat), Double.valueOf(longi)));
                        System.out.println(" -->" + aList.get(i));
                    }
                    circle = false;
                    polygon = true;

                } else {
                    //  this is circle

                    String[] geoFencePointsArray = aDeviceGeofence.split(",");
                    String mLat = geoFencePointsArray[1];
                    String mRadius = geoFencePointsArray[2];
                    String mLongi = geoFencePointsArray[0];
                    Utility.showLogMessage("Lat and Long", mLat + mRadius + mLongi);
                    aDeviceLat = mLat;
                    aDeviceLong = mLongi;
                    aDeviceRadius = mRadius;
                    circle = true;
                    polygon = false;
                }
                aDeviceEfenceId = bundle.getInt(IntentConstants.EFENCEID);
                aDeviceEfenceName = bundle.getString(IntentConstants.EFENCENAME);
                tv_geofence_title.setText(aDeviceEfenceName);
                aDeviceAlarmType = bundle.getInt(IntentConstants.ALARMTYPE);
                aDevicePhoneNumber = bundle.getString(IntentConstants.PHONENUMBER);
                aDeviceIsOpen = bundle.getInt(IntentConstants.ISOPEN);
                finalLat = Double.parseDouble(aDeviceLat);
                finalLong = Double.parseDouble(aDeviceLong);

            } else {
                //HERE WE ARE GETING CURRENT LOCATION OF THE DEVICE.
                deviceLat = devicesItemResponses.getLat();
                deviceLng = devicesItemResponses.getLng();

            }

        } catch (NullPointerException e) {

        }
//        VISIBLE BUTTONS FOE SAVE FOR CIRCLE AND POLYGON
        makeVisibleGeofence();

        seekbar.getMax();


    }

    private void callTrackAPI() {
        Observable<BaseTrackResponse> loginObservable = RadarTechService.getInstance().getAPIService()
                .trackAPI(SharedPreferenceUtils.readString(PreferenceConstants.PREF_COOKIES), String.valueOf(aDeviceid), AppConstants.MAP_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<BaseTrackResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(getApplicationContext(), e);
            }

            @Override
            public void onNext(BaseTrackResponse response) {
                UiUtils.logMessage("shiva : BaseTrackResponse");

                if (response.getErrorcode() == 0) {
                    //load map based on response
                    devicesItemResponses = response.getRecord();
                    initialiseMap();

                } else {
                    UiUtils.showToast(getApplicationContext(), response.getErrorcode().toString());
                }
                UiUtils.dismissProgress();
            }
        });

        addSubscription(subscribe);
//        drawCenterPointCircle();
    }



    private void initialiseMap() {
        fm.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                Object localObject = googleMap.getUiSettings();
                ((UiSettings) localObject).setZoomControlsEnabled(true);
                GeofenceNewActivity.this.googleMap = googleMap;
                GeofenceNewActivity.this.googleMap.clear();
                loadMap();
                try {
//                    drawCurrentLocationPoint(new LatLng(Double.parseDouble(aDeviceLat), Double.parseDouble(aDeviceLong)), googleMap);

                    markerOptions.position(new LatLng(devicesItemResponses.getLat(), devicesItemResponses.getLng()));
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.car_position));

                } catch (NullPointerException e) {

                }
                circleOptions = new CircleOptions();

                if (circle) {
                    try {
                        drawCurrentLocationPoint(new LatLng(Double.parseDouble(aDeviceLat), Double.parseDouble(aDeviceLong)), googleMap);
                    } catch (NullPointerException e) {

                    }

                    googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                        @Override
                        public void onCameraChange(CameraPosition cameraPosition) {
                            if (mRadiusCircle != null) {
                                mRadiusCircle.remove();
                            }
                            if (mcircle != null) {
                                mcircle.remove();
                            }
                            drawCircleCamera(cameraPosition.target, googleMap);

                        }
                    });

                }
                if (polygon) {
                    googleMap.clear();
                    arrayPoints.clear();
                    if (aDeviceShapeType == IntentConstants.Geofence_TYPE_THREE) {
                        PolygonOptions polygonOptions = new PolygonOptions();
                        polygonOptions.addAll(latLongPointsPolygon);
                        polygonOptions.strokeColor(Color.BLUE);
                        polygonOptions.strokeWidth(3);
                        p1 = googleMap.addPolygon(polygonOptions);

                        if (latLongPointsPolygon.size()>0){
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLongPointsPolygon.get(0), GOOGLE_MAP_ZOOM_LEVEL));
                        }
                    }
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                        @Override
                        public void onMapClick(LatLng latLng) {

                            circleOptions = null;
                            if (polygon) {
                                if (mcircle != null) {
                                    mcircle.remove();
                                }
                                markerOptions.position(latLng);
                                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.car_position));
//                                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                                googleMap.addMarker(markerOptions);
                                arrayPoints.add(new LatLng(latLng.latitude, latLng.longitude));
                                for (int i = 0; i < arrayPoints.size(); i++) {
                                    LatLng s = new LatLng(arrayPoints.get(i).latitude, arrayPoints.get(i).longitude);
                                    String lat = String.valueOf(s.latitude);
                                    String longi = String.valueOf(s.longitude);
                                    sBuffer.append(longi);
                                    sBuffer.append(",");
                                    sBuffer.append(lat);
                                    sBuffer.append(";");
                                    System.out.println(sBuffer);

                                    Utility.showLogMessage("latand long", sBuffer.toString());

                                }
                                Utility.showLogMessage("arrayPoints", arrayPoints.toString());
                                if (arrayPoints.size() < 2) {/*
                                    CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_GOOGLE_MAP_ZOOM_LEVEL_GEOFENCE);
                                    googleMap.animateCamera(yourLocation);*/

                                }
                                if (arrayPoints.size() > 2) {
                                    p.remove();
                                }
                                PolygonOptions polygonOptions = new PolygonOptions();
                                polygonOptions.addAll(arrayPoints);
                                polygonOptions.strokeColor(Color.BLUE);
                                polygonOptions.strokeWidth(3);
                                p = googleMap.addPolygon(polygonOptions);
                                googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                                    @Override
                                    public void onCameraChange(CameraPosition cameraPosition) {


                                        if (marker != null) {
                                            marker.remove();
                                        }

                                    }
                                });

                            }
                        }

                    });
                }

            }


        });
    }

    private void dialogSaveGeoFence() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_fence_info, null);
        final EditText etUsername = (EditText) alertLayout.findViewById(R.id.et_efencename);
        final EditText etPhoneNumber = (EditText) alertLayout.findViewById(R.id.et_phonenumber);
        final RadioGroup radioGroup = (RadioGroup) alertLayout.findViewById(R.id.rdog_alarmtype);
        if (aUpdate == IntentConstants.Geofence_TYPE_ONE) {
            etPhoneNumber.setText(aDevicePhoneNumber);
            etUsername.setText(aDeviceEfenceName);
            if (aDeviceAlarmType ==  IntentConstants.Geofence_TYPE_ONE){
                radioGroup.check(R.id.radio_in);
            }else if (aDeviceAlarmType == IntentConstants.Geofence_TYPE_ZERO ){
                radioGroup.check(R.id.radio_out);
            }
            else if (aDeviceAlarmType == IntentConstants.Geofence_TYPE_TWO ){
                radioGroup.check(R.id.radio_inout);
            }



        }
        AlertDialog.Builder alert = new AlertDialog.Builder(GeofenceNewActivity.this);
        alert.setTitle("Please Input Geo fence Information");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(GeofenceNewActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (aUpdate == IntentConstants.Geofence_TYPE_ONE) {

                    if (aDeviceShapeType == IntentConstants.Geofence_TYPE_TWO) {
                        aDeviceLat = String.valueOf(finalLat);
                        aDeviceLong = String.valueOf(finalLong);
                        aDeviceRadius = String.valueOf(radius);
                        aDeviceEfenceName = etUsername.getText().toString();
                        aDeviceShapeParam = aDeviceLong + "," + aDeviceLat + "," + aDeviceRadius;
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        if (selectedId == R.id.radio_in) {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_ONE;
                        } else if (selectedId == R.id.radio_out) {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_ZERO;
                        } else {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_TWO;
                        }
                        if (circle) {
                            aDeviceShapeType = IntentConstants.Geofence_TYPE_TWO;
                        } else {
                            aDeviceShapeType = IntentConstants.Geofence_TYPE_THREE;

                        }
                        aDevicePhoneNumber = etPhoneNumber.getText().toString();
                        aDeviceIsOpen = 1;
                        callHandledGeoEditFenceAPI();

                    } else {
                        aDeviceEfenceName = etUsername.getText().toString();
                        aDeviceShapeParam = sBuffer.toString();
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        if (selectedId == R.id.radio_in) {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_ONE;
                        } else if (selectedId == R.id.radio_out) {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_ZERO;
                        } else {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_TWO;
                        }
                        aDeviceShapeType = IntentConstants.Geofence_TYPE_THREE;
                        aDevicePhoneNumber = etPhoneNumber.getText().toString();
                        aDeviceIsOpen = IntentConstants.Geofence_TYPE_ONE;
                        callHandledGeoEditFenceAPI();
                    }

                } else {

                    if (circle) {
                        aDeviceLat = String.valueOf(finalLat);
                        aDeviceLong = String.valueOf(finalLong);
                        aDeviceRadius = String.valueOf(radius);
                        aDeviceEfenceName = etUsername.getText().toString();
                        if (aDeviceEfenceName.equals("")){
                            Toast.makeText(getApplicationContext(),"Name cannot be empty",Toast.LENGTH_LONG).show();
                            return;
                        }

                        aDeviceShapeParam = aDeviceLong + "," + aDeviceLat + "," + aDeviceRadius;
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        if (selectedId == R.id.radio_in) {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_ONE;
                        } else if (selectedId == R.id.radio_out) {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_ZERO;
                        } else {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_TWO;
                        }
                        if (circle) {
                            aDeviceShapeType = IntentConstants.Geofence_TYPE_TWO;
                        } else {
                            aDeviceShapeType = IntentConstants.Geofence_TYPE_THREE;

                        }

                        aDevicePhoneNumber = etPhoneNumber.getText().toString();
                        aDeviceIsOpen = IntentConstants.Geofence_TYPE_ONE;
                        callHandledGeoFenceAPI();

                    } else if (polygon) {
                        aDeviceEfenceName = etUsername.getText().toString();
                        aDeviceShapeParam = sBuffer.toString();
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        if (selectedId == R.id.radio_in) {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_ONE;
                        } else if (selectedId == R.id.radio_out) {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_ZERO;
                        } else {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_TWO;
                        }
                        aDeviceShapeType = IntentConstants.Geofence_TYPE_THREE;
                        aDevicePhoneNumber = etPhoneNumber.getText().toString();
                        aDeviceIsOpen = IntentConstants.Geofence_TYPE_ONE;
                        callHandledGeoFenceAPI();

                    }

                }

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.radio_in) {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_ONE;
                        } else if (checkedId == R.id.radio_out) {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_TWO;
                        } else {
                            aDeviceAlarmType = IntentConstants.Geofence_TYPE_THREE;
                        }

                    }
                });
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void makeVisibleGeofence() {

        if (circle && !polygon) {
            mSeekBarLayOut.setVisibility(View.VISIBLE);
            circleGeoFence.setVisibility(View.VISIBLE);
            polyGeoFence.setVisibility(View.GONE);
            aDeviceShapeType = IntentConstants.Geofence_TYPE_TWO;
        } else if (polygon && !circle) {
            mSeekBarLayOut.setVisibility(View.INVISIBLE);
            circleGeoFence.setVisibility(View.GONE);
            polyGeoFence.setVisibility(View.VISIBLE);
            aDeviceShapeType = IntentConstants.Geofence_TYPE_THREE;

        }
        loadMap();
    }

    private void loadMap() {
        if (googleMap == null){
            return;
        }
//        GeofenceNewActivity.this.googleMap.clear();

        googleMap.getUiSettings().setZoomControlsEnabled(true);

        try {
            //points received from server that is CURRENT LOCATION of device
            // THIS WILL DRAW CURRENT LOCATION CIRCLE

            drawCurrentLocationPoint(new LatLng(devicesItemResponses.getLat(), devicesItemResponses.getLng()), googleMap);
        } catch (NullPointerException e) {

        }
        circleOptions = new CircleOptions();

        if (circle) {
            try {
                drawCircle2(new LatLng(devicesItemResponses.getLat(), devicesItemResponses.getLng()), googleMap);
            } catch (NullPointerException e) {
            }

            googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    if (mRadiusCircle != null) {
                        mRadiusCircle.remove();
                    }
                    if (mcircle != null) {
                        mcircle.remove();
                    }
                    drawCircleCamera(cameraPosition.target, googleMap);

                }
            });

        }
        if (polygon) {
            googleMap.clear();
            arrayPoints.clear();
            if (latLongPointsPolygon.size()>0){
                if (aDeviceShapeType == IntentConstants.Geofence_TYPE_THREE) {
                    PolygonOptions polygonOptions = new PolygonOptions();
                    polygonOptions.addAll(latLongPointsPolygon);
                    polygonOptions.strokeColor(Color.BLUE);
                    polygonOptions.strokeWidth(3);
                    p1 = googleMap.addPolygon(polygonOptions);

                }
            }

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng latLng) {

                    circleOptions = null;
                    if (polygon) {
                        if (mcircle != null) {
                            mcircle.remove();
                        }
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.car_position));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        googleMap.addMarker(markerOptions);
                        arrayPoints.add(new LatLng(latLng.latitude, latLng.longitude));
                        for (int i = 0; i < arrayPoints.size(); i++) {
                            LatLng s = new LatLng(arrayPoints.get(i).latitude, arrayPoints.get(i).longitude);
                            String lat = String.valueOf(s.latitude);
                            String longi = String.valueOf(s.longitude);
                            sBuffer.append(longi);
                            sBuffer.append(",");
                            sBuffer.append(lat);
                            sBuffer.append(";");
                            System.out.println(sBuffer);

                            Utility.showLogMessage("latand long", sBuffer.toString());

                        }
                        Utility.showLogMessage("arrayPoints", arrayPoints.toString());
                        if (arrayPoints.size() < 2) {

                        }
                        if (arrayPoints.size() > 2) {
                            p.remove();
                        }
                        PolygonOptions polygonOptions = new PolygonOptions();
                        polygonOptions.addAll(arrayPoints);
                        polygonOptions.strokeColor(Color.BLUE);
                        polygonOptions.strokeWidth(3);
                        p = googleMap.addPolygon(polygonOptions);
                        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                            @Override
                            public void onCameraChange(CameraPosition cameraPosition) {


                                if (marker != null) {
                                    marker.remove();
                                }

                            }
                        });

                    }
                }

            });
        }
    }
    // FOR DRAWING PINK COLOR CIRCLE

    private void drawCircleCamera(LatLng point, GoogleMap googleMap) {


        circleCameraOptions = new CircleOptions();
        circleCameraOptions.center(point);
        finalLong = point.longitude;
        finalLat = point.latitude;
        circleCameraOptions.radius(radius);
        circleCameraOptions.strokeColor(Color.BLACK);
        circleCameraOptions.fillColor(0x30ff0000);
        circleCameraOptions.strokeWidth(2);
        mRadiusCircle = googleMap.addCircle(circleCameraOptions);


    }

    private void drawCurrentLocationPoint(LatLng point, GoogleMap googleMap) {

        currentLocationMarker.position(point);
//        markerOptions.title(point.latitude + " : " + point.longitude);
        currentLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.car_position));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, GOOGLE_MAP_ZOOM_LEVEL));
        googleMap.addMarker(currentLocationMarker);
        /*CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(point);
        circleOptions.radius(Double.parseDouble(aDeviceRadius));
        circleOptions.strokeColor(Color.BLACK);
        circleOptions.fillColor(0x30ffff00);
        circleOptions.strokeWidth(4);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, GEO_GOOGLE_MAP_ZOOM_LEVEL));
        googleMap.addCircle(circleOptions);
*/

    }

    private void drawCircle2(LatLng point, GoogleMap googleMap) {
       /* CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(point);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.BLACK);
        circleOptions.fillColor(0x30ff0000);
        circleOptions.strokeWidth(2);
        mcircle = googleMap.addCircle(circleOptions);*/
        markerOptions.position(point);
//        markerOptions.title(point.latitude + " : " + point.longitude);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.car_position));
        if (count==0){
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, GOOGLE_MAP_ZOOM_LEVEL));
            count ++;
        }
        googleMap.addMarker(markerOptions);


    }
    private void callHandledGeoEditFenceAPI() {
        Observable<Object> loginObservable;
        loginObservable = RadarTechService.getInstance().getAPIService()
                .effenceEditGeoFenceAPI(SharedPreferenceUtils.readString(PreferenceConstants.PREF_COOKIES),
                        String.valueOf(aDeviceEfenceId),
                        MAP_TYPE,
                        aDeviceEfenceName,
                        String.valueOf(aDeviceShapeType),
                        aDeviceShapeParam,
                        String.valueOf(aDeviceAlarmType),
                        String.valueOf(aDeviceIsOpen),
                        aDevicePhoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscribe = loginObservable.subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
                Toast.makeText(getApplicationContext(),"Geofence Created ",Toast.LENGTH_SHORT);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(getApplicationContext(), e);
            }

            @Override
            public void onNext(Object response) {

                try {
                    JSONObject jsono = new JSONObject(response.toString());
                    int errorcode = jsono.getInt("errorcode");
                    if (errorcode == 0) {
                        Utility.showLogMessage("data", "datadata" + "SUCCESSFULLY READ");
                    } else {
                        UiUtils.logMessage("unsuccessfully read");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        addSubscription(subscribe);
    }

    private void callHandledGeoFenceAPI() {
        Observable<Object> loginObservable;
        loginObservable = RadarTechService.getInstance().getAPIService()
                .effenceNewGeoFence(SharedPreferenceUtils.readString(PreferenceConstants.PREF_COOKIES),String.valueOf(aDeviceid)
                        ,MAP_TYPE,aDeviceEfenceName,String.valueOf(aDeviceShapeType),aDeviceShapeParam,String.valueOf(aDeviceAlarmType),String.valueOf(aDeviceIsOpen),aDevicePhoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscription subscribe = loginObservable.subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(getApplicationContext(), e);
            }

            @Override
            public void onNext(Object response) {

                try {
                    JSONObject jsono = new JSONObject(response.toString());
                    int errorcode = jsono.getInt("errorcode");
                    if (errorcode == 0) {
                        Utility.showLogMessage("data", "datadata" + "SUCCESSFULLY READ");
                        UiUtils.showToast(GeofenceNewActivity.this, getString(R.string.api_new_geofence_message));
                        finish();
                    } else {
                        UiUtils.logMessage("unsuccessfully read");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        addSubscription(subscribe);
    }

}
