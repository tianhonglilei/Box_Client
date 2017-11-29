package box.lilei.box_client.util;

import java.util.HashMap;
import java.util.Map;

import box.lilei.box_client.client.model.OrderInfo;
import box.lilei.box_client.client.model.paramsmodel.AddGoods;
import box.lilei.box_client.contants.Constants;

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
     *
     */
    public static Map<String,String> goodsNumAdd(AddGoods addGoods){
        Map<String,String> params = new HashMap<String,String>();
        params.put("machineid",addGoods.getMachineid());
        params.put("hid",addGoods.getHid());
        params.put("huodao_num",addGoods.getHuodao_num());
        params.put("hgid",addGoods.getHgid());
        params.put("pid",addGoods.getPid());
        params.put("huodao_max",addGoods.getHuodao_max());
        return params;
    }


    /**
     * 支付响应
     * @param tradeno
     * @param payType
     * @return
     */
    public static Map<String,String> getPayResponseParams(String tradeno,int payType){
        Map<String,String> params = new HashMap<String,String>();
        if (payType == Constants.PAY_TYPE_WX) {
            params.put("weixintradeno", tradeno);
        }else{
            params.put("tradeno", tradeno);
        }
        return params;
    }


    public static Map<String,String> sendOrderParams(OrderInfo orderInfo,int payType){
        Map<String,String> params = new HashMap<String,String>();
        params.put("imei", orderInfo.getImei());
        params.put("pid", orderInfo.getPid());
        params.put("hdid",orderInfo.getHdid());
        params.put("hgid",orderInfo.getHgid());
        params.put("title",orderInfo.getTitle());
        params.put("price",orderInfo.getPrice());
        params.put("sum",orderInfo.getOrderNum()+"-"+orderInfo.getOutGoodsNum());
        if (payType == Constants.PAY_TYPE_WX) {
            params.put("weixintradeno", orderInfo.getTradeno());
            params.put("mchTradeNo", "");
        }else{
            params.put("tradeno", orderInfo.getTradeno());
        }
        return params;
    }






















}
