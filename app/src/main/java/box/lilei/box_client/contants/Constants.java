package box.lilei.box_client.contants;

import android.os.Environment;

/**
 * Created by lilei on 2017/10/19.
 */

public class Constants {
    public static final String MAIN_URL_ONE="http://60.205.218.33";
    //测试域名
    public static final String MAIN_URL = "http://16p98712s5.iask.in";

    //数据库名称
    public static final String DB_NAME = "box_db";

    //默认文件地址
    public static final String DEMO_FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/Box_client";


    //天气接口获取weather接口
    public static final String WEATHER_INFO_URL = "http://www.sojson.com/open/api/weather/json.shtml?city=%E5%8C%97%E4%BA%AC";


    /**
     * 前端模块接口
     */

    //初始化数据获取
    public static final String BANNER_AD_URL = MAIN_URL + "/dc/boxapp/?c=app&m=machine_heart";

    /**
     * 微信支付
     */
    //微信二维码
    public static final String WX_GET_QR_URL = MAIN_URL_ONE + "/dc/dcbox/weixin/example/native.php";
    public static final int PAY_TYPE_WX = 0;
    //获取微信支付结果
    public static final String WX_GET_PAY_RESPONSE = MAIN_URL_ONE + "/dc/boxapp/?c=welcome&m=box_weixinuodao";
    //发送微信订单
    public static final String WX_SEND_ORDER = MAIN_URL_ONE + "/dc/boxapp/?c=welcome&m=box_weixinhuodaostatus";

    /**
     * 支付宝支付
     */
    //支付宝二维码
    public static final String ALI_GET_QR_URL = MAIN_URL_ONE + "/dc/f2f/f2fpay/qrpay_test.php";
    public static final int PAY_TYPE_ALI = 1;
    //获取支付宝支付结果
    public static final String ALI_GET_PAY_RESPONSE = MAIN_URL_ONE + "/dc/boxapp/?c=welcome&m=box_zhifubaohuodao";
    //发送支付宝订单
    public static final String ALI_SEND_ORDER = MAIN_URL_ONE + "/dc/boxapp/?c=welcome&m=box_zhifubaohuodaostatus";

    /**
     * 管理员模块
     */
    //补货接口
    public static final String GOODS_NUM_ADD = MAIN_URL + "/dc/boxapp/?c=app&m=buhuo";
}
