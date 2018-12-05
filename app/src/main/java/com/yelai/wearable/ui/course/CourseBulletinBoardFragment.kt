package com.yelai.wearable.ui.course

import android.os.Bundle
import cn.droidlover.xdroidmvp.mvp.KLazyFragment
import com.yelai.wearable.R
import com.yelai.wearable.present.PViod
import kotlinx.android.synthetic.main.course_fragment_bulletin_board.*
import org.jetbrains.anko.support.v4.onUiThread

/**
 * Created by hr on 18/9/16.
 */

class CourseBulletinBoardFragment : KLazyFragment<PViod>() {

    override fun initData(savedInstanceState: Bundle?) {
        tvBulletinBoard.text = "这里是公告板"
    }

    override fun getLayoutId(): Int {
        return R.layout.course_fragment_bulletin_board
    }

    override fun newP(): PViod? {
        return null
    }

    public fun setContent(content:String){
        if(tvBulletinBoard == null)return
        onUiThread {
            tvBulletinBoard.text = content
        }
    }


    companion object {

        fun newInstance(): CourseBulletinBoardFragment {
            val fragment = CourseBulletinBoardFragment()
            return fragment
        }
    }

}
