package com.yelai.wearable.model;

import java.io.Serializable;

/**
 * Created by hr on 18/11/13.
 */

public class DayStatusInfo implements Serializable{

        private int pressureGrade;
        private String pressure;
        private int tiredGrade;
        private String tired;
        private int heartGrade;
        private int quiet;
        private int recent;
        private int morning;
        private String resume;
        private int foundation;
        private int pulse;
        public void setPressureGrade(int pressureGrade) {
            this.pressureGrade = pressureGrade;
        }
        public int getPressureGrade() {
            return pressureGrade;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }
        public String getPressure() {
            return pressure;
        }

        public void setTiredGrade(int tiredGrade) {
            this.tiredGrade = tiredGrade;
        }
        public int getTiredGrade() {
            return tiredGrade;
        }

        public void setTired(String tired) {
            this.tired = tired;
        }
        public String getTired() {
            return tired;
        }

        public void setHeartGrade(int heartGrade) {
            this.heartGrade = heartGrade;
        }
        public int getHeartGrade() {
            return heartGrade;
        }

        public void setQuiet(int quiet) {
            this.quiet = quiet;
        }
        public int getQuiet() {
            return quiet;
        }

        public void setRecent(int recent) {
            this.recent = recent;
        }
        public int getRecent() {
            return recent;
        }

        public void setMorning(int morning) {
            this.morning = morning;
        }
        public int getMorning() {
            return morning;
        }

        public void setResume(String resume) {
            this.resume = resume;
        }
        public String getResume() {
            return resume;
        }

        public void setFoundation(int foundation) {
            this.foundation = foundation;
        }
        public int getFoundation() {
            return foundation;
        }

        public void setPulse(int pulse) {
            this.pulse = pulse;
        }
        public int getPulse() {
            return pulse;
        }

}
