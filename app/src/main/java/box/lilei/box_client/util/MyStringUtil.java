package box.lilei.box_client.util;

/**
 * Created by lilei on 2017/11/14.
 */

public class MyStringUtil {

    public static String getRandonInt(int lenth) {
        String str = "";
        for (int i = 0; i < lenth; i++) {
            str += String.valueOf((int) (Math.random() * 9));
        }
        return str;
    }
}
