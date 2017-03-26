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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.radartech.model.devices.list.DevicesListResponse;
import com.radartech.sw.R;
import com.radartech.util.AppConstants.StatusConstants;
import com.radartech.util.Utility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by moonz on 9/24/2016.
 */


/**
 * Handles Existing plans
 */
public class DevicesListAdapter extends RecyclerView.Adapter<DevicesListAdapter.DevicesListViewHolder> {
    private List<DevicesListResponse> devicesList;
    private Context context;

    public DevicesListAdapter(List<DevicesListResponse> devicesListResponses, Context context) {
        this.devicesList = devicesListResponses;
        this.context = context;
    }

    public void setDevicesList(List<DevicesListResponse> devicesListResponses) {
        this.devicesList = devicesListResponses;
        notifyDataSetChanged();
    }


    @Override
    public DevicesListAdapter.DevicesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_devices_list, null);
        return new DevicesListAdapter.DevicesListViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }

    @Override
    public void onBindViewHolder(final DevicesListAdapter.DevicesListViewHolder holder, int position) {
        DevicesListResponse userRecordsModel = devicesList.get(position);

        String deviceStatus = Utility.getDeviceStatus(userRecordsModel);

        int[] deviceStatusImages = Utility.getTrackingImage(String.valueOf(userRecordsModel.getDeviceid()));
        int greyVehicle = deviceStatusImages[0];
        int greenVehicle = deviceStatusImages[1];
        int redVehicle = deviceStatusImages[2];
        int offlineVehicle = deviceStatusImages[3];
        int orangeVehicle = deviceStatusImages[4];

        int vehicleImage = offlineVehicle;
        if (deviceStatus.equalsIgnoreCase(StatusConstants.OFFLINE)) {
            vehicleImage = offlineVehicle;
        } else if (deviceStatus.equalsIgnoreCase(StatusConstants.LOGGED_OFF)) {
            vehicleImage = offlineVehicle;
        } else if (deviceStatus.equalsIgnoreCase(StatusConstants.STATIC)) {
            vehicleImage = greyVehicle;
        } else if (deviceStatus.equalsIgnoreCase(StatusConstants.MOVING)) {
            vehicleImage = greenVehicle;
        } else if (deviceStatus.equalsIgnoreCase(StatusConstants.HIGH_SPEED)) {
            vehicleImage = orangeVehicle;
        } else if (deviceStatus.equalsIgnoreCase(StatusConstants.OVER_SPEED)) {
            vehicleImage = redVehicle;
        }
        setDeviceStatus(holder, deviceStatus, vehicleImage, userRecordsModel.getCarnumber());
        holder.deviceName.setText(userRecordsModel.getDevicename());
    }

    private void setDeviceStatus(DevicesListViewHolder holder, String deviceStatus, int carImage, String carNumber) {
        holder.deviceStatus.setText(holder.deviceStatus.getContext().getString(R.string.devices_list_status, deviceStatus, carNumber));
        holder.devciceIcon.setImageResource(carImage);
    }

    class DevicesListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.device_title)
        TextView deviceName;
        @BindView(R.id.device_status)
        TextView deviceStatus;
        @BindView(R.id.device_icon)
        ImageView devciceIcon;

        public DevicesListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
