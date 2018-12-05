package com.yelai.wearable.present

import cn.droidlover.xdroidmvp.mvp.XPresent
import cn.droidlover.xdroidmvp.net.ApiSubscriber
import cn.droidlover.xdroidmvp.net.NetError
import cn.droidlover.xdroidmvp.net.XApi
import com.yelai.wearable.AppData
import com.yelai.wearable.contract.SportContract
import com.yelai.wearable.model.*
import com.yelai.wearable.net.Api

/**
 * Created by wanglei on 2016/12/31.
 */

class PSport : XPresent<SportContract.View>(), SportContract.Presenter {
    override fun sportHistory(params: Map<String, Any>) {
        v.showIndicator()
        Api.getSportService().sportHistory(params)
                .compose(XApi.getApiTransformer<Result<Page<List<Sport>>>>())
                .compose(XApi.getScheduler<Result<Page<List<Sport>>>>())
                .compose(v.bindToLifecycle<Result<Page<List<Sport>>>>())
                .subscribe(object : ApiSubscriber<Result<Page<List<Sport>>>>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(result: Result<Page<List<Sport>>>) {
                        v.hideIndicator()
                        v.list(result.data)
                    }
                })
    }

    override fun sportType() {

        v.showIndicator()
        Api.getSportService().sportType()
                .compose(XApi.getApiTransformer<Result<List<SportType>>>())
                .compose(XApi.getScheduler<Result<List<SportType>>>())
                .compose(v.bindToLifecycle<Result<List<SportType>>>())
                .subscribe(object : ApiSubscriber<Result<List<SportType>>>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(result: Result<List<SportType>>) {
                        v.hideIndicator()
                        v.success(SportContract.Success.Type, result.data)
                    }
                })
    }

    override fun sportStart(params: Map<String, Any>) {
        v.showIndicator()

        Api.getSportService().sportStart(params)
                .compose<MapDataResult>(XApi.getApiTransformer())
                .compose<MapDataResult>(XApi.getScheduler())
                .compose<MapDataResult>(v.bindToLifecycle())
                .subscribe(object : ApiSubscriber<MapDataResult>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(result: MapDataResult) {
                        v.hideIndicator()
                        v.success(SportContract.Success.Start, result.getValue("sport_id"))
                    }
                })
    }

    override fun sporting(params: List<LocalSportRecord>) {
//        v.showIndicator()


        var list = ArrayList<Map<String,Any>>()

        params.forEach {
            list.add(mapOf(
                    "member_id" to it.member_id,
                    "sport_id" to it.sport_id,
                    "total_km" to it.total_km,
                    "total_time" to it.total_time,
                    "heart_rate" to it.heart_rate,
                    "total_step" to it.total_step,
                    "lat" to it.lat,
                    "lnt" to it.lnt
            ))
        }


        Api.getSportService().sporting(list)
                .compose<MapDataResult>(XApi.getApiTransformer())
                .compose<MapDataResult>(XApi.getScheduler())
                .compose<MapDataResult>(v.bindToLifecycle())
                .subscribe(object : ApiSubscriber<MapDataResult>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(result: MapDataResult) {
//                        v.hideIndicator()
                        v.success(SportContract.Success.Sporting, "")
                    }
                })
    }

    override fun sportFinish(params: List<LocalSportRecord>) {
        v.showIndicator()

        var list = ArrayList<Map<String,Any>>()

        params.forEach {
            list.add(mapOf(
                    "member_id" to it.member_id,
                    "sport_id" to it.sport_id,
                    "total_km" to it.total_km,
                    "total_time" to it.total_time,
                    "heart_rate" to it.heart_rate,
                    "total_step" to it.total_step,
                    "lat" to it.lat,
                    "lnt" to it.lnt
            ))
        }

        Api.getSportService().sportFinish(list)
                .compose<MapDataResult>(XApi.getApiTransformer())
                .compose<MapDataResult>(XApi.getScheduler())
                .compose<MapDataResult>(v.bindToLifecycle())
                .subscribe(object : ApiSubscriber<MapDataResult>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }
                    override fun onNext(result: MapDataResult) {
                        v.hideIndicator()
                        v.success(SportContract.Success.Finish, "")
                    }
                })
    }

    override fun sportDetail(params: Map<String, Any>) {
        v.showIndicator()
        Api.getSportService().sportDetail(params)
                .compose<Result<Sport>>(XApi.getApiTransformer())
                .compose<Result<Sport>>(XApi.getScheduler())
                .compose<Result<Sport>>(v.bindToLifecycle())
                .subscribe(object : ApiSubscriber<Result<Sport>>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }
                    override fun onNext(result: Result<Sport>) {
                        v.hideIndicator()
                        v.success(SportContract.Success.Detail, result.data)
                    }
                })
    }

    override fun sportStatistics(params: Map<String, Any>) {
        v.showIndicator()
        Api.getSportService().sportStatistics(params)
                .compose<Result<Sport>>(XApi.getApiTransformer())
                .compose<Result<Sport>>(XApi.getScheduler())
                .compose<Result<Sport>>(v.bindToLifecycle())
                .subscribe(object : ApiSubscriber<Result<Sport>>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }
                    override fun onNext(result: Result<Sport>) {
                        v.hideIndicator()
                        v.success(SportContract.Success.Statistics, result.data)
                    }
                })
    }



}
