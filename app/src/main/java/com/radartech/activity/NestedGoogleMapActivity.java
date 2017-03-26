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

import com.radartech.fragment.AlarmDetailFragment;
import com.radartech.fragment.MonitorFragment;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.UiUtils;

import butterknife.ButterKnife;


public class NestedGoogleMapActivity extends BaseActivity implements AppConstants.FragmentConstants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_google_map);
        mUnbinder = ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        setTitle();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        int fragmentType = extras.getInt(FRAGENT_TYPE);
        if (fragmentType == FRAGMENT_ALARM_DETAIL) {
            UiUtils.pushFragment(this, R.id.container, new AlarmDetailFragment(), extras, false, 0);
            titleTv.setText(getString(R.string.title_alarm_detail));
        } else if (fragmentType == FRAGMENT_MONITOR) {
            UiUtils.pushFragment(this, R.id.container, new MonitorFragment(), extras, false, 0);
            titleTv.setText(getString(R.string.title_monitor));
        }
    }

}
