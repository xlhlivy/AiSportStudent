package com.yelai.wearable

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.support.multidex.MultiDex
import android.util.Log
import cn.droidlover.xdroidmvp.cache.SharedPref
import cn.droidlover.xdroidmvp.net.NetError
import cn.droidlover.xdroidmvp.net.NetProvider
import cn.droidlover.xdroidmvp.net.RequestHandler
import cn.droidlover.xdroidmvp.net.XApi
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.bugly.crashreport.CrashReport
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import com.yelai.wearable.utils.DisplayManager
import com.zzhoujay.richtext.RichText
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import kotlin.properties.Delegates

/**
 * Created by wanglei on 2016/12/31.
 */

class App : Application() {

    private var appCount = 0


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        CrashReport.initCrashReport(applicationContext, "aae05ccac6", true);

        context = applicationContext
        initConfig()
        DisplayManager.init(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            builder.detectFileUriExposure()
        }


        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)

        ZXingLibrary.initDisplayOpinion(this);


        XApi.registerProvider(object : NetProvider {



            override fun configInterceptors(): Array<Interceptor> {

                return emptyArray()

//                return arrayOf(Interceptor { chain ->
//                    val request = chain.request().newBuilder().addHeader("Content-Type","application/json;charset=UTF-8").build()
//                    chain.proceed(request)
//                })

            }

            override fun configHttps(builder: OkHttpClient.Builder) {

            }

            override fun configCookie(): CookieJar? {
                return null
            }

            override fun configHandler(): RequestHandler? {
                return null
            }

            override fun configConnectTimeoutMills(): Long {
                return 0
            }

            override fun configReadTimeoutMills(): Long {
                return 0
            }

            override fun configLogEnable(): Boolean {
                return true
            }

            override fun handleError(error: NetError): Boolean {



                return false
            }

            override fun dispatchProgressEnable(): Boolean {
                return false
            }
        })
    }



    /**
     * 初始化配置
     */
    private fun initConfig() {
        RichText.initCacheDir(this)

        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 隐藏线程信息 默认：显示
                .methodCount(0)         // 决定打印多少行（每一行代表一个方法）默认：2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("hao_zz")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }


    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.d(TAG, "onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            Log.d(TAG, "onStart: " + activity.componentName.className)
            appCount++
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {
            appCount--
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.d(TAG, "onDestroy: " + activity.componentName.className)
        }
    }

    /**
     * app是否在前台
     * @return true前台，false后台
     */
    fun isForeground(): Boolean {
        return appCount > 0
    }


    companion object {

        private val TAG = "App"

        var context: Context by Delegates.notNull()
            private set

        fun isLogin():Boolean{
            return SharedPref.getInstance(context).getBoolean("is_login",false)
        }

        fun loginSuccess(){
            SharedPref.getInstance(context).putBoolean("is_login",true)
        }

        val isAndroidO = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    }

//    companion object {
//
//        var context: Context? = null
//            private set
//    }
}
