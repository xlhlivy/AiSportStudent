package com.yelai.wearable.present

import cn.droidlover.xdroidmvp.mvp.XPresent
import cn.droidlover.xdroidmvp.net.ApiSubscriber
import cn.droidlover.xdroidmvp.net.NetError
import cn.droidlover.xdroidmvp.net.XApi
import com.yelai.wearable.AppData
import com.yelai.wearable.contract.LoginContract
import com.yelai.wearable.model.MapDataResult
import com.yelai.wearable.model.UserInfoResult
import com.yelai.wearable.net.Api
import com.yelai.wearable.net.LoginService
import com.yelai.wearable.transfer

/**
 * Created by hr on 18/9/15.
 */

class PLogin : XPresent<LoginContract.View>(), LoginContract.Presenter {

    override fun modifyPassword(phone:String,code:String,password:String) {
        v.showIndicator()
        Api.getService<LoginService>(LoginService::class.java)
                .modifyPassword(mapOf("member_id" to AppData.user!!.memberId,"mobile" to phone,"code" to code,"password" to password))
                .transfer(v)
                .subscribe({
                    v.hideIndicator()
                    v.modifyPasswordSuccess()
                })
    }

    override fun setMember(userInfo: UserInfoResult.UserInfo) {
        v.showIndicator()
        Api.getLoginService().setMember(userInfo)
                .compose(XApi.getApiTransformer<UserInfoResult>())
                .compose(XApi.getScheduler<UserInfoResult>())
                .compose(v.bindToLifecycle<UserInfoResult>())
                .subscribe(object : ApiSubscriber<UserInfoResult>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(result: UserInfoResult) {
                        v.hideIndicator()
                        AppData.userInfo = userInfo
                        v.success()
                    }
                })
    }

    override fun login(params: Map<String, Any>) {
        v.showIndicator()
        Api.getLoginService().login(params)
                .transfer(v)
                .subscribe({
                    v.hideIndicator()
                    AppData.user = it.data
                    v.success()
                })
    }




    override fun sendVerifyCode(mobile: String,type:Int) {
        v.showIndicator()
        Api.getLoginService().getVerifyCode(mapOf("mobile" to mobile,"type" to type))
                .compose(XApi.getApiTransformer<MapDataResult>())
                .compose(XApi.getScheduler<MapDataResult>())
                .compose(v.bindToLifecycle<MapDataResult>())
                .subscribe(object : ApiSubscriber<MapDataResult>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(gankResults: MapDataResult) {
                        v.hideIndicator()
                        v.countDownVerifyCode()
                    }
                })

    }
}
