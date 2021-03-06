package com.zhang.box.client.presenter;

/**
 * Created by lilei on 2017/11/7.
 */

public interface ActivePresenter {

    void loadAllDataFromUrl(String imei);

    /**
     * 获取机器号
     */
    void getBoxId();

    void activeBox(String code);

    /**
     * 获取保存机器设置
     */
    void saveBoxSetting();

}
