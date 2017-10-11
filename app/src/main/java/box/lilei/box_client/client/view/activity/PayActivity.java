package box.lilei.box_client.client.view.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.RadioButton;

import box.lilei.box_client.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PayActivity extends Activity {

    @BindView(R.id.pay_rb_wechat)
    RadioButton payRbWechat;
    @BindView(R.id.pay_rb_ali)
    RadioButton payRbAli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_pay_activity);
        ButterKnife.bind(this);

        initLayoutRadioButton();

    }

    private void initLayoutRadioButton() {
        Drawable drawableWechat = ResourcesCompat.getDrawable(getResources(),R.drawable.pay_wechat_img_selector,null);
        drawableWechat.setBounds(0, 0, 110, 100);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        payRbWechat.setCompoundDrawables(null, drawableWechat, null, null);//只放上面
        Drawable drawableAli = ResourcesCompat.getDrawable(getResources(),R.drawable.pay_ali_img_selector,null);
        drawableAli.setBounds(0, 0, 100, 100);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        payRbAli.setCompoundDrawables(null, drawableAli, null, null);//只放上面
    }

}
