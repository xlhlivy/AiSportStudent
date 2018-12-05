package com.yelai.wearable.net

import cn.droidlover.xdroidmvp.mvp.XPresent
import com.yelai.wearable.AppData
import com.yelai.wearable.transfer

/**
 * Created by hr on 18/9/15.
 */

class PDay : XPresent<DayContract.View>(), DayContract.Presenter {
    override fun joinExam(itemId: String) {
        v.showIndicator()
        Api.getService<DayService>(DayService::class.java)
                .joinExam(mapOf("member_id" to AppData.user!!.memberId, "exam_id" to itemId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(DayContract.Success.JoinExam, it.data)
                }
    }


    override fun useCard(cardId: String, status: Int) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .useCard(mapOf("member_id" to AppData.user!!.memberId,"card_id" to cardId,"status" to status))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(DayContract.Success.UseCard, it.data)
                }
    }

    override fun cardList() {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .cardList(mapOf("member_id" to AppData.user!!.memberId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(DayContract.Success.CardList, it.data)
                }
    }


    override fun dayInfo(day: Int) {
        v.showIndicator()
        Api.getService<DayService>(DayService::class.java)
                .dayInfo(mapOf("member_id" to AppData.user!!.memberId, "day" to day))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(DayContract.Success.DayInfo, it.data)
                }
    }

    override fun behave(day: Int) {
        v.showIndicator()
        Api.getService<DayService>(DayService::class.java)
                .behave(mapOf("member_id" to AppData.user!!.memberId, "day" to day))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(DayContract.Success.Behave, it.data)
                }    }

    override fun sport(day: Int) {
        v.showIndicator()
        Api.getService<DayService>(DayService::class.java)
                .sport(mapOf("member_id" to AppData.user!!.memberId, "day" to day))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(DayContract.Success.Sport, it.data)
                }    }

    override fun status(day: Int) {
        v.showIndicator()
        Api.getService<DayService>(DayService::class.java)
                .status(mapOf("member_id" to AppData.user!!.memberId, "day" to day))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(DayContract.Success.Status, it.data)
                }    }

}
