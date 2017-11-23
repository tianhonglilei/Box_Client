package box.lilei.box_client.client.view.activity;

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

import java.util.Timer;
import java.util.TimerTask;

import box.lilei.box_client.R;
import box.lilei.box_client.box.BoxAction;
import box.lilei.box_client.client.listener.NetEvent;
import box.lilei.box_client.client.presenter.ActivePresenter;
import box.lilei.box_client.client.presenter.impl.ActivePresenterImpl;
import box.lilei.box_client.client.receiver.NetBroadcastReceiver;
import box.lilei.box_client.client.view.ActiveView;
import box.lilei.box_client.loading.ZLoadingDialog;
import box.lilei.box_client.loading.ZLoadingView;
import box.lilei.box_client.loading.Z_TYPE;
import box.lilei.box_client.util.SharedPreferencesUtil;
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
        }, 60000);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.active_btn:
//                activePresenter.loadAllDataFromUrl(BoxSetting.BOX_TEST_ID);
                String code = editActiveCode.getText().toString();
                if (!TextUtils.isEmpty(code)) {
                    SharedPreferencesUtil.putString(mContext, "active_code", code);
                    activePresenter.activeBox(code);
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
//            hideDialog();
            skipToADBannerActivity();
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
    public void hideDialog() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    @Override
    public void skipToADBannerActivity() {
        Intent intent = new Intent(ActiveActivity.this, ADBannerActivity.class);
        startActivity(intent);
        finish();
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
            activePresenter.loadAllDataFromUrl(SharedPreferencesUtil.getString(mContext, "box_id"));
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
        if (loadResult != MainHandler.LOAD_DATA_SUCCESS) {
            exitApplication();
        }
    }

    public void exitApplication() {
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "启动失败，即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.e(TAG, "error : ", e);
            e.printStackTrace();
        }
        //退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
