package com.yelai.wearable.contract

import cn.droidlover.xdroidmvp.mvp.IPresent
import cn.droidlover.xdroidmvp.mvp.IView
import com.yelai.wearable.model.UserInfoResult
import com.yelai.wearable.model.UserResult
import com.yelai.wearable.ui.login.LoginActivity
import java.util.*

/**
 * Created by xuhao on 2017/11/30.
 * desc: 搜索契约类
 */
interface UserContract {

    interface View : IView<Presenter> {

        fun showError(errorMsg: String)

        fun memberInfoSuccess()

        fun showIndicator()

        fun hideIndicator()

    }

    interface Presenter : IPresent<View> {

        fun memberInfo(params:Map<String,Any>)

    }
}