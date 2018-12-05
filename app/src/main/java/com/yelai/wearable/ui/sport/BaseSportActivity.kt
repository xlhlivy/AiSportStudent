package com.yelai.wearable.ui.sport

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.RemoteException
import cn.droidlover.xdroidmvp.mvp.IPresent
import com.today.step.lib.ISportStepInterface
import com.today.step.lib.TodayStepManager
import com.today.step.lib.TodayStepService
import com.yelai.wearable.App
import com.yelai.wearable.base.BaseActivity


/**
 * Created by  hr on 2018/11/3.
 * desc:运动共有的父类
 */

abstract class BaseSportActivity<P : IPresent<*>> : BaseActivity<P>(){


    private val TAG = "MainActivity"

    private val REFRESH_STEP_WHAT = 0

    //循环取当前时刻的步数中间的间隔时间
    private val TIME_INTERVAL_REFRESH: Long = 500

    private val mDelayHandler = Handler(TodayStepCounterCall())

    protected var mStepSum: Int = 0

    private var iSportStepInterface: ISportStepInterface? = null

    private var serviceConnection:ServiceConnection? = null;


    final fun initStepManager(){
        //初始化计步模块
        TodayStepManager.init(App.context as Application)

        //开启计步Service，同时绑定Activity进行aidl通信
        val intent = Intent(this, TodayStepService::class.java)
        startService(intent)


        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                //Activity和Service通过aidl进行通信
                iSportStepInterface = ISportStepInterface.Stub.asInterface(service)
                try {
                    mStepSum = iSportStepInterface!!.currentTimeSportStep
                    updateStepCount()
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }

                mDelayHandler.sendEmptyMessageDelayed(REFRESH_STEP_WHAT, TIME_INTERVAL_REFRESH)
            }

            override fun onServiceDisconnected(name: ComponentName) {

            }
        };

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)



    }

    final fun stopStepManager(){
        unbindService(serviceConnection)
        stopService(Intent(this, TodayStepService::class.java))
    }

    internal inner class TodayStepCounterCall : Handler.Callback {

        override fun handleMessage(msg: Message): Boolean {
            when (msg.what) {
                REFRESH_STEP_WHAT -> {
                    //每隔500毫秒获取一次计步数据刷新UI
                    if (null != iSportStepInterface) {
                        var step = 0
                        try {
                            step = iSportStepInterface!!.currentTimeSportStep
                        } catch (e: RemoteException) {
                            e.printStackTrace()
                        }

                        if (mStepSum != step) {
                            mStepSum = step
                            updateStepCount()
                        }
                    }
                    mDelayHandler.sendEmptyMessageDelayed(REFRESH_STEP_WHAT, TIME_INTERVAL_REFRESH)
                }
            }
            return false
        }
    }

     abstract fun updateStepCount();


//    fun onClick(view: View) {
//        when (view.id) {
//            R.id.stepArrayButton -> {
//                //获取所有步数列表
//                if (null != iSportStepInterface) {
//                    try {
//                        val stepArray = iSportStepInterface!!.todaySportStepArray
//                        mStepArrayTextView!!.text = stepArray
//                    } catch (e: RemoteException) {
//                        e.printStackTrace()
//                    }
//
//                }
//            }
//            R.id.stepArrayButton1 -> {
//                //根据时间来获取步数列表
//                if (null != iSportStepInterface) {
//                    try {
//                        val stepArray = iSportStepInterface!!.getTodaySportStepArrayByDate("2018-01-19")
//                        mStepArrayTextView!!.text = stepArray
//                    } catch (e: RemoteException) {
//                        e.printStackTrace()
//                    }
//
//                }
//            }
//            R.id.stepArrayButton2 -> {
//                //获取多天步数列表
//                if (null != iSportStepInterface) {
//                    try {
//                        val stepArray = iSportStepInterface!!.getTodaySportStepArrayByStartDateAndDays("2018-01-20", 6)
//                        mStepArrayTextView!!.text = stepArray
//                    } catch (e: RemoteException) {
//                        e.printStackTrace()
//                    }
//                }
//            }
//            else -> {
//            }
//        }
//    }


}
