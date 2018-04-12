package com.zhang.box.client.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhang.box.R;
import com.zhang.box.box.BoxParams;
import com.zhang.box.client.model.Goods;
import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.model.RoadInfo;
import com.zhang.box.contants.Constants;
import com.zhang.box.util.SharedPreferencesUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowCardActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "ShowCardActivity";

    @BindView(R.id.show_card_memo)
    TextView showCardMemo;

    private Context mContext;
    private Intent dataIntent;
    CountDownTimer countDownTimer;

    //商品货道信息
    private RoadGoods roadGoods;
    private RoadInfo roadInfo;
    private Goods goods;
    //商品信息
    @BindView(R.id.pay_img_goods)
    ImageView payImgGoods;
    @BindView(R.id.pay_txt_goods_name)
    TextView payTxtGoodsName;

    @BindView(R.id.pay_txt_return_time)
    TextView payTxtReturnTime;

    //返回按钮
    @BindView(R.id.pay_rl_return)
    RelativeLayout payRlReturn;

    //天气和时间
    @BindView(R.id.more_weather_time)
    TextView moreWeatherTime;
    @BindView(R.id.more_weather_date)
    TextView moreWeatherDate;
    @BindView(R.id.more_weather_txt)
    TextView moreWeatherTxt;
    @BindView(R.id.more_weather_wd_num)
    TextView moreWeatherWdNum;
    //机器编号
    @BindView(R.id.more_imei_num)
    TextView moreImeiNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_show_card_activity);
        ButterKnife.bind(this);
//        BaseApplication.addActivityToList(this);
        mContext = this;
        dataIntent = this.getIntent();
        roadGoods = dataIntent.getParcelableExtra("roadGoods");
        if (roadGoods == null) {
            returnAndFinish();
        }

        initCountDownTimer();
        initGoodsInfo();
        initDateAndWeather();

        //显示机器号
        moreImeiNum.setText(SharedPreferencesUtil.getString(mContext, BoxParams.BOX_ID));

        payRlReturn.setOnClickListener(this);


    }


    /**
     * 初始化商品信息
     */
    public void initGoodsInfo() {
        roadInfo = roadGoods.getRoadInfo();
        goods = roadGoods.getGoods();
        File file = new File(Constants.DEMO_FILE_PATH + "/" + goods.getGoodsBImgName());
        Glide.with(this)
                .load(file)
                .skipMemoryCache(true)
                .into(payImgGoods);
        payTxtGoodsName.setText(goods.getGoodsName());
        String memo = goods.getGoodsMemo().replace("\\n","\n");
        showCardMemo.setText(memo);
    }

    /**
     * 初始化天气
     */
    private void initDateAndWeather() {
        if (dataIntent != null) {
            moreWeatherTxt.setText(dataIntent.getStringExtra("weather"));
            moreWeatherWdNum.setText(dataIntent.getStringExtra("temp"));
        }
    }


    private void initCountDownTimer() {
        countDownTimer = new CountDownTimer(100000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long time = millisUntilFinished / 1000;
                payTxtReturnTime.setText(time + "S");
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

    private void returnAndFinish() {
        ShowCardActivity.this.finish();
        System.gc();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_rl_return:
                returnAndFinish();
                break;
        }
    }
}
