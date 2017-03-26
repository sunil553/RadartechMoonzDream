// Generated code from Butter Knife. Do not modify!
package com.radartech.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class GeofenceItemActivity_ViewBinding<T extends GeofenceItemActivity> extends BaseActivity_ViewBinding<T> {
  private View view2131558767;

  @UiThread
  public GeofenceItemActivity_ViewBinding(final T target, View source) {
    super(target, source);

    View view;
    view = Utils.findRequiredView(source, R.id.btn_newgeofence, "field 'mNewGeoFence' and method 'onClickNewGeoFence'");
    target.mNewGeoFence = Utils.castView(view, R.id.btn_newgeofence, "field 'mNewGeoFence'", Button.class);
    view2131558767 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickNewGeoFence();
      }
    });
    target.mListView = Utils.findRequiredViewAsType(source, R.id.lv_geofencelist, "field 'mListView'", ListView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.mNewGeoFence = null;
    target.mListView = null;

    view2131558767.setOnClickListener(null);
    view2131558767 = null;
  }
}
