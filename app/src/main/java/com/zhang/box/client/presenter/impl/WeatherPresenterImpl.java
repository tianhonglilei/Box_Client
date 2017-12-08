package com.zhang.box.client.presenter.impl;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import com.zhang.box.client.model.MyWeather;
import com.zhang.box.client.model.jsonmodel.WeatherTestInfo;
import com.zhang.box.client.okhttp.CommonOkHttpClient;
import com.zhang.box.client.okhttp.handler.DisposeDataHandle;
import com.zhang.box.client.okhttp.listener.DisposeDataListener;
import com.zhang.box.client.okhttp.request.CommonRequest;
import com.zhang.box.client.presenter.WeatherPresenter;
import com.zhang.box.client.view.ADBannerView;
import com.zhang.box.client.view.MoreGoodsView;
import com.zhang.box.client.view.PayView;
import com.zhang.box.contants.Constants;
import com.zhang.box.util.httputil.ResponseEntityToModule;

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
                        if (weatherInfo!=null) {
                            myWeather.setWeather(weatherInfo.getData().getForecast().get(0).getType());
                            myWeather.setTemp(weatherInfo.getData().getWendu() + "℃");
                            adBannerView.changeWeather(myWeather);
                        }
                    }

                    @Override
                    public void onFail(Object errorObject) {
                        ((Exception) errorObject).printStackTrace();
                    }
                }));

    }

}
