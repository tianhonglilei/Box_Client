package box.lilei.box_client.db.biz;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.application.BaseApplication;
import box.lilei.box_client.db.AdBean;
import box.lilei.box_client.db.AdBeanDao;
import box.lilei.box_client.db.DaoSession;

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
