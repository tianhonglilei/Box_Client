package box.lilei.box_client.client.biz;

import java.util.List;

import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.client.model.RoadInfo;
import box.lilei.box_client.db.RoadBean;

/**
 * Created by lilei on 2017/10/27.
 */

public interface RoadBiz {

    List<RoadInfo> getRoadList();


    List<RoadGoods> parseRoadBeanToRoadGoods(List<RoadBean> roadBeanList);


}
