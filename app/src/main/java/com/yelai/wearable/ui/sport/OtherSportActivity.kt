package com.yelai.wearable.ui.sport

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import cn.droidlover.xdroidmvp.router.Router
import com.baidu.mapapi.map.BitmapDescriptor
import com.yelai.wearable.AppData
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.contract.SportContract
import com.yelai.wearable.model.LocalSportRecord
import com.yelai.wearable.present.PSport
import com.yelai.wearable.showToast
import com.yelai.wearable.step.utils.DbUtils
import com.yelai.wearable.utils.GeneralUtil
import com.yelai.wearable.widget.slider.CustomSlideToUnlockView
import kotlinx.android.synthetic.main.sport_activity_other.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*


/**
 * Created by  hr on 2018/11/3.
 * desc:运动共有的父类
 */
 class OtherSportActivity : BaseSportActivityV2<SportContract.Presenter>(),SportContract.View{


    override fun getLayoutId(): Int = R.layout.sport_activity_other

    override fun newP(): PSport = PSport()

    private var isBind = false

    var sportId:Int? = null

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)

        if(errorMsg.contains("结束")){

            AppManager.finishCurrentActivity()
        }

    }

    override fun success(type: SportContract.Success, data: Any) {

        when(type){

            SportContract.Success.Start->{

                //开始成功
                sportId = data.toString().toInt()
                sliderView.visibility = View.VISIBLE

                showToast("请开始运动吧")

            }

            SportContract.Success.Sporting->{

                waitUploadSportRecordDatas.forEach { it.isUpload = "true" }

                DbUtils.updateALL(waitUploadSportRecordDatas)

                waitUploadSportRecordDatas.clear()

            }

            SportContract.Success.Finish->{
                finish()
            }

        }

    }

    private var distance = 0.0 // 跑步总距离
    private var time = 0 //跑步用时，单位秒
    private var heartRate = 0
    private var timer: Timer? = null
    private var timerTask: TimerTask? = null

    val sportType by lazy { intent.extras.getString("sport_type") }

    var idIndicator:Long = 0;


    override fun initData(savedInstanceState: Bundle?) {


        val a = object : Animation() {
            protected override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                val lparams = ivFloatBar.layoutParams as ConstraintLayout.LayoutParams
                lparams.horizontalBias = 0.0f
                ivFloatBar.layoutParams = lparams

            }
        }
        a.duration = 8 // in ms
        a.fillAfter = true
        ivFloatBar.startAnimation(a)
        tvFloatBar.text = "0"




        btnContinue.onClick {
            btnSlider.visibility = View.VISIBLE
            rlContinue.visibility = View.GONE
            btnSlider.resetView()
            resumeRun()
        }

        btnFinish.onClick {
            //上报数据
            finishRun()
        }

        btnSlider.setmCallBack(object : CustomSlideToUnlockView.CallBack {
            override fun onSlide(distance: Int) {}

            override fun onUnlocked() {
                btnSlider.visibility = View.GONE
                rlContinue.visibility = View.VISIBLE
                pauseRun()
            }
        })

        startRun()

    }


    override fun stepChanged(){

        System.out.println("步数改变=》" + step.toString())

        tvStepCount.text = step.toString()
//        tvRunDistance.text = "0.12"
//        tvBurnCal.text = "10"

    }


    public override fun onDestroy() {
        super.onDestroy()
        if (isBind) {
            this.unbindService(conn)
        }
    }



    private fun startRun() {

        DbUtils.createDb(this, LocalSportRecord::class.java)
//        DbUtils.deleteAll(LocalSportRecord::class.java)




        startTimer()


        cleanStep()

        startStepService()


        p.sportStart(mapOf(
                "member_id" to AppData.user!!.memberId,
                "type" to sportType,
                "lat" to "0",
                "lnt" to "0"))
    }

    private fun pauseRun() {

        stopStepService()
        stopTimer() // 停止计时


    }

    private fun resumeRun() {
        startTimer() // 开始计时

        startStepService()

    }

    private fun finishRun(){

        stopStepService()

        try {

            DbUtils.insert(LocalSportRecord().apply {
                _id = idIndicator++
                member_id = AppData.user!!.memberId
                sport_id = sportId!!
                total_km = GeneralUtil.doubleToString(distance).toDouble()
                total_time = time
                total_step = step
                heart_rate = heartRate
                lat = "0"
                lnt = "0"
            })

            var list = DbUtils.getQueryByWhereLength(LocalSportRecord::class.java,"isUpload", listOf<String>("false").toTypedArray(),0,200);
            waitUploadSportRecordDatas.addAll(list)
            p.sportFinish(waitUploadSportRecordDatas)

        }catch (e:Exception){
            e.printStackTrace()
        }



    }





    /**
     * 开始计时
     */
    private fun startTimer() {
        if (timer == null) {
            timer = Timer()
        }
        if (timerTask == null) {
            timerTask = object : TimerTask() {
                override fun run() {
                    time++
                    if(sportId == null)return

                    if(time % 2 == 0){
                        if(DbUtils.getQueryByWhereLength(LocalSportRecord::class.java,"total_time", listOf<String>(time.toString()).toTypedArray(),0,1).size == 1) return;

                        DbUtils.insert(LocalSportRecord().apply {
                            _id = idIndicator++
                            member_id = AppData.user!!.memberId
                            sport_id = sportId!!
                            total_km = GeneralUtil.doubleToString(distance).toDouble()
                            total_time = time
                            total_step = step
                            heart_rate = heartRate
                            lat = "0"
                            lnt = "0"
                        })
                    }

                    var list : ArrayList<LocalSportRecord> = ArrayList();

                    if(time % 300 == 0 ||
                            list.apply { this.addAll(DbUtils.getQueryByWhereLength(LocalSportRecord::class.java,"isUpload", listOf<String>("false").toTypedArray(),0,300))}.size > 300){

                        waitUploadSportRecordDatas.addAll(list)

                        p.sporting(waitUploadSportRecordDatas)

                    }

                }
            }
        }

        if (timer != null && timerTask != null) {
            timer!!.schedule(timerTask, 1000, 1000)
        }

    }

    /**
     * 结束计时
     */
    private fun stopTimer() {

        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
        if (timerTask != null) {
            timerTask!!.cancel()
            timerTask = null
        }


    }

    var waitUploadSportRecordDatas = ArrayList<LocalSportRecord>(200)


    companion object {

        /**
         * 图标
         */
        private var realtimeBitmap: BitmapDescriptor? = null

        fun launch(activity: Context, type:String) {
            Router.newIntent(activity as Activity)
                    .to(OtherSportActivity::class.java)
                    .putString("sport_type",type)
                    .launch()
        }
    }




}
