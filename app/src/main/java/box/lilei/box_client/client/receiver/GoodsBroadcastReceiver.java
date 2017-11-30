package box.lilei.box_client.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.avm.serialport_142.MainHandler;

import java.util.Timer;
import java.util.TimerTask;

import box.lilei.box_client.box.BoxAction;
import box.lilei.box_client.client.listener.OutGoodsListener;
import box.lilei.box_client.client.view.PayView;
import box.lilei.box_client.client.view.activity.PayActivity;
import box.lilei.box_client.manager.view.NavRoadFragmentView;
import box.lilei.box_client.manager.view.fragment.NavRoadFragment;
import box.lilei.box_client.util.ToastTools;

public class GoodsBroadcastReceiver extends BroadcastReceiver {

    private OutGoodsListener outGoodsListener;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals(BoxAction.OUT_GOODS_RECEIVER_ACTION)) {
            int state = BoxAction.getOutGoodsState();
            Log.e("GoodsBroadcastReceiver", "state:" + state);
            if (state == BoxAction.OUT_GOODS_SUCCESS) {
                outGoodsListener.outSuccess();
            } else if (state == BoxAction.OUT_GOODS_NULL) {
                outGoodsListener.outFail();
            } else if (state == BoxAction.OUT_GOODS_FAIL) {
                outGoodsListener.outFail();
            }
        }
    }

    public void setLisenter(OutGoodsListener outGoodsListener){
        this.outGoodsListener = outGoodsListener;
    }
}
