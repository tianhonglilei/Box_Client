package box.lilei.box_client.manager.presenter.impl;

import android.content.Context;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.box.BoxSetting;
import box.lilei.box_client.client.biz.RoadBiz;
import box.lilei.box_client.client.biz.impl.RoadBizImpl;
import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.client.model.RoadInfo;
import box.lilei.box_client.db.RoadBean;
import box.lilei.box_client.db.biz.RoadBeanService;
import box.lilei.box_client.manager.adapter.NavGoodsAdapter;
import box.lilei.box_client.manager.presenter.NavGoodsPresenter;
import box.lilei.box_client.manager.view.NavGoodsFragmentView;

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

    public NavGoodsPresenterImpl(Context mContext, NavGoodsFragmentView navGoodsFragmentView) {
        this.mContext = mContext;
        this.navGoodsFragmentView = navGoodsFragmentView;
        roadBiz = new RoadBizImpl();
        roadBeanService = new RoadBeanService(mContext, RoadBean.class);
    }

    @Override
    public void initGoodsGridView(GridView gridView) {
        roadGoodsList = roadBiz.parseRoadBeantoRoadAndGoods(roadBeanService.queryAllRoadBean());
        roadGoodsListMain = new ArrayList<>();
        roadGoodsListViceOne = new ArrayList<>();
        if (roadGoodsList.size() != 0) {
            for (RoadGoods roadGoods :
                    roadGoodsList) {
                RoadInfo roadInfo = roadGoods.getRoadInfo();
                if (roadInfo.getRoadBoxType() == Integer.parseInt(BoxSetting.BOX_TYPE_DRINK)) {
                    roadGoodsListMain.add(roadGoods);
                } else {
                    roadGoodsListViceOne.add(roadGoods);
                }
            }
            roadGoodsList = roadGoodsListMain;
            navGoodsAdapter = new NavGoodsAdapter(mContext, roadGoodsList, R.layout.nav_goods_gv_item);
            gridView.setAdapter(navGoodsAdapter);
            if (roadGoodsListViceOne.size()>0){
                navGoodsFragmentView.showViceOne();
            }
        }
    }

    @Override
    public void checkGoodsData(GridView gridView, String boxType) {
        if (boxType.equals(BoxSetting.BOX_TYPE_DRINK)) {
            navGoodsAdapter.setmDatas(roadGoodsListMain);
            navGoodsAdapter.notifyDataSetChanged();
        } else if (boxType.equals(BoxSetting.BOX_TYPE_FOOD)) {
            if (roadGoodsListViceOne.size() != 0){
                navGoodsAdapter.setmDatas(roadGoodsListViceOne);
                navGoodsAdapter.notifyDataSetChanged();
            }
        }
    }
}
