package box.lilei.box_client.manager.presenter;

import java.util.List;

import box.lilei.box_client.client.model.RoadGoods;

/**
 * Created by lilei on 2017/11/14.
 */

public interface NavRoadPresenter {

    void initRoadInfo();

    List<RoadGoods> checkRobot(String boxType);
}
