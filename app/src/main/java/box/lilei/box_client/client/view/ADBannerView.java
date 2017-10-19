package box.lilei.box_client.client.view;

import box.lilei.box_client.client.model.ADInfo;
import box.lilei.box_client.client.model.MyTime;
import box.lilei.box_client.client.model.MyWeather;

/**
 * Created by lilei on 2017/9/4.
 * 首页展示广告和推荐商品View接口
 */

public interface ADBannerView {
    //导航至支付页面
    void navigateToPay();
    //导航至更多商品展示
    void navigateToMoreGoods();
    //广告改变
    void changeAD(ADInfo adInfo,int position);

    /**
     * 天气时间变化
     * @param
     */
    void changeWeather(MyWeather myWeather);

    void updateDate(MyTime myTime);
}
