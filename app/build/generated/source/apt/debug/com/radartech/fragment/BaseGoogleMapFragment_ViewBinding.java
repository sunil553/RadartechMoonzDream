// Generated code from Butter Knife. Do not modify!
package com.radartech.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseGoogleMapFragment_ViewBinding<T extends BaseGoogleMapFragment> implements Unbinder {
  protected T target;

  private View view2131558552;

  private View view2131558551;

  @UiThread
  public BaseGoogleMapFragment_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_monitor_map_normal, "method 'onClickMapNormal'");
    view2131558552 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickMapNormal(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_monitor_map_satellite, "method 'onClickMapSatellite'");
    view2131558551 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickMapSatellite(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (this.target == null) throw new IllegalStateException("Bindings already cleared.");

    view2131558552.setOnClickListener(null);
    view2131558552 = null;
    view2131558551.setOnClickListener(null);
    view2131558551 = null;

    this.target = null;
  }
}
