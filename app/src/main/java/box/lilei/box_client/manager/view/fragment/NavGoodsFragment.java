package box.lilei.box_client.manager.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.client.biz.GoodsBiz;
import box.lilei.box_client.client.biz.RoadBiz;
import box.lilei.box_client.client.biz.impl.GoodsBizImpl;
import box.lilei.box_client.client.biz.impl.RoadBizImpl;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.client.model.RoadInfo;
import box.lilei.box_client.manager.adapter.NavGoodsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * 后台商品管理界面
 */
public class NavGoodsFragment extends Fragment {

    List<RoadGoods> roadGoodsList;
    GoodsBiz goodsBiz;
    RoadBiz roadBiz;
    private NavGoodsAdapter navGoodsAdapter;

    GridView navGoodsGv;


    public NavGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_goods, container, false);
        initView(view);


        goodsBiz = new GoodsBizImpl();
        roadBiz = new RoadBizImpl();
        initGoodsInfo();
        initNavGoodsGv();


        return view;
    }

    private void initView(View view) {
        navGoodsGv = (GridView) view.findViewById(R.id.nav_goods_gv);

    }


    //初始化补货商品信息
    private void initGoodsInfo() {
        roadGoodsList = new ArrayList<>();
        List<Goods> goodsList = goodsBiz.getGoodsListInfo();
        List<RoadInfo> roadInfoList = roadBiz.getRoadList();
        RoadGoods roadGoods;
        for (int i = 0; i < roadInfoList.size(); i++) {
            RoadInfo roadInfo = roadInfoList.get(i);
            Goods goods;
            if (i >= goodsList.size()) {
                goods = goodsList.get(i - (i + 1 - goodsList.size()));
            } else {
                goods = goodsList.get(i);
            }
            roadGoods = new RoadGoods(i, goods, roadInfo);
            roadGoodsList.add(roadGoods);
        }

    }

    private void initNavGoodsGv() {
        navGoodsAdapter = new NavGoodsAdapter(this.getContext(), roadGoodsList,R.layout.nav_goods_gv_item);
        navGoodsGv.setAdapter(navGoodsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
