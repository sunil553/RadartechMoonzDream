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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.radartech.adapter.AlarmListAdapter;
import com.radartech.model.alarm.AlarmModel;
import com.radartech.model.alarm.BaseAlarmResponse;
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

/**
 * UnreadAlaramActivity uses to show all unread alarm
 */
public class AlarmListActivity extends BaseActivity {


    @BindView(R.id.lv_alarmlist)
    ListView mAlarmListView;
    @BindView(R.id.ib_clear_alarmlist)
    Button clearButton;

    @BindView(R.id.tv_list_status)
    TextView mStatusAlarm;

    private List<AlarmModel> alarmModelArrayList = new ArrayList<>();
    private AlarmListAdapter mAlarmListAdapter;
    private boolean isUnreadAlarm = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alaram_list);
        mUnbinder = ButterKnife.bind(this);
        intViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        intViews();
    }

    private void intViews() {

        Bundle alarmTypeBundle = getIntent().getExtras();
        String alarmType = alarmTypeBundle.getString(AppConstants.IntentConstants.ALARM_TYPE);
        if (alarmType.equals(AppConstants.IntentConstants.ALARM_TYPE_UNREAD_ALARM)) {
            titleTv.setText(getString(R.string.title_unread_alarm));
            isUnreadAlarm = true;
            clearButton.setVisibility(View.VISIBLE);
        } else if (alarmType.equals(AppConstants.IntentConstants.ALARM_TYPE_ALL_ALARM)) {
            titleTv.setText(getString(R.string.title_all_alarm));
            isUnreadAlarm = false;
            clearButton.setVisibility(View.GONE);
        }

        mAlarmListAdapter = new AlarmListAdapter(getApplicationContext(), alarmModelArrayList);
        mAlarmListView.setAdapter(mAlarmListAdapter);


   /*     if (alarmModelArrayList.size()>0){
            clearButton.setVisibility(View.VISIBLE);

        }else {
            mStatusAlarm.setVisibility(View.VISIBLE);
            clearButton.setVisibility(View.INVISIBLE);
        }*/
        mAlarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlarmModel alarmModel = alarmModelArrayList.get(position);

                if (Utility.isNetworkAvailable(getApplicationContext())) {
                    callHandleAlarmAPI(String.valueOf(alarmModel.getAlarmid()));
                } else {
                    Utility.showToastMessage(getApplicationContext(), getString(R.string.error_network));
                }

                Bundle alarmBundle = new Bundle();
                alarmBundle.putInt(AppConstants.IntentConstants.DEVICE_ID, alarmModel.getDeviceid());
                alarmBundle.putString(AppConstants.IntentConstants.DEVICE_NAME, alarmModel.getDeviceName());
                alarmBundle.putString(AppConstants.IntentConstants.LATITUDE, String.valueOf(alarmModel.getLat()));
                alarmBundle.putString(AppConstants.IntentConstants.LONGITUDE, String.valueOf(alarmModel.getLng()));
                alarmBundle.putInt(AppConstants.IntentConstants.DEVICE_ID, alarmModel.getDeviceid());
                alarmBundle.putInt(AppConstants.IntentConstants.ALARM_TYPE, alarmModel.getAlarmType());
                alarmBundle.putLong(AppConstants.IntentConstants.ALARM_TIME, alarmModel.getReceiveTime());
                alarmBundle.putLong(AppConstants.IntentConstants.GPS_TIME, alarmModel.getGpstime());
                alarmBundle.putInt(AppConstants.FragmentConstants.FRAGENT_TYPE, AppConstants.FragmentConstants.FRAGMENT_ALARM_DETAIL);

                UiUtils.launchActivity(AlarmListActivity.this, NestedGoogleMapActivity.class, alarmBundle, false);
            }
        });

        if (Utility.isNetworkAvailable(getApplicationContext())) {
            callAlarmListAPI();
        } else {
            Utility.showToastMessage(getApplicationContext(), getString(R.string.error_network));
        }

    }

    @OnClick(R.id.ib_clear_alarmlist)
    public void onClickAllAlarm() {

        //TODO hvae to update constants
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AlarmListActivity.this);
        alertDialog.setTitle("Confirm Delete...");
        alertDialog.setMessage("Are you sure to clear all unread alarms?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                callClearAllAlarmAPI();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void callAlarmListAPI() {

        UiUtils.displayProgress(this, "");
        Observable<BaseAlarmResponse> loginObservable;

        if (isUnreadAlarm) {
            loginObservable = RadarTechService.getInstance().getAPIService()
                    .unreadAlarmListAPI(SharedPreferenceUtils.readString(AppConstants.PreferenceConstants.PREF_COOKIES))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            loginObservable = RadarTechService.getInstance().getAPIService()
                    .alarmListAPI(SharedPreferenceUtils.readString(AppConstants.PreferenceConstants.PREF_COOKIES), "0", "100", AppConstants.MAP_TYPE)//based on documentation hardcoded
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }


        Subscription subscribe = loginObservable.subscribe(new Observer<BaseAlarmResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(AlarmListActivity.this, e);
            }

            @Override
            public void onNext(BaseAlarmResponse response) {
                Log.i("shiva", "error :" + response.getErrorcode() + "");

                if (response.getErrorcode() == 0) {

                    alarmModelArrayList = response.getRecords();
                    mAlarmListAdapter.setAlarmList(alarmModelArrayList);
                    if (alarmModelArrayList.size() > 0 && isUnreadAlarm) {
                        clearButton.setVisibility(View.VISIBLE);
                    } else if (alarmModelArrayList.size() == 0){
                        mStatusAlarm.setVisibility(View.VISIBLE);
                        clearButton.setVisibility(View.INVISIBLE);
                    }

                    Log.i("shiva", "alarmModelArrayList  :" + alarmModelArrayList.size() + "");

                } else {
                    UiUtils.showToast(AlarmListActivity.this, response.getErrorcode().toString());
                }
                UiUtils.dismissProgress();
            }
        });

        addSubscription(subscribe);
    }

    private void callClearAllAlarmAPI() {

        Observable<Object> loginObservable = RadarTechService.getInstance().getAPIService()
                .clearAllAlarmAPI(SharedPreferenceUtils.readString(AppConstants.PreferenceConstants.PREF_COOKIES))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(AlarmListActivity.this, e);
            }

            @Override
            public void onNext(Object response) {

                try {
                    JSONObject jsono = new JSONObject(response.toString());
                    int errorcode = jsono.getInt("errorcode");
                    if (errorcode == 0) {
                        alarmModelArrayList.clear();
                        mAlarmListAdapter.notifyDataSetChanged();
                        Utility.showLogMessage("callClearAllAlarmAPI data", "datadata" + "SUCCESSFULLY READ");
                    } else {
                        UiUtils.logMessage("callClearAllAlarmAPI unsuccessfully read");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        addSubscription(subscribe);
    }

    private void callHandleAlarmAPI(final String alarmId) {

        Observable<Object> loginObservable = RadarTechService.getInstance().getAPIService()
                .handleAlarmAPI(SharedPreferenceUtils.readString(AppConstants.PreferenceConstants.PREF_COOKIES), alarmId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(AlarmListActivity.this, e);
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
