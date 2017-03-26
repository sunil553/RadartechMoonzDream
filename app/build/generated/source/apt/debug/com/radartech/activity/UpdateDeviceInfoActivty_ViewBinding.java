// Generated code from Butter Knife. Do not modify!
package com.radartech.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class UpdateDeviceInfoActivty_ViewBinding<T extends UpdateDeviceInfoActivty> extends BaseActivity_ViewBinding<T> {
  private View view2131558765;

  @UiThread
  public UpdateDeviceInfoActivty_ViewBinding(final T target, View source) {
    super(target, source);

    View view;
    target.deviceNumbUpdate = Utils.findRequiredViewAsType(source, R.id.et_update_device_plate, "field 'deviceNumbUpdate'", EditText.class);
    target.deviceNameUpdate = Utils.findRequiredViewAsType(source, R.id.et_update_device_device_name, "field 'deviceNameUpdate'", EditText.class);
    target.mySpinner = Utils.findRequiredViewAsType(source, R.id.vehicles_spinner, "field 'mySpinner'", Spinner.class);
    view = Utils.findRequiredView(source, R.id.ib_update_device_ok, "field 'ib_update_device_ok' and method 'onClickUpdateDevicePlate'");
    target.ib_update_device_ok = Utils.castView(view, R.id.ib_update_device_ok, "field 'ib_update_device_ok'", ImageButton.class);
    view2131558765 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickUpdateDevicePlate();
      }
    });
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.deviceNumbUpdate = null;
    target.deviceNameUpdate = null;
    target.mySpinner = null;
    target.ib_update_device_ok = null;

    view2131558765.setOnClickListener(null);
    view2131558765 = null;
  }
}
