package box.lilei.box_client.client.biz.impl;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.client.biz.PercentBiz;
import box.lilei.box_client.client.model.PercentInfo;
import box.lilei.box_client.db.PercentBean;

/**
 * Created by lilei on 2017/11/9.
 */

public class PercenteBizImpl implements PercentBiz {
    @Override
    public PercentInfo parseBeanToInfo(List<PercentBean> percentBeanList) {
        PercentInfo percentInfo = new PercentInfo();
        for (PercentBean bean :
                percentBeanList) {

            if(bean.getMaterial().equals("能量")){
                percentInfo.setEnergy(bean.getHundred()+"-"+bean.getPercentage());
            }else if(bean.getMaterial().equals("蛋白质")){
                percentInfo.setProtein(bean.getHundred()+"-"+bean.getPercentage());
            }else if(bean.getMaterial().equals("脂肪")){
                percentInfo.setFat(bean.getHundred()+"-"+bean.getPercentage());
            }else if(bean.getMaterial().equals("碳水化合物")){
                percentInfo.setcWater(bean.getHundred()+"-"+bean.getPercentage());
            }else if(bean.getMaterial().equals("钠")){
                percentInfo.setNa(bean.getHundred()+"-"+bean.getPercentage());
            }
        }
        return percentInfo;
    }
}
