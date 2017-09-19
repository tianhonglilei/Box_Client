package box.lilei.box_client.client.presenter.impl;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import box.lilei.box_client.BuildConfig;
import box.lilei.box_client.R;
import box.lilei.box_client.client.adapter.GvHomeGoodsAdapter;
import box.lilei.box_client.client.adapter.LvAdImgAdapter;
import box.lilei.box_client.client.listener.OnADBannerLoadListener;
import box.lilei.box_client.client.model.ADInfo;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.presenter.ADBannerPresenter;
import box.lilei.box_client.client.view.ADBannerView;

/**
 * Created by lilei on 2017/9/12.
 */

public class ADBannerPresenterImpl implements ADBannerPresenter, OnADBannerLoadListener{

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
        adBannerView.changeAD(adInfoList.get(0));
        adbannerAdLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adBannerView.changeAD(adInfoList.get(position));
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
                LinearLayout.LayoutParams(allWidth,LinearLayout.LayoutParams.MATCH_PARENT);
        adbannerGoodsGv.setLayoutParams(params);
        adbannerGoodsGv.setStretchMode(GridView.NO_STRETCH);
        adbannerGoodsGv.setNumColumns(size);
        gvHomeGoodsAdapter = new GvHomeGoodsAdapter(mContext, goodsList, R.layout.client_adbanner_goods_item);
        adbannerGoodsGv.setAdapter(gvHomeGoodsAdapter);

    }

    public void initTestData(){
        adInfoList = new ArrayList<>();
        goodsList = new ArrayList<>();
        ADInfo adInfo = null;
        Goods goods = null;
        for (int i = 0; i < 20; i++) {
            adInfo = new ADInfo();
            goods = new Goods();
            if(i%2==0){
                adInfo.setAdType(ADInfo.ADTYPE_IMG);
                goods.setGoodsName("饮品");
            }else{
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
    public void loadAdFail() {

    }

    @Override
    public void loadAdSuccess() {

    }

    @Override
    public void loadGoodsFail() {

    }

    @Override
    public void loadGoodsSuccess() {

    }
}
