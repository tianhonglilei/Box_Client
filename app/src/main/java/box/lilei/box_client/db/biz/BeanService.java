package box.lilei.box_client.db.biz;

import android.content.Context;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.application.BaseApplication;
import box.lilei.box_client.db.AdBeanDao;
import box.lilei.box_client.db.DaoSession;
import box.lilei.box_client.db.GoodsBean;
import box.lilei.box_client.db.GoodsBeanDao;

/**
 * Created by lilei on 2017/11/6.
 */

public class BeanService<T> {
    private static final String TAG = BeanService.class.getSimpleName();
    private static Context appContext;
    protected AbstractDaoSession mDaoSession;
    protected AbstractDao<T, Long> mDao;


    public BeanService(Context context, Class object) {
        if (appContext == null) {
            appContext = context.getApplicationContext();
        }
        mDaoSession = BaseApplication.getDaoSession(context);
        mDao = mDaoSession.getDao(object);
    }

    public T queryById(Long id) {
        return mDao.load(id);
    }

    public void saveBeanList(List<T> list) {
        mDao.insertOrReplaceInTx(list);
    }

    public void updateBean(T t) {
        mDao.update(t);
    }

}
