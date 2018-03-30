package com.zhang.box.box;

import com.avm.serialport_142.MainHandler;

/**
 * Created by lilei on 2017/11/2.
 */

public class BoxSetting {
    //应用包名
    public static final String BOX_PACKAGE_NAME = "com.zhang.box";

    //货柜类型
    public static final String BOX_TYPE_DRINK = "11";//饮料机
    public static final String BOX_TYPE_FOOD = "09";//弹簧机
    public static final String BOX_TYPE_CARD = "-1";//银行卡展示

    //测试机器号
    public static final String BOX_TEST_ID = "93006709";




//    public static boolean setMachineData(int leftStu, int rightStu, String ice, int hot) {
//
//        String info = "11000000000" + leftStu + rightStu + "01000600" + ice
//                + hot;
//        boolean tempSet = MainHandler.noticeAvmConfig(info);
//        return tempSet;
//    }


    /**
     * 设置温度和省电设置
     *
     * @return
     */
    public static boolean setBoxTemp(String leftState, String rightState, String cold, String hot, String lightTime) {
        if (Integer.parseInt(cold) < 10 && cold.length() == 1) {
            cold = "0" + cold;
        }

        String info = "11000000000" + leftState + rightState + lightTime + cold
                + hot;
        boolean tempSet = MainHandler.noticeAvmConfig(info);
        return tempSet;
    }


    /**
     * 在调用设置温度和省电的方法后，要在 500 到 800 毫秒后 调用此方法查询结果
     *
     * @return 返回：0，设置成功；1，设置未完成；其他，设置失败
     */
    public static int getBoxTempResponse() {
        return MainHandler.getConfigResult();
    }


}
