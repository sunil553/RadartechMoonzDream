package com.radartech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.radartech.model.login.UserData;

public class APIBaseResponse {

    @SerializedName("record")
    @Expose
    private UserData userData;
    @SerializedName("errorcode")
    @Expose
    private Integer errorcode;

    /**
     * @return The record
     */
    public UserData getUserData() {
        return userData;
    }

    /**
     * @param userData The record
     */
    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    /**
     * @return The errorcode
     */
    public Integer getErrorcode() {
        return errorcode;
    }

    /**
     * @param errorcode The errorcode
     */
    public void setErrorcode(Integer errorcode) {
        this.errorcode = errorcode;
    }

}
