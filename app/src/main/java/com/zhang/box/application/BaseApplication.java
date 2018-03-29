package com.zhang.box.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.widget.Toast;

import com.avm.serialport_142.MainHandler;
import com.squareup.leakcanary.LeakCanary;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import com.zhang.box.box.BoxSetting;
import com.zhang.box.client.pos.Demo;
import com.zhang.box.client.pos.SerialTool;
import com.zhang.box.client.view.activity.ActiveActivity;
import com.zhang.box.contants.Constants;
import com.zhang.box.db.DaoMaster;
import com.zhang.box.db.DaoSession;
import com.zhang.box.db.MySQLiteOpenHelper;
import com.zhang.box.loading.ZLoadingDialog;
import com.zhang.box.loading.Z_TYPE;
import com.zhang.box.service.HeartService;
import com.zhang.box.util.ExceptionHandler;
import com.zhang.box.util.FileUtils;

import android_serialport_api.SerialPort;

/**
 * Created by lilei on 2017/10/30.
 */

public class BaseApplication extends Application {

    private static BaseApplication mInstance;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mInstance == null) {
            mInstance = this;
        }
        activityList = new ArrayList<>();
        //初始化SDK配置文件
        initSDKiniFile();
        //初始化开机动画
//        initStartImg();
        //创建资源文件夹
        if (!FileUtils.exist(Constants.DEMO_FILE_PATH)) {
            FileUtils.creatSDDir("boxcontent");
        }

//        initBoxCheck();

//        LeakCanary.install(this);

        //初始化异常捕捉
        ExceptionHandler exceptionHandler = ExceptionHandler.getInstance();
        exceptionHandler.init(this);

        //初始化pos机
        initPos();


        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_BOX, "");

        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:友盟 app key
         * 参数3:友盟 channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
//        UMConfigure.init(this, "", "", UMConfigure.DEVICE_TYPE_BOX, "");

        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
//        UMConfigure.setLogEnabled(true);


    }

    private void initPos() {
        try {
            SerialTool.getSerialPort();
            //发送数据
            String message = "020034313030310001043030320010323031383031313831343233333230313030340001310313";
            try {
                System.out.println("正准备向" + "发送测试数据...");
                SerialTool.sendToPort(Constants.serialPort, Demo.hex2byte(message));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initStartImg() {
        String systemPath = Environment.getRootDirectory().getPath() + "/media/";
        FileUtils.assetsDataToSD(getApplicationContext(), systemPath
                , "bootanimation.zip");
    }


    private void initSDKiniFile() {
        String firstPath = Environment.getExternalStorageDirectory()
                + File.separator + "Android" + File.separator;
        String secondPath = firstPath + "data" + File.separator;
        String thirdPath = secondPath + BoxSetting.BOX_PACKAGE_NAME + File.separator;
        String fourthPath = thirdPath + "set" + File.separator;
        File dirFirstPath = new File(firstPath);
        if (!dirFirstPath.exists()) { // 目录存在返回false
            dirFirstPath.mkdirs(); // 创建一个目录
        }
        File dirSecondPath = new File(secondPath);
        if (!dirSecondPath.exists()) {
            dirSecondPath.mkdirs();
        }
        File dirThirdPath = new File(thirdPath);
        if (!dirThirdPath.exists()) {
            dirThirdPath.mkdirs();
        }
        File dirForthPath = new File(fourthPath);
        if (!dirForthPath.exists()) {
            dirForthPath.mkdirs();
        }
        FileUtils.assetsDataToSD(getApplicationContext(), fourthPath
                , "config.ini");
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context, Constants.DB_NAME, null);
//            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
//            DaoMaster.dropAllTables(daoMaster.getDatabase(),true);
//            DaoMaster.createAllTables(daoMaster.getDatabase(),true);
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public void initBoxCheck() {
        int loadResult = MainHandler.load(this);
        if (loadResult == MainHandler.ERROR_NO_SDCARD) {
            Toast.makeText(mInstance, "系统没有内存卡", Toast.LENGTH_SHORT).show();
        } else if (loadResult == MainHandler.ERROR_EMPTY_DATA) {
            Toast.makeText(mInstance, "串口信息没有配置或者读取失败", Toast.LENGTH_SHORT).show();
        } else if (loadResult == MainHandler.ERROR_NET_NOT_AVAILABLE) {
            Toast.makeText(mInstance, "系统没有连接网络", Toast.LENGTH_SHORT).show();
        } else if (loadResult == MainHandler.LOAD_DATA_SUCCESS) {
            Toast.makeText(mInstance, "加载成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mInstance, "其他错误", Toast.LENGTH_SHORT).show();
        }
    }

    private static List<Activity> activityList;

    public static void addActivityToList(Activity activity) {
        activityList.add(activity);
    }

    public static void exitAllActivity() {
        for (Activity activity :
                activityList) {
            if (activity != null) {
                if (!activity.isDestroyed()) {
                    activity.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    public static void showDialog(String text) {
        ZLoadingDialog dialog = new ZLoadingDialog(context);
        dialog.setLoadingBuilder(Z_TYPE.TEXT)
                .setLoadingColor(Color.parseColor("#ff5307"))
                .setHintText(text)
                .setHintTextSize(16) // 设置字体大小
                .setHintTextColor(Color.parseColor("#525252"))  // 设置字体颜色
                .setCanceledOnTouchOutside(false)
                .show();
    }


}
