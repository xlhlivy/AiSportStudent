package com.yelai.wearable.model;

import java.io.Serializable;

/**
 * Created by hr on 18/11/13.
 */

public class DayActionInfo implements Serializable{
        private String grade;
        private String longSitTotal;
        private String longSitRate;
        private String longSitTime;
        private String calmTime;
        private String totalStep;
        private String sleep;
        private String sleepDepth;
        private String sleepPropose;
        private String totalCal;
        private String feedCal;
        private String consumeCal;
        private String proposCal;

    public String getLongSitTotal() {
        return longSitTotal;
    }

    public void setLongSitTotal(String longSitTotal) {
        this.longSitTotal = longSitTotal;
    }

    public void setGrade(String grade) {
            this.grade = grade;
        }
        public String getGrade() {
            return grade;
        }

        public void setLongSitRate(String longSitRate) {
            this.longSitRate = longSitRate;
        }
        public String getLongSitRate() {
            return longSitRate;
        }

        public void setLongSitTime(String longSitTime) {
            this.longSitTime = longSitTime;
        }
        public String getLongSitTime() {
            return longSitTime;
        }

        public void setCalmTime(String calmTime) {
            this.calmTime = calmTime;
        }
        public String getCalmTime() {
            return calmTime;
        }

        public void setTotalStep(String totalStep) {
            this.totalStep = totalStep;
        }
        public String getTotalStep() {
            return totalStep;
        }

        public void setSleep(String sleep) {
            this.sleep = sleep;
        }
        public String getSleep() {
            return sleep;
        }

        public void setSleepDepth(String sleepDepth) {
            this.sleepDepth = sleepDepth;
        }
        public String getSleepDepth() {
            return sleepDepth;
        }

        public void setSleepPropose(String sleepPropose) {
            this.sleepPropose = sleepPropose;
        }
        public String getSleepPropose() {
            return sleepPropose;
        }

        public void setTotalCal(String totalCal) {
            this.totalCal = totalCal;
        }
        public String getTotalCal() {
            return totalCal;
        }

        public void setFeedCal(String feedCal) {
            this.feedCal = feedCal;
        }
        public String getFeedCal() {
            return feedCal;
        }

        public void setConsumeCal(String consumeCal) {
            this.consumeCal = consumeCal;
        }
        public String getConsumeCal() {
            return consumeCal;
        }

        public void setProposCal(String proposCal) {
            this.proposCal = proposCal;
        }
        public String getProposCal() {
            return proposCal;
        }
}
