package com.yelai.wearable.ui.course

import android.os.Bundle
import android.view.View
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import cn.droidlover.xrecyclerview.XRecyclerView
import com.yelai.wearable.AppData

import com.yelai.wearable.R
import com.yelai.wearable.adapter.CourseAdapter
import com.yelai.wearable.adapter.MyCourseAdapter
import com.yelai.wearable.base.BaseFragment
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.entity.CourseEntity
import com.yelai.wearable.model.Course
import com.yelai.wearable.present.PCourse
import com.yelai.wearable.present.PViod
import kotlinx.android.synthetic.main.recyclerview_layout.*

/**
 * Created by hr on 18/9/16.
 */

class MineCourseFragment : BaseFragment<CourseContract.Presenter>(),CourseContract.View {

    override fun success(type: CourseContract.Success, data: Any) {
        if(type == CourseContract.Success.MyCourseList){

            val list = data as List<Course>

            adapter.setData(list)

            AppData.myCourseList.clear()
            AppData.myCourseList.addAll(list)

            contentLayout.recyclerView.setPage(1, 1)

            if (adapter.itemCount < 1) {
                contentLayout.showEmpty()
                return
            }else{
                contentLayout.showContent()
            }
        }
    }

    val adapter: MyCourseAdapter by lazy {
        MyCourseAdapter(context)
    }

    override fun initData(savedInstanceState: Bundle?) {

        contentLayout.recyclerView.verticalLayoutManager(context)
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.onRefreshAndLoadMoreListener = object : XRecyclerView.OnRefreshAndLoadMoreListener {
            override fun onRefresh() {
                p.myCourseList()

//                p!!.loadData(getType(), 1)
            }

            override fun onLoadMore(page: Int) {
//                p!!.loadData(getType(), page)
            }
        }

//        contentLayout.refreshEnabled(false)
//        contentLayout.recyclerView.isRefreshEnabled = false
        //        contentLayout.recyclerView.useDefLoadMoreView()

//        contentLayout.recyclerView.horizontalDivider(R.color.default_bg,R.dimen.divider)

//
//
//        if (errorView == null) {
//            errorView = StateView(context)
//        }
//        contentLayout.errorView(errorView)
        contentLayout.showLoading()
        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null))

        contentLayout.recyclerView.setPage(1, 1)

//        adapter?.notifyDataSetChanged()

        contentLayout.showContent()

        adapter.setData(AppData.myCourseList)

        contentLayout.recyclerView.refreshData()


    }


    override fun onStartLazy() {
        super.onStartLazy()
        if(AppData.isBackFromPageWithDataAndCleanData(CourseDetailActivity::class.java)){
            contentLayout.recyclerView.refreshData()
        }
    }



    override fun getLayoutId(): Int {
        return R.layout.recyclerview_layout
    }

    override fun newP(): PCourse = PCourse()


    companion object {

        fun newInstance(): MineCourseFragment {
            val fragment = MineCourseFragment()
            return fragment
        }
    }
}
