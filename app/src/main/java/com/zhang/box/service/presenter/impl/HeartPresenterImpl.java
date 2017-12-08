package com.zhang.box.service.presenter.impl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avm.serialport_142.MainHandler;
import com.zhang.box.box.BoxAction;
import com.zhang.box.box.BoxParams;
import com.zhang.box.client.okhttp.CommonOkHttpClient;
import com.zhang.box.client.okhttp.handler.DisposeDataHandle;
import com.zhang.box.client.okhttp.listener.DisposeDataListener;
import com.zhang.box.client.okhttp.listener.DisposeDownloadDataListener;
import com.zhang.box.client.okhttp.request.CommonRequest;
import com.zhang.box.client.okhttp.request.RequestParams;
import com.zhang.box.client.view.ADBannerView;
import com.zhang.box.client.view.activity.ADBannerActivity;
import com.zhang.box.contants.Constants;
import com.zhang.box.service.presenter.HeartPresenter;
import com.zhang.box.service.view.HeartView;
import com.zhang.box.util.ParamsUtils;
import com.zhang.box.util.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lilei on 2017/12/4.
 */

public class HeartPresenterImpl implements HeartPresenter {

    Context mContext;
    ADBannerView adBannerView;
    HeartView heartView;
    Map<String, String> params;
    String apkUrl;
    String version;

    public HeartPresenterImpl(Context mContext, ADBannerView adBannerView, HeartView heartView) {
        this.mContext = mContext;
        this.adBannerView = adBannerView;
        this.heartView = heartView;
    }

    @Override
    public void sendHeartInfo() {
        getHeartInfo();
        CommonOkHttpClient.post(CommonRequest.createGetRequest(Constants.HEART_URL, new RequestParams(params)), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObject) {
                JSONObject jsonObject = JSONObject.parseObject(responseObject.toString());
                String msg = jsonObject.getString("msg");
                if (msg.equals("1")) {
                    heartView.restartApp();
                } else if (msg.equals("2")) {

                } else if (msg.equals("3")) {
                    JSONArray array = jsonObject.getJSONArray("down");
                    apkUrl = array.getJSONObject(0).getString("apk");
                    version = array.getJSONObject(1).getString("ver");
                    updateApk();
                }
            }

            @Override
            public void onFail(Object errorObject) {
                ((Exception) errorObject).printStackTrace();
            }
        }));


    }


    /**
     * 心跳信息
     */
    private void getHeartInfo() {
        String box_id = SharedPreferencesUtil.getString(mContext, BoxParams.BOX_ID);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < 22; i++) {
            String huodaoInfo = MainHandler.getGoodsInfo(11, i);
            String subHuodaoInfo = huodaoInfo.substring(0, 1);
            stringBuilder.append(i + "|" + subHuodaoInfo + "|");
        }
        String door = BoxAction.getAVMRunning() ? "0" : "1";
        if (door.equals("1")){
            disConnection();
        }
        String msg = SharedPreferencesUtil.getString(mContext, BoxParams.ERROR_MSG);
        PackageInfo pinfo;
        String versionCode = "";
        try {
            pinfo = mContext.getPackageManager().getPackageInfo("com.zhang.box",
                    PackageManager.GET_CONFIGURATIONS);
            versionCode = String.valueOf(pinfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        params = ParamsUtils.heartParams(box_id, stringBuilder.toString(), door, msg, versionCode);

    }


    public void updateApk() {
        if (isActivityTop(ADBannerActivity.class, mContext)) {
            adBannerView.showDialog("系统升级");
            CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(apkUrl, null), new DisposeDataHandle(new DisposeDownloadDataListener() {
                @Override
                public void onProgress(int progrss) {

                }

                @Override
                public void onSuccess(Object responseObject) {
                    adBannerView.hiddenDialog();
                    heartView.startAppAfterUpdate(version);
                }

                @Override
                public void onFail(Object errorObject) {
                    if (errorObject instanceof Exception) {
                        ((Exception) errorObject).printStackTrace();
                    }
                }
            }, Constants.DEMO_FILE_PATH + "/Box_" + version + ".apk"));
        } else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    updateApk();
                }
            }, 2000);
        }
    }


    /**
     * 判断某activity是否处于栈顶
     *
     * @return true在栈顶 false不在栈顶
     */
    private boolean isActivityTop(Class cls, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(cls.getName());
    }


    private void disConnection(){
        if (isActivityTop(ADBannerActivity.class, mContext)) {
            adBannerView.showDialog("系统重启");
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    heartView.restartApp();
                }
            },2000);
        }else{
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    disConnection();
                }
            },2000);
        }
    }

}