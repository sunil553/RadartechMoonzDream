/**
 *
 */
package com.radartech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.radartech.model.alarm.AlarmModel;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;
import com.radartech.util.Utility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Adapter that allows us to render right_arrow list of items
 */
public class AlarmListAdapter extends BaseAdapter {

    private List<AlarmModel> alarmModelArrayList;
    private LayoutInflater li;
    private Context context;

    /**
     * Constructor from right_arrow list of items
     */
    public AlarmListAdapter(Context context, List<AlarmModel> alarmModelArrayList) {
        this.context = context;
        this.alarmModelArrayList = alarmModelArrayList;
        li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setAlarmList(List<AlarmModel> alarmModelArrayList) {
        this.alarmModelArrayList = alarmModelArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return alarmModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return alarmModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // The search_item we want to get the view for
        // --
        final AlarmModel alarmModel = alarmModelArrayList.get(position);

        // Re-use the view if possible
        // --
        ViewHolder holder;
        if (convertView == null) {
            convertView = li.inflate(R.layout.item_alarm_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Set some view properties
        holder.deviceName.setText(alarmModel.getDeviceName());
        holder.alarmReceiveTime.setText(Utility.getDateFromLong(alarmModel.getReceiveTime(), AppConstants.YYYY_MM_DD_HH_MM_SS_SLASH));

        String alarmType = "";
        if (alarmModel.getAlarmType() == AppConstants.AlarmConstants.ALARM_GEO_FENCE_IN) {
            alarmType = context.getString(R.string.alarm_geo_fence_in);
        } else if (alarmModel.getAlarmType() == AppConstants.AlarmConstants.ALARM_GEO_FENCE_OUT_ALARM) {
            alarmType = context.getString(R.string.alarm_geo_fence_out);
        }
        holder.alarmMessage.setText(alarmType);

        if (alarmModel.getHandleStatus() == AppConstants.AlarmConstants.ALARM_HANDLE_STATUS) {
            holder.unreadAlarmImage.setVisibility(View.VISIBLE);
        } else {
            holder.unreadAlarmImage.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    static class ViewHolder {

        public ViewHolder(View rootView) {
            ButterKnife.bind(this, rootView);
        }

        @BindView(R.id.tv_device_name)
        public TextView deviceName;
        @BindView(R.id.tv_alarm_receive_time)
        public TextView alarmReceiveTime;
        @BindView(R.id.tv_alarm_message)
        public TextView alarmMessage;
        @BindView(R.id.iv_unread_alarm)
        public ImageView unreadAlarmImage;

    }
}
