package com.yelai.wearable.ui.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import cn.droidlover.xdroidmvp.router.Router
import com.jakewharton.rxbinding2.widget.RxTextView
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.model.UserInfoResult
import com.yelai.wearable.present.PViod
import io.reactivex.Observable
import io.reactivex.functions.Function3
import kotlinx.android.synthetic.main.activity_bind_identify.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by hr on 18/9/15.
 */

class IdentifyBindActivity : BaseActivity<PViod>() {

    override fun initData(savedInstanceState: Bundle?) {}

    override fun bindEvent() {
        super.bindEvent()


        btnNext.onClick {

            AppData.userInfo = UserInfoResult.UserInfo()

            with(AppData.userInfo!!){
                memberId = AppData.user!!.memberId
                schoolName = etSchool.text.trim().toString()
                schoolNo = etNo.text.trim().toString()
                trueName = etName.text.trim().toString()
            }

            GenderBirthdayActivity.launch(this@IdentifyBindActivity.context)

        }



        Observable.combineLatest(
                RxTextView.textChanges(etSchool),
                RxTextView.textChanges(etNo),
                RxTextView.textChanges(etName),
                Function3<CharSequence, CharSequence, CharSequence, Boolean> { t1, t2, t3 ->
                    t1.isNotEmpty() && t2.isNotEmpty() && t3.isNotEmpty()
                }
        ).subscribe{
            btnNext.alpha = if(it){ 1f } else { .5f }
            btnNext.isClickable = it
        }



    }

    override fun getLayoutId(): Int {
        return R.layout.activity_bind_identify
    }

    override fun newP(): PViod? {
        return null
    }

    companion object {
        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(IdentifyBindActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }


}
