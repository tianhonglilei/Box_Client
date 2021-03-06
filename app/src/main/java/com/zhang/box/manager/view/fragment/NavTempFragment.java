package com.zhang.box.manager.view.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhang.box.R;
import com.zhang.box.loading.Z_TYPE;
import com.zhang.box.manager.presenter.NavTempPresenter;
import com.zhang.box.manager.presenter.impl.NavTempPresenterImpl;
import com.zhang.box.util.ToastTools;

import java.util.ArrayList;
import java.util.List;

import com.zhang.box.box.BoxParams;
import com.zhang.box.loading.ZLoadingDialog;
import com.zhang.box.manager.view.NavTempFragmentView;
import com.zhang.box.util.SharedPreferencesUtil;

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

    List<RadioButton> leftRdos, rightRdos;


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
        navTempPresenter.getTempSetting();
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
        leftState = Integer.parseInt(SharedPreferencesUtil.getString(mContext,BoxParams.LEFT_STATE));
        leftRdos.get(leftState).setChecked(true);
        rightState = Integer.parseInt(SharedPreferencesUtil.getString(mContext,BoxParams.RIGHT_STATE));
        rightRdos.get(rightState).setChecked(true);
        navEditSetTempCold.setText(SharedPreferencesUtil.getString(mContext,BoxParams.COLD_TEMP));
        navEditSetTempHot.setText(SharedPreferencesUtil.getString(mContext,BoxParams.HOT_TEMP));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    public void setResult(boolean set) {
        if (set) {
            handler.sendEmptyMessage(1);
        } else {
            handler.sendEmptyMessage(2);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(mContext, "设置成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mContext, "设置失败，请重新设置", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_temp_btn_ok:
                String cold = navEditSetTempCold.getText().toString();
                String hot = navEditSetTempHot.getText().toString();
                if (Integer.parseInt(cold) < 0 && Integer.parseInt(cold) > 25) {
                    ToastTools.showShort(mContext, "制冷温度输入有误，请重新输入");
                    navEditSetTempCold.setFocusable(true);
                    return;
                }
                if (Integer.parseInt(hot) < 40 && Integer.parseInt(hot) > 63) {
                    ToastTools.showShort(mContext, "制热温度输入有误，请重新输入");
                    navEditSetTempHot.setFocusable(true);
                    return;
                }
                navTempPresenter.setTemp(leftState + "", rightState + "", cold, hot);
                break;
            case R.id.nav_temp_btn_refresh:
                navTempPresenter.getTempSetting();
                break;
        }
    }
}
