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

public class DevicesListAdapter$DevicesListViewHolder_ViewBinding<T extends DevicesListAdapter.DevicesListViewHolder> implements Unbinder {
  protected T target;

  @UiThread
  public DevicesListAdapter$DevicesListViewHolder_ViewBinding(T target, View source) {
    this.target = target;

    target.deviceName = Utils.findRequiredViewAsType(source, R.id.device_title, "field 'deviceName'", TextView.class);
    target.deviceStatus = Utils.findRequiredViewAsType(source, R.id.device_status, "field 'deviceStatus'", TextView.class);
    target.devciceIcon = Utils.findRequiredViewAsType(source, R.id.device_icon, "field 'devciceIcon'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.deviceName = null;
    target.deviceStatus = null;
    target.devciceIcon = null;

    this.target = null;
  }
}
