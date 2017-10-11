package box.lilei.box_client.client.view.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.RadioButton;
import android.widget.TextView;

import box.lilei.box_client.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PayActivity extends Activity {

    @BindView(R.id.pay_rb_wechat)
    RadioButton payRbWechat;
    @BindView(R.id.pay_rb_ali)
    RadioButton payRbAli;
    @BindView(R.id.pay_txt_goods_details_memo)
    TextView payTxtGoodsDetailsMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_pay_activity);
        ButterKnife.bind(this);

        initLayoutRadioButton();
        initFont();

    }

    private void initFont() {
        // 将字体文件保存在assets/fonts/目录下 创建Typeface对象
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/JXK.ttf");
        // 应用字体
        payTxtGoodsDetailsMemo.setTypeface(typeFace);
    }

    private void initLayoutRadioButton() {
        Drawable drawableWechat = ResourcesCompat.getDrawable(getResources(), R.drawable.pay_wechat_img_selector, null);
        drawableWechat.setBounds(0, 0, 55, 50);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        payRbWechat.setCompoundDrawables(null, drawableWechat, null, null);//只放上面
        Drawable drawableAli = ResourcesCompat.getDrawable(getResources(), R.drawable.pay_ali_img_selector, null);
        drawableAli.setBounds(0, 0, 50, 50);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        payRbAli.setCompoundDrawables(null, drawableAli, null, null);//只放上面
    }

}
