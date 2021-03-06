package com.zhang.box.client.presenter;

import android.widget.GridView;
import android.widget.ListView;

/**
 * Created by lilei on 2017/9/5.
 * 处理广告页面业务
 */

public interface ADBannerPresenter {

    /**
     * 初始化广告数据
     */
    void initAdData(ListView adbannerAdLv);

    /**
     * 初始化商品数据
     */
    void initGoodsData(GridView adbannerGoodsGv);




    /**
     * 删除错误广告
     * @param position 位置
     * @param
     */
    void deleteAdShow(int position);

}
