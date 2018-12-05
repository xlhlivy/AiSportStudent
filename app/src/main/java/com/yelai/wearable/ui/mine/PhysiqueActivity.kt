package com.yelai.wearable.ui.mine

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import cn.droidlover.xdroidmvp.router.Router
import com.flyco.tablayout.listener.OnTabSelectListener
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.present.PViod
import kotlinx.android.synthetic.main.mine_physique_activity.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class PhysiqueActivity : BaseActivity<PViod>(){


    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("我的体质", ContextCompat.getColor(this, R.color.text_black_color))

        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.page_bg_color))
        showToolbar()


        stlSwitchSegment.setTabData(listOf<String>("低","中","高").toTypedArray())

        stlSwitchSegment.setOnTabSelectListener(object : OnTabSelectListener{
            override fun onTabReselect(position: Int) {
            }

            override fun onTabSelect(position: Int) {

            }
        })
    }

    override fun bindEvent() {
        super.bindEvent()

        btnBodyDetail.onClick { BodyInfoActivity.launch(this@PhysiqueActivity.context) }

        btnPhysiqueDetail.onClick { PhysiqueDetailActivity.launch(this@PhysiqueActivity.context) }
    }


    override fun getLayoutId(): Int = R.layout.mine_physique_activity

    override fun newP(): PViod = PViod()


    companion object {

        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(PhysiqueActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }


}
