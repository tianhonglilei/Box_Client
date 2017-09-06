package box.lilei.box_client.client.view.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import box.lilei.box_client.R;
import box.lilei.box_client.client.adapter.ADViewPagerAdapter;
import box.lilei.box_client.client.adapter.RvBottomAdapter;
import box.lilei.box_client.client.listener.OnPageSelectListener;
import box.lilei.box_client.client.view.ADBannerView;
import box.lilei.box_client.client.widget.CoverFlowViewPager;
import box.lilei.box_client.client.widget.ScrollSpeedLinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lilei on 2017/9/4.\
 * 首页展示广告和推荐商品Activity
 */

public class ADBannerActivity extends Activity implements ADBannerView, View.OnClickListener {


    //底部商品导航栏
    @BindView(R.id.adbanner_b_rv)
    RecyclerView bRv;
    //广告轮播ViewPager
    @BindView(R.id.adviewpager)
    CoverFlowViewPager adviewpager;

    //底部导航栏适配器
    private RecyclerView.Adapter bAdapter;

    //底部布局管理
    private ScrollSpeedLinearLayoutManager bLayoutManager;

    //广告缩略图数据
    private List<Integer> aDList;

    private ADViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_adbanner_activity);
        //绑定控件
        ButterKnife.bind(this);
        initData();


    }




    /**
     * 初始化数据
     */
    private void initData() {
        aDList = new ArrayList<Integer>();
        for (int i = 0; i < 6; i++) {
            aDList.add(R.drawable.banner_forever_bu);
        }

        //底部左右方向轮播
        bAdapter = new RvBottomAdapter(aDList);
        bLayoutManager = new ScrollSpeedLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        bLayoutManager.setSpeedSlow();
        bRv.setLayoutManager(bLayoutManager);
        bRv.setAdapter(bAdapter);

        // 初始化数据
        List<View> list = new ArrayList<>();
        for(int i = 0;i<10;i++){
            ImageView img = new ImageView(this);
            img.setBackgroundColor(Color.parseColor("#"+getRandColorCode()));
            list.add(img);
        }
        //设置显示的数据
        adviewpager.setViewList(list);
        // 设置滑动的监听，该监听为当前页面滑动到中央时的索引
        adviewpager.setOnPageSelectListener(new OnPageSelectListener() {
            @Override
            public void select(int position) {
                Toast.makeText(getApplicationContext(),position+"", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 获取随机颜色，便于区分
     * @return
     */
    public static String getRandColorCode(){
        String r,g,b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length()==1 ? "0" + r : r ;
        g = g.length()==1 ? "0" + g : g ;
        b = b.length()==1 ? "0" + b : b ;

        return r+g+b;
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


    }
}
