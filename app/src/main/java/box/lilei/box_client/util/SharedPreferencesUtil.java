package box.lilei.box_client.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lilei on 2017/11/21.
 */

public class SharedPreferencesUtil {


    private static SharedPreferences sharedPreferences;

    public static void putString(Context context,String key,String value){
        if (sharedPreferences==null){
            sharedPreferences = context.getSharedPreferences("box",0);
        }
        sharedPreferences.edit().putString(key,value).commit();
    }

    public static String getString(Context context,String key){
        if (sharedPreferences==null){
            sharedPreferences = context.getSharedPreferences("box",0);
        }
        return sharedPreferences.getString(key,"");
    }
}
