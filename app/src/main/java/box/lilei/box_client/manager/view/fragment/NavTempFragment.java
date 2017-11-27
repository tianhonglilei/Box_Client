package box.lilei.box_client.manager.view.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import box.lilei.box_client.R;
import box.lilei.box_client.box.BoxParams;
import box.lilei.box_client.loading.ZLoadingDialog;
import box.lilei.box_client.loading.Z_TYPE;
import box.lilei.box_client.manager.presenter.NavTempPresenter;
import box.lilei.box_client.manager.presenter.impl.NavTempPresenterImpl;
import box.lilei.box_client.manager.view.NavTempFragmentView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavTempFragment extends Fragment implements NavTempFragmentView, View.OnClickListener {


    @BindView(R.id.nav_temp_left_rdo_cold)
    RadioButton navTempLeftRdoCold;
    @BindView(R.id.nav_temp_left_rdo_hot)
    RadioButton navTempLeftRdoHot;
    @BindView(R.id.nav_temp_left_rdo_close)
    RadioButton navTempLeftRdoClose;
    @BindView(R.id.nav_temp_btn_refresh)
    Button navTempBtnRefresh;

    List<RadioButton> leftRdos,rightRdos;


    @BindView(R.id.nav_temp_right_rdo_cold)
    RadioButton navTempRightRdoCold;
    @BindView(R.id.nav_temp_right_rdo_hot)
    RadioButton navTempRightRdoHot;
    @BindView(R.id.nav_temp_right_rdo_close)
    RadioButton navTempRightRdoClose;


    @BindView(R.id.nav_temp_rdo_group_left)
    RadioGroup navTempRdoGroupLeft;
    @BindView(R.id.nav_temp_rdo_group_right)
    RadioGroup navTempRdoGroupRight;


    @BindView(R.id.nav_edit_set_temp_cold)
    EditText navEditSetTempCold;
    @BindView(R.id.nav_edit_set_temp_hot)
    EditText navEditSetTempHot;
    @BindView(R.id.nav_temp_btn_ok)
    Button navTempBtnOk;

    Unbinder unbinder;
    @BindView(R.id.nav_temp_txt_left_now_temp)
    TextView navTempTxtLeftNowTemp;
    @BindView(R.id.nav_temp_txt_right_now_temp)
    TextView navTempTxtRightNowTemp;

    private Context mContext;
    private Timer timer;
    private NavTempPresenter navTempPresenter;
    private ZLoadingDialog dialog;


    public NavTempFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_temp, container, false);
        mContext = this.getContext();
        unbinder = ButterKnife.bind(NavTempFragment.this, view);

        navTempPresenter = new NavTempPresenterImpl(mContext, this);

        navTempBtnOk.setOnClickListener(this);
        navTempBtnRefresh.setOnClickListener(this);

        leftRdos = new ArrayList<>();
        leftRdos.add(navTempLeftRdoCold);
        leftRdos.add(navTempLeftRdoHot);
        leftRdos.add(navTempLeftRdoClose);
        rightRdos = new ArrayList<>();
        rightRdos.add(navTempRightRdoCold);
        rightRdos.add(navTempRightRdoHot);
        rightRdos.add(navTempRightRdoClose);


        initTemp();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                navTempPresenter.getTempSetting();
            }
        }, 0, 20000);

        initRdoGroup();


        return view;
    }


    private int leftState;
    private int rightState;

    /**
     * 初始化选择框
     */
    private void initRdoGroup() {
        navTempRdoGroupLeft.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.nav_temp_left_rdo_cold:
                        leftState = 0;
                        break;
                    case R.id.nav_temp_left_rdo_hot:
                        leftState = 1;
                        break;
                    case R.id.nav_temp_left_rdo_close:
                        leftState = 2;
                        break;
                }
            }
        });
        navTempRdoGroupRight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.nav_temp_right_rdo_cold:
                        rightState = 0;
                        break;
                    case R.id.nav_temp_right_rdo_hot:
                        rightState = 1;
                        break;
                    case R.id.nav_temp_right_rdo_close:
                        rightState = 2;
                        break;
                }
            }
        });
    }

    /**
     * 初始化温度显示
     */
    private void initTemp() {


        BoxParams params = new BoxParams();
        Toast.makeText(mContext, params.getAvmSetInfo(), Toast.LENGTH_LONG).show();
        Log.e("NavTempFragment", params.getAvmSetInfo());
        if (!params.getAvmSetInfo().equals("0")) {
            leftRdos.get(Integer.parseInt(params.getLeft_state())).setChecked(true);
            rightRdos.get(Integer.parseInt(params.getRight_state())).setChecked(true);
            changeTemp(params.getLeft_temp(), params.getRight_temp());
            navEditSetTempCold.setText(params.getCold_temp());
            navEditSetTempHot.setText(params.getHot_temp());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void changeTemp(String left, String right) {
        navTempTxtLeftNowTemp.setText(getResources().getString(R.string.string_temp_now) + left + "℃");
        navTempTxtRightNowTemp.setText(getResources().getString(R.string.string_temp_now) + right + "℃");
    }

    @Override
    public void showDialog(String text) {
        dialog = new ZLoadingDialog(mContext);
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
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_temp_btn_ok:
                String cold = navEditSetTempCold.getText().toString();
                String hot = navEditSetTempHot.getText().toString();
                navTempPresenter.setTemp(leftState + "", rightState + "", cold, hot);
                break;
            case R.id.nav_temp_btn_refresh:
                initTemp();
                break;
        }
    }
}
