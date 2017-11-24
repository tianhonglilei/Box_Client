package box.lilei.box_client.manager.view.fragment;


import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.common.controls.dialog.CommonDialogFactory;
import com.common.controls.dialog.DialogUtil;
import com.common.controls.dialog.ICommonDialog;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.box.BoxAction;
import box.lilei.box_client.box.BoxSetting;
import box.lilei.box_client.client.biz.GoodsBiz;
import box.lilei.box_client.client.biz.RoadBiz;
import box.lilei.box_client.client.biz.impl.GoodsBizImpl;
import box.lilei.box_client.client.biz.impl.RoadBizImpl;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.client.model.RoadInfo;
import box.lilei.box_client.client.receiver.GoodsBroadcastReceiver;
import box.lilei.box_client.client.view.activity.ActiveActivity;
import box.lilei.box_client.loading.ZLoadingDialog;
import box.lilei.box_client.loading.Z_TYPE;
import box.lilei.box_client.manager.adapter.NavRoadAdapter;
import box.lilei.box_client.manager.presenter.NavRoadPresenter;
import box.lilei.box_client.manager.presenter.impl.NavRoadPresenterImpl;
import box.lilei.box_client.manager.view.NavRoadFragmentView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavRoadFragment extends Fragment implements NavRoadFragmentView, View.OnClickListener {

    private static final int OUT_GOODS = 1;

    private NavRoadAdapter navRoadAdapter;

    private GridView navRoadGv;
    private Button navRoadTestBtn;
    private Button navRoadClearBtn;

    private RadioGroup navRdoGrp;
    private RadioButton navRdoRobotMain, navRdoRobotViceOne;
    private Context mContext;
    private NavRoadPresenter navRoadPresenter;
    private List<RoadGoods> roadGoodsList;
    private String name;
    private String index;

    private ZLoadingDialog dialog;

    private GoodsBroadcastReceiver goodsBroadcastReceiver;

    public NavRoadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_road, container, false);
        mContext = this.getContext();
        initView(view);
        navRoadPresenter = new NavRoadPresenterImpl(mContext, this);
        //初始化货道信息
        navRoadPresenter.initRoadInfo();

        return view;
    }

    private void initView(View view) {
        navRoadGv = (GridView) view.findViewById(R.id.nav_road_gv);
        navRoadTestBtn = (Button) view.findViewById(R.id.nav_road_btn_test);
        navRoadTestBtn.setOnClickListener(this);
        navRoadClearBtn = (Button) view.findViewById(R.id.nav_road_btn_clear);
        navRoadClearBtn.setOnClickListener(this);
        navRdoGrp = (RadioGroup) view.findViewById(R.id.nav_road_rdogrp_robot);
        navRdoRobotMain = (RadioButton) view.findViewById(R.id.nav_road_rdo_robot_main);
        navRdoRobotViceOne = (RadioButton) view.findViewById(R.id.nav_road_rdo_robot_add_one);
    }

    @Override
    public void initNavRoadGv(List<RoadGoods> roadGoodsList) {
        this.roadGoodsList = roadGoodsList;
        navRoadAdapter = new NavRoadAdapter(this.getContext(), roadGoodsList, R.layout.nav_road_gv_item);
        navRoadGv.setAdapter(navRoadAdapter);
        initRdoGrp();
    }

    private String boxType = BoxSetting.BOX_TYPE_DRINK;

    /**
     * 初始化选择机器
     */
    private void initRdoGrp() {
        navRdoGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.nav_road_rdo_robot_main) {
                    boxType = BoxSetting.BOX_TYPE_DRINK;
                    roadGoodsList = navRoadPresenter.checkRobot(BoxSetting.BOX_TYPE_DRINK);
                } else {
                    boxType = BoxSetting.BOX_TYPE_FOOD;
                    roadGoodsList = navRoadPresenter.checkRobot(BoxSetting.BOX_TYPE_FOOD);
                }
                navRoadTestBtn.setText(R.string.string_test_this_road);
                navRoadClearBtn.setText(R.string.string_clear_this_road);
                navRoadAdapter.setmDatas(roadGoodsList);
                navRoadAdapter.notifyDataSetChanged();
            }
        });
        navRoadGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RoadGoods roadGoods = navRoadAdapter.getItem(position);
                RoadInfo roadInfo = roadGoods.getRoadInfo();
                if (roadInfo.getRoadIndex() < 10) {
                    index = "0" + roadInfo.getRoadIndex();
                } else {
                    index = roadInfo.getRoadIndex().toString();
                }
                Goods goods = roadGoods.getGoods();
                String s1 = getResources().getString(R.string.string_test_this_road) + index;
                navRoadTestBtn.setText(s1);
                navRoadClearBtn.setText(getResources().getString(R.string.string_clear_this_road) + index);
                name = goods.getGoodsName();
            }
        });
    }

    @Override
    public void showViceOne() {
        navRdoRobotViceOne.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading(String text) {
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
    public void hiddenLoading() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        if (goodsBroadcastReceiver != null){
            mContext.unregisterReceiver(goodsBroadcastReceiver);
        }
    }

    @Override
    public void boxOutGoods() {
//        handler.sendEmptyMessage(OUT_GOODS);
        if (BoxAction.outGoods(boxType, index)) {
            registerGoodsBoradcastReceiver();
        }else{
            hiddenLoading();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case OUT_GOODS:

                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_road_btn_test:
                Toast.makeText(mContext, "测试", Toast.LENGTH_SHORT).show();
                //测试该货道
                if (!((Button) v).getText().toString().equals(getResources().getString(R.string.string_test_this_road))) {
                    navRoadPresenter.testRoad(boxType, index);
                } else {
                    Toast.makeText(mContext, "请选择货道", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_road_btn_clear:
                //清空该货道
                if (!((Button) v).getText().toString().equals(getResources().getString(R.string.string_clear_this_road))) {
                    showOkCancelDialog();
                } else {
                    Toast.makeText(mContext, "请选择货道", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    // 监听出货广播
    private void registerGoodsBoradcastReceiver() {
        goodsBroadcastReceiver = new GoodsBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.avm.serialport.OUT_GOODS");
        mContext.registerReceiver(goodsBroadcastReceiver, filter);
    }

    public void showOkCancelDialog() {
        final ICommonDialog okDialog = CommonDialogFactory.createDialogByType(mContext, DialogUtil.DIALOG_TYPE_1);
        okDialog.setTitleText("是否清空" + index + "货道商品：" + name);
        okDialog.setOkBtnStyleType(DialogUtil.OK_BTN_LARGE_BLUE_BG_WHITE_TEXT);
        okDialog.setOkBtn(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navRoadPresenter.clearRoad(boxType, index);
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

}
