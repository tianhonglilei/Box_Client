package com.zhang.box.client.biz;

import java.util.List;

import com.zhang.box.client.model.ADInfo;
import com.zhang.box.client.model.jsonmodel.AdJsonInfo;
import com.zhang.box.db.AdBean;

/**
 * Created by lilei on 2017/11/5.
 */

public interface AdBiz {

    /**
     * 将json对象转化数据库对象
     * @param adJsonInfoList
     * @return
     */
    List<AdBean> parseAdJsonListToAdBean(List<AdJsonInfo> adJsonInfoList);

    /**
     * 将Bean对象转化为展示对象
     * @param adBeanList
     * @return
     */
    List<ADInfo> parseAdBeanListToAdInfo(List<AdBean> adBeanList);

}
