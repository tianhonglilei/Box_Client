package com.zhang.box.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zhang.box.box.BoxAction;
import com.zhang.box.client.listener.OutGoodsListener;

public class GoodsBroadcastReceiver extends BroadcastReceiver {

    private OutGoodsListener outGoodsListener;

    public void setOutGoodsListener(OutGoodsListener outGoodsListener) {
        this.outGoodsListener = outGoodsListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(BoxAction.OUT_GOODS_RECEIVER_ACTION)) {
            int state = BoxAction.getOutGoodsState();
            Log.e("GoodsBroadcastReceiver", "state:" + state);
            if (state == BoxAction.OUT_GOODS_SUCCESS) {
                if (outGoodsListener!=null)
                outGoodsListener.outSuccess();
            } else if (state == BoxAction.OUT_GOODS_NULL) {
                if (outGoodsListener!=null)
                outGoodsListener.outFail();
            } else if (state == BoxAction.OUT_GOODS_FAIL) {
                if (outGoodsListener!=null)
                outGoodsListener.outFail();
            }
        }
    }
}
