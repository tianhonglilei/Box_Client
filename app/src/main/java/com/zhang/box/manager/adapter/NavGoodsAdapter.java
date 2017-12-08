package com.zhang.box.manager.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhang.box.R;
import com.zhang.box.client.model.Goods;

import java.util.List;

import com.zhang.box.client.adapter.MyBaseAdapter;
import com.zhang.box.client.adapter.MyViewHolder;
import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.model.RoadInfo;
import com.zhang.box.contants.Constants;

/**
 * Created by lilei on 2017/10/25.
 */

public class NavGoodsAdapter extends MyBaseAdapter<RoadGoods> {
    public NavGoodsAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
    }


    @Override
    protected void convert(RoadGoods roadGoods, MyViewHolder viewHolder, int position) {
        Goods goods = roadGoods.getGoods();
        RoadInfo roadInfo = roadGoods.getRoadInfo();
        ((TextView) viewHolder.getView(R.id.nav_goods_gv_item_road_num)).setText("" + roadInfo.getRoadIndex());
        ((TextView) viewHolder.getView(R.id.nav_goods_gv_item_max_num)).setText("" + roadInfo.getRoadMaxNum());
        ((TextView) viewHolder.getView(R.id.nav_goods_gv_item_now_num)).setText("" + roadInfo.getRoadNowNum());
        String name = "";
        if (goods.getGoodsTaste() != null && !goods.getGoodsTaste().equals("")) {
            name = goods.getGoodsName() + "(" + roadGoods.getGoods().getGoodsTaste() + ")";
        }else{
            name = goods.getGoodsName();
        }
        ((TextView) viewHolder.getView(R.id.nav_goods_gv_item_name)).setText(name);
        ImageView goodsImg = viewHolder.getView(R.id.nav_goods_gv_item_img);
        Glide.with(mContext).load(Constants.DEMO_FILE_PATH + "/" + goods.getGoodsSImgName()).into(goodsImg);
    }


}
