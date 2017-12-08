package com.zhang.box.manager.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhang.box.client.adapter.MyBaseAdapter;
import com.zhang.box.client.adapter.MyViewHolder;
import com.zhang.box.client.model.Goods;
import com.zhang.box.client.model.RoadGoods;

import java.util.List;

import box.lilei.box_client.R;

import com.zhang.box.contants.Constants;

/**
 * Created by lilei on 2017/10/25.
 */

public class NavRoadAdapter extends MyBaseAdapter<RoadGoods> {
    public NavRoadAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
    }


    @Override
    protected void convert(RoadGoods roadGoods, MyViewHolder viewHolder, int position) {
        Goods goods = roadGoods.getGoods();
        ((TextView)viewHolder.getView(R.id.nav_road_gv_item_road_num)).setText(""+ roadGoods.getRoadInfo().getRoadIndex());
        ImageView goodsImg = viewHolder.getView(R.id.nav_road_gv_item_img);
        Glide.with(mContext).load(Constants.DEMO_FILE_PATH + "/" + goods.getGoodsSImgName()).into(goodsImg);
    }
}
