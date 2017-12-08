package com.zhang.box.client.biz;

import java.util.List;

import com.zhang.box.client.model.PercentInfo;
import com.zhang.box.db.PercentBean;

/**
 * Created by lilei on 2017/11/9.
 */

public interface PercentBiz {

    PercentInfo parseBeanToInfo(List<PercentBean> percentBeanList);

}
