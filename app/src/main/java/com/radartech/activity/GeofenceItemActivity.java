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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.radartech.adapter.CustomGeoFenceAdapter;
import com.radartech.callbacks.CustomClickCallBack;
import com.radartech.model.geofence.BaseGeoFence;
import com.radartech.model.geofence.GeoFenceResponce;
import com.radartech.network.RadarTechService;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;
import com.radartech.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class GeofenceItemActivity extends BaseActivity  {


    @BindView(R.id.btn_newgeofence)
    Button mNewGeoFence;
    @BindView(R.id.lv_geofencelist)
    ListView mListView;

    private List<GeoFenceResponce> devicesListResponses;
    private CustomGeoFenceAdapter adapter;
    private int aDeviceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofence);
        mUnbinder = ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

        setTitle();
        mNewGeoFence.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();
        aDeviceId = bundle.getInt(AppConstants.IntentConstants.DEVICE_ID);
        titleTv.setText(bundle.getString(AppConstants.IntentConstants.DEVICE_NAME));

        devicesListResponses = new ArrayList<>();
        adapter = new CustomGeoFenceAdapter(getApplicationContext(), devicesListResponses, customClickCallBack);
        mListView.setAdapter(adapter);



    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utility.isNetworkAvailable(getApplicationContext())) {
            callGeofenceEfenceAPI();
        } else {
            Utility.showToastMessage(getApplicationContext(), "Please Check Your network");
        }
    }

    @OnClick(R.id.btn_newgeofence)
    public void onClickNewGeoFence() {
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.IntentConstants.UPDATE, AppConstants.IntentConstants.Geofence_TYPE_TWO);
        bundle.putInt(AppConstants.IntentConstants.DEVICE_ID, aDeviceId);

        UiUtils.launchActivity(GeofenceItemActivity.this, GeofenceNewActivity.class, bundle, false);

        Toast.makeText(getApplicationContext(), " button is pressed", Toast.LENGTH_SHORT).show();
    }

    private CustomClickCallBack customClickCallBack = new CustomClickCallBack() {
        @Override
        public void onClick(int position) {

            Bundle bundle = new Bundle();
            bundle.putInt(AppConstants.IntentConstants.DEVICE_ID, aDeviceId);
            bundle.putInt(AppConstants.IntentConstants.UPDATE, AppConstants.IntentConstants.Geofence_TYPE_ONE);
            bundle.putString(AppConstants.IntentConstants.GEOFENCE, devicesListResponses.get(position).getShapeparam());
            bundle.putInt(AppConstants.IntentConstants.EFENCEID, devicesListResponses.get(position).getEfenceid());
            bundle.putString(AppConstants.IntentConstants.EFENCENAME, devicesListResponses.get(position).getEfencename());
            bundle.putInt(AppConstants.IntentConstants.SHAPETYPE, devicesListResponses.get(position).getShapetype());
            bundle.putInt(AppConstants.IntentConstants.ALARMTYPE, devicesListResponses.get(position).getAlarmtype());
            bundle.putString(AppConstants.IntentConstants.PHONENUMBER, devicesListResponses.get(position).getPhonenumber());
            bundle.putInt(AppConstants.IntentConstants.ISOPEN, devicesListResponses.get(position).getIsopen());

            UiUtils.launchActivity(GeofenceItemActivity.this, GeofenceNewActivity.class, bundle, false);

        }

        @Override
        public void onLongClick(int position) {
            dialogdeleteGeofenceAPI(devicesListResponses.get(position).getEfenceid());
        }

        @Override
        public void onToggleClick(int position) {
            callHandledEffenceOnOffAPI(devicesListResponses.get(position).getEfenceid());
        }
    };

    private void dialogdeleteGeofenceAPI(final Integer efenceid) {
        AlertDialog.Builder alert = new AlertDialog.Builder(GeofenceItemActivity.this);
        alert.setTitle("Alert!!");
        alert.setMessage("Are you sure to delete record");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                callHandledEffenceDeleteAPI(efenceid);
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void callGeofenceEfenceAPI() {
        Observable<BaseGeoFence> loginObservable = RadarTechService.getInstance().getAPIService()
                .efenceListAPI(SharedPreferenceUtils.readString(AppConstants.PreferenceConstants.PREF_COOKIES), String.valueOf(aDeviceId), AppConstants.MAP_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<BaseGeoFence>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(getApplicationContext(), e);
            }

            @Override
            public void onNext(BaseGeoFence response) {
                if (response.getErrorcode() == 0) {
                    devicesListResponses = response.getRecords();
                    adapter.notifyDataSetChanged();
                    adapter.setGeoFenceList(devicesListResponses);
                } else {
                    UiUtils.showToast(getApplicationContext(), response.getErrorcode().toString());
                }
                UiUtils.dismissProgress();
            }
        });

        addSubscription(subscribe);
    }

    private void callHandledEffenceDeleteAPI(Integer effenceID) {

        Observable<Object> loginObservable = RadarTechService.getInstance().getAPIService()
                .effenceDeleteAPI(SharedPreferenceUtils.readString(AppConstants.PreferenceConstants.PREF_COOKIES), String.valueOf(effenceID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(GeofenceItemActivity.this, e);
            }

            @Override
            public void onNext(Object response) {

                try {
                    JSONObject jsono = new JSONObject(response.toString());
                    int errorcode = jsono.getInt("errorcode");
                    if (errorcode == 0) {
                        Utility.showLogMessage("data", "datadata" + "SUCCESSFULLY READ");
                        callGeofenceEfenceAPI();
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


    private void callHandledEffenceOnOffAPI(int effenceID) {

        Observable<Object> loginObservable = RadarTechService.getInstance().getAPIService()
                .effenceOnOffAPI(SharedPreferenceUtils.readString(AppConstants.PreferenceConstants.PREF_COOKIES), String.valueOf(effenceID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(GeofenceItemActivity.this, e);
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

}
