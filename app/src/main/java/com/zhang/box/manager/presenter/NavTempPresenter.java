package com.zhang.box.manager.presenter;

/**
 * Created by lilei on 2017/11/26.
 */

public interface NavTempPresenter {

    /**
     * 获取温度设置
     */
    void getTempSetting();

    /**
     * 设置温度
     * @param leftState
     * @param rightState
     * @param cold
     * @param hot
     */
    void setTemp(String leftState, String rightState, String cold, String hot);


}
