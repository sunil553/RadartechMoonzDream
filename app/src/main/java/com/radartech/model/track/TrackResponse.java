package com.radartech.model.track;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackResponse {

    @SerializedName("course")
    @Expose
    private Integer course;
    @SerializedName("systime")
    @Expose
    private long systime;
    @SerializedName("gpstime")
    @Expose
    private long gpstime;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("speed")
    @Expose
    private Integer speed;
    @SerializedName("hearttime")
    @Expose
    private long hearttime;
    @SerializedName("servertime")
    @Expose
    private long servertime;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("deviceid")
    @Expose
    private Integer deviceid;

    private String customStatus;

    public String getCustomAcc() {
        return customAcc;
    }

    private String customAcc;

    /**
     * @return The course
     */
    public Integer getCourse() {
        return course;
    }

    /**
     * @param course The course
     */
    public void setCourse(Integer course) {
        this.course = course;
    }

    /**
     * @return The systime
     */
    public long getSystime() {
        return systime;
    }

    /**
     * @param systime The systime
     */
    public void setSystime(Integer systime) {
        this.systime = systime;
    }

    /**
     * @return The gpstime
     */
    public long getGpstime() {
        return gpstime;
    }

    /**
     * @param gpstime The gpstime
     */
    public void setGpstime(Integer gpstime) {
        this.gpstime = gpstime;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The speed
     */
    public Integer getSpeed() {
        return speed;
    }

    /**
     * @param speed The speed
     */
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    /**
     * @return The hearttime
     */
    public long getHearttime() {
        return hearttime;
    }

    /**
     * @param hearttime The hearttime
     */
    public void setHearttime(Integer hearttime) {
        this.hearttime = hearttime;
    }

    /**
     * @return The servertime
     */
    public long getServertime() {
        return servertime;
    }

    /**
     * @param servertime The servertime
     */
    public void setServertime(Integer servertime) {
        this.servertime = servertime;
    }

    /**
     * @return The lng
     */
    public Double getLng() {
        return lng;
    }

    /**
     * @param lng The lng
     */
    public void setLng(Double lng) {
        this.lng = lng;
    }

    /**
     * @return The lat
     */
    public Double getLat() {
        return lat;
    }

    /**
     * @param lat The lat
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     * @return The deviceid
     */
    public Integer getDeviceid() {
        return deviceid;
    }

    /**
     * @param deviceid The deviceid
     */
    public void setDeviceid(Integer deviceid) {
        this.deviceid = deviceid;
    }

    public void setCustomStatus(String customStatus) {
        this.customStatus = customStatus;
    }

    public String getCustomStatus() {
        return customStatus;
    }

    public void setAcc(String customAcc) {

    }
}