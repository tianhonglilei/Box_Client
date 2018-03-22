package com.zhang.box.manager.view;

import com.zhang.box.client.model.RoadGoods;

/**
 * Created by lilei on 2017/11/14.
 */

public interface NavGoodsFragmentView {

    /**
     * 弹出补货数量窗口
     * @param roadGoods
     * @param position
     */
    void showInputDialog(RoadGoods roadGoods, int position);

    void showViceOne();

    void showLoading(String text);

    void fullProgress(int count,Long nowRoad,int success,int fail);

    /**
     * 弹出修改货道商品窗口
     * @param roadGoods
     * @param position
     */
    void showUpdateGoodsDislog(RoadGoods roadGoods, int position);

}
