// Generated code from Butter Knife. Do not modify!
package com.radartech.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class GeofenceNewActivity_ViewBinding<T extends GeofenceNewActivity> extends BaseActivity_ViewBinding<T> {
  @UiThread
  public GeofenceNewActivity_ViewBinding(T target, View source) {
    super(target, source);

    target.mGeoFenceButton = Utils.findRequiredViewAsType(source, R.id.ib_geofence_back, "field 'mGeoFenceButton'", ImageButton.class);
    target.tv_fence_radius = Utils.findRequiredViewAsType(source, R.id.tv_fence_radius, "field 'tv_fence_radius'", TextView.class);
    target.tv_geofence_title = Utils.findRequiredViewAsType(source, R.id.tv_geofence_title, "field 'tv_geofence_title'", TextView.class);
    target.mSaveBtn = Utils.findRequiredViewAsType(source, R.id.btn_set_circle_fence, "field 'mSaveBtn'", Button.class);
    target.mSavePolygonBtn = Utils.findRequiredViewAsType(source, R.id.btn_set_polygon_fence, "field 'mSavePolygonBtn'", Button.class);
    target.mResetPolygon = Utils.findRequiredViewAsType(source, R.id.btn_reset_polygon_fence, "field 'mResetPolygon'", Button.class);
    target.impolugon = Utils.findRequiredViewAsType(source, R.id.ib_geofence_polygon, "field 'impolugon'", ImageButton.class);
    target.imcircle = Utils.findRequiredViewAsType(source, R.id.ib_geofence_circle, "field 'imcircle'", ImageButton.class);
    target.imreduce = Utils.findRequiredViewAsType(source, R.id.ib_reduce_radius, "field 'imreduce'", ImageButton.class);
    target.imincrease = Utils.findRequiredViewAsType(source, R.id.ib_increase_radius, "field 'imincrease'", ImageButton.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.mGeoFenceButton = null;
    target.tv_fence_radius = null;
    target.tv_geofence_title = null;
    target.mSaveBtn = null;
    target.mSavePolygonBtn = null;
    target.mResetPolygon = null;
    target.impolugon = null;
    target.imcircle = null;
    target.imreduce = null;
    target.imincrease = null;
  }
}
