package com.yelai.wearable.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import com.yelai.wearable.R
import com.yelai.wearable.load
import com.yelai.wearable.model.Course
import com.yelai.wearable.toYyyyMMddWithSlash
import com.yelai.wearable.ui.course.CourseDetailActivity
import kotlinx.android.synthetic.main.course_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by wanglei on 2016/12/10.
 */

open class MyCourseAdapter(context: Context) : SimpleRecAdapter<Course, MyCourseAdapter.ViewHolder>(context) {

    override fun newViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun getLayoutId(): Int {
        return R.layout.course_item
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.setData(item)
        holder.itemView.setOnClickListener {

            CourseDetailActivity.launch(context,item)

        }

    }

   open fun sign(data:Course){

    }

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(data:Course) {
//            val options = ILoader.Options(XDroidConf.IL_LOADING_RES, XDroidConf.IL_ERROR_RES)
//            options.scaleType = ImageView.ScaleType.FIT_XY
//            ILFactory.getLoader().loadResource(itemView.ivBackground, course.background, options)
//            ILFactory.getLoader().loadResource(itemView.ivTag, course.type,  options)

            try {
                itemView.tvCourseName.text = data.title
                itemView.tvCourseTeacher.text = "开课时间: " + data.time.toLong().toYyyyMMddWithSlash()
                itemView.tvCourseTime.text = "课程数量: " + data.times.toString() + "节课"
            }catch (e:Exception){
                e.printStackTrace()
            }



            itemView.ivTag.visibility = View.VISIBLE

//                1：教学课 2：训练课 3：兴趣课
            when(data.type){
                "1"->{
                    itemView.ivTag.setImageResource(R.drawable.course_icon_item_teach)
                    itemView.ivBackground.load(R.drawable.course_item_background_banner1)
                }

                "2"->{
                    itemView.ivTag.setImageResource(R.drawable.course_icon_item_training)
                    itemView.ivBackground.load(R.drawable.course_item_background_banner2)

                }

                "3"->{
                    itemView.ivTag.setImageResource(R.drawable.course_icon_item_interest)
                    itemView.ivBackground.load(R.drawable.course_item_background_banner3)
                }

                else->{
                    itemView.ivTag.visibility = View.GONE
                }
            }




//            itemView.btnDone.visibility = if(data.isSign == 0){View.VISIBLE}else{View.GONE}

            itemView.btnDone.visibility = View.GONE


            itemView.btnDone.onClick {

                this@MyCourseAdapter.sign(data)
            }



        }

    }
}
