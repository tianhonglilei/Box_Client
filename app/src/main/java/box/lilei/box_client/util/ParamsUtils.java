package box.lilei.box_client.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lilei on 2017/11/12.
 * 接口参数工具
 */

public class ParamsUtils {

    /**
     * 微信二维码参数规则
     * @param tradeAmt 价格
     * @param body
     * @param mchTradeNo 随机数
     * @param subject 订单
     * @return
     */
    public static Map<String,String> wxGetQRParams(String tradeAmt,String body,String mchTradeNo,String subject){
        Map<String,String> params = new HashMap<String,String>();
        params.put("tradeAmt", tradeAmt);
        params.put("body", body);
        params.put("mchTradeNo", mchTradeNo);
//        String subject = desInfos.des + "|" + desInfos.id + "|"
//                + desInfos.hdid + "|" + SysData.imei + "|"
//                + desInfos.prohuogui;
        params.put("subject", subject);
        return params;
    }

    /**
     * 支付宝获取二维码参数规则
     * @param tradeno   订单号
     * @param zhiprice  支付价格
     * @param title     标题
     * @param des
     * @return
     */
    public static Map<String,String> aliGetQRParams(String tradeno,String zhiprice,String title,String des){
        Map<String,String> params = new HashMap<String,String>();
        params.put("tradeno", tradeno);
        params.put("price", zhiprice);
        params.put("title", title);
        params.put("des", des);
        return params;
    }

    /**
     * 补货接口参数规则
     * @param machineid 机器号
     * @param hid       货道号
     * @param huodao_num 当前数量
     * @param hgid      货柜编号
     * @param pid       商品号
     * @param huodao_max 货道最大数
     * @return
     */
    public static Map<String,String> goodsNumAdd(String machineid,String hid,String huodao_num,String hgid,String pid,String huodao_max){
        Map<String,String> params = new HashMap<String,String>();
        return params;
    }
























}
