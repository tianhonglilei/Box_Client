package com.zhang.box.client.presenter;

import com.zhang.box.client.model.Goods;
import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.model.RoadInfo;

/**
 * Created by lilei on 2017/11/9.
 */

public interface PayPresenter {


    void initPercenterInfo(Long goodsId);

    void getQRCode(String url, double price, int payType, int payNum, RoadGoods roadGoods);

    void postOrder(int orderNum,int outNum);

    void cancelOrder();

    void chengePayRequest(int num,int payType);

    void cancelRequest();

    void outGoodsAction(final int num, final String boxType, final String roadIndex);
}
