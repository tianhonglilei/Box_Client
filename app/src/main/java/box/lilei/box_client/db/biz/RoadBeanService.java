package box.lilei.box_client.db.biz;

import android.content.Context;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.db.RoadBean;

/**
 * Created by lilei on 2017/11/7.
 */

public class RoadBeanService extends BeanService<RoadBean> {
    public RoadBeanService(Context context, Class object) {
        super(context, object);
    }

    public List<RoadBean> queryAllRoadBean(){
        return mDao.loadAll();
    }

    public void clearAdBean(){
        mDao.deleteAll();
    }

    public void updateRoadNum(Long roadId ,int now,int max){
        RoadBean roadBean = mDao.load(roadId);
        roadBean.setMax(max);
        roadBean.setNowNum(now);
        updateBean(roadBean);
    }

    public void updateRoadNum(Long roadId, int num){

    }
}
