
package com.radartech.model.device.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseDeviceItem {

    @SerializedName("record")
    @Expose
    private DeviceItemResponse record;
    @SerializedName("errorcode")
    @Expose
    private Integer errorcode;

    /**
     * 
     * @return
     *     The record
     */
    public DeviceItemResponse getRecord() {
        return record;
    }

    /**
     * 
     * @param record
     *     The record
     */
    public void setRecord(DeviceItemResponse record) {
        this.record = record;
    }

    /**
     * 
     * @return
     *     The errorcode
     */
    public Integer getErrorcode() {
        return errorcode;
    }

    /**
     * 
     * @param errorcode
     *     The errorcode
     */
    public void setErrorcode(Integer errorcode) {
        this.errorcode = errorcode;
    }

}
