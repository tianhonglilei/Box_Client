package com.zhang.box.client.presenter;

import android.widget.GridView;

/**
 * Created by lilei on 2017/9/26.
 */

public interface MoreGoodsPresenter {

    //初始化所有商品
    void initAllGoods(GridView gridView);

    //商品导航
    void checkNav(int nav);

}
