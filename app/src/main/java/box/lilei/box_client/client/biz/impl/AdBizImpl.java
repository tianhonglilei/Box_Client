package box.lilei.box_client.client.biz.impl;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.client.biz.AdBiz;
import box.lilei.box_client.client.model.ADInfo;
import box.lilei.box_client.client.model.jsonmodel.AdJsonInfo;
import box.lilei.box_client.client.okhttp.CommonOkHttpClient;
import box.lilei.box_client.client.okhttp.handler.OkHttpDisposeHandler;
import box.lilei.box_client.client.okhttp.listener.OkHttpDisposeListener;
import box.lilei.box_client.client.okhttp.request.CommonRequest;
import box.lilei.box_client.client.okhttp.request.RequestParams;
import box.lilei.box_client.contants.Constants;
import box.lilei.box_client.db.AdBean;

/**
 * Created by lilei on 2017/11/5.
 */

public class AdBizImpl implements AdBiz {
    private List<ADInfo> adInfoList;


    /**
     * 获取url广告
     * @param imei
     * @return
     */
    @Override
    public List<ADInfo> getAdInfoListFromUrl(String imei) {
        adInfoList = new ArrayList<>();
        RequestParams params = new RequestParams();
        params.put("machineid", imei);
        CommonOkHttpClient.post(CommonRequest.createPostRequest(Constants.BANNER_AD_URL, params), new OkHttpDisposeHandler(new OkHttpDisposeListener() {
            @Override
            public void onSuccess(Object responseObject) {

            }

            @Override
            public void onFail(Object errorObject) {

            }
        }));

        return adInfoList;
    }

    /**
     * 将json对象转化数据库对象
     * @param adJsonInfoList
     * @return
     */
    @Override
    public List<AdBean> parseAdJsonListToAdBean(List<AdJsonInfo> adJsonInfoList) {
        List<AdBean> adBeanList = new ArrayList<>();
        for (AdJsonInfo adJson :
                adJsonInfoList) {
            AdBean adBean = new AdBean();
            adBean.setId(Long.parseLong(adJson.getAdv_id()));
            if(adJson.getVideo()!=null || !adJson.getVideo().equals("")){
                adBean.setAdType(ADInfo.ADTYPE_VIDEO);
                adBean.setAdVideoFile(adJson.getVideo());
            }else{
                adBean.setAdType(ADInfo.ADTYPE_IMG);
            }
            adBean.setAdImgFile(adJson.getImg());
            adBean.setAdPath(Constants.DEMO_FILE_PATH);
        }
        return adBeanList;
    }
}
