package com.zhang.box.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by lilei on 2017/12/9.
 */

public class BroadcastReceiverUtil {


    public static boolean broadcastReceiverIsRegister(Context mContext, String action){
        Intent intent = new Intent();
        intent.setAction(action);
        PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryBroadcastReceivers(intent, 0);
        if(resolveInfos != null && !resolveInfos.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

}
