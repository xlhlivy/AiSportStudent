package com.yelai.wearable.ui.mine

import android.content.Context
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.adapter.BaseAdapter
import com.yelai.wearable.base.BaseListFragment
import com.yelai.wearable.adapter.ViewHolder
import com.yelai.wearable.contract.SportContract
import com.yelai.wearable.model.Page
import com.yelai.wearable.model.Sport
import com.yelai.wearable.present.PSport
import com.yelai.wearable.toDate
import com.yelai.wearable.toYearAndMonth
import com.yelai.wearable.utils.GeneralUtil
import com.yelai.wearable.widget.horizontalDividerMargin
import kotlinx.android.synthetic.main.mine_sport_history_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*

/**
 * Created by hr on 18/9/16.
 */

class SportHistoryFragmentV2 : BaseListFragment<Sport,SportContract.Presenter>(),SportContract.View {

    enum class Type private constructor(val typeValue: Int){
        ALL(0),
        WEEK(1),
        MONTH(2),
        TERM(3);

    }

    override fun list(page: Page<List<Sport>>) {
        super<BaseListFragment>.list(page)
    }

    override fun initAdapter(): SimpleRecAdapter<Sport, ViewHolder<Sport>> {
        return Adapter(context)
    }

    override fun onRefresh() {
        yearMonthSet.clear()
        positionSet.clear()
        p.sportHistory(mapOf(
                "member_id" to AppData.user!!.memberId,
                "type" to mQueryType!!.typeValue,
                "page" to 1))
    }

    override fun onLoadMore(page: Int) {
        p.sportHistory(mapOf(
                "member_id" to AppData.user!!.memberId,
                "type" to mQueryType!!.typeValue,
                "page" to page))
    }



    var mQueryType:Type? = null;



    override fun divider(){
        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
    }



    override fun getLayoutId(): Int {
        return R.layout.recyclerview_layout
    }

    override fun newP(): PSport = PSport()


    companion object {

        fun newInstance(type:Type): SportHistoryFragmentV2 {
            val fragment = SportHistoryFragmentV2()
            fragment.mQueryType = type
            return fragment
        }
    }

    val yearMonthSet = HashSet<String>()
    val positionSet = HashSet<String>()

    internal inner class Adapter(context: Context) : BaseAdapter<Sport>(context) {

        override fun setData(itemView: View, data: Sport) {
            itemView.tvName.text = data.type
            itemView.tvDetail.text = data.totalKm + "公里"
            itemView.tvTime.text = GeneralUtil.secondsToString(data.totalTime.toInt())
            itemView.tvStep.text = data.totalStep
            itemView.tvSpeed.text = data.speed.toString()
            itemView.tvDate.text = data.createTime.toDate()

            itemView.tvDateTime.visibility = View.GONE

            data.createTime.toYearAndMonth().apply {
                if(!yearMonthSet.contains(this)){
                    yearMonthSet.add(this)
                    positionSet.add(data.id)
                    itemView.tvDateTime.text = this
                    itemView.tvDateTime.visibility = View.VISIBLE
                }else if(positionSet.contains(data.id)){
                    itemView.tvDateTime.text = this
                    itemView.tvDateTime.visibility = View.VISIBLE
                }



            }


        }

        override fun getLayoutId(): Int {
            return R.layout.mine_sport_history_item
        }

        override fun onBindViewHolder(holder: ViewHolder<Sport>, position: Int) {
            val item = data[position]
            holder.setData(item)
            holder.itemView.setOnClickListener {
                SportHistoryDetailActivity.launch(this@SportHistoryFragmentV2.context,item.id)
            }

        }

    }

}
