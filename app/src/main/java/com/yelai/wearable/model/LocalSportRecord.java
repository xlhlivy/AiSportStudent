package com.yelai.wearable.model;

import java.io.Serializable;

/**
 * Created by hr on 18/11/6.
 */

public class LocalSportRecord implements Serializable {


    public Long _id;

    public String member_id;

    public int sport_id;

    public double total_km;

    public int total_step;

    public int total_time;

    public int heart_rate;

    public String lnt;

    public String lat;

    public String isUpload = "false";

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String isUpload() {
        return isUpload;
    }

    public void setUpload(String upload) {
        isUpload = upload;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public int getSport_id() {
        return sport_id;
    }

    public void setSport_id(int sport_id) {
        this.sport_id = sport_id;
    }

    public double getTotal_km() {
        return total_km;
    }

    public void setTotal_km(double total_km) {
        this.total_km = total_km;
    }

    public int getTotal_step() {
        return total_step;
    }

    public void setTotal_step(int total_step) {
        this.total_step = total_step;
    }

    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }

    public int getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(int heart_rate) {
        this.heart_rate = heart_rate;
    }

    public String getLnt() {
        return lnt;
    }

    public void setLnt(String lnt) {
        this.lnt = lnt;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "LocalSportRecord{" +
                "_id=" + _id +
                ", member_id='" + member_id + '\'' +
                ", sport_id=" + sport_id +
                ", total_km=" + total_km +
                ", total_step=" + total_step +
                ", total_time=" + total_time +
                ", heart_rate=" + heart_rate +
                ", lnt='" + lnt + '\'' +
                ", lat='" + lat + '\'' +
                ", isUpload='" + isUpload + '\'' +
                '}';
    }
}
