package com.zhang.box.client.model;

import box.lilei.box_client.R;

/**
 * Created by lilei on 2017/10/12.
 */

public class MyWeather {

    private String weather;
    private String temp;
    private int weatherIcon;

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
        int weatherIcon;
        if (weather == null)return;
        if(weather.equals("晴")){
            weatherIcon = R.drawable.weather_qing;
        }else if(weather.contains("霾")){
            weatherIcon = R.drawable.weather_mai;
        }else if(weather.contains("多云")||weather.equals("多云")){
            weatherIcon = R.drawable.weather_duoyun;
        }else if(weather.contains("雪")){
            weatherIcon = R.drawable.weather_xue;
        }else if(weather.contains("阴")){
            weatherIcon = R.drawable.weather_yin;
        }else if(weather.contains("雨")){
            weatherIcon = R.drawable.weather_yu;
        }else{
            weatherIcon = R.drawable.weather_duoyun;
        }
        setWeatherIcon(weatherIcon);
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    private void setWeatherIcon(int weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}
