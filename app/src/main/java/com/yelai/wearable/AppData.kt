package com.yelai.wearable

import android.content.Context
import cn.droidlover.xdroidmvp.cache.SharedPref
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yelai.wearable.model.*
import org.json.JSONObject
import java.lang.reflect.Type
import kotlin.properties.Delegates

class AppData{

    companion object {
        val gson = Gson()


        var isFirstTime = false
            get() {

                val value = SharedPref.getInstance(App.context).getBoolean("isFirstTime",true)

                SharedPref.getInstance(App.context).putBoolean("isFirstTime",false)

                return value
            }
        private set;

        var backData:Any? = null
        var fromPage :Class<*>? = null
        var pushData:Any? = null

        fun clear(){
            backData = null
            fromPage = null
        }

        fun isBackFromPageWithDataAndCleanData(clazz: Class<*>):Boolean{
            val back = (AppData.backData != null && AppData.fromPage != null && AppData.fromPage == clazz)
            clear()
            return back
        }

        fun isBackFromPageWithData(clazz: Class<*>):Boolean{
            return (AppData.backData != null && AppData.fromPage != null && AppData.fromPage == clazz)
        }

        fun backWithData(page:Class<*>,data:Any = true){
            backData = data
            fromPage = page
        }


        var user:UserResult.User? = null
            get() {
                if(field == null){
                    val value = SharedPref.getInstance(App.context).getString("user",null) ?: return null
                    field = gson.fromJson(value,UserResult.User::class.java)
                }
                return field
            }
            set(value){
                field = value
                SharedPref.getInstance(App.context).putString("user", gson.toJson(value))
            }


        var userInfo:UserInfoResult.UserInfo? = null
            get() {
                if(field == null){
                    val value = SharedPref.getInstance(App.context).getString("user_info",null) ?: return null
                    field = gson.fromJson(value,UserInfoResult.UserInfo::class.java)
                }
                return field
            }
            set(value){
                field = value
                SharedPref.getInstance(App.context).putString("user_info", gson.toJson(value))
            }


        fun isLogin():Boolean{
            return SharedPref.getInstance(App.context).getBoolean("is_login",false)
        }

        fun loginSuccess(){
            SharedPref.getInstance(App.context).putBoolean("is_login",true)
        }



        var sportTypeList:MutableList<SportType> = ArrayList()
            get() {
                if(field.isEmpty()){
                    val value = SharedPref.getInstance(App.context).getString("sport_type_list",null) ?: return field
                    field.addAll(gson.fromJson(value,object : TypeToken<List<SportType>>() {}.type))
                }
                return field
            }
            set(value){
                field = value
                SharedPref.getInstance(App.context).putString("sport_type_list", gson.toJson(value))
            }


        var sportType:SportType? = null
            get() {
                if(field == null){
                    val value = SharedPref.getInstance(App.context).getString("sport_type",null) ?: return null
                    field = gson.fromJson(value,SportType::class.java)
                }
                return field
            }
            set(value){
                field = value
                SharedPref.getInstance(App.context).putString("sport_type", gson.toJson(value))
            }

        var sportStatistics:Sport? = null
            get() {
                if(field == null){
                    val value = SharedPref.getInstance(App.context).getString("sport_statistics",null) ?: return null
                    field = gson.fromJson(value,Sport::class.java)
                }
                return field
            }
            set(value){
                field = value
                SharedPref.getInstance(App.context).putString("sport_statistics", gson.toJson(value))
            }


        var myCourseList:MutableList<Course> = ArrayList()
            get() {
                if(field.isEmpty()){
                    val value = SharedPref.getInstance(App.context).getString("my_course_list",null) ?: return field
                    field.addAll(gson.fromJson(value,object : TypeToken<List<Course>>() {}.type))
                }
                return field
            }
            set(value){
                field = value
                SharedPref.getInstance(App.context).putString("my_course_list", gson.toJson(value))
            }

    }

}
