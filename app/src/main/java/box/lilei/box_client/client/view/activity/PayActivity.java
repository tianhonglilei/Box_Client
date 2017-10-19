package box.lilei.box_client.client.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import box.lilei.box_client.R;
import box.lilei.box_client.client.model.MyTime;
import box.lilei.box_client.client.presenter.WeatherPresenter;
import box.lilei.box_client.client.presenter.impl.WeatherPresenterImpl;
import box.lilei.box_client.client.receiver.DateTimeReceiver;
import box.lilei.box_client.client.view.PayView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PayActivity extends Activity implements View.OnClickListener, PayView {

    @BindView(R.id.pay_rb_wechat)
    RadioButton payRbWechat;
    @BindView(R.id.pay_rb_ali)
    RadioButton payRbAli;
    @BindView(R.id.pay_txt_goods_details_memo)
    TextView payTxtGoodsDetailsMemo;
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
    private Intent dataIntent;
    private WeatherPresenter weatherPresenter;
    private DateTimeReceiver mTimeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_pay_activity);
        ButterKnife.bind(this);
        dataIntent = this.getIntent();
        initLayoutRadioButton();
        initFont();
        initDateAndWeather();

    }


    private void initFont() {
        // 将字体文件保存在assets/fonts/目录下 创建Typeface对象
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/JXK.ttf");
        // 应用字体
        payTxtGoodsDetailsMemo.setTypeface(typeFace);
    }

    private void initLayoutRadioButton() {
        payRlReturn.setOnClickListener(this);
        Drawable drawableWechat = ResourcesCompat.getDrawable(getResources(), R.drawable.pay_wechat_img_selector, null);
        drawableWechat.setBounds(0, 0, 55, 50);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        payRbWechat.setCompoundDrawables(null, drawableWechat, null, null);//只放上面
        Drawable drawableAli = ResourcesCompat.getDrawable(getResources(), R.drawable.pay_ali_img_selector, null);
        drawableAli.setBounds(0, 0, 50, 50);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        payRbAli.setCompoundDrawables(null, drawableAli, null, null);//只放上面
    }

    /**
     * 初始化时间和天气
     */
    private void initDateAndWeather() {
        weatherPresenter = new WeatherPresenterImpl(this);
        mTimeReceiver = DateTimeReceiver.getInstance();
        mTimeReceiver.setWeatherPresenter(weatherPresenter);
        if (dataIntent != null) {
            moreWeatherTime.setText(dataIntent.getStringExtra("minute"));
            moreWeatherDate.setText(dataIntent.getStringExtra("date"));
            moreWeatherTxt.setText(dataIntent.getStringExtra("weather"));
            moreWeatherWdNum.setText(dataIntent.getStringExtra("temp"));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_rl_return:
                PayActivity.this.finish();
                System.gc();
                break;
        }
    }

    @Override
    public void updateDate(MyTime myTime) {
        if (myTime != null) {
            moreWeatherTime.setText(myTime.getTimeMinute());
            moreWeatherDate.setText(myTime.getTimeDay() + " " + myTime.getTimeWeek());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
