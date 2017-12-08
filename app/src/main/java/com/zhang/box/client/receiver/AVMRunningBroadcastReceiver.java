package com.zhang.box.client.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhang.box.box.BoxAction;
import com.zhang.box.client.view.activity.ADBannerActivity;

import java.util.Timer;
import java.util.TimerTask;

public class AVMRunningBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION = "com.avm.serialport.NOTICE_AVM_DISCONNECT";


    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            disconnect(context);
        }
    }

    private void disconnect(final Context context) {
        if (!BoxAction.getAVMRunning()) {
            if (isActivityTop(ADBannerActivity.class, context)) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        restartApp(context);
                    }
                }, 2000);
            } else {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        disconnect(context);
                    }
                }, 2000);
            }
        }
    }

    /**
     * 判断某activity是否处于栈顶
     *
     * @return true在栈顶 false不在栈顶
     */
    private boolean isActivityTop(Class cls, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(cls.getName());
    }


    public void restartApp(Context context) {
        Intent i = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
    }

}
