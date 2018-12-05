package com.yelai.wearable.ui.day

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.router.Router
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import cn.droidlover.xrecyclerview.XRecyclerView
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.model.DayStatusInfo
import com.yelai.wearable.model.DiscoveryItem
import com.yelai.wearable.net.DayContract
import com.yelai.wearable.net.PDay
import com.yelai.wearable.present.PViod
import kotlinx.android.synthetic.main.day_status_detail_activity.*
import kotlinx.android.synthetic.main.recyclerview_layout.*


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class DayStatusDetailActivity : BaseActivity<DayContract.Presenter>(),DayContract.View {


    override fun getLayoutId(): Int = R.layout.day_status_detail_activity

    override fun newP(): PDay = PDay()

    override fun success(type: DayContract.Success, data: Any) {
        if (type == DayContract.Success.Status) {

            data as DayStatusInfo

            rbPress.rating = data.pressureGrade.toFloat()
            tvPress.text = data.pressure

            rbFatigue.rating = data.tiredGrade.toFloat()
            tvFatigue.text = data.tired

            rbBlood.rating = data.heartGrade.toFloat()

            tvRecentlyHeartRate.text = data.recent.toString()

            tvQuietlyHeartRate.text = "安静心率 : " +data.quiet.toString()

            tvMorningHeartRate.text = data.morning.toString()

            tvHeartRateAbility.text = data.resume


            tvBloodPressureBase.text = data.foundation.toString()

            tvPulsePressure.text = data.pulse.toString()

        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        showToolbar()
        mToolbar.setMiddleText("DAY-状态", ContextCompat.getColor(this, R.color.text_black_color))

       p.status(date)

    }


    val date by lazy{intent.extras.getInt("date",0)}

    companion object {
        fun launch(activity: Context,date:Int) {
            Router.newIntent(activity as Activity)
                    .putInt("date",date)
                    .to(DayStatusDetailActivity::class.java).launch()
                    }
        }

}
