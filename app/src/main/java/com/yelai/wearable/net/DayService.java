package com.yelai.wearable.net;

import com.yelai.wearable.model.Course;
import com.yelai.wearable.model.DayActionInfo;
import com.yelai.wearable.model.DayInfo;
import com.yelai.wearable.model.DaySportInfo;
import com.yelai.wearable.model.DayStatusInfo;
import com.yelai.wearable.model.EmptyDataResult;
import com.yelai.wearable.model.MapDataResult;
import com.yelai.wearable.model.Result;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface DayService {


    /**
     * 扫码加入考试
     * member_id
     * day  : 日期，时间戳类型 -->秒
     * @return
     */
    @POST("index.php/Home/Exam/addExamStudent")
    Flowable<MapDataResult> joinExam(@Body Map<String, Object> params);



    /**
     * 获取当天的运动描述
     * member_id
     * day  : 日期，时间戳类型 -->秒
     * @return
     */
    @POST("index.php/home/show/index")
    Flowable<Result<DayInfo>> dayInfo(@Body Map<String, Object> params);



    /**
     * 获取当天的行为详情
     * member_id
     * day  : 日期，时间戳类型 -->秒
     * @return
     */
    @POST("index.php/home/show/behave_detail")
    Flowable<Result<DayActionInfo>> behave(@Body Map<String, Object> params);



    /**
     * 获取当天的运动详情
     * member_id
     * day  : 日期，时间戳类型 -->秒
     * @return
     */
    @POST("index.php/home/show/sport_detail")
    Flowable<Result<DaySportInfo>> sport(@Body Map<String, Object> params);


    /**
     * 获取当天的状态详情
     * member_id
     * day  : 日期，时间戳类型 -->秒
     * @return
     */
    @POST("index.php/home/show/status_detail")
    Flowable<Result<DayStatusInfo>> status(@Body Map<String, Object> params);








}
