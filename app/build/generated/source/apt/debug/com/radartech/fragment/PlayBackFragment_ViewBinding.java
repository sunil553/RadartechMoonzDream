// Generated code from Butter Knife. Do not modify!
package com.radartech.fragment;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class PlayBackFragment_ViewBinding<T extends PlayBackFragment> extends BaseGoogleMapFragment_ViewBinding<T> {
  private View view2131558648;

  private View view2131558647;

  private View view2131558650;

  private View view2131558646;

  @UiThread
  public PlayBackFragment_ViewBinding(final T target, View source) {
    super(target, source);

    View view;
    view = Utils.findRequiredView(source, R.id.ib_device_trace_time, "field 'mTimeButton' and method 'onClickDeviceTrackTime'");
    target.mTimeButton = Utils.castView(view, R.id.ib_device_trace_time, "field 'mTimeButton'", ImageView.class);
    view2131558648 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickDeviceTrackTime();
      }
    });
    view = Utils.findRequiredView(source, R.id.ib_device_trace_speed, "field 'mSpeedButton' and method 'onClickDeviceTrackSpeed'");
    target.mSpeedButton = Utils.castView(view, R.id.ib_device_trace_speed, "field 'mSpeedButton'", ImageView.class);
    view2131558647 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickDeviceTrackSpeed();
      }
    });
    target.sbar_history = Utils.findRequiredViewAsType(source, R.id.sbar_history, "field 'sbar_history'", SeekBar.class);
    view = Utils.findRequiredView(source, R.id.ibtn_control, "field 'mPlayStopSeekBar' and method 'onCheckboxPlayStop'");
    target.mPlayStopSeekBar = Utils.castView(view, R.id.ibtn_control, "field 'mPlayStopSeekBar'", CheckBox.class);
    view2131558650 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckboxPlayStop(p1);
      }
    });
    target.playback_timer = Utils.findRequiredViewAsType(source, R.id.playback_timer, "field 'playback_timer'", TextView.class);
    target.playback_kms = Utils.findRequiredViewAsType(source, R.id.playback_kms, "field 'playback_kms'", TextView.class);
    target.playback_speed = Utils.findRequiredViewAsType(source, R.id.playback_speed, "field 'playback_speed'", TextView.class);
    target.mTitle = Utils.findRequiredViewAsType(source, R.id.title, "field 'mTitle'", TextView.class);
    target.playbackTime = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'playbackTime'", TextView.class);
    target.playbackDetailsLayout = Utils.findRequiredViewAsType(source, R.id.layout1, "field 'playbackDetailsLayout'", RelativeLayout.class);
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

    target.mTimeButton = null;
    target.mSpeedButton = null;
    target.sbar_history = null;
    target.mPlayStopSeekBar = null;
    target.playback_timer = null;
    target.playback_kms = null;
    target.playback_speed = null;
    target.mTitle = null;
    target.playbackTime = null;
    target.playbackDetailsLayout = null;

    view2131558648.setOnClickListener(null);
    view2131558648 = null;
    view2131558647.setOnClickListener(null);
    view2131558647 = null;
    ((CompoundButton) view2131558650).setOnCheckedChangeListener(null);
    view2131558650 = null;
    view2131558646.setOnClickListener(null);
    view2131558646 = null;
  }
}
