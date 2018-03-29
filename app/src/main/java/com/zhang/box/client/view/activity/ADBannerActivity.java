package com.zhang.box.client.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhang.box.R;
import com.zhang.box.application.BaseApplication;
import com.zhang.box.box.BoxAction;
import com.zhang.box.box.BoxParams;
import com.zhang.box.box.BoxSetting;
import com.zhang.box.client.model.ADInfo;
import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.model.RoadInfo;
import com.zhang.box.client.presenter.ADBannerPresenter;
import com.zhang.box.client.presenter.WeatherPresenter;
import com.zhang.box.client.presenter.impl.ADBannerPresenterImpl;
import com.zhang.box.client.presenter.impl.WeatherPresenterImpl;
import com.zhang.box.client.receiver.AVMRunningBroadcastReceiver;
import com.zhang.box.client.view.ADBannerView;
import com.zhang.box.client.widget.FullVideoView;
import com.zhang.box.contants.Constants;
import com.zhang.box.loading.ZLoadingDialog;
import com.zhang.box.loading.Z_TYPE;
import com.zhang.box.service.HeartService;
import com.zhang.box.util.FileUtils;
import com.zhang.box.util.SharedPreferencesUtil;
import com.zhang.box.util.ToastTools;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;


import com.zhang.box.client.model.MyWeather;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lilei on 2017/9/4.\
 * 首页展示广告和推荐商品Activity
 */

public class ADBannerActivity extends Activity implements ADBannerView, View.OnClickListener {

    private static final String TAG = "ADBannerActivity";
    public int goodsItemWidth = 128;

    //时间模块。天气
    @BindView(R.id.home_img_weather)
    ImageView homeImgWeather;
    //温度
    @BindView(R.id.home_txt_weather_temp)
    TextView homeTxtWeatherTemp;


    private Context mContext = this;
    private Context appContext;

    //商品滚动模块
    private boolean isTouch = false, isRight = true;
    private int x1 = 0;
    private boolean mAutoScroll = true;
    private int scrollTotal = 0;
    private boolean refreshGoods = false;

    public void setRefreshGoods(boolean refreshGoods) {
        this.refreshGoods = refreshGoods;
    }

    //广告轮播模块
    private Timer adTimer;
    private boolean isContinue;
    private int adCount;
    private int errorVideoNum = 0;
    private boolean videoPlay = false;


    //右侧广告栏
    @BindView(R.id.adbanner_ad_lv)
    ListView adbannerAdLv;

    //底部商品栏
    @BindView(R.id.adbanner_goods_gv)
    GridView adbannerGoodsGv;

    //视频广告
    @BindView(R.id.ad_videoView)
    FullVideoView adVideoView;

    //图片广告
    @BindView(R.id.ad_imageView)
    ImageView adImageView;

    //商品滚动
    @BindView(R.id.adbanner_b_scroll)
    HorizontalScrollView adbannerBScroll;

    //更多商品
    @BindView(R.id.adbanner_b_more)
    TextView adbannerBMore;

    //广告页中间层
    private ADBannerPresenter adPresenter;

    //天气时间中间层
    private WeatherPresenter weatherPresenter;

    //更多商品按钮动画
    private AlphaAnimation txtMoreAlphaAnimation;

    //广告位置
    private int adPosition;

    //Loading
    ZLoadingDialog dialog;

