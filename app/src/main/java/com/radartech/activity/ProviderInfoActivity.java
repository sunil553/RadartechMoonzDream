package com.radartech.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.radartech.adapter.InfoListAdapter;
import com.radartech.model.SettingSpinnerItemData;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.SharedPreferenceUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProviderInfoActivity extends BaseActivity implements AppConstants.UserPersonalFields {


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
        listView = (ListView) findViewById(R.id.account_info_list);

        ArrayList<SettingSpinnerItemData> infoList = new ArrayList();
        infoList.add(new SettingSpinnerItemData(getString(R.string.provider_info_provider, SharedPreferenceUtils.readString(person_name)), AppConstants.InfoConstants.ARROW_INVISIBLE));
        infoList.add(new SettingSpinnerItemData(getString(R.string.provider_info_contact, SharedPreferenceUtils.readString(person_contact)), AppConstants.InfoConstants.ARROW_INVISIBLE));
        infoList.add(new SettingSpinnerItemData(getString(R.string.provider_info_phone, SharedPreferenceUtils.readString(person_phone)), AppConstants.InfoConstants.ARROW_INVISIBLE));
        infoList.add(new SettingSpinnerItemData(getString(R.string.provider_info_telephone, SharedPreferenceUtils.readString(person_telephone)), AppConstants.InfoConstants.ARROW_INVISIBLE));
        infoList.add(new SettingSpinnerItemData(getString(R.string.provider_info_address, SharedPreferenceUtils.readString(person_address)), AppConstants.InfoConstants.ARROW_INVISIBLE));

        InfoListAdapter infoListAdapter = new InfoListAdapter(this, infoList);
        listView.setAdapter(infoListAdapter);
    }
}


