package box.lilei.box_client.client.biz.impl;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.box.BoxAction;
import box.lilei.box_client.box.BoxParams;
import box.lilei.box_client.box.BoxSetting;
import box.lilei.box_client.client.biz.RoadBiz;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.client.model.RoadInfo;
import box.lilei.box_client.db.GoodsBean;
import box.lilei.box_client.db.RoadBean;
import box.lilei.box_client.util.SharedPreferencesUtil;

/**
 * Created by lilei on 2017/10/27.
 */

public class RoadBizImpl implements RoadBiz {

    @Override
    public List<RoadGoods> parseRoadBeanToRoadGoods(List<RoadBean> roadBeanList) {
        List<RoadGoods> roadGoodsList = new ArrayList<>();

        for (RoadBean bean:
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
            if (hgType == 9){
                roadInfo.setRoadBoxType(BoxSetting.BOX_TYPE_FOOD);
            }else{
                roadInfo.setRoadBoxType(BoxSetting.BOX_TYPE_DRINK);
            }
            //货道状态和开关
            roadInfo.setRoadState(BoxAction.getRoadState(roadInfo.getRoadBoxType(),roadInfo.getRoadIndex().toString()));
            roadInfo.setRoadOpen(RoadInfo.ROAD_OPEN);
            if (roadInfo.getRoadOpen() == RoadInfo.ROAD_OPEN
                    && roadInfo.getRoadState() == RoadInfo.ROAD_STATE_NORMAL){
                goods.setGoodsSaleState(Goods.SALE_STATE_NORMAL);
            }else{
                goods.setGoodsSaleState(Goods.SALE_STATE_OUT);
            }
            goods.setGoodsPrice((double) bean.getPrice()/100);
            goodsBean = bean.getGoodsBean();
            if (goodsBean != null) {
                goods.setGoodsId(goodsBean.getId());
                goods.setGoodsType(goodsBean.getType());
                if (roadInfo.getRoadBoxType().equals(RoadInfo.BOX_TYPE_DRINK)) {
                    BoxParams boxParams = new BoxParams();
                    String leftState = boxParams.getLeft_state();
                    String rightState = boxParams.getRight_state();
                    if (roadInfo.getRoadIndex()<9){
                        goods.setGoodsWd(Integer.parseInt(leftState));
                    }else{
                        goods.setGoodsWd(Integer.parseInt(rightState));
                    }
                } else {
                    goods.setGoodsWd(Goods.GOODS_WD_NORMAL);
                }
                goods.setGoodsName(goodsBean.getName());
                goods.setGoodsBImgName(goodsBean.getBig_img());
                goods.setGoodsSImgName(goodsBean.getSmall_img());
                goods.setGoodsOutImgName(goodsBean.getNo_pro_img());

                roadGoods.setRoadInfo(roadInfo);
                roadGoods.setGoods(goods);
                roadGoodsList.add(roadGoods);
            }

        }

        return roadGoodsList;
    }

}
