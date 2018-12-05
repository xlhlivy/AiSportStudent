package com.yelai.wearable.ui.sport

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import cn.droidlover.xdroidmvp.mvp.IPresent
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.step.StepData
import com.yelai.wearable.step.StepService
import com.yelai.wearable.step.utils.DbUtils


/**
 * Created by  hr on 2018/11/3.
 * desc:运动共有的父类
 */

abstract class BaseSportActivityV2<P : IPresent<*>> : BaseActivity<P>(){

    private var isBind = false

    protected var step = 0

    protected fun cleanStep(){
        DbUtils.createDb(this, StepData::class.java)
        DbUtils.deleteAll(StepData::class.java)
    }


    /**
     * 开启计步服务
     */
    protected fun startStepService() {
        if(!isBind){
            val intent = Intent(this, StepService::class.java)
            isBind = bindService(intent, conn, Context.BIND_AUTO_CREATE)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //android8.0以上通过startForegroundService启动service
                startForegroundService(intent);
            }else{
                startService(intent)
            }

        }
    }

    protected fun stopStepService() {
        if (isBind) {
            val intent = Intent(this, StepService::class.java)
            unbindService(conn)
            stopService(intent)
            isBind = false
        }
    }

    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    internal var conn: ServiceConnection = object : ServiceConnection {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val stepService = (service as StepService.StepBinder).service

            //设置初始化数据
            step = stepService.stepCount

            runOnUiThread{stepChanged()}

            //设置步数监听回调
            stepService.registerCallback { stepCount ->
                this@BaseSportActivityV2.step = stepCount
                runOnUiThread{stepChanged()}
            }
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        override fun onServiceDisconnected(name: ComponentName) {

        }
    }

    abstract fun stepChanged();


    public override fun onDestroy() {
        super.onDestroy()
        stopStepService()
//        if (isBind) {
//            this.unbindService(conn)
//        }
    }


}
