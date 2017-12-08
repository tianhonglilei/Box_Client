package com.zhang.box.manager.presenter;

import android.widget.GridView;

import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.model.paramsmodel.AddGoods;

/**
 * Created by lilei on 2017/11/14.
 */

public interface NavGoodsPresenter {

    void initGoodsGridView(GridView gridView);

    void checkGoodsData(GridView gridView,String boxType);

    /**
     * 补货
     * @param id
     * @param now
     * @param max
     */
    void updateGoodsNum(Long id,int now,int max);

    /**
     * 单独货道刷新数量
     * @param roadGoods
     */
    void refreshGoodsNum(RoadGoods roadGoods, int position);

    /**
     * 补满全部货道
     */
    void allRoadFull();

    void refreshAllRoadNum();


    /**
     * 一键补满
     * @param addGoods
     * @param dbId
     */
    void goodsAllToUrl(AddGoods addGoods,Long dbId);

    /**
     * 单个补货
     * @param addGoods
     * @param roadGoods
     * @param position
     */
    void goodsOneToUrl(AddGoods addGoods,RoadGoods roadGoods,int position);

}
