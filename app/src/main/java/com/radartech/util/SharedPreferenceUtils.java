//***************************************************************************************************
//***************************************************************************************************
//      Project name                    		: Magellan Restore
//      Class Name                              : SharedPreferenceUtils
//      Author                                  : PurpleTalk, Inc.
//***************************************************************************************************
//      Description: Manages all shared preferences keys with getter and setter methods.
//***************************************************************************************************
//***************************************************************************************************

package com.radartech.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.radartech.RadartechApplication;


/**
 * Manages all shared preferences keys with getter and setter methods.
 */
public class SharedPreferenceUtils {


    private static String Preference_Name = "RadartechSharedPreferences";

    static SharedPreferences prefs;

    public static void writeString(String key, String value) {
            getSharedPreference().edit().putString(key, value).apply();
    }

    public static String readString(String key) {
            return getSharedPreference().getString(key, "");
    }

    public static void writeBoolean(String key, boolean value) {
            getSharedPreference().edit().putBoolean(key, value).apply();
    }

    public static boolean readBoolean(String key) {
            return getSharedPreference().getBoolean(key, false);
    }

    public static void writeInteger(String key, int value) {
            getSharedPreference().edit().putInt(key, value).apply();
    }

    public static int readInteger(String key) {
            return getSharedPreference().getInt(key, AppConstants.DEFAULT_VALUE);
    }

    public static void clear() {
            getSharedPreference().edit().clear().apply();
    }

    private static SharedPreferences getSharedPreference() {
        if (prefs == null) {
            prefs = RadartechApplication.getInstance().getSharedPreferences(Preference_Name, Context.MODE_PRIVATE);
        }
        return prefs;
    }
}