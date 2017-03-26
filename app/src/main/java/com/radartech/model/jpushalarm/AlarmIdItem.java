
package com.radartech.model.jpushalarm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlarmIdItem {

    @SerializedName("record")
    @Expose
    private AlarmResponse record;
    @SerializedName("errorcode")
    @Expose
    private long errorcode;

    public AlarmResponse getRecord() {
        return record;
    }

    public void setRecord(AlarmResponse record) {
        this.record = record;
    }

    public long getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(long errorcode) {
        this.errorcode = errorcode;
    }

}
