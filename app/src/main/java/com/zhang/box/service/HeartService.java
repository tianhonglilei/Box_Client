package com.zhang.box.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.zhang.box.client.view.ADBannerView;
import com.zhang.box.contants.Constants;
import com.zhang.box.service.presenter.HeartPresenter;
import com.zhang.box.service.presenter.impl.HeartPresenterImpl;
import com.zhang.box.service.view.HeartView;
import com.zhang.box.util.SharedPreferencesUtil;

public class HeartService extends Service implements HeartView {
    public static final String LIVE_SERVICE_NAME = ".RunningBoxService";
    public static final String LIVE_SERVICE_PACKAGE_NAME = "com.wh.boxservies";

    //心跳间隔
    public static final int BOX_HEART_TIME = 1000 * 60 * 5;

    Context mContext;
    ADBannerView adBannerView;
    HeartPresenter heartPresenter;

    public void setAdBannerView(ADBannerView adBannerView) {
        this.adBannerView = adBannerView;
    }

    Timer timer;
    Calendar calendar;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(mContext, "心跳开始", Toast.LENGTH_SHORT).show();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isServiceRunning(getApplicationContext(), LIVE_SERVICE_PACKAGE_NAME + LIVE_SERVICE_NAME)) {
//                    startLiveService();
                    startLiveActivity();
                }
                heartPresenter.sendHeartInfo();
                //每日重启模块
//                calendar = Calendar.getInstance();
//                int hour = calendar.get(Calendar.HOUR_OF_DAY);
//                int minuts = calendar.get(Calendar.MINUTE);
////                Log.e("HeartService", "hour:minuts" + hour + ":" + minuts);
//
//                if (hour == 2) {
//                    if (minuts >= 0 && minuts <= 4) {
//                        restartApp();
//                    }
//                }

                System.gc();

            }
        }, 5000, BOX_HEART_TIME);
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        heartPresenter = new HeartPresenterImpl(mContext, this);
    }

    @Override
    public void restartApp() {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void startAppAfterUpdate(final String path) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.parse("file://" + path),
                        "application/vnd.android.package-archive");
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }, 60000);
    }


    /**
     * 方法描述：判断某一Service是否正在运行
     *
     * @param context     上下文
     * @param serviceName Service的全路径： 包名 + service的类名
     * @return true 表示正在运行，false 表示没有运行
     */
    public boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
            if (serviceInfo.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 通过服务名称启动服务
     */
    public void startLiveService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(LIVE_SERVICE_PACKAGE_NAME, LIVE_SERVICE_PACKAGE_NAME + LIVE_SERVICE_NAME));//设置一个组件名称  同组件名来启动所需要启动Service
        startService(intent);
    }

    /**
     * 通过包名启动应用
     */
    public void startLiveActivity() {
        Intent intent = this.getPackageManager().getLaunchIntentForPackage(
                LIVE_SERVICE_PACKAGE_NAME);
        startActivity(intent);
    }


}
