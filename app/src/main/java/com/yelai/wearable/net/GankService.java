package com.yelai.wearable.net;

import com.yelai.wearable.model.EmptyDataResult;
import com.yelai.wearable.model.GankResults;

import java.util.Map;
import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankService {

    @GET("data/{type}/{number}/{page}")
    Flowable<GankResults> getGankData(@Path("type") String type,
                                      @Path("number") int pageSize,
                                      @Path("page") int pageNum);


    @POST("index.php/home/member/send_sms")
    Flowable<EmptyDataResult> getVerifyCode(@Body Map<String,String> params);





    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @FormUrlEncoded
    @POST("index.php/home/member/send_sms")
    Flowable<EmptyDataResult> getVerifyCode2(@Field("mobile") String mobile);



}
