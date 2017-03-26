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

import com.radartech.sw.R;
import com.radartech.util.SharedPreferenceUtils;
import com.radartech.util.UiUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by moonz on 9/23/2016.
 */

/**
 * UserInfoActivity to show the user information.
 */
public class UserInfoActivity  extends BaseActivity {

    @Override
    public void onCreate(Bundle paramBundle){
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_user_info);
        mUnbinder = ButterKnife.bind(this);
        setTitle();
    }

    @OnClick(R.id.rl_provider_info)
    public void onClickrlProviderInfo() {
        UiUtils.launchActivity(this, ProviderInfoActivity.class, null, false);
    }

    @OnClick(R.id.iv_account_info_back)
    public void onClickrlClose() {
        finish();
    }

    @OnClick(R.id.r2_account_info)
    public void onClickAccountInfo() {
        UiUtils.launchActivity(this, AccountInfoActivity.class, null, false);
    }

    @OnClick(R.id.r3_currentversion_info)
    public void onClickCurrentVersion() {
        UiUtils.showToast(this, getString(R.string.user_account_info_current_version));
    }

    @OnClick(R.id.rl_provider_exit_system)
    public void onClickLogout() {
        SharedPreferenceUtils.clear();
        finishAffinity();
        UiUtils.launchActivity(UserInfoActivity.this, LoginActivity.class, null, true);
    }
}
