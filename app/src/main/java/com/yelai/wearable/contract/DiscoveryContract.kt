package com.yelai.wearable.contract

import cn.droidlover.xdroidmvp.mvp.IPresent
import cn.droidlover.xdroidmvp.mvp.IView
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
interface DiscoveryContract {

    interface View : IView<Presenter> {

        fun showError(errorMsg: String){}

        fun detail(detail: DiscoveryDetail){}

        fun list(page:Page<List<DiscoveryItem>>){}

        fun showIndicator(){}

        fun hideIndicator(){}

    }

    interface Presenter : IPresent<View> {

         fun detail(@Body params: Map<String, Any>)

         fun list(@Body params: Map<String, Any>)

    }
}