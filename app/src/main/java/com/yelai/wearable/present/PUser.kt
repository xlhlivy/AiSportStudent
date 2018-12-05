package com.yelai.wearable.present

import cn.droidlover.xdroidmvp.mvp.XPresent
import cn.droidlover.xdroidmvp.net.ApiSubscriber
import cn.droidlover.xdroidmvp.net.NetError
import cn.droidlover.xdroidmvp.net.XApi
import com.yelai.wearable.AppData
import com.yelai.wearable.contract.LoginContract
import com.yelai.wearable.contract.UserContract
import com.yelai.wearable.model.EmptyDataResult
import com.yelai.wearable.model.UserInfoResult
import com.yelai.wearable.model.UserResult
import com.yelai.wearable.net.Api
import com.yelai.wearable.ui.login.LoginActivity

/**
 * Created by hr on 18/9/15.
 */

class PUser : XPresent<UserContract.View>(), UserContract.Presenter {
    override fun memberInfo(params: Map<String, Any>) {
        v.showIndicator()
        Api.getLoginService().memberInfo(params)
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
                        AppData.userInfo = result.data
                        v.memberInfoSuccess()
                    }
                })
    }
}
