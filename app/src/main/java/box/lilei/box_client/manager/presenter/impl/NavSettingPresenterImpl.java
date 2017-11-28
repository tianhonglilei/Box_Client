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

        String leftState = SharedPreferencesUtil.getString(mContext, BoxParams.LEFT_STATE);
        String rightState = SharedPreferencesUtil.getString(mContext, BoxParams.RIGHT_STATE);
        String cold = SharedPreferencesUtil.getString(mContext, BoxParams.COLD_TEMP);
        String hot = SharedPreferencesUtil.getString(mContext, BoxParams.HOT_TEMP);
        int left = Integer.parseInt(leftState);
        int right = Integer.parseInt(rightState);
        boolean tempParams = BoxSetting.setBoxTemp(left+"", right+"", cold, hot, lightTime);
//        Toast.makeText(mContext, leftState + "-" + rightState + "-" + cold + "-" + hot + "-" + lightTime, Toast.LENGTH_LONG).show();
        if (tempParams) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    int result = BoxSetting.getBoxTempResponse();
                    navSettingFragmentView.hiddenDialog();
                    if (result == 0) {
                        SharedPreferencesUtil.putString(mContext, BoxParams.LIGHT_TIME, lightTime);
                        navSettingFragmentView.setResult(true);
                    } else {
                        navSettingFragmentView.setResult(false);
                    }
                }
            }, 1500);
        } else {
            navSettingFragmentView.hiddenDialog();
            Toast.makeText(mContext, "数据异常", Toast.LENGTH_SHORT).show();
        }

    }
}
