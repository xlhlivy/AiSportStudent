package com.yelai.wearable.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import cn.droidlover.xdroidmvp.cache.SharedPref
import com.yelai.wearable.App
import com.yelai.wearable.AppData
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.contract.UserContract
import com.yelai.wearable.present.PUser
import com.yelai.wearable.present.PViod
import com.yelai.wearable.ui.course.CourseDetailActivity
import com.yelai.wearable.ui.course.SearchActivity
import com.yelai.wearable.ui.login.IdentifyBindActivity
import com.yelai.wearable.ui.login.LoginActivity
import com.yelai.wearable.ui.sport.SportRunWithoutMapActivity
import com.yelai.wearable.utils.StatusBarUtil
import com.yelai.wearable.widget.slider.DensityUtil
import kotlinx.android.synthetic.main.activity_splash.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

/**
 * Created by hr on 18/9/15.
 */

class SplashActivity : BaseActivity<UserContract.Presenter>(),UserContract.View {


    override fun showError(errorMsg: String) {
        LoginActivity.launch(this@SplashActivity.context)
    }

    override fun memberInfoSuccess() {

        if(AppData.userInfo != null && AppData.userInfo!!.schoolName.isNotEmpty()){
            HomeActivity.launch(this@SplashActivity.context)
        }else{
            IdentifyBindActivity.launch(this@SplashActivity.context)
        }
    }

    private var textTypeface: Typeface?=null

    private var descTypeFace: Typeface?=null

    private var alphaAnimation: AlphaAnimation?=null

    init {
//        textTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/Lobster-1.4.otf")
//        descTypeFace = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun initData(savedInstanceState: Bundle?) {
//        tv_app_name.typeface = textTypeface
//        tv_splash_desc.typeface = descTypeFace
//        tv_version_name.text = "v${AppUtils.getVerName(MyApplication.context)}"

        DensityUtil.setScreenSize(this)

        //渐变展示启动屏
        alphaAnimation= AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 2000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                redirectTo()
            }
            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationStart(animation: Animation) {}

        })

//        StatusBarUtil.darkMode(context,R.color.background,0f)

//        checkPermission()


        if(AppData.isFirstTime){
            checkPermission()
        }else{
            methodRequiresTwoPermission()
        }

    }

    override fun requirePermission() {
        methodRequiresTwoPermission()
    }



    /**
     * 6.0以下版本(系统自动申请) 不会弹框
     * 有些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了
     */
    private fun checkPermission(){
        val perms = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        EasyPermissions.requestPermissions(this, "乐生应用需要以下权限，请允许", 0, *perms)
    }


    @AfterPermissionGranted(0)
    private fun methodRequiresTwoPermission() {
        val perms = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // Already have permission, do the thing
            if (alphaAnimation == null)return

            if (alphaAnimation != null) {
                view.startAnimation(alphaAnimation)
            }
        } else {
            // Do not have permissions, request them now
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isLackPermission(perms)) {
                requestPermissions(perms, 0)
            }
//            EasyPermissions.requestPermissions(this, "乐生应用需要以下权限，请允许", 0, *perms)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun newP(): PUser {
        return PUser()
    }



    fun redirectTo() {
        //是否已经登录，如果登录了，就直接跳转到HomeActivity 否则跳转到登录注册页面

        if(AppData.user != null){
            //已经登录过了->
            //检查信息是否完备
            with(AppData.user!!){
               if(isDone == "0"){//未完成注册信息

                   p.memberInfo(mapOf("member_id" to memberId))

               }else{//已完成注册信息
                   HomeActivity.launch(this@SplashActivity.context)
               }
            }
        }else{
            LoginActivity.launch(this@SplashActivity.context)
        }
//        val intent = Intent(this, if(App.isLogin()){HomeActivity::class.java} else {LoginActivity::class.java})
//        startActivity(intent)
        AppManager.finishCurrentActivity()

    }





    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {

        if (alphaAnimation == null)return

        if (alphaAnimation != null) {
            view.startAnimation(alphaAnimation)
        }
    }

    /**
     * 1.同时申请多项权限的时候有可能某些权限通过了，有些权限被禁止了
     * 2.如果在应用中设置了始终禁止某项权限在获取的时候会怎样
     * 先调用被允许的，在调用被拒绝的？还是说根据申请的先后顺序呢?
     * 3.onRationaleAccepted 后会执行向用户申请权限的操作
     *  a.如果没有被添加到禁止中
     *
     */

//    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
//        if (requestCode == 0) {
//            if (perms.isNotEmpty()) {
//                if (perms.contains(Manifest.permission.READ_PHONE_STATE)
//                        && perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//                    if (alphaAnimation == null)return
//
//                    if (alphaAnimation != null) {
//                        view.startAnimation(alphaAnimation)
//                    }
//                }
//            }
//        }
//    }
}
