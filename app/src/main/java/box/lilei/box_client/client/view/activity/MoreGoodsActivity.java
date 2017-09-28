package box.lilei.box_client.client.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import box.lilei.box_client.R;
import box.lilei.box_client.client.presenter.MoreGoodsPresenter;
import box.lilei.box_client.client.presenter.impl.MoreGoodsPresenterImpl;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreGoodsActivity extends Activity implements View.OnClickListener {



    private MoreGoodsPresenter moreGoodsPresenter;
    private Context mContext;

    //商品
    @BindView(R.id.more_goods_gv)
    GridView moreGoodsGv;
    //导航按钮组
    @BindView(R.id.more_goods_rbtn_group)
    RadioGroup moreGoodsRbtnGroup;
    //全部商品按钮
    @BindView(R.id.more_goods_nav_rb_allgoods)
    RadioButton moreGoodsNavRbAllgoods;
    //饮料
    @BindView(R.id.more_goods_nav_rb_drink)
    RadioButton moreGoodsNavRbDrink;
    //零食
    @BindView(R.id.more_goods_nav_rb_food)
    RadioButton moreGoodsNavRbFood;
    //返回
    @BindView(R.id.more_goods_nav_rl_return)
    RelativeLayout moreGoodsNavRlReturn;

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

        moreGoodsNavRlReturn.setOnClickListener(this);

        moreGoodsRbtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int navPosition = 0;
                switch (checkedId){
                    case R.id.more_goods_nav_rb_allgoods:
                        navPosition = 0;
                        break;
                    case R.id.more_goods_nav_rb_drink:
                        navPosition = 1;
                        break;
                    case R.id.more_goods_nav_rb_food:
                        navPosition = 2;
                        break;
                    default:
                        navPosition = 0;
                        break;
                }
                moreGoodsPresenter.checkNav(navPosition);
            }
        });
    }

    //点击事件
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.more_goods_nav_rl_return:
                MoreGoodsActivity.this.finish();
                break;
        }
    }


}
