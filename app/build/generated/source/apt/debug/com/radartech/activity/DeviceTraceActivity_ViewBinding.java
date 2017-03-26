// Generated code from Butter Knife. Do not modify!
package com.radartech.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class DeviceTraceActivity_ViewBinding<T extends DeviceTraceActivity> extends BaseActivity_ViewBinding<T> {
  private View view2131558556;

  private View view2131558559;

  private View view2131558562;

  @UiThread
  public DeviceTraceActivity_ViewBinding(final T target, View source) {
    super(target, source);

    View view;
    view = Utils.findRequiredView(source, R.id.trace_layout, "field 'trace_layout' and method 'onClickTrace'");
    target.trace_layout = Utils.castView(view, R.id.trace_layout, "field 'trace_layout'", LinearLayout.class);
    view2131558556 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickTrace(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.playback_layout, "field 'playback_layout' and method 'onClickPlayBack'");
    target.playback_layout = Utils.castView(view, R.id.playback_layout, "field 'playback_layout'", LinearLayout.class);
    view2131558559 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickPlayBack(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.info_layout, "field 'info_layout' and method 'onClickInfo'");
    target.info_layout = Utils.castView(view, R.id.info_layout, "field 'info_layout'", LinearLayout.class);
    view2131558562 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickInfo(p0);
      }
    });
    target.traceIV = Utils.findRequiredViewAsType(source, R.id.trace_iv, "field 'traceIV'", ImageView.class);
    target.infoIV = Utils.findRequiredViewAsType(source, R.id.info_iv, "field 'infoIV'", ImageView.class);
    target.playBackIV = Utils.findRequiredViewAsType(source, R.id.playback_iv, "field 'playBackIV'", ImageView.class);
    target.playBackTV = Utils.findRequiredViewAsType(source, R.id.playback_tv, "field 'playBackTV'", TextView.class);
    target.traceTV = Utils.findRequiredViewAsType(source, R.id.trace_tv, "field 'traceTV'", TextView.class);
    target.infoTV = Utils.findRequiredViewAsType(source, R.id.info_tv, "field 'infoTV'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.trace_layout = null;
    target.playback_layout = null;
    target.info_layout = null;
    target.traceIV = null;
    target.infoIV = null;
    target.playBackIV = null;
    target.playBackTV = null;
    target.traceTV = null;
    target.infoTV = null;

    view2131558556.setOnClickListener(null);
    view2131558556 = null;
    view2131558559.setOnClickListener(null);
    view2131558559 = null;
    view2131558562.setOnClickListener(null);
    view2131558562 = null;
  }
}
