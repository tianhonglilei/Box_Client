package box.lilei.box_client.client.view;

import box.lilei.box_client.client.model.ADInfo;

/**
 * Created by lilei on 2017/9/4.
 * 首页展示广告和推荐商品View接口
 */

public interface ADBannerView {
    //导航至支付页面
    void navigateToPay();
    //导航至更多商品展示
    void navigateToMoreGoods();
    //广告缩略图滚动
    void scrollAD(int position);
    //广告改变
    void changeAD(ADInfo adInfo);

    /**
     * 商品自动滚动
     */
    void autoScrollGoods();
}
