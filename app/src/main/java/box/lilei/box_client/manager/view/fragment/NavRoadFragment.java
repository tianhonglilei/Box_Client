package box.lilei.box_client.manager.view.fragment;


import android.content.Context;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.box.BoxSetting;
import box.lilei.box_client.client.biz.GoodsBiz;
import box.lilei.box_client.client.biz.RoadBiz;
import box.lilei.box_client.client.biz.impl.GoodsBizImpl;
import box.lilei.box_client.client.biz.impl.RoadBizImpl;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.client.model.RoadInfo;
import box.lilei.box_client.manager.adapter.NavRoadAdapter;
import box.lilei.box_client.manager.presenter.NavRoadPresenter;
import box.lilei.box_client.manager.presenter.impl.NavRoadPresenterImpl;
import box.lilei.box_client.manager.view.NavRoadFragmentView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavRoadFragment extends Fragment implements NavRoadFragmentView {


    private NavRoadAdapter navRoadAdapter;

    private GridView navRoadGv;
    private Button navRoadTestBtn;
    private Button navRoadClearBtn;

    private RadioGroup navRdoGrp;
    private RadioButton navRdoRobotMain, navRdoRobotViceOne;
    private Context mContext;
    private NavRoadPresenter navRoadPresenter;
    private List<RoadGoods> roadGoodsList;


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
        navRoadClearBtn = (Button) view.findViewById(R.id.nav_road_btn_clear);
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
                navRoadAdapter.setmDatas(roadGoodsList);
                navRoadAdapter.notifyDataSetChanged();
            }
        });
        navRoadGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtRoadIndex = (TextView) view.findViewById(R.id.nav_road_gv_item_road_num);
                String roadTestTxt = navRoadTestBtn.getText().toString();
                String s1 = roadTestTxt.substring(0, roadTestTxt.indexOf("：") + 1) + txtRoadIndex.getText().toString();
                navRoadTestBtn.setText(s1);
                String roadClearTxt = navRoadClearBtn.getText().toString();
                navRoadClearBtn.setText("" + roadClearTxt.substring(0, roadClearTxt.indexOf("：") + 1) + txtRoadIndex.getText().toString());
            }
        });
    }

    @Override
    public void showViceOne() {
        navRdoRobotViceOne.setVisibility(View.VISIBLE);
    }


}
