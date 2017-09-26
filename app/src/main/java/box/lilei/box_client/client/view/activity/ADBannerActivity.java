package box.lilei.box_client.client.view.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

import box.lilei.box_client.BuildConfig;
import box.lilei.box_client.R;
import box.lilei.box_client.client.model.ADInfo;
import box.lilei.box_client.client.presenter.ADBannerPresenter;
import box.lilei.box_client.client.presenter.impl.ADBannerPresenterImpl;
import box.lilei.box_client.client.view.ADBannerView;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lilei on 2017/9/4.\
 * 首页展示广告和推荐商品Activity
 */

public class ADBannerActivity extends Activity implements ADBannerView, View.OnClickListener {

    private static final String TAG = "ADBannerActivity";
    public int goodsItemWidth = 128;

    private Context mContext = this;

    //商品滚动模块
    private boolean isTouch = false, isRight;
    private int x1 = 0;
    private boolean mAutoScroll = true;
    private int scrollTotal = 0;

    //广告轮播模块
    private Timer adTimer;
    private boolean isContinue;
    private int adCount;
    private int errorVideoNum = 0;


    //右侧广告栏
    @BindView(R.id.adbanner_ad_lv)
    ListView adbannerAdLv;

    //底部商品栏
    @BindView(R.id.adbanner_goods_gv)
    GridView adbannerGoodsGv;

    //视频广告
    @BindView(R.id.ad_videoView)
    VideoView adVideoView;

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

    //更多商品动画
    private AlphaAnimation txtMoreAlphaAnimation;

    private int adPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_adbanner_activity);
        //绑定控件
        ButterKnife.bind(this);
        //初始化部分对象
        init();

        //调用中间层业务
        adPresenter.initAdData(adbannerAdLv);
        adPresenter.initGoodsData(adbannerGoodsGv);

        initGoodsScroll();
        scrollTotal = goodsItemWidth * (adbannerGoodsGv.getCount() - 8);
        Log.e(TAG, "scrollTotal:" + scrollTotal);
        adCount = adbannerAdLv.getCount();
        startAutoScroll();
    }


    //实例化对象
    private void init() {
        adPresenter = new ADBannerPresenterImpl(this, this);
        // 隐藏媒体控制条
        MediaController mc = new MediaController(this);
        mc.setVisibility(View.INVISIBLE);
        adVideoView.setMediaController(mc);
        //视频播放完成监听
        adVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mHandler.sendEmptyMessage(2);
                Log.e(TAG, "onCompletion: videofinash");
            }
        });
        //视频错误监听
        adVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if(errorVideoNum == 0) {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mHandler.sendEmptyMessage(2);
                        }
                    }.start();
                    errorVideoNum++;
                }
                return true;
            }
        });

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
                                    Thread.sleep(1000);
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
                                    Thread.sleep(1000);
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


    private void initAdScroll() {

    }


    /**
     * 实现点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        int id = v.getId();
        Log.e(TAG, "onClick: " + id);
        switch (id) {
            case R.id.adbanner_b_more:
                Intent intent = new Intent(ADBannerActivity.this, MoreGoodsActivity.class);
                startActivity(intent);
                Log.e(TAG, "onClick: " + id);
                break;
        }


    }

    /**
     * 导航至支付界面
     */
    @Override
    public void navigateToPay() {

    }

    /**
     * 导航至更多商品界面
     */
    @Override
    public void navigateToMoreGoods() {

    }

    //图片广告开启定时器
    public void scrollToNextAD() {
        //执行定时任务
        adTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(2);
                Log.e(TAG, "scrollToNextAD:imgfinash");
            }
        }, 10000);

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
            showImg();
            adImageView.setImageResource(R.drawable.ad_test_img1);
            scrollToNextAD();
        } else if (adInfo.getAdType() == ADInfo.ADTYPE_VIDEO) {
            showVideo();
            Uri uri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.ad_test_video1);
            adVideoView.setVideoURI(uri);
            adVideoView.start();
        }
        errorVideoNum = 0;
        Log.e(TAG, "adPosition:" + adPosition);

    }


    private Thread goodsScrollthread;

    //商品自动滚动
    private void startAutoScroll() {
//        goodsScrollthread = new Thread() {
//            @Override
//            public void run() {
//
//                while (mAutoScroll) {
//                    try {
//                        Thread.sleep(70);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Message msg = mHandler.obtainMessage(1, 0, 0);
//                    mHandler.sendMessage(msg);
//                }
//            }
//
//        };
//        goodsScrollthread.start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = mHandler.obtainMessage(1, 0, 0);
                mHandler.sendMessage(msg);
            }
        }, 500, 70);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (isTouch)
                        return;
                    if (isRight) {
                        x1 = x1 + 1;
                    } else {
                        x1 = x1 - 1;
                    }
                    if (x1 >= scrollTotal) {
                        isRight = false;
                    }
                    if (x1 <= 0) {
                        isRight = true;
                    }
                    adbannerBScroll.scrollTo(x1, 0);
                    break;
                case 2:
                    adPosition++;
                    if (adPosition >= adCount) {
                        adPosition = 0;
                        adbannerAdLv.smoothScrollToPositionFromTop(0, 0, 2000);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adbannerAdLv.setSelection(0);
                            }
                        }, 2000);
                    } else {
                        adbannerAdLv.smoothScrollToPosition(adPosition);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adbannerAdLv.setSelection(adPosition);
                            }
                        }, 800);
                    }
                    changeAD((ADInfo) adbannerAdLv.getItemAtPosition(adPosition), adPosition);
//                    Log.e(TAG, "handleMessage: " + adPosition);
//                    Log.e(TAG, "adCount:" + adCount);
            }
        }
    };


    /**
     * 展示图片广告
     */
    public void showImg() {
        adImageView.setVisibility(View.VISIBLE);
        adVideoView.stopPlayback();
        adVideoView.setVisibility(View.GONE);
    }

    /**
     * 展示视频广告
     */
    public void showVideo() {
        adImageView.setVisibility(View.GONE);
        adVideoView.setVisibility(View.VISIBLE);
    }

    /**
     * 视频错误图片
     */
    public void showErrorImg(){
        adImageView.setVisibility(View.VISIBLE);
        adVideoView.setVisibility(View.GONE);
//        adImageView.setImageResource();
    }

}
