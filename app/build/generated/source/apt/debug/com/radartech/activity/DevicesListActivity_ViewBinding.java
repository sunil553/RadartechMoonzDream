// Generated code from Butter Knife. Do not modify!
package com.radartech.activity;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class DevicesListActivity_ViewBinding<T extends DevicesListActivity> extends HamburgerMenuActivity_ViewBinding<T> {
  private View view2131558769;

  private View view2131558772;

  private View view2131558770;

  @UiThread
  public DevicesListActivity_ViewBinding(final T target, View source) {
    super(target, source);

    View view;
    target.mUnreadAlarmCount = Utils.findRequiredViewAsType(source, R.id.tv_unread_alarm_count, "field 'mUnreadAlarmCount'", TextView.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.device_list, "field 'recyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.device_list_button_back, "method 'onClickUserInfo'");
    view2131558769 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickUserInfo(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ib_monitor, "method 'onClickMonitor'");
    view2131558772 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickMonitor(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ib_alarm, "method 'onClickAlarm'");
    view2131558770 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAlarm(p0);
      }
    });
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.mUnreadAlarmCount = null;
    target.recyclerView = null;

    view2131558769.setOnClickListener(null);
    view2131558769 = null;
    view2131558772.setOnClickListener(null);
    view2131558772 = null;
    view2131558770.setOnClickListener(null);
    view2131558770 = null;
  }
}
