package box.lilei.box_client.client.presenter.impl;

import android.content.Context;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.biz.GoodsBiz;
import box.lilei.box_client.biz.impl.GoodsBizImpl;
import box.lilei.box_client.client.adapter.GvMoreGoodsAdapter;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.presenter.MoreGoodsPresenter;

/**
 * Created by lilei on 2017/9/26.
 */

public class MoreGoodsPresenterImpl implements MoreGoodsPresenter {

    private GvMoreGoodsAdapter gvMoreGoodsAdapter;

    private List<Goods> goodsList;
    private List<Goods> drinks = new ArrayList<>();
    private List<Goods> foods = new ArrayList<>();

    private Context mContext;

    private GridView gridView;

    private GoodsBiz goodsBiz;


    public MoreGoodsPresenterImpl(Context mContext) {

        this.mContext = mContext;
        goodsBiz = new GoodsBizImpl();
    }

    @Override
    public void initAllGoods(GridView gridView) {
        this.gridView = gridView;
        goodsList = goodsBiz.getGoodsListInfo();
        testGoodsData();
        gvMoreGoodsAdapter = new GvMoreGoodsAdapter(mContext, goodsList, R.layout.client_more_goods_item);
        gridView.setAdapter(gvMoreGoodsAdapter);
    }

    @Override
    public void checkNav(int nav) {
        if (nav == 0) {
            gvMoreGoodsAdapter.setmDatas(goodsList);
        } else if (nav == 1) {
            gvMoreGoodsAdapter.setmDatas(drinks);
        } else if (nav == 2) {
            gvMoreGoodsAdapter.setmDatas(foods);
        } else {
        }
        gvMoreGoodsAdapter.notifyDataSetChanged();
    }


    public void testGoodsData() {
        Goods goods;
        for (int i = 0; i < goodsList.size(); i++) {
            goods = goodsList.get(i);
            if (goods.getGoodsType() == Goods.GOODS_TYPE_DRINK) {
                drinks.add(goods);
            } else if (goods.getGoodsType() == Goods.GOODS_TYPE_FOOD) {
                foods.add(goods);
            } else {

            }
        }

    }
}
