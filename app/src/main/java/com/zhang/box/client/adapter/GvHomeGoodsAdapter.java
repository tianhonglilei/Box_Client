package com.zhang.box.client.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhang.box.R;
import com.zhang.box.box.BoxSetting;
import com.zhang.box.client.model.Goods;
import com.zhang.box.client.model.RoadGoods;

import java.io.File;
import java.util.List;


import com.zhang.box.client.model.RoadInfo;
import com.zhang.box.client.view.activity.ADBannerActivity;
import com.zhang.box.contants.Constants;

/**
 * Created by lilei on 2017/9/14.
 */

public class GvHomeGoodsAdapter extends MyBaseAdapter<RoadGoods> {

    Context mContext;

    public GvHomeGoodsAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
    }

    public View saleStateView;


    @Override
    protected void convert(RoadGoods roadGoods, MyViewHolder viewHolder, int position) {
        RoadInfo roadInfo = roadGoods.getRoadInfo();
        if (roadInfo.getRoadState() == RoadInfo.ROAD_STATE_ERROR) {
            ((ADBannerActivity) mContext).setRefreshGoods(true);
        }
        Goods goods = roadGoods.getGoods();
        ImageView goodsImg = viewHolder.getView(R.id.adbanner_b_item_img);
        ImageView wdImg = viewHolder.getView(R.id.adbanner_b_item_img_wd);
        saleStateView = viewHolder.getView(R.id.adbanner_b_item_rl_sale_state);
        TextView price = viewHolder.getView(R.id.adbanner_b_item_price);
        TextView rmbIco = viewHolder.getView(R.id.adbanner_b_item_ico);
        TextView score = viewHolder.getView(R.id.adbanner_b_item_score);
        TextView slash = viewHolder.getView(R.id.adbanner_b_item_slash);
        TextView jifen = viewHolder.getView(R.id.adbanner_b_item_jifen);
        TextView cardName = viewHolder.getView(R.id.adbanner_b_item_txt_card_name);
        if (goods.getGoodsType() == Goods.GOODS_TYPE_OTHER) {
            price.setVisibility(View.INVISIBLE);
            rmbIco.setVisibility(View.INVISIBLE);
            score.setVisibility(View.INVISIBLE);
            slash.setVisibility(View.INVISIBLE);
            jifen.setVisibility(View.INVISIBLE);
            cardName.setVisibility(View.VISIBLE);
        } else {
            price.setVisibility(View.VISIBLE);
            rmbIco.setVisibility(View.VISIBLE);
            score.setVisibility(View.VISIBLE);
            slash.setVisibility(View.VISIBLE);
            jifen.setVisibility(View.VISIBLE);
            cardName.setVisibility(View.INVISIBLE);
        }
        price.setText("" + goods.getGoodsPrice());
        score.setText("" + (int) (goods.getGoodsPrice() * 550));
        cardName.setText("" + goods.getGoodsName());
        int state = goods.getGoodsSaleState();
        int wd = goods.getGoodsWd();
        if (state == Goods.SALE_STATE_DISCOUNT) {
            //删除线
            ((TextView) viewHolder.getView(R.id.adbanner_b_item_price)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            saleStateView.setVisibility(View.VISIBLE);
            saleStateView.setBackgroundResource(R.drawable.shape_sale_state_discount);
            viewHolder.getView(R.id.adbanner_b_item_txt_sale_out).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_txt_sale).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_discount_rmb).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_txt_discount_price).setVisibility(View.VISIBLE);
            ((TextView) viewHolder.getView(R.id.adbanner_b_item_txt_discount_price)).setText("" + goods.getGoodsDiscountPrice());
            Glide.with(mContext)
                    .load(new File(Constants.DEMO_FILE_PATH + "/" + goods.getGoodsSImgName()))
                    .into(goodsImg);
        } else if (state == Goods.SALE_STATE_OUT) {
            saleStateView.setVisibility(View.VISIBLE);
            saleStateView.setBackgroundResource(R.drawable.shape_sale_state_off);
            ((TextView) viewHolder.getView(R.id.adbanner_b_item_price)).getPaint().setFlags(0);
            viewHolder.getView(R.id.adbanner_b_item_txt_sale_out).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_txt_sale).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_discount_rmb).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_txt_discount_price).setVisibility(View.INVISIBLE);
            ((TextView) viewHolder.getView(R.id.adbanner_b_item_txt_discount_price)).setText("" + goods.getGoodsDiscountPrice());
            Glide.with(mContext)
                    .load(new File(Constants.DEMO_FILE_PATH + "/" + goods.getGoodsOutImgName()))
                    .into(goodsImg);
        } else if (state == Goods.SALE_STATE_NORMAL) {
            saleStateView.setVisibility(View.INVISIBLE);
            Glide.with(mContext)
                    .load(new File(Constants.DEMO_FILE_PATH + "/" + goods.getGoodsSImgName()))
                    .into(goodsImg);
        }
        switch (wd) {
            case Goods.GOODS_WD_COLD:
                viewHolder.getView(R.id.adbanner_b_item_img_wd).setVisibility(View.VISIBLE);
                Glide.with(mContext).load(R.mipmap.logo_cold).into(wdImg);
                break;
            case Goods.GOODS_WD_HOT:
                viewHolder.getView(R.id.adbanner_b_item_img_wd).setVisibility(View.VISIBLE);
                Glide.with(mContext).load(R.mipmap.logo_hot).into(wdImg);
                break;
            case Goods.GOODS_WD_NORMAL:
                viewHolder.getView(R.id.adbanner_b_item_img_wd).setVisibility(View.INVISIBLE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) price.getLayoutParams();
                params.removeRule(RelativeLayout.ALIGN_RIGHT);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                price.setLayoutParams(params);
                break;
            default:
                break;
        }

        if (position % 2 == 0) {
            viewHolder.getView(R.id.adbanner_b_item_bg_rl).setBackgroundResource(R.drawable.more_goods_gv_item_bg_one_selector);
        } else {
            viewHolder.getView(R.id.adbanner_b_item_bg_rl).setBackgroundResource(R.drawable.more_goods_gv_item_bg_two_selector);
        }

    }
}
