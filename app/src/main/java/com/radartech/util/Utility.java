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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.radartech.model.devices.list.DevicesListResponse;
import com.radartech.model.track.TrackResponse;
import com.radartech.sw.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class Utility {


    public static String hexToBinary(String Hex) {
        int i = Integer.parseInt(Hex, 16);
        String Bin = Integer.toBinaryString(i);
        return Bin;
    }

    public static float distBetweenLatLngInKM(double lat1, double lng1, double lat2, double lng2) {
        return distBetweenLatLngInMeters(lat1, lng1, lat2, lng2) / 1000;
    }

    public static float distBetweenLatLngInMeters(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);
        return dist;
    }

    public static int[] getTrackingImage(String deviceId) {
        int storedDeviceModel = SharedPreferenceUtils.readInteger(deviceId);

        int[] deviceImages = new int[5];
        if (storedDeviceModel == AppConstants.PreferenceConstants.PREF_BIKE) {
            deviceImages[0] = R.drawable.gray_bike;
            deviceImages[1] = R.drawable.green_bike;
            deviceImages[2] = R.drawable.red_bike;
            deviceImages[3] = R.drawable.offline_bike;
            deviceImages[4] = R.drawable.orange_bike;
        } else if (storedDeviceModel == AppConstants.PreferenceConstants.PREF_BUS) {
            deviceImages[0] = R.drawable.gray_bus;
            deviceImages[1] = R.drawable.green_bus;
            deviceImages[2] = R.drawable.red_bus;
            deviceImages[3] = R.drawable.offline_bus;
            deviceImages[4] = R.drawable.orange_bus;
        } else if (storedDeviceModel == AppConstants.PreferenceConstants.PREF_LORRY) {
            deviceImages[0] = R.drawable.gray_lorry;
            deviceImages[1] = R.drawable.green_lorry;
            deviceImages[2] = R.drawable.red_lorry;
            deviceImages[3] = R.drawable.offline_lorry;
            deviceImages[4] = R.drawable.orange_lorry;
        } else if (storedDeviceModel == AppConstants.PreferenceConstants.PREF_CYCLE) {
            deviceImages[0] = R.drawable.gray_cycle;
            deviceImages[1] = R.drawable.green_cycle;
            deviceImages[2] = R.drawable.red_cycle;
            deviceImages[3] = R.drawable.offline_cycle;
            deviceImages[4] = R.drawable.orange_cycle;
        }else if (storedDeviceModel == AppConstants.PreferenceConstants.PREF_SUV) {
            deviceImages[0] = R.drawable.gray_suv;
            deviceImages[1] = R.drawable.green_suv;
            deviceImages[2] = R.drawable.red_suv;
            deviceImages[3] = R.drawable.offline_suv;
            deviceImages[4] = R.drawable.orange_suv;
        } else {
            deviceImages[0] = R.drawable.gray_car;
            deviceImages[1] = R.drawable.green_car;
            deviceImages[2] = R.drawable.red_car;
            deviceImages[3] = R.drawable.offline_car;
            deviceImages[4] = R.drawable.orange_car;
        }
            return deviceImages;
    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState() == NetworkInfo.State.CONNECTED
                    || connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState() == NetworkInfo.State.CONNECTING) {
                return true;
            } else if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .getState() == NetworkInfo.State.CONNECTED
                    || connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .getState() == NetworkInfo.State.CONNECTING) {

                return true;
            } else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getDurationBreakdown(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder();
        if (days != 0) {
            sb.append(days);
            sb.append("d");
        }
        if (hours != 0) {
            sb.append(hours);
            sb.append("h");
        }
        sb.append(minutes);
        sb.append("m");
        sb.append(seconds);
        sb.append("s");

        return (sb.toString());
    }


    public static String getDeviceId(Context context) {
        String str = null;
        str = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        return str;
    }

    public static String getDeviceId(FragmentActivity activity) {
        String android_id = "";
        if (activity != null) {
            android_id = Secure.getString(activity.getContentResolver(),
                    Secure.ANDROID_ID);
        }
        return android_id;
    }

    public static void showKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

    }

    public static void hideSoftKeyboard(FragmentActivity activity,
                                        EditText editText) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);
    }

    public static void showLogMessage(String logKey, String logValue) {
        if (AppConstants.isShowLog) {
            if (!isValueNullOrEmpty(logKey) && !isValueNullOrEmpty(logValue)) {
                Log.e(logKey, logValue);
            }
        }
    }

    public static void showToastMessage(Context context, String message) {
        try {
            if (context != null) {
                if (isValueNullOrEmpty(message)) {
                    message = getResourcesString(context, R.string.error_msg);
                }

                final Toast toast = Toast.makeText(
                        context.getApplicationContext(), message,
                        Toast.LENGTH_SHORT);
                toast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTodayDate() { //dd-mm-yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    public static String getDeviceStatus(TrackResponse userRecordsModel) {
        long heartTime = userRecordsModel.getHearttime();
        long serverTime = userRecordsModel.getServertime();

        long diffTime = serverTime - heartTime;
        long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diffTime);
        int speed = userRecordsModel.getSpeed();

        int overSpeed = SharedPreferenceUtils.readInteger(userRecordsModel.getDeviceid() + AppConstants.PreferenceConstants.DEVICE_OVER_SPEED);
        return getDeviceStatus(diffMinutes, overSpeed, speed);
    }

    public static String getDeviceStatus(DevicesListResponse userRecordsModel) {
        long heartTime = userRecordsModel.getHearttime();
        long serverTime = userRecordsModel.getServertime();


        long diffTime = serverTime - heartTime;
        long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diffTime);
        int speed = userRecordsModel.getSpeed();
        int overSpeed = SharedPreferenceUtils.readInteger(userRecordsModel.getDeviceid() + AppConstants.PreferenceConstants.DEVICE_OVER_SPEED);

        if (overSpeed == AppConstants.DEFAULT_VALUE) {
        overSpeed = userRecordsModel.getOverspeed();
        }
            return getDeviceStatus(diffMinutes, overSpeed, speed);
    }

    private static String getDeviceStatus(long diffMinutes, int overSpeed, int speed) {

        if (speed < 0) {
            return AppConstants.StatusConstants.LOGGED_OFF;
        } else if (diffMinutes >= AppConstants.StatusConstants.MAX_DIFF_OFFLINE) {
            return AppConstants.StatusConstants.OFFLINE;
        } else if (diffMinutes < AppConstants.StatusConstants.MAX_DIFF_OFFLINE) {
            int marginalOverSpeed = (AppConstants.StatusConstants.SPEED_BUFFER * overSpeed)/100;
            if (speed < 5) {
                return AppConstants.StatusConstants.STATIC;
            } else if (speed < overSpeed - marginalOverSpeed) {
                return AppConstants.StatusConstants.MOVING;
            } else if (speed < overSpeed) {
                return AppConstants.StatusConstants.HIGH_SPEED;
            } else {
                return AppConstants.StatusConstants.OVER_SPEED;
            }
        }
        return AppConstants.ERROR_DEVICE_STATUS;
    }

    public static String getDateFromLong(long milliSeconds, String dateFormat) {
        Date date=new Date(milliSeconds);
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    public static String getDate(Date date) { //yyyy-mm-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task) {
        execute(task, (P[]) null);
    }

    public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task,
                                                                 P... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }

    public static String getURL(String url, LinkedHashMap<String, String> paramMap) {
        StringBuilder sb = new StringBuilder().append(url);
        if (paramMap != null && paramMap.size() > 0) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                sb.append("&").append(entry.getKey()).append("=")
                        .append(entry.getValue());
            }
        }
        return sb.toString();
    }

    public static String getURLWithQuestion(String url, LinkedHashMap<String, String> paramMap) {
        StringBuilder sb = new StringBuilder().append(url);
        boolean first = true;
        if (paramMap != null && paramMap.size() > 0) {
            sb.append("&");
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                if (first) {
                    sb.append(entry.getKey()).append("=")
                            .append(entry.getValue());
                    first = false;
                } else {
                    sb.append("&").append(entry.getKey()).append("=")
                            .append(entry.getValue());
                }

            }
        }
        return sb.toString();
    }

    public static List<NameValuePair> getParams(LinkedHashMap<String, String> paramMap) {
        if (paramMap == null) {
            return null;
        }

        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            paramsList.add(new BasicNameValuePair(entry.getKey(), entry
                    .getValue()));
        }
        return paramsList;
    }


    public static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public static String getAddressFromLocation(final double latitude, final double longitude,
                                                final Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String result = null;
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
                sb.append(address.getLocality()).append("\n");
                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());
                result = sb.toString();
            }
        } catch (IOException e) {
            Log.e("EXCEPTION", "Unable connect to Geocoder", e);
        }
        return result;
    }

    public static void getAddressFromLocation(
            final double latitude, final double longitude, final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> list = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (list != null && list.size() > 0) {
                        Address address = list.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                        }
                        sb.append(address.getLocality()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getCountryName());
                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e("LOCATION", "Impossible to connect to Geocoder", e);
                } finally {
                    Message msg = Message.obtain();
                    msg.setTarget(handler);
                    if (result != null) {
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        msg.setData(bundle);
                    } else
                        msg.what = 0;
                    msg.sendToTarget();
                }
            }
        };
        thread.start();
    }


    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressWarnings("unchecked")
    public static String httpPostRequestToServer(String url, Object paramsList) {
        String result = null;
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, AppConstants.CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, AppConstants.CONNECTION_TIMEOUT);

        HttpClient httpclient = new DefaultHttpClient(httpParams);
        HttpPost httppost = new HttpPost(url);
        showLogMessage("HttpPost URL : ", url);
        InputStream is = null;
        try {
            if (paramsList != null) {
                httppost.setEntity(new UrlEncodedFormEntity(
                        (List<? extends NameValuePair>) paramsList));
            }

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity httpEntity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            Utility.showLogMessage("statusCode", "statusCode" + statusCode);
            if (statusCode == 204) {
                result = null;
            }

            if (statusCode == 200) {
                is = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, AppConstants.CHARACTER_SET_ISO_8859_1), 8);
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    if (line.length() > 0)
                        sb.append(line + "\n");
                }
                result = sb.toString();
                is.close();
                showLogMessage("HttpPost Response : ", result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void navigatePlayStore(Context context, String packageName) {
        if (context != null) {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("market://details?id="
                                + packageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                context.startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id="
                                + packageName)));
                anfe.printStackTrace();
            }
        }
    }

    public static String getResourcesString(Context context, int id) {
        String value = null;
        if (context != null && id != -1) {
            value = context.getResources().getString(id);
        }
        return value;
    }

    public static boolean isValueNullOrEmpty(String value) {
        boolean isValue = false;
        if (value == null || value.equals(null) || value.equals("") || value.equals("null") || value.trim().length() == 0) {
            isValue = true;
        }

        return isValue;
    }


}
