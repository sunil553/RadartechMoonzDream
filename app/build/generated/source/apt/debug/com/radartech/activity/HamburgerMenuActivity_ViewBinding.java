// Generated code from Butter Knife. Do not modify!
package com.radartech.activity;

import android.support.annotation.UiThread;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class HamburgerMenuActivity_ViewBinding<T extends HamburgerMenuActivity> extends BaseActivity_ViewBinding<T> {
  @UiThread
  public HamburgerMenuActivity_ViewBinding(T target, View source) {
    super(target, source);

    target.mNavigationMenuRecyclerView = Utils.findRequiredViewAsType(source, R.id.navigation_menu_RecyclerView, "field 'mNavigationMenuRecyclerView'", RecyclerView.class);
    target.userAccountProfilePic = Utils.findRequiredViewAsType(source, R.id.navigation_drawer_user_account_picture_profile, "field 'userAccountProfilePic'", ImageView.class);
    target.accountInformationDisplayName = Utils.findRequiredViewAsType(source, R.id.navigation_drawer_account_information_display_name, "field 'accountInformationDisplayName'", TextView.class);
    target.mDrawerLayout = Utils.findRequiredViewAsType(source, R.id.drawer_layout, "field 'mDrawerLayout'", DrawerLayout.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.mNavigationMenuRecyclerView = null;
    target.userAccountProfilePic = null;
    target.accountInformationDisplayName = null;
    target.mDrawerLayout = null;
  }
}
