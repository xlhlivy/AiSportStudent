package com.yelai.wearable.contract

import cn.droidlover.xdroidmvp.mvp.IPresent
import cn.droidlover.xdroidmvp.mvp.IView
import com.yelai.wearable.base.ViewPage
import com.yelai.wearable.model.*
import com.yelai.wearable.ui.login.LoginActivity
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

/**
 * Created by xuhao on 2017/11/30.
 * desc: 资讯和信息
 */
interface SportContract {

    enum class Success{
        Type,
        Start,
        Sporting,
        Finish,
        Detail,
        Statistics
    }

    interface View : IView<Presenter> {

        fun showError(errorMsg: String)

        fun success(type:Success,data:Any){}

        fun list(page:Page<List<Sport>>){}

        fun showIndicator()

        fun hideIndicator()

    }

    interface Presenter : IPresent<View> {

        /**
         * 获取运动的类型
         * @return
         */
        fun sportType()

        fun sportStart(@Body params: Map<String, Any>)

        fun sporting(@Body params: List<LocalSportRecord>)

        fun sportFinish(@Body params: List<LocalSportRecord>)

        fun sportDetail(@Body params: Map<String, Any>)

        fun sportStatistics(@Body params: Map<String, Any>)

        fun sportHistory(@Body params:Map<String,Any>)

    }
}