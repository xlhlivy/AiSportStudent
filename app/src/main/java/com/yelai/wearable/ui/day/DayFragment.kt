package com.yelai.wearable.ui.day

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.haibin.calendarview.Calendar
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.adapter.BaseAdapter
import com.yelai.wearable.adapter.ViewHolder
import com.yelai.wearable.base.BaseFragment
import com.yelai.wearable.entity.TabEntity
import com.yelai.wearable.load
import com.yelai.wearable.model.Card
import com.yelai.wearable.model.DayInfo
import com.yelai.wearable.net.DayContract
import com.yelai.wearable.net.PDay
import com.yelai.wearable.showToast
import com.yelai.wearable.utils.ImageUtil
import kotlinx.android.synthetic.main.day_fragment.*
import kotlinx.android.synthetic.main.day_gym_item.view.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColorResource
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by hr on 18/9/16.
 */

class DayFragment : BaseFragment<DayContract.Presenter>(),DayContract.View {


    inner class Adapter(context: Context) : BaseAdapter<Card>(context) {
        override fun getLayoutId(): Int = R.layout.day_gym_item


        override fun onBindViewHolder(holder: ViewHolder<Card>, position: Int) {
            val item = data[position]
            holder.setData(item)

            holder.itemView.onClick {

                GymActivity.launch(this@DayFragment.context,item.gymId)

            }
        }

        override fun setData(itemView: View, data: Card) {

            itemView.tvCardName.text = data.name

            itemView.tvCardDescription.text = data.desc

            itemView.ivCardBg.load(R.drawable.day_card_purple)

            itemView.tvCardTake.text = when(data.status){
                "0"->"领取"
                "1"->"去使用"
                else->"已使用"
            }

            itemView.tvCardTake.visibility = View.VISIBLE
            if(data.status == "2"){
                itemView.tvCardTake.visibility = View.GONE
            }

            itemView.tvCardTake.onClick {

                if(data.status == "0"){
                    p.useCard(data.cardId,1)
                }else{
                    GymActivity.launch(this@DayFragment.context,data.gymId,data)
//                    p.useCard(data.cardId,2)
                }
            }


        }

    }


    override fun success(type: DayContract.Success, data: Any) {

        if (type == DayContract.Success.DayInfo){

            dataUpadate(data as DayInfo)

        }else if(type == DayContract.Success.CardList){

            var list = data as ArrayList<Card>

            if(list == null || list.size == 0){
                list = ArrayList<Card>()
                list.add(Card().apply { gymId="";cardId = "";status = "2";name=""; desc=""})
            }


            adapter.setData(list)
            adapter.notifyDataSetChanged()

        }else if(type == DayContract.Success.UseCard){
//            showToast("领取卡券成功")
            GymDialog.attencance(this@DayFragment.context).show()
            p.cardList()
        }else if(type == DayContract.Success.JoinExam){

            showToast("加入考试成功")

        }
    }

    override fun onResumeLazy() {
        if(AppData.isBackFromPageWithDataAndCleanData(GymActivity::class.java)){
            p.cardList()
        }
    }


    val adapter by lazy {Adapter(context)}

    private var dayInfo:DayInfo? = null

    private var mTabDatas = ArrayList<DayInfo.HealthEvaluation>()


    private fun dataUpadate(data:DayInfo) {

        mTabDatas.clear()

        tvStepCurrent.text = data.totalStep
        tvStepGoal.text = "总目标${data.targetStep}步"

        tvGoalCurrent.text = data.totalKm
        tvGoalGoal.text = "总目标${data.targetKm}km"

        tvKcalCurrent.text = data.totalCal
        tvKcalGoal.text = "总目标${data.targetCal}卡路里"

        sportFragment.dataChange(data.sport)
        actionFragment.dataChange(data.behave)
        statusFragment.dataChange(data.status)

        mTabDatas.add(data.sport)
        mTabDatas.add(data.behave)
        mTabDatas.add(data.status)


        tvHitTxt.text = mTabDatas[ctlLayout.currentTab].rankTxt()


    }

