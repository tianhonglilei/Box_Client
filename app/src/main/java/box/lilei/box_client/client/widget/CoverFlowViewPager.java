package box.lilei.box_client.client.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.support.v4.view.ViewPager;

import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.client.adapter.ADViewPagerAdapter;
import box.lilei.box_client.client.listener.OnPageSelectListener;

/**
 * Created by lilei on 2017/9/5.
 * 突出显示的轮播控件
 */

public class CoverFlowViewPager extends RelativeLayout implements OnPageSelectListener   {

    /**
     * 用于左右滚动
     */
    private ViewPager mViewPager;

    private ADViewPagerAdapter mAdapter;

    private List<View> mViewList;


    private OnPageSelectListener listener;


    public CoverFlowViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.client_adbanner_activity,this);
        mViewPager = (ViewPager) findViewById(R.id.vp_conver_flow);
        init();
    }



    /**
     * 初始化方法
     */
    private void init() {
        // 构造适配器，传入数据源
        mAdapter = new ADViewPagerAdapter(mViewList,getContext());
        // 设置选中的回调
        mAdapter.setOnPageSelectListener(this);
        // 设置适配器
        mViewPager.setAdapter(mAdapter);
        // 设置滑动的监听，因为adpter实现了滑动回调的接口，所以这里直接设置adpter
        mViewPager.addOnPageChangeListener(mAdapter);
        // 自己百度
        mViewPager.setOffscreenPageLimit(5);

        // 设置触摸事件的分发
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 传递给ViewPager 进行滑动处理
                return mViewPager.dispatchTouchEvent(event);
            }
        });
    }


    /**
     * 设置显示的数据，进行一层封装
     * @param lists
     */
    public void setViewList(List<View> lists){
        if(lists==null){
            return;
        }
        mViewList.clear();
        for(View view:lists){

            FrameLayout layout = new FrameLayout(getContext());
            // 设置padding 值，默认缩小
            layout.setPadding(ADViewPagerAdapter.sWidthPadding,ADViewPagerAdapter.sHeightPadding,ADViewPagerAdapter.sWidthPadding,ADViewPagerAdapter.sHeightPadding);
            layout.addView(view);
            mViewList.add(layout);
        }
        // 刷新数据
        mAdapter.notifyDataSetChanged();
    }


    // 显示的回调
    @Override
    public void select(int position) {
        if(listener!=null){
            listener.select(position);
        }
    }

    public void setOnPageSelectListener(OnPageSelectListener listener){
        this.listener = listener;
    }


}
