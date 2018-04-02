package com.zhang.box.client.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.content.res.ResourcesCompat;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.common.controls.dialog.CommonDialogFactory;
import com.common.controls.dialog.DialogUtil;
import com.common.controls.dialog.ICommonDialog;
import com.zhang.box.R;
import com.zhang.box.box.BoxAction;
import com.zhang.box.box.BoxParams;
import com.zhang.box.box.BoxSetting;
import com.zhang.box.client.listener.OutGoodsListener;
import com.zhang.box.client.model.Goods;
import com.zhang.box.client.model.PercentInfo;
import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.model.RoadInfo;
import com.zhang.box.client.model.paramsmodel.AddGoods;
import com.zhang.box.client.pos.Demo;
import com.zhang.box.client.pos.PosRequest;
import com.zhang.box.client.pos.SerialPortActivity;
import com.zhang.box.client.pos.SerialTool;
import com.zhang.box.client.pos.TLVBody;
import com.zhang.box.client.presenter.PayPresenter;
import com.zhang.box.client.presenter.impl.PayPresenterImpl;
import com.zhang.box.client.receiver.GoodsBroadcastReceiver;
import com.zhang.box.client.view.PayView;
import com.zhang.box.contants.Constants;
import com.zhang.box.dialog.CustomDialog;
import com.zhang.box.loading.ZLoadingDialog;
import com.zhang.box.loading.ZLoadingView;
import com.zhang.box.loading.Z_TYPE;
import com.zhang.box.util.SharedPreferencesUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayActivity extends SerialPortActivity implements View.OnClickListener, PayView, OutGoodsListener {

    private static final String TAG = "PayActivity";

    @BindView(R.id.pay_rb_wechat)
    RadioButton payRbWechat;
    @BindView(R.id.pay_rb_ali)
    RadioButton payRbAli;
    @BindView(R.id.pay_txt_goods_details_memo)
    TextView payTxtGoodsDetailsMemo;
    //返回按钮
    @BindView(R.id.pay_rl_return)
    RelativeLayout payRlReturn;

    CountDownTimer countDownTimer;

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
    @BindView(R.id.pay_txt_count_rmb)
    TextView payTxtJiFen;
    @BindView(R.id.pay_txt_left_score)
    TextView payTxtLeftScore;

    //二维码
    @BindView(R.id.pay_img_qrcode)
    ImageView payImgQrcode;
    @BindView(R.id.pay_txt_qrcode_loading)
    TextView payTxtQrcodeLoading;
    @BindView(R.id.pay_qrcode_loading)
    ZLoadingView payQrcodeLoading;
    @BindView(R.id.more_imei_num)
    TextView moreImeiNum;
    @BindView(R.id.pay_txt_return_time)
    TextView payTxtReturnTime;
    @BindView(R.id.date_img_signal)
    ImageView dateImgSignal;

    //开始支付
    @BindView(R.id.pay_btn_start_pay)
    Button payBtnStartPay;

    private Bitmap bitmapWxPayOne, bitmapWxPayTwo, bitmapAliPayOne, bitmapAliPayTwo;

    //支付和数量的选择按钮
    @BindView(R.id.pay_rbgrp_num)
    RadioGroup payRbgrpNum;
    @BindView(R.id.pay_rbgrp_qrcode)
    RadioGroup payRbgrpQrcode;


    private Context mContext;
    private Intent dataIntent;

    private ZLoadingDialog dialog;

    //业务处理
    private PayPresenter payPresenter;
    //商品货道信息
    private RoadGoods roadGoods;
    private RoadInfo roadInfo;
    private Goods goods;

    GoodsBroadcastReceiver goodsBroadcastReceiver;
    //出货数量
    private int num = 1;

    private PopupWindow window;

    //语音提示
    MediaPlayer mediaPlayer = null;
    AssetManager assetManager = null;

    //支付选择弹窗
    View selectPayDialogView;
    View.OnClickListener listener;
    CustomDialog selectPayDialog, waitDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_pay_activity);
        ButterKnife.bind(this);