    private val mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("运动", "行为", "状态")
    private val mIconUnselectIds = intArrayOf(R.drawable.day_tab_icon_sport_normal, R.drawable.day_tab_icon_action_normal, R.drawable.day_tab_icon_status_normal)
    private val mIconSelectIds = intArrayOf(R.drawable.day_tab_icon_sport_press, R.drawable.day_tab_icon_action_press, R.drawable.day_tab_icon_status_press)
    private val mTabEntities = ArrayList<CustomTabEntity>()

    private val sportFragment by lazy{DayHealthEvaluationSportFragment.newInstance(1)}
    private val actionFragment by lazy{DayHealthEvaluationSportFragment.newInstance(2)}
    private val statusFragment by lazy{DayHealthEvaluationSportFragment.newInstance(3)}

    override fun initData(savedInstanceState: Bundle?) {
//        super.initData(savedInstanceState)
        itbWearableBind.buttonTextView.textColorResource = R.color.tab_text_unselected
        itbWearableBind.iconView.imageResource = R.drawable.ic_arrow_white_24dp
//        ctlLayout

        mTabEntities.clear()
        mFragments.clear()

        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }

        mFragments.add(sportFragment)
        mFragments.add(actionFragment)
        mFragments.add(statusFragment)

//        for (title in mTitles) {
//        }

        println("DayFragment=>>>>>>>>>>>>>")

        ctlLayout.setTabData(mTabEntities,childFragmentManager,R.id.ctlContent,mFragments)

        ctlLayout.setOnTabSelectListener(object: OnTabSelectListener{
            override fun onTabSelect(position: Int) {

                if(position < mTabDatas.size){
                    tvHitTxt.text = mTabDatas[position].rankTxt()
                }



                val fragment = mFragments[position] as DayHealthEvaluationSportFragment
                if(fragment.dataValue == null)return
                fragment.dataChange(fragment.dataValue!!)

            }

            override fun onTabReselect(position: Int) {}

        })



        ivScan.onClick {
            imagePickerView.show()
        }

//        ivCardBg.onClick {
//            GymActivity.launch(this@DayFragment.context)
//        }

//        contentLayout.recyclerView.refreshData()


        itbCalendar.buttonTextView.text = SimpleDateFormat("yyyy.MM.dd").format(Date())

        p.dayInfo(queryTime.toInt())


        recyclerView.layoutManager = object : LinearLayoutManager(context, VERTICAL,false){
            override fun canScrollVertically(): Boolean = false
        }

