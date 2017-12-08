package com.zhang.box.db.biz;

import android.content.Context;

import java.util.List;

import com.zhang.box.db.AdBean;

/**
 * Created by lilei on 2017/10/30.
 */

public class AdBeanService extends BeanService<AdBean>{

    private static final String TAG = AdBeanService.class.getSimpleName();

    public AdBeanService(Context context, Class object) {
        super(context, object);
    }

    public List<AdBean> queryAllAdBean(){
        return mDao.loadAll();
    }




}
