package com.yelai.wearable.net;

import com.yelai.wearable.model.EmptyDataResult;
import com.yelai.wearable.model.GankResults;
import com.yelai.wearable.model.UserResult;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.net.XApi;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.flowable.FlowableCreate;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static final String API_BASE_URL = "http://sport.aieye8.com/";

    public static final String IMAGE_URL = API_BASE_URL  + "data/upload/";


    private static HashMap<Class,Object> serviceMap = new HashMap<>();

    public static <T> T getService(Class clazz){
        if(!serviceMap.containsKey(clazz)){
            synchronized (Api.class){
                if(!serviceMap.containsKey(clazz)){
                    serviceMap.put(clazz,XApi.getInstance().getRetrofit(API_BASE_URL, true).create(clazz));
                }
            }
        }
        return (T)serviceMap.get(clazz);
    }


    private static GankService gankService;

    @Deprecated
    public static GankService getGankService() {
        if (gankService == null) {
            synchronized (Api.class) {
                if (gankService == null) {
                    gankService = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(GankService.class);
                }
            }
        }

//        loginService.login(null)
//                .onErrorResumeNext(new Function<Throwable, Publisher<? extends UserResult>>() {
//                    @Override
//                    public Publisher<? extends UserResult> apply(Throwable throwable) throws Exception {
//                        return new Publisher<UserResult>() {
//                            @Override
//                            public void subscribe(Subscriber<? super UserResult> s) {
//                                s.onComplete();
//                            }
//                        };
//                    }
//                })
//                .subscribe();

        return gankService;
    }



    private static LoginService loginService;


    @Deprecated
    public static LoginService getLoginService() {
        if (loginService == null) {
            synchronized (Api.class) {
                if (loginService == null) {
                    loginService = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(LoginService.class);
                }
            }
        }
        return loginService;
    }



    private static DiscoveryService discoveryService;
    @Deprecated
    public static DiscoveryService getDiscoveryService() {
        if (discoveryService == null) {
            synchronized (Api.class) {
                if (discoveryService == null) {
                    discoveryService = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(DiscoveryService.class);
                }
            }
        }
        return discoveryService;
    }


    private static SportService sportService;
    @Deprecated
    public static SportService getSportService() {
        if (sportService == null) {
            synchronized (Api.class) {
                if (sportService == null) {
                    sportService = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(SportService.class);
                }
            }
        }
        return sportService;
    }


    private static TeacherService teacherService;
    @Deprecated
    public static TeacherService getTeacherService() {
        if (teacherService == null) {
            synchronized (Api.class) {
                if (teacherService == null) {
                    teacherService = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(TeacherService.class);
                }
            }
        }
        return teacherService;
    }




}
