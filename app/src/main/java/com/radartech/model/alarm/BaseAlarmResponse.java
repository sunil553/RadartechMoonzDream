
package com.radartech.model.alarm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BaseAlarmResponse {

    @SerializedName("errorcode")
    @Expose
    private Integer errorcode;
    @SerializedName("records")
    @Expose
    private List<AlarmModel> records = new ArrayList<AlarmModel>();

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
    public List<AlarmModel> getRecords() {
        return records;
    }

    /**
     * 
     * @param records
     *     The records
     */
    public void setRecords(List<AlarmModel> records) {
        this.records = records;
    }

}
