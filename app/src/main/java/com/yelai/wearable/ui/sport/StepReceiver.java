package com.yelai.wearable.ui.sport;

import android.content.Context;
import android.content.Intent;

import com.today.step.lib.BaseClickBroadcast;
import com.yelai.wearable.App;
import com.yelai.wearable.ui.HomeActivity;

public class StepReceiver extends BaseClickBroadcast {

    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        App tsApplication = (App) context.getApplicationContext();
        if (!tsApplication.isForeground()) {
            Intent mainIntent = new Intent(context, HomeActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainIntent);
        } else {

        }
    }
}
