package com.zhang.box.client.biz.impl;

import com.zhang.box.box.BoxAction;
import com.zhang.box.box.BoxSetting;
import com.zhang.box.client.biz.RoadBiz;
import com.zhang.box.client.model.Goods;
import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.model.RoadInfo;
import com.zhang.box.db.GoodsBean;
import com.zhang.box.db.RoadBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lilei on 2017/10/27.
 */

public class RoadBizImpl implements RoadBiz {

    @Override
    public List<RoadGoods> parseRoadBeanToRoadGoods(List<RoadBean> roadBeanList, String leftState, String rightState) {
        List<RoadGoods> roadGoodsList = new ArrayList<>();
        for (RoadBean bean :
                roadBeanList) {
            RoadGoods roadGoods = new RoadGoods();
            RoadInfo roadInfo = new RoadInfo();
            Goods goods = new Goods();
            GoodsBean goodsBean;
            roadGoods.setRoadGoodsId(bean.getId());
            roadInfo.setRoadIndex(bean.getHid());
            roadInfo.setRoadMaxNum(bean.getMax());
            roadInfo.setRoadNowNum(bean.getHuodao_num());
            int hgType = Integer.parseInt(bean.getHuogui_num());
            if (hgType == 9) {
                roadInfo.setRoadBoxType(BoxSetting.BOX_TYPE_FOOD);
            } else {
                roadInfo.setRoadBoxType(BoxSetting.BOX_TYPE_DRINK);
            }
            //货道状态和开关
            roadInfo.setRoadState(BoxAction.getRoadState(roadInfo.getRoadBoxType(), roadInfo.getRoadIndex().toString()));
            if (roadInfo.getRoadNowNum() == 0){
                roadInfo.setRoadOpen(RoadInfo.ROAD_CLOSE);
            }else{
                roadInfo.setRoadOpen(RoadInfo.ROAD_OPEN);
            }
            if (roadInfo.getRoadOpen() == RoadInfo.ROAD_OPEN
                    && roadInfo.getRoadState() == RoadInfo.ROAD_STATE_NORMAL) {
                goods.setGoodsSaleState(Goods.SALE_STATE_NORMAL);
            } else {
                goods.setGoodsSaleState(Goods.SALE_STATE_OUT);
            }
            goods.setGoodsPrice((double) bean.getPrice() / 100);
            goodsBean = bean.getGoodsBean();
            if (goodsBean != null) {
                goods.setGoodsId(goodsBean.getId());
                goods.setGoodsType(goodsBean.getType());
                if (roadInfo.getRoadBoxType().equals(BoxSetting.BOX_TYPE_DRINK)) {
                    if (Integer.parseInt(roadInfo.getRoadIndex().toString()) < 9) {
//                        Log.e("RoadBizImpl", "Integer.parseInt(leftState):" + Integer.parseInt(leftState));
                        goods.setGoodsWd(Integer.parseInt(leftState));
                    } else {
//                        Log.e("RoadBizImpl", "Integer.parseInt(rightState):" + Integer.parseInt(rightState));
                        goods.setGoodsWd(Integer.parseInt(rightState));
                    }
                } else {
                    goods.setGoodsWd(Goods.GOODS_WD_NORMAL);
                }
                goods.setGoodsName(goodsBean.getName());
                goods.setGoodsBImgName(goodsBean.getBig_img());
                goods.setGoodsSImgName(goodsBean.getSmall_img());
                goods.setGoodsOutImgName(goodsBean.getNo_pro_img());
                goods.setGoodsMemo(goodsBean.getDes1());

                roadGoods.setRoadInfo(roadInfo);
                roadGoods.setGoods(goods);
                roadGoodsList.add(roadGoods);
            }

        }

        return roadGoodsList;
    }

}
