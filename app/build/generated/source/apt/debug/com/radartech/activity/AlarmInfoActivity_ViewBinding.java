// Generated code from Butter Knife. Do not modify!
package com.radartech.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class AlarmInfoActivity_ViewBinding<T extends AlarmInfoActivity> extends BaseActivity_ViewBinding<T> {
  private View view2131558524;

  private View view2131558527;

  @UiThread
  public AlarmInfoActivity_ViewBinding(final T target, View source) {
    super(target, source);

    View view;
    target.mUnreadAlarmCount = Utils.findRequiredViewAsType(source, R.id.unread_alarm_count, "field 'mUnreadAlarmCount'", TextView.class);
    view = Utils.findRequiredView(source, R.id.rel_unread_alarm, "method 'onClickUnreadAlarm'");
    view2131558524 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickUnreadAlarm();
      }
    });
    view = Utils.findRequiredView(source, R.id.rel_all_alarm, "method 'onClickAllAlarm'");
    view2131558527 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAllAlarm();
      }
    });
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.mUnreadAlarmCount = null;

    view2131558524.setOnClickListener(null);
    view2131558524 = null;
    view2131558527.setOnClickListener(null);
    view2131558527 = null;
  }
}
