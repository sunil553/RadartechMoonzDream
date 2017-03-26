// Generated code from Butter Knife. Do not modify!
package com.radartech.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class ModifyPasswordActivity_ViewBinding<T extends ModifyPasswordActivity> extends BaseActivity_ViewBinding<T> {
  private View view2131558765;

  @UiThread
  public ModifyPasswordActivity_ViewBinding(final T target, View source) {
    super(target, source);

    View view;
    view = Utils.findRequiredView(source, R.id.ib_update_device_ok, "field 'ib_update_device_ok' and method 'onClickUpdateDevicePlate'");
    target.ib_update_device_ok = Utils.castView(view, R.id.ib_update_device_ok, "field 'ib_update_device_ok'", ImageButton.class);
    view2131558765 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickUpdateDevicePlate();
      }
    });
    target.mOldPassword = Utils.findRequiredViewAsType(source, R.id.et_update_password_old_password, "field 'mOldPassword'", EditText.class);
    target.mNewPasswd = Utils.findRequiredViewAsType(source, R.id.et_update_password_new_password, "field 'mNewPasswd'", EditText.class);
    target.mNewUpdatePswd = Utils.findRequiredViewAsType(source, R.id.et_update_password_confirm_new_password, "field 'mNewUpdatePswd'", EditText.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.ib_update_device_ok = null;
    target.mOldPassword = null;
    target.mNewPasswd = null;
    target.mNewUpdatePswd = null;

    view2131558765.setOnClickListener(null);
    view2131558765 = null;
  }
}
