package com.yelai.wearable.ui.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.CompoundButton.OnCheckedChangeListener
import cn.droidlover.xdroidmvp.base.SimpleToolbar
import cn.droidlover.xdroidmvp.router.Router
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.present.PViod
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_personal_sport_status.*
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColorResource


/**
 * Created by hr on 18/9/15.
 */

class SportStatusActivity : BaseActivity<PViod>() {

    override fun initData(savedInstanceState: Bundle?) {

        showToolbar()

        mToolbar.setMiddleText("运动现状", ContextCompat.getColor(context,R.color.color_black))


        mToolbar.setRightText("跳过")

        mToolbar.tvRight.textSize = 12f
        mToolbar.tvRight.textColorResource = R.color.text_black_color

    }

    override fun onToolbarActionsClick(which:Int, view: View) {
        super.onToolbarActionsClick(which, view)

        if(which == SimpleToolbar.RIGHT_TEXT){
            //跳转到下一个页面去
            SportGoalActivity.launch(context)
        }

    }


    var basis:String = ""

    var interest:HashSet<String> = HashSet()


    override fun bindEvent() {
        super.bindEvent()

        val observableBasis = Observable.create(ObservableOnSubscribe<String> { emitter ->

            rgExperience.setOnCheckedChangeListener { group, checkedId ->
                basis = find<View>(checkedId).tag.toString()
                emitter.onNext(basis)
            }

        })

        val observableInterest = PublishSubject.create<HashSet<String>>()

        val lambda = OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                interest.add(buttonView.tag.toString())
            }else{
                interest.remove(buttonView.tag.toString())
            }
            observableInterest.onNext(interest)
        }

        cbSportPlace0.setOnCheckedChangeListener(lambda)

        cbSportPlace1.setOnCheckedChangeListener(lambda)

        cbSportPlace2.setOnCheckedChangeListener(lambda)

        cbSportPlace3.setOnCheckedChangeListener(lambda)

//        RxTextView.textChanges(mEmailView);


        btnNext.onClick {

            with(AppData.userInfo!!){
                basis = this@SportStatusActivity.basis
                interest = this@SportStatusActivity.interest.joinToString(separator = ",")
            }

            SportGoalActivity.launch(this@SportStatusActivity.context)
        }

        btnNext.alpha = .5f
        btnNext.isClickable = false



        Observable.combineLatest(
                observableBasis,
                observableInterest,
                BiFunction<String, HashSet<String>, Boolean> { t1, t2 -> t1.isNotEmpty() && t2.isNotEmpty() }
        ).subscribe {
            btnNext.alpha = if(it){ 1f } else { .5f }
            btnNext.isClickable = it
        }



    }


    override fun getLayoutId(): Int {
        return R.layout.activity_personal_sport_status
    }

    override fun newP(): PViod? {
        return null
    }

    companion object {
        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(SportStatusActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }


}
