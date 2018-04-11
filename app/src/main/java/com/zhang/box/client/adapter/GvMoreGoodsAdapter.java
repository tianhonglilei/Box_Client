package com.zhang.box.client.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhang.box.R;
import com.zhang.box.box.BoxSetting;
import com.zhang.box.client.model.Goods;
import com.zhang.box.client.model.RoadGoods;
import com.zhang.box.client.model.RoadInfo;
import com.zhang.box.contants.Constants;

import java.util.List;


/**
 * Created by lilei on 2017/9/25.
 */

public class GvMoreGoodsAdapter extends MyBaseAdapter<RoadGoods> {

    private Context mContext;

    private View saleStateView;

    public GvMoreGoodsAdapter(Context context, List<RoadGoods> datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
    }

    @Override
    protected void convert(RoadGoods roadGoods, MyViewHolder viewHolder, int position) {
        Goods goods = roadGoods.getGoods();
        RoadInfo roadInfo = roadGoods.getRoadInfo();
        ImageView goodsImg = viewHolder.getView(R.id.more_goods_item_img_goods);
        if (goods.getGoodsTaste() != null && !goods.getGoodsTaste().equals("")) {
            ((TextView) viewHolder.getView(R.id.more_goods_item_txt_name)).setText(goods.getGoodsName() + " (" + goods.getGoodsTaste() + ")");
        } else {
            ((TextView) viewHolder.getView(R.id.more_goods_item_txt_name)).setText(goods.getGoodsName());
        }

        ((TextView) viewHolder.getView(R.id.more_goods_item_txt_memo)).setText(goods.getGoodsMemo());
        TextView price = ((TextView) viewHolder.getView(R.id.more_goods_item_txt_price));
        price.setText("" + goods.getGoodsPrice());
        TextView score = viewHolder.getView(R.id.more_goods_item_score);
        TextView rmbIco = viewHolder.getView(R.id.more_goods_item_ico);
        TextView slash = viewHolder.getView(R.id.more_goods_item_slash);
        TextView jifen = viewHolder.getView(R.id.more_goods_item_jifen);

        score.setText("" + (int) (goods.getGoodsPrice() * 550));

        goodsSaleState(viewHolder, goods.getGoodsSaleState(), goods, goodsImg);
        goodsWd(goods.getGoodsWd(), (ImageView) viewHolder.getView(R.id.more_goods_item_img_wd));
        goodsType(viewHolder, goods.getGoodsType(), goodsImg);

        if (roadInfo.getRoadBoxType().equals(BoxSetting.BOX_TYPE_CARD)) {
            price.setVisibility(View.INVISIBLE);
            rmbIco.setVisibility(View.INVISIBLE);
            score.setVisibility(View.INVISIBLE);
            slash.setVisibility(View.INVISIBLE);
            jifen.setVisibility(View.INVISIBLE);
        } else {
            price.setVisibility(View.VISIBLE);
            rmbIco.setVisibility(View.VISIBLE);
            score.setVisibility(View.VISIBLE);
            slash.setVisibility(View.VISIBLE);
            jifen.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 销售状态显示
     *
     * @param viewHolder
     * @param state
     */
    public void goodsSaleState(MyViewHolder viewHolder, int state, Goods goods, ImageView goodsImg) {

        saleStateView = viewHolder.getView(R.id.more_goods_item_rl_sale_state);
        TextView txtPrice = ((TextView) viewHolder.getView(R.id.more_goods_item_txt_price));
        if (state == Goods.SALE_STATE_DISCOUNT) {//打折促销
            //删除线
            txtPrice.getPaint().setStrikeThruText(true);
            saleStateView.setVisibility(View.VISIBLE);
            saleStateView.setBackgroundResource(R.drawable.shape_more_sale_state_discount);
            viewHolder.getView(R.id.more_goods_item_txt_sale_out).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.more_goods_item_txt_sale).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.more_goods_item_discount_rmb).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.more_goods_item_txt_discount_price).setVisibility(View.VISIBLE);
            ((TextView) viewHolder.getView(R.id.more_goods_item_txt_discount_price)).setText("" + goods.getGoodsDiscountPrice());
            Glide.with(mContext).load(Constants.DEMO_FILE_PATH + "/" + goods.getGoodsSImgName()).dontAnimate().into(goodsImg);
        } else if (state == Goods.SALE_STATE_OUT) {//售罄
            txtPrice.getPaint().setFlags(0);
            saleStateView.setVisibility(View.VISIBLE);
            saleStateView.setBackgroundResource(R.drawable.shape_more_sale_state_off);
            viewHolder.getView(R.id.more_goods_item_txt_sale_out).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.more_goods_item_txt_sale).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.more_goods_item_discount_rmb).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.more_goods_item_txt_discount_price).setVisibility(View.INVISIBLE);
            Glide.with(mContext).load(Constants.DEMO_FILE_PATH + "/" + goods.getGoodsOutImgName()).dontAnimate().into(goodsImg);
        } else {
            Glide.with(mContext).load(Constants.DEMO_FILE_PATH + "/" + goods.getGoodsSImgName()).dontAnimate().into(goodsImg);
            saleStateView.setVisibility(View.INVISIBLE);
            txtPrice.getPaint().setFlags(0);
        }
        txtPrice.getPaint().setAntiAlias(true);
    }

    /**
     * 商品类型
     *
     * @param viewHolder
     * @param type
     * @param imageView
     */
    public void goodsType(MyViewHolder viewHolder, int type, ImageView imageView) {
        ImageView wdImg = (ImageView) viewHolder.getView(R.id.more_goods_item_img_wd);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        if (type == Goods.GOODS_TYPE_FOOD) {
            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            imageView.setLayoutParams(params);
            wdImg.setVisibility(View.INVISIBLE);
        } else if (type == Goods.GOODS_TYPE_DRINK) {
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            params.removeRule(RelativeLayout.CENTER_VERTICAL);
            imageView.setLayoutParams(params);
            wdImg.setVisibility(View.VISIBLE);
        }

    }


    /**
     * 温度状态
     *
     * @param wd
     * @param imageView
     */
    public void goodsWd(int wd, ImageView imageView) {
        if (wd == Goods.GOODS_WD_COLD) {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(R.mipmap.logo_cold).into(imageView);
        } else if (wd == Goods.GOODS_WD_HOT) {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(R.mipmap.logo_hot).into(imageView);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }

    }


}
