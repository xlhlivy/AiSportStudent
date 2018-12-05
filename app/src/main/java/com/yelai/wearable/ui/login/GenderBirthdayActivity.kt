package com.yelai.wearable.ui.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import cn.droidlover.xdroidmvp.router.Router
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.BasePickerView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.present.PViod
import kotlinx.android.synthetic.main.activity_personal_info_gender_birthday.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by hr on 18/9/15.
 */

class GenderBirthdayActivity : BaseActivity<PViod>() {

    override fun initData(savedInstanceState: Bundle?) {

        showToolbar()
        mToolbar.setMiddleText("个人信息",ContextCompat.getColor(context,R.color.color_black))

    }

    val birthdayPicker : BasePickerView by lazy { buildTimePicker()}

    var gender:Int = 1

    override fun bindEvent() {
        super.bindEvent()

        llFemale.onClick {
            it!!.alpha = 1f
            llMale.alpha = .5f
            gender = 2
        }

        llMale.onClick {
            it!!.alpha = 1f
            llFemale.alpha = .5f
            gender = 1
        }




        println("2" + btnNext.isClickable)

        btnBirthday.onClick {
            birthdayPicker.show()
        }


        btnNext.onClick {


            val birthdayValue = btnBirthday.text.trim().toString()

//            if(birthdayValue.length < 6){
//                showToast("请选择您的生日")
//                return@onClick
//            }

            with(AppData.userInfo!!){
                birthday = birthdayValue
                sex = gender
            }

            HeightWeightActivity.launch(this@GenderBirthdayActivity.context)
        }


        RxTextView.textChanges(btnBirthday).map { it.length > 6 }.subscribe{
            btnNext.alpha = if(it){ 1f } else { .5f }
            btnNext.isClickable = it
        }


    }


    fun buildTimePicker(): BasePickerView {


        val sdf = SimpleDateFormat("yyyy-MM-dd")

        val startTime = Calendar.getInstance()

        startTime.time = sdf.parse("1960-01-01")

        val endTime = Calendar.getInstance()

        endTime.time = sdf.parse("2018-12-31")


        val currentDate = Calendar.getInstance();

        currentDate.add(Calendar.YEAR,-20)


        val pvTime = TimePickerBuilder(context, OnTimeSelectListener { date, v ->

            btnBirthday.text = sdf.format(date)

        })
                .setTimeSelectChangeListener { }
                .setType(booleanArrayOf(true, true, true,false, false, false))
                .setRangDate(startTime,endTime)
                .setDate(currentDate)
                .isDialog(true)
                .build()

        val mDialog = pvTime.dialog
        if (mDialog != null) {

            val params = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM)

            params.leftMargin = 0
            params.rightMargin = 0
            pvTime.dialogContainerLayout.layoutParams = params

            val dialogWindow = mDialog.window
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim)//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM)//改成Bottom,底部显示
            }
        }
        return pvTime
    }



    override fun getLayoutId(): Int {
        return R.layout.activity_personal_info_gender_birthday
    }

    override fun newP(): PViod? {
        return null
    }

    companion object {
        fun launch(activity: Context) {
            Router.newIntent(activity as Activity)
                    .to(GenderBirthdayActivity::class.java)
                    .data(Bundle())
                    .launch()
        }
    }


}
