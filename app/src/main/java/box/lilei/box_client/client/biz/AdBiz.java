package box.lilei.box_client.client.biz;

import java.util.List;

import box.lilei.box_client.client.model.ADInfo;
import box.lilei.box_client.client.model.jsonmodel.AdJsonInfo;
import box.lilei.box_client.db.AdBean;

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
