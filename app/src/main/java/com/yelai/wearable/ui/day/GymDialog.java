package com.yelai.wearable.ui.day;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yelai.wearable.R;
import com.yelai.wearable.widget.slider.DensityUtil;

/**
 * Created by hr on 18/7/17.
 */

public class GymDialog extends AlertDialog {
    private ViewGroup llRoot;
    private TextView tv_title;

    protected String title;
    private  int backgroundId;

    public static GymDialog attencance(Context context){
        return new GymDialog(context,R.drawable.day_gym_attendance,"恭喜您今日打卡成功");
    }

    public static GymDialog consumption(Context context){
        return new GymDialog(context,R.drawable.day_gym_consumption,"恭喜您今日消费成功");
    }

    public static GymDialog get(Context context){
        return new GymDialog(context,R.drawable.day_gym_get,"恭喜您领取成功");
    }



    public GymDialog(Context context, @DrawableRes int backgroundId, String title) {
        super(context);
        this.title = title;
        this.backgroundId = backgroundId;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_gym_require);
        this.initView();
        this.initEvents();


    }
    private void initView(){
        llRoot =  findViewById(R.id.layout_root);
        tv_title = (TextView)findViewById(R.id.tv_title);
        android.view.WindowManager.LayoutParams params = this.getWindow()
                .getAttributes();

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        if (DensityUtil.WITH == 0) {
            params.width = DensityUtil.dip2px(300);
        } else {
            params.width = DensityUtil.WITH - 200;
        }

        params.y = -(int)((DensityUtil.HEIGHT - DensityUtil.dip2px(247)) * 0.1);

        tv_title.setText(this.title);
        llRoot.setBackgroundResource(this.backgroundId);

    }

    private void initEvents(){

        llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }







}
