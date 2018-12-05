package com.yelai.wearable.ui.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import cn.droidlover.xdroidmvp.router.Router
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.present.PViod
import kotlinx.android.synthetic.main.activity_personal_info_height_weight.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by hr on 18/9/15.
 */

class HeightWeightActivity : BaseActivity<PViod>() {

    override fun initData(savedInstanceState: Bundle?) {
        showToolbar()
        mToolbar.setMiddleText("个人信息", ContextCompat.getColor(context,R.color.color_black))
    }


    var heightValue:Int = 160
    var weightValue:Int = 50

    override fun bindEvent() {
        super.bindEvent()

        rulerWeight.setOnValueChangedListener { value ->
            run {
                weightValue = (value * 10).toInt()
                txtWeight.text = value.toString() + "Kg"
            }
        }

        rulerHeight.setOnValueChangedListener { value -> run {
            heightValue = value.toInt()
            txtHeight.text = value.toInt().toString() + "Cm"
        }
        }

        btnNext.onClick {

            with(AppData.userInfo!!){
                height = heightValue
                weight = weightValue
            }



            SportStatusActivity.launch(this@HeightWeightActivity.context)


        }
    }



    override fun getLayoutId(): Int {
        return R.layout.activity_personal_info_height_weight
    }

    override fun newP(): PViod? {
        return null
    }

    companion object {
        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(HeightWeightActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }


}
