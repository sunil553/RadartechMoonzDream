package com.radartech.adapter;

/**
 * Created by sandeep on 15-11-2016.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.radartech.model.SettingSpinnerItemData;
import com.radartech.sw.R;

import java.util.ArrayList;

public class SettingsSpinnerAdapter extends ArrayAdapter<SettingSpinnerItemData> {
    private ArrayList<SettingSpinnerItemData> list;
    private LayoutInflater inflater;

    public SettingsSpinnerAdapter(Activity context, ArrayList<SettingSpinnerItemData>
            list) {
        super(context, R.layout.item_setting_car_info, list);
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_setting_car_info, parent, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.icon_image);
        imageView.setImageResource(list.get(position).getImageId());
        TextView textView = (TextView) itemView.findViewById(R.id.icon_name);
        textView.setText(list.get(position).getText());
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent) {
        return getView(position, convertView, parent);
    }
}
