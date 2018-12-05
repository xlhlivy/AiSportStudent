package com.yelai.wearable.demo

import cn.droidlover.xdroidmvp.mvp.IPresent
import cn.droidlover.xdroidmvp.mvp.IView

/**
 * Created by xuhao on 2017/11/30.
 * desc: 搜索契约类
 */
interface DemoContract {

    interface View : IView<Presenter> {
        /**
         * 设置空 View
         */
        fun setEmptyView()

    }

    interface Presenter : IPresent<View> {

        fun loadMoreData()
    }
}