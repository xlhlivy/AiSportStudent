package com.yelai.wearable.net

import cn.droidlover.xdroidmvp.mvp.IPresent
import cn.droidlover.xdroidmvp.mvp.IView
import com.yelai.wearable.contract.HRIView
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
interface PhysiqueContract {

    enum class Success{

        GetShape,
        SetShape,

    }

    interface View : HRIView<Presenter> {

        fun success(type:Success,data:Any){}

    }

    interface Presenter : IPresent<View> {

        fun setShape(physique:Physique)

        fun getShape()

    }
}