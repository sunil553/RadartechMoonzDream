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

/**
 * Created by moonzdream on 9/10/2016.
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.radartech.model.version.VersionNumberResponse;
import com.radartech.network.RadarTechService;
import com.radartech.sw.R;
import com.radartech.util.UiUtils;
import com.radartech.util.Utility;

import cn.jpush.android.api.JPushInterface;
import jpushdemo.ExampleUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static jpushdemo.MainActivity.isForeground;

/**
 * SplashScreenActivity to implement splash screen.
 */
public class SplashScreenActivity extends BaseActivity
{
    private Handler mHandler;

    public void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_splashscreen);

        mHandler = new Handler();

        init();
//        registerMessageReceiver();  // used for receive msg

        callVersionNumberAPI("protrack-android");
    }

    private void callVersionNumberAPI(String versionNum) {

        Observable<VersionNumberResponse> loginObservable = RadarTechService.getInstance().getAPIService()
                .getVersionNumer(versionNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<VersionNumberResponse>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(SplashScreenActivity.this, e);
            }
            @Override
            public void onNext(VersionNumberResponse response) {
                UiUtils.dismissProgress();

                Log.i("shiva", response.getRecord().getDownloadurl() + "");
                //TODO have to check current version and api version
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UiUtils.launchActivity(SplashScreenActivity.this, LoginActivity.class, null, true);
                    }
                }, getResources().getInteger(R.integer.splash_delay));

                /*if (response.getStatus().isSuccess()) {
                    //saving firstname, lastname
                    UserData data = response.getData();
                    if (data != null) {
                        UiUtils.saveUserData(getActivity(), data);
                    }
                    UiUtils.launchActivity(getActivity(), HomeActivity.class, null, true);

                    RestoreDbHelper.getDbInstance().insertUserData(data);

                } else {
                    UiUtils.showToast(getActivity(), response.getStatus().getMessage().toString());
                }*/
            }
        });


        addSubscription(subscribe);
    }

    // initialization JPush。if you have already initialized, but did not log on successfully, the implementation of re-login.
    private void init(){
        JPushInterface.init(getApplicationContext());
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mMessageReceiver);
        mHandler.removeCallbacksAndMessages(null);

        if (subscriptions != null) {
            for (Subscription subscription : subscriptions) {
                subscription.unsubscribe();
            }
        }
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }
}

