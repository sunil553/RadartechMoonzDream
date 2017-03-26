package com.radartech.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.radartech.adapter.InfoListAdapter;
import com.radartech.model.SettingSpinnerItemData;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountInfoActivity extends BaseActivity implements AppConstants.UserPersonalFields {

    @BindView(R.id.account_info_list)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        mUnbinder = ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

        setTitle();
        ArrayList<SettingSpinnerItemData> infoList = new ArrayList();
        infoList.add(new SettingSpinnerItemData(getString(R.string.user_account_info_name, SharedPreferenceUtils.readString(person_name)), AppConstants.InfoConstants.ARROW_INVISIBLE));
        infoList.add(new SettingSpinnerItemData(getString(R.string.user_account_info_account, SharedPreferenceUtils.readString(person_account)), AppConstants.InfoConstants.ARROW_INVISIBLE));
        infoList.add(new SettingSpinnerItemData(getString(R.string.user_account_info_contact, SharedPreferenceUtils.readString(person_contact)), AppConstants.InfoConstants.ARROW_INVISIBLE));
        infoList.add(new SettingSpinnerItemData(getString(R.string.user_account_info_mobile, SharedPreferenceUtils.readString(person_phone)), AppConstants.InfoConstants.ARROW_INVISIBLE));
        infoList.add(new SettingSpinnerItemData(getString(R.string.user_account_info_address, SharedPreferenceUtils.readString(person_address)), AppConstants.InfoConstants.ARROW_INVISIBLE));
        infoList.add(new SettingSpinnerItemData(getString(R.string.user_account_info_telephone, SharedPreferenceUtils.readString(person_telephone)), AppConstants.InfoConstants.ARROW_INVISIBLE));
        infoList.add(new SettingSpinnerItemData(getString(R.string.user_account_info_email, SharedPreferenceUtils.readString(person_email)), AppConstants.InfoConstants.ARROW_INVISIBLE));
        infoList.add(new SettingSpinnerItemData(getString(R.string.user_account_info_modify_password, ""), AppConstants.InfoConstants.ARROW_VISIBLE));

        InfoListAdapter infoListAdapter = new InfoListAdapter(this, infoList);
        listView.setAdapter(infoListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 7) {
                    UiUtils.launchActivity(AccountInfoActivity.this, ModifyPasswordActivity.class, null, false);
                }
            }

        });
    }

}

