package com.zhang.box.manager.view;

/**
 * Created by lilei on 2017/11/26.
 */

public interface NavTempFragmentView {

    /**
     * 刷新当前温度
     * @param left
     * @param right
     */
    void changeTemp(String left,String right);

    void showDialog(String text);

    void hiddenDialog();

    void setResult(boolean set);


}
