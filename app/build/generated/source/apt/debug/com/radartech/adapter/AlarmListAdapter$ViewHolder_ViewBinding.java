// Generated code from Butter Knife. Do not modify!
package com.radartech.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AlarmListAdapter$ViewHolder_ViewBinding<T extends AlarmListAdapter.ViewHolder> implements Unbinder {
  protected T target;

  @UiThread
  public AlarmListAdapter$ViewHolder_ViewBinding(T target, View source) {
    this.target = target;

    target.deviceName = Utils.findRequiredViewAsType(source, R.id.tv_device_name, "field 'deviceName'", TextView.class);
    target.alarmReceiveTime = Utils.findRequiredViewAsType(source, R.id.tv_alarm_receive_time, "field 'alarmReceiveTime'", TextView.class);
    target.alarmMessage = Utils.findRequiredViewAsType(source, R.id.tv_alarm_message, "field 'alarmMessage'", TextView.class);
    target.unreadAlarmImage = Utils.findRequiredViewAsType(source, R.id.iv_unread_alarm, "field 'unreadAlarmImage'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.deviceName = null;
    target.alarmReceiveTime = null;
    target.alarmMessage = null;
    target.unreadAlarmImage = null;

    this.target = null;
  }
}
