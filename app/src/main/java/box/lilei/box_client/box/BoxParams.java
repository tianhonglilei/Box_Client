package box.lilei.box_client.box;

import android.text.TextUtils;

import com.avm.serialport_142.MainHandler;

import box.lilei.box_client.util.SharedPreferencesUtil;

/**
 * Created by lilei on 2017/11/21.
 * 获取机器设置参数的类
 */

public class BoxParams {

    public static final String ACTIVE_CODE = "active_code";
    public static final String LEFT_STATE = "left_state";
    public static final String RIGHT_STATE = "right_state";
    public static final String BOX_ID = "box_id";
    public static final String LIGHT_TIME = "light_time";
    public static final String COLD_TEMP = "cold_temp";
    public static final String HOT_TEMP = "hot_temp";
    public static final String HAVE_FOOD = "have_food";

    private String avmSetInfo;

    public BoxParams() {
        setAvmSetInfo();
    }

    //左室状态
    private String left_state;
    //当前左室温度
    private String left_temp;
    //右室状态
    private String right_state;
    //当前右室温度
    private String right_temp;
    //节能开始时间
    private String start_time;
    //节能关闭时间
    private String end_time;
    //当前设置制冷温度
    private String cold_temp;
    //当前设置制热温度
    private String hot_temp;

    public String getAvmSetInfo() {
        if (avmSetInfo == null || avmSetInfo.equals("")) {
            setAvmSetInfo();
        }
        return avmSetInfo;
    }

    private void setAvmSetInfo() {
        String info = MainHandler.getAVMConfigInfo(Integer.parseInt(BoxSetting.BOX_TYPE_DRINK));
        if (!TextUtils.isEmpty(info)) {
            this.avmSetInfo = info;
        } else {
            this.avmSetInfo = "0";
        }
    }


    public String getLeft_state() {
        if (avmSetInfo.length() > 41) {
            return avmSetInfo.substring(18, 20);
        } else {
            return "";
        }
    }

    public String getLeft_temp() {
        if (avmSetInfo.length() > 41) {
            return avmSetInfo.substring(34, 36);
        } else {
            return "";
        }
    }

    public String getRight_state() {
        if (avmSetInfo.length() > 41) {
            return avmSetInfo.substring(20, 22);
        } else {
            return "";
        }
    }

    public String getRight_temp() {
        if (avmSetInfo.length() > 41) {
            return avmSetInfo.substring(40, 42);
        } else {
            return "";
        }
    }

    public String getStart_time() {

        if (avmSetInfo.length() > 41) {
            return avmSetInfo.substring(22, 26);
        } else {
            return "";
        }
    }

    public String getEnd_time() {
        if (avmSetInfo.length() > 41) {
            return avmSetInfo.substring(26, 30);
        } else {
            return "";
        }
    }

    public String getCold_temp() {
        if (avmSetInfo.length() > 41) {
            return avmSetInfo.substring(31, 33);
        } else {
            return "";
        }
    }

    public String getHot_temp() {
        if (avmSetInfo.length() > 41) {
            return avmSetInfo.substring(37, 39);
        } else {
            return "";
        }
    }
}
