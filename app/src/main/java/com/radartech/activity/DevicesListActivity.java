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

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.radartech.adapter.DevicesListAdapter;
import com.radartech.model.APIBaseResponse;
import com.radartech.model.devices.list.BaseDevicesList;
import com.radartech.model.devices.list.DevicesListResponse;
import com.radartech.model.login.UserData;
import com.radartech.network.NetworkUtils;
import com.radartech.network.RadarTechService;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.RecyclerViewSpaceItemDecoration;
import com.radartech.callbacks.RecyclerTouchListener;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;
import com.radartech.util.Utility;

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

import static com.radartech.util.AppConstants.PreferenceConstants.INVALIDCREDENTIALS;
import static com.radartech.util.AppConstants.PreferenceConstants.PREF_PASSWORD;
import static com.radartech.util.AppConstants.PreferenceConstants.PREF_USER_NAME;


/**
 * Created by moonz on 9/23/2016.
 */


/**
 * DeviceListActivity to implement device list in this class.
 */
public class DevicesListActivity extends HamburgerMenuActivity {

    @BindView(R.id.tv_unread_alarm_count)
    TextView mUnreadAlarmCount;

    @BindView(R.id.device_list)
    RecyclerView recyclerView;

    private DevicesListAdapter mAdapter;
    private List<DevicesListResponse> devicesListResponses;
    final Handler handler = new Handler();


    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        initViews();
    }

    @Override
    public int setNavigationMenuView() {
        return R.layout.activity_devices_list;
    }

    public Runnable onTimerLiveData = new Runnable() {
        @Override
        public void run() {
            callMyDeviceListAPI();
        }
    };

    private void refreshList() {
        UiUtils.logMessage("device list, timer called");
        handler.postDelayed(onTimerLiveData, AppConstants.DEVICE_LIST_REFRESH_TIME);
    }

    public void setAlarmCount(int record) {
        mUnreadAlarmCount.setText(String.valueOf(record));
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(onTimerLiveData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.removeCallbacks(onTimerLiveData);
        callMyDeviceListAPI();
        callAlarmCountAPI();
    }

    private void initViews() {

        setTitle();

        devicesListResponses = new ArrayList<>();
        mAdapter = new DevicesListAdapter(devicesListResponses,getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecyclerViewSpaceItemDecoration(3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                DevicesListResponse devicesListResponse = devicesListResponses.get(position);
                int deviceid = devicesListResponse.getDeviceid();
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstants.IntentConstants.DEVICE_ID, deviceid);
                bundle.putString(AppConstants.IntentConstants.DEVICE_NAME, devicesListResponse.getDevicename());

                if (SharedPreferenceUtils.readInteger(deviceid+ AppConstants.PreferenceConstants.DEVICE_OVER_SPEED) == AppConstants.DEFAULT_VALUE){
                    SharedPreferenceUtils.writeInteger(deviceid + AppConstants.PreferenceConstants.DEVICE_OVER_SPEED, devicesListResponse.getOverspeed());
                }
                if (SharedPreferenceUtils.readInteger(deviceid+ AppConstants.PreferenceConstants.PARK_TIME) == AppConstants.DEFAULT_VALUE){
                    SharedPreferenceUtils.writeInteger(deviceid + AppConstants.PreferenceConstants.PARK_TIME, AppConstants.PARK_TIME_IN_MINUTES);
                }
                UiUtils.launchActivity(DevicesListActivity.this, DeviceTraceActivity.class, bundle, false);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    @OnClick(R.id.device_list_button_back)
    public void onClickUserInfo(View v) {
//        UiUtils.launchActivity(DevicesListActivity.this, UserInfoActivity.class, null, false);
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.ib_monitor)
    public void onClickMonitor(View v) {
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.FragmentConstants.FRAGENT_TYPE, AppConstants.FragmentConstants.FRAGMENT_MONITOR);
        UiUtils.launchActivity(DevicesListActivity.this, NestedGoogleMapActivity.class, bundle, false);
    }

    @OnClick(R.id.ib_alarm)
    public void onClickAlarm(View v) {
        UiUtils.launchActivity(DevicesListActivity.this, AlarmInfoActivity.class, null, false);
    }

    private void callMyDeviceListAPI() {
        Observable<BaseDevicesList> loginObservable = RadarTechService.getInstance().getAPIService()
                .myDeviceListAPI(SharedPreferenceUtils.readString(AppConstants.PreferenceConstants.PREF_COOKIES))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<BaseDevicesList>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(DevicesListActivity.this, e);
            }

            @Override
            public void onNext(BaseDevicesList response) {
                if (response.getErrorcode() == 0) {
                    refreshList();
                    devicesListResponses = response.getRecords();
                    mAdapter.setDevicesList(devicesListResponses);
                } else {
                    UiUtils.showToast(DevicesListActivity.this, "Device items added");
                    String password = Utility.md5(SharedPreferenceUtils.readString(PREF_PASSWORD));
                    callLoginAPI(SharedPreferenceUtils.readString(PREF_USER_NAME),password);
                }
                UiUtils.dismissProgress();
            }
        });

        addSubscription(subscribe);
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
                    callMyDeviceListAPI();
                    callAlarmCountAPI();
                } else {
                    if (response.getErrorcode().equals(INVALIDCREDENTIALS)){
                    }
                }
            }
        });

        addSubscription(subscribe);
    }



}
