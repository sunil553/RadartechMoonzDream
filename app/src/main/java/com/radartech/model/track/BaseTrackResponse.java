package com.radartech.model.track;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseTrackResponse {

@SerializedName("record")
@Expose
private TrackResponse record;
@SerializedName("errorcode")
@Expose
private Integer errorcode;

/**
* 
* @return
* The record
*/
public TrackResponse getRecord() {
return record;
}

/**
* 
* @param record
* The record
*/
public void setRecord(TrackResponse record) {
this.record = record;
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

}