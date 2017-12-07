package box.lilei.box_client.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import box.lilei.box_client.client.view.activity.ActiveActivity;

public class AVMRunningBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION = "com.avm.serialport.NOTICE_AVM_DISCONNECT";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {

        }
    }
}
