package com.zhang.box.manager.presenter;

import java.util.List;

import com.zhang.box.client.model.RoadGoods;

/**
 * Created by lilei on 2017/11/14.
 */

public interface NavRoadPresenter {

    void initRoadInfo();

    List<RoadGoods> checkRobot(String boxType);

    void testRoad(String boxType,String index);

    void clearRoad(String boxType,String index);

}
