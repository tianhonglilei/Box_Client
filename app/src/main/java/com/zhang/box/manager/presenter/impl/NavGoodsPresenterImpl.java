package com.zhang.box.manager.presenter.impl;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.zhang.box.box.BoxSetting;
import com.zhang.box.client.biz.RoadBiz;
import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.okhttp.CommonOkHttpClient;
import com.zhang.box.client.okhttp.handler.DisposeDataHandle;
import com.zhang.box.client.okhttp.listener.DisposeDataListener;
import com.zhang.box.client.okhttp.request.RequestParams;
import com.zhang.box.manager.adapter.NavGoodsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import box.lilei.box_client.R;
import com.zhang.box.box.BoxParams;
import com.zhang.box.client.biz.impl.RoadBizImpl;
import com.zhang.box.client.model.RoadInfo;
import com.zhang.box.client.model.paramsmodel.AddGoods;
import com.zhang.box.client.okhttp.request.CommonRequest;
import com.zhang.box.contants.Constants;
import com.zhang.box.db.RoadBean;
import com.zhang.box.db.biz.RoadBeanService;
import com.zhang.box.manager.presenter.NavGoodsPresenter;
import com.zhang.box.manager.view.NavGoodsFragmentView;
import com.zhang.box.util.ParamsUtils;
import com.zhang.box.util.SharedPreferencesUtil;


/**
 * Created by lilei on 2017/11/14.
 */

public class NavGoodsPresenterImpl implements NavGoodsPresenter {
    private Context mContext;
    private NavGoodsFragmentView navGoodsFragmentView;
    private List<RoadGoods> roadGoodsList, roadGoodsListMain, roadGoodsListViceOne;
    private RoadBiz roadBiz;
    private RoadBeanService roadBeanService;
    private NavGoodsAdapter navGoodsAdapter;

    //补货统计
    int count = 0;
    long index = 0;
    int success = 0;
    int fail = 0;

    public NavGoodsPresenterImpl(Context mContext, NavGoodsFragmentView navGoodsFragmentView) {
        this.mContext = mContext;
        this.navGoodsFragmentView = navGoodsFragmentView;
        roadBiz = new RoadBizImpl();
        roadBeanService = new RoadBeanService(mContext, RoadBean.class);
    }

    @Override
    public void initGoodsGridView(GridView gridView) {
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
            navGoodsAdapter = new NavGoodsAdapter(mContext, roadGoodsList, R.layout.nav_goods_gv_item);
            gridView.setAdapter(navGoodsAdapter);
            if (roadGoodsListViceOne.size() > 0) {
                navGoodsFragmentView.showViceOne();
            }
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    navGoodsFragmentView.showInputDialog(roadGoodsList.get(position), position);
                }
            });
        }
    }

    @Override
    public void checkGoodsData(GridView gridView, String boxType) {
        if (boxType.equals(BoxSetting.BOX_TYPE_DRINK)) {
            roadGoodsList = roadGoodsListMain;
            refreshAllRoadNum();
        } else if (boxType.equals(BoxSetting.BOX_TYPE_FOOD)) {
            if (roadGoodsListViceOne.size() != 0) {
                roadGoodsList = roadGoodsListViceOne;
                refreshAllRoadNum();
            }
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navGoodsFragmentView.showInputDialog(roadGoodsList.get(position), position);
            }
        });
    }

    @Override
    public void updateGoodsNum(Long id, int now, int max) {
        roadBeanService.updateRoadNum(id, now, max);
    }

    @Override
    public void refreshGoodsNum(RoadGoods roadGoods, int mPosition) {
        roadGoodsList.set(mPosition, roadGoods);
        navGoodsAdapter.setmDatas(roadGoodsList);
        navGoodsAdapter.notifyDataSetChanged();
    }

    @Override
    public void allRoadFull() {
        navGoodsFragmentView.showLoading("一键补满");
        count = roadGoodsList.size();
        success = 0;
        fail = 0;
        for (int i = 0; i < roadGoodsList.size(); i++) {
            RoadGoods roadGoods = roadGoodsList.get(i);
            RoadInfo roadInfo = roadGoods.getRoadInfo();
            roadInfo.setRoadNowNum(roadInfo.getRoadMaxNum());
            roadGoods.setRoadInfo(roadInfo);
            roadGoodsList.set(i, roadGoods);
            AddGoods addGoods = new AddGoods();
            addGoods.setHgid(roadInfo.getRoadBoxType());
            addGoods.setHid(roadInfo.getRoadIndex().toString());
            addGoods.setMachineid(BoxSetting.BOX_TEST_ID);
            addGoods.setPid(roadGoods.getGoods().getGoodsId().toString());
            addGoods.setHuodao_max("" + roadInfo.getRoadMaxNum());
            addGoods.setHuodao_num("" + roadInfo.getRoadMaxNum());
            goodsAllToUrl(addGoods, roadGoods.getRoadGoodsId());
        }
    }

    @Override
    public void refreshAllRoadNum() {
        navGoodsAdapter.setmDatas(roadGoodsList);
        navGoodsAdapter.notifyDataSetChanged();
    }


    /**
     * 一键补满访问网络
     *
     * @param addGoods
     * @param dbId
     */
    @Override
    public void goodsAllToUrl(final AddGoods addGoods, final Long dbId) {
        Map<String, String> params = ParamsUtils.goodsNumAdd(addGoods);
        CommonOkHttpClient.post(CommonRequest.createPostRequest(Constants.GOODS_NUM_ADD, new RequestParams(params)),
                new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObject) {
                        JSONObject jsonObject = JSONObject.parseObject((String) responseObject);
                        if (jsonObject.get("msg").equals("0")) {
                            index = Long.parseLong(addGoods.getHid());
                            navGoodsFragmentView.fullProgress(count, index, ++success, fail);
                            roadBeanService.updateRoadNum(dbId, Integer.parseInt(addGoods.getHuodao_num()), Integer.parseInt(addGoods.getHuodao_max()));
                        } else {
                            navGoodsFragmentView.fullProgress(count, index, success, ++fail);
                        }
                    }

                    @Override
                    public void onFail(Object errorObject) {
                        navGoodsFragmentView.fullProgress(count, index, success, ++fail);
                    }
                }));
    }

    @Override
    public void goodsOneToUrl(final AddGoods addGoods, final RoadGoods roadGoods, final int position) {
        Map<String, String> params = ParamsUtils.goodsNumAdd(addGoods);
        CommonOkHttpClient.post(CommonRequest.createPostRequest(Constants.GOODS_NUM_ADD, new RequestParams(params)),
                new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObject) {
                        JSONObject jsonObject = JSONObject.parseObject((String) responseObject);
                        if (jsonObject.get("msg").equals("0")) {
                            roadBeanService.updateRoadNum(roadGoods.getRoadGoodsId(), Integer.parseInt(addGoods.getHuodao_num()), Integer.parseInt(addGoods.getHuodao_max()));
                            refreshGoodsNum(roadGoods, position);
                            Toast.makeText(mContext, "补货完成", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "补货失败,请重新尝试", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFail(Object errorObject) {
                        Toast.makeText(mContext, "补货失败,请检查网络", Toast.LENGTH_SHORT).show();
                    }
                }));
    }


}
