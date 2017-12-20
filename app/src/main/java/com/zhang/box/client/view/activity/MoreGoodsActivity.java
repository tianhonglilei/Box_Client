package com.zhang.box.client.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.IdRes;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhang.box.R;
import com.zhang.box.application.BaseApplication;
import com.zhang.box.client.listener.OpenDoorListener;
import com.zhang.box.client.model.Goods;

import java.util.Timer;
import java.util.TimerTask;

import com.zhang.box.box.BoxAction;
import com.zhang.box.box.BoxParams;
import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.model.RoadInfo;
import com.zhang.box.client.presenter.MoreGoodsPresenter;
import com.zhang.box.client.presenter.impl.MoreGoodsPresenterImpl;
import com.zhang.box.client.receiver.OpenDoorBroadcastReceiver;
import com.zhang.box.client.view.MoreGoodsView;
import com.zhang.box.manager.view.activity.ManagerNavgationActivity;
import com.zhang.box.util.BroadcastReceiverUtil;
import com.zhang.box.util.SharedPreferencesUtil;
import com.zhang.box.util.ToastTools;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreGoodsActivity extends Activity implements View.OnClickListener, MoreGoodsView, OpenDoorListener {


    //向上的GIF
    @BindView(R.id.more_goods_up_gif)
    ImageView moreGoodsUpGif;
    @BindView(R.id.more_imei_num)
    TextView moreImeiNum;
    @BindView(R.id.date_img_signal)
    ImageView dateImgSignal;
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

    //语音提示
    MediaPlayer mediaPlayer = null;
    AssetManager assetManager = null;

    private boolean managerShow = false;

    boolean isRegister = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_more_goods_activity);
        ButterKnife.bind(this);
        BaseApplication.addActivityToList(this);
        mContext = this;
        dataIntent = this.getIntent();
        Glide.with(mContext).load(R.drawable.more_goods_up).into(moreGoodsUpGif);

        initControl();

        moreGoodsPresenter.initAllGoods(moreGoodsGv);
        haveFoods();

        initGoodsGridView();

        initDateAndWeather();

        initMediaPlayer();

        initSignListener();

        initAnimation();

        initDoorReceiver();

        initCountDownTimer();

    }


    private OpenDoorBroadcastReceiver openDoorBroadcastReceiver;


    /**
     * 初始化开门广播
     */
    private void initDoorReceiver() {
        openDoorBroadcastReceiver = new OpenDoorBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BoxAction.OPEN_DOOR_ACTION);
        openDoorBroadcastReceiver.setOpenDoorListener(this);
        if (!isRegister) {
            registerReceiver(openDoorBroadcastReceiver, filter);
            isRegister = true;
        }
    }


    /**
     * 是否有食品
     */
    private void haveFoods() {
        String haveFoods = SharedPreferencesUtil.getString(mContext, BoxParams.HAVE_FOOD);
        if (haveFoods.equals("false")) {
            moreGoodsNavRbFood.setVisibility(View.INVISIBLE);
        } else {
            moreGoodsNavRbFood.setVisibility(View.VISIBLE);
        }
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

    private int resultCode;

    private void initGoodsGridView() {
        //设置gv item 点击事件
        moreGoodsGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resultCode = 1;
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
                    startActivityForResult(intent, resultCode);
                    telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
                    if (isRegister) {
                        unregisterReceiver(openDoorBroadcastReceiver);
                        isRegister = false;
                    }
                } else {
                    ToastTools.showShort(mContext, "该商品已售罄，请选购其他商品");
                    //刷新商品
                    moreGoodsPresenter.initAllGoods(moreGoodsGv);
                }

            }
        });
    }

    private int resultRefresh = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("MoreGoodsActivity", "requestCode:" + requestCode + "--resultCode:" + resultCode);
//        initDoorReceiver();
//        initCountDownTimer();
        if (requestCode == 2){
            resultRefresh = 2;
        }
        switch (resultCode) {
            case 2:
                resultRefresh = 2;
                MoreGoodsActivity.this.setResult(resultRefresh);
                MoreGoodsActivity.this.finish();
                break;
        }
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
                returnAndFinish();
                break;
            case R.id.more_imei_num:
