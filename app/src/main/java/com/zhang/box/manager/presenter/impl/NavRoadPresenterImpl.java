package com.zhang.box.manager.presenter.impl;

import android.content.Context;
import android.widget.Toast;

import com.zhang.box.box.BoxAction;
import com.zhang.box.box.BoxParams;
import com.zhang.box.box.BoxSetting;
import com.zhang.box.client.biz.RoadBiz;
import com.zhang.box.client.biz.impl.RoadBizImpl;
import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.model.RoadInfo;
import com.zhang.box.db.RoadBean;
import com.zhang.box.db.biz.RoadBeanService;
import com.zhang.box.manager.presenter.NavRoadPresenter;
import com.zhang.box.manager.view.NavRoadFragmentView;
import com.zhang.box.util.SharedPreferencesUtil;
import com.zhang.box.util.ToastTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lilei on 2017/11/14.
 */

public class NavRoadPresenterImpl implements NavRoadPresenter {
    private Context mContext;
    private NavRoadFragmentView navRoadFragmentView;
    private RoadBiz roadBiz;
    private RoadBeanService roadBeanService;
    //货道信息
    private List<RoadGoods> roadGoodsList, roadGoodsListMain, roadGoodsListViceOne;

    public NavRoadPresenterImpl(Context mContext, NavRoadFragmentView navRoadFragmentView) {
        this.mContext = mContext;
        this.navRoadFragmentView = navRoadFragmentView;
        roadBiz = new RoadBizImpl();
        roadBeanService = new RoadBeanService(mContext, RoadBean.class);
    }

    @Override
    public void initRoadInfo() {
        String leftState = SharedPreferencesUtil.getString(mContext, BoxParams.LEFT_STATE);
        String rightState = SharedPreferencesUtil.getString(mContext, BoxParams.RIGHT_STATE);
        roadGoodsList = roadBiz.parseRoadBeanToRoadGoods(roadBeanService.queryAllRoadBean(), leftState, rightState);
        roadGoodsListMain = new ArrayList<>();
        roadGoodsListViceOne = new ArrayList<>();
        if (roadGoodsList.size() != 0) {
            for (RoadGoods roadGoods :
                    roadGoodsList) {
                RoadInfo roadInfo = roadGoods.getRoadInfo();
                if (roadInfo.getRoadBoxType().equals(BoxSetting.BOX_TYPE_DRINK)) {
                    roadGoodsListMain.add(roadGoods);
                } else {
                    roadGoodsListViceOne.add(roadGoods);
                }
            }
            roadGoodsList = roadGoodsListMain;
            navRoadFragmentView.initNavRoadGv(roadGoodsList);
            if (roadGoodsListViceOne.size() > 0) {
                navRoadFragmentView.showViceOne();
            }
        }
    }

    @Override
    public List<RoadGoods> checkRobot(String boxType) {
        if (boxType.equals(BoxSetting.BOX_TYPE_DRINK)) {
            return roadGoodsListMain;
        } else {
            return roadGoodsListViceOne;
        }
    }

    @Override
    public void testRoad(String boxType, String index) {
        int state = BoxAction.getRoadState(boxType, index);
        if (state == RoadInfo.ROAD_STATE_NORMAL) {
            BoxAction.outGoods(boxType, index, BoxAction.OUT_GOODS_TYPE_TEST);
        } else if (state == RoadInfo.ROAD_STATE_NULL) {
            Toast.makeText(mContext, index + "货道没有检测到货品", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "货道出现异常，请重启程序", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void clearRoad(String boxType, String index) {
        navRoadFragmentView.showLoading("出货中...");
        int i = 0;
        while (true) {
            int state = BoxAction.getRoadState(boxType, index);
            if (state == RoadInfo.ROAD_STATE_NORMAL) {
                if (BoxAction.outGoods(boxType, index, BoxAction.OUT_GOODS_TYPE_TEST)) {
                    i++;
                    ToastTools.showShort(mContext, "数量：" + i);
                }
                try {
                    Thread.sleep(1500);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (state == RoadInfo.ROAD_STATE_NULL) {
                ToastTools.showShort(mContext, index + "货道已清空");
                break;
            } else {
                Toast.makeText(mContext, "货道出现异常，请重启程序", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        navRoadFragmentView.hiddenLoading();
    }


}
