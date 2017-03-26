package com.radartech.model;

/**
 * Created by sandeep on 15-11-2016.
 */
public class SettingSpinnerItemData {

    String text;
    Integer imageId;
    public SettingSpinnerItemData(String text, Integer imageId){
        this.text=text;
        this.imageId=imageId;
    }

    public String getText(){
        return text;
    }

    public Integer getImageId(){
        return imageId;
    }
}

