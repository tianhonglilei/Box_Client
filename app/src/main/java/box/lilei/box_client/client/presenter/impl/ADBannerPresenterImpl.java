package box.lilei.box_client.client.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.box.BoxParams;
import box.lilei.box_client.client.adapter.GvHomeGoodsAdapter;
import box.lilei.box_client.client.adapter.LvAdImgAdapter;
import box.lilei.box_client.client.biz.AdBiz;
import box.lilei.box_client.client.biz.RoadBiz;
import box.lilei.box_client.client.biz.impl.AdBizImpl;
import box.lilei.box_client.client.biz.impl.RoadBizImpl;
import box.lilei.box_client.client.listener.OnADBannerLoadListener;
import box.lilei.box_client.client.model.ADInfo;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.MyTime;
import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.client.model.RoadInfo;
import box.lilei.box_client.client.presenter.ADBannerPresenter;
import box.lilei.box_client.client.view.ADBannerView;
import box.lilei.box_client.db.AdBean;
import box.lilei.box_client.db.GoodsBean;
import box.lilei.box_client.db.PercentBean;
import box.lilei.box_client.db.RoadBean;
import box.lilei.box_client.db.biz.AdBeanService;
import box.lilei.box_client.db.biz.GoodsBeanService;
import box.lilei.box_client.db.biz.PercentBeanService;
import box.lilei.box_client.db.biz.RoadBeanService;
import box.lilei.box_client.util.SharedPreferencesUtil;
import box.lilei.box_client.util.TimeUtil;

/**
 * Created by lilei on 2017/9/12.
 */

public class ADBannerPresenterImpl implements ADBannerPresenter, OnADBannerLoadListener {

    //首页界面View
    private ADBannerView adBannerView;

    private Context mContext;

    //广告缩略图数据
    private List<ADInfo> adInfoList;

    //广告缩略图适配器
    private LvAdImgAdapter lvAdImgAdapter;

    //底部商品适配器
    private GvHomeGoodsAdapter gvHomeGoodsAdapter;

    //商品数据
//    private List<Goods> goodsList;
    private List<RoadGoods> roadGoodsMainList;

    //广告类处理
    private AdBiz adBiz;
    //货道商品信息
    private RoadBiz roadBiz;
    //数据库处理
    private AdBeanService adBeanService;
    private RoadBeanService roadBeanService;
    private GoodsBeanService goodsBeanService;
    private PercentBeanService percentBeanService;


    public ADBannerPresenterImpl(ADBannerView adBannerView, Context mContext) {
        this.adBannerView = adBannerView;
        this.mContext = mContext;
//        initTestData();
        adBiz = new AdBizImpl();
        roadBiz = new RoadBizImpl();
        adBeanService = new AdBeanService(mContext, AdBean.class);
        roadBeanService = new RoadBeanService(mContext, RoadBean.class);
        goodsBeanService = new GoodsBeanService(mContext, GoodsBean.class);
        percentBeanService = new PercentBeanService(mContext, PercentBean.class);
    }


    @Override
    public void initAdData(ListView adbannerAdLv) {
        getAdInfoFromDB();
        lvAdImgAdapter = new LvAdImgAdapter(mContext, adInfoList, R.layout.client_adbanner_ad_item_img);
        adbannerAdLv.setAdapter(lvAdImgAdapter);
        if (adInfoList.size() > 0) {
            adBannerView.changeAD(adInfoList.get(0), 0);
        }
        adbannerAdLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adBannerView.changeAD(adInfoList.get(position), position);
            }
        });
        Log.e("ADBannerPresenterImpl", "adInfoList.size():" + adInfoList.size());
    }


    @Override
    public void initGoodsData(GridView adbannerGoodsGv) {
        getRoadInfoFromDB();
        int itemWidth = 128;
//        int itemWidth = 256;
        int size = roadGoodsMainList.size();//要显示数据的个数
        int allWidth = itemWidth * size;
        LinearLayout.LayoutParams params = new
                LinearLayout.LayoutParams(allWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        adbannerGoodsGv.setLayoutParams(params);
        adbannerGoodsGv.setStretchMode(GridView.NO_STRETCH);
        adbannerGoodsGv.setNumColumns(size);
        gvHomeGoodsAdapter = new GvHomeGoodsAdapter(mContext, roadGoodsMainList, R.layout.client_adbanner_goods_item);
        adbannerGoodsGv.setAdapter(gvHomeGoodsAdapter);
        adBannerView.hiddenDialog();
        adbannerGoodsGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RoadGoods roadGoods = roadGoodsMainList.get(position);
                if (roadGoods.getGoods().getGoodsSaleState() != Goods.SALE_STATE_OUT) {
                    adBannerView.navigateToPay(roadGoodsMainList.get(position));
                } else {
//                    Toast.makeText(mContext, "该商品已售罄，请选购其他商品", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void getAdInfoFromDB() {
        List<AdBean> adBeanList = adBeanService.queryAllAdBean();
        adInfoList = adBiz.parseAdBeanListToAdInfo(adBeanList);

    }

    public void getRoadInfoFromDB() {
        List<RoadBean> roadBeanList = roadBeanService.queryAllRoadBean();
        String leftState = SharedPreferencesUtil.getString(mContext, BoxParams.LEFT_STATE);
        String rightState = SharedPreferencesUtil.getString(mContext, BoxParams.RIGHT_STATE);
        List<RoadGoods> roadGoodsList = roadBiz.parseRoadBeanToRoadGoods(roadBeanList, leftState, rightState);

        roadGoodsMainList = roadGoodsList;

//        getMainShowGoods(roadGoodsList);
    }

    /**
     * 筛选一部分商品在首页展示
     *
     * @param list
     */
    public void getMainShowGoods(List<RoadGoods> list) {
        List<RoadGoods> list1 = new ArrayList<>();
        List<RoadGoods> list2 = new ArrayList<>();
        for (RoadGoods roadGoods :
                list) {
            RoadInfo roadInfo = roadGoods.getRoadInfo();
            if (roadInfo.getRoadNowNum() > 0 && roadInfo.getRoadState() == RoadInfo.ROAD_STATE_NORMAL && roadInfo.getRoadOpen() == RoadInfo.ROAD_OPEN) {
                list1.add(roadGoods);
            } else {
                list2.add(roadGoods);
            }
        }
        if (list1.size() > 25) {
            for (int i = 0; i < 25; i++) {
                roadGoodsMainList.add(list1.get(i));
            }
        } else if (list1.size() < 20) {
            int num = 20 - list1.size();
            for (RoadGoods roadGoods :
                    list1) {
                roadGoodsMainList.add(roadGoods);
            }
            for (int i = 0; i < num; i++) {
                roadGoodsMainList.add(list2.get(i));
            }
        } else {
            for (RoadGoods roadGoods :
                    list1) {
                roadGoodsMainList.add(roadGoods);
            }
        }
    }

    @Override
    public void deleteAdShow(int position) {
        adInfoList.remove(position);
        lvAdImgAdapter.setmDatas(adInfoList);
        lvAdImgAdapter.notifyDataSetChanged();
    }


}
