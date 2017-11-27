package box.lilei.box_client.manager.presenter.impl;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import box.lilei.box_client.box.BoxAction;
import box.lilei.box_client.box.BoxParams;
import box.lilei.box_client.box.BoxSetting;
import box.lilei.box_client.manager.presenter.NavTempPresenter;
import box.lilei.box_client.manager.view.NavTempFragmentView;
import box.lilei.box_client.util.SharedPreferencesUtil;

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
    public void setTemp(String leftState, String rightState, String cold, String hot) {
        navTempFragmentView.showDialog("设置中...");
        String lightTime = SharedPreferencesUtil.getString(mContext, BoxParams.LIGHT_TIME);
        if (TextUtils.isEmpty(lightTime)) {
            lightTime = "00000700";
        }
        boolean tempParams = BoxSetting.setBoxTemp(leftState, rightState, cold, hot, lightTime);
        if (tempParams) {
            try {
                Thread.sleep(1000);
                int result = BoxSetting.getBoxTempResponse();
                navTempFragmentView.hiddenDialog();
                if (result == 0) {
                    Toast.makeText(mContext, "设置成功", Toast.LENGTH_LONG).show();
                    SharedPreferencesUtil.putString(mContext, BoxParams.LEFT_STATE, leftState);
                    SharedPreferencesUtil.putString(mContext, BoxParams.RIGHT_STATE, rightState);
                } else {
                    Toast.makeText(mContext, "设置失败，请重新设置", Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                navTempFragmentView.hiddenDialog();
            }
        } else {
            navTempFragmentView.hiddenDialog();
            Toast.makeText(mContext, "设置失败，请重新设置", Toast.LENGTH_SHORT).show();
        }

    }
}
