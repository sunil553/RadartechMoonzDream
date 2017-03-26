package com.radartech.model.version;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionNumberRecord {

@SerializedName("downloadurl")
@Expose
private String downloadurl;
@SerializedName("updatetime")
@Expose
private String updatetime;
@SerializedName("description")
@Expose
private String description;
@SerializedName("version")
@Expose
private String version;

/**
* 
* @return
* The downloadurl
*/
public String getDownloadurl() {
return downloadurl;
}

/**
* 
* @param downloadurl
* The downloadurl
*/
public void setDownloadurl(String downloadurl) {
this.downloadurl = downloadurl;
}

/**
* 
* @return
* The updatetime
*/
public String getUpdatetime() {
return updatetime;
}

/**
* 
* @param updatetime
* The updatetime
*/
public void setUpdatetime(String updatetime) {
this.updatetime = updatetime;
}

/**
* 
* @return
* The description
*/
public String getDescription() {
return description;
}

/**
* 
* @param description
* The description
*/
public void setDescription(String description) {
this.description = description;
}

/**
* 
* @return
* The version
*/
public String getVersion() {
return version;
}

/**
* 
* @param version
* The version
*/
public void setVersion(String version) {
this.version = version;
}

}
