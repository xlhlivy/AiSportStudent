package com.yelai.wearable.demo

import android.os.Bundle
import android.view.View
import com.yelai.wearable.R

import com.yelai.wearable.base.BaseActivity
import kotlinx.android.synthetic.main.activity_demo.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hr on 18/9/17.
 */

class DemoActivity : BaseActivity<DemoContract.Presenter>(), DemoContract.View {

    override fun setEmptyView() {

        background.visibility = View.GONE

    }

    override fun initData(savedInstanceState: Bundle) {

    }

    override fun bindEvent() {
        super.bindEvent()
        btn.onClick { p.loadMoreData() }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_demo
    }

    override fun newP(): DemoContract.Presenter {
        return DemoPresenter()
    }
}
