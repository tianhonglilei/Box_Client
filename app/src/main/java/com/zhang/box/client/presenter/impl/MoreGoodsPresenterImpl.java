package com.zhang.box.client.presenter.impl;

import android.content.Context;
import android.widget.GridView;

import com.zhang.box.R;
import com.zhang.box.box.BoxParams;
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
    private List<RoadGoods> drinks = new ArrayList<>();
    private List<RoadGoods> foods = new ArrayList<>();

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
            gvMoreGoodsAdapter.setmDatas(foods);
        } else {
        }
        gvMoreGoodsAdapter.notifyDataSetChanged();
    }


    public void getFoodAndDrink() {
        for (RoadGoods roadGoods :
                goodsList) {
            if (roadGoods.getGoods().getGoodsType() == Goods.GOODS_TYPE_DRINK) {
                drinks.add(roadGoods);
            } else {
                foods.add(roadGoods);
            }
        }
        if (foods.size() == 0){
            SharedPreferencesUtil.putString(mContext, BoxParams.HAVE_FOOD, "false");
        }else{
            SharedPreferencesUtil.putString(mContext, BoxParams.HAVE_FOOD, "true");
        }

    }

}
