package box.lilei.box_client.manager.presenter;

import android.widget.GridView;

import box.lilei.box_client.client.model.RoadGoods;

/**
 * Created by lilei on 2017/11/14.
 */

public interface NavGoodsPresenter {

    void initGoodsGridView(GridView gridView);

    void checkGoodsData(GridView gridView,String boxType);

    /**
     * 补货
     * @param id
     * @param now
     * @param max
     */
    void updateGoodsNum(Long id,int now,int max);

    /**
     * 单独货道刷新数量
     * @param roadGoods
     */
    void refreshGoodsNum(RoadGoods roadGoods, int position);

    /**
     * 补满全部货道
     */
    void allRoadFull();

    void refreshAllRoadNum();

}
