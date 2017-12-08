package com.zhang.box.service.view;

/**
 * Created by lilei on 2017/12/4.
 */

public interface HeartView {

    /**
     * 重启app
     */
    void restartApp();

    /**
     * 启动更新完成后的app
     */
    void startAppAfterUpdate(String version);

}
