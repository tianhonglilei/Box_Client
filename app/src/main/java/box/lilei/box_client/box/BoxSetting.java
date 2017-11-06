package box.lilei.box_client.box;

import android.util.Log;

import com.avm.serialport_142.MainHandler;

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


    public static boolean setMachineData(int leftStu, int rightStu, String ice, int hot) {

        String info = "11000000000" + leftStu + rightStu + "01000600" + ice
                + hot;
        boolean tempSet = MainHandler.noticeAvmConfig(info);
        return tempSet;
    }
}
