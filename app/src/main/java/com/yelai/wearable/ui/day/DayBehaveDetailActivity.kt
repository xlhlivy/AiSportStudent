package com.yelai.wearable.ui.day

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.Spanned
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.router.Router
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import cn.droidlover.xrecyclerview.XRecyclerView
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.model.DayActionInfo
import com.yelai.wearable.model.DiscoveryItem
import com.yelai.wearable.net.DayContract
import com.yelai.wearable.net.PDay
import kotlinx.android.synthetic.main.day_action_detail_activity.*
import kotlinx.android.synthetic.main.day_action_detail_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class DayBehaveDetailActivity : BaseActivity<DayContract.Presenter>(),DayContract.View {

    data class Item(val progress:Float, val progressTxt:String, val hint:String, val txt1: Spanned, val txt1Unit:String, val txt2:Spanned, val txt2Unit:String, val txt3:Spanned, val txt3Unit:String)


    override fun getLayoutId(): Int = R.layout.day_action_detail_activity

    override fun newP(): PDay = PDay()

    override fun success(type: DayContract.Success, data: Any) {
        if(DayContract.Success.Behave == type){
            data as DayActionInfo

            ratingBarBody.rating = data.grade.toFloat()


            adapter.setData(arrayOf(
                    Item(data.longSitRate.toFloat(),data.longSitRate,"久坐率",
                            Html.fromHtml("${data.longSitTotal}<small>分钟</small>"),"总的静坐时长",
                            Html.fromHtml("${data.longSitTime}<small>次</small>"),"久坐时间",
                            Html.fromHtml("${data.calmTime}<small><font color=\"#6d819c\">分钟</font></small>"),"本次静坐时间"
                            ),
                    Item(data.totalStep.toFloat(),data.totalStep,"睡眠完成度",
                            Html.fromHtml("${data.sleep}<small>分钟</small>"),"睡眠时间",
                            Html.fromHtml("${data.sleepDepth}<small>分钟</small>"),"深层睡眠",
                            Html.fromHtml("${data.sleepPropose}<small><font color=\"#6d819c\">分钟</font></small>"),"建议睡眠"
                    ),
                    Item(data.totalCal.toFloat(),data.totalCal,"消耗完成度",
                            Html.fromHtml("${data.feedCal}<small>cal</small>"),"摄入",
                            Html.fromHtml("${data.consumeCal}<small>cal</small>"),"消耗",
                            Html.fromHtml("${data.proposCal}<small><font color=\"#6d819c\">cal</font></small>"),"建议消耗"
                    )
            ))
        }
    }




    internal val adapter: Adapter by lazy {
        Adapter(context)
    }

    override fun initData(savedInstanceState: Bundle?) {

        showToolbar()
        mToolbar.setMiddleText("DAY-健康", ContextCompat.getColor(this, R.color.text_black_color))


        contentLayout.recyclerView.verticalLayoutManager(context)
//        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.isRefreshEnabled = false

        contentLayout.showContent()

        p.behave(date)

    }

    override fun bindEvent() {
        super.bindEvent()
    }

    internal inner  class Adapter(context: Context) : SimpleRecAdapter<Item, Adapter.ViewHolder>(context) {

        override fun newViewHolder(itemView: View): ViewHolder {
            return ViewHolder(itemView)
        }

        override fun getLayoutId(): Int {
            return R.layout.day_action_detail_item
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = data[position]

            holder.setData(item)

        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun setData(data: Item) {

                itemView.tvProgress.text = data.progressTxt
                itemView.apBlue1.progress = data.progress
                itemView.tvHit.text = data.hint

                itemView.tvTxt1.text = data.txt1
                itemView.tvTxt2.text = data.txt1Unit


                itemView.tvTxt3.text = data.txt2
                itemView.tvTxt4.text = data.txt2Unit

                itemView.tvTxt5.text = data.txt3
//                itemView.tvTxt6.text = data.txt3Unit

                itemView.tvTxt7.text = data.txt3Unit
            }

        }
    }



    val date by lazy{intent.extras.getInt("date",0)}




    companion object {
        fun launch(activity: Context,date:Int) {
            Router.newIntent(activity as Activity)
                    .putInt("date",date)
                    .to(DayBehaveDetailActivity::class.java).launch()
                    }
        }

}
