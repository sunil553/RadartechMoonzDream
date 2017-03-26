package com.radartech;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;


public class RadartechApplication extends Application {
    private static RadartechApplication sInstance;

    public static RadartechApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;



        JPushInterface.setDebugMode(false); 	//Set up the log,Close the log when publishing
        JPushInterface.init(this);     		//initialization JPush
    }
}
