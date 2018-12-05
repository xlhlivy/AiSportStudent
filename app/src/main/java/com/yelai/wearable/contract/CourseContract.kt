package com.yelai.wearable.contract

import cn.droidlover.xdroidmvp.mvp.IPresent
import cn.droidlover.xdroidmvp.mvp.IView
import com.yelai.wearable.base.ViewPage
import com.yelai.wearable.model.*
import com.yelai.wearable.ui.login.LoginActivity
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

/**
 * Created by xuhao on 2017/11/30.
 * desc: 资讯和信息
 */
interface CourseContract {

    enum class Success{
        TeacherCourseList,
        Sign,
        SignOut,
        InterestCourseList,
        TrainingCourseList,
        MyCourseList,
        CourseDetail,
        MessageList,
        PublishMessage,
        Feedback,
        CardList,
        PunchClock,
        UseCard,
        ImageUpload,
        GymInfo,
    }

    interface View : HRIView<Presenter> {

        fun success(type:Success,data:Any){}//除分页以外的通用返回

//        fun list(page:Page<List<Message>>){}//分页

    }

    interface Presenter : IPresent<View> {

        /**
         * 获取运动的类型
         * @return
         */
        fun teacherCourseList(teacherId:String)

        fun feedback(message:String)

        fun sign(memberId:String,lessonId:String)

        fun signOut(memberId:String,lessonId:String)

        fun interestCourseList()

        fun trainingCourseList()

        fun myCourseList()

        fun courseDetail(lessonId:String)

        fun messageList(lessonId:String,page:Int,size:Int = 10)

        fun publishMessage(lessonId:String,memberId:String,message:String)

        fun imageUpload(photo:String)

        //卡券列表
        fun cardList()

        fun gymInfo(gymId:String)

        //打卡
        fun punchClock(gymId:String)

        //1 待使用 2已使用
        fun useCard(cardId:String,status:Int);


    }
}