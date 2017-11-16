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
import box.lilei.box_client.client.model.jsonmodel.WeatherTestInfo;
import box.lilei.box_client.client.okhttp.CommonOkHttpClient;
import box.lilei.box_client.client.okhttp.exception.OkHttpException;
import box.lilei.box_client.client.okhttp.handler.DisposeDataHandle;
import box.lilei.box_client.client.okhttp.listener.DisposeDataListener;
import box.lilei.box_client.client.okhttp.request.CommonRequest;
import box.lilei.box_client.client.presenter.WeatherPresenter;
import box.lilei.box_client.client.view.ADBannerView;
import box.lilei.box_client.client.view.MoreGoodsView;
import box.lilei.box_client.client.view.PayView;
import box.lilei.box_client.contants.Constants;
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


    @Override
    public void getWeatherInfo() {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(Constants.WEATHER_INFO_URL, null),
                new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObject) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject((String)responseObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        WeatherTestInfo weatherInfo = (WeatherTestInfo) ResponseEntityToModule.parseJsonObjectToModule(jsonObject, WeatherTestInfo.class);
                        myWeather.setWeather(weatherInfo.getData().getForecast().get(0).getType());
                        myWeather.setTemp(weatherInfo.getData().getWendu()+"℃");
                        adBannerView.changeWeather(myWeather);
                    }

                    @Override
                    public void onFail(Object errorObject) {
                        ((Exception) errorObject).printStackTrace();
                    }
                }));

    }

}
