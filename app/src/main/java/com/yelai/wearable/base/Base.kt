package com.yelai.wearable.base

import android.content.Context
import android.view.View

import java.io.Serializable

import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import cn.droidlover.xdroidmvp.mvp.IPresent
import com.yelai.wearable.adapter.ViewHolder

/**
 * Created by hr on 18/11/7.
 */

abstract class Base<D : Serializable, P : IPresent<*>> : BaseFragment<P>() {


    internal inner class Adapter(context: Context) : SimpleRecAdapter<D, ViewHolder<D>>(context) {

        override fun newViewHolder(itemView: View): ViewHolder<D> {
            return object : ViewHolder<D>(itemView) {
                override fun setData(data: D) {

                }
            }
        }

        override fun getLayoutId(): Int {
            return 0
        }

        override fun onBindViewHolder(holder: ViewHolder<D>, position: Int) {

        }
    }


}
