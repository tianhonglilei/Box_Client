package com.zhang.box.box;

import android.content.Context;

import com.avm.serialport_142.MainHandler;
import com.avm.serialport_142.utils.Avm;

import com.zhang.box.util.SharedPreferencesUtil;

/**
 * Created by lilei on 2017/11/22.
 */

public class BoxAction {

    public static final int OUT_GOODS_NULL = -1;
    public static final int OUT_GOODS_FAIL = 1;
    public static final int OUT_GOODS_SUCCESS = 0;
    public static final int OUT_GOODS_TYPE_TEST = 1;
    public static final int OUT_GOODS_TYPE_PAY = 0;

    public static final String OUT_GOODS_RECEIVER_ACTION = "com.avm.serialport.OUT_GOODS";
    public static final String OPEN_DOOR_ACTION = "com.avm.serialport.door_state";

    /**
     * 出货
     *
     * @param boxType   货柜类型
     * @param roadIndex 货道编号
     * @return
     */
    public static boolean outGoods(String boxType, String roadIndex, int type) {
        if (roadIndex == null || roadIndex.equals("")) {
            return false;
        }
        if (Integer.parseInt(roadIndex) < 10 && roadIndex.length() == 1) {
            roadIndex = "0" + roadIndex;
        }
        String outType;
        if (type == OUT_GOODS_TYPE_PAY) {
            if (boxType.equals(BoxSetting.BOX_TYPE_FOOD)) {
                outType = Avm.OUT_GOODS_ROAD_CHECK;
            } else {
                outType = Avm.OUT_GOODS_ALIPAY;
            }
        } else {
            outType = Avm.OUT_GOODS_ROAD_CHECK;
        }
        if (boxType == null || boxType.equals("")) {
            return false;
        }
        String params = boxType + "1" + roadIndex + "00000100" + outType;
        String random = "" + (int) ((Math.random() * 9 + 1) * 100000);
        if (MainHandler.noticeAvmOutGoods(params, random)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 货道状态
     *
     * @param boxType
     * @param roadIndex
     * @return
     */
    public static int getRoadState(String boxType, String roadIndex) {
        if (Integer.parseInt(roadIndex) < 10 && roadIndex.length() == 1) {
            roadIndex = "0" + roadIndex;
        }
        String result = MainHandler.getGoodsInfo(Integer.parseInt(boxType), Integer.parseInt(roadIndex));
        int str = Integer.parseInt(result.substring(0, 1));
        return str;
    }

    /**
     * 获取本机号
     *
     * @return
     */
    public static String getBoxId() {
        return MainHandler.getMachNo();
    }

    public static String getBoxIdFromSP(Context context) {
        return SharedPreferencesUtil.getString(context, BoxParams.BOX_ID);
    }


    public static int getOutGoodsState() {
        String result = MainHandler.getTranResult();
        if (result.equals("-1")) {
            return OUT_GOODS_NULL;
        } else if (result.length() > 10) {
            int num = Integer.parseInt(result.substring(17, 18));
            if (num == 0) {
                return OUT_GOODS_SUCCESS;
            } else {
                return OUT_GOODS_FAIL;
            }
        } else {
            return OUT_GOODS_NULL;
        }
    }


    /**
     * 检测工控机连接状态
     *
     * @return
     */
    public static boolean getAVMRunning() {
        return MainHandler.isAvmRunning();
    }

}
