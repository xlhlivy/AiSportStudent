package com.yelai.wearable.ui.sport

import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseFragment
import com.yelai.wearable.contract.SportContract
import com.yelai.wearable.entity.LocalLog
import com.yelai.wearable.model.LocalSportRecord
import com.yelai.wearable.model.Sport
import com.yelai.wearable.present.PSport
import com.yelai.wearable.step.utils.DbUtils
import com.yelai.wearable.widget.FullScreenPopup
import kotlinx.android.synthetic.main.sport_fragment.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by hr on 18/9/16.
 */

class SportFragment : BaseFragment<SportContract.Presenter>(),SportContract.View {


    override fun success(type: SportContract.Success, data: Any) {

        when(type){

            SportContract.Success.Statistics->{
                AppData.sportStatistics = data as Sport

                bindDataToView()
            }


        }

    }

    override fun initData(savedInstanceState: Bundle?) {

        p.sportStatistics(mapOf("member_id" to AppData.user!!.memberId,"type" to 1))

        bindDataToView()


        DbUtils.createDb(context, LocalSportRecord::class.java)

        val list = DbUtils.getQueryAll(LocalSportRecord::class.java)

        list.forEach {
            println(it)
        }


        DbUtils.createDb(context,LocalLog::class.java)
        val list2 = DbUtils.getQueryAll(LocalLog::class.java)

        list2.forEach {
            println(it)
        }

    }

    private fun bindDataToView(){

        if(tvRunDistanceTotal == null)return

        AppData.sportStatistics?.apply {

            tvRunDistanceTotal.text = totalKm
            tvRunTimeTotal.text = (totalTime.toInt() / 60).toString()
        }

    }

    override fun bindEvent() {
        super.bindEvent()

        btnGo.onClick {

            DbUtils.createDb(this@SportFragment.context, LocalSportRecord::class.java)
            DbUtils.deleteAll(LocalSportRecord::class.java)

            if(AppData.sportType == null){
                SportTypeChoiceActivity.launch(this@SportFragment.context)
            }else{

                if(AppData.sportType!!.name == "跑步"){
                    SportRunWithMapActivity.launch(this@SportFragment.context,AppData.sportType!!.id)
                }else{
                    OtherSportActivity.launch(this@SportFragment.context,AppData.sportType!!.id)
                }
            }

        }

        btnSportTypeChoice.onClick {
            SportTypeChoiceActivity.launch(this@SportFragment.context)
        }

        btnSportTypeChoice.text = if( AppData.sportType == null) {"运动项目选择"} else { AppData.sportType!!.name + " >" }

    }

    override fun onResume() {
        super.onResume()
        btnSportTypeChoice?.text = if( AppData.sportType == null) {"运动项目选择"} else { AppData.sportType!!.name + " >" }

        p.sportStatistics(mapOf("member_id" to AppData.user!!.memberId,"type" to 1))

    }

    override fun getLayoutId(): Int {
        return R.layout.sport_fragment
    }

    override fun newP(): PSport = PSport()


    companion object {

        fun newInstance(): SportFragment {
            val fragment = SportFragment()
            return fragment
        }
    }
}
