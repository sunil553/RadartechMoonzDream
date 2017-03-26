package com.radartech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayBackResponse {

@SerializedName("record")
@Expose
private String record;
@SerializedName("errorcode")
@Expose
private Integer errorcode;

/**
* 
* @return
* The record
*/
public String getRecord() {
return record;
}

/**
* 
* @param record
* The record
*/
public void setRecord(String record) {
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
