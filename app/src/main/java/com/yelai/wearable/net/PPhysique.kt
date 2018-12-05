package com.yelai.wearable.net

import cn.droidlover.xdroidmvp.mvp.XPresent
import cn.droidlover.xdroidmvp.net.ApiSubscriber
import cn.droidlover.xdroidmvp.net.NetError
import cn.droidlover.xdroidmvp.net.XApi
import com.yelai.wearable.AppData
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.contract.DiscoveryContract
import com.yelai.wearable.contract.LoginContract
import com.yelai.wearable.contract.UserContract
import com.yelai.wearable.model.*
import com.yelai.wearable.net.Api
import com.yelai.wearable.transfer
import com.yelai.wearable.ui.login.LoginActivity

/**
 * Created by hr on 18/9/15.
 */

class PPhysique : XPresent<PhysiqueContract.View>(), PhysiqueContract.Presenter {
    override fun setShape(physique: Physique) {
        v.showIndicator()

        val data = HashMap<String,Any>()

        data.put("member_id",AppData.user!!.memberId)

        data.put("weight",physique.weight)

        data.put("height",physique.height)

        data.put("bust",physique.bust)

        data.put("waistline",physique.waistline)

        data.put("upper_arm",physique.upperArm)

        data.put("thigh_arm",physique.thighArm)

        data.put("crus_arm",physique.crusArm)


        Api.getService<PhysiqueService>(CourseService::class.java)
                .setShape(data)
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(PhysiqueContract.Success.SetShape, it.data)
                }    }

    override fun getShape() {
        v.showIndicator()
        Api.getService<PhysiqueService>(CourseService::class.java)
                .getShape(mapOf("member_id" to AppData.user!!.memberId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(PhysiqueContract.Success.GetShape, it.data)
                }    }




}
