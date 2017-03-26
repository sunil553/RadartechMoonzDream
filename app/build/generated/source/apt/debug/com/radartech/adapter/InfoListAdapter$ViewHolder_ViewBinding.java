// Generated code from Butter Knife. Do not modify!
package com.radartech.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InfoListAdapter$ViewHolder_ViewBinding<T extends InfoListAdapter.ViewHolder> implements Unbinder {
  protected T target;

  @UiThread
  public InfoListAdapter$ViewHolder_ViewBinding(T target, View source) {
    this.target = target;

    target.tagDescription = Utils.findRequiredViewAsType(source, R.id.tag_description, "field 'tagDescription'", TextView.class);
    target.arrowTv = Utils.findRequiredViewAsType(source, R.id.arrow, "field 'arrowTv'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tagDescription = null;
    target.arrowTv = null;

    this.target = null;
  }
}
