// Generated code from Butter Knife. Do not modify!
package com.radartech.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseActivity_ViewBinding<T extends BaseActivity> implements Unbinder {
  protected T target;

  private View view2131558646;

  @UiThread
  public BaseActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.deviceListTitleBarLayout = Utils.findRequiredViewAsType(source, R.id.title_bar_device_list, "field 'deviceListTitleBarLayout'", RelativeLayout.class);
    target.normalTitleBarLayout = Utils.findRequiredViewAsType(source, R.id.title_bar_normal, "field 'normalTitleBarLayout'", RelativeLayout.class);
    target.titleTv = Utils.findRequiredViewAsType(source, R.id.title, "field 'titleTv'", TextView.class);
    view = Utils.findRequiredView(source, R.id.back_button, "method 'onClickAlarmBack'");
    view2131558646 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAlarmBack();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.deviceListTitleBarLayout = null;
    target.normalTitleBarLayout = null;
    target.titleTv = null;

    view2131558646.setOnClickListener(null);
    view2131558646 = null;

    this.target = null;
  }
}