        recyclerView.isNestedScrollingEnabled = false
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        p.cardList()



//        viewPager.adapter = MyPagerAdapter(childFragmentManager)

//        initViewpager()
    }


    override fun onRationaleDenied(requestCode: Int) {
        super.onRationaleDenied(requestCode)
//        AppManager.finishCurrentActivity()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        // 用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this)
                    .setRationale("此功能需要相机权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("好")
                    .setNegativeButton("不行")
                    .build()
                    .show()
        } else if (!EasyPermissions.hasPermissions(context, *perms.toTypedArray())) {
            requirePermission()
        }
    }


    val REQUEST_CODE = 222

    @AfterPermissionGranted(222)
    fun scan(){

        val perms = arrayOf(Manifest.permission.CAMERA)

        if (EasyPermissions.hasPermissions(context, *perms)) {

            val intent = Intent(this@DayFragment.context, ScanActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "扫码需要相机权限,请允许", 2, *perms)
        }
    }

    @AfterPermissionGranted(111)
    private fun getImage() {

        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (EasyPermissions.hasPermissions(context, *perms)) {
            //检查权限
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            startActivityForResult(intent, 111)
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "扫码本地图片需要访问文件权限,请允许", 1, *perms)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                val bundle = data.extras ?: return
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {


                    val result = bundle.getString(CodeUtils.RESULT_STRING)

                    joinExam(result)

//                    Toast.makeText(context, "解析结果:" + result!!, Toast.LENGTH_LONG).show()
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(context, "解析二维码失败", Toast.LENGTH_LONG).show()
                }
            }
        } else if (requestCode == 111) run {
            if (data != null) {
                val uri = data.data
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this@DayFragment.context, uri), object : CodeUtils.AnalyzeCallback {
                        override fun onAnalyzeSuccess(mBitmap: Bitmap, result: String) {
                            joinExam(result)
//                            Toast.makeText(this@DayFragment.context, "解析结果:$result", Toast.LENGTH_LONG).show()
                        }
                        override fun onAnalyzeFailed() {
                            Toast.makeText(this@DayFragment.context, "解析二维码失败", Toast.LENGTH_LONG).show()
                        }
                    })
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun joinExam(result:String){
        val list = result.split("|")
        if(list.size != 2){
            showToast("二维码解析异常,请检查二维码")
            return
        }
        p.joinExam(list[1])
    }



    var queryTime = System.currentTimeMillis() / 1000

    val calendarPickerView by lazy{
        CalendarPickerViewDialog(context,object :CalendarPickerViewDialog.Callback{
            override fun done(calendar: Calendar) {

                itbCalendar.buttonTextView.text = "${calendar.year}.${calendar.month}.${calendar.day}"

                queryTime = calendar.timeInMillis/1000

                p.dayInfo(queryTime.toInt())
            }
        })
    }

    override fun bindEvent() {
        super.bindEvent()

//        ivCardBg.onClick {
//            GymActivity.launch(this@DayFragment.context)
//        }

        itbDetail.onClick {

            if(System.currentTimeMillis() % 10 > 5){
                GymDialog.attencance(this@DayFragment.context).show()
            }else{
                GymDialog.consumption(this@DayFragment.context).show()
            }
        }

        itbCalendar.onClick {
            calendarPickerView.show(queryTime)
        }

        itbDetail.onClick {


            if(ctlLayout.currentTab == 0){
                DaySportDetailActivity.launch(this@DayFragment.context,queryTime.toInt())
            }else if(ctlLayout.currentTab == 1){
                DayBehaveDetailActivity.launch(this@DayFragment.context,queryTime.toInt())
            }else{
                DayStatusDetailActivity.launch(this@DayFragment.context,queryTime.toInt())
            }
        }
//        GymDialog.consumption(this@DayFragment.context).show()

    }



    val options by lazy{
        listOf("相册选取","相机扫描")
    }

    val imagePickerView by lazy { buildImagePicker() }


    fun buildImagePicker(): OptionsPickerView<String> {
        val pvTime = OptionsPickerBuilder(context, OnOptionsSelectListener { options1, options2, options3, v ->

            if(options1 == 0){

                getImage()

            }else{
                scan()
            }
        })

                .isDialog(true)
                .build<String>()

        val mDialog = pvTime.dialog


//
        if (mDialog != null) {


            val windowManager = mDialog.window!!.windowManager
            val display = windowManager.defaultDisplay
            val lp = mDialog.window!!.attributes
            lp.width = (display.width).toInt() //设置宽度
            mDialog.window!!.attributes = lp



            val containerParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM)

            containerParams.leftMargin = 0
            containerParams.rightMargin = 0
            pvTime.dialogContainerLayout.layoutParams = containerParams


            val contentParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            val viewGroup = pvTime.dialogContainerLayout.getChildAt(0) as ViewGroup

            viewGroup.layoutParams = contentParams


            val dialogWindow = mDialog.window
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim)//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM)//改成Bottom,底部显示
            }
        }

        pvTime.setPicker(options)

        return pvTime;
    }



    override fun getLayoutId(): Int {
        return R.layout.day_fragment
    }

    override fun newP(): PDay = PDay()


//    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
//
//        override fun getCount(): Int {
//            return mFragments.size
//        }
//
//        override fun getPageTitle(position: Int): CharSequence? {
//            return mTitles[position]
//        }
//
//        override fun getItem(position: Int): Fragment {
//            return mFragments[position]
//        }
//    }

    companion object {

        fun newInstance(): DayFragment {
            val fragment = DayFragment()
            return fragment
        }
    }
}
