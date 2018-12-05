package com.yelai.wearable.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yelai.wearable.R;
import com.yelai.wearable.widget.slider.DensityUtil;

/**
 * Created by hr on 18/7/17.
 */

public class PermissionRequireDialog extends AlertDialog {

    interface Callback{
        public void cancel();
    }


    private Callback callback;

    public PermissionRequireDialog setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    private TextView tv_content,tv_title;

    protected String title,content;
    protected Intent intent;

//    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

    public static PermissionRequireDialog location(Context context){

        String title = "定位服务已关闭";
        String content = "您需要打开定位权限\n才可以获取到您的位置进行考勤。";
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        return new PermissionRequireDialog(context,title,content,intent);
    }

    public static PermissionRequireDialog photo(Context context){

        String title = "拍照服务已关闭";
        String content = "您需要打开拍照权限\n才可以获取到您的照片进行考勤。";
        Uri packageURI = Uri.parse("package:" + "com.linbang.xiaoguan");
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        return new PermissionRequireDialog(context,title,content,intent);
    }

    public static PermissionRequireDialog image(Context context){

        String title = "获取相册服务已关闭";
        String content = "您需要打开相册权限\n才可以获取到您的照片。";
        Uri packageURI = Uri.parse("package:" + "com.linbang.xiaoguan");
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        return new PermissionRequireDialog(context,title,content,intent);
    }


    public static PermissionRequireDialog storage(Context context){
        String title = "读取存储服务已关闭";
        String content = "您需要打开存储权限\n才可以使用软件的更新服务。";
        Uri packageURI = Uri.parse("package:" + "com.linbang.xiaoguan");
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        return new PermissionRequireDialog(context,title,content,intent);
    }


    public PermissionRequireDialog(Context context,String title,String content,Intent intent) {
        super(context);

        this.title = title;
        this.content = content;
        this.intent = intent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_permission_require);
        this.initView();
        this.initEvents();


    }
    private void initView(){
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_title = (TextView)findViewById(R.id.tv_title);
        android.view.WindowManager.LayoutParams params = this.getWindow()
                .getAttributes();
        if (DensityUtil.WITH == 0) {
            params.width = DensityUtil.dip2px(300);
        } else {
            params.width = DensityUtil.WITH - 200;
        }

        tv_title.setText(this.title);
        tv_content.setText(this.content);

    }

    private void initEvents(){
        Button btn_ok = (Button) findViewById(R.id.button_ok);
        Button btn_cancel = (Button) findViewById(R.id.button_cancel);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getContext().startActivity(intent);
                dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(callback != null)callback.cancel();
                dismiss();
            }
        });
    }







}
