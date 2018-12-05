package com.yelai.wearable.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hr on 18/11/23.
 */

public class Gym implements Serializable {

    private String count;
    private String isClock;
    private String qrcodeClock;
    private String qrcodeUse;
    private List<Card> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getIsClock() {
        return isClock;
    }

    public void setIsClock(String isClock) {
        this.isClock = isClock;
    }

    public String getQrcodeClock() {
        return qrcodeClock;
    }

    public void setQrcodeClock(String qrcodeClock) {
        this.qrcodeClock = qrcodeClock;
    }

    public String getQrcodeUse() {
        return qrcodeUse;
    }

    public void setQrcodeUse(String qrcodeUse) {
        this.qrcodeUse = qrcodeUse;
    }

    public List<Card> getList() {
        return list;
    }

    public void setList(List<Card> list) {
        this.list = list;
    }
}
