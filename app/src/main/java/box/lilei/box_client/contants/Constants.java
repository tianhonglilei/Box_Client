package box.lilei.box_client.contants;

import android.os.Environment;

/**
 * Created by lilei on 2017/10/19.
 */

public class Constants {
    //    public String MAIN_URL="http://60.205.218.33";
    //测试域名
    public static final String MAIN_URL = "http://16p98712s5.iask.in";

    //数据库名称
    public static final String DB_NAME = "box_db";

    //默认文件地址
    public static final String DEMO_FILE_PATH = Environment.getExternalStorageDirectory().toString()+"/Box_client";


    //天气接口获取weather接口
    public static final String WEATHER_INFO_URL = "http://www.sojson.com/open/api/weather/json.shtml?city=%E5%8C%97%E4%BA%AC";


    /**
     * 前端模块接口
     */

    //初始化数据获取
    public static final String BANNER_AD_URL = MAIN_URL + "/dc/boxapp/?c=app&m=initialize_data";


}