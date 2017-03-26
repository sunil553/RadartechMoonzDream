// Generated code from Butter Knife. Do not modify!
package com.radartech.activity;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class UserInfoActivity_ViewBinding<T extends UserInfoActivity> extends BaseActivity_ViewBinding<T> {
  private View view2131558598;

  private View view2131558597;

  private View view2131558601;

  private View view2131558602;

  private View view2131558603;

  @UiThread
  public UserInfoActivity_ViewBinding(final T target, View source) {
    super(target, source);

    View view;
    view = Utils.findRequiredView(source, R.id.rl_provider_info, "method 'onClickrlProviderInfo'");
    view2131558598 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickrlProviderInfo();
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_account_info_back, "method 'onClickrlClose'");
    view2131558597 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickrlClose();
      }
    });
    view = Utils.findRequiredView(source, R.id.r2_account_info, "method 'onClickAccountInfo'");
    view2131558601 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAccountInfo();
      }
    });
    view = Utils.findRequiredView(source, R.id.r3_currentversion_info, "method 'onClickCurrentVersion'");
    view2131558602 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickCurrentVersion();
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_provider_exit_system, "method 'onClickLogout'");
    view2131558603 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickLogout();
      }
    });
  }

  @Override
  public void unbind() {
    super.unbind();

    view2131558598.setOnClickListener(null);
    view2131558598 = null;
    view2131558597.setOnClickListener(null);
    view2131558597 = null;
    view2131558601.setOnClickListener(null);
    view2131558601 = null;
    view2131558602.setOnClickListener(null);
    view2131558602 = null;
    view2131558603.setOnClickListener(null);
    view2131558603 = null;
  }
}
