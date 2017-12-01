package box.lilei.box_client.client.presenter.impl;

import android.content.Context;
import android.widget.GridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.box.BoxParams;
import box.lilei.box_client.client.biz.GoodsBiz;
import box.lilei.box_client.client.biz.RoadBiz;
import box.lilei.box_client.client.biz.impl.GoodsBizImpl;
import box.lilei.box_client.client.adapter.GvMoreGoodsAdapter;
import box.lilei.box_client.client.biz.impl.RoadBizImpl;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.MyTime;
import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.client.presenter.MoreGoodsPresenter;
import box.lilei.box_client.client.view.MoreGoodsView;
import box.lilei.box_client.db.RoadBean;
import box.lilei.box_client.db.biz.RoadBeanService;
import box.lilei.box_client.util.SharedPreferencesUtil;
import box.lilei.box_client.util.TimeUtil;

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
