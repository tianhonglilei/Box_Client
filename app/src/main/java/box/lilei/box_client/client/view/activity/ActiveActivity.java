package box.lilei.box_client.client.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import box.lilei.box_client.R;
import box.lilei.box_client.client.presenter.ActivePresenter;
import box.lilei.box_client.client.presenter.impl.ActivePresenterImpl;
import box.lilei.box_client.client.view.ActiveView;
import box.lilei.box_client.loading.ZLoadingDialog;
import box.lilei.box_client.loading.Z_TYPE;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ActiveActivity extends Activity implements View.OnClickListener, ActiveView {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_active_activity);
        ButterKnife.bind(this);

        activeBtn.setOnClickListener(this);

        activePresenter = new ActivePresenterImpl(this, this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.active_btn:
                showDialog("加载中...");
                activePresenter.loadAllDataFromUrl("93006709");
                break;
        }

    }

    @Override
    public void changeDownloadProgress(int maxNum, int successNum, int failNum) {
        activeDownloadTxt.setText("下载:" + maxNum + "{成功:" + successNum + "，失败:" + failNum + "}");
        if (maxNum == successNum + failNum){
            hideDialog();
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
        Intent intent = new Intent(ActiveActivity.this,ADBannerActivity.class);
        startActivity(intent);
        finish();
    }
}
