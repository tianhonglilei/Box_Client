package box.lilei.box_client.manager.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import box.lilei.box_client.manager.adapter.NavRoadAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavRoadFragment extends Fragment {

    List<RoadGoods> roadGoodsList;
    GoodsBiz goodsBiz;
    RoadBiz roadBiz;
    private NavRoadAdapter navRoadAdapter;

    GridView navRoadGv;
    Button navRoadTestBtn;
    Button navRoadClearBtn;

    private Context context;


    public NavRoadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_road, container, false);


        initView(view);
        initGoodsInfo();
        initNavRoadGv();


        return view;
    }

    private void initView(View view) {
        context = this.getContext();
        goodsBiz = new GoodsBizImpl();
        roadBiz = new RoadBizImpl();
        navRoadGv = (GridView) view.findViewById(R.id.nav_road_gv);
        navRoadTestBtn = (Button) view.findViewById(R.id.nav_road_btn_test);
        navRoadClearBtn = (Button) view.findViewById(R.id.nav_road_btn_clear);
    }

    private void initNavRoadGv() {

        navRoadAdapter = new NavRoadAdapter(this.getContext(), roadGoodsList, R.layout.nav_road_gv_item);
        navRoadGv.setAdapter(navRoadAdapter);
        navRoadGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RoadInfo roadInfo = roadGoodsList.get(position).getRoadInfo();
                String roadTestTxt = navRoadTestBtn.getText().toString();
                String s1 = roadTestTxt.substring(0, roadTestTxt.indexOf("：") + 1) + roadInfo.getRoadIndex();
                navRoadTestBtn.setText(s1);
                String roadClearTxt = navRoadClearBtn.getText().toString();
                navRoadClearBtn.setText("" + roadClearTxt.substring(0, roadClearTxt.indexOf("：") + 1) + roadInfo.getRoadIndex());
            }
        });
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


}
