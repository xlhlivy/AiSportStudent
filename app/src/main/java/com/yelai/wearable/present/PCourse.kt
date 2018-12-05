package com.yelai.wearable.present

import cn.droidlover.xdroidmvp.mvp.XPresent
import cn.droidlover.xdroidmvp.net.ApiSubscriber
import cn.droidlover.xdroidmvp.net.NetError
import cn.droidlover.xdroidmvp.net.XApi
import com.yelai.wearable.AppData
import com.yelai.wearable.contract.CourseContract
import com.yelai.wearable.model.Course
import com.yelai.wearable.model.MapDataResult
import com.yelai.wearable.model.Result
import com.yelai.wearable.net.Api
import com.yelai.wearable.net.CourseService
import com.yelai.wearable.transfer

/**
 * Created by wanglei on 2016/12/31.
 * type: 1：教学课 2：训练课 3：兴趣课
 */



class PCourse : XPresent<CourseContract.View>(), CourseContract.Presenter {
    override fun gymInfo(gymId:String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .gymInfo(mapOf("member_id" to AppData.user!!.memberId,"gym_id" to gymId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.GymInfo, it.data)
                }
    }


    override fun imageUpload(photo: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .imageUpload(mapOf("member_id" to AppData.user!!.memberId,"data_img" to photo))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.ImageUpload, it.data)
                }
    }

    override fun cardList() {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .cardList(mapOf("member_id" to AppData.user!!.memberId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.CardList, it.data)
                }
    }

    override fun punchClock(gymId:String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .punchClock(mapOf("member_id" to AppData.user!!.memberId,"gym_id" to gymId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.PunchClock, it.data)
                }
    }

    override fun useCard(cardId: String, status: Int) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .useCard(mapOf("member_id" to AppData.user!!.memberId,"card_id" to cardId,"status" to status))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.UseCard, it.data)
                }
    }

    override fun feedback(text: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .feedback(mapOf("member_id" to AppData.user!!.memberId,"message" to text))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.Feedback, "反馈成功")
                }
    }


    override fun signOut(memberId: String, lessonId: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .sign(mapOf("member_id" to memberId,"lesson_id" to lessonId))
                .transfer(v)
                .subscribe {
                    v.hideIndicator()
                    v.success(CourseContract.Success.SignOut,"退出课程成功")
                }
    }

    override fun publishMessage(lessonId: String, memberId: String, message: String) {
        Api.getService<CourseService>(CourseService::class.java)
                .publishMessage(mapOf("lesson_id" to lessonId,"member_id" to memberId,"message" to message))
                .transfer(v)
                .subscribe({
                    v.hideIndicator()
                    v.success(CourseContract.Success.PublishMessage,"发布消息成功")
                })
    }

    override fun messageList(lessonId: String, page: Int, size: Int) {
//        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .messageList(mapOf("lesson_id" to lessonId,"page" to page,"size" to size))
                .transfer(v)
                .subscribe({
                    v.hideIndicator()
                    v.success(CourseContract.Success.MessageList,it.data)
                })
    }

    override fun courseDetail(lessonId: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .courseDetail(mapOf("lesson_id" to lessonId))
                .transfer(v)
                .subscribe({
                    v.hideIndicator()
                    v.success(CourseContract.Success.CourseDetail,it.data)
                })
    }

    override fun myCourseList() {
        Api.getService<CourseService>(CourseService::class.java)
                .courseList(mapOf("member_id" to AppData.user!!.memberId))
                .compose(XApi.getApiTransformer<Result<List<Course>>>())
                .compose(XApi.getScheduler<Result<List<Course>>>())
                .compose(v.bindToLifecycle<Result<List<Course>>>())
                .subscribe(object : ApiSubscriber<Result<List<Course>>>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(result: Result<List<Course>>) {
                        v.hideIndicator()
                        v.success(CourseContract.Success.MyCourseList,result.data)
                    }
                })
    }


    override fun interestCourseList() {
        Api.getService<CourseService>(CourseService::class.java)
                .courseList(mapOf("type" to 3,"member_id" to AppData.user!!.memberId))
                .compose(XApi.getApiTransformer<Result<List<Course>>>())
                .compose(XApi.getScheduler<Result<List<Course>>>())
                .compose(v.bindToLifecycle<Result<List<Course>>>())
                .subscribe(object : ApiSubscriber<Result<List<Course>>>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(result: Result<List<Course>>) {
                        v.hideIndicator()
                        v.success(CourseContract.Success.InterestCourseList,result.data)
                    }
                })
    }

    override fun trainingCourseList() {
//        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .courseList(mapOf("type" to 2,"member_id" to AppData.user!!.memberId))
                .compose(XApi.getApiTransformer<Result<List<Course>>>())
                .compose(XApi.getScheduler<Result<List<Course>>>())
                .compose(v.bindToLifecycle<Result<List<Course>>>())
                .subscribe(object : ApiSubscriber<Result<List<Course>>>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(result: Result<List<Course>>) {
                        v.hideIndicator()
                        v.success(CourseContract.Success.TrainingCourseList,result.data)
                    }
                })
    }

    override fun sign(memberId: String, lessonId: String) {
        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .sign(mapOf("member_id" to memberId,"lesson_id" to lessonId))
                .compose(XApi.getApiTransformer<MapDataResult>())
                .compose(XApi.getScheduler<MapDataResult>())
                .compose(v.bindToLifecycle<MapDataResult>())
                .subscribe(object : ApiSubscriber<MapDataResult>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(result: MapDataResult) {
                        v.hideIndicator()
                        v.success(CourseContract.Success.Sign,"报名成功")
                    }
                })
    }

    override fun teacherCourseList(teacherId:String) {
//        v.showIndicator()
        Api.getService<CourseService>(CourseService::class.java)
                .courseList(mapOf("teacher_id" to teacherId,"member_id" to AppData.user!!.memberId))
                .compose(XApi.getApiTransformer<Result<List<Course>>>())
                .compose(XApi.getScheduler<Result<List<Course>>>())
                .compose(v.bindToLifecycle<Result<List<Course>>>())
                .subscribe(object : ApiSubscriber<Result<List<Course>>>() {
                    override fun onFail(error: NetError) {
                        v.hideIndicator()
                        v.showError(error.message!!)
                    }

                    override fun onNext(result: Result<List<Course>>) {
                        v.hideIndicator()
                        v.success(CourseContract.Success.TeacherCourseList,result.data)
                    }
                })
    }


}
