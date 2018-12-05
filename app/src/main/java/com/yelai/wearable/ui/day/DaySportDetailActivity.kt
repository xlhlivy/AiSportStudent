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
import com.yelai.wearable.model.DaySportInfo
import com.yelai.wearable.model.DiscoveryItem
import com.yelai.wearable.net.DayContract
import com.yelai.wearable.net.PDay
import com.yelai.wearable.present.PViod
import kotlinx.android.synthetic.main.day_sport_detail_activity.*
import kotlinx.android.synthetic.main.day_sport_detail_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class DaySportDetailActivity : BaseActivity<DayContract.Presenter>(),DayContract.View {

    override fun success(type: DayContract.Success, data: Any) {
        if(type == DayContract.Success.Sport){

            data as DaySportInfo


            tvEffectDistance.text = "${data.totalKm}公里"


            tvEffectTime.text = data.totalTime

            tvEffectTimeUnit.text = "剩余${data.overTime}分钟"

            apEffectTime.progress =  (data.totalTime.toInt()/(data.totalTime.toInt() + data.overTime.toInt())).toFloat() * 100


            ratingBarBody.rating = data.bodyGrade.toFloat()

            tvWeak.text = data.low
            tvWeakUnit.text = "剩余${data.overLowTime}分钟"
            apWeak.progress =  (data.low.toInt()/(data.low.toInt() + data.overLowTime.toInt())).toFloat() * 100



            tvIntermediate.text = data.centre
            tvIntermediateUnit.text = "剩余${data.overCentreTime}分钟"
            apIntermediate.progress =  (data.centre.toInt()/(data.centre.toInt() + data.overCentreTime.toInt())).toFloat() * 100



            tvStrong.text = data.high
            tvStrongUnit.text = "剩余${data.overHighTime}分钟"
            apStrong.progress =  (data.high.toInt()/(data.high.toInt() + data.overHighTime.toInt())).toFloat() * 100


            ratingBarEffect.rating = data.sportGrade.toFloat()


            adapter.setData(arrayOf(data.fat.toInt(),data.heart.toInt(),data.skeleton.toInt(),data.power.toInt()))

        }
    }


    internal val adapter: Adapter by lazy {
        Adapter(context)
    }

    override fun initData(savedInstanceState: Bundle?) {

        showToolbar()
        mToolbar.setMiddleText("DAY-运动", ContextCompat.getColor(this, R.color.text_black_color))

        contentLayout.recyclerView.verticalLayoutManager(context)
//        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.isRefreshEnabled = false

        contentLayout.showContent()

        p.sport(date)


    }

    override fun bindEvent() {
        super.bindEvent()
    }

    var names = listOf<String>("脂肪燃烧","心肺强化","骨骼强化","力量强化")

    internal inner  class Adapter(context: Context) : SimpleRecAdapter<Int, Adapter.ViewHolder>(context) {

        override fun newViewHolder(itemView: View): ViewHolder {
            return ViewHolder(itemView)
        }

        override fun getLayoutId(): Int {
            return R.layout.day_sport_detail_item
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = data[position]

            holder.setData(item,position)
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun setData(data: Int,positon:Int) {
                itemView.tvPbTxt.text = "${data}%"
                itemView.pb.progress = data
                itemView.tvHit.text = names[positon]
            }

        }
    }





    override fun getLayoutId(): Int = R.layout.day_sport_detail_activity

    override fun newP(): PDay = PDay()

    val date by lazy{intent.extras.getInt("date",0)}

    companion object {
        fun launch(activity: Context,date:Int) {
            Router.newIntent(activity as Activity)
                    .putInt("date",date)
                    .to(DaySportDetailActivity::class.java).launch()
                    }
        }

}