    //检测中控机连接广播
    AVMRunningBroadcastReceiver avmRunningBroadcastReceiver;

    
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_adbanner_activity);
        ButterKnife.bind(this);
        appContext = getApplicationContext();
        BaseApplication.addActivityToList(this);

        showDialog("加载中...");
        //初始化部分对象
        init();
        //调用中间层业务
        adPresenter.initAdData(adbannerAdLv);

        adPresenter.initGoodsData(adbannerGoodsGv);
        scrollTotal = goodsItemWidth * (adbannerGoodsGv.getCount() - 8);
        initGoodsScroll();

        adCount = adbannerAdLv.getCount();
        startAutoScroll();
        initDateAndWeather();

        startHeartService();


        
    }




    /**
     * 实例化天气控件
     */
    private void initDateAndWeather() {

        //每3小时刷新温度。每天刷新天气
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(4);
                System.gc();

            }
        }, 100, 1000 * 60 * 60 * 3);
    }


    //实例化对象
    private void init() {
        adPresenter = new ADBannerPresenterImpl(this, this);
        weatherPresenter = new WeatherPresenterImpl(this, this);
        // 隐藏媒体控制条
        MediaController mc = new MediaController(this);
        mc.setVisibility(View.INVISIBLE);
        adVideoView.setMediaController(mc);
        //视频播放完成监听
        adVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mHandler.sendEmptyMessage(2);
            }
        });
        //视频错误监听
        adVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                if (errorVideoNum == 0) {
                    adPresenter.deleteAdShow(adPosition);
                    adCount = adbannerAdLv.getCount();
                    mHandler.sendEmptyMessage(2);
//                    Glide.with(appContext)
//                            .load(R.drawable.ad_test_img1)
//                            .into(adImageView);
//                    showImg();
//                    scrollToNextAD();
                    ++errorVideoNum;
                }
                return true;
            }
        });

        //更多商品动画
        txtMoreAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.home_more_txt_alpha_anim);
        adbannerBMore.setAnimation(txtMoreAlphaAnimation);
        adbannerAdLv.setSelectionAfterHeaderView();

    }

    private void initGoodsScroll() {
        adbannerBScroll.fling(0);
        //初始化滚动动画
        adbannerBScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        isTouch = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isTouch = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isTouch = true;
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                x1 = adbannerBScroll.getScrollX();
                                isTouch = false;
                            }
                        }.start();
                        break;
                }
                return false;
            }
        });
        adbannerGoodsGv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        isTouch = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isTouch = true;

                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                x1 = adbannerBScroll.getScrollX();
                                isTouch = false;
                            }
                        }.start();
                        break;
                }
                return false;
            }
        });


    }

    /**
     * 启动心跳服务
     */
    private void startHeartService() {
        HeartService heartService = new HeartService();
        heartService.setAdBannerView(this);
        Intent intent = new Intent(this, HeartService.class);
        startService(intent);

//        avmRunningBroadcastReceiver = new AVMRunningBroadcastReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.avm.serialport.NOTICE_AVM_DISCONNECT");
//        registerReceiver(avmRunningBroadcastReceiver, filter);
    }


    /**
     * 实现点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.adbanner_b_more:
                navigateToMoreGoods();
                break;
        }


    }


    /**
     * 导航至支付界面
     */
    @Override
    public void navigateToPay(RoadGoods roadGoods) {
        int requestCode = 0;
        RoadInfo roadInfo = roadGoods.getRoadInfo();
        Long index = roadInfo.getRoadIndex();
        int state = BoxAction.getRoadState(roadInfo.getRoadBoxType(), index + "");
        if (roadInfo.getRoadBoxType().equals(BoxSetting.BOX_TYPE_DRINK)) {
            if (state == RoadInfo.ROAD_STATE_NORMAL) {
                Intent intent = new Intent(ADBannerActivity.this, PayActivity.class);
                intent.putExtra("roadGoods", roadGoods);
                intent.putExtra("result", 2);
                intentDateWeather(intent);
                startActivityForResult(intent, requestCode);
                adVideoView.pause();
//            adVideoView.stopPlayback();
            } else {
                ToastTools.showShort(mContext, "该商品已经售罄，请选购其他商品");
                //刷新商品
                adPresenter.initGoodsData(adbannerGoodsGv);
            }
        } else {
            if (state != RoadInfo.ROAD_STATE_DATA_ERROR) {
                Intent intent = new Intent(ADBannerActivity.this, PayActivity.class);
                intent.putExtra("roadGoods", roadGoods);
                intent.putExtra("result", 2);
                intentDateWeather(intent);
                startActivityForResult(intent, requestCode);
                adVideoView.pause();
//            adVideoView.stopPlayback();
            } else {
                ToastTools.showShort(mContext, "该商品已经售罄，请选购其他商品");
                //刷新商品
                adPresenter.initGoodsData(adbannerGoodsGv);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 2:
//                adPresenter.initGoodsData(adbannerGoodsGv);
                break;
        }

    }

    /**
     * 导航至更多商品界面
     */
    @Override
    public void navigateToMoreGoods() {
        Intent intent = new Intent(ADBannerActivity.this, MoreGoodsActivity.class);
        intentDateWeather(intent);
        startActivityForResult(intent, 0);
        adVideoView.pause();
    }

    //时间天气传递
    public void intentDateWeather(Intent intent) {
        if (myWeather != null) {
            intent.putExtra("temp", myWeather.getTemp());
            intent.putExtra("weather", myWeather.getWeather());
        }
    }

    //图片广告开启定时器
    public void scrollToNextAD() {
        //执行定时任务
        adTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(2);
            }
        }, 15000);

    }

    @Override
    public void changeAD(ADInfo adInfo, int adPosition) {
        this.adPosition = adPosition;
        //广告定时器
        if (adTimer != null) {
            adTimer.cancel();
        }
        adTimer = new Timer();
        if (adInfo.getAdType() == ADInfo.ADTYPE_IMG) {

            File file = new File(Constants.DEMO_FILE_PATH + "/" + adInfo.getImgFileName());
            Glide.with(appContext)
                    .load(file)
                    .error(R.drawable.ad_test_img1)
                    .into(adImageView);
            showImg();
            videoPlay = false;
            scrollToNextAD();
        } else if (adInfo.getAdType() == ADInfo.ADTYPE_VIDEO) {
//            Log.e(TAG, "exist:" + FileUtils.exist(Constants.DEMO_FILE_PATH + "/" + adInfo.getVideoFileName()).toString());
            if (FileUtils.exist(Constants.DEMO_FILE_PATH + "/" + adInfo.getVideoFileName())) {
                File file = new File(Constants.DEMO_FILE_PATH + "/" + adInfo.getVideoFileName());
                adVideoView.setVideoURI(Uri.fromFile(file));
                adVideoView.start();
                showVideo();
                videoPlay = true;
            } else {
//                Glide.with(appContext)
//                        .load(R.drawable.ad_test_img1)
//                        .into(adImageView);
//                showImg();
//                scrollToNextAD();
                adPresenter.deleteAdShow(adPosition);
                adCount = adbannerAdLv.getCount();
                mHandler.sendEmptyMessage(2);
            }
        }
        System.gc();

    }


    //商品自动滚动
    private void startAutoScroll() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = mHandler.obtainMessage(1, 0, 0);
                mHandler.sendMessage(msg);
            }
        }, 1000, 70);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (isTouch)
                        return;
                    if (isRight) {
                        ++x1;
                    } else {
                        --x1;
                    }
                    if (x1 >= scrollTotal) {
                        isRight = false;
                    }
                    if (x1 < 1) {
                        isRight = true;
                    }
                    adbannerBScroll.scrollTo(x1, 0);
                    break;
                case 2:
                    adPosition++;
                    if (adPosition >= adCount) {
                        adPosition = 0;
                        adbannerAdLv.smoothScrollToPositionFromTop(0, 0, 2000);
                    } else {
                        adbannerAdLv.smoothScrollToPosition(adPosition);
                    }
                    changeAD((ADInfo) adbannerAdLv.getItemAtPosition(adPosition), adPosition);
                    errorVideoNum = 0;
                    break;
                case 3:

                    break;
                case 4:
                    weatherPresenter.getWeatherInfo();
                    break;
            }
        }
    };


    /**
     * 展示图片广告
     */
    public void showImg() {
        if (adVideoView != null && adImageView != null) {
            adImageView.setVisibility(View.VISIBLE);
            adVideoView.stopPlayback();
            adVideoView.setVisibility(View.GONE);
            adVideoView.suspend();
        }
    }

    /**
     * 展示视频广告
     */
    public void showVideo() {
        if (adVideoView != null && adImageView != null) {
            adImageView.setVisibility(View.GONE);
            adVideoView.setVisibility(View.VISIBLE);
        }
    }


    MyWeather myWeather;

    @Override
    public void changeWeather(MyWeather myWeather) {
        this.myWeather = myWeather;
        if (myWeather != null) {
            homeImgWeather.setImageResource(myWeather.getWeatherIcon());
            homeTxtWeatherTemp.setText(myWeather.getWeather() + " " + myWeather.getTemp());
        }
    }

    


    @Override
    public void showDialog(String text) {
        dialog = new ZLoadingDialog(ADBannerActivity.this);
        dialog.setLoadingBuilder(Z_TYPE.TEXT)
                .setLoadingColor(Color.parseColor("#ff5307"))
                .setHintText(text)
                .setHintTextSize(16) // 设置字体大小
                .setHintTextColor(Color.parseColor("#525252"))  // 设置字体颜色
                .setCanceledOnTouchOutside(false)
                .show();
    }

    @Override
    public void hiddenDialog() {
        if (dialog != null) dialog.cancel();
    }

    //播放进度记录
    int per = 0;

    @Override
    protected void onPause() {
        if (adVideoView != null && videoPlay) {
            adVideoView.pause();
            per = adVideoView.getCurrentPosition();
            videoPlay = false;
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (adVideoView != null && videoPlay == false) {
            adVideoView.seekTo(per);
            adVideoView.start();
            videoPlay = true;
        }
        String updateDb = SharedPreferencesUtil.getString(mContext, BoxParams.UPDATE_DB);
        if (updateDb.equals("true")) {
            adPresenter.initGoodsData(adbannerGoodsGv);
            SharedPreferencesUtil.putString(mContext, BoxParams.UPDATE_DB, "false");
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        if (adVideoView != null && videoPlay) {
            adVideoView.pause();
            videoPlay = false;
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Glide.with(mContext).pauseRequests();
        if (adTimer != null) {
            adTimer.cancel();
            adTimer = null;
        }
    }
}
