package com.zhang.box.manager.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.controls.dialog.CommonDialogFactory;
import com.common.controls.dialog.DialogUtil;
import com.common.controls.dialog.ICommonDialog;
import com.zhang.box.R;
import com.zhang.box.application.BaseApplication;
import com.zhang.box.box.BoxAction;
import com.zhang.box.manager.view.fragment.NavExitApplicationFragment;
import com.zhang.box.manager.view.fragment.NavExitManagerFragment;
import com.zhang.box.manager.view.fragment.NavGoodsFragment;
import com.zhang.box.manager.view.fragment.NavRoadFragment;
import com.zhang.box.manager.view.fragment.NavSettingFragment;
import com.zhang.box.manager.view.fragment.NavTempFragment;
import com.zhang.box.manager.view.fragment.NavVersionFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerNavgationActivity extends FragmentActivity {


    //页面切换模块
    @BindView(R.id.manager_nav_grp)
    RadioGroup managerNavGrp;
    //信号图片
    @BindView(R.id.manager_head_img_sign)
    ImageView managerHeadImgSign;
    @BindView(R.id.manager_head_txt_imei)
    TextView managerHeadTxtImei;
    private NavGoodsFragment navGoodsFragment;
    private NavRoadFragment navRoadFragment;
    private NavTempFragment navTempFragment;
    private NavVersionFragment navVersionFragment;
    private NavSettingFragment navSettingFragment;
    private NavExitManagerFragment navExitManagerFragment;
    private NavExitApplicationFragment navExitApplicationFragment;
    private Fragment[] fragments;
    private int mIndex;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_navgation);
        ButterKnife.bind(this);
        mContext = this;
        BaseApplication.addActivityToList(this);
        managerHeadTxtImei.setText(BoxAction.getBoxIdFromSP(mContext));

        initView();
        initFragment();
        initSignListener();

    }

    private void initView() {
        managerNavGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkId) {
                switch (checkId) {
                    case R.id.manager_nav_rdo_btn_goods:
                        setIndexSelected(0);
                        break;
                    case R.id.manager_nav_rdo_btn_road_test:
                        setIndexSelected(1);
                        break;
                    case R.id.manager_nav_rdo_btn_temp:
                        setIndexSelected(2);
                        break;
                    case R.id.manager_nav_rdo_btn_version:
                        setIndexSelected(3);
                        break;
                    case R.id.manager_nav_rdo_btn_continue:
                        setIndexSelected(4);
                        break;
                    case R.id.manager_nav_rdo_btn_restart:
                        setIndexSelected(5);
//                        showOkCancelDialog();
                        break;
                    case R.id.manager_nav_rdo_btn_exit:
                        setIndexSelected(6);
                        break;
                }

            }
        });

    }

    public void showOkCancelDialog() {
        final ICommonDialog okDialog = CommonDialogFactory.createDialogByType(mContext, DialogUtil.DIALOG_TYPE_1);
        okDialog.setTitleText("确定退出后台并继续销售？");
        okDialog.setOkBtnStyleType(DialogUtil.OK_BTN_LARGE_BLUE_BG_WHITE_TEXT);
        okDialog.setOkBtn(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnAndFinish();
                okDialog.dismiss();
            }
        });
        okDialog.setCancelBtn(R.string.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okDialog.dismiss();
            }
        });
        okDialog.setCanceledOnTouchOutside(false);
        okDialog.show();
    }


    private void returnAndFinish() {
        ManagerNavgationActivity.this.setResult(2);
        ManagerNavgationActivity.this.finish();
    }


    //默认补货界面
    private void initFragment() {
        navGoodsFragment = new NavGoodsFragment();
        navRoadFragment = new NavRoadFragment();
        navTempFragment = new NavTempFragment();
        navVersionFragment = new NavVersionFragment();
        navSettingFragment = new NavSettingFragment();
        navExitManagerFragment = new NavExitManagerFragment();
        navExitApplicationFragment = new NavExitApplicationFragment();
        //添加到数组
        fragments = new Fragment[]{navGoodsFragment, navRoadFragment, navTempFragment, navVersionFragment, navSettingFragment, navExitManagerFragment, navExitApplicationFragment};
        //开启事务
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //添加首页
        fragmentTransaction.add(R.id.manager_nav_main_fragment_rl, navGoodsFragment).commit();
        //默认设置为第0个
        setIndexSelected(0);
    }

    //选中显示与隐藏
    private void setIndexSelected(int index) {

        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //隐藏
        fragmentTransaction.hide(fragments[mIndex]);
        //判断是否添加
        if (!fragments[index].isAdded()) {
            fragmentTransaction.add(R.id.manager_nav_main_fragment_rl, fragments[index]).show(fragments[index]);
        } else {
            fragmentTransaction.show(fragments[index]);
        }

        fragmentTransaction.commit();
        //再次赋值
        mIndex = index;

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
                switch (telephonyManager.getNetworkType()) {
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                    case TelephonyManager.NETWORK_TYPE_LTE:
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
                        break;
                    default:
                            changeSignSize(0);
                        break;

                }
            }
        };
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }


    public void changeSignSize(int level) {
        switch (level) {
            case 0:
                managerHeadImgSign.setImageResource(R.drawable.sign_no);
                break;
            case 1:
                managerHeadImgSign.setImageResource(R.drawable.sign_one);
                break;
            case 2:
                managerHeadImgSign.setImageResource(R.drawable.sign_two);
                break;
            case 3:
                managerHeadImgSign.setImageResource(R.drawable.sign_three);
                break;
            case 4:
                managerHeadImgSign.setImageResource(R.drawable.sign_four);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_NONE);
//        Glide.with(mContext).pauseRequests();
    }
}



