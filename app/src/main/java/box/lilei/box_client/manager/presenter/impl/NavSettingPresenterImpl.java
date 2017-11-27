package box.lilei.box_client.manager.presenter.impl;

import android.content.Context;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import box.lilei.box_client.box.BoxParams;
import box.lilei.box_client.box.BoxSetting;
import box.lilei.box_client.manager.presenter.NavSettingPresenter;
import box.lilei.box_client.manager.view.NavSettingFragmentView;
import box.lilei.box_client.util.SharedPreferencesUtil;

/**
 * Created by lilei on 2017/11/27.
 */

public class NavSettingPresenterImpl implements NavSettingPresenter {
    private Context mContext;
    private NavSettingFragmentView navSettingFragmentView;
    String lightTime;

    public NavSettingPresenterImpl(Context mContext, NavSettingFragmentView navSettingFragmentView) {
        this.mContext = mContext;
        this.navSettingFragmentView = navSettingFragmentView;
    }

    @Override
    public void setLightTime(String start, String end) {
        navSettingFragmentView.showDialog("设置中...");
        lightTime = start + "00" + end + "00";
        String leftState = SharedPreferencesUtil.getString(mContext,BoxParams.LEFT_STATE);
        String rightState = SharedPreferencesUtil.getString(mContext,BoxParams.RIGHT_STATE);
        String cold = SharedPreferencesUtil.getString(mContext,BoxParams.COLD_TEMP);
        String hot = SharedPreferencesUtil.getString(mContext,BoxParams.HOT_TEMP);
        boolean tempParams = BoxSetting.setBoxTemp(leftState, rightState, cold, hot, lightTime);
        if (tempParams) new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int result = BoxSetting.getBoxTempResponse();
                navSettingFragmentView.hiddenDialog();
                if (result == 0) {
                    Toast.makeText(mContext, "设置成功", Toast.LENGTH_LONG).show();
                    SharedPreferencesUtil.putString(mContext,BoxParams.LIGHT_TIME,lightTime);
                } else {
                    Toast.makeText(mContext, "设置失败，请重新设置", Toast.LENGTH_SHORT).show();
                }
            }
        }, 1500);
        else {
            navSettingFragmentView.hiddenDialog();
            Toast.makeText(mContext, "设置失败，请重新设置", Toast.LENGTH_SHORT).show();
        }

    }
}
