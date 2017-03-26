
package com.radartech.model.device.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceItemResponse {

    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("userremark")
    @Expose
    private String userremark;
    @SerializedName("factorytime")
    @Expose
    private long factorytime;
    @SerializedName("deviceid")
    @Expose
    private Integer deviceid;
    @SerializedName("overspeed")
    @Expose
    private Integer overspeed;
    @SerializedName("simcard")
    @Expose
    private String simcard;
    @SerializedName("distributortime")
    @Expose
    private long distributortime;
    @SerializedName("selltime")
    @Expose
    private long selltime;
    @SerializedName("plattime")
    @Expose
    private long plattime;
    @SerializedName("devicename")
    @Expose
    private String devicename;
    @SerializedName("devicetype")
    @Expose
    private String devicetype;
    @SerializedName("carnum")
    @Expose
    private String carnum;
    @SerializedName("onlinetime")
    @Expose
    private long onlinetime;
    @SerializedName("icontype")
    @Expose
    private Integer icontype;

    /**
     * 
     * @return
     *     The imei
     */
    public String getImei() {
        return imei;
    }

    /**
     * 
     * @param imei
     *     The imei
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * 
     * @return
     *     The userremark
     */
    public String getUserremark() {
        return userremark;
    }

    /**
     * 
     * @param userremark
     *     The userremark
     */
    public void setUserremark(String userremark) {
        this.userremark = userremark;
    }

    /**
     * 
     * @return
     *     The factorytime
     */
    public long getFactorytime() {
        return factorytime;
    }

    /**
     * 
     * @param factorytime
     *     The factorytime
     */
    public void setFactorytime(long factorytime) {
        this.factorytime = factorytime;
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

    /**
     * 
     * @return
     *     The overspeed
     */
    public Integer getOverspeed() {
        return overspeed;
    }

    /**
     * 
     * @param overspeed
     *     The overspeed
     */
    public void setOverspeed(Integer overspeed) {
        this.overspeed = overspeed;
    }

    /**
     * 
     * @return
     *     The simcard
     */
    public String getSimcard() {
        return simcard;
    }

    /**
     * 
     * @param simcard
     *     The simcard
     */
    public void setSimcard(String simcard) {
        this.simcard = simcard;
    }

    /**
     * 
     * @return
     *     The distributortime
     */
    public long getDistributortime() {
        return distributortime;
    }

    /**
     * 
     * @param distributortime
     *     The distributortime
     */
    public void setDistributortime(long distributortime) {
        this.distributortime = distributortime;
    }

    /**
     * 
     * @return
     *     The selltime
     */
    public long getSelltime() {
        return selltime;
    }

    /**
     * 
     * @param selltime
     *     The selltime
     */
    public void setSelltime(long selltime) {
        this.selltime = selltime;
    }

    /**
     * 
     * @return
     *     The plattime
     */
    public long getPlattime() {
        return plattime;
    }

    /**
     * 
     * @param plattime
     *     The plattime
     */
    public void setPlattime(long plattime) {
        this.plattime = plattime;
    }

    /**
     * 
     * @return
     *     The devicename
     */
    public String getDevicename() {
        return devicename;
    }

    /**
     * 
     * @param devicename
     *     The devicename
     */
    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    /**
     * 
     * @return
     *     The devicetype
     */
    public String getDevicetype() {
        return devicetype;
    }

    /**
     * 
     * @param devicetype
     *     The devicetype
     */
    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    /**
     * 
     * @return
     *     The carnum
     */
    public String getCarnum() {
        return carnum;
    }

    /**
     * 
     * @param carnum
     *     The carnum
     */
    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    /**
     * 
     * @return
     *     The onlinetime
     */
    public long getOnlinetime() {
        return onlinetime;
    }

    /**
     * 
     * @param onlinetime
     *     The onlinetime
     */
    public void setOnlinetime(long onlinetime) {
        this.onlinetime = onlinetime;
    }

    /**
     * 
     * @return
     *     The icontype
     */
    public Integer getIcontype() {
        return icontype;
    }

    /**
     * 
     * @param icontype
     *     The icontype
     */
    public void setIcontype(Integer icontype) {
        this.icontype = icontype;
    }

}
