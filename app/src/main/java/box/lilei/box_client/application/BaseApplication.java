package box.lilei.box_client.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.avm.serialport_142.MainHandler;
import com.squareup.leakcanary.LeakCanary;

import java.io.File;

import box.lilei.box_client.box.BoxSetting;
import box.lilei.box_client.contants.Constants;
import box.lilei.box_client.db.DaoMaster;
import box.lilei.box_client.db.DaoSession;
import box.lilei.box_client.db.MySQLiteOpenHelper;
import box.lilei.box_client.db.biz.AdBeanService;
import box.lilei.box_client.util.ExceptionHandler;
import box.lilei.box_client.util.FileUtils;

/**
 * Created by lilei on 2017/10/30.
 */

public class BaseApplication extends Application {

    private static BaseApplication mInstance;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        if(mInstance == null)
            mInstance = this;

        //初始化SDK配置文件
        initSDKiniFile();
        //创建资源文件夹
        if (!FileUtils.exist(Constants.DEMO_FILE_PATH)) {
            FileUtils.creatSDDir("Box_client");
        }

//        initBoxCheck();

        LeakCanary.install(this);

        //初始化异常捕捉
        ExceptionHandler exceptionHandler = ExceptionHandler.getInstance();
        exceptionHandler.init(this);
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
                + "config.ini");
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

    public void initBoxCheck(){
        int loadResult = MainHandler.load(this);
        if (loadResult == MainHandler.ERROR_NO_SDCARD) {
            Toast.makeText(mInstance, "系统没有内存卡", Toast.LENGTH_SHORT).show();
        } else if (loadResult == MainHandler.ERROR_EMPTY_DATA) {
            Toast.makeText(mInstance, "串口信息没有配置或者读取失败", Toast.LENGTH_SHORT).show();
        } else if (loadResult == MainHandler.ERROR_NET_NOT_AVAILABLE) {
            Toast.makeText(mInstance, "系统没有连接网络", Toast.LENGTH_SHORT).show();
        } else if (loadResult == MainHandler.LOAD_DATA_SUCCESS) {
            Toast.makeText(mInstance, "加载成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mInstance, "其他错误", Toast.LENGTH_SHORT).show();
        }
        if (loadResult!=MainHandler.LOAD_DATA_SUCCESS){
            Toast.makeText(mInstance, "2秒后退出程序", Toast.LENGTH_SHORT).show();
        }
    }

}
