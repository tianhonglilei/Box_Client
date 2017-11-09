package box.lilei.box_client.client.biz.impl;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.client.biz.GoodsBiz;
import box.lilei.box_client.client.model.Goods;

/**
 * Created by lilei on 2017/10/27.
 */

public class GoodsBizImpl implements GoodsBiz {



    @Override
    public List<Goods> getGoodsListInfo() {
        List<Goods> goodsList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Goods goods = new Goods();
            goods.setGoodsName("雪碧");
            goods.setGoodsMemo("清爽碳酸饮品");
            goods.setGoodsPrice(2.5);
            goods.setGoodsSaleState(Goods.SALE_STATE_DISCOUNT);
            goods.setGoodsDiscountPrice(2);
            goods.setGoodsType(Goods.GOODS_TYPE_DRINK);
            goods.setGoodsWd(Goods.GOODS_WD_COLD);
            goods.setGoodsTaste("听装");
            goodsList.add(goods);
            Goods goods1 = new Goods();
            goods1.setGoodsName("雪碧");
            goods1.setGoodsMemo("清爽碳酸饮品");
            goods1.setGoodsPrice(5);
            goods1.setGoodsSaleState(Goods.SALE_STATE_DISCOUNT);
            goods1.setGoodsDiscountPrice(4.5);
            goods1.setGoodsType(Goods.GOODS_TYPE_DRINK);
            goods1.setGoodsWd(Goods.GOODS_WD_HOT);
            goods1.setGoodsTaste("听装");
            goodsList.add(goods1);
            Goods goods2 = new Goods();
            goods2.setGoodsName("雪碧");
            goods2.setGoodsMemo("清爽碳酸饮品");
            goods2.setGoodsPrice(2.5);
            goods2.setGoodsSaleState(Goods.SALE_STATE_NORMAL);
            goods2.setGoodsType(Goods.GOODS_TYPE_DRINK);
            goods2.setGoodsWd(Goods.GOODS_WD_COLD);
            goods2.setGoodsTaste("听装");
            goodsList.add(goods2);
            Goods goods3 = new Goods();
            goods3.setGoodsName("维利多");
            goods3.setGoodsMemo("清爽碳酸饮品");
            goods3.setGoodsPrice(5);
            goods3.setGoodsSaleState(Goods.SALE_STATE_OUT);
            goods3.setGoodsType(Goods.GOODS_TYPE_FOOD);
            goods3.setGoodsTaste("听装");
            goodsList.add(goods3);
            Goods goods4 = new Goods();
            goods4.setGoodsName("维利多");
            goods4.setGoodsMemo("清爽碳酸饮品");
            goods4.setGoodsPrice(5.5);
            goods4.setGoodsSaleState(Goods.SALE_STATE_DISCOUNT);
            goods4.setGoodsDiscountPrice(3.5);
            goods4.setGoodsType(Goods.GOODS_TYPE_FOOD);
            goods4.setGoodsTaste("听装");
            goodsList.add(goods4);
        }
        return goodsList;
    }
}
