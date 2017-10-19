package box.lilei.box_client.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import box.lilei.box_client.client.presenter.WeatherPresenter;

/**
 * Created by lilei on 2017/10/17.
 */

public class DateTimeReceiver extends BroadcastReceiver {

    private WeatherPresenter weatherPresenter;

    private static DateTimeReceiver dateTimeReceiver=null;

    public static DateTimeReceiver getInstance() {
        if (dateTimeReceiver == null) {
            synchronized (DateTimeReceiver.class) {
                if (dateTimeReceiver == null) {
                    dateTimeReceiver = new DateTimeReceiver();
                }
            }
        }
        return dateTimeReceiver;
    }

    public void setWeatherPresenter(WeatherPresenter weatherPresenter) {
        this.weatherPresenter = weatherPresenter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            //更新时间的方法
            weatherPresenter.getDateInfo();
            System.gc();
        }
    }

}
