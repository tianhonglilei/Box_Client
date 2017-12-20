package com.zhang.box.client.view;

import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.model.ADInfo;
import com.zhang.box.client.model.MyWeather;

/**
 * Created by lilei on 2017/9/4.
 * 首页展示广告和推荐商品View接口
 */

public interface ADBannerView extends BaseView{
    //导航至支付页面
    void navigateToPay(RoadGoods roadGoods);
    //导航至更多商品展示
    void navigateToMoreGoods();
    //广告改变
    void changeAD(ADInfo adInfo,int position);

    /**
     * 天气时间变化
     * @param
     */
    void changeWeather(MyWeather myWeather);

}
