package box.lilei.box_client.db.biz;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.db.PercentBean;
import box.lilei.box_client.db.PercentBeanDao;

/**
 * Created by lilei on 2017/11/7.
 */

public class PercentBeanService extends BeanService<PercentBean> {
    public PercentBeanService(Context context, Class object) {
        super(context, object);
    }

    public List<PercentBean> getPercentBeanListByGoodsId(Long id){
        return mDao.queryBuilder().where(PercentBeanDao.Properties.Id.eq(id)).build().list();
    }

}