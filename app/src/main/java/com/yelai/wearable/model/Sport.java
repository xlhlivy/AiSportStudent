package com.yelai.wearable.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2016/12/10.
 */

public class Sport implements Serializable {

    private String id;
    private String memberId;
    private String type;
    private String status;
    private String totalKm;
    private String totalStep;
    private String totalTime;
    private String useCal;

    private Long createTime;

    private Long speed;

    private Long endTime;
    private List<SportDetail.Item> detail;


    private String maxHeartRate;
    private String avgHeartRate;
    private String rate;

    private String strength;
    private String density;

    private Long recoveryTime;

    public String getMaxHeartRate() {
        return maxHeartRate;
    }

    public void setMaxHeartRate(String maxHeartRate) {
        this.maxHeartRate = maxHeartRate;
    }

    public String getAvgHeartRate() {
        return avgHeartRate;
    }

    public void setAvgHeartRate(String avgHeartRate) {
        this.avgHeartRate = avgHeartRate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public Long getRecoveryTime() {
        return recoveryTime;
    }

    public void setRecoveryTime(Long recoveryTime) {
        this.recoveryTime = recoveryTime;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(String totalKm) {
        this.totalKm = totalKm;
    }

    public String getTotalStep() {
        return totalStep;
    }

    public void setTotalStep(String totalStep) {
        this.totalStep = totalStep;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getUseCal() {
        return useCal;
    }

    public void setUseCal(String useCal) {
        this.useCal = useCal;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public List<SportDetail.Item> getDetail() {
        return detail;
    }

    public void setDetail(List<SportDetail.Item> detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Sport{" +
                "id='" + id + '\'' +
                ", memberId='" + memberId + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", totalKm='" + totalKm + '\'' +
                ", totalStep='" + totalStep + '\'' +
                ", totalTime='" + totalTime + '\'' +
                ", useCal='" + useCal + '\'' +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                ", detail=" + detail +
                '}';
    }

    public static class Item{
        private String heartRate;
        private Double lat;
        private Double lnt;
        private Long createTime;


        public String getHeartRate() {
            return heartRate;
        }

        public void setHeartRate(String heartRate) {
            this.heartRate = heartRate;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLnt() {
            return lnt;
        }

        public void setLnt(Double lnt) {
            this.lnt = lnt;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "heartRate='" + heartRate + '\'' +
                    ", lat=" + lat +
                    ", lnt=" + lnt +
                    ", createTime=" + createTime +
                    '}';
        }
    }




}
