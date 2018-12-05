package com.yelai.wearable.net;

import com.yelai.wearable.model.MapDataResult;
import com.yelai.wearable.model.Physique;
import com.yelai.wearable.model.Result;
import com.yelai.wearable.model.UserInfoResult;
import com.yelai.wearable.model.UserResult;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface PhysiqueService {



    @POST("index.php/Home/Member/getShape")
    Flowable<Result<Physique>> getShape(@Body Map<String, Object> params);



    @POST("index.php/Home/Member/setShape")
    Flowable<Result<MapDataResult>> setShape(@Body Map<String, Object> params);



}
