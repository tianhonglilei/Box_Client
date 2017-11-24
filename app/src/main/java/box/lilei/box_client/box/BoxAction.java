package box.lilei.box_client.box;

import android.content.Context;
import android.util.Log;

import com.avm.serialport_142.MainHandler;
import com.avm.serialport_142.utils.Avm;

import java.util.Random;

import box.lilei.box_client.client.model.RoadInfo;
import box.lilei.box_client.util.SharedPreferencesUtil;

/**
 * Created by lilei on 2017/11/22.
 */

public class BoxAction {

    public static final int OUT_GOODS_NULL = -1;
    public static final int OUT_GOODS_FAIL = 1;
    public static final int OUT_GOODS_SUCCESS = 0;

    /**
     * 出货
     *
     * @param boxType   货柜类型
     * @param roadIndex 货道编号
     * @return
     */
    public static void outGoods(String boxType, String roadIndex) {
        if (Integer.parseInt(roadIndex) < 10 && roadIndex.length() == 1) {
            roadIndex = "0" + roadIndex;
        }
        String params = boxType + "1" + roadIndex + "00000100" + Avm.OUT_GOODS_OTHER;
        String random = "" + ((Math.random() * 9 + 1) * 100000);
        Log.e("BoxAction", params);
        MainHandler.noticeAvmOutGoods(params, random);
    }

    /**
     * 货道状态
     *
     * @param boxType
     * @param roadIndex
     * @return
     */
    public static int getRoadState(String boxType, String roadIndex) {
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

    public static String getBoxIdFromSP(Context context){
        return SharedPreferencesUtil.getString(context,"box_id");
    }


    public static int getOutGoodsState() {
        String result = MainHandler.getTranResult();
        if (result.equals("-1") || Integer.parseInt(result) == -1) {
            return OUT_GOODS_NULL;
        } else if (result.length() > 10) {
            int num = Integer.parseInt(result.substring(17,18));
            if (num == 0) {
                return OUT_GOODS_SUCCESS;
            }else{
                return OUT_GOODS_FAIL;
            }
        }else{
            return OUT_GOODS_NULL;
        }
    }



}
