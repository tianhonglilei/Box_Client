package box.lilei.box_client.client.view;

import android.graphics.Bitmap;

import box.lilei.box_client.client.listener.OutGoodsListener;
import box.lilei.box_client.client.model.MyTime;
import box.lilei.box_client.client.model.PercentInfo;

/**
 * Created by lilei on 2017/10/19.
 */

public interface PayView  extends BaseView{

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

    /**
     * 出货后弹窗
     * @param success
     * @param orderNum
     * @param successNum
     */
    void showPopwindow(boolean success,int orderNum,int successNum);


    /**
     * 出货检测
     * @param num 出货数量
     */
    void outGoodsCheck(int num);


}
