package com.yelai.wearable.ui.sport

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Notification
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageView
import android.widget.Toast
import android.widget.ZoomControls
import cn.droidlover.xdroidmvp.router.Router
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.model.LatLngBounds
import com.baidu.mapapi.utils.DistanceUtil
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.jakewharton.rxbinding2.widget.RxTextView
import com.yelai.wearable.AppData
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.contract.SportContract
import com.yelai.wearable.entity.LocalLog
import com.yelai.wearable.entity.TabEntity
import com.yelai.wearable.model.LocalSportRecord
import com.yelai.wearable.present.PSport
import com.yelai.wearable.showToast
import com.yelai.wearable.step.StepService
import com.yelai.wearable.step.utils.DbUtils
import com.yelai.wearable.ui.PermissionRequireDialog
import com.yelai.wearable.utils.GeneralUtil
import com.yelai.wearable.utils.NotificationUtils
import com.yelai.wearable.widget.FullScreenPopup
import com.yelai.wearable.widget.slider.CustomSlideToUnlockView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.sport_activity_run_with_map.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColorResource
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.*
import kotlin.collections.ArrayList


class SportRunWithMapActivity : BaseSportActivityV2<SportContract.Presenter>(),SportContract.View {

    override fun stepChanged() {

        println("当前步数:" + step)

    }

    var sportId:Int? = null

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)

        if(errorMsg.contains("结束")){

            AppManager.finishCurrentActivity()
        }

    }


    override fun getLayoutId(): Int = R.layout.sport_activity_run_with_map


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

    private var isStart = false // 标示 是否开始运动，默认false，未开始
    private var distance = 0.0 // 跑步总距离
    private var time = 0 //跑步用时，单位秒
