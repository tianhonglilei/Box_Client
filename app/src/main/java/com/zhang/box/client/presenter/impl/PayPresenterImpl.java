package com.zhang.box.client.presenter.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;


import com.alibaba.fastjson.JSONObject;
import com.zhang.box.box.BoxAction;
import com.zhang.box.box.BoxParams;
import com.zhang.box.box.BoxSetting;
import com.zhang.box.client.biz.PercentBiz;
import com.zhang.box.client.biz.impl.PercenteBizImpl;
import com.zhang.box.client.model.Goods;
import com.zhang.box.client.model.OrderInfo;
import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.model.RoadInfo;
import com.zhang.box.client.okhttp.CommonOkHttpClient;
import com.zhang.box.client.okhttp.exception.OkHttpException;
import com.zhang.box.client.okhttp.handler.DisposeDataHandle;
import com.zhang.box.client.okhttp.listener.DisposeDataListener;
import com.zhang.box.client.okhttp.request.CommonRequest;
import com.zhang.box.client.okhttp.request.RequestParams;
import com.zhang.box.client.presenter.PayPresenter;
import com.zhang.box.client.view.PayView;
import com.zhang.box.contants.Constants;
import com.zhang.box.db.PercentBean;
import com.zhang.box.db.RoadBean;
import com.zhang.box.db.biz.PercentBeanService;
import com.zhang.box.db.biz.RoadBeanService;
import com.zhang.box.util.MyStringUtil;
import com.zhang.box.util.QRCodeUtil;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.zhang.box.client.model.PercentInfo;
import com.zhang.box.util.ParamsUtils;
import com.zhang.box.util.SharedPreferencesUtil;

/**
 * Created by lilei on 2017/11/9.
 */

public class PayPresenterImpl implements PayPresenter {
    private Context mContext;
    private PayView payView;
    private PercentBeanService percentBeanService;
    private PercentBiz percentBiz;
    private OrderInfo orderInfo;

    //微信识别号
    private String weixinno1, weixinno2;
    //支付宝识别号
    private String tradeno1, tradeno2;
    //货道
    private String boxType;
    //货柜
    private Long roadIndex;

    private RequestParams requestParams;

    private String url = Constants.WX_GET_PAY_RESPONSE;

    //    private boolean isStart;
    private Map<String, String> params;

    private RoadBeanService roadBeanService;

    private int num, payType;

    private Handler mHandler;


    public PayPresenterImpl(Context mContext, PayView payView, Handler handler) {
        this.mContext = mContext;
        this.payView = payView;
        mHandler = handler;
        percentBeanService = new PercentBeanService(mContext, PercentBean.class);
        percentBiz = new PercenteBizImpl();
        orderInfo = new OrderInfo();
        roadBeanService = new RoadBeanService(mContext, RoadBean.class);
    }

    @Override
    public void initPercenterInfo(Long goodsId) {
        PercentInfo percentInfo = percentBiz.parseBeanToInfo(percentBeanService.getPercentBeanListByGoodsId(goodsId));
        payView.showPercentInfo(percentInfo);
    }


