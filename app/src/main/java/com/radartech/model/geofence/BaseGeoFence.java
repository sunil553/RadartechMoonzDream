
package com.radartech.model.geofence;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseGeoFence {

    @SerializedName("errorcode")
    @Expose
    private Integer errorcode;
    @SerializedName("records")
    @Expose
    private List<GeoFenceResponce> records = new ArrayList<GeoFenceResponce>();

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

    /**
     * 
     * @return
     *     The records
     */
    public List<GeoFenceResponce> getRecords() {
        return records;
    }

    /**
     * 
     * @param records
     *     The records
     */
    public void setRecords(List<GeoFenceResponce> records) {
        this.records = records;
    }

}
