package box.lilei.box_client.manager.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import box.lilei.box_client.R;

/**
 * A simple {@link Fragment} subclass.
 * 后台商品管理界面
 *
 */
public class NavGoodsFragment extends Fragment {


    public NavGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initGoodsInfo();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_goods, container, false);
    }

    //初始化补货商品信息
    private void initGoodsInfo() {



    }

}
