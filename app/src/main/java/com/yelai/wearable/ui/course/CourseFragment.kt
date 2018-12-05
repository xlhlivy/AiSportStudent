package com.yelai.wearable.ui.course

import android.os.Bundle
import android.support.v4.app.Fragment
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import com.flyco.tablayout.listener.CustomTabEntity

import com.yelai.wearable.R
import com.yelai.wearable.entity.TabEntity
import com.yelai.wearable.present.PViod
import kotlinx.android.synthetic.main.course_fragment.*
import org.jetbrains.anko.sdk25.coroutines.onClick

import java.util.ArrayList

/**
 * Created by hr on 18/9/16.
 */

class CourseFragment : KLazyFragment<PViod>() {


    private val mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("我的课程", "兴趣课", "训练")
    private val mIconUnselectIds = intArrayOf(R.drawable.day_tab_icon_sport_normal, R.drawable.day_tab_icon_action_normal, R.drawable.day_tab_icon_status_normal)
    private val mIconSelectIds = intArrayOf(R.drawable.day_tab_icon_sport_press, R.drawable.day_tab_icon_action_press, R.drawable.day_tab_icon_status_press)
    private val mTabEntities = ArrayList<CustomTabEntity>()

    override fun initData(savedInstanceState: Bundle?) {

        mTabEntities.clear()
        mFragments.clear()

        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }

        mFragments.add(MineCourseFragment.newInstance())
        mFragments.add(InterestCourseFragment.newInstance())
        mFragments.add(TrainingCourseFragment.newInstance())
        ctlLayout.setTabData(mTabEntities,childFragmentManager,R.id.ctlContent,mFragments)


        tvAddCourse.onClick {
            SearchActivity.launch(this@CourseFragment.context)
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.course_fragment
    }

    override fun newP(): PViod? {
        return null
    }


    companion object {

        fun newInstance(): CourseFragment {
            val fragment = CourseFragment()
            return fragment
        }
    }
}
