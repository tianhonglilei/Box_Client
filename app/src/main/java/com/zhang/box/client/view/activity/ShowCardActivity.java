package com.zhang.box.client.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhang.box.R;
import com.zhang.box.client.model.Goods;
import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.model.RoadInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowCardActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "ShowCardActivity";

    private Context mContext;
    private Intent dataIntent;
    CountDownTimer countDownTimer;

    //商品货道信息
    private RoadGoods roadGoods;
    private RoadInfo roadInfo;
    private Goods goods;

    @BindView(R.id.pay_txt_return_time)
    TextView payTxtReturnTime;

    //返回按钮
    @BindView(R.id.pay_rl_return)
    RelativeLayout payRlReturn;


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

        payRlReturn.setOnClickListener(this);

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
