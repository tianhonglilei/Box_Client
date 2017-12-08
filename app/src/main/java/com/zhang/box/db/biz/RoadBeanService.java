package com.zhang.box.db.biz;

import android.content.Context;

import com.zhang.box.db.RoadBean;

import java.util.List;

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


    public void updateRoadNum(Long roadId ,int now,int max){
        RoadBean roadBean = mDao.load(roadId);
        roadBean.setMax(max);
        roadBean.setHuodao_num(now);
        updateBean(roadBean);
    }

    public void updateRoadNum(Long roadId, int num){
        RoadBean roadBean = mDao.load(roadId);
        roadBean.setHuodao_num(roadBean.getHuodao_num()-num);
        updateBean(roadBean);
    }
}
