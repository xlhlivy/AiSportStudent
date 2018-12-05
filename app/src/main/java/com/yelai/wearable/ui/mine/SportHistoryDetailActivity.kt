package com.yelai.wearable.ui.mine

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import cn.droidlover.xdroidmvp.router.Router
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.contract.SportContract
import com.yelai.wearable.model.Sport
import com.yelai.wearable.present.PSport
import com.yelai.wearable.toYyyyMMddHHmm
import com.yelai.wearable.utils.GeneralUtil
import kotlinx.android.synthetic.main.mine_sport_history_detail.*


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class SportHistoryDetailActivity : BaseActivity<SportContract.Presenter>(),SportContract.View{

    override fun success(type: SportContract.Success, data: Any) {

        if(type == SportContract.Success.Detail && data is Sport){

            tvDistance.text = data.totalKm
            tvRetrievalTime.text = GeneralUtil.secondsToString(data.recoveryTime.toInt())
            tvDescription.text = "乐生·" + data.type
            tvTime.text = data.createTime.toYyyyMMddHHmm()

            tvTotalTime.text = data.totalTime

            tvStepCount.text = data.totalStep

            tvKcal.text = data.useCal

            tvStrength.text = data.strength
            tvDensity.text = data.density

            tvMaxHeartRate.text = data.maxHeartRate

            tvAvgHeartRate.text = data.avgHeartRate

            tvAvgSpeed.text = data.speed.toString()
            tvAvgRate.text = data.rate

        }

    }

    val sportId by lazy { intent.extras.getString("sportId") }


    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("运动详情", ContextCompat.getColor(this, R.color.text_black_color))

        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.page_bg_color))
        showToolbar()

        p.sportDetail(mapOf("member_id" to AppData.user!!.memberId,"sport_id" to sportId))

    }

    override fun getLayoutId(): Int = R.layout.mine_sport_history_detail

    override fun newP(): PSport = PSport()


    companion object {

        fun launch(activity: Context,sportId:String) {
            Router.newIntent(activity as Activity)
                    .to(SportHistoryDetailActivity::class.java)
                    .putString("sportId",sportId)
                    .launch()
        }
    }

}
