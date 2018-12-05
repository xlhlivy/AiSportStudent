package com.yelai.wearable.model;

import java.io.Serializable;

/**
 * Created by hr on 18/11/13.
 */

public class DayInfo implements Serializable {

    /**
     * Auto-generated: 2018-11-13 12:54:13
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */

    /**
     *布尔变量  是否薄弱  0 薄弱  1不薄弱
     * 头部状态
     */
    private int headStatus;
    /**
     * 肩部状态
     */
    private int shoulderStatus;
    /**
     * 手臂状态
     */
    private int armStatus;
    /**
     * 胸部状态
     */
    private int chestStatus;
    /**
     * 腿部状态
     */
    private int legStatus;
    /**
     * 运动步数
     */
    private String totalStep;
    /**
     * 目标步数
     */
    private int targetStep;
    /**
     * 运动公里数
     */
    private String totalKm;
    /**
     * 目标公里数
     */
    private int targetKm;

    /**
     * 消耗卡路里
     */
    private String totalCal;
    /**
     * 目标消耗卡路里
     */
    private int targetCal;



    private Sport sport;
    private Behave behave;
    private Status status;

    public void setHeadStatus(int headStatus) {
        this.headStatus = headStatus;
    }

    public int getHeadStatus() {
        return headStatus;
    }

    public void setShoulderStatus(int shoulderStatus) {
        this.shoulderStatus = shoulderStatus;
    }

    public int getShoulderStatus() {
        return shoulderStatus;
    }

    public void setArmStatus(int armStatus) {
        this.armStatus = armStatus;
    }

    public int getArmStatus() {
        return armStatus;
    }

    public void setChestStatus(int chestStatus) {
        this.chestStatus = chestStatus;
    }

    public int getChestStatus() {
        return chestStatus;
    }

    public void setLegStatus(int legStatus) {
        this.legStatus = legStatus;
    }

    public int getLegStatus() {
        return legStatus;
    }

    public void setTotalStep(String totalStep) {
        this.totalStep = totalStep;
    }

    public String getTotalStep() {
        return totalStep;
    }

    public void setTargetStep(int targetStep) {
        this.targetStep = targetStep;
    }

    public int getTargetStep() {
        return targetStep;
    }

    public void setTotalKm(String totalKm) {
        this.totalKm = totalKm;
    }

    public String getTotalKm() {
        return totalKm;
    }

    public void setTargetKm(int targetKm) {
        this.targetKm = targetKm;
    }

    public int getTargetKm() {
        return targetKm;
    }

    public void setTotalCal(String totalCal) {
        this.totalCal = totalCal;
    }

    public String getTotalCal() {
        return totalCal;
    }

    public void setTargetCal(int targetCal) {
        this.targetCal = targetCal;
    }

    public int getTargetCal() {
        return targetCal;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Sport getSport() {
        return sport;
    }

    public void setBehave(Behave behave) {
        this.behave = behave;
    }

    public Behave getBehave() {
        return behave;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public interface HealthEvaluation{

        public int greenProgress();

        public int blueProgress();

        public int orangeProgress();

        public String greenTxt();

        public String blueTxt();

        public String orangeTxt();

        public int complatedProgress();

        public String rankTxt();

    }


    /**
     * Auto-generated: 2018-11-13 12:54:13
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public static class Sport implements HealthEvaluation,Serializable{

        /**
         * 活动量
         */
        private int num;
        /**
         * 有效活动量
         */
        private int effectiveNum;

        /**
         * 运动效益
         */
        private int benefit;

        /**
         * 分数目标
         */
        private int score;
        /**
         * 超过排名
         */
        private int rank;

        public void setNum(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public void setEffectiveNum(int effectiveNum) {
            this.effectiveNum = effectiveNum;
        }

        public int getEffectiveNum() {
            return effectiveNum;
        }

        public void setBenefit(int benefit) {
            this.benefit = benefit;
        }

        public int getBenefit() {
            return benefit;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getRank() {
            return rank;
        }

        @Override
        public int greenProgress() {
            return num;
        }

        @Override
        public int blueProgress() {
            return effectiveNum;
        }

        @Override
        public int orangeProgress() {
            return benefit;
        }

        @Override
        public String greenTxt() {
            return "活动量";
        }

        @Override
        public String blueTxt() {
            return "有效活动量";
        }

        @Override
        public String orangeTxt() {
            return "运动效益";
        }

        @Override
        public int complatedProgress() {
            return 0;
        }

        @Override
        public String rankTxt() {
            return "目标分数" + score + "  当前排名" + rank;
        }
    }


    /**
     * Auto-generated: 2018-11-13 12:54:13
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public static class Behave implements HealthEvaluation,Serializable{

        /**
         * 久坐指数
         */
        private int longSit;
        /**
         * 睡眠时长
         */
        private int sleep;
        /**
         * 消耗指数
         */
        private int consume;
        /**
         * 分数目标
         */
        private int score;
        /**
         * 超过排名
         */
        private int rank;
        public void setLongSit(int longSit) {
            this.longSit = longSit;
        }
        public int getLongSit() {
            return longSit;
        }

        public void setSleep(int sleep) {
            this.sleep = sleep;
        }
        public int getSleep() {
            return sleep;
        }

        public void setConsume(int consume) {
            this.consume = consume;
        }
        public int getConsume() {
            return consume;
        }

        public void setScore(int score) {
            this.score = score;
        }
        public int getScore() {
            return score;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
        public int getRank() {
            return rank;
        }

        @Override
        public int greenProgress() {
            return longSit;
        }

        @Override
        public int blueProgress() {
            return sleep;
        }

        @Override
        public int orangeProgress() {
            return consume;
        }


        @Override
        public String greenTxt() {
            return "久坐指数";
        }

        @Override
        public String blueTxt() {
            return "睡眠时长";
        }

        @Override
        public String orangeTxt() {
            return "消耗指数";
        }

        @Override
        public int complatedProgress() {
            return 0;
        }

        @Override
        public String rankTxt() {
            return "目标分数" + score + "  当前排名" + rank;
        }
    }



    /**
     * Auto-generated: 2018-11-13 12:54:13
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public static class Status implements HealthEvaluation,Serializable{

        /**
         * 压力指数
         */
        private int pressure;
        /**
         * 疲劳指数
         */
        private int tired;
        /**
         * 心血管指数
         */
        private int heart;
        /**
         * 分数目标
         */
        private int score;
        /**
         * 超过排名
         */
        private int rank;
        public void setPressure(int pressure) {
            this.pressure = pressure;
        }
        public int getPressure() {
            return pressure;
        }

        public void setTired(int tired) {
            this.tired = tired;
        }
        public int getTired() {
            return tired;
        }

        public void setHeart(int heart) {
            this.heart = heart;
        }
        public int getHeart() {
            return heart;
        }

        public void setScore(int score) {
            this.score = score;
        }
        public int getScore() {
            return score;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
        public int getRank() {
            return rank;
        }

        @Override
        public int greenProgress() {
            return pressure;
        }

        @Override
        public int blueProgress() {
            return tired;
        }

        @Override
        public int orangeProgress() {
            return heart;
        }

        @Override
        public String greenTxt() {
            return "压力指数";
        }

        @Override
        public String blueTxt() {
            return "疲劳指数";
        }

        @Override
        public String orangeTxt() {
            return "心血管指数";
        }

        @Override
        public int complatedProgress() {
            return 0;
        }

        @Override
        public String rankTxt() {
            return "目标分数" + score + "  当前排名" + rank;
        }
    }

}