//        BaseApplication.addActivityToList(this);
        mContext = this;
        dataIntent = this.getIntent();
        roadGoods = dataIntent.getParcelableExtra("roadGoods");
        if (roadGoods == null) {
            returnAndFinish();
        }
        payPresenter = new PayPresenterImpl(this, this, handler);
        initLayoutRadioButton();
        initFont();
        initDateAndWeather();

        //显示机器号
        moreImeiNum.setText(SharedPreferencesUtil.getString(mContext, BoxParams.BOX_ID));

        //初始化营养成分
        payPresenter.initPercenterInfo(roadGoods.getGoods().getGoodsId());

        //初始化商品信息
        initGoodsInfo();
        initRadioGroup();


        //测试pos机出货 注释二维码访问网络接口
//        payQrcodeLoading.setLoadingBuilder(Z_TYPE.values()[1]);
//        payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, roadGoods);

//        payImgQrcode.setOnClickListener(this);


        if (roadGoods.getRoadInfo().getRoadBoxType().equals(BoxSetting.BOX_TYPE_DRINK)) {
            registerGoodsBoradcastReceiver();
        }

        int request = dataIntent.getIntExtra("result", 0);
        if (request == 2) {
            initMediaPlayer(1);
        }

        initSignListener();

        initCountDownTimer();

        payBtnStartPay.setOnClickListener(this);

        //初始化pos机
        initPos();

        //初始化选择支付弹窗
        showPayChoiceDialog();

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
                    payTxtGoodsPriceCount.setText("" + Double.parseDouble(payTxtGoodsPrice.getText().toString()));
