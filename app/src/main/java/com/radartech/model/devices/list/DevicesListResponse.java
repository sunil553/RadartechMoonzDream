package com.radartech.model.devices.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DevicesListResponse {

@SerializedName("systime")
@Expose
private long systime;
@SerializedName("carnumber")
@Expose
private String carnumber;
@SerializedName("hearttime")
@Expose
private long hearttime;
@SerializedName("speed")
@Expose
private Integer speed;
@SerializedName("status")
@Expose
private String status;
@SerializedName("imei")
@Expose
private String imei;
@SerializedName("lng")
@Expose
private double lng;
@SerializedName("validate")
@Expose
private Boolean validate;
@SerializedName("deviceid")
@Expose
private Integer deviceid;
@SerializedName("course")
@Expose
private Integer course;
@SerializedName("gpstime")
@Expose
private long gpstime;
@SerializedName("overspeed")
@Expose
private Integer overspeed;
@SerializedName("servertime")
@Expose
private long servertime;
@SerializedName("simcard")
@Expose
private String simcard;
@SerializedName("devicename")
@Expose
private String devicename;
@SerializedName("devicetype")
@Expose
private String devicetype;
@SerializedName("groupid")
@Expose
private Integer groupid;
@SerializedName("lat")
@Expose
private double lat;
@SerializedName("icontype")
@Expose
private Integer icontype;

/**
* 
* @return
* The systime
*/
public long getSystime() {
return systime;
}

/**
* 
* @param systime
* The systime
*/
public void setSystime(long systime) {
this.systime = systime;
}

/**
* 
* @return
* The carnumber
*/
public String getCarnumber() {
return carnumber;
}

/**
* 
* @param carnumber
* The carnumber
*/
public void setCarnumber(String carnumber) {
this.carnumber = carnumber;
}

/**
* 
* @return
* The hearttime
*/
public long getHearttime() {
return hearttime;
}

/**
* 
* @param hearttime
* The hearttime
*/
public void setHearttime(long hearttime) {
this.hearttime = hearttime;
}

/**
* 
* @return
* The speed
*/
public Integer getSpeed() {
return speed;
}

/**
* 
* @param speed
* The speed
*/
public void setSpeed(Integer speed) {
this.speed = speed;
}

/**
* 
* @return
* The status
*/
public String getStatus() {
return status;
}

/**
* 
* @param status
* The status
*/
public void setStatus(String status) {
this.status = status;
}

/**
* 
* @return
* The imei
*/
public String getImei() {
return imei;
}

/**
* 
* @param imei
* The imei
*/
public void setImei(String imei) {
this.imei = imei;
}

/**
* 
* @return
* The lng
*/
public double getLng() {
return lng;
}

/**
* 
* @param lng
* The lng
*/
public void setLng(double lng) {
this.lng = lng;
}

/**
* 
* @return
* The validate
*/
public Boolean getValidate() {
return validate;
}

/**
* 
* @param validate
* The validate
*/
public void setValidate(Boolean validate) {
this.validate = validate;
}

/**
* 
* @return
* The deviceid
*/
public Integer getDeviceid() {
return deviceid;
}

/**
* 
* @param deviceid
* The deviceid
*/
public void setDeviceid(Integer deviceid) {
this.deviceid = deviceid;
}

/**
* 
* @return
* The course
*/
public Integer getCourse() {
return course;
}

/**
* 
* @param course
* The course
*/
public void setCourse(Integer course) {
this.course = course;
}

/**
* 
* @return
* The gpstime
*/
public long getGpstime() {
return gpstime;
}

/**
* 
* @param gpstime
* The gpstime
*/
public void setGpstime(long gpstime) {
this.gpstime = gpstime;
}

/**
* 
* @return
* The overspeed
*/
public Integer getOverspeed() {
return overspeed;
}

/**
* 
* @param overspeed
* The overspeed
*/
public void setOverspeed(Integer overspeed) {
this.overspeed = overspeed;
}

/**
* 
* @return
* The servertime
*/
public long getServertime() {
return servertime;
}

/**
* 
* @param servertime
* The servertime
*/
public void setServertime(long servertime) {
this.servertime = servertime;
}

/**
* 
* @return
* The simcard
*/
public String getSimcard() {
return simcard;
}

/**
* 
* @param simcard
* The simcard
*/
public void setSimcard(String simcard) {
this.simcard = simcard;
}

/**
* 
* @return
* The devicename
*/
public String getDevicename() {
return devicename;
}

/**
* 
* @param devicename
* The devicename
*/
public void setDevicename(String devicename) {
this.devicename = devicename;
}

/**
* 
* @return
* The devicetype
*/
public String getDevicetype() {
return devicetype;
}

/**
* 
* @param devicetype
* The devicetype
*/
public void setDevicetype(String devicetype) {
this.devicetype = devicetype;
}

/**
* 
* @return
* The groupid
*/
public Integer getGroupid() {
return groupid;
}

/**
* 
* @param groupid
* The groupid
*/
public void setGroupid(Integer groupid) {
this.groupid = groupid;
}

/**
* 
* @return
* The lat
*/
public double getLat() {
return lat;
}

/**
*
* @param lat
* The lat
*/
public void setLat(double lat) {
this.lat = lat;
}

/**
* 
* @return
* The icontype
*/
public Integer getIcontype() {
return icontype;
}

/**
* 
* @param icontype
* The icontype
*/
public void setIcontype(Integer icontype) {
this.icontype = icontype;
}

}