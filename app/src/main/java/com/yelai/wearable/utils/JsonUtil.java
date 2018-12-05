package com.yelai.wearable.utils;

import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Json解析工具类
 * Created by 洋 on 2016/5/4.
 */
public class JsonUtil {



    /**
     * list集合转化为json
     */
    public static String listTojson(List list) {

        String json = null;
        Gson gson = new Gson();
        json = gson.toJson(list);
        return json;
    }

    /**
     * json转化为list,获取坐标点集合
     * @param json
     * @return
     */
    public static List<LatLng> jsonToListPoint(String json) {

        List<LatLng> list = null;
        Gson gson = new Gson();

        list = gson.fromJson(json, new TypeToken<List<LatLng>>() {
        }.getType());

        return list;
    }

    /**
     * json转化为list,获取速度集合
     * @param json
     * @return
     */
    public static List<Float> jsonToListSpeed(String json){

        List<Float> list = null;
        Gson gson = new Gson();

        list = gson.fromJson(json,new TypeToken<List<Float>>(){}.getType());

        return list;

    }


}
