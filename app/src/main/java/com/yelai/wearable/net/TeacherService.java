package com.yelai.wearable.net;

import com.yelai.wearable.model.MapDataResult;
import com.yelai.wearable.model.Page;
import com.yelai.wearable.model.Result;
import com.yelai.wearable.model.Sport;
import com.yelai.wearable.model.SportType;
import com.yelai.wearable.model.Teacher;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface TeacherService {

    /**
     * 获取运动的类型
     * @return
     */
    @POST("index.php/Home/Teacher/getList")
    Flowable<Result<List<Teacher>>> teacherList();


}
