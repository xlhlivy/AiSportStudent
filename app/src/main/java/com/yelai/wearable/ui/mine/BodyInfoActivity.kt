package com.yelai.wearable.ui.mine

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.SpannableStringBuilder
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleToolbar
import cn.droidlover.xdroidmvp.router.Router
import com.jakewharton.rxbinding2.widget.RxTextView
import com.yelai.wearable.AppManager
import com.yelai.wearable.R
import com.yelai.wearable.base.BaseActivity
import com.yelai.wearable.model.Physique
import com.yelai.wearable.net.PPhysique
import com.yelai.wearable.net.PhysiqueContract
import com.yelai.wearable.showToast
import io.reactivex.Observable
import kotlinx.android.synthetic.main.mine_body_info_activity.*
import org.jetbrains.anko.textColorResource


/**
 * Created by xuhao on 2017/12/1.
 * desc:搜索功能
 */

class BodyInfoActivity : BaseActivity<PhysiqueContract.Presenter>(),PhysiqueContract.View{


    override fun success(type: PhysiqueContract.Success, data: Any) {

        if(PhysiqueContract.Success.SetShape == type){
            showToast("录入成功")
            AppManager.finishCurrentActivity()
        }else if(PhysiqueContract.Success.GetShape == type){

            data as Physique

            etWeight.isEnabled = false
            etHeight.isEnabled = false
            etBust.isEnabled = false
            etWaistline.isEnabled = false
            etHipline.isEnabled = false
            etBigThigh.isEnabled = false
            etLittleThigh.isEnabled = false

            etWeight.text = SpannableStringBuilder(data.weight)
            etHeight.text = SpannableStringBuilder(data.height)
            etBust.text = SpannableStringBuilder(data.bust)
            etWaistline.text = SpannableStringBuilder(data.waistline)
            etHipline.text = SpannableStringBuilder(data.upperArm)
            etBigThigh.text = SpannableStringBuilder(data.thighArm)
            etLittleThigh.text = SpannableStringBuilder(data.crusArm)
        }
    }

    val physique = Physique()

    override fun initData(savedInstanceState: Bundle?) {

        mToolbar.setMiddleText("体型录入", ContextCompat.getColor(this, R.color.text_black_color))

        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.page_bg_color))
        showToolbar()

        if(isEdit){
            mToolbar.setRightText("保存")
            mToolbar.tvRight.textSize = 12f
            mToolbar.tvRight.textColorResource = R.color.text_black_color
            RxTextView.textChanges(etWeight).subscribe { physique.weight = it.toString() }
            RxTextView.textChanges(etHeight).subscribe { physique.height = it.toString()}
            RxTextView.textChanges(etBust).subscribe { physique.bust = it.toString() }
            RxTextView.textChanges(etWaistline).subscribe { physique.waistline = it.toString() }
            RxTextView.textChanges(etHipline).subscribe { physique.upperArm = it.toString() }//上臀围  大退
            RxTextView.textChanges(etBigThigh).subscribe { physique.thighArm = it.toString()}
            RxTextView.textChanges(etLittleThigh).subscribe { physique.crusArm = it.toString()}

        }else{
            p.getShape()
        }

    }

    override fun onToolbarActionsClick(which: Int, view: View?) {
        super.onToolbarActionsClick(which, view)
        if(which == SimpleToolbar.RIGHT_TEXT){

            if(physique.weight.isEmpty()){
                showToast("请填写体重")
                return
            }


            if(physique.height.isEmpty()){
                showToast("请填写升高")
                return
            }

            if(physique.bust.isEmpty()){
                showToast("请填写胸围")
                return
            }

            if(physique.waistline.isEmpty()){
                showToast("请填写腰围")
                return
            }

            if(physique.upperArm.isEmpty()){
                showToast("请填写上臀围")
                return
            }

            if(physique.thighArm.isEmpty()){
                showToast("请填写大腿围")
                return
            }


            if(physique.crusArm.isEmpty()){
                showToast("请填写小腿围")
                return
            }
            p.setShape(physique)
        }
    }


    override fun getLayoutId(): Int = R.layout.mine_body_info_activity

    override fun newP(): PPhysique = PPhysique()

    val isEdit by lazy{intent.extras.getBoolean("isEdit",true)}

    companion object {

        fun launch(activity: Context,edit:Boolean = true) {
            Router.newIntent(activity as Activity)
                    .to(BodyInfoActivity::class.java)
                    .putBoolean("isEdit",edit)
                    .launch()
        }
    }


}
