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
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.contract.LoginContract
import com.yelai.wearable.present.PLogin
import com.yelai.wearable.ui.HomeActivity
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_personal_sport_goal.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColorResource
import org.jetbrains.anko.textSizeDimen


/**
 * Created by hr on 18/9/15.
 */

class SportGoalActivity : BaseActivity<LoginContract.Presenter>(),LoginContract.View {


    override fun success() {
        AppManager.finishAllActivity()
        HomeActivity.launch(this@SportGoalActivity.context)
    }


    override fun initData(savedInstanceState: Bundle?) {

        showToolbar()

        mToolbar.setMiddleText("运动目标", ContextCompat.getColor(context,R.color.color_black))

        mToolbar.setRightText("跳过")

        mToolbar.tvRight.textSize = 12f
        mToolbar.tvRight.textColorResource = R.color.text_black_color

    }

    override fun onToolbarActionsClick(which:Int, view: View) {
        super.onToolbarActionsClick(which, view)

        if(which == SimpleToolbar.RIGHT_TEXT){
            //跳转到下一个页面去
            //向服务器设置基本数据
            p.setMember(AppData.userInfo!!)
        }

    }

    var target:HashSet<String> = HashSet()

    var sports:HashSet<String> = HashSet()

    override fun bindEvent() {
        super.bindEvent()

        val observableTarget = PublishSubject.create<HashSet<String>>()

        val observableSport = PublishSubject.create<HashSet<String>>()


        val targetLambda = OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                target.add(buttonView.tag.toString())
            }else{
                target.remove(buttonView.tag.toString())
            }
            observableTarget.onNext(target)
        }

        val sportLambda = OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                sports.add(buttonView.tag.toString())
            }else{
                sports.remove(buttonView.tag.toString())
            }
            observableSport.onNext(sports)
        }


        cbSportGoal0.setOnCheckedChangeListener(targetLambda)
        cbSportGoal1.setOnCheckedChangeListener(targetLambda)
        cbSportGoal2.setOnCheckedChangeListener(targetLambda)
        cbSportGoal3.setOnCheckedChangeListener(targetLambda)


        cbSportType0.setOnCheckedChangeListener(sportLambda)
        cbSportType1.setOnCheckedChangeListener(sportLambda)
        cbSportType2.setOnCheckedChangeListener(sportLambda)
        cbSportType3.setOnCheckedChangeListener(sportLambda)
        cbSportType4.setOnCheckedChangeListener(sportLambda)
        cbSportType5.setOnCheckedChangeListener(sportLambda)
        cbSportType6.setOnCheckedChangeListener(sportLambda)
        cbSportType7.setOnCheckedChangeListener(sportLambda)


        btnNext.onClick {

            with(AppData.userInfo!!){
                target = this@SportGoalActivity.target.joinToString(separator = ",")
                sports = this@SportGoalActivity.sports.joinToString(separator = ",")
            }

            p.setMember(AppData.userInfo!!)

        }

        btnNext.alpha = .5f
        btnNext.isClickable = false

        Observable.combineLatest(
                observableTarget,
                observableSport,
                BiFunction<HashSet<String>, HashSet<String>, Boolean> { t1, t2 -> t1.isNotEmpty() && t2.isNotEmpty() }
        ).subscribe {
            btnNext.alpha = if(it){ 1f } else { .5f }
            btnNext.isClickable = it
        }



    }


    override fun getLayoutId(): Int {
        return R.layout.activity_personal_sport_goal
    }

    override fun newP(): PLogin? {
        return PLogin()
    }

    companion object {
        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(SportGoalActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }


}
