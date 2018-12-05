package com.yelai.wearable.ui.mine

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import cn.droidlover.xrecyclerview.XRecyclerView
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseFragment
import com.yelai.wearable.contract.SportContract
import com.yelai.wearable.model.Page
import com.yelai.wearable.model.Sport
import com.yelai.wearable.present.PSport
import com.yelai.wearable.toDate
import com.yelai.wearable.widget.horizontalDividerMargin
import kotlinx.android.synthetic.main.mine_sport_history_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*

/**
 * Created by hr on 18/9/16.
 */

class SportHistoryFragment : BaseFragment<SportContract.Presenter>(),SportContract.View {

    enum class Type private constructor(val typeValue: Int){
        ALL(0),
        WEEK(1),
        MONTH(2),
        TERM(3);

    }

    var mQueryType:Type? = null;

    override fun list(pager: Page<List<Sport>>) {

        val page = pager.currPage

        if (page > 1) {
            adapter.addData(pager.data)
        } else {
            adapter.setData(pager.data)
        }

        contentLayout.recyclerView.setPage(page, pager.pages)

        if (adapter.itemCount < 1) {
            contentLayout.showEmpty()
            return
        }else{
            contentLayout.showContent()
        }

    }

    internal val adapter:Adapter by lazy {
        Adapter(context)
    }

    override fun initData(savedInstanceState: Bundle?) {

        contentLayout.recyclerView.verticalLayoutManager(context)
        contentLayout.recyclerView.horizontalDividerMargin(R.color.divider_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.showLoading()
        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null))


        contentLayout.recyclerView.onRefreshAndLoadMoreListener = object : XRecyclerView.OnRefreshAndLoadMoreListener {
            override fun onRefresh() {
                p.sportHistory(mapOf(
                        "member_id" to AppData.user!!.memberId,
                        "type" to mQueryType!!.typeValue,
                        "page" to 1))
            }

            override fun onLoadMore(page: Int) {
                p.sportHistory(mapOf(
                        "member_id" to AppData.user!!.memberId,
                        "type" to mQueryType!!.typeValue,
                        "page" to page))            }
        }

        contentLayout.showContent()

        contentLayout.recyclerView.refreshData()

    }


    override fun getLayoutId(): Int {
        return R.layout.recyclerview_layout
    }

    override fun newP(): PSport = PSport()


    companion object {

        fun newInstance(type:Type): SportHistoryFragment {
            val fragment = SportHistoryFragment()
            fragment.mQueryType = type
            return fragment
        }
    }

    internal inner  class Adapter(context: Context) : SimpleRecAdapter<Sport,Adapter.ViewHolder>(context) {

        override fun newViewHolder(itemView: View): ViewHolder {
            return ViewHolder(itemView)
        }

        override fun getLayoutId(): Int {
            return R.layout.mine_sport_history_item
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = data[position]

            holder.setData(item)

            holder.itemView.setOnClickListener {
                SportHistoryDetailActivity.launch(this@SportHistoryFragment.context,item.id)
            }
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun setData(data: Sport) {
                itemView.tvName.text = data.type
                itemView.tvDetail.text = data.totalKm + "公里"
                itemView.tvTime.text = data.totalTime
                itemView.tvStep.text = data.totalStep
                itemView.tvSpeed.text = data.speed.toString()
                itemView.tvDate.text = data.createTime.toDate()
            }

        }
    }
}
