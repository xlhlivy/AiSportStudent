package com.yelai.wearable.entity;

import java.io.Serializable;

/**
 * Created by hr on 18/11/9.
 */

public class LocalLog implements Serializable{

    public Long _id;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    private String time;

    private String message;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LocalLog{" +
                "_id=" + _id +
                ", time='" + time + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
