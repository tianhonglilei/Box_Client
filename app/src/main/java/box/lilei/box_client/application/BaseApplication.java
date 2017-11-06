package box.lilei.box_client.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;

import box.lilei.box_client.box.BoxSetting;
import box.lilei.box_client.contants.Constants;
import box.lilei.box_client.db.DaoMaster;
import box.lilei.box_client.db.DaoSession;
import box.lilei.box_client.db.biz.AdBeanService;
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
        AdBeanService adBeanService = AdBeanService.getInstance(getApplicationContext());
        
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
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
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

}
