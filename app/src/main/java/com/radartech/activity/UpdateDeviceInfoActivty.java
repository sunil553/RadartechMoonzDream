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

package com.radartech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.radartech.adapter.SettingsSpinnerAdapter;
import com.radartech.model.SettingSpinnerItemData;
import com.radartech.network.RadarTechService;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;
import com.radartech.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class UpdateDeviceInfoActivty extends BaseActivity implements AppConstants {


    @BindView(R.id.et_update_device_plate)
    EditText deviceNumbUpdate;
    @BindView(R.id.et_update_device_device_name)
    EditText deviceNameUpdate;
    @BindView(R.id.vehicles_spinner)
    Spinner mySpinner;
    @BindView(R.id.ib_update_device_ok)
    ImageButton ib_update_device_ok;

    private String aCarNumber, aCarName;
    private String adeviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_device_info);
        mUnbinder = ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {


        Bundle b = getIntent().getExtras();
        aCarNumber = b.getString(IntentConstants.CAR_NUMB);
        aCarName = b.getString(IntentConstants.CAR_NAME);
        adeviceId = b.getString(IntentConstants.CAR_ID);

        titleTv.setText(aCarName);
        ib_update_device_ok.setVisibility(View.VISIBLE);

        final ArrayList<SettingSpinnerItemData> list = new ArrayList<>();
        list.add(new SettingSpinnerItemData("Car", R.drawable.gray_car));
        list.add(new SettingSpinnerItemData("Bike", R.drawable.gray_bike));
        list.add(new SettingSpinnerItemData("Bus", R.drawable.gray_bus));
        list.add(new SettingSpinnerItemData("Truck", R.drawable.gray_lorry));
        list.add(new SettingSpinnerItemData("Cycle", R.drawable.gray_cycle));
        list.add(new SettingSpinnerItemData("Suv", R.drawable.gray_suv));


        SettingsSpinnerAdapter adapter = new SettingsSpinnerAdapter(this, list);
        mySpinner.setAdapter(adapter);

        mySpinner.setSelection(SharedPreferenceUtils.readInteger(adeviceId));
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Utility.showLogMessage("POSITION OF DROPDOWN", String.valueOf(position));
                //TODO Write condition when it is clicked on save only

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        deviceNameUpdate.setText(aCarName);
        deviceNumbUpdate.setText(aCarNumber);
    }

    @OnClick(R.id.ib_update_device_ok)
    public void onClickUpdateDevicePlate() {
        if (Utility.isNetworkAvailable(getApplicationContext())) {
            aCarName = deviceNameUpdate.getText().toString();
            aCarNumber = deviceNumbUpdate.getText().toString();
            SharedPreferenceUtils.writeInteger(adeviceId, mySpinner.getSelectedItemPosition());
            callHandledDeviceOverSpeedAPI();
        } else {
            Utility.showToastMessage(getApplicationContext(), getString(R.string.error_network));
        }
    }


    private void callHandledDeviceOverSpeedAPI() {
        Observable<Object> loginObservable = RadarTechService.getInstance().getAPIService()
                .deviceEditAPI(SharedPreferenceUtils.readString(PreferenceConstants.PREF_COOKIES), adeviceId, aCarName, aCarNumber)
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
                        finish();
                    } else {
                        UiUtils.logMessage("unsuccessfully read");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        addSubscription(subscribe);
    }
}
