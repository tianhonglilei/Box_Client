package com.zhang.box.client.presenter.impl;

import android.content.Context;
import android.widget.GridView;

import com.zhang.box.R;
import com.zhang.box.box.BoxParams;
import com.zhang.box.box.BoxSetting;
import com.zhang.box.client.biz.GoodsBiz;
import com.zhang.box.client.biz.RoadBiz;
import com.zhang.box.client.biz.impl.GoodsBizImpl;
import com.zhang.box.client.biz.impl.RoadBizImpl;
import com.zhang.box.client.model.Goods;
import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.presenter.MoreGoodsPresenter;
import com.zhang.box.client.view.MoreGoodsView;
import com.zhang.box.db.RoadBean;
import com.zhang.box.db.biz.RoadBeanService;
import com.zhang.box.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;


import com.zhang.box.client.adapter.GvMoreGoodsAdapter;

/**
 * Created by lilei on 2017/9/26.
 */

public class MoreGoodsPresenterImpl implements MoreGoodsPresenter {

    private GvMoreGoodsAdapter gvMoreGoodsAdapter;

    private List<RoadGoods> goodsList;
    private List<RoadGoods> drinks;
    private List<RoadGoods> foods;
    private List<RoadGoods> cards;

    private Context mContext;
    private GridView gridView;
    private GoodsBiz goodsBiz;
    private RoadBeanService roadBeanService;
    private RoadBiz roadBiz;

    private MoreGoodsView moreGoodsView;


    public MoreGoodsPresenterImpl(Context mContext, MoreGoodsView moreGoodsView) {
        this.moreGoodsView = moreGoodsView;
        this.mContext = mContext;
        goodsBiz = new GoodsBizImpl();
        roadBeanService = new RoadBeanService(mContext, RoadBean.class);
        roadBiz = new RoadBizImpl();
        drinks = new ArrayList<>();
        foods = new ArrayList<>();
        cards = new ArrayList<>();
    }

    @Override
    public void initAllGoods(GridView gridView) {
        this.gridView = gridView;
        String leftState = SharedPreferencesUtil.getString(mContext, BoxParams.LEFT_STATE);
        String rightState = SharedPreferencesUtil.getString(mContext, BoxParams.RIGHT_STATE);
        goodsList = roadBiz.parseRoadBeanToRoadGoods(roadBeanService.queryAllRoadBean(), leftState, rightState);
        getFoodAndDrink();
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
            gvMoreGoodsAdapter.setmDatas(cards);
        } else {
        }
        gvMoreGoodsAdapter.notifyDataSetChanged();
    }

    @Override
    public void finish() {
        goodsList = null;
        drinks = null;
        foods = null;
        cards = null;
        moreGoodsView = null;
        mContext = null;
        goodsBiz = null;
        roadBeanService = null;
    }


    public void getFoodAndDrink() {
        drinks.clear();
        cards.clear();
        for (RoadGoods roadGoods :
                goodsList) {
            if (roadGoods.getGoods().getGoodsType() == Goods.GOODS_TYPE_DRINK) {
                drinks.add(roadGoods);
            } else if (roadGoods.getGoods().getGoodsType() == Goods.GOODS_TYPE_FOOD) {
                foods.add(roadGoods);
            } else {
                cards.add(roadGoods);
            }
        }
        int c_num = 0, d_num = 0;
        goodsList.clear();
        for (int i = 0; i < drinks.size() + cards.size(); i++) {
            if (i % 2 == 0) {
                if (c_num < cards.size()) {
                    goodsList.add(cards.get(c_num));
                    c_num++;
                } else {
                    if (d_num < drinks.size()) {
                        goodsList.add(drinks.get(d_num));
                        d_num++;
                    }
                }
            } else {
                if (d_num < drinks.size()) {
                    goodsList.add(drinks.get(d_num));
                    d_num++;
                } else {
                    if (c_num < cards.size()) {
                        goodsList.add(cards.get(c_num));
                        c_num++;
                    }
                }
            }
        }

        if (cards.size() == 0) {
            SharedPreferencesUtil.putString(mContext, BoxParams.HAVE_FOOD, "false");
        } else {
            SharedPreferencesUtil.putString(mContext, BoxParams.HAVE_FOOD, "true");
        }

    }

}
