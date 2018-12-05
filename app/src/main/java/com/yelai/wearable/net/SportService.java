package com.yelai.wearable.net;

import com.yelai.wearable.model.DiscoveryDetail;
import com.yelai.wearable.model.DiscoveryItem;
import com.yelai.wearable.model.EmptyDataResult;
import com.yelai.wearable.model.MapDataResult;
import com.yelai.wearable.model.Page;
import com.yelai.wearable.model.Result;
import com.yelai.wearable.model.Sport;
import com.yelai.wearable.model.SportType;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface SportService {

    /**
     * 获取运动的类型
     * @return
     */
    @POST("index.php/home/sport/sport_type")
    Flowable<Result<List<SportType>>> sportType();


    /**
     * 开始运动
     * member_id userid
     * type      sport type
     * lnt       经度
     * lat       纬度

     * @return   {sport_id:1}
     */
    @POST("index.php/home/sport/start_sport")
    Flowable<MapDataResult> sportStart(@Body Map<String,Object> params);



    /**
     * 运动中汇报数据
     * member_id userid
     * sport_id
     * total_km
     * total_step
     * total_time
     * heart_rate
     * lnt       经度
     * lat       纬度
     * @return
     */
    @POST("index.php/home/sport/sporting")
    Flowable<MapDataResult> sporting(@Body List<Map<String,Object>>params);


    /**
     * 结束运动
     * member_id userid
     * sport_id
     * total_km
     * total_step
     * total_time
     * heart_rate
     * lnt       经度
     * lat       纬度
     * @return
     */
    @POST("index.php/home/sport/end_sport")
    Flowable<MapDataResult> sportFinish(@Body List<Map<String,Object>> params);


    /**
     * 运动详情
     * member_id userid
     * sport_id
     * @return
     */
    @POST("index.php/home/sport/sport_detail")
    Flowable<Result<Sport>> sportDetail(@Body Map<String,Object> params);


    /**
     * 获取运动统计数据
     * member_id userid
     * type
     * @return
     */
    @POST("index.php/home/sport/sport_total")
    Flowable<Result<Sport>> sportStatistics(@Body Map<String,Object> params);


    /**
     * 获取运动历史
     * member_id
     * type
     * page
     * size
     * @return
     */
    @POST("index.php/home/sport/history_sport")
    Flowable<Result<Page<List<Sport>>>> sportHistory(@Body Map<String,Object> params);


}
