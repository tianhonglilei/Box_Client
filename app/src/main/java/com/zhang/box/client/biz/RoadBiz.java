package com.zhang.box.client.biz;

import java.util.List;

import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.db.RoadBean;

/**
 * Created by lilei on 2017/10/27.
 */

public interface RoadBiz {


    List<RoadGoods> parseRoadBeanToRoadGoods(List<RoadBean> roadBeanList, String leftState, String rightState);


}
