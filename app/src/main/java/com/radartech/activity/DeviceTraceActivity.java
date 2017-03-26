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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.radartech.fragment.PlayBackFragment;
import com.radartech.fragment.SettingsFragment;
import com.radartech.fragment.TrackFragment;
import com.radartech.sw.R;
import com.radartech.util.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DeviceTraceActivity extends BaseActivity {

    @BindView(R.id.trace_layout)
    LinearLayout trace_layout;
    @BindView(R.id.playback_layout)
    LinearLayout playback_layout;
    @BindView(R.id.info_layout)
    LinearLayout info_layout;


    @BindView(R.id.trace_iv)
    ImageView traceIV;

    @BindView(R.id.info_iv)
    ImageView infoIV;

    @BindView(R.id.playback_iv)
    ImageView playBackIV;


    @BindView(R.id.playback_tv)
    TextView playBackTV;

    @BindView(R.id.trace_tv)
    TextView traceTV;

    @BindView(R.id.info_tv)
    TextView infoTV;
    Bundle bundleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_trace);
        mUnbinder = ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

        setTitle();
        bundleData = getIntent().getExtras();
        onClickTrace(trace_layout);
    }

    @OnClick(R.id.trace_layout)
    public void onClickTrace(View v) {


//        info_layout.setBackgroundResource(R.color.white_title);
        traceIV.setImageResource(R.drawable.track_icon_3);
        playBackIV.setImageResource(R.drawable.playback_icon_4);
        infoIV.setImageResource(R.drawable.settings_4);



        playBackTV.setTextColor(getResources().getColor(R.color.black));
        infoTV.setTextColor(getResources().getColor(R.color.black));
        traceTV.setTextColor(getResources().getColor(R.color.menubar_bg));


        trace_layout.setBackgroundResource(R.color.set_normal_send);
//        playback_layout.setBackgroundResource(R.color.white_title);

        UiUtils.pushFragment(this, R.id.fragment_switch, new TrackFragment(), bundleData, false, 0);

    }

    @OnClick(R.id.playback_layout)
    public void onClickPlayBack(View v) {
        traceIV.setImageResource(R.drawable.track_icon_4);
        playBackIV.setImageResource(R.drawable.playback_icon_3);
        infoIV.setImageResource(R.drawable.settings_4);


        playBackTV.setTextColor(getResources().getColor(R.color.menubar_bg));
        infoTV.setTextColor(getResources().getColor(R.color.black));
        traceTV.setTextColor(getResources().getColor(R.color.black));

//        info_layout.setBackgroundResource(R.color.white_title);
//        trace_layout.setBackgroundResource(R.color.white_title);
        playback_layout.setBackgroundResource(R.color.set_normal_send);
        UiUtils.pushFragment(this, R.id.fragment_switch, new PlayBackFragment(), bundleData, false, 0);
    }

    @OnClick(R.id.info_layout)
    public void onClickInfo(View v) {

        traceIV.setImageResource(R.drawable.track_icon_4);
        playBackIV.setImageResource(R.drawable.playback_icon_4);
        infoIV.setImageResource(R.drawable.settings_3);


        playBackTV.setTextColor(getResources().getColor(R.color.black));
        infoTV.setTextColor(getResources().getColor(R.color.menubar_bg));
        traceTV.setTextColor(getResources().getColor(R.color.black));


        info_layout.setBackgroundResource(R.color.set_normal_send);
//        trace_layout.setBackgroundResource(R.color.white_title);
//        playback_layout.setBackgroundResource(R.color.white_title);

        UiUtils.pushFragment(this, R.id.fragment_switch, new SettingsFragment(), bundleData, false, 0);
    }

}

