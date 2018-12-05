package com.yelai.wearable.model;

import java.io.Serializable;

/**
 * Created by hr on 18/11/23.
 */

public class Physique implements Serializable {

    //体重
    private String weight;
    //身高
    private String height;
    //胸围
    private String bust;
    //腰围
    private String waistline;

    //上臀围
    private String upperArm;
    //大腿围
    private String thighArm;
    //小腿围
    private String crusArm;


    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBust() {
        return bust;
    }

    public void setBust(String bust) {
        this.bust = bust;
    }

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public String getUpperArm() {
        return upperArm;
    }

    public void setUpperArm(String upperArm) {
        this.upperArm = upperArm;
    }

    public String getThighArm() {
        return thighArm;
    }

    public void setThighArm(String thighArm) {
        this.thighArm = thighArm;
    }

    public String getCrusArm() {
        return crusArm;
    }

    public void setCrusArm(String crusArm) {
        this.crusArm = crusArm;
    }
}
