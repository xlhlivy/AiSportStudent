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
interface LoginContract {

    interface View : HRIView<Presenter> {

        fun countDownVerifyCode(){}

        fun success()

        fun modifyPasswordSuccess(){}

//        fun showError(errorMsg: String)
//
//        fun showIndicator()
//
//        fun hideIndicator()

    }

    interface Presenter : IPresent<View> {

        fun sendVerifyCode(mobile:String,type:Int = 1)

        fun login(params:Map<String,Any>)

        fun setMember(userInfo:UserInfoResult.UserInfo)

        fun modifyPassword(phone:String,code:String,password:String)

    }
}