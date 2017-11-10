package box.lilei.box_client.client.presenter.impl;

import android.content.Context;

import java.util.List;

import box.lilei.box_client.client.biz.PercentBiz;
import box.lilei.box_client.client.biz.impl.PercenteBizImpl;
import box.lilei.box_client.client.model.PercentInfo;
import box.lilei.box_client.client.presenter.PayPresenter;
import box.lilei.box_client.client.view.PayView;
import box.lilei.box_client.db.PercentBean;
import box.lilei.box_client.db.biz.PercentBeanService;

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
}
