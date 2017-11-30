package box.lilei.box_client.client.presenter.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;


import com.alibaba.fastjson.JSONObject;
import com.google.zxing.common.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import box.lilei.box_client.box.BoxAction;
import box.lilei.box_client.box.BoxSetting;
import box.lilei.box_client.client.biz.PercentBiz;
import box.lilei.box_client.client.biz.impl.PercenteBizImpl;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.MyTime;
import box.lilei.box_client.client.model.OrderInfo;
import box.lilei.box_client.client.model.PercentInfo;
import box.lilei.box_client.client.model.RoadInfo;
import box.lilei.box_client.client.okhttp.CommonOkHttpClient;
import box.lilei.box_client.client.okhttp.exception.OkHttpException;
import box.lilei.box_client.client.okhttp.handler.DisposeDataHandle;
import box.lilei.box_client.client.okhttp.listener.DisposeDataListener;
import box.lilei.box_client.client.okhttp.request.CommonRequest;
import box.lilei.box_client.client.okhttp.request.RequestParams;
import box.lilei.box_client.client.presenter.PayPresenter;
import box.lilei.box_client.client.view.PayView;
import box.lilei.box_client.contants.Constants;
import box.lilei.box_client.db.PercentBean;
import box.lilei.box_client.db.RoadBean;
import box.lilei.box_client.db.biz.PercentBeanService;
import box.lilei.box_client.db.biz.RoadBeanService;
import box.lilei.box_client.util.MyStringUtil;
import box.lilei.box_client.util.ParamsUtils;
import box.lilei.box_client.util.QRCodeUtil;
import box.lilei.box_client.util.TimeUtil;
import box.lilei.box_client.util.ToastTools;
import okhttp3.OkHttpClient;

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


    public PayPresenterImpl(Context mContext, PayView payView) {
        this.mContext = mContext;
        this.payView = payView;
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
    public void getQRCode(String url, double price, final int payType, final int payNum, Goods goods, RoadInfo roadInfo) {
        payView.loadQRCode();
        //获取二维码所需参数
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Long goodsId = goods.getGoodsId();
        orderInfo.setPid(goodsId.toString());
        orderInfo.setPrice(price+"");
        String box_id = BoxAction.getBoxIdFromSP(mContext);
        orderInfo.setImei(box_id);
        roadIndex = roadInfo.getRoadIndex();
        orderInfo.setHdid(roadInfo.getRoadIndex()+"");
        boxType = roadInfo.getRoadBoxType();
        orderInfo.setHgid(roadInfo.getRoadBoxType());
        final String tradeno = now + "," + goodsId + ","
                + box_id + "," + roadIndex + ","
                + boxType;
        String goodsName = goods.getGoodsName();
        orderInfo.setName(goodsName);
        String title = goodsName + "x" + payNum;
        String des = "商品" + goodsName + payNum + "份，共" + price + "元";
        String subject = des + "|" + goodsId + "|"
                + roadIndex + "|" + box_id + "|"
                + boxType;
        final String mchTradeNo = MyStringUtil.getRandonInt(20);
        Map<String, String> params;
        if (payType == Constants.PAY_TYPE_WX) {
            //微信二维码
            params = ParamsUtils.wxGetQRParams(Double.toString(price), des, mchTradeNo, subject);
        } else if (payType == Constants.PAY_TYPE_ALI) {
            //支付宝二维码
            params = ParamsUtils.aliGetQRParams(tradeno, Double.toString(price), title, des);
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
        sendOrder(orderNum, outNum);
    }



    @Override
    public void cancelOrder() {
        orderInfo.setCancel(true);
    }


    /**
     * 改变支付方式
     * @param num
     * @param payType
     */
    @Override
    public void chengePayRequest(int num, int payType) {
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
        Log.e("PayPresenterImpl:" + payType + "-" + num, tradeno);
        params = ParamsUtils.getPayResponseParams(tradeno, payType);
        requestParams = new RequestParams(params);
        getPayResponse(payType, num);
    }

    @Override
    public void cancelRequest() {
        if (task != null){
            task.cancel();
            task = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private Timer timer;
    private TimerTask task;

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
                    orderInfo.setPayType(payType);
                    orderInfo.setOrderNum(num);
                    payView.showDialog("出货中...");
                    orderInfo.setPayState(true);
                    outGoodsAction(num, boxType, roadIndex + "");
                } else if (jsonObject.getString("error").equals("-1")) {
                    if (!orderInfo.isPayState() && !orderInfo.isCancel()) {
                        timer = new Timer();
                        task = new TimerTask() {
                            @Override
                            public void run() {
                                chengePayRequest(num, payType);
                            }
                        };
                        timer.schedule(task, 1000);
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


    private void outGoodsAction(final int num, final String boxType, final String roadIndex) {
        payView.outGoodsCheck(num);
        for (int i = 0; i < num; i++) {
            if (i == 1) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                            BoxAction.outGoods(boxType, roadIndex);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                if (!BoxAction.outGoods(boxType, roadIndex)) {
                    payView.hiddenDialog();
                    payView.showPopwindow(false, 0, 0);
                    break;
                }
            }
        }
    }


    /**
     * 上传订单信息
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
        orderInfo.setTitle(orderInfo.getName()+"X"+orderNum);
        params = ParamsUtils.sendOrderParams(orderInfo, payType);
        requestParams = new RequestParams(params);
        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, requestParams), new DisposeDataHandle(new DisposeDataListener() {

            @Override
            public void onSuccess(Object responseObject) {

            }

            @Override
            public void onFail(Object errorObject) {

            }
        }));
        updateDBNum(outNum);
    }

    private void updateDBNum(int num){
        roadBeanService.updateRoadNum(Long.parseLong(orderInfo.getHdid()),num);
    }














}
