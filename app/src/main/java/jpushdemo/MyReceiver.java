package jpushdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.radartech.activity.LoginActivity;
import com.radartech.activity.NestedGoogleMapActivity;
import com.radartech.model.APIBaseResponse;
import com.radartech.model.jpushalarm.AlarmIdItem;
import com.radartech.model.jpushalarm.AlarmResponse;
import com.radartech.network.RadarTechService;
import com.radartech.util.AppConstants;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;
import com.radartech.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.radartech.util.AppConstants.PreferenceConstants.INVALIDCREDENTIALS;
import static com.radartech.util.AppConstants.PreferenceConstants.PREF_PASSWORD;
import static com.radartech.util.AppConstants.PreferenceConstants.PREF_USER_NAME;

/**
 * Customize the receiver
 *
 *
 If this is not defined Receiver，then：
 * 1)The default user opens the main interface
 * 2) The custom message was not received
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	private ArrayList<Object> subscriptions;
	private AlarmResponse devicesListResponses  = null;
	Bundle alarmBundle = new Bundle();

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		UiUtils.logMessage("onReceive printBundle");
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "[MyReceiver] receive Registration Id : " + regId);
			//send the Registration Id to your server...
			UiUtils.logMessage("onReceive ACTION_REGISTRATION_ID");

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] \n" +
					"Receive the push-down custom message: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
			processCustomMessage(context, bundle);
			UiUtils.logMessage("onReceive ACTION_MESSAGE_RECEIVED");

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] \n" +
					"Received push notifications");
			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			Log.d(TAG, "[MyReceiver] The ID of the pushed notification: " + notifactionId);
			UiUtils.logMessage("onReceive ACTION_NOTIFICATION_RECEIVED");

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] The user clicks to turn on notifications");
			try {
				JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
				String alarmId = json.optString("alarmid");
				String password = Utility.md5(SharedPreferenceUtils.readString(PREF_PASSWORD));
				callLoginAPI(SharedPreferenceUtils.readString(PREF_USER_NAME),password,String.valueOf(alarmId),context);
//				callHandleAlarmAPI(String.valueOf(alarmId),context);

			} catch (JSONException e) {
			}
			UiUtils.logMessage("onReceive ACTION_NOTIFICATION_OPENED");

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] The user received the RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
			//Here basis JPushInterface.EXTRA_EXTRA Of the content handling code，Such as opening a new Activity， Open a web page and so on..
			UiUtils.logMessage("onReceive ACTION_RICHPUSH_CALLBACK");

		} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
			boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			UiUtils.logMessage("onReceive ACTION_CONNECTION_CHANGE");

		} else {
			Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			UiUtils.logMessage("onReceive else");

		}
	}

	private void callLoginAPI(final String userName, final String password ,final  String alarmId,final Context context ) {
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
					callHandleAlarmAPI(String.valueOf(alarmId),context);

				} else {
					Intent i = new Intent(context, LoginActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(i);

					if (response.getErrorcode().equals(INVALIDCREDENTIALS)){

					}
				}
			}
		});

		addSubscription(subscribe);
	}


	private void callHandleAlarmAPI(final String alarmId , final Context context) {

		Observable<AlarmIdItem> loginObservable = RadarTechService.getInstance().getAPIService()
				.handlePushAlarmAPI(SharedPreferenceUtils.readString(AppConstants.PreferenceConstants.PREF_COOKIES),alarmId,AppConstants.MAP_TYPE)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
		Subscription subscribe = loginObservable.subscribe(new Observer<AlarmIdItem>() {
			@Override
			public void onCompleted() {
			}

			@Override
			public void onError(Throwable e) {
				UiUtils.dismissProgress();
//				UiUtils.showErrorMessage(AlarmListActivity.this, e);
			}

			@Override
			public void onNext(AlarmIdItem response) {

				try {
					if (response.getErrorcode() == 0) {
						devicesListResponses = response.getRecord();

						alarmBundle.putInt(AppConstants.IntentConstants.DEVICE_ID, devicesListResponses.getDeviceid());
						alarmBundle.putString(AppConstants.IntentConstants.DEVICE_NAME, devicesListResponses.getDeviceName());
						alarmBundle.putString(AppConstants.IntentConstants.LATITUDE, String.valueOf(devicesListResponses.getLat()));
						alarmBundle.putString(AppConstants.IntentConstants.LONGITUDE, String.valueOf(devicesListResponses.getLng()));
						alarmBundle.putInt(AppConstants.IntentConstants.DEVICE_ID, devicesListResponses.getDeviceid());
						alarmBundle.putInt(AppConstants.IntentConstants.ALARM_TYPE, devicesListResponses.getAlarmType());
						alarmBundle.putLong(AppConstants.IntentConstants.ALARM_TIME, devicesListResponses.getReceiveTime());
						alarmBundle.putLong(AppConstants.IntentConstants.GPS_TIME, devicesListResponses.getGpstime());
						alarmBundle.putInt(AppConstants.FragmentConstants.FRAGENT_TYPE, AppConstants.FragmentConstants.FRAGMENT_ALARM_DETAIL);

						Intent i = new Intent(context, NestedGoogleMapActivity.class);
						i.putExtras(alarmBundle);
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(i);

					} else {
						UiUtils.logMessage("unsuccessfully read");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		addSubscription(subscribe);
	}
	public boolean addSubscription(Subscription subscription) {
		if (subscriptions == null) {
			subscriptions = new ArrayList<>();
		}
		return subscriptions.add(subscription);
	}

	// Print all	intent extra data
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		if (MainActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (null != extraJson && extraJson.length() > 0) {
						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			context.sendBroadcast(msgIntent);
		}
	}
}
