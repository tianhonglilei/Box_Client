package com.zhang.box.manager.view;

import com.zhang.box.client.model.RoadGoods;

import java.util.List;

/**
 * Created by lilei on 2017/11/14.
 */

public interface NavRoadFragmentView {

    void initNavRoadGv(List<RoadGoods> roadGoodsList);

    void showViceOne();

    void showLoading(String text);

    void hiddenLoading();

    void toastInfo(String info);

}
