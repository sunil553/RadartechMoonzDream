package com.radartech.network;

import android.util.Log;

import com.radartech.util.AppConstants;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.util.List;
import java.util.Map;


class MyCookieManager extends CookieManager {

    @Override
    public void put(URI uri, Map<String, List<String>> stringListMap) throws IOException {
        super.put(uri, stringListMap);
        if (stringListMap != null && stringListMap.get("Set-Cookie") != null)
            for (String cookieHeader : stringListMap.get("Set-Cookie")) {
                Log.i("shiva", "cookieHeader :" + cookieHeader);
                if (cookieHeader.contains(AppConstants.PreferenceConstants.PREF_COOKIES)) {
                    String authToken = cookieHeader.substring((AppConstants.PreferenceConstants.PREF_COOKIES + "=").length(), cookieHeader.indexOf(";"));
                    Log.i("shiva", "authToken token :" + authToken);
                    //save your cookie here
//                    Utility.setSharedPrefStringData(ExampleApplication.getInstance(), Constants.PreferenceConstants.PREF_COOKIES, authToken);
                }
            }
    }
}