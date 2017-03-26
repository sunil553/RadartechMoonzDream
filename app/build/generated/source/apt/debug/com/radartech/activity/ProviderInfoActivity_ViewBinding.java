// Generated code from Butter Knife. Do not modify!
package com.radartech.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class ProviderInfoActivity_ViewBinding<T extends ProviderInfoActivity> extends BaseActivity_ViewBinding<T> {
  @UiThread
  public ProviderInfoActivity_ViewBinding(T target, View source) {
    super(target, source);

    target.listView = Utils.findRequiredViewAsType(source, R.id.account_info_list, "field 'listView'", ListView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.listView = null;
  }
}
