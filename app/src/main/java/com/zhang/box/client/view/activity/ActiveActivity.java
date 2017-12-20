package com.zhang.box.client.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avm.serialport_142.MainHandler;
import com.zhang.box.R;
import com.zhang.box.box.BoxAction;
import com.zhang.box.box.BoxParams;
import com.zhang.box.client.listener.NetEvent;
import com.zhang.box.client.presenter.ActivePresenter;
import com.zhang.box.client.view.ActiveView;
import com.zhang.box.loading.ZLoadingView;
import com.zhang.box.loading.Z_TYPE;

import java.util.Timer;
import java.util.TimerTask;


import com.zhang.box.client.presenter.impl.ActivePresenterImpl;
import com.zhang.box.client.receiver.NetBroadcastReceiver;
import com.zhang.box.loading.ZLoadingDialog;
import com.zhang.box.util.SharedPreferencesUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ActiveActivity extends Activity implements View.OnClickListener, ActiveView, NetEvent {
    private static final String TAG = "ActiveActivity";

    //激活码
    @BindView(R.id.edit_active_code)
    EditText editActiveCode;
    //激活按钮
    @BindView(R.id.active_btn)
    Button activeBtn;
    //激活界面
    @BindView(R.id.active_rl)
    RelativeLayout activeRl;

    //获取数据
    ActivePresenter activePresenter;

    Context mContext;
    //下载进度
    @BindView(R.id.active_download_txt)
    TextView activeDownloadTxt;

    //提示框
    ZLoadingDialog dialog;
    @BindView(R.id.active_bg_loading)
    ZLoadingView activeBgLoading;
    @BindView(R.id.active_bg_txt)
    TextView activeBgTxt;

    //检测网络
    private NetBroadcastReceiver netBroadcastReceiver;
    private int netState;

    private Timer exitTimer;

    @Override
    protected void onStart() {
        super.onStart();
        //注册广播
        if (netBroadcastReceiver == null) {
            netBroadcastReceiver = new NetBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netBroadcastReceiver, filter);
            /**
             * 设置监听
             */
            netBroadcastReceiver.setNetEvent(this);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_active_activity);
        ButterKnife.bind(this);
        mContext = this;

        activeBgLoading.setLoadingBuilder(Z_TYPE.values()[0]);

        activeBtn.setOnClickListener(this);

        activePresenter = new ActivePresenterImpl(this, this);

//        activePresenter.getBoxId();

        exitTimer = new Timer();
        exitTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                exitApplication();
            }
        }, 120000);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.active_btn:
//                activePresenter.loadAllDataFromUrl(BoxSetting.BOX_TEST_ID);
                String code = editActiveCode.getText().toString();
                if (!TextUtils.isEmpty(code)) {
                    activePresenter.activeBox(code);
                    showDialog("激活中...");
                } else {
                    Toast.makeText(mContext, "请输入激活码", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netBroadcastReceiver);
        netBroadcastReceiver = null;
        if (exitTimer != null) {
            exitTimer.cancel();
            exitTimer = null;
        }
    }

    @Override
    public void changeDownloadProgress(int maxNum, int successNum, int failNum) {
        activeDownloadTxt.setText("下载:" + maxNum + "{成功:" + successNum + "，失败:" + failNum + "}");
        if (maxNum == successNum + failNum) {
            hiddenDialog();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    activePresenter.saveBoxSetting();
                }
            },13000);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    skipToADBannerActivity();
                }
            },15000);
        }
    }

    @Override
    public void showDialog(String text) {
        dialog = new ZLoadingDialog(ActiveActivity.this);
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
        if (dialog != null) {
            dialog.cancel();
            dialog = null;
        }
    }

    @Override
    public void skipToADBannerActivity() {
        if (BoxAction.getAVMRunning()){
            Intent intent = new Intent(ActiveActivity.this, ADBannerActivity.class);
            startActivity(intent);
            finish();
        }else{
            restartApp();
        }

    }

    @Override
    public void showActiveLayout() {
        if (activeRl.getVisibility() == View.INVISIBLE) {
            activeRl.setVisibility(View.VISIBLE);
            activeBgLoading.setVisibility(View.INVISIBLE);
            activeBgTxt.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void hiddenActiveLayout(boolean success) {
        if (activeRl.getVisibility() == View.VISIBLE) {
            activeRl.setVisibility(View.INVISIBLE);
            activeBgLoading.setVisibility(View.VISIBLE);
            activeBgTxt.setVisibility(View.VISIBLE);
        }
        if (success) {
            activePresenter.loadAllDataFromUrl(SharedPreferencesUtil.getString(mContext, BoxParams.BOX_ID));
        }
    }


    @Override
    public void onNetChange(int netMobile) {
        netState = netMobile;
        switch (netState) {
            case 0://移动数据
            case 1://wifi
            case 2://以太网
                if (exitTimer != null) {
                    exitTimer.cancel();
                    exitTimer = null;
                }
//                activePresenter.getBoxId();
                initBoxCheck();
                break;
            case -1://没有网络

                break;
        }
    }

    public void initBoxCheck() {
        int loadResult = MainHandler.load(mContext.getApplicationContext());
        if (loadResult == MainHandler.ERROR_NO_SDCARD) {
            Toast.makeText(mContext, "系统没有内存卡", Toast.LENGTH_SHORT).show();
        } else if (loadResult == MainHandler.ERROR_EMPTY_DATA) {
            Toast.makeText(mContext, "串口信息没有配置或者读取失败", Toast.LENGTH_SHORT).show();
        } else if (loadResult == MainHandler.ERROR_NET_NOT_AVAILABLE) {
            Toast.makeText(mContext, "系统没有连接网络", Toast.LENGTH_SHORT).show();
        } else if (loadResult == MainHandler.LOAD_DATA_SUCCESS) {
            Toast.makeText(mContext, "加载成功", Toast.LENGTH_SHORT).show();
            activePresenter.getBoxId();
        } else {
            Toast.makeText(mContext, "其他错误", Toast.LENGTH_SHORT).show();
        }

    }

    public void exitApplication() {
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "启动失败，即将重启.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        try {
            Thread.sleep(2000);
            //退出程序
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(1);
            restartApp();
        } catch (InterruptedException e) {
            Log.e(TAG, "error : ", e);
            e.printStackTrace();
        }

    }

    /**
     * 重启程序
     */
    public void restartApp() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        },2000);
    }



    @Override
    protected void onResume() {
        super.onResume();

    }
}
