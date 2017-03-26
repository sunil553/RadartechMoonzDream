// Generated code from Butter Knife. Do not modify!
package com.radartech.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingsFragment_ViewBinding<T extends SettingsFragment> implements Unbinder {
  protected T target;

  private View view2131558646;

  @UiThread
  public SettingsFragment_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.mCarNoPlate = Utils.findRequiredViewAsType(source, R.id.car_info_name_plate, "field 'mCarNoPlate'", TextView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.title, "field 'tvTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.back_button, "field 'img_setting' and method 'onClickAlarmBack'");
    target.img_setting = Utils.castView(view, R.id.back_button, "field 'img_setting'", ImageView.class);
    view2131558646 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAlarmBack();
      }
    });
    target.imupdate = Utils.findRequiredViewAsType(source, R.id.car_info_update, "field 'imupdate'", ImageView.class);
    target.imcaricon = Utils.findRequiredViewAsType(source, R.id.car_info_icon, "field 'imcaricon'", ImageView.class);
    target.listView = Utils.findRequiredViewAsType(source, R.id.car_info_list, "field 'listView'", ListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mCarNoPlate = null;
    target.tvTitle = null;
    target.img_setting = null;
    target.imupdate = null;
    target.imcaricon = null;
    target.listView = null;

    view2131558646.setOnClickListener(null);
    view2131558646 = null;

    this.target = null;
  }
}
