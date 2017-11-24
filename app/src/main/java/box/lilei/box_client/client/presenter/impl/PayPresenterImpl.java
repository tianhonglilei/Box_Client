package box.lilei.box_client.client.presenter.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;


import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import box.lilei.box_client.box.BoxAction;
import box.lilei.box_client.client.biz.PercentBiz;
import box.lilei.box_client.client.biz.impl.PercenteBizImpl;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.MyTime;
import box.lilei.box_client.client.model.PercentInfo;
import box.lilei.box_client.client.model.RoadInfo;
import box.lilei.box_client.client.okhttp.CommonOkHttpClient;
import box.lilei.box_client.client.okhttp.handler.DisposeDataHandle;
import box.lilei.box_client.client.okhttp.listener.DisposeDataListener;
import box.lilei.box_client.client.okhttp.request.CommonRequest;
import box.lilei.box_client.client.okhttp.request.RequestParams;
import box.lilei.box_client.client.presenter.PayPresenter;
import box.lilei.box_client.client.view.PayView;
import box.lilei.box_client.contants.Constants;
import box.lilei.box_client.db.PercentBean;
import box.lilei.box_client.db.biz.PercentBeanService;
import box.lilei.box_client.util.MyStringUtil;
import box.lilei.box_client.util.ParamsUtils;
import box.lilei.box_client.util.QRCodeUtil;
import box.lilei.box_client.util.TimeUtil;

/**
 * Created by lilei on 2017/11/9.
 */

public class PayPresenterImpl implements PayPresenter {
    private Context mContext;
    private PayView payView;
    private PercentBeanService percentBeanService;
    private PercentBiz percentBiz;


    public PayPresenterImpl(Context mContext, PayView payView) {
        this.mContext = mContext;
        this.payView = payView;
        percentBeanService = new PercentBeanService(mContext, PercentBean.class);
        percentBiz = new PercenteBizImpl();
    }

    @Override
    public void initPercenterInfo(Long goodsId) {
        PercentInfo percentInfo = percentBiz.parseBeanToInfo(percentBeanService.getPercentBeanListByGoodsId(goodsId));
        payView.showPercentInfo(percentInfo);
    }

    @Override
    public void getQRCode(String url,double price, final int payType, int payNum, Goods goods, RoadInfo roadInfo) {
        payView.loadQRCode();
        //获取二维码所需参数
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Long goodsId = goods.getGoodsId();
        String box_id = BoxAction.getBoxIdFromSP(mContext);
        Long roadIndex = roadInfo.getRoadIndex();
        String boxType = roadInfo.getRoadBoxType();
        String tradeno = now + "," + goodsId + ","
                + box_id + "," + roadIndex + ","
                + boxType;
        String goodsName = goods.getGoodsName();
        String title = goodsName+"x"+payNum;
        String des = "商品"+goodsName+payNum+"份，共"+price+"元";
        String subject = des + "|" + goodsId + "|"
                + roadIndex + "|" + box_id + "|"
                + boxType;

        Map<String, String> params;
        if (payType == Constants.PAY_TYPE_WX) {
            params = ParamsUtils.wxGetQRParams(Double.toString(price), des, tradeno, subject);
        } else if (payType == Constants.PAY_TYPE_ALI) {
            params = ParamsUtils.aliGetQRParams(tradeno, Double.toString(price), title, des);
        }else{
            params = new HashMap<>();
        }
        CommonOkHttpClient.get(CommonRequest.createPostRequest(url, new RequestParams(params)), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObject) {
                JSONObject jsonObject = JSONObject.parseObject((String) responseObject);
                Log.e("PayPresenterImpl", "jsonObject:" + jsonObject);
                String url ="";
                if (payType == Constants.PAY_TYPE_WX){
                    if (jsonObject.getString("error").equals("0")){
                        url = jsonObject.getString("url");
                    }
                }else {
                    if (jsonObject.getString("err").equals("0")){
                        url = jsonObject.getString("url");
                    }
                }
                Bitmap bitmap;
                if (url!=null && !url.equals("")){
                    bitmap = QRCodeUtil.createQRImage(url);
                }else{
                    bitmap = null;
                }
                payView.showQRCode(bitmap);
            }

            @Override
            public void onFail(Object errorObject) {
                payView.showQRCode(null);
            }
        }));
    }


}
