package box.lilei.box_client.client.biz;

import java.util.List;

import box.lilei.box_client.client.model.ADInfo;
import box.lilei.box_client.client.model.jsonmodel.AdJsonInfo;
import box.lilei.box_client.db.AdBean;

/**
 * Created by lilei on 2017/11/5.
 */

public interface AdBiz {

    List<ADInfo> getAdInfoListFromUrl(String imei);

    List<AdBean> parseAdJsonListToAdBean(List<AdJsonInfo> adJsonInfoList);

}
