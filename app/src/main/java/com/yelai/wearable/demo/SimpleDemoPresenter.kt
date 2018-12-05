package com.yelai.wearable.demo

import cn.droidlover.xdroidmvp.mvp.XPresent
import com.yelai.wearable.demo.DemoContract

/**
 * Created by xuhao on 2017/12/4.
 * desc: 搜索的 Presenter
 */
class SimpleDemoPresenter : XPresent<SimpleDemoActivity>() {

    fun loadMoreData() {
         v.setEmptyView()
    }


}