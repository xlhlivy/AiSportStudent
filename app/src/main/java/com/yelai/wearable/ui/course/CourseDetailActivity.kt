package com.yelai.wearable.ui.course

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import cn.droidlover.xdroidmvp.router.Router
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.yelai.wearable.*
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.entity.TabEntity
import com.yelai.wearable.model.Course
import com.yelai.wearable.model.CourseDetail
import com.yelai.wearable.present.PCourse
import kotlinx.android.synthetic.main.course_activity_detail.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class CourseDetailActivity : BaseActivity<CourseContract.Presenter>(),CourseContract.View{

    override fun success(type: CourseContract.Success, data: Any) {

        if(type == CourseContract.Success.CourseDetail && data is CourseDetail){

            tvTitle.text = data.title
            tvCourseTeacher.text = data.teacherName

            tvCourseTeacherDescription.text = "任课老师"

            tvCourseDetail.text = "${data.count}/${data.times}节课"

            tvCourseDetailDescription.text = "课程时长"


            boardFragment.setContent(data.notice)


//            boardFragment.

        }else if(type == CourseContract.Success.SignOut){

            showToast(data as String)

            AppData.backWithData(CourseDetailActivity::class.java)


            AppManager.finishCurrentActivity()

        }

    }

    private val mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("公告栏", "留言板")
    private val mTabEntities = ArrayList<CustomTabEntity>()

    private val boardFragment by lazy{CourseBulletinBoardFragment.newInstance()}

    private val course by lazy{intent.extras.getSerializable("course") as Course}

    override fun initData(savedInstanceState: Bundle?) {

        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i],0, 0))
        }

        mFragments.add(boardFragment)
        mFragments.add(CourseMessageBoardFragment.newInstance(course.id))

        viewPager.adapter = MyPagerAdapter(supportFragmentManager)

        ivLeft.onClick { AppManager.finishCurrentActivity() }

        initViewpager()

        p.courseDetail(course.id)


        when(course.type){
            "1"->{
                ivBackground.load(R.drawable.course_item_background_banner1)
            }

            "2"->{
                ivBackground.load(R.drawable.course_item_background_banner2)

            }

            "3"->{
                ivBackground.load(R.drawable.course_item_background_banner3)
            }


        }

    }


    private fun initViewpager(){


        tabLayout.setTabData(mTabEntities)
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewPager.currentItem = position
            }

            override fun onTabReselect(position: Int) {
                if (position == 0) {
                    tabLayout.showMsg(0, 2)
                    //                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        })

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                tabLayout.currentTab = position

                btnExitCourse.visibility = if(course.isSign == 1 && position == 0){View.VISIBLE}else{View.GONE}
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


        btnExitCourse.visibility = if(course.isSign == 1 ){View.VISIBLE}else{View.GONE}

        viewPager.offscreenPageLimit = 2;
        viewPager.currentItem = 0

        btnExitCourse.onClick {

            p.signOut(AppData.user!!.memberId,course.id)
        }
    }

    override fun getLayoutId(): Int = R.layout.course_activity_detail

    override fun newP(): PCourse = PCourse()


    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mTitles[position]
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }
    }


    companion object {
        fun launch(activity: Context,course:Course) {
            Router.newIntent(activity as Activity)
                    .to(CourseDetailActivity::class.java)
                    .putSerializable("course",course)
                    .launch()
        }
    }


}
