// Generated code from Butter Knife. Do not modify!
package com.radartech.fragment;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class TrackFragment_ViewBinding<T extends TrackFragment> extends BaseGoogleMapFragment_ViewBinding<T> {
  private View view2131558646;

  @UiThread
  public TrackFragment_ViewBinding(final T target, View source) {
    super(target, source);

    View view;
    target.mDeviceName = Utils.findRequiredViewAsType(source, R.id.title, "field 'mDeviceName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.back_button, "method 'onClickDeviceBack'");
    view2131558646 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickDeviceBack();
      }
    });
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.mDeviceName = null;

    view2131558646.setOnClickListener(null);
    view2131558646 = null;
  }
}
