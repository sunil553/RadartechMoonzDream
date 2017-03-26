
package com.radartech.model.alarm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlarmModel {

    @SerializedName("course")
    @Expose
    private Integer course;
    @SerializedName("gpstime")
    @Expose
    private long gpstime;
    @SerializedName("device_name")
    @Expose
    private String deviceName;
    @SerializedName("alarmid")
    @Expose
    private Integer alarmid;
    @SerializedName("speed")
    @Expose
    private Integer speed;
    @SerializedName("alarm_type")
    @Expose
    private Integer alarmType;
    @SerializedName("receive_time")
    @Expose
    private long receiveTime;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("handle_status")
    @Expose
    private Integer handleStatus;
    @SerializedName("gps_status")
    @Expose
    private String gpsStatus;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("deviceid")
    @Expose
    private Integer deviceid;

    /**
     * 
     * @return
     *     The course
     */
    public Integer getCourse() {
        return course;
    }

    /**
     * 
     * @param course
     *     The course
     */
    public void setCourse(Integer course) {
        this.course = course;
    }

    /**
     * 
     * @return
     *     The gpstime
     */
    public long getGpstime() {
        return gpstime;
    }

    /**
     * 
     * @param gpstime
     *     The gpstime
     */
    public void setGpstime(Integer gpstime) {
        this.gpstime = gpstime;
    }

    /**
     * 
     * @return
     *     The deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 
     * @param deviceName
     *     The device_name
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * 
     * @return
     *     The alarmid
     */
    public Integer getAlarmid() {
        return alarmid;
    }

    /**
     * 
     * @param alarmid
     *     The alarmid
     */
    public void setAlarmid(Integer alarmid) {
        this.alarmid = alarmid;
    }

    /**
     * 
     * @return
     *     The speed
     */
    public Integer getSpeed() {
        return speed;
    }

    /**
     * 
     * @param speed
     *     The speed
     */
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    /**
     * 
     * @return
     *     The alarmType
     */
    public Integer getAlarmType() {
        return alarmType;
    }

    /**
     * 
     * @param alarmType
     *     The alarm_type
     */
    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    /**
     * 
     * @return
     *     The receiveTime
     */
    public long getReceiveTime() {
        return receiveTime;
    }

    /**
     * 
     * @param receiveTime
     *     The receive_time
     */
    public void setReceiveTime(Integer receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * 
     * @return
     *     The lng
     */
    public Double getLng() {
        return lng;
    }

    /**
     * 
     * @param lng
     *     The lng
     */
    public void setLng(Double lng) {
        this.lng = lng;
    }

    /**
     * 
     * @return
     *     The handleStatus
     */
    public Integer getHandleStatus() {
        return handleStatus;
    }

    /**
     * 
     * @param handleStatus
     *     The handle_status
     */
    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    /**
     * 
     * @return
     *     The gpsStatus
     */
    public String getGpsStatus() {
        return gpsStatus;
    }

    /**
     * 
     * @param gpsStatus
     *     The gps_status
     */
    public void setGpsStatus(String gpsStatus) {
        this.gpsStatus = gpsStatus;
    }

    /**
     * 
     * @return
     *     The lat
     */
    public Double getLat() {
        return lat;
    }

    /**
     * 
     * @param lat
     *     The lat
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     * 
     * @return
     *     The deviceid
     */
    public Integer getDeviceid() {
        return deviceid;
    }

    /**
     * 
     * @param deviceid
     *     The deviceid
     */
    public void setDeviceid(Integer deviceid) {
        this.deviceid = deviceid;
    }

}