//                Intent intent = new Intent(MoreGoodsActivity.this, ManagerNavgationActivity.class);
//                startActivity(intent);
                break;
        }
    }

    private void returnAndFinish() {
        if (isRegister) {
            unregisterReceiver(openDoorBroadcastReceiver);
            isRegister = false;
        }
//        if (phoneStateListener!=null){
//            phoneStateListener = null;
//        }
        if (telephonyManager != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
        MoreGoodsActivity.this.setResult(resultRefresh);
        MoreGoodsActivity.this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (moreGoodsPresenter != null)
            moreGoodsPresenter = null;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
//        Glide.with(mContext).pauseRequests();
        if (isRegister) {
            unregisterReceiver(openDoorBroadcastReceiver);
            isRegister = false;
        }
    }


    /**
     * 倒计时
     */
    private void initCountDownTimer() {
        if (countDownTimer != null) {
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
                returnAndFinish();
            }
        }.start();
    }


    /**
     * 初始化音频播放
     */
    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        assetManager = getAssets();
        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = assetManager.openFd("welcome.wav");
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(),
                    fileDescriptor.getStartOffset());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    TelephonyManager telephonyManager;
    PhoneStateListener phoneStateListener;

    /**
     * 初始化信号监听
     */
    public void initSignListener() {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        }
        if (phoneStateListener == null) {
            phoneStateListener = new PhoneStateListener() {
                @Override
                public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                    super.onSignalStrengthsChanged(signalStrength);
                    String signalInfo = signalStrength.toString();
                    String[] params = signalInfo.split(" ");
//                    Toast.makeText(mContext, signalInfo, Toast.LENGTH_LONG).show();
//                    Toast.makeText(mContext, "telephonyManager.getNetworkType():" + telephonyManager.getNetworkType(), Toast.LENGTH_SHORT).show();
                    if (telephonyManager.getNetworkType() != TelephonyManager.NETWORK_TYPE_UNKNOWN) {
                        //4G网络 最佳范围   >-90dBm 越大越好
                        int ltedbm = Integer.parseInt(params[6]);
                        if (ltedbm > -44) {
                            changeSignSize(0);
                        } else if (ltedbm >= -90) {
                            changeSignSize(4);
                        } else if (ltedbm >= -105) {
                            changeSignSize(3);
                        } else if (ltedbm >= -115) {
                            changeSignSize(2);
                        } else if (ltedbm >= -120) {
                            changeSignSize(1);
                        } else if (ltedbm >= -140) {
                            changeSignSize(0);
                        } else {
                            changeSignSize(0);
                        }
                    } else {
                        changeSignSize(0);
                    }
                }
            };
        }
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }


    public void changeSignSize(int level) {
        switch (level) {
            case 0:
                dateImgSignal.setImageResource(R.drawable.sign_no_black);
                break;
            case 1:
                dateImgSignal.setImageResource(R.drawable.sign_one_black);
                break;
            case 2:
                dateImgSignal.setImageResource(R.drawable.sign_two_black);
                break;
            case 3:
                dateImgSignal.setImageResource(R.drawable.sign_three_black);
                break;
            case 4:
                dateImgSignal.setImageResource(R.drawable.sign_four_black);
                break;
        }
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                moreGoodsGv.smoothScrollBy(moreGoodsGv.getMeasuredHeight(), 1000);
            }
        }, 1500);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                moreGoodsGv.smoothScrollBy(-moreGoodsGv.getHeight(), 1000);
            }
        }, 3000);
    }


    @Override
    public void openTheDoor() {
        Intent intent = new Intent(MoreGoodsActivity.this, ManagerNavgationActivity.class);
        startActivityForResult(intent, 0);
        if (isRegister) {
            unregisterReceiver(openDoorBroadcastReceiver);
            isRegister = false;
        }
        finish();
    }
}
