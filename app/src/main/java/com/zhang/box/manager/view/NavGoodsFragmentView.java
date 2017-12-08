package com.zhang.box.manager.view;

import com.zhang.box.client.model.RoadGoods;

/**
 * Created by lilei on 2017/11/14.
 */

public interface NavGoodsFragmentView {


    void showInputDialog(RoadGoods roadGoods, int position);

    void showViceOne();

    void showLoading(String text);

    void fullProgress(int count,Long nowRoad,int success,int fail);

}
