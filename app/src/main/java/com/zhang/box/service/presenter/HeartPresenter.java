package com.zhang.box.service.presenter;

/**
 * Created by lilei on 2017/12/4.
 */

public interface HeartPresenter {

    /**
     * 发送心跳
     */
    void sendHeartInfo();

    /**
     * 从机器获取心跳信息
     */
    void getHeartInfo();

}
