package box.lilei.box_client.manager.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import box.lilei.box_client.BuildConfig;
import box.lilei.box_client.R;
import box.lilei.box_client.manager.view.fragment.NavContinueFragment;
import box.lilei.box_client.manager.view.fragment.NavExitFragment;
import box.lilei.box_client.manager.view.fragment.NavGoodsFragment;
import box.lilei.box_client.manager.view.fragment.NavRestartFragment;
import box.lilei.box_client.manager.view.fragment.NavRoadFragment;
import box.lilei.box_client.manager.view.fragment.NavTempFragment;
import box.lilei.box_client.manager.view.fragment.NavVersionFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerNavgationActivity extends FragmentActivity{


    //页面切换模块
    @BindView(R.id.manager_nav_grp)
    RadioGroup managerNavGrp;
    private NavGoodsFragment navGoodsFragment;
    private NavRoadFragment navRoadFragment;
    private NavTempFragment navTempFragment;
    private NavVersionFragment navVersionFragment;
    private NavContinueFragment navContinueFragment;
    private NavRestartFragment navRestartFragment;
    private NavExitFragment navExitFragment;
    private Fragment[] fragments;
    private int mIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_navgation);
        ButterKnife.bind(this);

        initView();
        initFragment();

    }

    private void initView() {
        managerNavGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkId) {
//                //遍历RadioGroup 里面所有的子控件。
//                for (int i = 0; i < group.getChildCount(); i++) {
//                    //获取到指定位置的RadioButton
//                    RadioButton rb = (RadioButton)group.getChildAt(i);
//                    //如果被选中
//                    if (rb.isChecked()) {
//                        setIndexSelected(i);
//                        break;
//                    }
//                }
                switch (checkId){
                    case R.id.manager_nav_rdo_btn_goods:
                        setIndexSelected(0);
                        break;
                    case R.id.manager_nav_rdo_btn_road_test:
                        setIndexSelected(1);
                        break;
                    case R.id.manager_nav_rdo_btn_temp:
                        setIndexSelected(2);
                        break;
                    case R.id.manager_nav_rdo_btn_version:
                        setIndexSelected(3);
                        break;
                    case R.id.manager_nav_rdo_btn_continue:
                        setIndexSelected(4);
                        break;
                    case R.id.manager_nav_rdo_btn_restart:
                        setIndexSelected(5);
                        break;
                    case R.id.manager_nav_rdo_btn_exit:
                        setIndexSelected(6);
                        break;
                }

            }
        });

    }


    //默认补货界面
    private void initFragment() {
        navGoodsFragment = new NavGoodsFragment();
        navRoadFragment = new NavRoadFragment();
        navTempFragment = new NavTempFragment();
        navVersionFragment = new NavVersionFragment();
        navContinueFragment = new NavContinueFragment();
        navRestartFragment = new NavRestartFragment();
        navExitFragment = new NavExitFragment();
        //添加到数组
        fragments = new Fragment[]{navGoodsFragment,navRoadFragment,navTempFragment,navVersionFragment,navContinueFragment,navRestartFragment,navExitFragment};
        //开启事务
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        //添加首页
        fragmentTransaction.add(R.id.manager_nav_main_fragment_rl,navGoodsFragment).commit();
        //默认设置为第0个
        setIndexSelected(0);
    }
    //选中显示与隐藏
    private void setIndexSelected(int index) {

        if(mIndex==index){
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        //隐藏
        fragmentTransaction.hide(fragments[mIndex]);
        //判断是否添加
        if(!fragments[index].isAdded()){
            fragmentTransaction.add(R.id.manager_nav_main_fragment_rl,fragments[index]).show(fragments[index]);
        }else {
            fragmentTransaction.show(fragments[index]);
        }

        fragmentTransaction.commit();
        //再次赋值
        mIndex=index;

    }


}
