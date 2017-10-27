package box.lilei.box_client.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import box.lilei.box_client.biz.RoadBiz;
import box.lilei.box_client.client.model.RoadInfo;

/**
 * Created by lilei on 2017/10/27.
 */

public class RoadBizImpl implements RoadBiz {
    @Override
    public List<RoadInfo> getRoadList() {
        List<RoadInfo> roadInfoList = new ArrayList<>();
        RoadInfo roadInfo;
        for (int i = 1; i < 22; i++) {
            roadInfo = new RoadInfo(i,0,0,21,new Random().nextInt(22));
            roadInfoList.add(roadInfo);
        }
        return roadInfoList;
    }
}
