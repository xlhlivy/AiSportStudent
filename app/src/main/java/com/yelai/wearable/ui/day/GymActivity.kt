package com.yelai.wearable.ui.day

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.router.Router
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bumptech.glide.Glide
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.yelai.wearable.*
import com.yelai.wearable.adapter.BaseAdapter
import com.yelai.wearable.adapter.ViewHolder
import com.yelai.wearable.base.BaseListActivity
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.model.Card
import com.yelai.wearable.model.Gym
import com.yelai.wearable.model.Message
import com.yelai.wearable.model.Page
import com.yelai.wearable.present.PCourse
import com.yelai.wearable.utils.ImageUtil
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.day_gym_activity.*
import kotlinx.android.synthetic.main.day_gym_item.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class GymActivity : BaseListActivity<Card,CourseContract.Presenter>(),CourseContract.View {


    override fun success(type: CourseContract.Success, data: Any) {
        if(type == CourseContract.Success.GymInfo){

            data as Gym
            val list = data.list
            list(Page(list))


            tvAttendance.text = "累计打卡次数${data.count}次"
            tvAttendance.tag = data.count.toString()
            btnAttendance.visibility = if(data.isClock == "0"){View.VISIBLE}else{View.GONE}




        }else if(type == CourseContract.Success.PunchClock){

            GymDialog.attencance(context).show()

//            showToast("打开成功")
            btnAttendance.visibility = View.GONE

            try{
                tvAttendance.text = "累计打卡次数${tvAttendance.tag.toString().toInt() + 1}次"
            }catch (e:Exception){}

        }else if(type == CourseContract.Success.UseCard){

            if(consumeCard){
                consumeCard = false
                GymDialog.consumption(context).show()
            }

            AppData.backWithData(GymActivity::class.java,true)

            contentLayout.recyclerView.refreshData()
        }

    }


    override fun initAdapter(): SimpleRecAdapter<Card, ViewHolder<Card>> = Adapter(context)

    override fun onRefresh() {
        if(gymId.isNotEmpty()){
            p.gymInfo(gymId)
        }else{
            contentLayout.refreshState(false)
        }
    }

    override fun onLoadMore(page: Int) {}

    override fun initData(savedInstanceState: Bundle?) {

        super.initData(savedInstanceState)

        useCard =  intent.extras.getSerializable("useCard") as Card?

        contentLayout.recyclerView.refreshData()

        btnAttendance.onClick {
            method = 2
            imagePickerView.show()
//            p.punchClock(gymId)
        }
//        //设置banner样式
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)
//        //设置图片加载器
//        banner.setImageLoader(GlideImageLoader())
//        //设置图片集合
////        banner.setImages(images)
//        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage)
//        //设置标题集合（当banner样式有显示title时）
////        banner.setBannerTitles(titles)
//        //设置自动轮播，默认为true
//        banner.isAutoPlay(true)
//        //设置轮播时间
//        banner.setDelayTime(1500)
//        //设置指示器位置（当banner模式中有指示器时）
//        banner.setIndicatorGravity(BannerConfig.CENTER)
//        //banner设置方法全部调用完毕时最后调用
//        banner.start()



    }

    override fun onResume() {
        super.onResume()

        if(useCard != null){
            scan()
        }
    }

    override fun bindEvent() {
        super.bindEvent()
        ivLeft.onClick { AppManager.finishCurrentActivity() }
    }

    internal inner  class  GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context, path: Any, imageView: ImageView) {
            Glide.with(context).load(path).into(imageView);
        }

//        override fun createImageView(context: Context?): ImageView {
////            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context)
//            return super.createImageView(context)
//        }

    }


    inner class Adapter(context: Context) : BaseAdapter<Card>(context) {
        override fun getLayoutId(): Int = R.layout.day_gym_item


        override fun onBindViewHolder(holder: ViewHolder<Card>, position: Int) {

            val item = data[position]

            holder.setData(item)
        }

        override fun setData(itemView: View, data: Card) {

            itemView.tvCardName.text = data.name

            itemView.tvCardDescription.text = data.desc

            itemView.tvCardTake.text = when(data.status){
                "0"->"领取"
                "1"->"去使用"
                else->"已使用"
            }

            itemView.tvCardTake.visibility = View.VISIBLE
            if(data.status == "2"){
                itemView.tvCardTake.visibility = View.GONE
            }

            itemView.ivCardBg.load(R.drawable.day_card_purple)

            itemView.tvCardTake.onClick {

                if(data.status == "0"){
                    p.useCard(data.cardId,1)
                }else{
                    consumeCard = true
                    useCard = data
                    method = 1
                    imagePickerView.show()
                }
            }
        }

    }

    var consumeCard = false


    val gymId by lazy{intent.extras.getString("gymId")}



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

            val intent = Intent(this@GymActivity.context, ScanActivity::class.java)
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

                    scanResult(result)

//                    Toast.makeText(context, "解析结果:" + result!!, Toast.LENGTH_LONG).show()
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(context, "解析二维码失败", Toast.LENGTH_LONG).show()
                }
            }
        } else if (requestCode == 111) run {
            if (data != null) {
                val uri = data.data
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this@GymActivity.context, uri), object : CodeUtils.AnalyzeCallback {
                        override fun onAnalyzeSuccess(mBitmap: Bitmap, result: String) {
                            scanResult(result)
//                            Toast.makeText(this@DayFragment.context, "解析结果:$result", Toast.LENGTH_LONG).show()
                        }
                        override fun onAnalyzeFailed() {
                            Toast.makeText(this@GymActivity.context, "解析二维码失败", Toast.LENGTH_LONG).show()
                        }
                    })
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
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



    var method = 1;//1 使用，2 打卡

    fun scanResult(result:String){



        val list = result.split("|")
        if(list.size != 2){
            showToast("二维码解析异常,请检查二维码")
            return
        }

        val gymId = list[0];
        val type = list[1];

        if(method == 1){//使用卡券
            if(useCard != null && useCard!!.gymId == gymId){
                p.useCard(useCard!!.cardId,2)
            }

            useCard = null

        }else{//打卡
            p.punchClock(gymId)

        }

    }


    var useCard:Card? = null


    override fun getLayoutId(): Int = R.layout.day_gym_activity


    override fun newP(): PCourse = PCourse()

    companion object {
        fun launch(activity: Context,gymId:String,useCard:Card? = null) {
            Router.newIntent(activity as Activity)
                    .putString("gymId",gymId)
                    .putSerializable("useCard",useCard)
                    .to(GymActivity::class.java).launch()
                    }
        }

}
