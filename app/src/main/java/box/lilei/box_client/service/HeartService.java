package box.lilei.box_client.service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import box.lilei.box_client.client.view.BaseView;
import box.lilei.box_client.contants.Constants;
import box.lilei.box_client.service.view.HeartView;

public class HeartService extends Service implements  HeartView {
    public static final String LIVE_SERVICE_NAME = "";
    public static final String LIVE_SERVICE_PACKAGE_NAME = "";

    //心跳间隔
    public static final int BOX_HEART_TIME = 1000 * 60 * 5;

    private BaseView baseView;

    public void setBaseView(BaseView baseView) {
        this.baseView = baseView;
    }

    Timer timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isServiceRunning(getApplicationContext(), LIVE_SERVICE_PACKAGE_NAME + LIVE_SERVICE_NAME)) {
                    startLiveService();
                }


            }
        },5000,BOX_HEART_TIME);
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public void restartApp() {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void startAppAfterUpdate() {
        String path = Constants.DEMO_FILE_PATH
                + "/update/Box_Client.apk";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + path),
                "application/vnd.android.package-archive");
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());

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


}
