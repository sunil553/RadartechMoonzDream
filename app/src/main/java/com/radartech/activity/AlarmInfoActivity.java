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

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * AlarmsInfoActivityAll To implement alram information.
 */
public class AlarmInfoActivity extends BaseActivity {

    @BindView(R.id.unread_alarm_count)
    TextView mUnreadAlarmCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_info);
        mUnbinder = ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        callAlarmCountAPI();
    }

    private void initViews() {
        setTitle();
    }

    public void setAlarmCount(int record) {
        mUnreadAlarmCount.setText(String.valueOf(record));
    }

    @OnClick(R.id.rel_unread_alarm)
    public void onClickUnreadAlarm() {
        Bundle unreadAlarmBundle = new Bundle();
        unreadAlarmBundle.putString(AppConstants.IntentConstants.ALARM_TYPE, AppConstants.IntentConstants.ALARM_TYPE_UNREAD_ALARM);
        UiUtils.launchActivity(this, AlarmListActivity.class, unreadAlarmBundle, false);
    }

    @OnClick(R.id.rel_all_alarm)
    public void onClickAllAlarm() {
        Bundle allAlarmBundle = new Bundle();
        allAlarmBundle.putString(AppConstants.IntentConstants.ALARM_TYPE, AppConstants.IntentConstants.ALARM_TYPE_ALL_ALARM);
        UiUtils.launchActivity(this, AlarmListActivity.class, allAlarmBundle, false);
    }

}
