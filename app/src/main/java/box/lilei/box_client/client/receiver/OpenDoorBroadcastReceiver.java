package box.lilei.box_client.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import box.lilei.box_client.manager.view.activity.LoginActivity;

public class OpenDoorBroadcastReceiver extends BroadcastReceiver {
    private static final String DOOR_ACTION = "com.avm.serialport.door_state";


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        String action = intent.getAction();
        if (action.equals(DOOR_ACTION)){
            Intent managerIntent = new Intent(context, LoginActivity.class);
            context.startActivity(managerIntent);
        }


        throw new UnsupportedOperationException("Not yet implemented");
    }
}
