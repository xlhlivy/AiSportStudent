package com.yelai.wearable.present

import cn.droidlover.xdroidmvp.mvp.XPresent
import cn.droidlover.xdroidmvp.net.ApiSubscriber
import cn.droidlover.xdroidmvp.net.NetError
import cn.droidlover.xdroidmvp.net.XApi
import com.yelai.wearable.AppData
import com.yelai.wearable.contract.SportContract
import com.yelai.wearable.contract.TeacherContract
import com.yelai.wearable.model.*
import com.yelai.wearable.net.Api

/**
 * Created by wanglei on 2016/12/31.
 */

class PTeacher : XPresent<TeacherContract.View>(), TeacherContract.Presenter {
    override fun teacherList() {
//        v.showIndicator()
        Api.getTeacherService().teacherList()
                .compose(XApi.getApiTransformer<Result<List<Teacher>>>())
                .compose(XApi.getScheduler<Result<List<Teacher>>>())
                .compose(v.bindToLifecycle<Result<List<Teacher>>>())
                .subscribe(object : ApiSubscriber<Result<List<Teacher>>>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(result: Result<List<Teacher>>) {
                        v.hideIndicator()
                        v.success(result.data)
                    }
                })
    }


}
