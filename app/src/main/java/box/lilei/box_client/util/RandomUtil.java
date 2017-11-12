package box.lilei.box_client.util;

/**
 * Created by lilei on 2017/11/12.
 */

public class RandomUtil {

    //随机一个长度的字符串
    public static String getRandonInt(int lenth) {
        String str = "";
        for (int i = 0; i < lenth; i++) {
            str += String.valueOf((int) (Math.random() * 9));
        }
        return str;
    }
}
