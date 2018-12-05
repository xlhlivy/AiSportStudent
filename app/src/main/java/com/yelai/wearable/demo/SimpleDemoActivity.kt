package com.yelai.wearable.demo

import android.os.Bundle
import android.view.View
import com.yelai.wearable.R

import com.yelai.wearable.base.BaseActivity
import kotlinx.android.synthetic.main.activity_demo.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hr on 18/9/17.
 *
 * 这种方式适合快速开发模式下的mvp,直接将Presenter的引用丢到activity中，
 *
 * 然后将Activity的引用丢到Presenter中,  只是省略了Contract层，少掉了接口,省去了麻烦，但是维护的成本增加了，而且使用这种方式后，
 *
 * 不可多个视图使用同一个Presenter
 */

class SimpleDemoActivity : BaseActivity<SimpleDemoPresenter>() {

    fun setEmptyView() {

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

    override fun newP(): SimpleDemoPresenter {
        return SimpleDemoPresenter()
    }
}
