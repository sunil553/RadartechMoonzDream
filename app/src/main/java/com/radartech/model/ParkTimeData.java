package com.radartech.model;

/**
 * Created by sandeep on 15-11-2016.
 */
public class ParkTimeData {

    private long currentHeartTime;
    private long previousHeartTime;

    public ParkTimeData(long currentHeartTime, long previousHeartTime) {
        this.currentHeartTime = currentHeartTime;
        this.previousHeartTime = previousHeartTime;
    }

    public long getCurrentHeartTime() {
        return currentHeartTime;
    }

    public void setCurrentHeartTime(long currentHeartTime) {
        this.currentHeartTime = currentHeartTime;
    }

    public long getPreviousHeartTime() {
        return previousHeartTime;
    }

    public void setPreviousHeartTime(long previousHeartTime) {
        this.previousHeartTime = previousHeartTime;
    }
}

