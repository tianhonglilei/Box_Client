package box.lilei.box_client.client.view;

import box.lilei.box_client.client.model.MyTime;
import box.lilei.box_client.client.model.PercentInfo;

/**
 * Created by lilei on 2017/10/19.
 */

public interface PayView {

    void updateDate(MyTime myTime);

    void showDialog(String text);

    void hiddenDialog();

    void showPercentInfo(PercentInfo percentInfo);

}
