package com.radartech.network;



public class APIConstants {

    public static final String API_APP_VERSION = "AppService?method=latestversion";

    public static final String API_LOGIN = "AppService?method=login";

    public static final String API_MY_DEVICES_LIST = "AppService?method=myDeviceList";

    public static final String API_CUSTOMER_DEVICE_AND_GPSONE = "AppService?method=customerDeviceAndGpsone";

    public static final String API_UNREAD_ALARM_NUMBER = "AppService?method=unreadAlarmNumber";

    public static final String API_UNREAD_ALARM_LIST = "AppService?method=unreadAlarmList";

    public static final String API_CLEAR_ALL_ALARM = "AppService?method=clearallalarm";

    public static final String API_ALARM_LIST = "AppService?method=alarmList";

    public static final String API_HANDLE_ALARM = "AppService?method=handlealarm";

    public static final String API_TRACK = "AppService?method=track";

    public static final String API_PLAYBACK = "AppService?method=playback";

    public static final String API_DEVICEINFO = "AppService?method=deviceInfo";

    public static final String API_DEVICEOVERSPEED = "AppService?method=modifyoverspeed";

    public static final String API_EDIT_DEVICE = "AppService?method=editdevice";



    //geofence Api effence list
    public static final String API_EFENCE_LIST = "AppService?method=efencelist";


    public static final String API_EFENCE_ON_OFF_API = "AppService?method=switchefence";


    public static final String API_EFENCE_DELETE_API = "AppService?method=deleteefence";

    public static final String API_GEOFENCE_NEW_API = "AppService?method=newefence";

    public static final String API_GEOFENCE_EDIT_API = "AppService?method=editefence";
//http://www.radartech.in:80/AppService?method=alarmdetail&alarmid=%s&maptype=google
    public static final String API_PUSH_ALARMID_API = "AppService?method=alarmdetail";

    public static final String API_PASSWORD_CHANGE = "AppService?method=modifypwd";


//    change password api
//    http://www.radartech.in:80/AppService?method=modifypwd&oldpwd=md5_of_old_password&newpwd=md5_of_new_password
}
