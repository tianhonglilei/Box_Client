package box.lilei.box_client.db.biz;

import android.content.Context;

import java.util.List;

import box.lilei.box_client.application.BaseApplication;
import box.lilei.box_client.db.AdBean;
import box.lilei.box_client.db.AdBeanDao;
import box.lilei.box_client.db.DaoSession;

/**
 * Created by lilei on 2017/10/30.
 */

public class AdBeanService {

    private static final String TAG = AdBeanService.class.getSimpleName();
    private static AdBeanService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private AdBeanDao adBeanDao;


    public static AdBeanService getInstance(Context context) {
        if (instance == null) {
            instance = new AdBeanService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.adBeanDao = instance.mDaoSession.getAdBeanDao();
        }
        return instance;
    }

    public void insertAdBeanToDb(List<AdBean> adInfoList) {

    }


    public AdBean queryAdByAdNo(int adNo) {
        AdBean adBean = new AdBean();

        return adBean;
    }

    public void saveAdBean(List<AdBean> adBeanList) {
        for (AdBean bean :
                adBeanList) {

        }


    }


}
