package com.zhang.box.client.biz.impl;

import com.zhang.box.client.biz.AdBiz;
import com.zhang.box.client.model.ADInfo;
import com.zhang.box.client.model.jsonmodel.AdJsonInfo;
import com.zhang.box.db.AdBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lilei on 2017/11/5.
 */

public class AdBizImpl implements AdBiz {
    private List<ADInfo> adInfoList;
    private List<AdBean> adBeanList;



    @Override
    public List<AdBean> parseAdJsonListToAdBean(List<AdJsonInfo> adJsonInfoList) {
        adBeanList = new ArrayList<>();
        for (AdJsonInfo adJson :
                adJsonInfoList) {
            AdBean adBean = new AdBean();
            adBean.setId(Long.parseLong(adJson.getAdv_id()));
            if(adJson.getVideo().trim().equals("")){
                adBean.setAdType(ADInfo.ADTYPE_IMG);
            }else{
                adBean.setAdType(ADInfo.ADTYPE_VIDEO);
                adBean.setAdVideoFile(adJson.getVideo());
            }
            adBean.setAdImgFile(adJson.getImg());
            adBeanList.add(adBean);
        }
        return adBeanList;
    }

    @Override
    public List<ADInfo> parseAdBeanListToAdInfo(List<AdBean> adBeanList) {
        adInfoList = new ArrayList<>();
        for (AdBean bean :
                adBeanList) {
            ADInfo adInfo = new ADInfo();
            adInfo.setAdId(bean.getId());
            adInfo.setAdType(bean.getAdType());
            if (bean.getAdType() == ADInfo.ADTYPE_VIDEO){
                adInfo.setVideoFileName(bean.getAdVideoFile());
            }
            adInfo.setImgFileName(bean.getAdImgFile());
            adInfoList.add(adInfo);
        }
        return adInfoList;
    }
}
