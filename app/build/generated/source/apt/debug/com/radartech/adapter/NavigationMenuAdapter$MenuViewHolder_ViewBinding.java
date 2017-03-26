// Generated code from Butter Knife. Do not modify!
package com.radartech.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NavigationMenuAdapter$MenuViewHolder_ViewBinding<T extends NavigationMenuAdapter.MenuViewHolder> implements Unbinder {
  protected T target;

  private View view2131558716;

  @UiThread
  public NavigationMenuAdapter$MenuViewHolder_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.mMenuIV = Utils.findRequiredViewAsType(source, R.id.menu_imageView, "field 'mMenuIV'", ImageView.class);
    target.mMenuTV = Utils.findRequiredViewAsType(source, R.id.menu_textView, "field 'mMenuTV'", TextView.class);
    target.mSeparator = Utils.findRequiredView(source, R.id.menu_separatorView, "field 'mSeparator'");
    view = Utils.findRequiredView(source, R.id.menu_parent_ll, "method 'onItemClick'");
    view2131558716 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onItemClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mMenuIV = null;
    target.mMenuTV = null;
    target.mSeparator = null;

    view2131558716.setOnClickListener(null);
    view2131558716 = null;

    this.target = null;
  }
}
