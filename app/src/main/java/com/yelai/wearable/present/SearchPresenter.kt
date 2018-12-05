package com.yelai.wearable.present

import cn.droidlover.xdroidmvp.mvp.XPresent
import com.yelai.wearable.entity.TeacherEntity
import com.yelai.wearable.ui.course.SearchActivity

/**
 * Created by xuhao on 2017/12/4.
 * desc: 搜索的 Presenter
 */
class SearchPresenter : XPresent<SearchActivity>() {

    private var nextPageUrl: String? = null

//    private val searchModel by lazy { SearchModel() }


    /**
     * 获取热门关键词
     */
     fun requestHotWordData() {
//        checkViewAttached()
//        checkViewAttached()
//        mRootView?.apply {
//            closeSoftKeyboard()
//            showLoading()
//        }
//        addSubscription(disposable = searchModel.requestHotWordData()
//                .subscribe({ string ->
//                    mRootView?.apply {
//                        setHotWordData(string)
//                    }
//                }, { throwable ->
//                    mRootView?.apply {
//                        //处理异常
//                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
//                    }
//                }))
    }

    /**
     * 查询关键词
     */
     fun querySearchData(words: String) {
//        checkViewAttached()
//        mRootView?.apply {
//            closeSoftKeyboard()
//            showLoading()
//        }
//        addSubscription(disposable = searchModel.getSearchResult(words)
//                .subscribe({ issue ->
//                    mRootView?.apply {
//                        dismissLoading()
//                        if (issue.count > 0 && issue.itemList.size > 0) {
//                            nextPageUrl = issue.nextPageUrl
//                            setSearchResult(issue)
//                        } else
//                            setEmptyView()
//                    }
//                }, { throwable ->
//                    mRootView?.apply {
//                        dismissLoading()
//                        //处理异常
//                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
//                    }
//                })
//        )

    }




    /**
     * 加载更多数据
     */
     fun loadMoreData() {
//        checkViewAttached()
//        nextPageUrl?.let {
//            addSubscription(disposable = searchModel.loadMoreData(it)
//                    .subscribe({ issue ->
//                        mRootView?.apply {
//                            nextPageUrl = issue.nextPageUrl
//                            setSearchResult(issue)
//                        }
//                    }, { throwable ->
//                        mRootView?.apply {
//                            //处理异常
//                            showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
//                        }
//                    }))
//        }

    }


}