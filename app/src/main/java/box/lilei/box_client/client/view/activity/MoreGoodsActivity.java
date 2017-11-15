package box.lilei.box_client.client.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.Timer;
import java.util.TimerTask;

import box.lilei.box_client.R;
import box.lilei.box_client.client.model.MyTime;
import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.client.presenter.MoreGoodsPresenter;
import box.lilei.box_client.client.presenter.impl.MoreGoodsPresenterImpl;
import box.lilei.box_client.client.view.MoreGoodsView;
import box.lilei.box_client.manager.view.activity.ManagerNavgationActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreGoodsActivity extends Activity implements View.OnClickListener, MoreGoodsView {


    @BindView(R.id.more_imei_num)
    TextView moreImeiNum;
    //向上的GIF
    @BindView(R.id.more_goods_up_gif)
    ImageView moreGoodsUpGif;
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
    @BindView(R.id.more_weather_time)
    TextView moreWeatherTime;
    @BindView(R.id.more_weather_date)
    TextView moreWeatherDate;
    @BindView(R.id.more_weather_txt)
    TextView moreWeatherTxt;
    @BindView(R.id.more_weather_wd_num)
    TextView moreWeatherWdNum;
    private Timer timer;


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
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(3);
            }
        }, 0, 1000 * 30);
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
                Intent intent = new Intent(MoreGoodsActivity.this, PayActivity.class);
                intent.putExtra("minute", moreWeatherTime.getText().toString());
                intent.putExtra("date", moreWeatherDate.getText().toString());
                intent.putExtra("temp", moreWeatherWdNum.getText().toString());
                intent.putExtra("weather", moreWeatherTxt.getText().toString());
                RoadGoods roadGoods = (RoadGoods) moreGoodsGv.getItemAtPosition(position);
                intent.putExtra("roadGoods", roadGoods);
                startActivity(intent);
            }
        });
    }

    private void initControl() {
        moreImeiNum.setOnClickListener(this);
        mContext = this;
        if (moreGoodsPresenter == null) {
            moreGoodsPresenter = new MoreGoodsPresenterImpl(mContext, this);
        }
        moreGoodsPresenter.initAllGoods(moreGoodsGv);

        moreGoodsNavRlReturn.setOnClickListener(this);

        moreGoodsRbtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int navPosition = 0;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        moreGoodsPresenter = null;
        timer = null;
    }

    //处理handler消息
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 3:
                    moreGoodsPresenter.getDateInfo();
                    break;
            }


        }
    };

    @Override
    public void updateDate(MyTime myTime) {
        if (myTime != null) {
            moreWeatherTime.setText(myTime.getTimeMinute());
            moreWeatherDate.setText(myTime.getTimeDay() + " " + myTime.getTimeWeek());
        }
    }

}
