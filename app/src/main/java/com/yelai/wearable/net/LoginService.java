package com.yelai.wearable.net;

import com.yelai.wearable.model.EmptyDataResult;
import com.yelai.wearable.model.GankResults;
import com.yelai.wearable.model.MapDataResult;
import com.yelai.wearable.model.UserInfoResult;
import com.yelai.wearable.model.UserResult;

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

public interface LoginService {


    /**
     * 获取验证码接口
     *
     * mobile : 电话
     * type : 1 登录  2 注册  3 重置密码
     *
     * @param params
     * @return
     */
    @POST("index.php/home/member/send_sms")
    Flowable<MapDataResult> getVerifyCode(@Body Map<String, Object> params);

    @POST("index.php/Home/Member/modifyPwd")
    Flowable<MapDataResult> modifyPassword(@Body Map<String, Object> params);


    /**
     * mobile
     * code
     * password
     * @param params
     * @return
     */
    @POST("index.php/home/member/register")
    Flowable<UserResult> register(@Body Map<String,String> params);




    @POST("index.php/home/member/login")
    Flowable<UserResult> login(@Body Map<String,Object> params);


    @POST("index.php/home/member/setMember")
    Flowable<UserInfoResult> setMember(@Body UserInfoResult.UserInfo userInfo);


    /**
     * member_id
     *
     * @param params
     * @return
     */
    @POST("index.php/Home/Member/getMemberInfo")
    Flowable<UserInfoResult> memberInfo(@Body Map<String,Object> params);




}
