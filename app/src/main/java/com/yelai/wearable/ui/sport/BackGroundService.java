package com.yelai.wearable.ui.sport;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.LocaleList;

import com.yelai.wearable.R;
import com.yelai.wearable.entity.LocalLog;
import com.yelai.wearable.step.utils.DbUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BackGroundService extends Service {

    public interface Callback{
        public void call();
    }
 
    Notification notification;
    private Context mContext;
    private static Thread uploadGpsThread;
    private boolean isrun = true;
    private MediaPlayer bgmediaPlayer;

    private Callback callback;

    public void registerCallback(Callback callback){
        this.callback = callback;
    }

    public BackGroundService() {
    }


    private String notificationId = "backgroundservice.debug";
    private String notificationName = "backgroundservice.debug.name";

    private boolean isAndroid26(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("---->BackGroundService onStartCommand");

        mContext = this;
 
        Intent notificationIntent = new Intent(this, SportRunWithMapActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
        notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //1.通知栏占用，不清楚的看官网或者音乐类APP的效果

        Notification.Builder builder = new Notification.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setTicker(getString(R.string.app_name))
                .setContentTitle("正在跑步中")
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false);


        if(isAndroid26()){
            builder.setChannelId(notificationId);
        }


        notification = builder.build();


        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(isAndroid26()) {
            NotificationChannel channel = new NotificationChannel(notificationId,notificationName,NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
        }



        /*使用startForeground,如果id为0，那么notification将不会显示*/
        startForeground(100, notification);
 
        //2.开启线程（或者需要定时操作的事情）
        if(uploadGpsThread == null){
            uploadGpsThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //这里用死循环就是模拟一直执行的操作
                    while (isrun){
                        
                        //你需要执行的任务
//                        doSomething();
                        if(callback != null){
                            callback.call();
                        }
 
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            uploadGpsThread.start();
        }
 
        //3.最关键的神来之笔，也是最投机的动作，没办法要骗过CPU
        //这就是播放音乐类APP不被杀的做法，自己找个无声MP3放进来循环播放
        if(bgmediaPlayer == null){
            bgmediaPlayer = MediaPlayer.create(this,R.raw.haha);
            bgmediaPlayer.setLooping(true);
            bgmediaPlayer.start();
        }
 
        return START_STICKY;
    }

//    public void doSomething(){
//
//    }

    private MyBinder binder = new MyBinder();

    public class MyBinder extends Binder {

        /**
         * 获取当前service对象
         *
         * @return StepService
         */
        public BackGroundService getService() {
            return BackGroundService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
 
    @Override
    public void onDestroy() {
        System.out.println("---->BackGroundService onDestory");

        LocalLog log = new LocalLog();
        log.set_id(System.currentTimeMillis());
        log.setTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        log.setMessage("BackGroundService onDestory");
        DbUtils.createDb(this, LocalLog.class);
        DbUtils.insert(log);


        isrun = false;
        uploadGpsThread = null;
        stopForeground(true);
        callback = null;
        bgmediaPlayer.release();
        stopSelf();
        super.onDestroy();
    }
}