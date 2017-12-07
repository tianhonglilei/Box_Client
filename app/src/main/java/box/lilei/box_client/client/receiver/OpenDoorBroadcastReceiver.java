package box.lilei.box_client.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.avm.serialport_142.MainHandler;

import box.lilei.box_client.manager.view.activity.LoginActivity;
import box.lilei.box_client.manager.view.activity.ManagerNavgationActivity;

public class OpenDoorBroadcastReceiver extends BroadcastReceiver {
    private static final String DOOR_ACTION = "com.avm.serialport.door_state";


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        String action = intent.getAction();
        if (action.equals(DOOR_ACTION)){
            boolean isOpen = MainHandler.isDoorOpen();//获取机器开门信息
            if (isOpen) {
                Intent managerIntent = new Intent(context, ManagerNavgationActivity.class);
                context.startActivity(managerIntent);
            }
        }
    }
}
