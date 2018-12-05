package com.yelai.wearable.model;

import java.io.Serializable;

/**
 * Created by wanglei on 2016/12/10.
 */

public class Teacher implements Serializable {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
