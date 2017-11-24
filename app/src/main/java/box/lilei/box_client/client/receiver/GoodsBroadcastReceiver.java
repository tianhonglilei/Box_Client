package box.lilei.box_client.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.avm.serialport_142.MainHandler;

import box.lilei.box_client.box.BoxAction;
import box.lilei.box_client.client.view.activity.PayActivity;
import box.lilei.box_client.manager.view.fragment.NavRoadFragment;
import box.lilei.box_client.util.ToastTools;

public class GoodsBroadcastReceiver extends BroadcastReceiver {
    private NavRoadFragment roadFragment;
    private PayActivity payActivity;

    private Context mContext;

    public GoodsBroadcastReceiver(){}

    public GoodsBroadcastReceiver(NavRoadFragment roadFragment) {
        this.roadFragment = roadFragment;
    }

    public GoodsBroadcastReceiver(PayActivity payActivity) {
        this.payActivity = payActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        String action = intent.getAction();
        if (payActivity != null) {
            mContext = payActivity;
        }
        if (roadFragment != null) {
            mContext = roadFragment.getContext();
        }
        if (action.equals("com.avm.serialport.OUT_GOODS")) {
            while (true) {
                int num = BoxAction.getOutGoodsState();
                if (num == BoxAction.OUT_GOODS_SUCCESS) {
                    ToastTools.showShort(mContext,"出货成功");
                    if (payActivity != null) {

                    }
                    if (roadFragment != null) {
                        roadFragment.hiddenLoading();
                    }
                    break;
                } else if (num == BoxAction.OUT_GOODS_NULL) {
                    continue;
                } else if (num == BoxAction.OUT_GOODS_FAIL) {
                    ToastTools.showShort(mContext,"出货失败");
                    break;
                }
                break;
            }

        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
