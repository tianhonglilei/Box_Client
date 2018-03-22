package com.zhang.box.manager.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhang.box.R;
import com.zhang.box.client.adapter.MyBaseAdapter;
import com.zhang.box.client.adapter.MyViewHolder;
import com.zhang.box.client.model.Goods;
import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.contants.Constants;

import java.util.List;

/**
 * Created by lilei on 2017/10/25.
 */

public class NavAllGoodsAdapter extends MyBaseAdapter<RoadGoods> {
    public NavAllGoodsAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
    }


    @Override
    protected void convert(RoadGoods roadGoods, MyViewHolder viewHolder, int position) {
        Goods goods = roadGoods.getGoods();
        ((TextView)viewHolder.getView(R.id.nav_goods_set_name_txt)).setText(""+ goods.getGoodsName());
        ImageView goodsImg = viewHolder.getView(R.id.nav_goods_set_img);
        Glide.with(mContext).load(Constants.ONLINE_FILE_PATH + "/" + goods.getGoodsSImgName()).into(goodsImg);
    }
}
