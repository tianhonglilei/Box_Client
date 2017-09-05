package box.lilei.box_client.client.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.client.adapter.RvBottomAdapter;
import box.lilei.box_client.client.adapter.RvRightAdapter;
import box.lilei.box_client.client.view.ADBannerView;
import butterknife.BindView;


/**
 * Created by lilei on 2017/9/4.\
 * 首页展示广告和推荐商品Activity
 */

public class ADBannerActivity extends Activity implements ADBannerView, View.OnClickListener {

    //视频控件
    @BindView(R.id.adbanner_video)
    VideoView mVideo;

    //图片控件
    @BindView(R.id.adbanner_img)
    ImageView mImg;

    //右侧缩略图和底部商品导航栏
    @BindView(R.id.adbanner_r_rv)
    RecyclerView rRv;
    @BindView(R.id.adbanner_b_rv)
    RecyclerView bRv;

    //右侧和底部导航栏适配器
    private RecyclerView.Adapter rAdapter, bAdapter;

    //右侧和底部布局管理
    private RecyclerView.LayoutManager rLayoutManager, bLayoutManager;

    //广告缩略图数据
    private List<Integer> aDList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_adbanner_activity);
        //绑定控件
        butterknife.ButterKnife.bind(this);
        initData();


    }

    /**
     * 初始化数据
     */
    private void initData() {
        aDList = new ArrayList<Integer>();
        for (int i = 0; i<6 ; i++){
            aDList.add(R.drawable.banner_forever_bu);
        }
        //右侧垂直方向轮播
        rAdapter = new RvRightAdapter(aDList);
        rLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rRv.setLayoutManager(rLayoutManager);
        rRv.setAdapter(rAdapter);

        //底部左右方向轮播
        bAdapter = new RvBottomAdapter(aDList);
        bLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        bRv.setLayoutManager(bLayoutManager);
        bRv.setAdapter(bAdapter);

        mImg.setImageResource(R.drawable.banner_forever_bu);

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
}
