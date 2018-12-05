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
interface DayContract {

    enum class Success{

        DayInfo,
        Behave,
        Sport,
        Status,
        CardList,
        UseCard,
        JoinExam,
    }

    interface View : HRIView<Presenter> {

        fun success(type:Success,data:Any){}

    }

    interface Presenter : IPresent<View> {


        /**
         * 获取当天的运动描述
         * member_id
         * day  : 日期，时间戳类型 -->秒
         * @return
         */
         fun dayInfo(day:Int)


        /**
         * 获取当天的行为详情
         * member_id
         * day  : 日期，时间戳类型 -->秒
         * @return
         */
         fun behave(day:Int)

        /**
         * 获取当天的运动详情
         * member_id
         * day  : 日期，时间戳类型 -->秒
         * @return
         */
         fun sport(day:Int)


        /**
         * 获取当天的状态详情
         * member_id
         * day  : 日期，时间戳类型 -->秒
         * @return
         */
         fun status(day:Int)


        //卡券列表
        fun cardList()

        //1 待使用 2已使用
        fun useCard(cardId:String,status:Int)

        fun joinExam(itemId:String)

    }
}