package box.lilei.box_client.client.view;

import android.graphics.Bitmap;

import box.lilei.box_client.client.model.MyTime;
import box.lilei.box_client.client.model.PercentInfo;

/**
 * Created by lilei on 2017/10/19.
 */

public interface PayView {

    /**
     * 刷新时间
     * @param myTime
     */
    void updateDate(MyTime myTime);

    void showDialog(String text);

    void hiddenDialog();

    /**
     * 展示营养信息
     * @param percentInfo
     */
    void showPercentInfo(PercentInfo percentInfo);

    /**
     * 展示二维码
     * @param bitmap
     */
    void showQRCode(Bitmap bitmap);

    void loadQRCode();

}
