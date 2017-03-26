// Generated code from Butter Knife. Do not modify!
package com.radartech.fragment;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class MonitorFragment_ViewBinding<T extends MonitorFragment> extends BaseGoogleMapFragment_ViewBinding<T> {
  @UiThread
  public MonitorFragment_ViewBinding(T target, View source) {
    super(target, source);

    target.mLocationTv = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'mLocationTv'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.mLocationTv = null;
  }
}
