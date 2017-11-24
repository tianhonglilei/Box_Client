package box.lilei.box_client.client.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Timer;

import box.lilei.box_client.R;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.MyTime;
import box.lilei.box_client.client.model.PercentInfo;
import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.client.model.RoadInfo;
import box.lilei.box_client.client.presenter.PayPresenter;
import box.lilei.box_client.client.presenter.WeatherPresenter;
import box.lilei.box_client.client.presenter.impl.PayPresenterImpl;
import box.lilei.box_client.client.view.PayView;
import box.lilei.box_client.contants.Constants;
import box.lilei.box_client.loading.ZLoadingDialog;
import box.lilei.box_client.loading.ZLoadingView;
import box.lilei.box_client.loading.Z_TYPE;
import box.lilei.box_client.util.SharedPreferencesUtil;
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
    //营养成分
    @BindView(R.id.pay_percent_energy1)
    TextView payPercentEnergy1;
    @BindView(R.id.pay_percent_energy2)
    TextView payPercentEnergy2;
    @BindView(R.id.pay_percent_protein1)
    TextView payPercentProtein1;
    @BindView(R.id.pay_percent_protein2)
    TextView payPercentProtein2;
    @BindView(R.id.pay_percent_fat1)
    TextView payPercentFat1;
    @BindView(R.id.pay_percent_fat2)
    TextView payPercentFat2;
    @BindView(R.id.pay_percent_cwater1)
    TextView payPercentCwater1;
    @BindView(R.id.pay_percent_cwater2)
    TextView payPercentCwater2;
    @BindView(R.id.pay_percent_na1)
    TextView payPercentNa1;
    @BindView(R.id.pay_percent_na2)
    TextView payPercentNa2;
    //商品信息
    @BindView(R.id.pay_img_goods)
    ImageView payImgGoods;
    @BindView(R.id.pay_txt_goods_name)
    TextView payTxtGoodsName;
    @BindView(R.id.pay_img_wd)
    ImageView payImgWd;
    //数量
    @BindView(R.id.pay_rb_num_one)
    RadioButton payRbNumOne;
    @BindView(R.id.pay_rb_num_two)
    RadioButton payRbNumTwo;
    //价格
    @BindView(R.id.pay_txt_goods_price)
    TextView payTxtGoodsPrice;
    @BindView(R.id.pay_txt_goods_price_count)
    TextView payTxtGoodsPriceCount;
    //二维码
    @BindView(R.id.pay_img_qrcode)
    ImageView payImgQrcode;
    @BindView(R.id.pay_txt_qrcode_loading)
    TextView payTxtQrcodeLoading;
    @BindView(R.id.pay_qrcode_loading)
    ZLoadingView payQrcodeLoading;
    @BindView(R.id.more_imei_num)
    TextView moreImeiNum;
    private Bitmap bitmapWxPayOne, bitmapWxPayTwo, bitmapAliPayOne, bitmapAliPayTwo;

    //支付和数量的选择按钮
    @BindView(R.id.pay_rbgrp_num)
    RadioGroup payRbgrpNum;
    @BindView(R.id.pay_rbgrp_qrcode)
    RadioGroup payRbgrpQrcode;


    private Context mContext;
    private Intent dataIntent;
    private WeatherPresenter weatherPresenter;
    private Timer timer;

    private ZLoadingDialog dialog;

    //业务处理
    private PayPresenter payPresenter;
    //商品货道信息
    private RoadGoods roadGoods;
    private RoadInfo roadInfo;
    private Goods goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_pay_activity);
        ButterKnife.bind(this);
        mContext = this;
        dataIntent = this.getIntent();
        roadGoods = dataIntent.getParcelableExtra("roadGoods");
        payPresenter = new PayPresenterImpl(this, this);
        initLayoutRadioButton();
        initFont();
        initDateAndWeather();

        //显示机器号
        moreImeiNum.setText(SharedPreferencesUtil.getString(mContext, "box_id"));

        //初始化营养成分
        payPresenter.initPercenterInfo(roadGoods.getGoods().getGoodsId());

        //初始化商品信息
        initGoodsInfo();
        initRadioGroup();
        payQrcodeLoading.setLoadingBuilder(Z_TYPE.values()[1]);
        payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, goods, roadInfo);


    }


    private int checkNum = 1;
    private int checkPay = Constants.PAY_TYPE_WX;
    private String payQRCodeUrl = Constants.WX_GET_QR_URL;

    /**
     * 初始化选择按钮
     */
    private void initRadioGroup() {
        payRbgrpNum.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.pay_rb_num_one) {
                    checkNum = 1;
                    payTxtGoodsPriceCount.setText("" + goods.getGoodsPrice());
                    if (checkPay == Constants.PAY_TYPE_WX) {
                        if (bitmapWxPayOne == null) {
                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, goods, roadInfo);
                        } else {
                            showQRCode(bitmapWxPayOne);
                        }
                    } else {
                        if (bitmapAliPayOne == null) {
                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, goods, roadInfo);
                        } else {
                            showQRCode(bitmapAliPayOne);
                        }
                    }
                } else {
                    checkNum = 2;
                    payTxtGoodsPriceCount.setText("" + goods.getGoodsPrice() * 2);
                    if (checkPay == Constants.PAY_TYPE_WX) {
                        if (bitmapWxPayTwo == null) {
                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, goods, roadInfo);
                        } else {
                            showQRCode(bitmapWxPayTwo);
                        }
                    } else {
                        if (bitmapAliPayTwo == null) {
                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, goods, roadInfo);
                        } else {
                            showQRCode(bitmapAliPayTwo);
                        }
                    }
                }

            }
        });
        payRbgrpQrcode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.pay_rb_wechat) {
                    checkPay = Constants.PAY_TYPE_WX;
                    payQRCodeUrl = Constants.WX_GET_QR_URL;
                    if (checkNum == 1) {
                        if (bitmapWxPayOne == null) {
                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, goods, roadInfo);
                        } else {
                            showQRCode(bitmapWxPayOne);
                        }
                    } else {
                        if (bitmapWxPayTwo == null) {
                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, goods, roadInfo);
                        } else {
                            showQRCode(bitmapWxPayTwo);
                        }
                    }
                } else {
                    checkPay = Constants.PAY_TYPE_ALI;
                    payQRCodeUrl = Constants.ALI_GET_QR_URL;
                    if (checkNum == 1) {
                        if (bitmapAliPayOne == null) {
                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, goods, roadInfo);
                        } else {
                            showQRCode(bitmapAliPayOne);
                        }
                    } else {
                        if (bitmapAliPayTwo == null) {
                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, goods, roadInfo);
                        } else {
                            showQRCode(bitmapAliPayTwo);
                        }
                    }
                }
            }
        });
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
     * 初始化天气
     */
    private void initDateAndWeather() {
        if (dataIntent != null) {
            moreWeatherTxt.setText(dataIntent.getStringExtra("weather"));
            moreWeatherWdNum.setText(dataIntent.getStringExtra("temp"));
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 3:

                    break;
            }


        }
    };


    /**
     * 初始化商品信息
     */
    public void initGoodsInfo() {
        roadInfo = roadGoods.getRoadInfo();
        goods = roadGoods.getGoods();
        payTxtGoodsDetailsMemo.setText(goods.getGoodsMemo());
        File file = new File(Constants.DEMO_FILE_PATH + "/" + goods.getGoodsBImgName());
        Glide.with(this).load(file).into(payImgGoods);
        payTxtGoodsName.setText(goods.getGoodsName());
        switch (goods.getGoodsWd()) {
            case Goods.GOODS_WD_COLD:
                payImgWd.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(R.mipmap.logo_cold).into(payImgWd);
                break;
            case Goods.GOODS_WD_HOT:
                payImgWd.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(R.mipmap.logo_hot).into(payImgWd);
                break;
            case Goods.GOODS_WD_NORMAL:
                payImgWd.setVisibility(View.INVISIBLE);
                break;
        }
        payTxtGoodsPrice.setText("" + goods.getGoodsPrice());
        initRadioNum();
        payTxtGoodsPriceCount.setText("" + goods.getGoodsPrice());
    }

    /**
     * 初始化数量选择
     */
    private void initRadioNum() {
        payRbNumTwo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_rl_return:
                PayActivity.this.finish();
                break;
            case R.id.pay_rb_num_two:
//                if(roadInfo.getRoadNowNum()<2){
//                    Toast.makeText(this, "选购商品数量不足2瓶", Toast.LENGTH_SHORT).show();
//                    payRbNumOne.setChecked(true);
//                }

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
    public void showDialog(String text) {
        dialog = new ZLoadingDialog(PayActivity.this);
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
        dialog.cancel();
    }

    @Override
    public void showPercentInfo(PercentInfo percentInfo) {
        String[] ss1 = percentInfo.getEnergy().split("-");
        payPercentEnergy1.setText(ss1[0]);
        payPercentEnergy2.setText(ss1[1]);
        String[] ss2 = percentInfo.getProtein().split("-");
        payPercentProtein1.setText(ss2[0]);
        payPercentProtein2.setText(ss2[1]);
        String[] ss3 = percentInfo.getFat().split("-");
        payPercentFat1.setText(ss3[0]);
        payPercentFat2.setText(ss3[1]);
        String[] ss4 = percentInfo.getcWater().split("-");
        payPercentCwater1.setText(ss4[0]);
        payPercentCwater2.setText(ss4[1]);
        String[] ss5 = percentInfo.getNa().split("-");
        payPercentNa1.setText(ss5[0]);
        payPercentNa2.setText(ss5[1]);
    }

    @Override
    public void showQRCode(Bitmap bitmap) {
        payQrcodeLoading.setVisibility(View.GONE);
        if (bitmap != null) {
            payTxtQrcodeLoading.setText("");
            if (checkNum == 1) {
                if (checkPay == Constants.PAY_TYPE_WX) {
                    bitmapWxPayOne = bitmap;
                } else {
                    bitmapAliPayOne = bitmap;
                }
            } else {
                if (checkPay == Constants.PAY_TYPE_WX) {
                    bitmapWxPayTwo = bitmap;
                } else {
                    bitmapAliPayTwo = bitmap;
                }
            }
            payImgQrcode.setImageBitmap(bitmap);
        } else {
            payTxtQrcodeLoading.setText("二维码生成失败");
        }

    }

    @Override
    public void loadQRCode() {
        payImgQrcode.setImageResource(R.drawable.pay_demo_qrcode);
        payTxtQrcodeLoading.setText("二维码正在生成...");
        payQrcodeLoading.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payPresenter = null;
        weatherPresenter = null;
        //回收bitmap
        recycleBitmap(bitmapAliPayOne);
        recycleBitmap(bitmapAliPayTwo);
        recycleBitmap(bitmapWxPayOne);
        recycleBitmap(bitmapWxPayTwo);
    }

    private void recycleBitmap(Bitmap bitmap){
        if (bitmap!=null){
            bitmap.recycle();
            bitmap = null;
        }
    }

}
