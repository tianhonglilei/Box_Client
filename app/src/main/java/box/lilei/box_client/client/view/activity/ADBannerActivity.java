package box.lilei.box_client.client.view.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

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


    private boolean isTouch, isRight;
    private int x1 = 0;
    private boolean mAutoScroll = true;
    private int scrollTotal = 0;

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


    //广告页中间层
    private ADBannerPresenter adPresenter;

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
        scrollTotal =  goodsItemWidth * (adbannerGoodsGv.getCount() - 8);
        Log.e(TAG, "scrollTotal:" + scrollTotal);
        startAutoScroll();
    }


    //实例化对象
    private void init() {
        adPresenter = new ADBannerPresenterImpl(this, this);
        // 隐藏媒体控制条
        MediaController mc = new MediaController(this);
        mc.setVisibility(View.INVISIBLE);
        adVideoView.setMediaController(mc);
        adVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });


    }

    private void initGoodsScroll() {
//        scrollTotal = adbannerBScroll.gets
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
                        new Thread(){
                            @Override
                            public void run() {
                                try {
                                        Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                            }
                        }.start();
                        x1 = adbannerBScroll.getScrollX();
                        isTouch = false;
                        break;
                }
                return false;
            }
        });


    }

    public void checkscrollfling(){

    }



    /**
     * 实现点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

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

    @Override
    public void scrollAD(int position) {
        adbannerAdLv.smoothScrollToPosition(position);
    }

    @Override
    public void changeAD(ADInfo adInfo) {
        if (adInfo.getAdType() == ADInfo.ADTYPE_IMG) {
            showImg();
            adImageView.setImageResource(R.drawable.ad_test_img1);
        } else if (adInfo.getAdType() == ADInfo.ADTYPE_VIDEO) {
            showVideo();
            Uri uri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.ad_test_video1);
            adVideoView.setVideoURI(uri);
            adVideoView.start();
        }
    }

    @Override
    public void autoScrollGoods() {


    }

    private Thread goodsScrollthread;

    private void startAutoScroll() {
        goodsScrollthread = new Thread() {
            @Override
            public void run() {
                while (mAutoScroll) {
                    try {
                        Thread.sleep(70);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = mHandler.obtainMessage(1, 0, 0);
                    mHandler.sendMessage(msg);
                }
            }

        };
        goodsScrollthread.start();
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
                    Log.d(TAG, "x1:" + x1);
                    break;
            }
        }
    };


    /**
     * 展示图片广告
     */
    public void showImg() {
        adImageView.setVisibility(View.VISIBLE);
        adVideoView.setVisibility(View.GONE);
    }

    /**
     * 展示视频广告
     */
    public void showVideo() {
        adImageView.setVisibility(View.GONE);
        adVideoView.setVisibility(View.VISIBLE);
    }


}
