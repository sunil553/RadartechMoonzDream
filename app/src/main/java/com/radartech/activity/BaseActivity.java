package com.radartech.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.radartech.network.RadarTechService;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;

import net.hockeyapp.android.CrashManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created on 24 Oct 2016 5:33 PM.
 *
 * @author PurpleTalk India Pvt. Ltd.
 */

public class BaseActivity extends AppCompatActivity {


    @BindView(R.id.title_bar_device_list)
    RelativeLayout deviceListTitleBarLayout;
    @BindView(R.id.title_bar_normal)
    RelativeLayout normalTitleBarLayout;

    @BindView(R.id.title)
    TextView titleTv;

    public List<Subscription> subscriptions;
    public Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForCrashes();
    }

    public boolean addSubscription(Subscription subscription) {
        if (subscriptions == null) {
            subscriptions = new ArrayList<>();
        }
        return subscriptions.add(subscription);
    }
    public void setTitle() {
        if (this instanceof DevicesListActivity) {
            deviceListTitleBarLayout.setVisibility(View.VISIBLE);
            normalTitleBarLayout.setVisibility(View.GONE);
            return;
        }

        deviceListTitleBarLayout.setVisibility(View.GONE);
        normalTitleBarLayout.setVisibility(View.VISIBLE);

        if (this instanceof AccountInfoActivity){
            titleTv.setText(getString(R.string.title_account_info));
        }else if (this instanceof AlarmInfoActivity){
            titleTv.setText(getString(R.string.title_alarm_info));
        }else if (this instanceof ProviderInfoActivity){
            titleTv.setText(getString(R.string.title_provider_info));
        }else if (this instanceof UserInfoActivity){
            titleTv.setText(getString(R.string.title_user_info));
        }else if (this instanceof ModifyPasswordActivity){
            titleTv.setText(getString(R.string.title_modify_password));
        }
    }



    @OnClick(R.id.back_button)
    public void onClickAlarmBack() {
        finish();
    }



    public void unbindView(Unbinder unbinder) {
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mUnbinder != null) {
            unbindView(mUnbinder);
        }
        if (subscriptions != null) {
            for (Subscription subscription : subscriptions) {
                subscription.unsubscribe();
            }
        }
    }

    public void callAlarmCountAPI() {

        Observable<Object> loginObservable = RadarTechService.getInstance().getAPIService()
                .unreadAlarmNumberAPI(SharedPreferenceUtils.readString(AppConstants.PreferenceConstants.PREF_COOKIES))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscription subscribe = loginObservable.subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                UiUtils.showErrorMessage(BaseActivity.this, e);
            }

            @Override
            public void onNext(Object response) {
                Log.i("shiva", "callAlarmCountAPI :" + response.toString() + "");

                try {

                    JSONObject jsono = new JSONObject(response.toString());
                    int errorcode = jsono.getInt("errorcode");
                    if (errorcode == 0) {
                        int record = jsono.getInt("record");
                        if (BaseActivity.this instanceof DevicesListActivity) {
                            DevicesListActivity activity = (DevicesListActivity) BaseActivity.this;
                            activity.setAlarmCount(record);
                        }else if (BaseActivity.this instanceof AlarmInfoActivity) {
                            AlarmInfoActivity activity = (AlarmInfoActivity) BaseActivity.this;
                            activity.setAlarmCount(record);
                        }

                    } else {
                        UiUtils.showToast(BaseActivity.this, errorcode + " in callAlarmCountAPI");
                    }
                } catch (Exception e) {

                }
            }
        });

        addSubscription(subscribe);
    }

}
