package box.lilei.box_client.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import box.lilei.box_client.box.BoxAction;
import box.lilei.box_client.client.listener.OutGoodsListener;

public class GoodsBroadcastReceiver extends BroadcastReceiver {

    private OutGoodsListener outGoodsListener;

    public void setOutGoodsListener(OutGoodsListener outGoodsListener) {
        this.outGoodsListener = outGoodsListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("GoodsBroadcastReceiver", intent.getAction());
        if (intent.getAction().equals(BoxAction.OUT_GOODS_RECEIVER_ACTION)) {
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
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
