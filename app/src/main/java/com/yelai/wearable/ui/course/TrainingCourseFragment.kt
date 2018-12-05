package com.yelai.wearable.ui.course

import android.os.Bundle
import android.view.View
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import cn.droidlover.xdroidmvp.net.NetError
import cn.droidlover.xrecyclerview.XRecyclerAdapter
import cn.droidlover.xrecyclerview.XRecyclerView
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.adapter.CourseAdapter
import com.yelai.wearable.base.BaseFragment
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.entity.CourseEntity
import com.yelai.wearable.model.Course
import com.yelai.wearable.present.PCourse
import com.yelai.wearable.widget.StateView
import kotlinx.android.synthetic.main.recyclerview_layout.*

/**
 * Created by hr on 18/9/16.
 */

class TrainingCourseFragment : BaseFragment<CourseContract.Presenter>(), CourseContract.View {

    override fun success(type: CourseContract.Success, data: Any) {
        if(type == CourseContract.Success.TrainingCourseList){

            val list = data as List<Course>

            adapter.setData(list)

            contentLayout.recyclerView.setPage(1, 1)

            if (adapter.itemCount < 1) {
                contentLayout.showEmpty()
                return
            }else{
                contentLayout.showContent()
            }
        }else if(type == CourseContract.Success.Sign){
            contentLayout.recyclerView.refreshData()
        }
    }

    val adapter: CourseAdapter by lazy {
        object:CourseAdapter(context){
            override fun sign(data: Course) {
                p.sign(AppData.user!!.memberId,data.id)
            }
        }
    }


    override fun initData(savedInstanceState: Bundle?) {

        contentLayout.recyclerView.verticalLayoutManager(context)
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.onRefreshAndLoadMoreListener = object : XRecyclerView.OnRefreshAndLoadMoreListener {
            override fun onRefresh() {
                p.trainingCourseList()
            }

            override fun onLoadMore(page: Int) {}
        }

        contentLayout.showLoading()
        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null))

        contentLayout.recyclerView.setPage(1, 1)

        contentLayout.showContent()

        contentLayout.recyclerView.refreshData()

    }

    override fun getLayoutId(): Int {
        return R.layout.recyclerview_layout
    }

    override fun newP(): PCourse = PCourse()

    companion object {

        fun newInstance(): TrainingCourseFragment {
            val fragment = TrainingCourseFragment()
            return fragment
        }
    }
}
