package box.lilei.box_client.client.presenter;

import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.RoadInfo;

/**
 * Created by lilei on 2017/11/9.
 */

public interface PayPresenter {


    void initPercenterInfo(Long goodsId);

    void getQRCode(String url,double price, int payType, int payNum, Goods goods, RoadInfo roadInfo);

    void postOrder(int orderNum,int outNum);

}
