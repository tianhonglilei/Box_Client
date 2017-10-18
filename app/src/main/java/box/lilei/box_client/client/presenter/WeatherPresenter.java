package box.lilei.box_client.client.presenter;

/**
 * Created by lilei on 2017/10/16.
 */

public interface WeatherPresenter {
    /**
     * 获取时间
     */
    void getDateInfo();

    /**
     * 获取当天天气信息
     */
    void getWeatherInfo();

    /**
     * 获取实时温度
     */
    void getNowTemp();

}
