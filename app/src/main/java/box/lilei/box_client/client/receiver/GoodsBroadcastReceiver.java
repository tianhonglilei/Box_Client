package box.lilei.box_client.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
    private int num;

    public GoodsBroadcastReceiver() {
    }

    public GoodsBroadcastReceiver(NavRoadFragmentView roadFragmentView) {
        this.roadFragmentView = roadFragmentView;
        mContext = ((NavRoadFragment) roadFragmentView).getContext();
    }

    public GoodsBroadcastReceiver(PayView payView, OutGoodsListener outGoodsListener) {
        this.payView = payView;
        mContext = (PayActivity) payView;
        this.outGoodsListener = outGoodsListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        String action = intent.getAction();
        if (action.equals("com.avm.serialport.OUT_GOODS")) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    int state = BoxAction.getOutGoodsState();
                    Log.e("GoodsBroadcastReceiver", "state:" + state);
                    if (state == BoxAction.OUT_GOODS_SUCCESS) {
                        outGoodsListener.outSuccess();
                        num++;
                    } else if (state == BoxAction.OUT_GOODS_NULL) {

                    } else if (state == BoxAction.OUT_GOODS_FAIL) {
                        outGoodsListener.outFail();
                        num++;
                    }
                }
            },0,200);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (num == 0){
                        outGoodsListener.outFail();
                    }
                }
            },5000);

        }

        throw new

    UnsupportedOperationException("Not yet implemented");
}
}
