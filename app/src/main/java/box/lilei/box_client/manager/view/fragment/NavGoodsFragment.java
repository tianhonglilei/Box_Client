package box.lilei.box_client.manager.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.common.controls.dialog.CommonDialogFactory;
import com.common.controls.dialog.DialogUtil;
import com.common.controls.dialog.ICommonDialog;
import com.common.controls.window.WindowPopUtil;

import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.box.BoxSetting;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.client.model.RoadInfo;
import box.lilei.box_client.client.model.paramsmodel.AddGoods;
import box.lilei.box_client.client.view.activity.ActiveActivity;
import box.lilei.box_client.contants.Constants;
import box.lilei.box_client.loading.ZLoadingDialog;
import box.lilei.box_client.loading.Z_TYPE;
import box.lilei.box_client.manager.adapter.NavGoodsAdapter;
import box.lilei.box_client.manager.presenter.NavGoodsPresenter;
import box.lilei.box_client.manager.presenter.impl.NavGoodsPresenterImpl;
import box.lilei.box_client.manager.view.NavGoodsFragmentView;

/**
 * A simple {@link Fragment} subclass.
 * 后台商品管理界面
 */
public class NavGoodsFragment extends Fragment implements NavGoodsFragmentView,View.OnClickListener {

    private List<RoadGoods> roadGoodsList;
    private NavGoodsAdapter navGoodsAdapter;
    private RadioButton rdoBoxMain, rdoBoxViceOne;
    private RadioGroup rdogrpBoxCheck;
    private Button btnAllFull;

    private GridView navGoodsGv;
    private NavGoodsPresenter navGoodsPresenter;
    private Context mContext;

    private LayoutInflater inflater;

    private ZLoadingDialog loadingDialog;

    public NavGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.fragment_nav_goods, container, false);
        mContext = getContext();
        initView(view);
        navGoodsPresenter = new NavGoodsPresenterImpl(mContext, this);
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
                if (checkedId == R.id.nav_goods_rdo_robot_main) {
                    navGoodsPresenter.checkGoodsData(navGoodsGv, BoxSetting.BOX_TYPE_DRINK);
                } else {
                    navGoodsPresenter.checkGoodsData(navGoodsGv, BoxSetting.BOX_TYPE_FOOD);
                }
            }
        });
        btnAllFull = (Button) view.findViewById(R.id.nav_goods_btn_all_full);
        btnAllFull.setOnClickListener(this);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private EditText edit_now;
    private EditText edit_max;
    @Override
    public void showInputDialog(final RoadGoods roadGoods, final int position) {
        final RoadInfo roadInfo = roadGoods.getRoadInfo();
        final Goods goods = roadGoods.getGoods();
        final ICommonDialog inputDialog = CommonDialogFactory.createDialogByType(mContext, DialogUtil.DIALOG_TYPE_202);
        View dialogView = inflater.inflate(R.layout.nav_goods_num_dialog, null);
        edit_now = (EditText) dialogView.findViewById(R.id.nav_goods_dialog_edit_now);
        edit_max = (EditText) dialogView.findViewById(R.id.nav_goods_dialog_edit_max);
        inputDialog.setTitleText(roadInfo.getRoadIndex() + "货道：" + goods.getGoodsName());
        edit_max.setText("" + roadInfo.getRoadMaxNum());
        inputDialog.setContentView(dialogView);
        inputDialog.setOkBtnStyleType(DialogUtil.OK_BTN_SMALL_BLUE_BG_WHITE_TEXT);
        inputDialog.setOkBtn(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String now = edit_now.getText().toString();
                String max = edit_max.getText().toString();
                if (!TextUtils.isEmpty(now)){
                    if (!TextUtils.isEmpty(max)){
                        if (Integer.parseInt(max)>0){
                            if (Integer.parseInt(now)<=Integer.parseInt(max)){
                                roadInfo.setRoadNowNum(Integer.parseInt(now));
                                roadInfo.setRoadMaxNum(Integer.parseInt(max));
                                roadGoods.setRoadInfo(roadInfo);
                                AddGoods addGoods = new AddGoods();
                                addGoods.setHid(roadInfo.getRoadIndex().toString());
                                addGoods.setMachineid(BoxSetting.BOX_TEST_ID);
                                addGoods.setPid(goods.getGoodsId().toString());
                                addGoods.setHgid(roadInfo.getRoadBoxType());
                                addGoods.setHuodao_num(now);
                                addGoods.setHuodao_max(max);
                                navGoodsPresenter.goodsOneToUrl(addGoods,roadGoods,position);
                                inputDialog.dismiss();
                            }else{
                                Toast.makeText(mContext, "当前数不能大于最大数", Toast.LENGTH_SHORT).show();
                                edit_now.setText("");
                                edit_now.setFocusable(true);
                            }
                        }else{
                            Toast.makeText(mContext, "最大数必须大于0", Toast.LENGTH_SHORT).show();
                            edit_max.setText("");
                            edit_max.setFocusable(true);
                        }
                    }else{
                        Toast.makeText(mContext, "最大数不能为空", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(mContext, "当前数不能为空", Toast.LENGTH_SHORT).show();
                }
//                inputDialog.dismiss();
            }
        });
        inputDialog.setCancelBtn(R.string.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog.dismiss();
            }
        });
        inputDialog.setCanceledOnTouchOutside(false);
        inputDialog.show();

    }

    @Override
    public void showViceOne() {
        rdoBoxViceOne.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading(String text) {
        loadingDialog = new ZLoadingDialog(mContext);
        loadingDialog.setLoadingBuilder(Z_TYPE.TEXT)
                .setLoadingColor(Color.parseColor("#ff5307"))
                .setHintText(text)
                .setHintTextSize(16) // 设置字体大小
                .setHintTextColor(Color.parseColor("#525252"))  // 设置字体颜色
                .setCanceledOnTouchOutside(false)
                .show();
    }

    @Override
    public void fullProgress(int count, Long nowRoad ,int success,int fail) {
        if (loadingDialog != null){
            loadingDialog.setHintText("货道总数："+count+"，货道："+nowRoad+"，成功："+ success + "，失败：" + fail);
            if (count == success + fail){
                Toast.makeText(mContext, "补货完成", Toast.LENGTH_SHORT).show();
                navGoodsPresenter.refreshAllRoadNum();
                loadingDialog.dismiss();
            }
        }
    }

    /**
     * 处理点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nav_goods_btn_all_full:
                showOkCancelDialog();
                break;
        }
    }

    public void showOkCancelDialog(){
        final ICommonDialog okDialog = CommonDialogFactory.createDialogByType(mContext, DialogUtil.DIALOG_TYPE_1);
        okDialog.setTitleText("是否执行一键补满？");
        okDialog.setOkBtnStyleType(DialogUtil.OK_BTN_LARGE_BLUE_BG_WHITE_TEXT);
        okDialog.setOkBtn(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navGoodsPresenter.allRoadFull();
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
