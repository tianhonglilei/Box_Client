package com.zhang.box.client.view;

/**
 * Created by lilei on 2017/11/7.
 */

public interface ActiveView extends BaseView{

    void changeDownloadProgress(int maxNum, int successNum, int failNum);

    void skipToADBannerActivity();

    void showActiveLayout();

    void hiddenActiveLayout(boolean success);

    void exitApplication();

    void restartApp();

}
