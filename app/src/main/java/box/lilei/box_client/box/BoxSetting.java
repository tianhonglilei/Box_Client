package box.lilei.box_client.box;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.avm.serialport_142.MainHandler;

import box.lilei.box_client.util.SharedPreferencesUtil;

/**
 * Created by lilei on 2017/11/2.
 */

public class BoxSetting {
    //应用包名
    public static final String BOX_PACKAGE_NAME = "box.lilei.box_client";
    //心跳间隔
    public static final int BOX_HEART_TIME = 1000 * 60 * 5;
    //机器自检间隔
    public static final int BOX_CHECK_TIME = 1000 * 60;
    //货柜类型
    public static final String BOX_TYPE_DRINK = "11";//饮料机
    public static final String BOX_TYPE_FOOD = "09";//弹簧机

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
        if (Integer.parseInt(cold) < 10) {
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
