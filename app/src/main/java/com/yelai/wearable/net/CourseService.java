package com.yelai.wearable.net;

import com.yelai.wearable.model.Card;
import com.yelai.wearable.model.Course;
import com.yelai.wearable.model.CourseDetail;
import com.yelai.wearable.model.EmptyDataResult;
import com.yelai.wearable.model.Gym;
import com.yelai.wearable.model.MapDataResult;
import com.yelai.wearable.model.Message;
import com.yelai.wearable.model.Page;
import com.yelai.wearable.model.Result;
import com.yelai.wearable.model.Teacher;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface CourseService {

    /**
     * 获取老师的课程列表
     * teacher_id
     * type  : 1：教学课 2：训练课 3：兴趣课
     * @return
     */
    @POST("index.php/Home/Lesson/getLessonByStudent")
    Flowable<Result<List<Course>>> courseList(@Body Map<String, Object> params);




    /**
     * 添加反馈
     * times_id
     * @return
     */
    @POST("index.php/Home/Member/feedBack")
    Flowable<Result<MapDataResult>> feedback(@Body Map<String,Object> params);

    /**
     * 图片上传
     * times_id
     * @return
     */
    @POST("index.php/Home/Member/upload_img")
    Flowable<EmptyDataResult> imageUpload(@Body Map<String,Object> params);


    /**
     * 学生报名
     * member_id
     * lesson_id
     * @return
     */
    @POST("index.php/Home/Lesson/sign")
    Flowable<MapDataResult> sign(@Body Map<String, Object> params);



    /**
     * 学生报名
     * member_id
     * lesson_id
     * @return
     */
    @POST("index.php/Home/Lesson/signOut")
    Flowable<MapDataResult> signOut(@Body Map<String, Object> params);


    /**
     * member_id
     * @param params
     * @return
     */
    @POST("index.php/Home/Lesson/getStudentLesson")
    Flowable<Result<List<Course>>> myCourseList(@Body Map<String, Object> params);



    /**
     * lesson_id
     * @param params
     * @return
     */
    @POST("index.php/Home/Lesson/getLessonDetail")
    Flowable<Result<CourseDetail>> courseDetail(@Body Map<String, Object> params);


    /**
     * lesson_id
     * page
     * size
     * @param params
     * @return
     */
    @POST("index.php/Home/Lesson/getMessage")
    Flowable<Result<Page<List<Message>>>> messageList(@Body Map<String, Object> params);


    /**
     * lesson_id
     * member_id
     * message
     * @param params
     * @return
     */
    @POST("index.php/Home/Lesson/addMessage")
    Flowable<MapDataResult> publishMessage(@Body Map<String, Object> params);



    /**
     * member_id
     * @param params
     * @return
     */
    @POST("index.php/Home/Card/getCardList")
    Flowable<Result<List<Card>>> cardList(@Body Map<String, Object> params);


    /**
     * member_id
     * card_id
     * status  1 待使用  2 已使用
     * @param params
     * @return
     */
    @POST("index.php/Home/Card/setCardStatus")
    Flowable<MapDataResult> useCard(@Body Map<String, Object> params);


    /**
     * member_id
     * @param params
     * @return
     */
    @POST("index.php/Home/Card/punchClock")
    Flowable<MapDataResult> punchClock(@Body Map<String, Object> params);


    /**
     * member_id
     * gym_id
     * @param params
     * @return
     */
    @POST("index.php/Home/Card/getGymCardList")
    Flowable<Result<Gym>> gymInfo(@Body Map<String, Object> params);



}
