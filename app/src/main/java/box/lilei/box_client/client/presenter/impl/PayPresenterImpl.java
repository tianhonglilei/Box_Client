package box.lilei.box_client.client.presenter.impl;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box.lilei.box_client.client.biz.PercentBiz;
import box.lilei.box_client.client.biz.impl.PercenteBizImpl;
import box.lilei.box_client.client.model.PercentInfo;
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
import box.lilei.box_client.util.ParamsUtils;
import box.lilei.box_client.util.QRCodeUtil;

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
    public void getQRCode(String url, double price, int payType) {
        Map<String, String> params;
        if (payType == Constants.PAY_TYPE_WX) {
            params = ParamsUtils.wxGetQRParams(Double.toString(price), "", "", "");
        } else if (payType == Constants.PAY_TYPE_ALI) {
            params = ParamsUtils.aliGetQRParams("", Double.toString(price), "", "");
        }else{
            params = new HashMap<>();
        }
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, new RequestParams(params)), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObject) {
                payView.showQRCode(QRCodeUtil.createQRImage(responseObject.toString()));
            }

            @Override
            public void onFail(Object errorObject) {
                payView.showQRCode(null);
            }
        }));
    }


}
