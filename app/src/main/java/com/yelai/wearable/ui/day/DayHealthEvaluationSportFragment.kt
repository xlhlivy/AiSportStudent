package com.yelai.wearable.ui.day

import android.os.Bundle
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import com.yelai.wearable.R
import com.yelai.wearable.model.DayInfo
import com.yelai.wearable.present.PViod
import kotlinx.android.synthetic.main.day_fragment_health_evaluation.*

/**
 * Created by hr on 18/9/16.
 */

class DayHealthEvaluationSportFragment : KLazyFragment<PViod>() {

    private var type:Int = 0;
    override fun initData(savedInstanceState: Bundle?) {
//        ctlLayout
        println("DayHealthEvaluationSportFragment 初始化")


        apOrange.progress = 0f
        apGreen.progress = 0f
        apBlue.progress = 0f
        tvProgress.text = "0"
        tvProgressSymbol.text = "%"


        when(type){
            1->{
                tvHit.text = "活动量"
                tvHit1.text = "有效活动量"
                tvHit2.text = "运动效益"
            }
            2->{
                tvHit.text = "久坐指数"
                tvHit1.text = "睡眠时长"
                tvHit2.text = "消耗指数"
            }
            else->{
                tvHit.text = "压力指数"
                tvHit1.text = "疲劳指数"
                tvHit2.text = "心血管指数"
            }
        }


    }

    var dataValue:DayInfo.HealthEvaluation? = null

    fun dataChange(healthEvaluation: DayInfo.HealthEvaluation){

        dataValue = healthEvaluation

        with(healthEvaluation){

            apOrange.progress = orangeProgress().toFloat()
            apGreen.progress = greenProgress().toFloat()
            apBlue.progress = blueProgress().toFloat()
            tvProgress.text =  complatedProgress().toString()
            tvProgressSymbol.text = "%"

            pb.progress = orangeProgress()
            tvPbPercent.text = orangeProgress().toString() + "%"


            pb1.progress = greenProgress()
            tvPbPercent1.text = greenProgress().toString() + "%"


            pb2.progress = blueProgress()
            tvPbPercent2.text = blueProgress().toString() + "%"

            tvHit.text = orangeTxt()

            tvHit1.text = greenTxt()

            tvHit2.text = blueTxt()

        }

    }

    override fun onStartLazy() {
        if(dataValue == null)return

        with(dataValue!!){

            apOrange.progress = orangeProgress().toFloat()
            apGreen.progress = greenProgress().toFloat()
            apBlue.progress = blueProgress().toFloat()
            tvProgress.text =  complatedProgress().toString()
            tvProgressSymbol.text = "%"

            pb.progress = orangeProgress()
            tvPbPercent.text = orangeProgress().toString() + "%"


            pb1.progress = greenProgress()
            tvPbPercent1.text = greenProgress().toString() + "%"


            pb2.progress = blueProgress()
            tvPbPercent2.text = blueProgress().toString() + "%"

            tvHit.text = orangeTxt()

            tvHit1.text = greenTxt()

            tvHit2.text = blueTxt()

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.day_fragment_health_evaluation
    }

    override fun newP(): PViod? {
        return null
    }

    companion object {

        fun newInstance(type:Int): DayHealthEvaluationSportFragment {
            val fragment = DayHealthEvaluationSportFragment()
            fragment.type = type;
            return fragment
        }
    }
}
