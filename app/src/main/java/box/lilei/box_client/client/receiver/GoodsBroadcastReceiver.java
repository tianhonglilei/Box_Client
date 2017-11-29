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
    private NavRoadFragmentView roadFragmentView;
    private PayView payView;


    private Context mContext;
    private OutGoodsListener outGoodsListener;

    public GoodsBroadcastReceiver() {
    }

    public GoodsBroadcastReceiver(PayView payView, OutGoodsListener outGoodsListener) {
        this.payView = payView;
        mContext = (PayActivity) payView;
        this.outGoodsListener = outGoodsListener;
    }

    private CountDownTimer countDownTimer;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        String action = intent.getAction();
        if (action.equals("com.avm.serialport.OUT_GOODS")) {
            countDownTimer = new CountDownTimer(40000, 500) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int state = BoxAction.getOutGoodsState();
                    Log.e("GoodsBroadcastReceiver", "state:" + state);
                    if (state == BoxAction.OUT_GOODS_SUCCESS) {
                        outGoodsListener.outSuccess();
                    } else if (state == BoxAction.OUT_GOODS_NULL) {

                    } else if (state == BoxAction.OUT_GOODS_FAIL) {
                        outGoodsListener.outFail();
                    }
                }
                @Override
                public void onFinish() {
                    outGoodsListener.outOver();
                }
            };
        }


        throw new UnsupportedOperationException("Not yet implemented");
    }
}
