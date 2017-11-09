package box.lilei.box_client.client.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import box.lilei.box_client.client.biz.RoadBiz;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.client.model.RoadInfo;
import box.lilei.box_client.db.GoodsBean;
import box.lilei.box_client.db.RoadBean;

/**
 * Created by lilei on 2017/10/27.
 */

public class RoadBizImpl implements RoadBiz {
    @Override
    public List<RoadInfo> getRoadList() {
        List<RoadInfo> roadInfoList = new ArrayList<>();
        RoadInfo roadInfo;
        for (int i = 1; i < 22; i++) {
            roadInfo = new RoadInfo(Long.valueOf(i),0,0,21,new Random().nextInt(22));
            roadInfoList.add(roadInfo);
        }
        return roadInfoList;
    }

    @Override
    public List<RoadGoods> parseRoadBeantoRoadAndGoods(List<RoadBean> roadBeanList) {
        List<RoadGoods> roadGoodsList = new ArrayList<>();

        for (RoadBean bean:
             roadBeanList) {
            RoadGoods roadGoods = new RoadGoods();
            RoadInfo roadInfo = new RoadInfo();
            Goods goods = new Goods();
            GoodsBean goodsBean;
            roadInfo.setRoadIndex(bean.getHid());
            roadInfo.setRoadMaxNum(bean.getMax());
            roadInfo.setRoadNowNum(bean.getNowNum());
            roadInfo.setRoadBoxType(Integer.parseInt(bean.getHuogui_num()));
            //货道状态和开关
            roadInfo.setRoadState(RoadInfo.ROAD_STATE_NORMAL);
            roadInfo.setRoadOpen(RoadInfo.ROAD_OPEN);
            if (roadInfo.getRoadOpen() == RoadInfo.ROAD_OPEN
                    || roadInfo.getRoadState() == RoadInfo.ROAD_STATE_NORMAL){
                goods.setGoodsSaleState(Goods.SALE_STATE_NORMAL);
            }else{
                goods.setGoodsSaleState(Goods.SALE_STATE_OUT);
            }
            goods.setGoodsPrice(bean.getPrice()/100);
            goodsBean = bean.getGoodsBean();
            if (goodsBean != null) {
                goods.setGoodsId(goodsBean.getId());
                goods.setGoodsType(goodsBean.getType());
                if (roadInfo.getRoadBoxType() == RoadInfo.BOX_TYPE_DRINK) {
                    if (roadInfo.getRoadIndex() < 9) {
                        goods.setGoodsWd(Goods.GOODS_WD_HOT);
                    } else if (roadInfo.getRoadIndex() < 17) {
                        goods.setGoodsWd(Goods.GOODS_WD_COLD);
                    } else {
                        goods.setGoodsWd(Goods.GOODS_WD_NORMAL);
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
