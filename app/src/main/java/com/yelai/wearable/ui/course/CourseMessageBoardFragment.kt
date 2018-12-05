package com.yelai.wearable.ui.course

import android.content.Context
import android.view.View
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter
import com.yelai.wearable.AppData
import com.yelai.wearable.R
import com.yelai.wearable.adapter.BaseAdapter
import com.yelai.wearable.adapter.ViewHolder
import com.yelai.wearable.base.BaseListFragment
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.model.Message
import com.yelai.wearable.model.Page
import com.yelai.wearable.present.PCourse
import com.yelai.wearable.showToast
import kotlinx.android.synthetic.main.course_fragment_message_board.*
import kotlinx.android.synthetic.main.course_item_message.view.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hr on 18/9/16.
 */

class CourseMessageBoardFragment : BaseListFragment<Message,CourseContract.Presenter>(),CourseContract.View {

    override fun success(type: CourseContract.Success, data: Any) {

        if(type == CourseContract.Success.PublishMessage){
            showToast(data as String)
            contentLayout.recyclerView.refreshData()
        }else if(type == CourseContract.Success.MessageList){
            list(data as Page<List<Message>>)
        }

    }
//
//    override fun list(pager: Page<List<Message>>) {
//        super<BaseListFragment>.list(pager)
//    }

    override fun initAdapter(): SimpleRecAdapter<Message, ViewHolder<Message>> {
        return Adapter(context)
    }

    private var lessonId:String? = null

    override fun onRefresh() {
        p.messageList(lessonId!!,1)
    }

    override fun onLoadMore(page: Int) {
        p.messageList(lessonId!!,1)
    }

    private val commentDialogFragment = CommentDialogFragment()

    override fun bindEvent() {
        super.bindEvent()

        commentDialogFragment.setCallback(object:CommentDialogFragment.DialogFragmentDataCallback{
            override fun getCommentText(): String {
                return ""
            }

            override fun setCommentText(commentTextTemp: String) {
                //发送
                p.publishMessage(lessonId!!,AppData.user!!.memberId,commentTextTemp)
            }

        })

        btnWriteMessage.onClick {
            commentDialogFragment.show(childFragmentManager as android.support.v4.app.FragmentManager, "CommentDialogFragment")
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.course_fragment_message_board
    }

    override fun newP(): PCourse = PCourse()


    companion object {

        fun newInstance(lessonId:String): CourseMessageBoardFragment {
            val fragment = CourseMessageBoardFragment()
            fragment.lessonId = lessonId
            return fragment
        }
    }

    inner class Adapter(context:Context):BaseAdapter<Message>(context){

        override fun getLayoutId(): Int = R.layout.course_item_message

        override fun onBindViewHolder(holder: ViewHolder<Message>, position: Int) {

            val item = data[position]

            holder.setData(item)
        }


        override fun setData(itemView: View, data: Message) {

//            itemView.ivHeader
            // val options = ILoader.Options(XDroidConf.IL_LOADING_RES, XDroidConf.IL_ERROR_RES)
//            options.scaleType = ImageView.ScaleType.FIT_XY
//            ILFactory.getLoader().loadResource(itemView.ivBackground, course.background, options)

            itemView.tvUserName.text = data.trueName
            itemView.tvTime.text = data.time
            itemView.tvContent.text = data.message
        }
    }


}
