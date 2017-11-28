package box.lilei.box_client.manager.view;

import java.util.List;

import box.lilei.box_client.client.model.RoadGoods;

/**
 * Created by lilei on 2017/11/14.
 */

public interface NavRoadFragmentView {

    void initNavRoadGv(List<RoadGoods> roadGoodsList);

    void showViceOne();

    void showLoading(String text);

    void hiddenLoading();

}
