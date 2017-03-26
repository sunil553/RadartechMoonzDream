
package com.radartech.model.jpushalarm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlarmResponse {

    @SerializedName("course")
    @Expose
    private long course;
    @SerializedName("gpstime")
    @Expose
    private long gpstime;
    @SerializedName("device_name")
    @Expose
    private String deviceName;
    @SerializedName("alarmid")
    @Expose
    private long alarmid;
    @SerializedName("speed")
    @Expose
    private long speed;
    @SerializedName("alarm_type")
    @Expose
    private Integer alarmType;
    @SerializedName("receive_time")
    @Expose
    private long receiveTime;
    @SerializedName("lng")
    @Expose
    private double lng;
    @SerializedName("handle_status")
    @Expose
    private long handleStatus;
    @SerializedName("gps_status")
    @Expose
    private String gpsStatus;
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("deviceid")
    @Expose
    private Integer deviceid;

    public long getCourse() {
        return course;
    }

    public void setCourse(long course) {
        this.course = course;
    }

    public long getGpstime() {
        return gpstime;
    }

    public void setGpstime(long gpstime) {
        this.gpstime = gpstime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public long getAlarmid() {
        return alarmid;
    }

    public void setAlarmid(long alarmid) {
        this.alarmid = alarmid;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(long receiveTime) {
        this.receiveTime = receiveTime;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public long getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(long handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getGpsStatus() {
        return gpsStatus;
    }

    public void setGpsStatus(String gpsStatus) {
        this.gpsStatus = gpsStatus;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Integer getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Integer deviceid) {
        this.deviceid = deviceid;
    }

}
