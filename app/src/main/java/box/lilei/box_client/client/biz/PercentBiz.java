package box.lilei.box_client.client.biz;

import java.util.List;

import box.lilei.box_client.client.model.PercentInfo;
import box.lilei.box_client.db.PercentBean;

/**
 * Created by lilei on 2017/11/9.
 */

public interface PercentBiz {

    PercentInfo parseBeanToInfo(List<PercentBean> percentBeanList);

}
