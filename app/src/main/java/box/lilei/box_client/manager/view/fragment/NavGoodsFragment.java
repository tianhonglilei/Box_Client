package box.lilei.box_client.manager.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.box.BoxSetting;
import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.contants.Constants;
import box.lilei.box_client.manager.adapter.NavGoodsAdapter;
import box.lilei.box_client.manager.presenter.NavGoodsPresenter;
import box.lilei.box_client.manager.presenter.impl.NavGoodsPresenterImpl;
import box.lilei.box_client.manager.view.NavGoodsFragmentView;

/**
 * A simple {@link Fragment} subclass.
 * 后台商品管理界面
 */
public class NavGoodsFragment extends Fragment implements NavGoodsFragmentView {

    private List<RoadGoods> roadGoodsList;
    private NavGoodsAdapter navGoodsAdapter;
    private RadioButton rdoBoxMain,rdoBoxViceOne;
    private RadioGroup rdogrpBoxCheck;

    private GridView navGoodsGv;
    private NavGoodsPresenter navGoodsPresenter;
    private Context mContext;

    public NavGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_goods, container, false);
        mContext = getContext();
        initView(view);
        navGoodsPresenter = new NavGoodsPresenterImpl(mContext,this);
        navGoodsPresenter.initGoodsGridView(navGoodsGv);







        return view;
    }

    private void initView(View view) {
        navGoodsGv = (GridView) view.findViewById(R.id.nav_goods_gv);
        rdogrpBoxCheck = (RadioGroup) view.findViewById(R.id.nav_goods_rdogrp_robot);
        rdoBoxMain = (RadioButton) view.findViewById(R.id.nav_goods_rdo_robot_main);
        rdoBoxViceOne = (RadioButton) view.findViewById(R.id.nav_goods_rdo_robot_add_one);

        rdogrpBoxCheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.nav_goods_rdo_robot_main){
                    navGoodsPresenter.checkGoodsData(navGoodsGv, BoxSetting.BOX_TYPE_DRINK);
                }else{
                    navGoodsPresenter.checkGoodsData(navGoodsGv, BoxSetting.BOX_TYPE_FOOD);
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showInputDialog() {

    }

    @Override
    public void showViceOne() {
        rdoBoxViceOne.setVisibility(View.VISIBLE);
    }
}
