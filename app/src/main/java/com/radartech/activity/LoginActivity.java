
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
 *    information disclosed here falls within Exemption (left_arrow)(4) of 5 USC 522
 *    and the prohibitions of 18 USC 1905
 */

package com.radartech.activity;

/**
 * Created by moonzdream on 9/10/2016.
 */


import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.radartech.model.APIBaseResponse;
import com.radartech.model.login.UserData;
import com.radartech.network.RadarTechService;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;
import com.radartech.util.Utility;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import jpushdemo.ExampleUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;
import static com.radartech.util.AppConstants.PreferenceConstants.INVALIDCREDENTIALS;
import static com.radartech.util.AppConstants.PreferenceConstants.PREF_AUTO_LOGIN;
import static com.radartech.util.AppConstants.PreferenceConstants.PREF_PASSWORD;
import static com.radartech.util.AppConstants.PreferenceConstants.PREF_REMEMBER;
import static com.radartech.util.AppConstants.PreferenceConstants.PREF_USER_NAME;


/**
 * LoginActivity uses to implement login screen .
 */
public class LoginActivity extends BaseActivity implements AppConstants.UserPersonalFields {

    @BindView(R.id.et_account)
    EditText userAccount;
    @BindView(R.id.et_password)
    EditText userPwd;
    @BindView(R.id.cbox_remember)
    CheckBox chkRememberMe;
    @BindView(R.id.cbox_auto_login)
    CheckBox chkAutoLogin;

    //  for animation
    private long DELAYTIME = 500;

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_login);
        mUnbinder = ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();
        boolean autoLogIn = SharedPreferenceUtils.readBoolean(PREF_AUTO_LOGIN);

        if (autoLogIn) {
            chkAutoLogin.setChecked(true);
            userAccount.setText(SharedPreferenceUtils.readString(PREF_USER_NAME));
            userPwd.setText(SharedPreferenceUtils.readString(PREF_PASSWORD));

            String uName = userAccount.getText().toString().trim();
            String uPassword = userPwd.getText().toString().trim();
            uPassword = Utility.md5(uPassword);

            callLoginAPI(uName, uPassword);

        }

        boolean rememberCredentials = SharedPreferenceUtils.readBoolean(PREF_REMEMBER);
        if (rememberCredentials) {
            chkRememberMe.setChecked(true);
            userAccount.setText(SharedPreferenceUtils.readString(PREF_USER_NAME));
            userPwd.setText(SharedPreferenceUtils.readString(PREF_PASSWORD));
        }
    }

    @OnClick(R.id.btn_login)
    public void onClickLogin(View v) {

        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        v.startAnimation(animScale);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String uName = userAccount.getText().toString().trim();
                String uPassword = userPwd.getText().toString().trim();

                if (uName.length() > 0 && uPassword.length() > 0) {
                    uPassword = Utility.md5(uPassword);

                    callLoginAPI(uName, uPassword);
                } else {
                    Utility.showLogMessage("data", "datadata" + "No Network");

                }
            }
        }, DELAYTIME);

    }

    @OnCheckedChanged(R.id.cbShowPwd)
    public void checkboxPwdToggled(boolean isChecked) {
        if (!isChecked) {
            userPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            userPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    @OnCheckedChanged(R.id.cbox_auto_login)
    public void checkboxAutoLoginToggled(boolean isChecked) {
        SharedPreferenceUtils.writeBoolean(PREF_AUTO_LOGIN, isChecked);
    }

    @OnCheckedChanged(R.id.cbox_remember)
    public void checkboxRememberToggled(boolean isChecked) {
        SharedPreferenceUtils.writeBoolean(PREF_REMEMBER, isChecked);
    }

    //TODO Alias  call back
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (ExampleUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }

            ExampleUtil.showToast(logs, getApplicationContext());
        }

    };
    //TODO TAG
    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (ExampleUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }

            ExampleUtil.showToast(logs, getApplicationContext());
        }

    };

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;

                case MSG_SET_TAGS:
                    Log.d(TAG, "Set tags in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                    break;

                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;


    private void setAlias() {
        String alias = "C" + SharedPreferenceUtils.readInteger(person_id);

        if (TextUtils.isEmpty(alias)) {
            Toast.makeText(LoginActivity.this, R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ExampleUtil.isValidTagAndAlias(alias)) {
            Toast.makeText(LoginActivity.this, R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        //JPush API Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
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
                UiUtils.showErrorMessage(LoginActivity.this, e);
            }

            @Override
            public void onNext(APIBaseResponse response) {
                Log.i("shiva", response.getErrorcode() + "");

                UiUtils.dismissProgress();
                if (response.getErrorcode() == 0) {

                    SharedPreferenceUtils.writeString(AppConstants.PreferenceConstants.PREF_USER_NAME, userAccount.getText().toString());
                    SharedPreferenceUtils.writeString(AppConstants.PreferenceConstants.PREF_PASSWORD, userPwd.getText().toString());


                    UserData data = response.getUserData();
                    setSharedPreferences(data);

                    setAlias();

                    UiUtils.launchActivity(LoginActivity.this, DevicesListActivity.class, null, true);
                } else {
                    UiUtils.showToast(LoginActivity.this, response.getErrorcode().toString());
                    if (response.getErrorcode().equals(INVALIDCREDENTIALS)){
                        UiUtils.showToast(LoginActivity.this, "Login account or password error");
                    }
                }
            }
        });

        addSubscription(subscribe);
    }

    private void setSharedPreferences(UserData data) {
        SharedPreferenceUtils.writeInteger(person_id, data.getId());
        SharedPreferenceUtils.writeString(person_contact, data.getContact());
        SharedPreferenceUtils.writeInteger(person_customertype, data.getCustomertype());
        SharedPreferenceUtils.writeString(person_phone, data.getPhone());
        SharedPreferenceUtils.writeString(person_email, data.getEmail());
        SharedPreferenceUtils.writeString(person_address, data.getAddress());
        SharedPreferenceUtils.writeString(person_name, data.getName());
        SharedPreferenceUtils.writeString(person_account, data.getAccount());
        SharedPreferenceUtils.writeInteger(person_pid, data.getPid());
        SharedPreferenceUtils.writeInteger(person_type, data.getType());
        SharedPreferenceUtils.writeString(person_telephone, data.getTelephone());
        SharedPreferenceUtils.writeString(person_account, data.getAccount());
    }
}