//    private var step = 0
    private var heartRate = 0
    private var timer: Timer? = null
    private var timerTask: TimerTask? = null

    /**
     * 定位客户端
     */
    var mLocationClient: LocationClient? = null
    /**
     * 定位监听器
     */
    private val locationListener = MyLocationListener()

    private val pointList = ArrayList<LatLng>() //坐标点集合

    private val speedList = ArrayList<Float>() // 速度集合

    private var baiduMap: BaiduMap? = null  //地图对象

    /**
     * 地图状态更新
     */
    private var update: MapStatusUpdate? = null

    /**
     * 实时点覆盖物
     */
    private var realtimeOptions: OverlayOptions? = null

    /**
     * 开始点覆盖物
     */
    private var startOptions: OverlayOptions? = null

    /**
     * 结束点覆盖物
     */
    private var endOptions: OverlayOptions? = null

    /**
     * 路径覆盖物
     */
    private var polyLine: PolylineOptions? = null


    val sportType by lazy { intent.extras.getString("sport_type") }

    var idIndicator:Long = 0;

    val heartRateHbias = PublishSubject.create<Float>()



    override fun newP(): PSport = PSport()

    override fun initData(savedInstanceState: Bundle?) {

//        DbUtils.createDb(this, LocalSportRecord::class.java)
//        DbUtils.deleteAll(LocalSportRecord::class.java)

        DbUtils.createDb(this, LocalLog::class.java)

        RxTextView.textChanges(tvRunDistance).subscribe { tvRunDistance1.text = it }

        RxTextView.textChanges(tvRunTime).subscribe { tvRunTime1.text = it }

        heartRateHbias.subscribe{

            val a = object : Animation() {
                protected override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                    val lparams = ivFloatBar.layoutParams as ConstraintLayout.LayoutParams
                    lparams.horizontalBias = it
                    ivFloatBar.layoutParams = lparams

                    val lparams2 = ivFloatBar.layoutParams as ConstraintLayout.LayoutParams
                    lparams2.horizontalBias = it
                    ivFloatBar1.layoutParams = lparams2

                }
            }
            a.duration = 8 // in ms
            a.fillAfter = true
            ivFloatBar.startAnimation(a)
            tvFloatBar.text = "0"

            tvFloatBar1.startAnimation(a)
            tvFloatBar1.text = "0"
        }

        heartRateHbias.onNext(0.0f)

        baiduMap = mapView!!.map

        val child = mapView.getChildAt(1)
        if (child != null && (child is ImageView || child is ZoomControls)) {
            child!!.visibility = View.INVISIBLE
        }

        // 不显示地图上比例尺
        mapView.showScaleControl(false)
        // 不显示地图缩放控件（按钮控制栏）
        mapView.showZoomControls(false)




        mLocationClient = LocationClient(context)
        mLocationClient!!.registerLocationListener(locationListener)

        initLocation()


        val mTabEntities = ArrayList<CustomTabEntity>()
        mTabEntities.add(TabEntity("地图模式", 0, 0))
        mTabEntities.add(TabEntity("无地图模式", 0, 0))
        ctlLayout.setTabData(mTabEntities)

        ctlLayout.setOnTabSelectListener(object:OnTabSelectListener{
            override fun onTabSelect(position: Int) {
                if(position == 1){//当前有地图模式

                    //设置无地图模式的参数以及配置
                    clWithMap.visibility = View.GONE
                    clWithoutMap.visibility = View.VISIBLE

                    btnSlider.viewBackgroundResId = R.drawable.sport_block_white
                    btnSlider.slideImageViewResId = R.drawable.sport_icon_slide
                    btnSlider.tv_hint.textColorResource = R.color.text_black_color

                    btnSlider.resetView()

                }else{//当前是无地图模式

                    clWithMap.visibility = View.VISIBLE
                    clWithoutMap.visibility = View.GONE

                    btnSlider.viewBackgroundResId = R.drawable.sport_block_green
                    btnSlider.slideImageViewResId = R.drawable.sport_icon_slide
                    btnSlider.tv_hint.textColorResource = R.color.white;
                    btnSlider.resetView()


                }
            }

            override fun onTabReselect(position: Int) {}

        });




        btnContinue.onClick {
            btnSlider.visibility = View.VISIBLE
            rlContinue.visibility = View.GONE
            btnSlider.resetView()
            resumeRun()
        }

        btnFinish.onClick {

            //上报数据
            stopRun()
        }

        btnSlider.setmCallBack(object : CustomSlideToUnlockView.CallBack {
            override fun onSlide(distance: Int) {}

            override fun onUnlocked() {
                btnSlider.visibility = View.GONE
                rlContinue.visibility = View.VISIBLE
                pauseRun()
            }
        })

        startRun();

    }


    /**
     * 绘制轨迹
     * @param latLng
     */
    private fun drawTrace(latLng: LatLng) {

        Log.i("TAG", "绘制实时点")

        baiduMap!!.clear() //清除覆盖物

        val mapStatus = MapStatus.Builder().target(latLng).zoom(17f).build()

        update = MapStatusUpdateFactory.newMapStatus(mapStatus)

        //实时点
        realtimeBitmap = BitmapDescriptorFactory.fromResource(R.drawable.sport_icon_point)

        if (isStart) {
            realtimeOptions = MarkerOptions().position(latLng).icon(realtimeBitmap!!)
                    .zIndex(9).draggable(true)
        }
        // 开始点
        val startBitmap = BitmapDescriptorFactory.fromResource(R.drawable.sport_icon_startpoint)

        if (pointList.size > 1) {
            startOptions = MarkerOptions().position(pointList[0]).icon(startBitmap).zIndex(9).draggable(true)
        }

        // 路线
        if (pointList.size >= 2) {

            polyLine = PolylineOptions().width(5).color(Color.GREEN).points(pointList)
        }
        addMarker()
    }

    /**
     * 添加地图覆盖物
     */
    private fun addMarker() {

        Log.i("TAG", "添加覆盖物")
        if (null != update) {
            baiduMap!!.setMapStatus(update)
        }
        //开始点覆盖物
        if (null != startOptions) {
            baiduMap!!.addOverlay(startOptions)
        }
        // 路线覆盖物
        if (null != polyLine) {
            baiduMap!!.addOverlay(polyLine)
        }
        // 实时点覆盖物
        if (null != realtimeOptions) {
            baiduMap!!.addOverlay(realtimeOptions)
        }
        //结束点覆盖物
        if (null != endOptions) {
            baiduMap!!.addOverlay(endOptions)
        }

    }


    /**
     * 初始化定位，设置定位参数
     */
    private fun initLocation() {
        //用来设置定位sdk的定位方式
        val option = LocationClientOption()
        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll")//可选，默认gcj02，设置返回的定位结果坐标系
        val span = 2000
        option.setScanSpan(span)//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true)//可选，设置是否需要地址信息，默认不需要
        option.isOpenGps = true//可选，默认false,设置是否使用gps
        option.isLocationNotify = true//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true)//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false)//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false)//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false)//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false)//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient!!.locOption = option



    }

    private var mNotificationUtils: NotificationUtils? = null
    private var notification: Notification? = null


    private fun android8(){
        //设置后台定位
        //android8.0及以上使用NotificationUtils
        if (Build.VERSION.SDK_INT >= 26) {
            mNotificationUtils = NotificationUtils(this)
            val builder2 = mNotificationUtils!!.getAndroidChannelNotification("已开启定位功能", "正在使用位置权限")
            notification = builder2.build()
        } else {
            //获取一个Notification构造器
            val builder = Notification.Builder(context)
            val nfIntent = Intent(context, SportRunWithoutMapActivity::class.java)

            builder.setContentIntent(PendingIntent.getActivity(context, 0, nfIntent, 0)) // 设置PendingIntent
                    .setContentTitle("已开启定位功能") // 设置下拉列表里的标题
                    .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                    .setContentText("正在使用位置权限") // 设置上下文内容
                    .setWhen(System.currentTimeMillis()) // 设置该通知发生的时间

            notification = builder.build() // 获取构建好的Notification
        }
        notification!!.defaults = Notification.DEFAULT_SOUND //设置为默认的声音

        mLocationClient!!.enableLocInForeground(1, notification)

    }

    /**
     * 定位监听器
     */
    private inner class MyLocationListener : BDAbstractLocationListener() {

        override fun onReceiveLocation(bdLocation: BDLocation) {

            if (bdLocation.locType == BDLocation.TypeGpsLocation || bdLocation.locType == BDLocation.TypeNetWorkLocation) { //gps,网络定位成功定位

                val latitude = bdLocation.latitude //纬度
                val longitude = bdLocation.longitude // 经度
                val radius = bdLocation.radius.toDouble() //精度
                var speed = 0f
                if (bdLocation.hasSpeed()) {
                    speed = bdLocation.speed
                    speedList.add(speed)

                    Log.i("TAG", "速度$speed")
                }
                val latLng = LatLng(latitude, longitude) //坐标点

                if (Math.abs(latitude - 0.0) < 0.000001 && Math.abs(longitude - 0.0) < 0.000001) {

                } else {


                    if (pointList.size < 1) { //初次定位

                        pointList.add(latLng)

                        p.sportStart(mapOf(
                                "member_id" to AppData.user!!.memberId,
                                "type" to sportType,
                                "lat" to latitude,
                                "lnt" to longitude))

                    } else {
                        val lastPoint = pointList[pointList.size - 1]//上一次定位坐标点
                        val rang = DistanceUtil.getDistance(lastPoint, latLng) // 两次定位的距离

//                        if (rang > 3)
                        run{


                            if(sportId == null)return@run

                            //重复数据不添加
                            if(DbUtils.getQueryByWhereLength(LocalSportRecord::class.java,"total_time", listOf<String>(time.toString()).toTypedArray(),0,1).size == 1) return@run;

                            distance += rang
                            pointList.add(latLng)

                            DbUtils.insert(LocalSportRecord().apply {
                                _id = idIndicator++
                                member_id = AppData.user!!.memberId
                                sport_id = sportId!!
                                total_km = GeneralUtil.doubleToString(distance).toDouble()
                                total_time = time
                                total_step = step
                                heart_rate = heartRate
                                lat = latitude.toString()
                                lnt = longitude.toString()

                            })


                            var list : ArrayList<LocalSportRecord> = ArrayList();

                            if(time % 300 == 0 ||
                                    list.apply { this.addAll(DbUtils.getQueryByWhereLength(LocalSportRecord::class.java,"isUpload", listOf<String>("false").toTypedArray(),0,300))}.size > 300){

                                waitUploadSportRecordDatas.addAll(list)

                                if(waitUploadSportRecordDatas.size != 0){
                                    p.sporting(waitUploadSportRecordDatas)
                                }
                            }


                        }
                    }
                    tvRunDistance!!.text = GeneralUtil.doubleToString(distance)

                }
                drawTrace(latLng)

            } else if (bdLocation.locType == BDLocation.TypeServerError) { //服务器错误
                Toast.makeText(context, "服务器错误，请稍后重试", Toast.LENGTH_SHORT).show()
            } else if (bdLocation.locType == BDLocation.TypeNetWorkException) {
                Toast.makeText(context, "网络错误，请连接网络", Toast.LENGTH_SHORT).show()
            } else if (bdLocation.locType == BDLocation.TypeCriteriaException) {
                Toast.makeText(context, "请允许使用定位权限", Toast.LENGTH_SHORT).show()
            }
        }

    }

    /**
     * 绘制最终完成地图
     *
     */
    private fun drawFinishMap() {
        baiduMap!!.clear()

        val startLatLng = pointList[0]
        val endLatLng = pointList[pointList.size - 1]

        //地理范围
        val bounds = LatLngBounds.Builder().include(startLatLng).include(endLatLng).build()

        update = MapStatusUpdateFactory.newLatLngBounds(bounds)

        if (pointList.size >= 2) {

            // 开始点
            val startBitmap = BitmapDescriptorFactory.fromResource(R.drawable.sport_icon_startpoint)
            startOptions = MarkerOptions().position(startLatLng).icon(startBitmap).zIndex(9).draggable(true)

            // 终点
            val endBitmap = BitmapDescriptorFactory.fromResource(R.drawable.sport_icon_endpoint)
            endOptions = MarkerOptions().position(endLatLng)
                    .icon(endBitmap).zIndex(9).draggable(true)

            polyLine = PolylineOptions().width(10).color(Color.GREEN).points(pointList)
        } else {
            //实时点
            realtimeBitmap = BitmapDescriptorFactory.fromResource(R.drawable.sport_icon_point)
            realtimeOptions = MarkerOptions().position(startLatLng).icon(realtimeBitmap!!)
                    .zIndex(9).draggable(true)

        }

        addMarker()
    }


    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
        mLocationClient!!.disableLocInForeground(true)
        mLocationClient!!.unRegisterLocationListener(locationListener)

        mLocationClient!!.stop()

    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }




    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }


    var secondCounterConnection: ServiceConnection = object : ServiceConnection {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val stepService = (service as BackGroundService.MyBinder).service

            //设置步数监听回调
            stepService.registerCallback {
                runOnUiThread {
                    callback()
                }
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

    private fun callback(){
        time++
        tvRunTime!!.text = GeneralUtil.secondsToString(time)
    }


    private var isCounterBind = false

    /**
     * 开启计步服务
     */
    protected fun startCounterService() {
        if(!isCounterBind){
            val intent = Intent(this, BackGroundService::class.java)
            isCounterBind = bindService(intent, secondCounterConnection, Context.BIND_AUTO_CREATE)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //android8.0以上通过startForegroundService启动service
                startForegroundService(intent);
            }else{
                startService(intent)
            }
        }
    }

    protected fun stopCounterService() {
        if (isCounterBind) {
            val intent = Intent(this, BackGroundService::class.java)
            unbindService(secondCounterConnection)
            stopService(intent)
            isCounterBind = false
        }
    }


    /**
     * 开始计时
     */
    private fun startTimer() {
        startCounterService()
    }

    /**
     * 结束计时
     */
    private fun stopTimer() {
        stopCounterService();
    }

    override fun onRationaleDenied(requestCode: Int) {
        super.onRationaleDenied(requestCode)
        AppManager.finishCurrentActivity()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        // 用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this)
                    .setRationale("此功能需要定位权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("好")
                    .setNegativeButton("不行")
                    .build()
                    .show()
        } else if (!EasyPermissions.hasPermissions(this, *perms.toTypedArray())) {
            requirePermission()
        }
    }

    override fun requirePermission() {
        startRun()
    }


    @AfterPermissionGranted(222)
    private fun startRun() {
        val perms = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

        if (EasyPermissions.hasPermissions(this, *perms)) {

            android8()
            isStart = true
            mLocationClient!!.start()
            startTimer()
            cleanStep()
            startStepService()

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "跑步需要定位权限,请允许", 222, *perms)
        }
    }

    private fun pauseRun() {
        mLocationClient!!.stop()
        stopTimer() // 停止计时
        stopStepService()



    }

    private fun resumeRun() {
        startTimer() // 开始计时
        startStepService()
    }

    var waitUploadSportRecordDatas = ArrayList<LocalSportRecord>(300)

    private fun stopRun() {

        try {

            stopTimer()
            //绘制完成轨迹图
            drawFinishMap()

            val lastPoint = pointList.lastOrNull() ?: return//上一次定位坐标点

            val lastSportRecord = LocalSportRecord().apply {
                _id = idIndicator++
                member_id = AppData.user!!.memberId
                sport_id = sportId!!
                total_km = GeneralUtil.doubleToString(distance).toDouble()
                total_time = time
                total_step = step
                heart_rate = heartRate
                lat = lastPoint.latitude.toString()
                lnt = lastPoint.longitude.toString()
            }

            DbUtils.insert(lastSportRecord)


            var list = DbUtils.getQueryByWhereLength(LocalSportRecord::class.java,"isUpload", listOf<String>("false").toTypedArray(),0,300);
            waitUploadSportRecordDatas.addAll(list)

            if(waitUploadSportRecordDatas.size == 0){
                waitUploadSportRecordDatas.add(lastSportRecord)
            }

            p.sportFinish(waitUploadSportRecordDatas)

        } catch (e: Exception) {

            e.printStackTrace()
        }

    }

    companion object {

        /**
         * 图标
         */
        private var realtimeBitmap: BitmapDescriptor? = null

        fun launch(activity: Context,type:String) {
            Router.newIntent(activity as Activity)
                    .to(SportRunWithMapActivity::class.java)
                    .putString("sport_type",type)
                    .launch()
        }
    }

}
