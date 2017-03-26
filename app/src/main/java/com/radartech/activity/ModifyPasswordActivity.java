package com.radartech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.radartech.model.device.settings.BaseDeviceItem;
import com.radartech.network.NetworkUtils;
import com.radartech.network.RadarTechService;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;
import com.radartech.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ModifyPasswordActivity extends BaseActivity {


    @BindView(R.id.ib_update_device_ok)
    ImageButton ib_update_device_ok;

    @BindView(R.id.et_update_password_old_password)
    EditText mOldPassword;
    @BindView(R.id.et_update_password_new_password)
    EditText mNewPasswd;
    @BindView(R.id.et_update_password_confirm_new_password)
    EditText mNewUpdatePswd;
    private String oldPassword,newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        mUnbinder = ButterKnife.bind(this);

        ib_update_device_ok.setVisibility(View.VISIBLE);

        setTitle();
    }

    @OnClick(R.id.ib_update_device_ok)
    public void onClickUpdateDevicePlate() {
        if (!validate()){
            if (mNewPasswd.getText().toString().equals(mNewUpdatePswd.getText().toString()) && NetworkUtils.isNetworkAvailable(getApplicationContext())){
                oldPassword = mOldPassword.getText().toString().trim();
                newPassword = mNewPasswd.getText().toString().trim();

                oldPassword = Utility.md5(oldPassword);
                newPassword = Utility.md5(newPassword);

                callChangePasswordAPI();

                finish();
            }
        }else {
            UiUtils.showToast(getApplicationContext(), "Enter New Password");

        }
    }

    private boolean validate() {
        Boolean flag = false;
        if (mOldPassword.getText().toString().length()==0){
            flag = true;
            UiUtils.showToast(getApplicationContext(), "Old Password error");
        }if (mNewPasswd.getText().toString().length()==0){
            flag = true;
            UiUtils.showToast(getApplicationContext(), "Old Password error");
        }if (mNewUpdatePswd.getText().toString().length()==0){
            flag = true;
            UiUtils.showToast(getApplicationContext(), "Old Password error");
        }
        return flag;
    }

    private void callChangePasswordAPI() {
        Observable<Object> loginObservable = RadarTechService.getInstance().getAPIService()
                .changePasswordAPI(SharedPreferenceUtils.readString(AppConstants.PreferenceConstants.PREF_COOKIES), oldPassword, newPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.dismissProgress();
                UiUtils.showErrorMessage(getApplicationContext(), e);
            }

            @Override
            public void onNext(Object response) {
                try {

                    JSONObject jsono = new JSONObject(response.toString());
                    int errorcode = jsono.getInt("errorcode");
                    if (errorcode == 0) {
                        Utility.showLogMessage("data", "datadata" + "SUCCESSFULLY READ");
                        Utility.showToastMessage(getApplicationContext(),"Password Successfull Changed");
                    } else {
                        UiUtils.logMessage("unsuccessfully read");
                        Utility.showToastMessage(getApplicationContext(),"failed Please try again");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                UiUtils.dismissProgress();
            }
        });

        addSubscription(subscribe);
    }



}
