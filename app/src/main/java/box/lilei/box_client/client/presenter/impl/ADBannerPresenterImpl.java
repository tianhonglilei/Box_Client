package box.lilei.box_client.client.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.client.adapter.GvHomeGoodsAdapter;
import box.lilei.box_client.client.adapter.LvAdImgAdapter;
import box.lilei.box_client.client.listener.OnADBannerLoadListener;
import box.lilei.box_client.client.model.ADInfo;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.jsonmodel.AdJsonInfo;
import box.lilei.box_client.client.okhttp.CommonOkHttpClient;
import box.lilei.box_client.client.okhttp.exception.OkHttpException;
import box.lilei.box_client.client.okhttp.handler.OkHttpDisposeHandler;
import box.lilei.box_client.client.okhttp.listener.OkHttpDisposeListener;
import box.lilei.box_client.client.okhttp.request.CommonRequest;
import box.lilei.box_client.client.presenter.ADBannerPresenter;
import box.lilei.box_client.client.view.ADBannerView;
import box.lilei.box_client.contants.Constants;
import box.lilei.box_client.db.GoodsBean;
import box.lilei.box_client.db.PercentBean;
import box.lilei.box_client.db.RoadBean;
import box.lilei.box_client.util.JsonListUtil;

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
    private List<Goods> goodsList;


    public ADBannerPresenterImpl(ADBannerView adBannerView, Context mContext) {
        this.adBannerView = adBannerView;
        this.mContext = mContext;
        initTestData();
    }


    @Override
    public void initAdData(ListView adbannerAdLv) {

        lvAdImgAdapter = new LvAdImgAdapter(mContext, adInfoList, R.layout.client_adbanner_ad_item_img);
        adbannerAdLv.setAdapter(lvAdImgAdapter);
        adBannerView.changeAD(adInfoList.get(0), 0);
        adbannerAdLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adBannerView.changeAD(adInfoList.get(position), position);
            }
        });
    }


    @Override
    public void initGoodsData(GridView adbannerGoodsGv) {
        int itemWidth = 128;
//        int itemWidth = 256;
        int size = goodsList.size();//要显示数据的个数
        int allWidth = itemWidth * size;
        LinearLayout.LayoutParams params = new
                LinearLayout.LayoutParams(allWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        adbannerGoodsGv.setLayoutParams(params);
        adbannerGoodsGv.setStretchMode(GridView.NO_STRETCH);
        adbannerGoodsGv.setNumColumns(size);
        gvHomeGoodsAdapter = new GvHomeGoodsAdapter(mContext, goodsList, R.layout.client_adbanner_goods_item);
        adbannerGoodsGv.setAdapter(gvHomeGoodsAdapter);

    }

    public void initTestData() {
        adInfoList = new ArrayList<>();
        goodsList = new ArrayList<>();
        ADInfo adInfo;
        Goods goods;
        for (int i = 0; i < 20; i++) {
            adInfo = new ADInfo();
            goods = new Goods();
            if (i % 2 == 0) {
                adInfo.setAdType(ADInfo.ADTYPE_IMG);
                goods.setGoodsName("饮品");
            } else {
                adInfo.setAdType(ADInfo.ADTYPE_VIDEO);
                goods.setGoodsName("零食");
            }
            adInfoList.add(adInfo);
            goodsList.add(goods);
        }
    }


    @Override
    public void saveAdData() {

    }

    @Override
    public void saveGoodsData() {

    }

    @Override
    public void getAdInfoFromUrl(String imei) {
        StringBuilder url = new StringBuilder(Constants.BANNER_AD_URL);
        url.append("&machineid=" + imei);
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url.toString(), null), new OkHttpDisposeHandler(new OkHttpDisposeListener() {
            @Override
            public void onSuccess(Object responseObject) {
                JSONObject mainJson = JSONObject.parseObject(responseObject.toString());
                String urlString = "";
                JSONArray adJsonArray = null;
                JSONArray roadJsonArray = null;
                JSONArray goodsJsonArray = null;
                JSONObject percentJsonObject = null;
                if (mainJson != null) {
                    try {
                        urlString = mainJson.getString("img_url");
                        adJsonArray = mainJson.getJSONArray("adv");
                        roadJsonArray = mainJson.getJSONArray("huodao");
                        goodsJsonArray = mainJson.getJSONArray("machine_goods");
                        percentJsonObject = mainJson.getJSONObject("product_info");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (adJsonArray != null) {
                    List<AdJsonInfo> adJsonList = JsonListUtil.getListByArray(AdJsonInfo.class, adJsonArray.toString());
                    Log.e("ADBannerPresenterImpl", "adjsonList.size():" + adJsonList.size());
                }
                if (roadJsonArray != null) {
                    List<RoadBean> roadBeanList = JsonListUtil.getListByArray(RoadBean.class, roadJsonArray.toString());
                    Log.e("ADBannerPresenterImpl", "roadBeanList.size():" + roadBeanList.size());
                }
                if (goodsJsonArray != null) {
                    List<GoodsBean> goodsBeanList = JsonListUtil.getListByArray(GoodsBean.class, goodsJsonArray.toString());
                    if (goodsBeanList != null) {
                        Log.e("ADBannerPresenterImpl", "goodsBeanList.size():" + goodsBeanList.size());
                        List<List<PercentBean>> listPercentList = new ArrayList<List<PercentBean>>();
                        for (GoodsBean goodsBean :
                                goodsBeanList) {
                            if (goodsBean != null && goodsBean.getId() != null) {
                                JSONArray jsonArray = percentJsonObject.getJSONArray(goodsBean.getId().toString());
                                List<PercentBean> percentList = JsonListUtil.getListByArray(PercentBean.class, jsonArray.toString());
                                listPercentList.add(percentList);
                            }
                        }
                        Log.e("ADBannerPresenterImpl", "listPercentList.size():" + listPercentList.size());
                    }
                }
            }

            @Override
            public void onFail(Object errorObject) {
                Log.e("ADBannerPresenterImpl", "errorObject:" + ((OkHttpException) errorObject).getEmsg());
                ((Exception) errorObject).printStackTrace();
            }
        }));

    }


//    @Override
//    public void loadAdFail() {
//
//    }
//
//    @Override
//    public void loadAdSuccess() {
//
//    }
//
//    @Override
//    public void loadGoodsFail() {
//
//    }
//
//    @Override
//    public void loadGoodsSuccess() {
//
//    }
}
