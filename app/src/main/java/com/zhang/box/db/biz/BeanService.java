package com.zhang.box.db.biz;

import android.content.Context;

import com.zhang.box.application.BaseApplication;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;

import java.util.List;

import com.zhang.box.db.DaoMaster;

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

    public void clearBean(){
        DaoMaster.dropAllTables(mDao.getDatabase(),true);
        DaoMaster.createAllTables(mDao.getDatabase(),true);
    }
}
