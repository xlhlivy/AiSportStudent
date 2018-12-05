package com.yelai.wearable.ui.course

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
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.adapter.CourseAdapter
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.model.Course
import com.yelai.wearable.present.PCourse
import com.yelai.wearable.showToast
import com.yelai.wearable.toYyyyMMddWithSlash
import kotlinx.android.synthetic.main.course_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class CourseActivity : BaseActivity<CourseContract.Presenter>(),CourseContract.View{

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        if(adapter.dataSource.size == 0){
            contentLayout.refreshState(false)
            contentLayout.showError()
        }
    }

    override fun success(type: CourseContract.Success, data: Any) {

        when(type){
            CourseContract.Success.TeacherCourseList->{
                val list = data as List<Course>

                adapter.setData(list)

                contentLayout.recyclerView.setPage(1, 1)

                if (adapter.itemCount < 1) {
                    contentLayout.showEmpty()
                    return
                }else{
                    contentLayout.showContent()
                }
            }

            CourseContract.Success.Sign->{
                showToast(data as String)
                contentLayout.recyclerView.refreshData()

//                p.myCourseList()
            }

//            CourseContract.Success.MyCourseList->{
//
//                AppData.myCourseList.clear()
//                AppData.myCourseList.addAll(data as List<Course>)
//
//                adapter.notifyDataSetChanged()
//            }

        }
    }

    val adapter: CourseAdapter by lazy {
        object:CourseAdapter(context){
            override fun sign(data: Course) {
                p.sign(AppData.user!!.memberId,data.id)
            }
        }
    }

    private val teacherId : String by lazy { intent.extras.getString("teacherId") }

    private val teacherName: String by lazy { intent.extras.getString("teacherName") }


    override fun initData(savedInstanceState: Bundle?) {

        showToolbar()
        mToolbar.setMiddleText(teacherName + "的课程",ContextCompat.getColor(this, R.color.text_black_color))


        contentLayout.recyclerView.verticalLayoutManager(context)
//        contentLayout.recyclerView.horizontalDividerMargin(R.color.text_black_color,R.dimen.divider,R.dimen.padding_common_h,R.dimen.padding_common_h)
        contentLayout.recyclerView.adapter = XRecyclerAdapter(adapter)

        contentLayout.recyclerView.onRefreshAndLoadMoreListener = object : XRecyclerView.OnRefreshAndLoadMoreListener {
            override fun onRefresh() {
                p.teacherCourseList(teacherId)
            }

            override fun onLoadMore(page: Int) {}
        }
        contentLayout.refreshEnabled(true)

//        contentLayout.recyclerView.isRefreshEnabled = false
        contentLayout.showLoading()
        contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null))

        contentLayout.recyclerView.setPage(1, 1)

        contentLayout.showContent()

        contentLayout.recyclerView.refreshData()

    }




    override fun getLayoutId(): Int = R.layout.recyclerview_layout

    override fun newP(): PCourse = PCourse()


//    inner class Adapter(context: Context) : SimpleRecAdapter<Course, Adapter.ViewHolder>(context) {
//
//        override fun newViewHolder(itemView: View): ViewHolder {
//            return ViewHolder(itemView)
//        }
//
//        override fun getLayoutId(): Int {
//            return R.layout.course_item
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            val item = data[position]
//
//            holder.setData(item)
//
//            holder.itemView.setOnClickListener {
//
//                CourseDetailActivity.launch(this@CourseActivity.context,item)
//
//            }
//        }
//
//       inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//            fun setData(data: Course) {
//                itemView.tvCourseName.text = data.title
//                itemView.tvCourseTeacher.text = "开课时间: " + data.time.toLong().toYyyyMMddWithSlash()
//                itemView.tvCourseTime.text = "课程数量: " + data.times.toString() + "节课"
//
//                itemView.ivTag.visibility = View.VISIBLE
//
////                1：教学课 2：训练课 3：兴趣课
//                when(data.type){
//                    "1"->{
//                        itemView.ivTag.setImageResource(R.drawable.course_icon_item_teach)
//                    }
//
//                    "2"->{
//                        itemView.ivTag.setImageResource(R.drawable.course_icon_item_training)
//                    }
//
//                    "3"->{
//                        itemView.ivTag.setImageResource(R.drawable.course_icon_item_interest)
//                    }
//                    else->{
//                        itemView.ivTag.visibility = View.GONE
//                    }
//                }
//
//
//
//
//                itemView.btnDone.visibility = View.VISIBLE
//                itemView.btnDone.text = "报名"
//
//                itemView.btnDone.onClick {
//                    p.sign(AppData.user!!.memberId,data.id)
//                }
//
//
////                if(AppData.myCourseList.contains(data)){
////
////                    itemView.btnDone.visibility = View.GONE
////
////                }
//
//
//
//
//            }
//        }
//    }


    companion object {
        fun launch(activity: Context,teacherId:String,teacherName:String) {
            Router.newIntent(activity as Activity)
                    .putString("teacherId",teacherId)
                    .putString("teacherName",teacherName)
                    .to(CourseActivity::class.java).launch()
        }
    }


}
