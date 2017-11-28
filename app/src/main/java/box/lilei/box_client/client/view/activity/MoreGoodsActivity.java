package box.lilei.box_client.client.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import box.lilei.box_client.R;
import box.lilei.box_client.box.BoxAction;
import box.lilei.box_client.box.BoxParams;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.client.model.RoadInfo;
import box.lilei.box_client.client.presenter.MoreGoodsPresenter;
import box.lilei.box_client.client.presenter.impl.MoreGoodsPresenterImpl;
import box.lilei.box_client.client.view.MoreGoodsView;
import box.lilei.box_client.manager.view.activity.ManagerNavgationActivity;
import box.lilei.box_client.util.SharedPreferencesUtil;
import box.lilei.box_client.util.ToastTools;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreGoodsActivity extends Activity implements View.OnClickListener, MoreGoodsView {


    //向上的GIF
    @BindView(R.id.more_goods_up_gif)
    ImageView moreGoodsUpGif;
    @BindView(R.id.more_imei_num)
    TextView moreImeiNum;
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


    //温度时间模块
    private Intent dataIntent;
    @BindView(R.id.more_weather_txt)
    TextView moreWeatherTxt;
    @BindView(R.id.more_weather_wd_num)
    TextView moreWeatherWdNum;

    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_more_goods_activity);
        ButterKnife.bind(this);
        mContext = this;
        dataIntent = this.getIntent();
        Glide.with(mContext).load(R.drawable.more_goods_up).into(moreGoodsUpGif);

        initControl();

        initGoodsGridView();

        initDateAndWeather();


    }


    /**
     * 初始化时间和温度
     */
    private void initDateAndWeather() {
        if (dataIntent != null) {
            moreWeatherTxt.setText(dataIntent.getStringExtra("weather"));
            moreWeatherWdNum.setText(dataIntent.getStringExtra("temp"));
        }
    }

    private void initGoodsGridView() {
        //设置gv item 点击事件
        moreGoodsGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RoadGoods roadGoods = (RoadGoods) moreGoodsGv.getItemAtPosition(position);
                Goods goods = roadGoods.getGoods();
                if (goods.getGoodsSaleState() == Goods.SALE_STATE_OUT) {
//                    ToastTools.showShort(mContext,"该商品已售罄，请选购其他商品");
                    return;
                }
                RoadInfo roadInfo = roadGoods.getRoadInfo();
                Long index = roadInfo.getRoadIndex();
                int state = BoxAction.getRoadState(roadInfo.getRoadBoxType(), index + "");
                if (state == RoadInfo.ROAD_STATE_NORMAL) {
                    Intent intent = new Intent(MoreGoodsActivity.this, PayActivity.class);
                    intent.putExtra("temp", moreWeatherWdNum.getText().toString());
                    intent.putExtra("weather", moreWeatherTxt.getText().toString());
                    intent.putExtra("roadGoods", roadGoods);
                    startActivity(intent);
                } else {
                    ToastTools.showShort(mContext, "该商品已售罄，请选购其他商品");
                    //刷新商品
                    moreGoodsPresenter.initAllGoods(moreGoodsGv);
                }

            }
        });
    }

    private void initControl() {
        moreImeiNum.setOnClickListener(this);
        mContext = this;
        if (moreGoodsPresenter == null) {
            moreGoodsPresenter = new MoreGoodsPresenterImpl(mContext, this);
        }

        moreGoodsNavRlReturn.setOnClickListener(this);

        //显示机器号
        moreImeiNum.setText(SharedPreferencesUtil.getString(mContext, BoxParams.BOX_ID));

        moreGoodsRbtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int navPosition;
                switch (checkedId) {
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
            case R.id.more_imei_num:
                Intent intent = new Intent(MoreGoodsActivity.this, ManagerNavgationActivity.class);
                startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer!=null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        moreGoodsPresenter.initAllGoods(moreGoodsGv);
        initCountDownTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (moreGoodsPresenter!=null)
        moreGoodsPresenter = null;
        if (countDownTimer!=null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }



    /**
     * 倒计时
     */
    private void initCountDownTimer() {
        if (countDownTimer!=null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                moreNavTxtReturnTime.setText(millisUntilFinished / 1000 + "S");
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }


}
