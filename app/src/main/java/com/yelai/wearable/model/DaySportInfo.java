package com.yelai.wearable.model;

import java.io.Serializable;

/**
 * Created by hr on 18/11/13.
 */

public class DaySportInfo implements Serializable{

        private String totalKm;
        private String totalTime;
        private String overTime;
        private String totalCal;
        private String bodyGrade;
        private String low;
        private String overLowTime;
        private String centre;
        private String overCentreTime;
        private String high;
        private String overHighTime;
        private String sportGrade;
        private String fat;
        private String heart;
        private String skeleton;
        private String power;


        public void setTotalKm(String totalKm) {
            this.totalKm = totalKm;
        }
        public String getTotalKm() {
            return totalKm;
        }

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
        }
        public String getTotalTime() {
            return totalTime;
        }

        public void setOverTime(String overTime) {
            this.overTime = overTime;
        }
        public String getOverTime() {
            return overTime;
        }

        public void setTotalCal(String totalCal) {
            this.totalCal = totalCal;
        }
        public String getTotalCal() {
            return totalCal;
        }

        public void setBodyGrade(String bodyGrade) {
            this.bodyGrade = bodyGrade;
        }
        public String getBodyGrade() {
            return bodyGrade;
        }

        public void setLow(String low) {
            this.low = low;
        }
        public String getLow() {
            return low;
        }

        public void setOverLowTime(String overLowTime) {
            this.overLowTime = overLowTime;
        }
        public String getOverLowTime() {
            return overLowTime;
        }

        public void setCentre(String centre) {
            this.centre = centre;
        }
        public String getCentre() {
            return centre;
        }

        public void setOverCentreTime(String overCentreTime) {
            this.overCentreTime = overCentreTime;
        }
        public String getOverCentreTime() {
            return overCentreTime;
        }

        public void setHigh(String high) {
            this.high = high;
        }
        public String getHigh() {
            return high;
        }

        public void setOverHighTime(String overHighTime) {
            this.overHighTime = overHighTime;
        }
        public String getOverHighTime() {
            return overHighTime;
        }

        public void setSportGrade(String sportGrade) {
            this.sportGrade = sportGrade;
        }
        public String getSportGrade() {
            return sportGrade;
        }

        public void setFat(String fat) {
            this.fat = fat;
        }
        public String getFat() {
            return fat;
        }

        public void setHeart(String heart) {
            this.heart = heart;
        }
        public String getHeart() {
            return heart;
        }

        public void setSkeleton(String skeleton) {
            this.skeleton = skeleton;
        }
        public String getSkeleton() {
            return skeleton;
        }

        public void setPower(String power) {
            this.power = power;
        }
        public String getPower() {
            return power;
        }

}
