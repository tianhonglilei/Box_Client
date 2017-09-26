package box.lilei.box_client.client.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.client.model.Goods;

/**
 * Created by lilei on 2017/9/25.
 */

public class GvMoreGoodsAdapter extends MyBaseAdapter<Goods> {

    private Context mContext;

    private View saleStateView;

    public GvMoreGoodsAdapter(Context context, List<Goods> datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
    }

    @Override
    protected void convert(Goods goods, MyViewHolder viewHolder, int position) {
        ((TextView) viewHolder.getView(R.id.more_goods_item_txt_name)).setText(goods.getGoodsName() + " (" + goods.getGoodsTaste() + ")");

        ImageView goodsImg = viewHolder.getView(R.id.more_goods_item_img_goods);
        ((TextView) viewHolder.getView(R.id.more_goods_item_txt_memo)).setText(goods.getGoodsMemo());
        ((TextView) viewHolder.getView(R.id.more_goods_item_txt_price)).setText("" + goods.getGoodsPrice());
        goodsSaleState(viewHolder, goods.getGoodsSaleState(), goods);
//        goodsWd(viewHolder, goods.getGoodsWd(), ((ImageView) viewHolder.getView(R.id.more_goods_item_img_wd)));
        goodsType(viewHolder, goods.getGoodsType(), goodsImg ,goods.getGoodsWd());
    }

    /**
     * 销售状态显示
     *
     * @param viewHolder
     * @param state
     */
    public void goodsSaleState(MyViewHolder viewHolder, int state, Goods goods) {
        saleStateView = viewHolder.getView(R.id.more_goods_item_rl_sale_state);
        if (state == Goods.SALESTATE_DISCOUNT) {//打折促销
            //删除线
            ((TextView) viewHolder.getView(R.id.more_goods_item_txt_price)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            saleStateView.setVisibility(View.VISIBLE);
            saleStateView.setBackgroundResource(R.drawable.shape_more_sale_state_discount);
            viewHolder.getView(R.id.more_goods_item_txt_sale_out).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.more_goods_item_txt_sale).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.more_goods_item_discount_rmb).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.more_goods_item_txt_discount_price).setVisibility(View.VISIBLE);
            ((TextView) viewHolder.getView(R.id.more_goods_item_txt_discount_price)).setText("" + goods.getGoodsDiscountPrice());
        } else if (state == Goods.SALESTATE_OUT) {//售罄
            ((TextView) viewHolder.getView(R.id.more_goods_item_txt_price)).getPaint().setFlags(0);
            saleStateView.setVisibility(View.VISIBLE);
            saleStateView.setBackgroundResource(R.drawable.shape_more_sale_state_off);
            viewHolder.getView(R.id.more_goods_item_txt_sale_out).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.more_goods_item_txt_sale).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.more_goods_item_discount_rmb).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.more_goods_item_txt_discount_price).setVisibility(View.INVISIBLE);
        } else {
            saleStateView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 商品类型
     * @param viewHolder
     * @param type
     * @param imageView
     * @param wd
     */
    public void goodsType(MyViewHolder viewHolder, int type, ImageView imageView, int wd) {
        ImageView wdImg = (ImageView) viewHolder.getView(R.id.more_goods_item_img_wd);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        if (type == Goods.GOODS_TYPE_FOOD) {
            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.mipmap.food_ww_wld);
            wdImg.setVisibility(View.INVISIBLE);
        } else if (type == Goods.GOODS_TYPE_DRINK) {
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            params.removeRule(RelativeLayout.CENTER_VERTICAL);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.mipmap.goods_xuebi);
            goodsWd(viewHolder, wd, wdImg);
            wdImg.setVisibility(View.VISIBLE);
        } else {

        }

    }


    /**
     * 温度状态
     *
     * @param viewHolder
     * @param wd
     * @param imageView
     */
    public void goodsWd(MyViewHolder viewHolder, int wd, ImageView imageView) {
        if (wd == Goods.GOODS_WD_COLD) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.mipmap.logo_cold);
        } else if (wd == Goods.GOODS_WD_HOT) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.mipmap.logo_hot);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }

    }




}
