
package com.radartech.model.geofence;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoFenceResponce {

    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;
    @SerializedName("isopen")
    @Expose
    private Integer isopen;
    @SerializedName("shapeparam")
    @Expose
    private String shapeparam;
    @SerializedName("efencename")
    @Expose
    private String efencename;
    @SerializedName("efenceid")
    @Expose
    private Integer efenceid;
    @SerializedName("shapetype")
    @Expose
    private Integer shapetype;
    @SerializedName("alarmtype")
    @Expose
    private Integer alarmtype;

    /**
     * 
     * @return
     *     The phonenumber
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * 
     * @param phonenumber
     *     The phonenumber
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     * 
     * @return
     *     The isopen
     */
    public Integer getIsopen() {
        return isopen;
    }

    /**
     * 
     * @param isopen
     *     The isopen
     */
    public void setIsopen(Integer isopen) {
        this.isopen = isopen;
    }

    /**
     * 
     * @return
     *     The shapeparam
     */
    public String getShapeparam() {
        return shapeparam;
    }

    /**
     * 
     * @param shapeparam
     *     The shapeparam
     */
    public void setShapeparam(String shapeparam) {
        this.shapeparam = shapeparam;
    }

    /**
     * 
     * @return
     *     The efencename
     */
    public String getEfencename() {
        return efencename;
    }

    /**
     * 
     * @param efencename
     *     The efencename
     */
    public void setEfencename(String efencename) {
        this.efencename = efencename;
    }

    /**
     * 
     * @return
     *     The efenceid
     */
    public Integer getEfenceid() {
        return efenceid;
    }

    /**
     * 
     * @param efenceid
     *     The efenceid
     */
    public void setEfenceid(Integer efenceid) {
        this.efenceid = efenceid;
    }

    /**
     * 
     * @return
     *     The shapetype
     */
    public Integer getShapetype() {
        return shapetype;
    }

    /**
     * 
     * @param shapetype
     *     The shapetype
     */
    public void setShapetype(Integer shapetype) {
        this.shapetype = shapetype;
    }

    /**
     * 
     * @return
     *     The alarmtype
     */
    public Integer getAlarmtype() {
        return alarmtype;
    }

    /**
     * 
     * @param alarmtype
     *     The alarmtype
     */
    public void setAlarmtype(Integer alarmtype) {
        this.alarmtype = alarmtype;
    }

}
