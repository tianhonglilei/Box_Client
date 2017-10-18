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

    public DateTimeReceiver(WeatherPresenter weatherPresenter) {
        this.weatherPresenter = weatherPresenter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            //更新时间的方法
            weatherPresenter.getDateInfo();
        }
    }
}
