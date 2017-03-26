package com.radartech.model.devices.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BaseDevicesList {

@SerializedName("alarmnumber")
@Expose
private long alarmnumber;
@SerializedName("servertime")
@Expose
private String servertime;
@SerializedName("errorcode")
@Expose
private Integer errorcode;
@SerializedName("records")
@Expose
private List<DevicesListResponse> records = new ArrayList<DevicesListResponse>();
@SerializedName("groups")
@Expose
private List<Object> groups = new ArrayList<Object>();

/**
* 
* @return
* The alarmnumber
*/
public long getAlarmnumber() {
return alarmnumber;
}

/**
* 
* @param alarmnumber
* The alarmnumber
*/
public void setAlarmnumber(long alarmnumber) {
this.alarmnumber = alarmnumber;
}

/**
* 
* @return
* The servertime
*/
public String getServertime() {
return servertime;
}

/**
* 
* @param servertime
* The servertime
*/
public void setServertime(String servertime) {
this.servertime = servertime;
}

/**
* 
* @return
* The errorcode
*/
public Integer getErrorcode() {
return errorcode;
}

/**
* 
* @param errorcode
* The errorcode
*/
public void setErrorcode(Integer errorcode) {
this.errorcode = errorcode;
}

/**
* 
* @return
* The records
*/
public List<DevicesListResponse> getRecords() {
return records;
}

/**
* 
* @param records
* The records
*/
public void setRecords(List<DevicesListResponse> records) {
this.records = records;
}

/**
* 
* @return
* The groups
*/
public List<Object> getGroups() {
return groups;
}

/**
* 
* @param groups
* The groups
*/
public void setGroups(List<Object> groups) {
this.groups = groups;
}

}