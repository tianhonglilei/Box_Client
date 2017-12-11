package com.zhang.box.service.presenter;

import com.zhang.box.client.view.ADBannerView;

/**
 * Created by lilei on 2017/12/4.
 */

public interface HeartPresenter {

    /**
     * 发送心跳
     */
    void sendHeartInfo();


    void setAdView(ADBannerView adBannerView);

}
