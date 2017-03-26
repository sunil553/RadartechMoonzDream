// Generated code from Butter Knife. Do not modify!
package com.radartech.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.radartech.sw.R;
import java.lang.Override;

public class AlarmListActivity_ViewBinding<T extends AlarmListActivity> extends BaseActivity_ViewBinding<T> {
  private View view2131558766;

  @UiThread
  public AlarmListActivity_ViewBinding(final T target, View source) {
    super(target, source);

    View view;
    target.mAlarmListView = Utils.findRequiredViewAsType(source, R.id.lv_alarmlist, "field 'mAlarmListView'", ListView.class);
    view = Utils.findRequiredView(source, R.id.ib_clear_alarmlist, "field 'clearButton' and method 'onClickAllAlarm'");
    target.clearButton = Utils.castView(view, R.id.ib_clear_alarmlist, "field 'clearButton'", Button.class);
    view2131558766 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAllAlarm();
      }
    });
    target.mStatusAlarm = Utils.findRequiredViewAsType(source, R.id.tv_list_status, "field 'mStatusAlarm'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    super.unbind();

    target.mAlarmListView = null;
    target.clearButton = null;
    target.mStatusAlarm = null;

    view2131558766.setOnClickListener(null);
    view2131558766 = null;
  }
}