//                    if (checkPay == Constants.PAY_TYPE_WX) {
//                        if (bitmapWxPayOne == null) {
//                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, roadGoods);
//                        } else {
//                            showQRCode(bitmapWxPayOne);
//                        }
//                    } else {
//                        if (bitmapAliPayOne == null) {
//                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, roadGoods);
//                        } else {
//                            showQRCode(bitmapAliPayOne);
//                        }
//                    }
                } else {
                    if (roadInfo.getRoadNowNum() < 2) {
                        payRbNumTwo.setClickable(false);
                        Toast.makeText(mContext, "非常抱歉，只剩一个咯", Toast.LENGTH_SHORT).show();
                        payRbNumOne.setChecked(true);
                        return;
                    }
                    checkNum = 2;
                    payTxtGoodsPriceCount.setText("" + Double.parseDouble(payTxtGoodsPrice.getText().toString()) * 2);


//                    if (checkPay == Constants.PAY_TYPE_WX) {
//                        if (bitmapWxPayTwo == null) {
//                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, roadGoods);
//                        } else {
//                            showQRCode(bitmapWxPayTwo);
//                        }
//                    } else {
//                        if (bitmapAliPayTwo == null) {
//                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, roadGoods);
//                        } else {
//                            showQRCode(bitmapAliPayTwo);
//                        }
//                    }
                }

            }
        });
        payRbgrpQrcode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.pay_rb_wechat) {
                    checkPay = Constants.PAY_TYPE_WX;
                    payQRCodeUrl = Constants.WX_GET_QR_URL;
//                    if (checkNum == 1) {
//                        if (bitmapWxPayOne == null) {
//                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, roadGoods);
//                        } else {
//                            showQRCode(bitmapWxPayOne);
//                        }
//                    } else {
//                        if (bitmapWxPayTwo == null) {
//                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, roadGoods);
//                        } else {
//                            showQRCode(bitmapWxPayTwo);
//                        }
//                    }
                } else {
                    checkPay = Constants.PAY_TYPE_ALI;
                    payQRCodeUrl = Constants.ALI_GET_QR_URL;
//                    if (checkNum == 1) {
//                        if (bitmapAliPayOne == null) {
//                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, roadGoods);
//                        } else {
//                            showQRCode(bitmapAliPayOne);
//                        }
//                    } else {
//                        if (bitmapAliPayTwo == null) {
//                            payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, roadGoods);
//                        } else {
//                            showQRCode(bitmapAliPayTwo);
//                        }
//                    }
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
        drawableWechat.setBounds(0, 0, 100, 100);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        payRbWechat.setCompoundDrawables(null, drawableWechat, null, null);//只放上面
        Drawable drawableAli = ResourcesCompat.getDrawable(getResources(), R.drawable.pay_ali_img_selector, null);
        drawableAli.setBounds(0, 0, 100, 100);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
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
        double price = goods.getGoodsPrice();
        if (goods.getGoodsSaleState() == Goods.SALE_STATE_DISCOUNT) {
            double disPrice = goods.getGoodsDiscountPrice();
            if (price > disPrice) {
                price = disPrice;
            }
        }
        payTxtGoodsPrice.setText("" + price);
        payTxtGoodsPriceCount.setText("" + price);
        payTxtLeftScore.setText("" + (int) (price * 550));
        payTxtJiFen.setText("" + (int) (price * 550));
        initRadioNum();
        payTxtGoodsPriceCount.setText("" + price);
        payTxtGoodsDetailsMemo.setText(goods.getGoodsMemo());
    }

    /**
     * 初始化数量选择
     */
    private void initRadioNum() {
        payRbNumTwo.setOnClickListener(this);
    }


    //支付完成倒计时
    CountDownTimer paySureCountTimer;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_rl_return:
                returnAndFinish();
                break;
            case R.id.pay_rb_num_two:

                break;
            case R.id.pay_img_qrcode:
                payPresenter.getQRCode(payQRCodeUrl, Double.parseDouble(payTxtGoodsPriceCount.getText().toString()), checkPay, checkNum, roadGoods);
                break;
            case R.id.pay_btn_start_pay:
                startXingyeCardPay();
                break;
        }
    }

    private void startXingyeCardPay() {
        selectPayDialog.show();

    }


    private void returnAndFinish() {
        if (paySureCountTimer != null) {
            paySureCountTimer.cancel();
            paySureCountTimer = null;
        }
        payPresenter.cancelOrder();
        PayActivity.this.finish();
    }


    boolean dialogShow = false;

    @Override
    public void showDialog(String text) {
        dialogShow = true;
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
        dialogShow = false;
        if (dialog != null)
            dialog.cancel();
    }

    @Override
    public void showPercentInfo(PercentInfo percentInfo) {
        String[] ss1 = percentInfo.getEnergy().split("-");
        if (ss1.length > 1) {
            payPercentEnergy1.setText(ss1[0]);
            payPercentEnergy2.setText(ss1[1]);
        }
        String[] ss2 = percentInfo.getProtein().split("-");
        if (ss2.length > 1) {
            payPercentProtein1.setText(ss2[0]);
            payPercentProtein2.setText(ss2[1]);
        }
        String[] ss3 = percentInfo.getFat().split("-");
        if (ss3.length > 1) {
            payPercentFat1.setText(ss3[0]);
            payPercentFat2.setText(ss3[1]);
        }
        String[] ss4 = percentInfo.getcWater().split("-");
        if (ss4.length > 1) {
            payPercentCwater1.setText(ss4[0]);
            payPercentCwater2.setText(ss4[1]);
        }
        String[] ss5 = percentInfo.getNa().split("-");
        if (ss5.length > 1) {
            payPercentNa1.setText(ss5[0]);
            payPercentNa2.setText(ss5[1]);
        }
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
            payImgQrcode.setClickable(false);
            if (!countTimeStart) {
                initCountDownTimer();
            }
        } else {
            payTxtQrcodeLoading.setText("生成失败,点击刷新二维码");
            payImgQrcode.setClickable(true);
        }

    }

    /**
     * 轮询支付接口
     */
    private void requestTimerStart() {
        countTimeStart = true;
        if (payPresenter == null) {
            payPresenter = new PayPresenterImpl(mContext, this, handler);
        }
        if (bitmapWxPayOne != null) {
            payPresenter.chengePayRequest(1, Constants.PAY_TYPE_WX);
        }
        if (bitmapWxPayTwo != null) {
            payPresenter.chengePayRequest(2, Constants.PAY_TYPE_WX);
        }
        if (bitmapAliPayOne != null) {
            payPresenter.chengePayRequest(1, Constants.PAY_TYPE_ALI);
        }
        if (bitmapAliPayTwo != null) {
            payPresenter.chengePayRequest(2, Constants.PAY_TYPE_ALI);
        }

    }

    @Override
    public void cancelRequest() {
        if (bitmapWxPayOne != null) {
            bitmapWxPayOne = null;
        }
        if (bitmapWxPayTwo != null) {
            bitmapWxPayTwo = null;
        }
        if (bitmapAliPayOne != null) {
            bitmapAliPayOne = null;
        }
        if (bitmapAliPayTwo != null) {
            bitmapAliPayTwo = null;
        }
    }

    @Override
    public void showPayChoiceDialog() {

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.pay_select_dialog_btn_score:
                        //积分支付
                        int score = (int)(Double.parseDouble(payTxtGoodsPrice.getText().toString()) * 100);
                        send(2, score);
                        selectPayDialog.dismiss();
                        showCardPayNoticePop();
                        countDownTimer.cancel();
                        break;
                    case R.id.pay_select_dialog_btn_money:
                        //金额支付
                        int money = (int)(Double.parseDouble(payTxtGoodsPrice.getText().toString()) * 100);
                        send(1, money);
                        showCardPayNoticePop();
                        countDownTimer.cancel();
                        break;
                    case R.id.pay_select_dialog_btn_cancel:
                        //取消
                        selectPayDialog.dismiss();
                        break;
                }
            }
        };

        //选择支付弹窗
        CustomDialog.Builder selectbuilder = new CustomDialog.Builder(this);
        selectPayDialog = selectbuilder
                .style(R.style.Dialog)
                .heightDimenRes(R.dimen.payDialogHeight)
                .widthDimenRes(R.dimen.payDialogWidth)
                .cancelTouchout(false)
                .view(R.layout.pay_select_card_dialog)
                .addViewOnclick(R.id.pay_select_dialog_btn_score, listener)
                .addViewOnclick(R.id.pay_select_dialog_btn_money, listener)
                .addViewOnclick(R.id.pay_select_dialog_btn_cancel, listener)
                .build();


        //支付提示弹窗
        CustomDialog.Builder waitbuilder = new CustomDialog.Builder(this);
        waitDialog = waitbuilder
                .style(R.style.Dialog)
                .heightDimenRes(R.dimen.payDialogHeight)
                .widthDimenRes(R.dimen.payDialogWidth)
                .cancelTouchout(false)
                .view(R.layout.pay_pay_wait_dialog)
                .build();


    }


    @Override
    public void showCardPayNoticePop() {
        if (selectPayDialog != null) {
            selectPayDialog.dismiss();
        }
        if (waitDialog != null) {
            waitDialog.show();
        }


    }

    @Override
    public void loadQRCode() {
        payImgQrcode.setImageResource(R.drawable.pay_demo_qrcode);
        payTxtQrcodeLoading.setText("二维码正在生成...");
        payQrcodeLoading.setVisibility(View.VISIBLE);
    }


    @Override
    public void showPopwindow(boolean success, int orderNum, int successNum) {
        countDownTimer.cancel();
        final TextView dialogReturn, dialogMsg;
        View mainView = this.getLayoutInflater().inflate(R.layout.client_pay_activity, null);
        View popupView;
        long count;
        if (success) {
            popupView = this.getLayoutInflater().inflate(R.layout.pay_out_goods_success, null);
            dialogReturn = (TextView) popupView.findViewById(R.id.pay_dialog_success_return_txt);
            count = 4000;
            initMediaPlayer(2);
        } else {
            popupView = this.getLayoutInflater().inflate(R.layout.pay_out_goods_fail, null);
            dialogReturn = (TextView) popupView.findViewById(R.id.pay_dialog_fail_return_txt);
            dialogMsg = (TextView) popupView.findViewById(R.id.pay_dialog_txt_msg);
            dialogMsg.setText("订单数量:" + orderNum + "份，出货数量:" + successNum + "份");
            count = 21000;
            initMediaPlayer(3);
        }
        window = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        window.setFocusable(false);
        window.setOutsideTouchable(false);
        changePopwindowBg(0.8f);
        if (mainView != null)
            window.showAsDropDown(mainView, 0, 0);
        new CountDownTimer(count, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                dialogReturn.setText(millisUntilFinished / 1000 + "秒后自动返回");
            }

            @Override
            public void onFinish() {
                if (window != null) {
                    if (!PayActivity.this.isFinishing() && !PayActivity.this.isDestroyed()) {
                        window.dismiss();
                    }
                }
            }
        }.start();
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                PayActivity.this.setResult(2);
                returnAndFinish();
            }
        });

    }

    private void changePopwindowBg(float alpha) {
        WindowManager.LayoutParams l = this.getWindow().getAttributes();
        l.alpha = alpha;
        getWindow().setAttributes(l);
    }


    @Override
    public void outGoodsCheck(int num) {
        this.num = num;
//        registerGoodsBoradcastReceiver();
    }

    boolean isRegister = false;

    private void registerGoodsBoradcastReceiver() {
        goodsBroadcastReceiver = new GoodsBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BoxAction.OUT_GOODS_RECEIVER_ACTION);
        if (isRegister == false) {
            mContext.registerReceiver(goodsBroadcastReceiver, filter);
            isRegister = true;
        }
        goodsBroadcastReceiver.setOutGoodsListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Glide.with(mContext).pauseRequests();
        if (payPresenter != null)
            payPresenter = null;
        //回收bitmap
        recycleBitmap(bitmapAliPayOne);
        recycleBitmap(bitmapAliPayTwo);
        recycleBitmap(bitmapWxPayOne);
        recycleBitmap(bitmapWxPayTwo);
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        if (goodsBroadcastReceiver != null) {
            if (isRegister == true) {
                unregisterReceiver(goodsBroadcastReceiver);
                isRegister = false;
            }
        }
        if (telephonyManager != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
    }

    private void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.recycle();
        }
    }

    boolean countTimeStart = false;
    long showTime = 0;

    private void initCountDownTimer() {
        countDownTimer = new CountDownTimer(100000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long time = millisUntilFinished / 1000;
                if (time % 2 == 0) {
                    requestTimerStart();
                }
                if (dialogShow) {
                    if (showTime == 0) {
                        showTime = time;
                    }
                }
                if (dialogShow && time == showTime - 20) {
                    hiddenDialog();
                    payPresenter.postOrder(num, successNum);
                }
                if (time < 15) {
                    payTxtReturnTime.setTextColor(getResources().getColor(R.color.colorDemoLogo));
                } else {
                    payTxtReturnTime.setTextColor(Color.WHITE);
                }
                payTxtReturnTime.setText(time + "S");

            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

    private int successNum, failNum;

    @Override
    public void outSuccess() {
        successNum++;
        if (num == successNum + failNum) {
            payPresenter.postOrder(num, successNum);
        }
    }

    @Override
    public void outFail() {
        failNum++;
        if (num == successNum + failNum) {
            payPresenter.postOrder(num, successNum);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    /**
     * 初始化音频播放
     */
    private void initMediaPlayer(int musicId) {
        String path = "";
        switch (musicId) {
            case 1:
                path = "welcome.wav";
                break;
            case 2:
                path = "out_success_music.wav";
                break;
            case 3:
                path = "out_fail_music.wav";
                break;
        }
        mediaPlayer = new MediaPlayer();
        assetManager = getAssets();
        AssetFileDescriptor fileDescriptor;
        try {
            fileDescriptor = assetManager.openFd(path);
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
        telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                super.onSignalStrengthsChanged(signalStrength);
                String signalInfo = signalStrength.toString();
                String[] params = signalInfo.split(" ");
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

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 3:
                    payPresenter.postOrder(2, 2);
                    break;
            }
        }
    };


    //pos  模块

    private void initPos() {
        String bpsStr = "9600";
        int bps = Integer.parseInt(bpsStr);
        //发送数据
        String message = "020034313030310001043030320010323031383031313831343233333230313030340001310313";
        try {
            Log.e(TAG, "initPos: " + "正准备向" + "发送测试数据...");
            SerialTool.sendToPort(Demo.hex2byte(message));

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    /**
     * 发送消息
     *
     * @param type
     * @param money
     */
    public void send(int type, int money) {

        PosRequest pr = new PosRequest();

        TLVBody body1 = new TLVBody();
        body1.setTitle(Demo.strTo16("001"));
        body1.setContent(Demo.longTo16(type, 2));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        TLVBody body2 = new TLVBody();
        body2.setTitle(Demo.strTo16("002"));
        body2.setContent(Demo.strTo16(sdf.format(new Date()) + (10 + (int) (Math.random() * 90))));

        TLVBody body3 = new TLVBody();
        body3.setTitle(Demo.strTo16("004"));
        body3.setContent(Demo.strTo16(money + ""));

        List<TLVBody> tlvList = new ArrayList<>();
        tlvList.add(body1);
        tlvList.add(body2);
        tlvList.add(body3);

        pr.setTlvBody(tlvList);

        try {
            SerialTool.sendToPort(Demo.hex2byte(pr.toHex().toUpperCase(Locale.SIMPLIFIED_CHINESE)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    String useDataStr = "";

    /**
     * 接受pos机返回数据
     *
     * @param data
     * @param size
     */
    @Override
    protected void onDataReceived(byte[] data, int size) {

        String responseData = Demo.printHexString(data);
        Log.e(TAG, "onDataReceived: " + responseData);
        responseData = responseData.replace(" ", "");
        parseDataStr(responseData);
//        for (int i = 0; i < responseData.length(); i++) {
//            if (responseData.charAt(i) == '2') {
//                if (responseData.indexOf(i + 6) < responseData.length()) {
//                    if (responseData.indexOf(i - 1) == 0) {
//                        String lenStr = responseData.substring(i + 2, i + 6);
//                        int len = Integer.parseInt(lenStr, 16);
//                        Log.e(TAG, "onDataReceived: 报文长度" + len);
//                        useDataStr = responseData.substring(i - 1, i + 2 + 4 + len + 4);
//                        parseDataStr(useDataStr);
//                    }
//                }
//            }
//        }


    }

    @Override
    protected void disConnect() {
        if (waitDialog!=null&&waitDialog.isShowing()) {
            waitDialog.dismiss();
            countDownTimer.start();
        }
    }

    private void parseDataStr(String responseData) {
        if (responseData.equals("020007323030310001040302")) {
            Log.e(TAG, "onDataReceived: " + "收到POS响应，已找到POS机端口");
            return;
        }
        Log.e(TAG, "onDataReceived: " + "响应加密：" + responseData);
        PosRequest posRequest = PosRequest.decRequest(responseData);
        String srt3 = posRequest.toString();
        Log.e(TAG, "onDataReceived: " + "响应解密：" + srt3);

        for (int i = 0; i < posRequest.getTlvBody().size(); i++) {
            TLVBody thisTLV = posRequest.getTlvBody().get(i);
            if (thisTLV.getTitle().equals("002")) {
            }
            if (thisTLV.getTitle().equals("039")) {
                if (thisTLV.getContent().equals("00")) {
                    //成功
                    Log.e("CCCCCCCCCCCCCCCCCCCCCCCCC", "成功" + num + roadInfo.getRoadBoxType() + roadInfo.getRoadIndex().toString());
                    if (waitDialog!=null) {
                        waitDialog.dismiss();
                        countDownTimer.start();
                    }
                    showDialog("出货中...");
                    payPresenter.outGoodsAction(num, roadInfo.getRoadBoxType(), roadInfo.getRoadIndex().toString());
                    payPresenter.updateDBNum(roadGoods.getRoadGoodsId(), num);
                } else {
                    //失败
                    Log.e("CCCCCCCCSSSSSSSSSS","失败失败");
                    if (waitDialog!=null) {
                        waitDialog.dismiss();
                        countDownTimer.start();
                    }
                }
            }
        }
    }

}
