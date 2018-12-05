package com.yelai.wearable.ui.day

import android.app.Dialog
import android.content.Context
import android.view.KeyEvent
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.yelai.wearable.R
import kotlinx.android.synthetic.main.calendar_dialog.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onScrollChange
import java.util.*


/**
 * 自定义透明的dialog
 */
class CalendarPickerViewDialog constructor(context: Context,val callback:Callback) : Dialog(context, R.style.CustomDialog) {

    @FunctionalInterface
    public interface Callback{
        fun done(calendar:Calendar)
    }




    init {
        setContentView(R.layout.calendar_dialog)

        setCanceledOnTouchOutside(true)
        val windowManager = window!!.windowManager
        val display = windowManager.defaultDisplay
        val lp = window!!.attributes
        lp.width = (display.width ).toInt() //设置宽度
//        lp.alpha = 0.9f
        window!!.attributes = lp
        setCancelable(true)
        initView()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK//拦截返回按键事件
            -> {

            }
        }
        return true
    }


    var selectedTime = 0L;
    fun show(time:Long){
        show()
        selectedTime = time;
        resetSelectedCalendar()

    }

    override fun show() {
        super.show()
    }


    fun resetSelectedCalendar(){
        if(calendarView != null && selectedTime > 100){

            try{

                val date = Date(selectedTime * 1000)

                val calendar = java.util.Calendar.getInstance()

                calendar.time = date

                calendarView.scrollToCalendar(calendar.get(java.util.Calendar.YEAR),calendar.get(java.util.Calendar.MONTH) + 1,calendar.get(java.util.Calendar.DATE))

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }




    fun initView() {


        emptyView.onClick {
            dismiss()
        }

        calendarView.setRange(2018,11,1,2030,12,3)

        calendarView.setOnMonthChangeListener { year, month ->

//            resetSelectedCalendar()

        }



        tvSelectView.text = "${calendarView.curYear}.${calendarView.curMonth}.${calendarView.curDay}"

        tv_current_day.text = "${calendarView.curDay}"

        tvNextMonth.onClick {
            calendarView.scrollToNext(true)
        }

        tvPrevMonth.onClick {
            calendarView.scrollToPre(true)
        }

        fl_current.onClick {
            calendarView.scrollToCurrent(true)
        }

        tvSelectView.onClick {
            calendarView.scrollToSelectCalendar()
        }

        calendarView.scrollToCurrent()


        calendarView.setOnCalendarSelectListener(object:CalendarView.OnCalendarSelectListener{
            override fun onCalendarSelect(calendar: Calendar, isClick: Boolean) {
                if(isClick){
                    tvSelectView.text = "${calendar.year}.${calendar.month}.${calendar.day}"
                    callback.done(calendar)
                    dismiss()
                }
            }
            override fun onCalendarOutOfRange(calendar: Calendar?) {
            }

        })

    }




}