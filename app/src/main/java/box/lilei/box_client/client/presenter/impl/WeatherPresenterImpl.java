package box.lilei.box_client.client.presenter.impl;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import box.lilei.box_client.client.model.MyTime;
import box.lilei.box_client.client.model.MyWeather;
import box.lilei.box_client.client.model.jsonmodel.WeatherInfo;
import box.lilei.box_client.client.okhttp.CommonOkHttpClient;
import box.lilei.box_client.client.okhttp.exception.OkHttpException;
import box.lilei.box_client.client.okhttp.handler.OkHttpDisposeHandler;
import box.lilei.box_client.client.okhttp.listener.OkHttpDisposeListener;
import box.lilei.box_client.client.okhttp.request.CommonRequest;
import box.lilei.box_client.client.presenter.WeatherPresenter;
import box.lilei.box_client.client.view.ADBannerView;
import box.lilei.box_client.client.view.MoreGoodsView;
import box.lilei.box_client.client.view.PayView;
import box.lilei.box_client.contants.Contants;
import box.lilei.box_client.util.TimeUtil;
import box.lilei.box_client.util.httputil.ResponseEntityToModule;

/**
 * Created by lilei on 2017/10/16.
 */

public class WeatherPresenterImpl implements WeatherPresenter {
    private static final String TAG = "WeatherPresenterImpl";

    private ADBannerView adBannerView;
    private Context mContext;
    private MyWeather myWeather;
    private MoreGoodsView moreGoodsView;
    private PayView payView;


    public WeatherPresenterImpl(ADBannerView adBannerView, Context mContext) {
        this.adBannerView = adBannerView;
        this.mContext = mContext;
        myWeather = new MyWeather();
    }

    public WeatherPresenterImpl(MoreGoodsView moreGoodsView) {
        this.moreGoodsView = moreGoodsView;
    }

    public WeatherPresenterImpl(PayView payView) {
        this.payView = payView;
    }

    @Override
    public void getDateInfo() {
        Calendar calendar = Calendar.getInstance();
        Date timeNow = new Date();
        long time = timeNow.getTime();
        String dateDay = TimeUtil.dateString(time);
        String dateMinute = new SimpleDateFormat("HH:mm").format(timeNow);
        String dateWeek = TimeUtil.dayForWeek(calendar);
        MyTime myTime = new MyTime(dateDay, dateWeek, dateMinute);
        if (adBannerView != null) {
            adBannerView.updateDate(myTime);
            moreGoodsView = null;
            payView = null;
            return;
        }
        if (moreGoodsView != null) {
            moreGoodsView.updateDate(myTime);
            payView = null;
            return;
        }
        if (payView != null) {
            payView.updateDate(myTime);
            moreGoodsView = null;
            return;
        }

    }

    @Override
    public void getWeatherInfo() {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(Contants.WEATHER_INFO_URL, null),
                new OkHttpDisposeHandler(new OkHttpDisposeListener() {
                    @Override
                    public void onSuccess(Object responseObject) {
                        JSONObject jsonObject = (JSONObject) responseObject;
                        WeatherInfo weatherInfo = null;
                        try {
                            weatherInfo = (WeatherInfo) ResponseEntityToModule.parseJsonObjectToModule(jsonObject.getJSONObject("weatherinfo"), WeatherInfo.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        myWeather.setWeather(weatherInfo.getWeather());
                        myWeather.setTemp(weatherInfo.getTemp2());
                        adBannerView.changeWeather(myWeather);
                        Log.e(TAG, "weather-onSuccess: " + responseObject.toString());
                    }

                    @Override
                    public void onFail(Object errorObject) {
                        Log.e(TAG, "onFail: w" + ((OkHttpException) errorObject).getEcode());
                    }
                }));

    }

}
