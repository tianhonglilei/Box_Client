package box.lilei.box_client.box;

import com.avm.serialport_142.MainHandler;

import box.lilei.box_client.util.SharedPreferencesUtil;

/**
 * Created by lilei on 2017/11/21.
 * 获取机器设置参数的类
 */

public class BoxParams {

    private String avmSetInfo;

    public BoxParams(String box_id){
        setAvmSetInfo();
        this.box_id = box_id;
    }

    //机器号
    private String box_id;
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

    private String getAvmSetInfo() {
        if (avmSetInfo==null||avmSetInfo.equals("")) {
            setAvmSetInfo();
        }
        return avmSetInfo;
    }

    private void setAvmSetInfo() {
        this.avmSetInfo = MainHandler.getAVMConfigInfo(Integer.parseInt(BoxSetting.BOX_TYPE_DRINK));
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public String getLeft_state() {
        return avmSetInfo.substring(18,20);
    }


    public String getLeft_temp() {
        return avmSetInfo.substring(34,36);
    }

    public String getRight_state() {
        return avmSetInfo.substring(20,22);
    }

    public String getRight_temp() {
        return avmSetInfo.substring(40,42);
    }

    public String getStart_time() {
        return avmSetInfo.substring(22,26);
    }

    public String getEnd_time() {
        return avmSetInfo.substring(26,30);
    }

    public String getCold_temp() {
        return avmSetInfo.substring(31,33);
    }

    public String getHot_temp() {
        return avmSetInfo.substring(37,39);
    }
}
