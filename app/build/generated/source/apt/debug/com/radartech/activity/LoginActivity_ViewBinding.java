// Generated code from Butter Knife. Do not modify!
package com.radartech.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class LoginActivity_ViewBinding<T extends LoginActivity> extends BaseActivity_ViewBinding<T> {
  private View view2131558578;

  private View view2131558579;

  private View view2131558580;

  private View view2131558577;

  @UiThread
  public LoginActivity_ViewBinding(final T target, View source) {
    super(target, source);

    View view;
    target.userAccount = Utils.findRequiredViewAsType(source, R.id.et_account, "field 'userAccount'", EditText.class);
    target.userPwd = Utils.findRequiredViewAsType(source, R.id.et_password, "field 'userPwd'", EditText.class);
    view = Utils.findRequiredView(source, R.id.cbox_remember, "field 'chkRememberMe' and method 'checkboxRememberToggled'");
    target.chkRememberMe = Utils.castView(view, R.id.cbox_remember, "field 'chkRememberMe'", CheckBox.class);
    view2131558578 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.checkboxRememberToggled(p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.cbox_auto_login, "field 'chkAutoLogin' and method 'checkboxAutoLoginToggled'");
    target.chkAutoLogin = Utils.castView(view, R.id.cbox_auto_login, "field 'chkAutoLogin'", CheckBox.class);
    view2131558579 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.checkboxAutoLoginToggled(p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.cbShowPwd, "method 'checkboxPwdToggled'");
    view2131558580 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.checkboxPwdToggled(p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_login, "method 'onClickLogin'");
    view2131558577 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickLogin(p0);
      }
    });
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.userAccount = null;
    target.userPwd = null;
    target.chkRememberMe = null;
    target.chkAutoLogin = null;

    ((CompoundButton) view2131558578).setOnCheckedChangeListener(null);
    view2131558578 = null;
    ((CompoundButton) view2131558579).setOnCheckedChangeListener(null);
    view2131558579 = null;
    ((CompoundButton) view2131558580).setOnCheckedChangeListener(null);
    view2131558580 = null;
    view2131558577.setOnClickListener(null);
    view2131558577 = null;
  }
}
