package box.lilei.box_client.client.presenter;

/**
 * Created by lilei on 2017/11/9.
 */

public interface PayPresenter {

    void initPercenterInfo(Long goodsId);

    void getQRCode(String url,double price,int payType);

}
