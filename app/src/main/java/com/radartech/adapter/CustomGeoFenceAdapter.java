/*
 *
 *                  Moonzdream Confidential and Proprietary
 *
 *    This work contains valuable confidential and proprietary information.
 *    Disclosure, use or reproduction outside of Moonzdream, Inc. is prohibited
 *    except as authorized in writing. This unpublished work is protected by
 *    the laws of the United States and other countries. If publication occurs,
 *    following notice shall apply:
 *
 *                        Copyright 2016, Moonzdream Inc.
 *                            All rights reserved.
 *                   Freedom of Information Act(5 USC 522) and
 *            Disclosure of Confidential Information Generaly(18 USC 1905)
 *
 *    This material is being furnished in confidence by Moonzdream, Inc. The
 *    information disclosed here falls within Exemption (b)(4) of 5 USC 522
 *    and the prohibitions of 18 USC 1905
 */

package com.radartech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.radartech.callbacks.CustomClickCallBack;
import com.radartech.model.geofence.GeoFenceResponce;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;

import java.util.List;


/**
 * Created by moonz on 9/24/2016.
 */
public class CustomGeoFenceAdapter extends BaseAdapter {
    private Context activity;
    private LayoutInflater inflater;
    private List<GeoFenceResponce> carDevicesList;
    private CustomClickCallBack customClickCallBack;

    public CustomGeoFenceAdapter(Context activity, List<GeoFenceResponce> movieItems, CustomClickCallBack customClickCallBack) {
        this.activity = activity;
        this.carDevicesList = movieItems;
        this.customClickCallBack = customClickCallBack;
    }

    public void setGeoFenceList(List<GeoFenceResponce> movieItems) {
        this.carDevicesList = movieItems;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return carDevicesList.size();
    }

    @Override
    public Object getItem(int location) {
        return carDevicesList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_geofence_layout, null);

        carDevicesList.get(position);
        TextView deviceCarName = (TextView) convertView.findViewById(R.id.geo_name_tv);
        Switch toggleButton = (Switch) convertView.findViewById(R.id.geo_onof_toggle);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customClickCallBack.onToggleClick(position);
            }
        });
        deviceCarName.setText(carDevicesList.get(position).getEfencename());
        int isOpen = carDevicesList.get(position).getIsopen();
        deviceCarName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customClickCallBack.onClick(position);
            }
        });
        deviceCarName.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                customClickCallBack.onLongClick(position);
                return true;
            }
        });
        if (isOpen == AppConstants.IntentConstants.Geofence_TYPE_ONE) {
            toggleButton.setChecked(true);
        } else {
            toggleButton.setChecked(false);

        }
        return convertView;
    }



}
