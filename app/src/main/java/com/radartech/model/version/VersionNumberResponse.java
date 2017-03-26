package com.radartech.model.version;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionNumberResponse {

@SerializedName("record")
@Expose
private VersionNumberRecord record;
@SerializedName("errorcode")
@Expose
private Integer errorcode;

/**
* 
* @return
* The record
*/
public VersionNumberRecord getRecord() {
return record;
}

/**
* 
* @param record
* The record
*/
public void setRecord(VersionNumberRecord record) {
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