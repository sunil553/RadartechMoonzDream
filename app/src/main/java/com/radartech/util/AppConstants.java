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

package com.radartech.util;

public interface AppConstants {
	String YYYY_MM_DD_HH_MM_SS_SLASH = "yyyy/MM/dd HH:mm:ss";
	String YYYY_MM_DD_HH_MM_SS_DASH = "yyyy-MM-dd HH:mm:ss";
	String YYYY_MM_DD= "yyyy-MM-dd";
	String HH_mm_ss= "HH:mm:ss";

	String START_DATE_FORMAT = "EEE MMM dd 00:00:00 z yyyy";
	String END_DATE_FORMAT = "EEE MMM dd 23:59:00 z yyyy";
	String CURRENT_DATE_FORMAT = "EEE MMM dd HH:mm:ss z yyyy";
	String EEE_MMM_dd_yyyy = "EEE MMM dd yyyy";

	String CHARACTER_SET_ISO_8859_1 = "iso-8859-1";

	public static final int CONNECTION_TIMEOUT = 30000;
	/*for displaying logs*/
	public static final boolean isShowLog = true;


	int DEVICE_LIST_REFRESH_TIME = 5 * 1000;
	String ERROR_DEVICE_STATUS = "Custom error message contact shiva";
	String MAP_TYPE = "GOOGLE";

	int DEFAULT_GOOGLE_MAP_ZOOM_LEVEL = 16;
	int DEFAULT_MINIMUM_GOOGLE_MAP_ZOOM_LEVEL = 4;
	int GEO_GOOGLE_MAP_ZOOM_LEVEL = 13;

	int DEFAULT_GOOGLE_MAP_LINE_WIDTH = 8;
	int DEFAULT_VALUE = Integer.MIN_VALUE;



	int DEFAULT_MAX_RADIUS = 4800;
	int DEFAULT_MIN_RADIUS = 100;
	int DEFAULT_MIN_ADD_RADIUS = 200;

	int INDEX_NORMAL_SPEED = 2;
	int PARK_TIME_IN_MINUTES = 3;

	interface PreferenceConstants{
		String PREF_COOKIES = "token";

		int PREF_SUV = 5;
		int PREF_CYCLE = 4;
		int PREF_LORRY = 3;
		int PREF_BUS = 2;
		int PREF_BIKE = 1;
		int PREF_CAR = 0;

		String DEVICE_OVER_SPEED = "_" + "OVERSPEED";
		String  PARK_TIME = "_" + "PARKTIME";
		String PREF_REMEMBER ="PREF_REMEMBER";
		String PREF_AUTO_LOGIN ="AUTOLOGIN";
		String PREF_USER_NAME ="USERNAME";
		String PREF_PASSWORD ="PREF_PASSWORD";
		int  INVALIDCREDENTIALS = 20001;

	}

	interface InfoConstants{
		int ARROW_VISIBLE = 0;
		int ARROW_INVISIBLE = 1;
	}

	interface FragmentConstants{
		String FRAGENT_TYPE = "FRAGENT_TYPE";
		int FRAGMENT_ALARM_DETAIL = 1;
		int FRAGMENT_MONITOR = 2;

		int CAPTURE_GALLERY_ACTIVITY_REQUEST_CODE = 22;
		int CAPTURE_CAMERA_ACTIVITY_REQUEST_CODE = 11;

	}

	interface AlarmConstants{
		int ALARM_GEO_FENCE_IN = 5;
		int ALARM_GEO_FENCE_OUT_ALARM = 6;
		int ALARM_HANDLE_STATUS = 0;
	}
	interface SpeedConstants{
		int SPEED_VERY_SLOW = 2000;
		int SPEED_SLOW = 1000;
		int SPEED_NORMAL = 800;
		int SPEED_FAST = 500;
		int SPEED_VERY_FAST = 300;
	}

	interface IntentConstants{
		String LAT_LNG_ADDRESS = "address";
		String ALARM_TYPE = "alarm_type";
		String ALARM_TYPE_UNREAD_ALARM = "unread_alarm";
		String ALARM_TYPE_ALL_ALARM = "all_alarm";
		String LATITUDE = "LAT";
		String LONGITUDE = "LONG";
		String DEVICE_ID = "DEVICEID";
		String ALARM_TIME = "ALARMTIME";
		String GPS_TIME = "GPSTIME";
		String DEVICE_NAME = "DEVICE_NAME";
		String DEVICE_OVER_SPEED = "DEVICE_OVER_SPEED";
		String PREFIX_IMAGE ="IMAGE";
		String UNDER_SCORE ="_";

		String CAR_NAME ="CAR_NAME";
		String CAR_NUMB ="CAR_NUMB";
		String CAR_ID ="CAR_ID";



		String ACC_ON ="ON";
		String ACC_OFF ="OFF";

		String GEOFENCE="GEOFENCE";
		String EFENCEID="EFENCEID";
		String EFENCENAME="EFENCENAME";
		String SHAPETYPE="SHAPETYPE";

		String ALARMTYPE="ALARMTYPE";
		String PHONENUMBER="PHONENUMBER";
		String ISOPEN="ISOPEN";
		String UPDATE="UPDATE";

		int Geofence_TYPE_ZERO = 0;
		int Geofence_TYPE_ONE = 1;
		int Geofence_TYPE_TWO = 2;
		int Geofence_TYPE_THREE = 3;

		int SPEEDOMETER_MAX_SPEED = 150;
		int SPEEDOMETER_MINOR_TICK_STEP = 1;
		int SPEEDOMETER_MAJOR_TICK_STEP = 15;

		int SPEEDOMETER_MIN_SPEED = 10;
		int SPEEDOMETER_AVG_SPEED = 70;
		int SPEEDOMETER_ABOVE_AVG_SPEED = 90;

	}

	interface StatusConstants{
		String LOGGED_OFF = "Logged Off";
		String OFFLINE = "Offline";
		String STATIC = "Static";
		String MOVING = "Moving";
		String HIGH_SPEED = "HighSpeed";
		String OVER_SPEED = "OverSpeed";

		String ACC_OFF = "Off";
		String ACC_ON = "On";

		int ORANGE_PERCENTAGE = 10;

		String ACC_BINARY_NUMBER = "1010";
		int MAX_DIFF_OFFLINE = 25;
		int SPEED_BUFFER = 10;

	}

	interface UserPersonalFields{

		String person_id="id";

		String person_customertype="customertype";

		String person_phone="phone";

		String person_address="address";

		String person_email="email";

		String person_name="name";

		String person_account="account";

		String person_pid="pid";

		String person_type="type";

		String person_contact="contact";

		String person_telephone="telephone";

	}
}