    @Override
    public void getQRCode(String url, double price, final int payType, final int payNum, RoadGoods roadGoods) {
        payView.loadQRCode();
        Goods goods = roadGoods.getGoods();
        RoadInfo roadInfo = roadGoods.getRoadInfo();
        //获取二维码所需参数
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Long goodsId = goods.getGoodsId();
        orderInfo.setId(roadGoods.getRoadGoodsId().toString());
        orderInfo.setPid(goodsId.toString());
        orderInfo.setPrice(price + "");
        String box_id = BoxAction.getBoxIdFromSP(mContext);
        orderInfo.setImei(box_id);
        roadIndex = roadInfo.getRoadIndex();
        orderInfo.setHdid(roadInfo.getRoadIndex() + "");
        boxType = roadInfo.getRoadBoxType();
        orderInfo.setHgid(roadInfo.getRoadBoxType());
        final String tradeno = now + "," + goodsId + ","
                + box_id + "," + roadIndex + ","
                + boxType;
        String goodsName = goods.getGoodsName();
        orderInfo.setName(goodsName);
        String title = goodsName + "x" + payNum;
        String des = "商品" + goodsName + payNum + "份，共" + price + "元";
        String company = SharedPreferencesUtil.getString(mContext, BoxParams.COMPANY);
        String subject = des + "|" + goodsId + "|"
                + roadIndex + "|" + box_id + "|"
                + boxType + "|" + company;

        final String mchTradeNo = MyStringUtil.getRandonInt(20);
        Map<String, String> params;
        if (payType == Constants.PAY_TYPE_WX) {
            //微信二维码
            params = ParamsUtils.wxGetQRParams(Double.toString(price), des, mchTradeNo, subject);
        } else if (payType == Constants.PAY_TYPE_ALI) {
            //支付宝二维码
            params = ParamsUtils.aliGetQRParams(tradeno, Double.toString(price), title, des, company);
        } else {
            params = new HashMap<>();
        }
        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, new RequestParams(params)), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObject) {
                JSONObject jsonObject = JSONObject.parseObject((String) responseObject);
//                Log.e("PayPresenterImpl", "jsonObject:" + jsonObject);
                String imgUrl = "";
                if (payType == Constants.PAY_TYPE_WX) {
                    if (jsonObject.getString("error").equals("0")) {
                        if (payNum == 1) {
                            weixinno1 = jsonObject.getString("tradeno");
                        } else if (payNum == 2) {
                            weixinno2 = jsonObject.getString("tradeno");
                        }
                    }
                } else {
                    if (jsonObject.getString("err").equals("0")) {
                        if (payNum == 1) {
                            tradeno1 = tradeno;
                        } else if (payNum == 2) {
                            tradeno2 = tradeno;
                        }
                    }
                }
                imgUrl = jsonObject.getString("url");
                Bitmap bitmap;
                if (!TextUtils.isEmpty(imgUrl)) {
                    bitmap = QRCodeUtil.createQRImage(imgUrl);
//                    if (!isStart) {
//                        chengePayRequest(payNum, payType);
////                        getPayResponse(payType, payNum);
//                        isStart = true;
//                    }
                } else {
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

    @Override
    public void postOrder(int orderNum, int outNum) {
        payView.hiddenDialog();
        if (orderNum == outNum) {
            payView.showPopwindow(true, orderNum, outNum);
        } else {
            payView.showPopwindow(false, orderNum, outNum);
        }
//        sendOrder(orderNum, outNum);
//        updateDBNum(Long.parseLong(orderInfo.getId()),outNum);
    }


    @Override
    public void cancelOrder() {
        orderInfo.setCancel(true);
    }


    /**
     * 改变支付方式
     *
     * @param num
     * @param payType
     */
    @Override
    public void chengePayRequest(int num, int payType) {
        this.num = num;
        this.payType = payType;
        String tradeno;
        if (payType == Constants.PAY_TYPE_WX) {
            url = Constants.WX_GET_PAY_RESPONSE;
            if (num == 1) {
                tradeno = weixinno1;
            } else {
                tradeno = weixinno2;
            }
        } else {
            url = Constants.ALI_GET_PAY_RESPONSE;
            if (num == 1) {
                tradeno = tradeno1;
            } else {
                tradeno = tradeno2;
            }
        }
//        Log.e("PayPresenterImpl:" , payType + "-" + num + tradeno);
        params = ParamsUtils.getPayResponseParams(tradeno, payType);
        requestParams = new RequestParams(params);
        getPayResponse(payType, num);
    }

    @Override
    public void cancelRequest() {
//        if (task != null) {
//            task.cancel();
//            task = null;
//        }
//        if (timer != null) {
//            timer.cancel();
//        }
    }


    /**
     * 支付结果查询
     *
     * @param payType
     */
    private void getPayResponse(final int payType, final int num) {
        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, requestParams), new DisposeDataHandle(new DisposeDataListener() {

            @Override
            public void onSuccess(Object responseObject) {
                JSONObject jsonObject = JSONObject.parseObject((String) responseObject);
                Log.e("PayPresenterImpl", "responseObject:" + responseObject);
                if (jsonObject.getString("error").equals("0")) {
                    if (!orderInfo.isPayState()) {
                        orderInfo.setPayType(payType);
                        orderInfo.setOrderNum(num);
                        payView.cancelRequest();
                        payView.showDialog("出货中...");
                        orderInfo.setPayState(true);
                        outGoodsAction(num, boxType, roadIndex + "");
                    }
                } else if (jsonObject.getString("error").equals("-1")) {
                    if (orderInfo.isPayState() || orderInfo.isCancel()) {
                        payView.cancelRequest();
                    }
                }
            }

            @Override
            public void onFail(Object errorObject) {
                if (errorObject instanceof OkHttpException) {
                    ((OkHttpException) errorObject).getEmsg();
                }
                ((Exception) errorObject).printStackTrace();
            }
        }));
    }

    int nextTime;

    public void outGoodsAction(final int num, final String boxType, final String roadIndex) {
        payView.outGoodsCheck(num);
        if (boxType.equals(BoxSetting.BOX_TYPE_DRINK)) {
            nextTime = 2500;
        } else {
            nextTime = 6000;
        }
        for (int i = 0; i < num; i++) {
            if (i == 1) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(nextTime);
                            BoxAction.outGoods(boxType, roadIndex, BoxAction.OUT_GOODS_TYPE_PAY);
                            if (boxType.equals(BoxSetting.BOX_TYPE_FOOD)) {
                                mHandler.sendEmptyMessage(3);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                BoxAction.outGoods(boxType, roadIndex, BoxAction.OUT_GOODS_TYPE_PAY);
                if (boxType.equals(BoxSetting.BOX_TYPE_FOOD)) {
                    if (num == 1) {
                        postOrder(num, 1);
                    }
                }
            }
        }
    }


    /**
     * 上传订单信息
     *
     * @param orderNum
     * @param outNum
     */
    private void sendOrder(int orderNum, int outNum) {
        String tradeno;
        orderInfo.setOutGoodsNum(outNum);
        int payType = orderInfo.getPayType();
        if (payType == Constants.PAY_TYPE_WX) {
            url = Constants.WX_SEND_ORDER;
            if (orderNum == 1) {
                tradeno = weixinno1;
            } else {
                tradeno = weixinno2;
            }
        } else {
            url = Constants.ALI_SEND_ORDER;
            if (orderNum == 1) {
                tradeno = tradeno1;
            } else {
                tradeno = tradeno2;
            }
        }
        orderInfo.setTradeno(tradeno);
        orderInfo.setTitle(orderInfo.getName() + "X" + orderNum);
        params = ParamsUtils.sendOrderParams(orderInfo, payType);
        requestParams = new RequestParams(params);
        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, requestParams), new DisposeDataHandle(new DisposeDataListener() {

            @Override
            public void onSuccess(Object responseObject) {
                Log.e("PayPresenterImpl", "responseObject:" + responseObject);
            }

            @Override
            public void onFail(Object errorObject) {
                if (errorObject instanceof OkHttpException) {
                    ((OkHttpException) errorObject).getEmsg();
                    ((Exception) errorObject).printStackTrace();
                }
            }
        }));

    }

    public void updateDBNum(Long rid, int num) {
        roadBeanService.updateRoadNum(rid, num);
        SharedPreferencesUtil.putString(mContext, BoxParams.UPDATE_DB, "true");
    }

    @Override
    public void finish() {
        mContext =  null;
        payView = null;
        percentBeanService = null;
        percentBiz = null;
    }


}
