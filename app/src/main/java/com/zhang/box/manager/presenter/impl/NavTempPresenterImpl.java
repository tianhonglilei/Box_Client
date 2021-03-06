package com.zhang.box.manager.presenter.impl;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.zhang.box.box.BoxSetting;
import com.zhang.box.manager.presenter.NavTempPresenter;

import java.util.Timer;
import java.util.TimerTask;

import com.zhang.box.box.BoxParams;
import com.zhang.box.manager.view.NavTempFragmentView;
import com.zhang.box.util.SharedPreferencesUtil;

/**
 * Created by lilei on 2017/11/26.
 */

public class NavTempPresenterImpl implements NavTempPresenter {
    private Context mContext;
    private NavTempFragmentView navTempFragmentView;

    public NavTempPresenterImpl(Context mContext, NavTempFragmentView navTempFragmentView) {
        this.mContext = mContext;
        this.navTempFragmentView = navTempFragmentView;
    }

    @Override
    public void getTempSetting() {
        BoxParams boxParams = new BoxParams();
        if (!boxParams.getAvmSetInfo().equals("0")) {
            String left = boxParams.getLeft_temp();
            String right = boxParams.getRight_temp();
            navTempFragmentView.changeTemp(left, right);
        }
    }

    @Override
    public void setTemp(final String leftState, final String rightState, final String cold, final String hot) {
        navTempFragmentView.showDialog("设置中...");
        String lightTime = SharedPreferencesUtil.getString(mContext, BoxParams.LIGHT_TIME);
        if (TextUtils.isEmpty(lightTime)) {
            lightTime = "00000700";
        }
        boolean tempParams = BoxSetting.setBoxTemp(leftState, rightState, cold, hot, lightTime);
        if (tempParams) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    int result = BoxSetting.getBoxTempResponse();
                    navTempFragmentView.hiddenDialog();
                    if (result == 0) {
                        SharedPreferencesUtil.putString(mContext, BoxParams.LEFT_STATE, leftState);
                        SharedPreferencesUtil.putString(mContext, BoxParams.RIGHT_STATE, rightState);
                        SharedPreferencesUtil.putString(mContext, BoxParams.COLD_TEMP, cold);
                        SharedPreferencesUtil.putString(mContext, BoxParams.HOT_TEMP, hot);
                        navTempFragmentView.setResult(true);
                    } else {
                        navTempFragmentView.setResult(false);
                    }
                }
            }, 1500);
        } else {
            navTempFragmentView.hiddenDialog();
            Toast.makeText(mContext, "设置失败，请重新设置", Toast.LENGTH_SHORT).show();
        }

    }


}
