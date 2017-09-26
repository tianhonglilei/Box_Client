package box.lilei.box_client.client.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import box.lilei.box_client.R;
import box.lilei.box_client.client.presenter.MoreGoodsPresenter;
import box.lilei.box_client.client.presenter.impl.MoreGoodsPresenterImpl;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreGoodsActivity extends Activity implements View.OnClickListener{

    private MoreGoodsPresenter moreGoodsPresenter;
    private Context mContext;

    //商品
    @BindView(R.id.more_goods_gv)
    GridView moreGoodsGv;
    //全部商品按钮
    @BindView(R.id.more_nav_txt_allgoods)
    TextView moreNavTxtAllgoods;
    //饮料
    @BindView(R.id.more_nav_txt_drink)
    TextView moreNavTxtDrink;
    //零食
    @BindView(R.id.more_nav_txt_food)
    TextView moreNavTxtFood;
    //返回
    @BindView(R.id.more_nav_txt_return)
    TextView moreNavTxtReturn;
    //返回时间
    @BindView(R.id.more_nav_txt_return_time)
    TextView moreNavTxtReturnTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_more_goods_activity);
        ButterKnife.bind(this);

        initControl();


    }

    private void initControl() {
        mContext = this;

        moreGoodsPresenter = new MoreGoodsPresenterImpl(mContext);
        moreGoodsPresenter.initAllGoods(moreGoodsGv);

    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_goods_nav_rl_return:
                finish();
                break;
        }
    }
}
